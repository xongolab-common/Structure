package com.example.structure.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.structure.R
import com.example.structure.core.CoreFragment
import com.example.structure.databinding.FragmentAccountBinding
import com.example.structure.util.Util
import com.example.structure.viewModel.UserViewModel
import com.example.structure.viewModel.ViewModelFactory


class AccountFragment : CoreFragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFullScreenMode(requireActivity().window)
        initView()
    }


    private fun initView() {
        initViewModel()
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, ViewModelFactory(coreActivity!!.mainRepository))[UserViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        // Logout API
        userViewModel.deleteAccountApiResponse.observe(coreActivity!!) { response ->
            response?.let {

            }
        }
    }

    private fun logoutApi() {
        if (Util.isOnline(coreActivity!!)) {
            userViewModel.deleteAccountApi(coreActivity!!)
        } else {
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvTitle ->{

            }
        }
    }
}