package com.example.kite.ridedetails.extra

import android.content.Intent
import android.os.Environment
import android.util.Log

import okhttp3.ResponseBody
import java.io.*


private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
    try {

       val fileName = "File name" + "KITE" + "_from"  + ".pdf"
        //File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        val futureStudioIconFile = File(
            Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/" + fileName
        )
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            val fileReader = ByteArray(4096)
            val fileSize = body.contentLength()
            var fileSizeDownloaded: Long = 0
            inputStream = body.byteStream()
            outputStream = FileOutputStream(futureStudioIconFile)
            while (true) {
                val read: Int = inputStream.read(fileReader)
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read.toLong()
                Log.d("TAG", "file download: $fileSizeDownloaded of $fileSize")
            }
            outputStream.flush()
            return true
        } catch (e: IOException) {
            return false
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
        return false
    }
}


/*
fun extra(){
    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
    if (writtenToDisk) {
        time.setToNow();
        fileName = "Statement_" + request.getAccountNumber() + "_from" + time.format("%k : %M : %S") + ".pdf";
        //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        java.io.File file = new java.io.File(Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + fileName);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(rootCoordinatorLayout, "No PDF reader found to open this file.", Snackbar.LENGTH_LONG).show();
        }
}*/
