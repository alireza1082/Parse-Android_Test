package ir.batna.parsetest.model

data class Request(
    var objectName: String)

class RequestRepository {
    fun getRequestData(): Request {
        // Fetch weather data from a remote server or local storage
        return Request("Sunny")
    }
}