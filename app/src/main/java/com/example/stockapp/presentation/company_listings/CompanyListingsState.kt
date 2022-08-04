package com.example.stockapp.presentation.company_listings

import com.example.stockapp.domain.model.CompanyListingModel

data class CompanyListingsState(
    val companies: List<CompanyListingModel> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
