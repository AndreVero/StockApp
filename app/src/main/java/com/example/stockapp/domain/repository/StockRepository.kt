package com.example.stockapp.domain.repository

import com.example.stockapp.domain.model.CompanyListingModel
import com.example.stockapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ) : Flow<Resource<List<CompanyListingModel>>>
}