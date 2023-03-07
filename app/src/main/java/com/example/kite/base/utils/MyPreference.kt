

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Base64
import android.util.Base64InputStream
import android.util.Base64OutputStream
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.appname.structure.utils.DebugLog
import com.example.kite.constants.Constants
import java.io.*


object MyPreference {
    var mSharedPref: SharedPreferences? = null

    fun init(context: Context) {
        if (Build.VERSION.SDK_INT >= 23) {
            mSharedPref = EncryptedSharedPreferences.create(
                Constants.PREFERENCE_NAME,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            if (mSharedPref == null)
                mSharedPref =
                    context.getSharedPreferences(Constants.PREFERENCE_NAME, Activity.MODE_PRIVATE)
        }
    }

    fun getValueString(
        key: String,
        defaultValue: String
    ): String? {
        return mSharedPref?.getString(key, defaultValue)
    }

    fun setValueString(key: String, value: String) {
        mSharedPref?.edit {
            putString(key, value)
            apply()
        }
    }

    fun getValueBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        return mSharedPref!!.getBoolean(key, defaultValue)
    }

    fun setValueBoolean(key: String, value: Boolean) {
        mSharedPref?.edit {
            putBoolean(key, value)
            apply()
        }
    }

    fun getValueInt(
        key: String,
        defaultValue: Int
    ): Int {
        return mSharedPref!!.getInt(key, defaultValue)
    }

    fun setValueInt(key: String, value: Int) {
        mSharedPref?.edit {
            putInt(key, value)
            apply()
        }
    }


    fun clearAllData() {
        mSharedPref?.edit {
            clear()
            clearAllData()
            apply()
        }
    }

    fun setBeanValue(key: String, myObj: Any) {
        val prefsPrivateEditor = mSharedPref!!.edit()

        val arrayOutputStream = ByteArrayOutputStream()

        val objectOutput: ObjectOutputStream
        try {
            objectOutput = ObjectOutputStream(arrayOutputStream)
            objectOutput.writeObject(myObj)
            val data = arrayOutputStream.toByteArray()
            objectOutput.close()
            arrayOutputStream.close()

            val out = ByteArrayOutputStream()
            val b64 = Base64OutputStream(out, Base64.DEFAULT)
            b64.write(data)
            b64.close()
            out.close()

            prefsPrivateEditor.putString(key, String(out.toByteArray()))

            prefsPrivateEditor.apply()
        } catch (e: IOException) {
            DebugLog.print(e)
        }

    }

    fun getBeanValue(key: String): Any? {

        val bytes = mSharedPref!!.getString(key, "{}")!!.toByteArray()
        if (bytes.isEmpty()) {
            return null
        }
        val byteArray = ByteArrayInputStream(bytes)
        val base64InputStream = Base64InputStream(byteArray, Base64.DEFAULT)
        var objIn: ObjectInputStream? = null
        var myObject = Any()
        try {
            objIn = ObjectInputStream(base64InputStream)
            try {
                myObject = objIn.readObject()
            } catch (e: ClassNotFoundException) {
                DebugLog.print(e)
            }

        } catch (e: StreamCorruptedException) {
            DebugLog.print(e)
        } catch (e: IOException) {
            DebugLog.print(e)
        }

        if (objIn != null) {
            try {
                objIn.close()
            } catch (e: IOException) {
                DebugLog.print(e)
            }

        }
        return myObject
    }
}