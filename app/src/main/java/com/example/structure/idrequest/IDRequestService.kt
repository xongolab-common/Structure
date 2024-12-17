package com.example.structure.idrequest

import com.anrim.voltpoint.model.UserModel
import com.example.structure.model.ChangeContactRequest
import com.example.structure.model.LanguageModel
import com.example.structure.model.SignInRequest
import com.example.structure.model.SignUpRequest
import com.example.structure.model.SocialLoginRequestModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface IDRequestService {

    @GET(IDRequestBuilder.LANGUAGE_LIST_API)
    suspend fun getCountryListApi(@Query("code") code: String): Response<ResponseArrayModel<UserModel>>

    @POST(IDRequestBuilder.LANGUAGE_LIST_API)
    suspend fun getSignInApi(@Body body: SignInRequest): Response<ResponseModel<UserModel>>

    @PUT(IDRequestBuilder.LANGUAGE_LIST_API)
    suspend fun getSignUpApi(@Body body: SignUpRequest): Response<ResponseModel<UserModel>>

    @PATCH(IDRequestBuilder.LANGUAGE_LIST_API)
    suspend fun changeContactRequestApi(
        @Body requestBody: ChangeContactRequest
    ): Response<ResponseModel<UserModel>>

    @Multipart
    @POST(IDRequestBuilder.LANGUAGE_LIST_API)
    suspend fun getVehicleManagementApi(
        @Part typeRequest : MultipartBody.Part,
        @Part makerIDRequest : MultipartBody.Part,
    ): Response<ResponseArrayModel<UserModel>>

    @DELETE(IDRequestBuilder.LANGUAGE_LIST_API)
    suspend fun deleteAccountApi(): Response<ResponseModel<UserModel>>

}
