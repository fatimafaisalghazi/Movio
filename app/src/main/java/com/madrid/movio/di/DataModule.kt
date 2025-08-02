package com.madrid.movio.di

import android.content.Context
import androidx.room.Room
import com.madrid.data.dataSource.encrypted.AuthenticationDatastoreImpl
import com.madrid.data.dataSource.local.LocalDataSourceImpl
import com.madrid.data.dataSource.local.dao.ArtistDao
import com.madrid.data.dataSource.local.dao.MovieDao
import com.madrid.data.dataSource.local.dao.MovieGenreDao
import com.madrid.data.dataSource.local.dao.RecentSearchDao
import com.madrid.data.dataSource.local.dao.SeriesDao
import com.madrid.data.dataSource.local.dao.SeriesGenreDao
import com.madrid.data.dataSource.local.database.MovioDatabase
import com.madrid.data.repositories.ArtistRepositoryImpl
import com.madrid.data.repositories.MovieRepositoryImpl
import com.madrid.data.repositories.SearchRepositoryImpl
import com.madrid.data.repositories.SeriesRepositoryImpl
import com.madrid.data.repositories.UserRepositoryImpl
import com.madrid.data.repositories.datasource.AuthenticationDataSource
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.detectImageContent.GetImageBitmap
import com.madrid.detectImageContent.SensitiveContentDetection
import com.madrid.domain.repository.ArtistRepository
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.Singleton
import org.koin.dsl.module

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideSearchRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    fun provideMovieRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideSeriesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): SeriesRepository {
        return SeriesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideArtistRepository(
        remoteDataSource: RemoteDataSource
    ): ArtistRepository {
        return ArtistRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideUserRepository(
//        localDataSource: LocalDataSource,
//        remoteDataSource: RemoteDataSource,
//        authenticationDataSource: AuthenticationDataSource
    ): UserRepository {
        return UserRepositoryImpl(
//            localDataSource = localDataSource,
//            remoteDataSource = remoteDataSource,
//            authenticationDatasource = authenticationDataSource
        )
    }

    @Provides
    fun provideAuthenticationDataSource(context: Context): AuthenticationDataSource {
        return AuthenticationDatastoreImpl(context)
    }

    @Provides
    fun provideGetImageBitmap(context: Context): GetImageBitmap {
        return GetImageBitmap(context)
    }

    @Provides
    fun provideSensitiveContentDetection(context: Context): SensitiveContentDetection {
        return SensitiveContentDetection(context)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideMovioDatabase(
        context: Context
    ): MovioDatabase {
        val DATABASE_NAME = "MOVIO_DATABASE"

        return synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                MovioDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration(true)
                .build()

        }
    }

        @Provides
        fun provideLocalDataSource(
            database: MovioDatabase
        ): LocalDataSource {
            return LocalDataSourceImpl(
                movieDao = database.movieDao(),
                seriesDao = database.seriesDao(),
                artistDao = database.artistDao(),
                movieGenreDao = database.movieGenreDao(),
                seriesGenreDao = database.seriesGenreDao(),
                recentSearchDao = database.recentSearchDao(),
            )
        }

    }

    val dataModule = module {
//    single<LocalDataSource> { LocalDataSourceImpl(get(), get(), get(), get(), get(), get()) }
//    single { get<MovioDatabase>().movieDao() }
//    single { get<MovioDatabase>().seriesDao() }
//    single { get<MovioDatabase>().artistDao() }
//    single { get<MovioDatabase>().movieGenreDao() }
//    single { get<MovioDatabase>().seriesGenreDao() }
//    single { get<MovioDatabase>().recentSearchDao() }


//    single { MovioDatabase.getInstance(androidContext()) }
//    single<AuthenticationDataSource> { AuthenticationDatastoreImpl(androidContext()) }
//    single { GetImageBitmap(get()) }
//    single { SensitiveContentDetection(get()) }


//    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
//    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
//    single<SeriesRepository> { SeriesRepositoryImpl(get(), get()) }
//    single<ArtistRepository> { ArtistRepositoryImpl(get()) }
//    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

    }