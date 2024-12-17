package com.example.structure.view.activity

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.structure.R
import com.example.structure.core.CoreActivity
import com.example.structure.databinding.ActivityMainBinding
import com.example.structure.view.fragment.AccountFragment
import com.example.structure.view.fragment.HistoryFragment
import com.example.structure.view.fragment.MapFragment
import com.example.structure.viewModel.UserViewModel
import com.example.structure.viewModel.ViewModelFactory


class MainActivity : CoreActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    private var isFrom: Int = 0
    private var selectedPosition: Int = 0

    companion object {
        private const val NOTIFICATION_PERMISSION_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFullLightScreenMode(window)
        initView()

        // Handle back press for Android 14 (API 34) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showExitConfirmationDialog()
                }
            })
        } else {
            // For older versions
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showExitConfirmationDialog()
                }
            })
        }
    }


    private fun initView() {
        initViewModel()

        binding.footer.changeBackground(isFrom)
        changeFragment(isFrom)

        binding.footer.binding.llMap.setOnClickListener(this)
        binding.footer.binding.llHistory.setOnClickListener(this)
        binding.footer.binding.llAccount.setOnClickListener(this)

    }


    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, ViewModelFactory(mainRepository))[UserViewModel::class.java]

    }


    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("LBL_ARE_YOU_SURE_YOU_WANT_TO_EXIT_THE_APP")
            .setCancelable(false)
            .setPositiveButton("LBL_YES") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setNegativeButton("LBL_NO") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.llMap -> {
                if (selectedPosition != 0) {
                    changeFragment(0)
                }
            }

            R.id.llHistory -> {
                if (selectedPosition != 1) {
                    changeFragment(1)
                }
            }

            R.id.llAccount -> {
                if (selectedPosition != 2) {
                    changeFragment(2)
                }
            }
        }
    }


    private fun changeFragment(pos: Int) {
        selectedPosition = pos
        binding.footer.changeBackground(selectedPosition)

        val currentFragment =
            when (pos) {
                0 -> MapFragment()
                1 -> HistoryFragment()
                else -> AccountFragment()
            }

        val ft = supportFragmentManager.beginTransaction()
        if (pos == 0) {
            val args = Bundle()
            currentFragment.arguments = args
        }

        ft.replace(R.id.frameContainer, currentFragment)
        ft.commit()
    }

    override fun onResume() {
        super.onResume()

        if (!areNotificationsEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 or higher
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }

    private fun areNotificationsEnabled(): Boolean {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }
}
