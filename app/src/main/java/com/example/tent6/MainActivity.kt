package com.example.tent6

import android.R.attr
import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import android.R.attr.button
import android.content.Intent
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.main_test_schermo)
        setContentView(R.layout.activity_main)

        var initialContent: String = ""
        for (i in 1..Constants.amountEntries){
            initialContent += Constants.defaultState + "\n"
        }

        val measure_file = MyFile(Constants.states_file, this, initialContent = initialContent)

        val inflaterStateCard: LayoutInflater = LayoutInflater.from(this)
        val stateCardScrollViewHolder: LinearLayout = findViewById(R.id.states_scrollview_container)

        measure_file.getAllLines().reversed().forEach { stateDescriptor ->
            val stateCardLayout = inflaterStateCard.inflate(R.layout.state_card, null, false)
            val cardTextView: TextView = stateCardLayout.findViewById(R.id.stateInformationText)
            cardTextView.text = stateDescriptor

            try {
                stateCardScrollViewHolder.addView(stateCardLayout)
            } catch (e: Exception) {}
        }

        // val textView:TextView = findViewById(R.id.waldsensor_nachricht)
        var execute = true;

        Thread {
            while (execute) {
                try {
                    val mess = Methods.sendGet(Constants.endpoint) //post()
                    val postResponse = Methods.sendPost(Constants.endpoint, Methods.createJSONMessage("noMessage", "no content"))
                    //println("Post response: $postResponse")
                    val jsonMessage = JSONObject(mess)
                    runOnUiThread {
                        //Update UI
                        try {
                            val decodedText = jsonMessage.getString(Constants.messageKey)
                            //textView.text = decodedText
                            val mutableList = measure_file.getAllLinesMutable()
                            mutableList.add(decodedText)
                            mutableList.removeAt(0)
                            measure_file.clearFile()
                            mutableList.forEach { element ->
                                measure_file.appendLine(element)
                            }

                            stateCardScrollViewHolder.removeAllViews()
                            mutableList.reversed().forEach { element ->
                                val stateCardLayout = inflaterStateCard.inflate(R.layout.state_card, null, false)
                                val cardTextView: TextView = stateCardLayout.findViewById(R.id.stateInformationText)
                                cardTextView.text = element

                                try {
                                    stateCardScrollViewHolder.addView(stateCardLayout)
                                } catch (e: Exception) {}
                            }

                        }catch (e: Exception){
                            println("Exception during state update:")
                            println(e.message)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("background error", e.toString())
                }
                Thread.sleep(7000) // millisecs
            }

        }.start()

    }



}