package com.example.ransomwareinfo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ransomwareinfo.data.RansomwareData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RansomwareRecoveryListScreen(
    onRansomwareClick: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    // 동영상 ID가 null이 아닌 항목(복구 동영상이 있는 항목)만 필터링합니다.
    val recoveryList = RansomwareData.list.filter { it.recoveryVideoResId != null }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("복구 동영상 가이드 목록") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (recoveryList.isEmpty()) {
            Text(
                "현재 동영상 복구 가이드가 제공되는 랜섬웨어가 없습니다.",
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recoveryList) { ransomware ->
                    // RansomwareListScreen.kt에 정의된 항목 UI 재사용
                    RansomwareListItem(
                        name = ransomware.name,
                        type = ransomware.type,
                        year = ransomware.year,
                        onClick = { onRansomwareClick(ransomware.name) }
                    )
                }
            }
        }
    }
}