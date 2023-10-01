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
    fun GreetingForm(context: Context) {

        var name by remember { mutableStateOf("") }
        var nameHasError by remember { mutableStateOf(false) }
        var nameLabel by remember { mutableStateOf(context.getString(R.string.defaultNameLabel)) }

        var password by rememberSaveable { mutableStateOf("") }
        var passwordHasError by remember { mutableStateOf(false) }
        var passwordLabel by remember { mutableStateOf(context.getString(R.string.defaultPasswordLabel)) }

        var email by remember { mutableStateOf("") }
        var emailHasError by remember { mutableStateOf(false) }
        var emailLabel by remember { mutableStateOf(context.getString(R.string.defaultEmailLabel)) }

        fun checkValidateFields() {
            if (name.isNotEmpty())
                nameLabel = context.getString(R.string.defaultNameLabel)
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailLabel = context.getString(R.string.defaultEmailLabel)
            if (PasswordValidator().execute(password).successful)
                context.getString(R.string.defaultPasswordLabel)
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = nameLabel) },
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(20.dp)
            )

            TextField(
                value = email,
                isError = emailHasError,
                label = { Text(text = emailLabel) },
                modifier = Modifier.padding(20.dp),
                onValueChange = { value -> email = value },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = passwordLabel) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.padding(20.dp)
            )

            Button(onClick = {
                when {
                    name.isEmpty() -> {
                        nameHasError = true
                        nameLabel = context.getString(R.string.errorNameLabel)
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        emailHasError = true
                        emailLabel = context.getString(R.string.errorEmailLabel)
                    }

                    !PasswordValidator().execute(password).successful -> {
                        passwordHasError = true
                        passwordLabel = context.getString(R.string.errorPasswordLabel)
                        password = ""
                    }

                    else -> Toast.makeText(context, "All fields are valid!", Toast.LENGTH_SHORT)
                        .show()
                }

                checkValidateFields()

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