package com.example.ransomwareinfo.data

import com.example.ransomwareinfo.R

/**
 * 랜섬웨어 항목의 구조를 정의하는 데이터 클래스
 */
data class Ransomware(
    val name: String,
    val type: String,
    val year: Int,
    val method: String,
    val symptoms: String,
    val recoveryStatus: String,
    val recoveryVideoResId: Int? = null // 로컬 동영상 리소스 ID
)

// 앱에서 사용할 샘플 랜섬웨어 데이터 리스트
object RansomwareData {
    val list = listOf(
        Ransomware(
            name = "WannaCry",
            type = "암호화형",
            year = 2017,
            method = "SMB 취약점 (EternalBlue)을 이용한 네트워크 전파",
            symptoms = ".WNCRY 확장자 추가, 바탕화면 변경, 몸값 요구 메시지",
            recoveryStatus = "일부 버전 복호화 가능, 긴급 패치로 확산 차단",
            recoveryVideoResId = null // <--- 동영상 파일 참조
        ),
        Ransomware(
            name = "Ryuk",
            type = "암호화형",
            year = 2018,
            method = "주로 Emotet, TrickBot을 통한 초기 감염 후 수동 공격",
            symptoms = ".ryk 확장자 추가, 고액의 몸값 요구",
            recoveryStatus = "복호화 도구 없음 (백업 권장)",
            recoveryVideoResId = null
        ),
        Ransomware(
            name = "Locky",
            type = "암호화형",
            year = 2016,
            method = "이메일 첨부 파일 (스크립트 포함된 문서)",
            symptoms = ".locky, .zepto 등 확장자 변경, 몸값 요구 메시지",
            recoveryStatus = "일부 초기 버전 복호화 가능",
            recoveryVideoResId = null
        ),
        Ransomware(
            name = "NotPetya",
            type = "와이퍼/변종",
            year = 2017,
            method = "우크라이나 회계 소프트웨어 공급망 공격 및 SMB 취약점",
            symptoms = "파일 복구 불가능 (파괴 목적), 마스터 부트 레코드(MBR) 손상",
            recoveryStatus = "복구 불가능 (주로 파괴 목적의 와이퍼)",
            recoveryVideoResId = null
        )
    )
}