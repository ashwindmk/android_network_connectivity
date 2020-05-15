package com.ashwin.android.networkconnectivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("network-status", "on-create")

        update_button.setOnClickListener {
            update()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d("network-status", "on-attached-to-window")
        update()
    }

    private fun update() {
        Log.d("network-status", "Updating status")
        when (ConnectivityHelper.status) {
            ConnectivityHelper.ONLINE_WIFI -> status_textview.text = "Status: WIFI"
            ConnectivityHelper.ONLINE_DATA -> status_textview.text = "Status: Data"
            else -> status_textview.text = "Status: Offline"
        }
    }
}
