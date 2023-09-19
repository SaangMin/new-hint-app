package com.skysmyoo.new_hint_app.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val moshi: Moshi by lazy { Moshi.Builder().build() }
}