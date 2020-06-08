package com.naver.heycal

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.naver.heycal.calendar.MyCalendar
import com.naver.heycal.databinding.ActivityMainBinding
import com.naver.heycal.dataclass.NERResponse
import com.naver.heycal.extension.create
import com.naver.heycal.extension.dateTimeOfNERResult
import com.naver.heycal.extension.toast
import com.naver.heycal.network.APIService
import com.naver.heycal.network.RetrofitClient
import com.naver.heycal.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var testDateTime: DateTime
    private lateinit var myCalendar: MyCalendar
    private lateinit var retrofit: Retrofit
    private lateinit var nerAPI: APIService
    private lateinit var permission: Permission
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
        private const val ADAMS_API_KEY = "3631590821428161861"

        private val permissions = arrayOf(
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.RECORD_AUDIO
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrofit = RetrofitClient.getInstnace()
        nerAPI = retrofit.create(APIService::class.java)

        initDataBinding()
        myCalendar = MyCalendar(this)
        launch {
            myCalendar.initCalendar()
            initWebView()
        }
        initButton()

        permission = Permission(this)
        if (!permission.checkPermissions(permissions)) {
            permission.requestPermission()
        }
    }

    override fun onStart() {
        super.onStart()
        // 음성인식 서버 초기화
        mainViewModel.start()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.resume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (!permission.permissionResult(requestCode, grantResults)) {
            permission.requestPermission()
        }
    }

    override fun onStop() {
        super.onStop()
        // 음성인식 서버 종료
        mainViewModel.stop()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        calendarWebView.webViewClient = WebViewClient()
        with(calendarWebView.settings) {
            javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
            loadWithOverviewMode = true // 메타태그 허용 여부
            useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
            setSupportZoom(true) // 화면 줌 허용 여부
            builtInZoomControls = false // 화면 확대 축소 허용 여부
        }
        calendarWebView.loadUrl("https://calendar.google.com/calendar/r") // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }

    private fun initDataBinding() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            mainViewModel = this@MainActivity.mainViewModel
            lifecycleOwner = this@MainActivity
        }
        mainViewModel.init()
        mainViewModel.toastLiveText.observe(this, Observer {
            toast(it)
        })
        mainViewModel.resultLiveText.observe(this, Observer {
            machineLearning(it)
        })
    }

    private fun machineLearning(it: String) {
        //TODO:머신러닝 함수 이쪽에 연결해주세요
        Log.d(TAG, it)
        getDateTimeFromStringNER(it)
    }

    private fun initButton() {
        Log.d(TAG, "init button")
        heyCalBtn.setOnClickListener {
            Log.d(TAG, "hey cal button click")
            startHeyCal()
        }

        /**
         * 현재 시간으로 새로운 일정을 만드는 테스트 버튼
         */
        addEventBtn.setOnClickListener {
            testDateTime = DateTime(System.currentTimeMillis())
            Log.d(TAG, testDateTime.toString())
            launch {
                myCalendar.callCalendarAPI(
                    MyCalendar.CalendarAction.CREATE,
                    Event().create("Title", "description", testDateTime, testDateTime)
                )
                calendarWebView.reload()
            }
        }

        /**
         * testDateTime인 Title일정을 newDescription 과 현재 시간으로 업데이트하는 테스트 버튼
         */
        updateEventBtn.setOnClickListener {
            val newTestDateTime = DateTime(System.currentTimeMillis())
            launch {
                myCalendar.getEvent("Title", testDateTime)?.create(
                    "Title",
                    "newDescription",
                    newTestDateTime,
                    newTestDateTime
                )?.let {
                    myCalendar.callCalendarAPI(MyCalendar.CalendarAction.UPDATE, it)
                    testDateTime = newTestDateTime
                    calendarWebView.reload()
                }
            }
        }
        /**
         * testDateTime인 Title 일정을 삭제하는 테스트 함수
         */
        deleteEventBtn.setOnClickListener {
            launch {
                myCalendar.getEvent("Title", testDateTime)?.let {
                    myCalendar.callCalendarAPI(MyCalendar.CalendarAction.DELETE, it)
                    calendarWebView.reload()
                }
            }
        }
    }

    /**
     * retrofit, ADAMS API를 이용해 input String 값에 대한 NE 값을 받아와 DateTime 객체로 변환하는 메서드
     * 음성 인식 결과 문자열을 인자로 넣어 DateTime 객체을 반환해 calendar create 이벤트에 넘겨주는 형식으로 수정해야 할 것 같습니다.
     */
    private fun getDateTimeFromStringNER(str: String){
        val input = str.replace("오늘", "").replace("이번주", "")

        nerAPI.neAnalysis(Companion.ADAMS_API_KEY, input, "ne", "kor").enqueue(object: Callback<NERResponse> {
            override fun onFailure(call: Call<NERResponse>, t: Throwable) {
                Log.d(TAG, t.message)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<NERResponse>, response: Response<NERResponse>) {
                if(response.code() == 200){
                    val resultOfNER = response.body()

                    if(resultOfNER?.result_code == "success"){
                        val dateTimeFromNER: DateTime =
                            dateTimeOfNERResult(resultOfNER.return_object.sentence[0].ne)
                        Log.d(TAG, "input String : ${resultOfNER.return_object.sentence[0].text}")
                        Log.d(TAG, "DateTime from NER : $dateTimeFromNER")
                        launch {
                            myCalendar.callCalendarAPI(
                                MyCalendar.CalendarAction.CREATE,
                                Event().create(str, str, dateTimeFromNER, dateTimeFromNER)
                            )
                            calendarWebView.reload()
                        }
                    }
                }
            }
        })
    }

    /**
     * STT 이벤트 여기에 적용해주세요
     */

    private fun startHeyCal() {
        mainViewModel.startRecognize()
    }
}