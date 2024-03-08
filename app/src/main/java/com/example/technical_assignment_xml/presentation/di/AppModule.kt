package com.example.technical_assignment_xml.presentation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.technical_assignment_xml.BaseApplication
import com.example.technical_assignment_xml.db.StoreItemDatabase
import com.example.technical_assignment_xml.db.StoreItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): StoreItemDatabase {
        return Room.databaseBuilder(
            app,
            StoreItemDatabase::class.java,
            "storedb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(storeItemDatabase: StoreItemDatabase): StoreItemDao {
        return storeItemDatabase.storeItemDao
    }
}