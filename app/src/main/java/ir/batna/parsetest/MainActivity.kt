package ir.batna.parsetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.http.ParseHttpResponse
import ir.batna.parsetest.ui.theme.ParseTestTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParseTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        initializedPars()
        Log.d("alireza", Parse.getServer().toString())
        addGameScore()
    }

    private fun initializedPars() {
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .applicationId(getString(R.string.app_id)) // if defined
                .clientKey(getString(R.string.client_key))
                .server(getString(R.string.server_url))
                .build()
        )
    }

    private fun addGameScore() {
        val gameScore = ParseObject("GameScore")
        gameScore.put("score", 1337)
        gameScore.put("playerName", "Sean Plott")
        gameScore.put("cheatMode", false)
        gameScore.saveInBackground()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ParseTestTheme {
        Greeting("Android")
    }
}