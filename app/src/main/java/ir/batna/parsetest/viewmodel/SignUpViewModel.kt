package ir.batna.parsetest.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseUser
import ir.batna.parsetest.api.ParseServer
import ir.batna.parsetest.form.PasswordValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _loggedIn = MutableStateFlow(false)
    val loggedIn = _loggedIn.asStateFlow()
    private val parseServer = ParseServer()

    private val _usernameState: MutableLiveData<String> = MutableLiveData("")
    val usernameState: LiveData<String> = _usernameState

    private val _passwordState: MutableLiveData<String> = MutableLiveData("")
    val passwordState: LiveData<String> = _passwordState

    private val _mailState: MutableLiveData<String> = MutableLiveData("")
    val mailState: LiveData<String> = _mailState

    fun initParse(applicationContext: Context) {
        parseServer.initializePars(applicationContext = applicationContext)
    }

    fun refreshData(boolean: Boolean) {
        _loggedIn.value = boolean
    }

    fun updateUsername(username: String) {
        _usernameState.value = username
    }

    fun updatePassword(pass: String) {
        _passwordState.value = pass
    }

    fun updateMail(mail: String) {
        _mailState.value = mail
    }

    private fun getParsUser(): ParseUser {
        val user = ParseUser()
        user.username = _usernameState.value
        user.setPassword(_passwordState.value)
        user.email = _mailState.value
        return user
    }

    fun submitButton(): String {
        val pass = PasswordValidator().execute(_passwordState.value!!)
        when {
            _usernameState.value?.isEmpty()!! -> {
                return "username error"
            }

            !Patterns.EMAIL_ADDRESS.matcher(_mailState.value!!).matches() -> {
                return "mail error"
            }

            !pass.successful -> {
                when {
                    !pass.hasMinimum ->
                        return "errorPasswordMinimum"

                    !pass.hasCapitalizedLetter ->
                        return "errorPasswordCapitalize"

                    !pass.hasSpecialCharacter ->
                        return "errorPasswordSpecialChar"
                }
                _passwordState.value = ""
            }

            else -> {
                registerUser()
            }
        }
        return ""
    }

    private fun registerUser() {
        viewModelScope.launch {
            parseServer.signUpUser(getParsUser())
        }
    }
}
