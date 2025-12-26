package com.example.ransomwareinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ransomwareinfo.ui.MenuScreen
import com.example.ransomwareinfo.ui.RansomwareDetailScreen
import com.example.ransomwareinfo.ui.RansomwareListScreen
import com.example.ransomwareinfo.ui.RansomwareRecoveryListScreen
import com.example.ransomwareinfo.ui.theme.RansomwareInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RansomwareInfoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RansomwareAppNavigation()
                }
            }
        }
    }
}

@Composable
fun RansomwareAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "menu") {

        // 0. 메뉴 화면 경로
        composable("menu") {
            MenuScreen(
                onNavigateToList = { navController.navigate("list") },
                onNavigateToRecoveryList = { navController.navigate("recovery_list") }
            )
        }

        // 0.5. 랜섬웨어 복구 동영상 목록 화면 경로
        composable("recovery_list") {
            RansomwareRecoveryListScreen(
                onRansomwareClick = { name ->
                    navController.navigate("detail/$name")
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        // 1. 랜섬웨어 전체 목록 화면 (수정됨)
        composable("list") {
            RansomwareListScreen(
                onRansomwareClick = { name ->
                    navController.navigate("detail/$name")
                },
                // 뒤로가기 동작 연결
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        // 2. 상세 정보 화면
        composable(
            route = "detail/{ransomwareName}",
            arguments = listOf(navArgument("ransomwareName") { type = NavType.StringType })
        ) { backStackEntry ->
            val ransomwareName = backStackEntry.arguments?.getString("ransomwareName") ?: return@composable

            RansomwareDetailScreen(
                ransomwareName = ransomwareName,
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}