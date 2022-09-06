package com.example.myplants

import android.app.Application

class Androidmyplants : Application() {

    override fun onCreate() {
        super.onCreate()
        Backend.initialize(applicationContext)
    }
}