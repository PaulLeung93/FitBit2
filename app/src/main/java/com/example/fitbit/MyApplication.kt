package com.example.fitbit

import android.app.Application

class MyApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}