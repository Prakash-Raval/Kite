package com.appname.structure.utils

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

/**
 * Created by rajanbhavsar on 20/11/19, 1:55 PM
 * Package Name : com.appname.structure.utils
 * Project Name : BrainvireStructure
 */

object Validation {

    /**
     * method is used for checking valid email id format.
     *
     * @param email  email address as String
     * @return boolean true for valid false for invalid
     */
    fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    /**
     * method is used for checking password is in valid format.
     *
     * @param password password as String
     *
     * 1 Upper case, 1 Lower case, 1 Special Characters and 6-12 characters
     * @return boolean true for valid false for invalid
     */
    fun isValidPassword(password: String): Boolean {
        //        String patn = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,12})";
        val patn = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[#?!@$%^&*-]).{6,12})"
        val pattern = Pattern.compile(patn, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    /**
     * method is used for checking if string is empty or not.
     *
     * @param mString as String
     * @return boolean true if [mString] is notnull.
     */
    fun isNotNull(mString: String?): Boolean {
        return if (mString == null) {
            false
        } else if (mString.equals("", ignoreCase = true)) {
            false
        } else if (mString.equals("N/A", ignoreCase = true)) {
            false
        } else if (mString.equals("[]", ignoreCase = true)) {
            false
        } else if (mString.equals("null", ignoreCase = true)) {
            false
        } else
            !mString.equals("{}", ignoreCase = true)
    }
}
