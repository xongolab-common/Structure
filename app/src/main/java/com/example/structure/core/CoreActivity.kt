package com.example.structure.core

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.OpenableColumns
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.structure.util.Pref
import com.example.structure.R
import com.example.structure.databinding.DialogValidationMessageBinding
import com.example.structure.idrequest.ApiRepository
import com.example.structure.idrequest.IDRequestBuilder
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.floor


abstract class CoreActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG: String = "CoreActivity"
    var MAIN_LOCATION_PERMISSION_CODE = 48
    var READ_CONTACTS_PERMISSION_CODE = 42

    private val retrofitService = IDRequestBuilder.getInstance(this)
    val mainRepository = ApiRepository(retrofitService)

    val cal = Calendar.getInstance()
    val tz = cal.timeZone

    private var timer: CountDownTimer? = null
    private lateinit var messageDialog: Dialog
    private lateinit var dialogValidationMessageBinding: DialogValidationMessageBinding

    override fun attachBaseContext(newBase: Context) {
        val languageCode = Pref.getStringValue(Pref.PREF_LANGUAGE, "EN").lowercase()
        super.attachBaseContext(com.example.structure.locale.LocaleManager.setLocale(newBase, languageCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: ")

    }

    fun makeLinks(textView: TextView, vararg links: Pair<String, View.OnClickListener>) {
        if (textView.text.isNotEmpty()) {

            val spannableString = SpannableString(textView.text)
            for (link in links) {
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        link.second.onClick(view)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.isUnderlineText = true
                        ds.color = ContextCompat.getColor(this@CoreActivity, R.color.colorPrimary)
                    }
                }
                val startIndexOfLink = textView.text.toString().indexOf(link.first)
                if (startIndexOfLink != -1) {
                    spannableString.setSpan(
                        clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

            }
            textView.movementMethod =
                LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
            textView.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }

    public fun askNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        }
    }

    // Function to set full screen mode with transparent status bar
    fun setFullScreenMode(window: Window) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
    }

    fun setFullLightScreenMode(window: Window) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.statusBarColor = Color.TRANSPARENT
    }

    fun getAppVersionName(): String? {
        val pm = packageManager
        val pkgName = packageName
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return pkgInfo!!.versionName
    }

    // Convert the Uri to a File
    fun getFileFromUri(uri: Uri): String {
        var fileName = ""
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) {
                    fileName = it.getString(nameIndex)
                }
            }
        }


        val tempFile = File(this.filesDir, fileName)
        tempFile.deleteOnExit()

        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(tempFile)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        Log.e(TAG, "getFileFromUri: $tempFile")

        return tempFile.absolutePath
    }

    fun areLocationGranted(): Boolean {
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        )

        return coarseLocationPermission == PackageManager.PERMISSION_GRANTED &&
                fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    fun arePermissionsGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val readExternalStoragePermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_IMAGES
            )
            val cameraPermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            )
            return readExternalStoragePermission == PackageManager.PERMISSION_GRANTED && cameraPermission == PackageManager.PERMISSION_GRANTED
        }else{
            val readExternalStoragePermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val cameraPermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            )
            return readExternalStoragePermission == PackageManager.PERMISSION_GRANTED && writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED && cameraPermission == PackageManager.PERMISSION_GRANTED
        }
    }


    public fun checkContactsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_CONTACTS,
            ),
            READ_CONTACTS_PERMISSION_CODE
        )
    }


    fun msgDialog(msg: String) {
        try {
            dialogValidationMessageBinding = DialogValidationMessageBinding.inflate(layoutInflater)

            messageDialog = Dialog(this)

            messageDialog.setContentView(dialogValidationMessageBinding.root)

            messageDialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            messageDialog.window!!.setGravity(Gravity.TOP)
            messageDialog.window!!.setWindowAnimations(R.style.DialogMessageAnimation)
            messageDialog.setCancelable(true)

            dialogValidationMessageBinding.tvMessage.text = msg

            messageDialog.show()

            timer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    //No operation required
                }

                override fun onFinish() {
                    if (timer != null) {
                        timer!!.cancel()
                    }
                    try {
                        if (messageDialog.isShowing)
                            messageDialog.dismiss()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }
            }.start()

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    fun setPriceWithUnit(price: Double): String {
        val priceStr: String = if (checkIfInt(price)) {
            String.format(Locale.US, "%.2f", price)
        } else {
            String.format(Locale.US, "%.2f", price)
        }
        return priceStr
    }

    open fun checkIfInt(value: Double): Boolean {
        return value - floor(value) == 0.0
    }

    fun getHourAndMinute(startDate: Date, endDate: Date): Pair<Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val startMillis = calendar.timeInMillis

        calendar.time = endDate
        val endMillis = calendar.timeInMillis

        val difference = Math.abs(endMillis - startMillis)

        val hours = (difference / (1000 * 60 * 60)).toInt()
        val minutes = ((difference / (1000 * 60)) % 60).toInt()

        return Pair(hours, minutes)
    }
}