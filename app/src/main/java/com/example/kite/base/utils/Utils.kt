package com.example.kite.base.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.example.kite.BuildConfig
import com.example.kite.base.network.model.ListOfJson
import com.google.gson.Gson
import org.json.JSONArray


object Utils {
    fun sendToSettings(context: Context) {
        AlertDialog.Builder(context).setTitle("Permission Dialog")
            .setMessage("Allow Permission for access module")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                )
                (context as AppCompatActivity).startActivityForResult(intent, 123)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .setCancelable(false).create().show()
    }


    fun <T> toList(
        json: String?,
        typeClass: Class<T>?
    ): List<T>? {
        return Gson().fromJson(json, ListOfJson(typeClass!!))
    }

    fun <T> toClassResponse(
        json: String?,
        typeClass: Class<T>?
    ): T? {
        return if (json == null)
            null
        else
            Gson().fromJson(json, (typeClass!!))
    }

    fun <T> toList(jsonArray: JSONArray?): List<T>? {
        val list: MutableList<T> = ArrayList()
        if (jsonArray != null) {
            val len = jsonArray.length()
            for (i in 0 until len) {
                list.add(jsonArray.opt(i) as T)
            }
        }
        return list


//        val listType: Type = object : TypeToken<ArrayList<T?>?>() {}.type
//        return  Gson().fromJson(jsonArray, listType)
    }

    /**
     * Remove [] from Error Objects when there are multiple errors
     *
     * @param message as String
     * @return replacedString
     */
    fun removeArrayBrace(message: String): String {
        val replaceString: String
        replaceString = message.replace("[\"", "").replace("\"]", "").replace(".", "")
        return replaceString
    }

    fun marginToPx(mContext: Context, margin: Int): Int {
        val r = mContext.resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            mContext.resources.getDimension(margin),
            r.displayMetrics
        ).toInt()
        return px
    }

    fun findWordFromString(
        str: String,
        offset: Int
    ): String { // when you touch ' ', this method returns left word.
        var offset = offset
        if (str.length == offset) {
            offset-- // without this code, you will get exception when touching end of the text
        }

        if (str[offset] == ' ') {
            offset--
        }
        var startIndex = offset
        var endIndex = offset

        try {
            while (str[startIndex] != ' ' && str[startIndex] != '\n') {
                startIndex--
            }
        } catch (e: StringIndexOutOfBoundsException) {
            startIndex = 0
        }

        try {
            while (str[endIndex] != ' ' && str[endIndex] != '\n') {
                endIndex++
            }
        } catch (e: StringIndexOutOfBoundsException) {
            endIndex = str.length
        }

        // without this code, you will get 'here!' instead of 'here'
        // if you use only english, just check whether this is alphabet,
        // but 'I' use korean, so i use below algorithm to get clean word.
        val last = str[endIndex - 1]
        if (last == ',' || last == '.' ||
            last == '!' || last == '?' ||
            last == ':' || last == ';'
        ) {
            endIndex--
        }
        return str.substring(startIndex, endIndex)
    }

    /**
     * start next actiivty with animation
     *
     * @param activity  instance of Activity
     * @param intent    instance of Intent
     * @param canFinish true if want to finish existing activity
     */
    /*  fun startNextActivity(activity: AppCompatActivity, intent: Intent, canFinish: Boolean) {
          activity.startActivity(intent)
          activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left)
          if (canFinish) {
              activity.finish()
          }
      }*/

    internal fun checkIsImageOrNot(path: String): Boolean {
        val regular_expression =
            "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|gif|png))(?:\\?([^#]*))?(?:#(.*))?".toRegex()
        return path.matches(regular_expression)
    }

    /**
     * Get Application Name
     * @param context context
     * @return  application name
     */
    fun getApplicationName(context: Context): String {
        val applicationInfo = context.applicationInfo
        val stringId = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context.getString(
            stringId
        )
    }
}