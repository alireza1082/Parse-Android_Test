package ir.batna.parsetest.form

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import ir.batna.parsetest.R

class BasicForm {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GreetingForm(name: String, context: Context) {
        var text by remember { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var nameHasError by remember { mutableStateOf(false) }
        var nameLabel by remember { mutableStateOf(name) }

        var email by remember { mutableStateOf("") }
        var emailHasError by remember { mutableStateOf(false) }
        var emailLabel by remember { mutableStateOf("Enter your email address") }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CardDefaults.outlinedShape)
                    .background(MaterialTheme.colorScheme.primary, CardDefaults.shape)
                    .padding(16.dp)
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }
                Column(
                    Modifier.clickable { expanded = !expanded },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(id = R.drawable.ic_launcher_foreground), "image")
                    AnimatedVisibility(visible = expanded) {
                        Text(text = "Jetpack", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}