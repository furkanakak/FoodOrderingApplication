package com.example.foodorderingapplication.di

import com.example.foodorderingapplication.BuildConfig
import com.example.foodorderingapplication.model.local.LocalDataSource
import com.example.foodorderingapplication.model.remote.APIService
import com.example.foodorderingapplication.model.remote.AuthAPIService
import com.example.foodorderingapplication.model.remote.AuthRemoteDataSource
import com.example.foodorderingapplication.model.remote.RemoteDataSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
class NetworkModule {

    @Provides
    fun provideApiService(@NoAuthRetrofit retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideAuthApiService(@AuthRetrofit retrofit: Retrofit): AuthAPIService {
        return retrofit.create(AuthAPIService::class.java)
    }

    @Provides
    @NoAuthRetrofit
    fun provideRetrofit(
        noAuthOkHttpClient: NoAuthOkHttpClient,
        gson: Gson,
        endPoint: EndPoint
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(noAuthOkHttpClient.okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): NoAuthOkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        return provideNoAuthOkHttpClient(builder.build())
    }

    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(
        authOkHttpClient: AuthOkHttpClient,
        gson: Gson,
        endPoint: EndPoint
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(authOkHttpClient.okHttpClient)
            .build()
    }


    @Provides
    fun provideAuthInterceptorOkHttpClient(
        localDataSource: LocalDataSource
    ): AuthOkHttpClient {
        return provideAuthOkHttpClient(
            OkHttpClient.Builder()
            .addInterceptor {
                val token = localDataSource.getToken()
                val request = it.request().newBuilder().addHeader("Authorization", token!!).build()
                it.proceed(request)
            }
            .build())
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

//    @Provides
//    fun provideLocalDataSource(
//        sharedPrefManager: SharedPrefManager
//    ): LocalDataSource{
//        return LocalDataSource(sharedPrefManager)
//    }
//
//    @Provides
//    fun provideSharedPrefManager(
//        @ApplicationContext context: Context
//    ): SharedPrefManager{
//        return SharedPrefManager(context)
//    }


    @Provides
    fun provideRemoteDataSource(
        apiService: APIService,
    ): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    fun provideAuthRemoteDataSource(
        authAPIService: AuthAPIService,
    ): AuthRemoteDataSource {
        return AuthRemoteDataSource(authAPIService)
    }

    @Provides
    fun provideEndPoint(): EndPoint {
        return EndPoint("https://food-delivery-app-furkan.herokuapp.com/api/")
    }

    private fun provideAuthOkHttpClient(okHttpClient: OkHttpClient): AuthOkHttpClient {
        return AuthOkHttpClient(okHttpClient)
    }

    private fun provideNoAuthOkHttpClient(okHttpClient: OkHttpClient): NoAuthOkHttpClient {
        return NoAuthOkHttpClient(okHttpClient)
    }
}

data class EndPoint(val url: String)

data class AuthOkHttpClient(val okHttpClient: OkHttpClient)

data class NoAuthOkHttpClient(val okHttpClient: OkHttpClient)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthRetrofit