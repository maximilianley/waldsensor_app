package com.example.tent6

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Methods {
    companion object{

        fun sendGet(endpoint:String):String {
            val h = URL(/*"https://google.com"*/Constants.primaryURL+endpoint)
            Log.d("HTTP GET", h.content.toString())
            val connection = h.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.doOutput = true
            connection.connect()
            //connection.inputStream.use { it.read().use{} }

            val br: BufferedReader = BufferedReader(
                InputStreamReader(/*connection.inputStream*/h.content as InputStream)
            )//, "utf-8"))
            val response:StringBuilder = StringBuilder()
            var responseLine:String? = null
            responseLine = br.readLine()
            while (responseLine != null){
                response.append(responseLine)
                responseLine = br.readLine()
            }

            val string = response.toString()
            Log.d("GET RESPONSE", string)
            return string
        }

        fun sendPost(endpoint: String, postData: String): String {
            val url = URL(Constants.primaryURL + endpoint)
            val connection = url.openConnection() as HttpURLConnection

            try {
                // Set up the connection for a POST request
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                connection.doOutput = true
                // connection.connect()

                // Write the POST data to the connection's output stream
                val os: OutputStream = connection.outputStream // <- connection.connect() è implicito quì
                os.write(postData.toByteArray(charset("UTF-8")))
                os.flush()
                os.close()

                // Get the response from the server
                val inputStream: InputStream = connection.inputStream
                val br = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

                val response = StringBuilder()
                var responseLine: String? = null
                while (br.readLine().also { responseLine = it } != null) {
                    response.append(responseLine!!.trim())
                }

                val string = response.toString()
                Log.d("POST RESPONSE", string)

                return string
            } catch (e: Exception) {
                Log.e("POST ERROR", "Error during POST request", e)
                return "POST ERROR" // Handle the error as needed
            } finally {
                connection.disconnect()
            }
        }

        fun createJSONMessage(key: String, value: String):String{
            return "{\"$key\": \"$value\"}"
        }

        fun getCurrentDateTimeFormatted(): String {
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            val currentDate = dateFormat.format(Date())
            val currentTime = timeFormat.format(Date())

            return "vom $currentDate um $currentTime Uhr"
        }

    }
}