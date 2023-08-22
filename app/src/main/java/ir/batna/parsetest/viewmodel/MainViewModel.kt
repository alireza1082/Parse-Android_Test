package ir.batna.parsetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.batna.parsetest.MainActivity
import ir.batna.parsetest.api.ParseServer
import ir.batna.parsetest.model.Request
import ir.batna.parsetest.model.RequestRepository

class MainViewModel(parseServer: ParseServer, mainActivity: MainActivity) : ViewModel() {
    private val requestRepository = RequestRepository()

    private val _requestData = MutableLiveData<Request>()
    val requestData: LiveData<Request> = _requestData

    init {
        parseServer.initializePars(applicationContext = mainActivity.applicationContext)
    }

    fun refreshWeatherData() {
        val request = requestRepository.getRequestData()
        _requestData.value = request
    }
}