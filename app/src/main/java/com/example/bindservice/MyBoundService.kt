package com.example.bindservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer


/**
 * Created by PrernaSurbhi on 18/02/22.
 */
class MyBoundService: Service() {
    private val iBinder:IBinder = MyBinder()
    lateinit var mChronometer:Chronometer

    override fun onBind(intent: Intent?): IBinder? {
        Log.v(TAG, "in onBind")
       return  iBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(TAG, "in onUnbind")
        return true
    }
    override fun onCreate() {
        Log.v(TAG, "in onCreate")
        mChronometer=  Chronometer(this).apply {
            base = SystemClock.elapsedRealtime()
            start()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "in onDestroy");
        mChronometer.stop();
    }

    class MyBinder : Binder() {
        val service: MyBoundService
            get() = MyBoundService()
    }


    fun getTimestamp(): String? {
        val elapsedMillis = (SystemClock.elapsedRealtime()
                - mChronometer.base)
        val hours = (elapsedMillis / 3600000).toInt()
        val minutes = (elapsedMillis - hours * 3600000).toInt() / 60000
        val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000).toInt() / 1000
        val millis = (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000).toInt()
        return "$hours:$minutes:$seconds:$millis" ?: null
    }


    companion object{
        const val TAG ="MyBoundService"
    }
}