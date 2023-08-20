package ir.batna.parsetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.batna.parsetest.model.Request
import ir.batna.parsetest.model.RequestRepository

class MainViewModel: ViewModel() {
    private val weatherRepository = RequestRepository()

    private val _weatherData = MutableLiveData<Request>()
    val requestData: LiveData<Request> = _weatherData

    fun refreshWeatherData() {
        val weather = weatherRepository.getRequestData()
        _weatherData.value = weather
    }
}