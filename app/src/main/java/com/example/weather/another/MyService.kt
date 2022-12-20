package com.example.weather.another

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

const val BUNDLE_KEY = "key"

class MyService : IntentService("") {

    override fun onHandleIntent(intent: Intent?) {
        Log.d("@@@", " MyService ")
        intent?.let {
            it.getStringExtra(BUNDLE_KEY)
            Log.d("@@@", " MyService ${it.getStringExtra(BUNDLE_KEY)}")
            Log.d("@@@", " onHandleIntent ${Thread.currentThread()}")
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                action = "answer"
            })
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}