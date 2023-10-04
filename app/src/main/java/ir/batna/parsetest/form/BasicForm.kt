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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ir.batna.parsetest.R
import ir.batna.parsetest.viewmodel.SignUpViewModel


class BasicForm() {
    private val tag = "BasicForm"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GreetingForm(context: Context, signUpViewModel: SignUpViewModel) {

        val name = signUpViewModel.usernameState.observeAsState(initial = "")
        var nameHasError by remember { mutableStateOf(false) }
        var nameLabel by remember { mutableStateOf(context.getString(R.string.defaultNameLabel)) }

        val password = signUpViewModel.passwordState.observeAsState(initial = "")
        var passwordHasError by remember { mutableStateOf(false) }
        var passwordLabel by remember { mutableStateOf(context.getString(R.string.defaultPasswordLabel)) }

        val email = signUpViewModel.mailState.observeAsState(initial = "")
        var emailHasError by remember { mutableStateOf(false) }
        var emailLabel by remember { mutableStateOf(context.getString(R.string.defaultEmailLabel)) }

        fun updateFieldsLabels() {
            if (name.value.isNotEmpty())
                nameLabel = context.getString(R.string.defaultNameLabel)
            else
                context.getString(R.string.errorNameLabel)
            if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches())
                emailLabel = context.getString(R.string.defaultEmailLabel)
            else
                emailLabel = context.getString(R.string.errorEmailLabel)
            if (PasswordValidator().execute(password.value).successful)
                context.getString(R.string.defaultPasswordLabel)
            else
                passwordLabel = context.getString(R.string.errorPasswordLabel)
        }

        fun toastApp(id: Int) {
            Toast.makeText(context, context.getString(id), Toast.LENGTH_SHORT)
                .show()
        }

        fun toastApp(string: String) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT)
                .show()
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name.value,
                onValueChange = { signUpViewModel.updateUsername(it) },
                label = { Text(text = nameLabel) },
                modifier = Modifier.padding(20.dp)
            )

            TextField(
                value = email.value,
                isError = emailHasError,
                label = { Text(text = emailLabel) },
                modifier = Modifier.padding(20.dp),
                onValueChange = { signUpViewModel.updateMail(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            TextField(
                value = password.value,
                onValueChange = { signUpViewModel.updatePassword(it) },
                label = { Text(text = passwordLabel) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.padding(20.dp)
            )

            Button(onClick = {
                signUpViewModel.submitButton()
                updateFieldsLabels()

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

            Column {
                CircularProgressIndicator()
            }
        }
    }
}