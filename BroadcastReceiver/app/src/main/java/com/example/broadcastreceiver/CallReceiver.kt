@file:Suppress("DEPRECATION")

package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

                if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                    val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    incomingNumber?.let {
                        Log.d("CallReceiver", "Cuộc gọi nhỡ từ: $it")
                        sendSMS(it)
                    }
                }
            }
        }

        private fun sendSMS(phoneNumber: String) {
            try {
                val smsManager = SmsManager.getDefault()
                val message = "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại sau."
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                Log.d("CallReceiver", "Đã gửi tin nhắn đến: $phoneNumber")
            } catch (e: Exception) {
                Log.e("CallReceiver", "Lỗi gửi tin nhắn: ${e.message}")
            }
        }
    }