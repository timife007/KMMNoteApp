package com.timife.noteapp.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory{
    fun createDriver(): SqlDriver
}