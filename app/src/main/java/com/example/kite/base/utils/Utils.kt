package com.example.kite.base.utils


object Utils {


    /**
     * Remove [] from Error Objects when there are multiple errors
     *
     * @param message as String
     * @return replacedString
     */
    fun removeArrayBrace(message: String): String {
        val replaceString: String = message.replace("[\"", "").replace("\"]", "").replace(".", "")
        return replaceString
    }

}