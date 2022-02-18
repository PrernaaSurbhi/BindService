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

    var mChronometer:Chronometer? = null


    override fun onBind(intent: Intent?): IBinder? {
        Log.v(TAG, "in onBind")
       return  iBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(TAG, "in onUnbind")
        return true
    }
    override fun onCreate() {
        mChronometer = Chronometer(this)
        mChronometer?.base = SystemClock.elapsedRealtime()
        mChronometer?.start()
        getTimestamp()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "in onDestroy");
        mChronometer?.stop();
    }

    class MyBinder : Binder() {
        val service: MyBoundService
            get() = MyBoundService()
    }


    fun getTimestamp(): String? {
        var time:String? = null
        val elapsedMillis = mChronometer?.let{SystemClock.elapsedRealtime() - it.base}
        elapsedMillis?.let{
            val hours = (elapsedMillis?.div(3600000))?.toInt()
            val timeval  = it.minus(hours * 3600000)
            val minutes = (timeval).toInt() / 60000
            val seconds = (timeval - minutes * 60000).toInt() / 1000
            val millis = (timeval - minutes * 60000 - seconds * 1000).toInt()
             time= "$hours:$minutes:$seconds:$millis"
        }

        return time
    }

    companion object{
        const val TAG ="MyBoundService"
    }

}