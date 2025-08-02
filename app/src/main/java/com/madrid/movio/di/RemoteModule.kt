package com.madrid.movio.di

import com.madrid.data.BuildConfig.BASE_URL
import com.madrid.data.dataSource.remote.MovieApi
import com.madrid.data.dataSource.remote.MovieInterceptor
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.data.dataSource.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideRemoteDataSource(

    ):MovieApi{
        val apiVersion = "/3/"
        val urlProtocol = "https://"


        val client = OkHttpClient.Builder().addInterceptor(MovieInterceptor()).build()
        val retrofit :Retrofit = Retrofit.Builder()
            .baseUrl( urlProtocol + BASE_URL + apiVersion)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MovieApi::class.java)
    }

   @Provides
   fun provideRemoteDataSourceImpl(
       movieApi: MovieApi
   ):RemoteDataSource{
      return RemoteDataSourceImpl(movieApi)
   }
}
val remoteModule = module {

//    single<Interceptor> { MovieInterceptor() }
//
//    single { OkHttpClient.Builder().addInterceptor(get<Interceptor>()).build() }
//
//    single<String>(named("baseUrl")) {
//        val apiVersion = "/3/"
//        val urlProtocol = "https://"
//        urlProtocol + BASE_URL + apiVersion
//    }
//
//    single {
//        val retrofit :Retrofit = Retrofit.Builder()
//            .baseUrl(get<String>(named("baseUrl")))
//            .client(get())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//    }
//    single<RemoteDataSource> {  }
    single { Json { ignoreUnknownKeys = true } }
}