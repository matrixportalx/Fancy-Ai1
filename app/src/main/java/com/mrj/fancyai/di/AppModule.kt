package com.mrj.fancyai.di

import android.content.Context
import androidx.room.Room
import com.mrj.fancyai.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "fancy_ai.db"
    ).build()

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideMessageDao(db: AppDatabase) = db.messageDao()

    @Singleton
    @Provides
    fun provideMemoryDao(db: AppDatabase) = db.memoryDao()

    @Singleton
    @Provides
    fun provideDossierDao(db: AppDatabase) = db.dossierDao()

    @Singleton
    @Provides
    fun provideSocialPostDao(db: AppDatabase) = db.socialPostDao()
}
