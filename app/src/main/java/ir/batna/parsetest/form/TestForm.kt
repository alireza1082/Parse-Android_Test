package ir.batna.parsetest.form

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


class TestForm {
    private val tag = "TestForm"

    @Composable
    fun GreetingForm(
        context: Context,
        onClick: () -> Unit,
    ) {

        fun toastApp(string: String) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT)
                .show()
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                onClick()
                Log.i(tag, "submit button click")
                toastApp("clicked")
            }) {
                Text("Submit")

            }
        }
    }
}