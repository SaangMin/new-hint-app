package com.skysmyoo.new_hint_app.di

import android.content.Context
import androidx.room.Room
import com.skysmyoo.new_hint_app.data.source.local.AppDatabase
import com.skysmyoo.new_hint_app.data.source.local.SharedPreferencesManager
import com.skysmyoo.new_hint_app.data.source.local.StoreModelDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val moshi: Moshi by lazy { Moshi.Builder().build() }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hint-database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideStoreModelDao(appDatabase: AppDatabase): StoreModelDao {
        return appDatabase.storeModelDao()
    }

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }
}