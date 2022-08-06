package com.example.stockapp.presentation.company_info

import com.example.stockapp.domain.model.CompanyInfoModel
import com.example.stockapp.domain.model.IntradayInfoModel

data class CompanyInfoState (
    val stockInfos: List<IntradayInfoModel> = emptyList(),
    val company: CompanyInfoModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)