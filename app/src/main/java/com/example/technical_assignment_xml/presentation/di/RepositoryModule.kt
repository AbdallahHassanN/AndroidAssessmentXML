package com.example.technical_assignment_xml.presentation.di


import android.content.Context
import com.example.technical_assignment_xml.network.ShopService
import com.example.technical_assignment_xml.domain.repository.Repository
import com.example.technical_assignment_xml.repository.RepositoryImpl
import com.example.technical_assignment_xml.db.StoreItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        shopService: ShopService,
        @ApplicationContext appContext: Context, // Use @ApplicationContext to get the application context
        storeItemDao: StoreItemDao
    ): Repository {
        return RepositoryImpl(shopService,appContext,storeItemDao)
    }
}