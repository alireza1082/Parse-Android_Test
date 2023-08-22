package ir.batna.parsetest.api

import android.content.Context
import com.parse.Parse
import com.parse.ParseObject
import ir.batna.parsetest.R

class ParseServer {

    fun addObject(parseObject: ParseObject) {
        parseObject.saveInBackground()
    }

    fun initializePars(applicationContext: Context) {
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .applicationId(applicationContext.getString(R.string.app_id)) // if defined
                .clientKey(applicationContext.getString(R.string.client_key))
                .server(applicationContext.getString(R.string.server_url))
                .build()
        )
    }
}