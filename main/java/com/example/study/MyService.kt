package com.example.study

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class MyService : Service() {
    companion object{
        val My_INTENT = "com.psw.android.test.intent"
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let{
            startDynamicBroadCastReceiver()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    val mReceiver : BroadcastReceiver by lazy{
        object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent) {
                if(intent.action == My_INTENT){
                    if (context != null) {
                        showDialog(context)
                    }
                }
            }
        }
    }
    private fun showDialog(context: Context){
        val i = Intent(context,MainActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.putExtra("message","user boradcast")
        context.startActivity(i)
    }

    private fun startDynamicBroadCastReceiver(){
        val theFilter = IntentFilter()
        theFilter.addAction(My_INTENT)
        registerReceiver(this.mReceiver,theFilter)
    }
}
