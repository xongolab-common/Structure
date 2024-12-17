package com.example.structure.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.CountDownTimer
import android.provider.Settings
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.structure.custom.CustomDialog
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import com.example.structure.R
import com.example.structure.databinding.DialogValidationMessageBinding
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter



object Util {

    private var customDialog: CustomDialog? = null
    private var messageDialog: Dialog? = null

    fun showProgress(context: Context) {
        try {
            if (customDialog != null && customDialog!!.isShowing)
                customDialog!!.dismiss()

            customDialog = CustomDialog(context)
            customDialog!!.setCancelable(false)
            customDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun dismissProgress() {
        if (customDialog != null && customDialog!!.isShowing)
            customDialog!!.dismiss()
        customDialog = null
    }

    fun createPartFromString(value: String, key: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value)
    }

    fun millisToDate(millis: Long, myFormat: String): String {
        return SimpleDateFormat(myFormat, Locale.US).format(Date(millis))
    }


    fun isOnline(context: Context): Boolean {
        return try {
            val conMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = conMgr.activeNetworkInfo
            info != null && info.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isEmptyText(view: View?): Boolean {
        return if (view == null)
            true
        else
            getTextValue(view).isEmpty()
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    fun isStrongPassword(view: View): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()-_=+{};:,<.>]).{8,}\$".toRegex()
        return regex.matches(getTextValue(view))
    }

    fun getTextValue(view: View): String {
        return (view as? EditText)?.text?.toString()?.trim { it <= ' ' }
            ?: ((view as? TextView)?.text?.toString()?.trim { it <= ' ' }
                ?: "")
    }


    var timer: CountDownTimer? = null
    fun msgDialog(context: Context, msg: String) {
        try {
            val dialogValidationMessageBinding = DialogValidationMessageBinding.inflate(
                LayoutInflater.from(context))
            messageDialog = Dialog(context, R.style.DialogSheetNoTranBack)

            messageDialog!!.setContentView(dialogValidationMessageBinding.root)

            messageDialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            messageDialog!!.window!!.setGravity(Gravity.TOP)
            messageDialog!!.window!!.setWindowAnimations(R.style.DialogMessageAnimation)
            messageDialog!!.setCancelable(true)

            dialogValidationMessageBinding.tvMessage.text = msg

            if (messageDialog != null && messageDialog!!.isShowing){
                messageDialog!!.dismiss()
            }
            messageDialog!!.show()

            timer = object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    //No operation required
                }

                override fun onFinish() {
                    if (timer != null) {
                        timer!!.cancel()
                    }
                    try {
                        if (messageDialog!!.isShowing) messageDialog!!.dismiss()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }
            }.start()

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    fun dismissMsgDialog() {
        if (messageDialog != null && messageDialog!!.isShowing)
            messageDialog!!.dismiss()
        messageDialog = null
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.lowercase(Locale.getDefault())
                .startsWith(manufacturer.lowercase(Locale.getDefault()))
        ) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s.isNullOrEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }

    fun getAppVersionName(context: Context): String? {
        val pm = context.packageManager
        val pkgName = context.packageName
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return pkgInfo!!.versionName
    }


    @SuppressLint("SimpleDateFormat")
    fun formatTimestamp(timestamp: String, currentFormat: String, parseFormat: String): String {
        val inputFormat = SimpleDateFormat(currentFormat)
        val outputFormat = SimpleDateFormat(parseFormat)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(timestamp)
        return outputFormat.format(date!!)
    }


    // 10:00 AM-11:00 AM
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatStartEndTime(startTime: String, endTime: String): String {
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
        val formattedStartTime = ZonedDateTime.parse(startTime, DateTimeFormatter.ISO_ZONED_DATE_TIME).format(timeFormatter)
        val formattedEndTime = ZonedDateTime.parse(endTime, DateTimeFormatter.ISO_ZONED_DATE_TIME).format(timeFormatter)
        return "$formattedStartTime-$formattedEndTime"
    }


    // 1 Hour
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateDuration(startTime: String, endTime: String): String {
        val slotStartTime = ZonedDateTime.parse(startTime, DateTimeFormatter.ISO_ZONED_DATE_TIME)
        val slotEndTime = ZonedDateTime.parse(endTime, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        val duration = Duration.between(slotStartTime, slotEndTime)

        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60

        return when {
            hours > 0 && minutes > 0 -> "$hours Hour ${minutes} Min"
            hours > 0 -> "$hours Hour"
            minutes > 0 -> "$minutes Min"
            else -> "0 Min"
        }
    }


    // 26 Mar 2023
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String, pattern: String = "dd MMM yyyy", locale: Locale = Locale.ENGLISH): String {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)
        return zonedDateTime.format(formatter)
    }

    fun getAppVersion(context: Context): String {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName
            val packageInfo: PackageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION") // Suppress warning for older APIs
                packageManager.getPackageInfo(packageName, 0)
            }
            packageInfo.versionName ?: "Unknown"
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }
    }

}