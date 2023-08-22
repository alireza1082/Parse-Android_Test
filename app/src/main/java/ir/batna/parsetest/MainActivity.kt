package ir.batna.parsetest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.outlinedShape
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parse.Parse
import com.parse.ParseObject
import ir.batna.parsetest.api.ParseServer
import ir.batna.parsetest.ui.theme.ParseTestTheme
import ir.batna.parsetest.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParseTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", this)
                }
            }
        }
        mainViewModel = MainViewModel(ParseServer(), this)
        Log.d("alireza", Parse.getServer().toString())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, context: Context) {
    var text by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var nameHasError by remember { mutableStateOf(false) }
    var nameLabel by remember { mutableStateOf(name) }

    var email by remember { mutableStateOf("") }
    var emailHasError by remember { mutableStateOf(false) }
    var emailLabel by remember { mutableStateOf("Enter your email address") }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = nameLabel) },
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(20.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(20.dp)
        )

        TextField(
            value = email,
            isError = emailHasError,
            label = { Text(text = emailLabel) },
            modifier = Modifier.padding(20.dp),
            onValueChange = { value -> email = value },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Button(onClick = {
            when {
                text.isEmpty() -> {
                    nameHasError = true
                    nameLabel = "Name cannot be empty"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailHasError = true
                    emailLabel = "Invalid email address"
                }
                else -> Toast.makeText(context, "All fields are valid!", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit")

        }
        Card (modifier = Modifier
            .padding(16.dp)
            .border(2.dp, MaterialTheme.colorScheme.secondary, outlinedShape)
            .background(MaterialTheme.colorScheme.primary, shape)
            .padding(16.dp)) {
            var expanded by remember {
                mutableStateOf(false)
            }
            Column(Modifier.clickable { expanded = !expanded },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(id = R.drawable.ic_launcher_foreground), "image")
                AnimatedVisibility(visible = expanded) {
                    Text(text = "Jetpack", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}