package com.example.bindservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var boundService:MyBoundService? = null
    private var bound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val serviceConnection:ServiceConnection = object:ServiceConnection{

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            var binder = binder as MyBoundService.MyBinder
            boundService = binder.service
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

    }

    override fun onStart() {
        super.onStart()
        val bindingIntent = Intent(this,MyBoundService::class.java)
        //The code Context.BIND_AUTO_CREATE tells Android to create the service if it doesn’t already exist.
        bindService(bindingIntent,serviceConnection,Context.BIND_AUTO_CREATE)
    }

    override fun onClick(view: View?) {
        when(view){
            
        }
    }


}