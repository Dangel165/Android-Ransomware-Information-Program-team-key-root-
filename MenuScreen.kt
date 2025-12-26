package com.example.ransomwareinfo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onNavigateToList: () -> Unit,
    onNavigateToRecoveryList: () -> Unit // 복구 동영상 목록으로 이동
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🛡️ 랜섬웨어 정보 센터") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "원하는 메뉴를 선택해주세요.",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 1. 랜섬웨어 목록 메뉴 카드
            MenuItemCard(
                icon = Icons.Default.List,
                title = "랜섬웨어 전체 목록",
                description = "주요 랜섬웨어의 상세 정보 및 특징을 모두 확인합니다.",
                onClick = onNavigateToList
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. 복구 동영상 목록 메뉴 카드
            MenuItemCard(
                icon = Icons.Default.PlayCircle,
                title = "랜섬웨어 복구 동영상",
                description = "동영상 가이드가 제공되는 랜섬웨어의 복구 방법을 확인합니다.",
                onClick = onNavigateToRecoveryList // 복구 목록 화면으로 이동
            )
        }
    }
}

@Composable
fun MenuItemCard(icon: ImageVector, title: String, description: String, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}