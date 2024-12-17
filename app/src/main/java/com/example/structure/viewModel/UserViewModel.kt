package com.example.structure.viewModel

import android.app.Dialog
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anrim.voltpoint.model.UserModel
import com.example.structure.util.Util
import com.example.structure.R
import com.example.structure.databinding.DialogValidationMessageBinding
import com.example.structure.idrequest.ApiRepository
import com.example.structure.idrequest.ResponseArrayModel
import com.example.structure.idrequest.ResponseModel
import com.example.structure.model.ChangeContactRequest
import com.example.structure.model.ErrorResponse
import com.example.structure.model.SignInRequest
import com.example.structure.model.SignUpRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class UserViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }


    val getCountryListApiResponse = MutableLiveData<ResponseArrayModel<UserModel>>()
    val getSignInApiResponse = MutableLiveData<ResponseModel<UserModel>>()
    val getSignUpApiResponse = MutableLiveData<ResponseModel<UserModel>>()
    val changeContactRequestApiResponse = MutableLiveData<ResponseModel<UserModel>>()
    val deleteAccountApiResponse = MutableLiveData<ResponseModel<UserModel>>()
    val getVehicleManagementApiResponse = MutableLiveData<ResponseArrayModel<UserModel>>()


    fun changeContactRequestApi(
        context: Context,
        changeContactRequest: ChangeContactRequest
    ) {
        Util.showProgress(context)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = apiRepository.changeContactRequestApi(
                    changeContactRequest
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        changeContactRequestApiResponse.postValue(response.body())
                        Util.dismissProgress()
                    } else {
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        val errorResponse: ErrorResponse =
                            Gson().fromJson(response.errorBody()!!.charStream(), type)
                        onError("Error : ${errorResponse.error.message}")
                        msgDialog(context, errorResponse.error.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Util.dismissProgress()
                    onError("Error: ${e.message}")
                    msgDialog(context, "Error: ${e.message}")
                }
            }
        }
    }


    fun getCountryListApi(context: Context, code: String, showDialog: Boolean) {
        if (showDialog) Util.showProgress(context)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiRepository.getCountryListApi(code)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getCountryListApiResponse.value = response.body()
                    Util.dismissProgress()
                } else {
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
                    onError("Error : ${errorResponse.error.message} ")
                    msgDialog(context, errorResponse.error.message)
                }
            }
        }
    }

    fun getSignInApi(
        context: Context,
        signInRequest: SignInRequest
    ) {
        Util.showProgress(context)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = apiRepository.getSignInApi(
                    signInRequest
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getSignInApiResponse.postValue(response.body())
                        Util.dismissProgress()
                    } else {
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        val errorResponse: ErrorResponse =
                            Gson().fromJson(response.errorBody()!!.charStream(), type)
                        onError("Error : ${errorResponse.error.message} ")
                        msgDialog(context, errorResponse.error.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Util.dismissProgress()
                    onError("Error: ${e.message}")
                    msgDialog(context, "Error: ${e.message}")
                }
            }
        }
    }

    fun getSignUpApi(context: Context, signUpRequest: SignUpRequest) {
        Util.showProgress(context)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = apiRepository.getSignUpApi(signUpRequest)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getSignUpApiResponse.postValue(response.body())
                        Util.dismissProgress()
                    } else {
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        val errorResponse: ErrorResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
                        onError("Error : ${errorResponse.error.message} ")
                        msgDialog(context, errorResponse.error.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Util.dismissProgress()
                    onError("Error: ${e.message}")
                    msgDialog(context, "Error: ${e.message}")
                }
            }
        }
    }

    fun deleteAccountApi(context: Context) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = apiRepository.deleteAccountApi()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        deleteAccountApiResponse.postValue(response.body())
                        Util.dismissProgress()
                    } else {
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        val errorResponse: ErrorResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
                        onError("Error : ${errorResponse.error.message} ")
                        msgDialog(context, errorResponse.error.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Util.dismissProgress()
                    onError("Error: ${e.message}")
                    msgDialog(context, "Error: ${e.message}")
                }
            }
        }
    }

    fun getVehicleManagementApi(
        context: Context,
        typeRequest: MultipartBody.Part,
        makerIDRequest: MultipartBody.Part,
    ) {
        Util.showProgress(context)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = apiRepository.getVehicleManagementApi(typeRequest, makerIDRequest)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getVehicleManagementApiResponse.postValue(response.body())
                        Util.dismissProgress()
                    } else {
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        val errorResponse: ErrorResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
                        onError("Error : ${errorResponse.error.message} ")
                        msgDialog(context, errorResponse.error.message)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Error: ${e.message}")
                    msgDialog(context, "Error: ${e.message}")
                }
            }
        }
    }


    private fun onError(message: String) {
        Log.d("TAG", "onError: MESSAGE :: >> $message")
        Util.dismissProgress()
    }

    var timer: CountDownTimer? = null

    fun msgDialog(context: Context, msg: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val dialogValidationMessageBinding = DialogValidationMessageBinding.inflate(
                    LayoutInflater.from(context))
                val messageDialog = Dialog(context)

                messageDialog.setContentView(dialogValidationMessageBinding.root)

                messageDialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                messageDialog.window!!.setGravity(Gravity.TOP)
                messageDialog.window!!.setWindowAnimations(R.style.DialogMessageAnimation)
                messageDialog.setCancelable(true)

                dialogValidationMessageBinding.tvMessage.text = msg

                dialogValidationMessageBinding.mainLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                dialogValidationMessageBinding.tvMessage.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))

                messageDialog.show()

                timer = object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        //No operation required
                    }

                    override fun onFinish() {
                        timer?.cancel()

                        try {
                            if (messageDialog.isShowing) messageDialog.dismiss()
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                    }
                }.start()

            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        timer?.cancel()
    }

}