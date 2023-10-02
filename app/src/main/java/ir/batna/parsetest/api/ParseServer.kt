package ir.batna.parsetest.api

import android.content.Context
import android.util.Log
import com.parse.Parse
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.SignUpCallback
import ir.batna.parsetest.R
import ir.batna.parsetest.model.Request


class ParseServer {
    private val tag = "ParseServer"

    fun addObject(parseObject: ParseObject) {
        parseObject.saveInBackground()
    }

    fun initializePars(applicationContext: Context) {
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE)
        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .applicationId(applicationContext.getString(R.string.app_id)) // if defined
                .clientKey(applicationContext.getString(R.string.client_key))
                .server(applicationContext.getString(R.string.server_url))
                .build()
        )
    }

    fun getAllObjects(): List<Request>{
        return emptyList()
    }

    fun getX(parseObject: ParseObject, x: String, key: String?): Any? {
        return when (x) {
            "int" -> parseObject.getInt(key!!)
            "parseObject" -> parseObject.getParseObject(key!!)
            "string" -> parseObject.getString(key!!)
            "objectId" -> parseObject.objectId
            "updatedAt" -> parseObject.updatedAt
            "createdAt" -> parseObject.createdAt
            "acl" -> parseObject.acl

            else -> null
        }
    }

    fun getObjectFromServer(query: ParseQuery<ParseObject>, objectId: String): ParseObject? {
        var parseObject1: ParseObject? = null
        query.getInBackground(objectId) { parseObject, e ->
            if (e == null) {
                parseObject1 = parseObject
            } else {
                Log.e("parse server", e.message.toString())
            }
        }
        return parseObject1
    }

    fun getFromServer(query: ParseQuery<ParseObject>): List<ParseObject> {
        var result: List<ParseObject> = emptyList()
        query.findInBackground { objects , e ->
            if (objects.isNotEmpty())
                result = objects
            else
                Log.e(tag, "failed get from server with: $e")
        }
        return result
    }

    fun createParseQuery(objectName: String): ParseQuery<ParseObject>{
        return ParseQuery.getQuery(objectName)
    }

    fun signUpUser(parseUser: ParseUser): ParseException?{
        parseUser.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
                Log.d(tag, "SignUp successfully")
                return@signUpInBackground
            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                return@signUpInBackground
            }
        }
        return null
    }
}