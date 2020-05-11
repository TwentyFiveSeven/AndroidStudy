package com.example.invite_scrollingactivity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract.Events
import android.provider.CalendarContract

class ScrollingActivity : AppCompatActivity() {
    val dateStr = "2020/05/09"
    val WeddingPlaceStr = "서울특별시 용산구 용산2가동 남산공원길 105"
    val geoStr ="37.5509473,126.9871078"
    val WeddingMsg = "00이네 결혼식"
    val MessageStr = "오랜세월 \n"+"오셔서 축복해주세요\n"+"신랑 : 오오오\n"+"신부 : 우우우\n"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        toolbar_layout.title = "축복해주세요"

        txtCount?.text =
            setDayCount()?.let{
                if( it> 0) "^^"
                else "${it*-1}일"
            }
        txtMessage?.text = MessageStr.format("백모군","신모양")
        fab.setOnClickListener { view ->
            Snackbar.make(view, "오시는 곳\n${WeddingPlaceStr}", Snackbar.LENGTH_LONG)
                    .setAction("지도 찾기", {startMapActivity()}).show()
        }
        anlClock.setOnClickListener {
            addCalendar(2018,11,1,12,0,WeddingMsg)
        }
    }
    private fun startMapActivity(){
        val uri = Uri.parse("geo:${geoStr}")
        val intent = Intent(android.content.Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
    private fun setDayCount() : Long {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val date = sdf.parse(dateStr)
        val d = Date().apply{time = Date().time - date.time}
        return (d.time/(60*60*24*1000))
    }
    private fun addCalendar(y : Int, m : Int, d : Int, hh: Int, mm : Int, msg : String){
        val beginTime = Calendar.getInstance()
        beginTime.set(y,m,d,hh,mm)
        val endTime = Calendar.getInstance()
        endTime.set(y,m,d,hh,mm)
        val intent = Intent(Intent.ACTION_INSERT).apply{
            setData(Events.CONTENT_URI)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,beginTime.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime.timeInMillis)
            putExtra(Events.TITLE,msg)
            putExtra(Events.DESCRIPTION, msg)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
