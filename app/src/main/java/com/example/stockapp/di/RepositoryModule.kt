package com.example.stockapp.di

import com.example.stockapp.data.csv.CSVParser
import com.example.stockapp.data.csv.CompanyListingsParser
import com.example.stockapp.data.repository.StockRepositoryImpl
import com.example.stockapp.domain.model.CompanyListingModel
import com.example.stockapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListingModel>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}