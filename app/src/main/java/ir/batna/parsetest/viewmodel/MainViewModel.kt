package ir.batna.parsetest.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.parse.ParseObject
import ir.batna.parsetest.MainActivity
import ir.batna.parsetest.api.ParseServer
import ir.batna.parsetest.model.Request
import ir.batna.parsetest.model.RequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(parseServer: ParseServer, mainActivity: MainActivity) : ViewModel() {

    private val requestRepository = RequestRepository()
    private val _requestData = MutableStateFlow(emptyList<Request>())
    val requestData = _requestData.asStateFlow()

    private var parseServerObject: ParseServer

    @SuppressLint("StaticFieldLeak")
    private var applicationContext: Context

    init {
        parseServer.initializePars(applicationContext = mainActivity.applicationContext)
        parseServerObject = parseServer
        applicationContext = mainActivity.applicationContext
    }

    fun refreshData() {
        val request = parseServerObject.getAllObjects()
        _requestData.value = request
    }

    fun addObjectToServer(parseObject: ParseObject) {
        parseServerObject.addObject(parseObject)
    }
}