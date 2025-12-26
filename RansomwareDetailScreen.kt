package com.example.ransomwareinfo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ransomwareinfo.data.RansomwareData

// 로컬 동영상 재생을 위한 Android View 클래스 import
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RansomwareDetailScreen(ransomwareName: String, onBackClicked: () -> Unit) {
    val ransomware = RansomwareData.list.find { it.name == ransomwareName }
    val context = LocalContext.current // VideoView 생성에 필요

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ransomwareName) },
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
        if (ransomware != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 상단에 랜섬웨어 유형 정보 카드 추가
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "유형: ${ransomware.type} (${ransomware.year}년 발견)",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                DetailItem(label = "주요 유포 방식", value = ransomware.method)
                DetailItem(label = "주요 증상 및 확장자", value = ransomware.symptoms)

                // 👇 랜섬웨어 복구 동영상 섹션 시작 👇
                ransomware.recoveryVideoResId?.let { videoResId ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "🎥 랜섬웨어 복구 동영상 가이드",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 1. 동영상 설명/안내 문구 섹션 (수정 가능 영역)
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "이 동영상은 ${ransomware.name} 랜섬웨어에 대한 일반적인 복구 절차를 보여줍니다. 시작 전 반드시 백업 여부를 확인하세요.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. 실제 동영상 재생 영역 (VideoView)
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            factory = {
                                VideoView(context).apply {
                                    // 미디어 컨트롤러 설정 (재생/정지 UI)
                                    val mediaController = MediaController(context)
                                    mediaController.setAnchorView(this)
                                    setMediaController(mediaController)

                                    // 동영상 리소스 URI 설정
                                    val uri = Uri.parse("android.resource://" + context.packageName + "/" + videoResId)
                                    setVideoURI(uri)

                                    // 준비되면 자동 재생
                                    setOnPreparedListener { mp ->
                                        mp.isLooping = false
                                        start()
                                    }
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                // 👆 랜섬웨어 복구 동영상 섹션 끝 👆

                // 복구 현황을 강조하기 위한 다른 카드 사용
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("⚠️ 복구 현황", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(ransomware.recoveryStatus, style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 예방 팁
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("💡 예방 팁", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("출처가 불분명한 이메일 첨부 파일이나 링크를 열지 마세요. 중요한 파일은 반드시 외부 저장 장치에 백업하세요.", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        } else {
            Box(Modifier.fillMaxSize()) {
                Text("데이터를 찾을 수 없습니다.", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        Divider(color = Color.LightGray, thickness = 0.5.dp)
    }
}