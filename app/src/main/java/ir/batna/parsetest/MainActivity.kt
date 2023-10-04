package ir.batna.parsetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.parse.Parse
import ir.batna.parsetest.form.BasicForm
import ir.batna.parsetest.ui.theme.ParseTestTheme
import ir.batna.parsetest.viewmodel.SignUpViewModel

class MainActivity : ComponentActivity() {
    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParseTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    BasicForm().GreetingForm(this, signUpViewModel = signUpViewModel)
                }
            }
        }
        signUpViewModel = SignUpViewModel().also {
            it.initParse(this.applicationContext)
        }
        Log.d("alireza", Parse.getServer().toString())
    }
}