package ir.batna.parsetest

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
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

        getDeviceInfo()
    }

    @SuppressLint("HardwareIds")
    fun getDeviceInfo() {
        val deviceModel = Build.MODEL
        val deviceBrand = Build.BRAND
        val deviceManufacturer = Build.MANUFACTURER
        val deviceName = Build.DEVICE
        val deviceBase = Build.VERSION_CODES.BASE
        val deviceSdk = Build.VERSION.SDK_INT
        val deviceBuildId = Build.ID
        val deviceVersionCode = Build.VERSION.RELEASE
        val deviceCodeInc = Build.VERSION.INCREMENTAL

        val manager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        manager.simCarrierIdName
        val localSubscriptionManager = SubscriptionManager.from(this)

        Log.i(
            "MainActivity", "deviceBrand: $deviceBrand \n" +
                    "deviceModel: $deviceModel \n" +
                    "deviceName: $deviceName \n" +
                    "deviceBase: $deviceBase" +
                    "ID: $deviceBuildId \n" +
                    "SDK: $deviceSdk \n" +
                    "Manufacture: $deviceManufacturer \n" +
                    "versionCodeRelease: $deviceVersionCode \n" +
                    "deviceCodeInc: $deviceCodeInc \n" +
                    "simOperatorName: ${manager.simOperatorName} \n" +
                    "simCarrierIdName: ${manager.simCarrierIdName} \n" +
                    "networkOperatorName: ${manager.networkOperatorName} \n" +
                    "networkCountryIso: ${manager.networkCountryIso} \n"
        )
    }
}