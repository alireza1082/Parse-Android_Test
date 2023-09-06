package ir.batna.parsetest.model

data class Request(
    var objectName: String,
    var title: String,
    var body: String,
    var lastEdite: String
)

class RequestRepository {
    fun getRequestData(): List<Request> {
        // Fetch weather data from a remote server or local storage
        return listOf(Request("Sunny", "", "", ""))

    }
}
