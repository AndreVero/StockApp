package com.example.stockapp.data.repository

import com.example.stockapp.data.csv.CSVParser
import com.example.stockapp.data.local.StockDatabase
import com.example.stockapp.data.mappers.toCompanyInfoModel
import com.example.stockapp.data.mappers.toCompanyListing
import com.example.stockapp.data.mappers.toCompanyListingEntity
import com.example.stockapp.data.remote.StockApi
import com.example.stockapp.domain.model.CompanyInfoModel
import com.example.stockapp.domain.model.CompanyListingModel
import com.example.stockapp.domain.model.IntradayInfoModel
import com.example.stockapp.domain.repository.StockRepository
import com.example.stockapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListingModel>,
    private val intradayInfoParser: CSVParser<IntradayInfoModel>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListingModel>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListing?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListing("").map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfoModel>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't intraday data")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't intraday data")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfoModel> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfoModel())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't company info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't company info")
        }
    }
}