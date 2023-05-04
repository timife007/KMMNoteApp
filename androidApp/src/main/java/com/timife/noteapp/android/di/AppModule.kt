package com.timife.noteapp.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import com.timife.noteapp.data.local.DatabaseDriverFactory
import com.timife.noteapp.data.note.SqlDelightNoteDataSource
import com.timife.noteapp.database.NoteDatabase
import com.timife.noteapp.domain.note.NoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app:Application):SqlDriver{
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver:SqlDriver): NoteDataSource{
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }
}