package com.example.structure.idrequest

import com.example.structure.model.ChangeContactRequest
import com.example.structure.model.SignInRequest
import com.example.structure.model.SignUpRequest
import okhttp3.MultipartBody

class ApiRepository(private val idRequestService: IDRequestService) {

    suspend fun getCountryListApi(code: String) = idRequestService.getCountryListApi(code)
    suspend fun getSignInApi(signInRequest: SignInRequest) = idRequestService.getSignInApi(signInRequest)
    suspend fun getSignUpApi(signUpRequest: SignUpRequest) = idRequestService.getSignUpApi(signUpRequest)
    suspend fun deleteAccountApi() = idRequestService.deleteAccountApi()
    suspend fun changeContactRequestApi(changeContactRequest: ChangeContactRequest) = idRequestService.changeContactRequestApi(changeContactRequest)
    suspend fun getVehicleManagementApi(typeRequest: MultipartBody.Part, makerIDRequest: MultipartBody.Part, ) = idRequestService.getVehicleManagementApi(typeRequest, makerIDRequest,)

}