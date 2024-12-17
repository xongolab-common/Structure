package com.anrim.voltpoint.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UserModel(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("_id")
    var id: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("startDate")
    var startDate: String = "",
    @SerializedName("endDate")
    var endDate: String = "",
    @SerializedName("amount")
    var amount: Int = 0,
    @SerializedName("walletAmount")
    var walletAmount: Float = 0F,
    @SerializedName("callingCode")
    var callingCode: String = "",
    @SerializedName("region")
    var region: String = "",
    @SerializedName("countryCode")
    var countryCode: String? = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("appStoreURL")
    val appStoreURL: String = "",
    @SerializedName("defaultCurrency")
    val defaultCurrency: String = "",
    @SerializedName("facebookURL")
    val facebookURL: String = "",
    @SerializedName("instagramURL")
    val instagramURL: String = "",
    @SerializedName("otpAutoFill")
    val otpAutoFill: Boolean = false,
    @SerializedName("isRegistered")
    val isRegistered: Boolean = false,
    @SerializedName("otpResendMin")
    val otpResendMin: Int = 0,
    @SerializedName("playStoreURL")
    val playStoreURL: String = "",
    @SerializedName("privacyURL")
    val privacyURL: String = "",
    @SerializedName("radius")
    val radius: Int = 0,
    @SerializedName("supportEmail")
    val supportEmail: String = "",
    @SerializedName("supportMobile")
    val supportMobile: String = "",
    @SerializedName("termURL")
    val termURL: String = "",
    @SerializedName("timeSlotMin")
    val timeSlotMin: Int = 0,
    @SerializedName("twitterURL")
    val twitterURL: String = "",
    @SerializedName("webSocketUrl")
    val webSocketUrl: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("idTag")
    val idTag: String = "",
    @SerializedName("isEmailVerify")
    val isEmailVerify: Boolean = false,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("noticationEnabled")
    val notificationEnabled: Boolean = false,
    @SerializedName("password")
    val password: String = "",
    @SerializedName("surname")
    val surname: String = "",
    @SerializedName("token")
    val token: String = "",
    @SerializedName("minBalance")
    val minBalance: Long = 0,
    @SerializedName("billingAddress")
    val billingAddress: BillingAddress = BillingAddress(),
    @SerializedName("notificationSetting")
    val notificationSetting: NotificationSetting = NotificationSetting(),

    @SerializedName("file")
    var file: String = "",
    @SerializedName("units")
    var units: String = "",


    // Success Model
    @Expose
    @SerializedName("success")
    val success: Boolean = false,

    ) {

    @Expose
    @SerializedName("description")
    var description: String = ""

    @Expose
    @SerializedName("schedules")
    var schedules: String = ""

    @Expose
    @SerializedName("image")
    var image: Int = 0

    var isSelect: Boolean = false

    var isCheck: Boolean = false

    var isExpand: Boolean = false

    data class BillingAddress(
        @SerializedName("city")
        val city: String = "",
        @SerializedName("state")
        val state: String = "",
        @SerializedName("streetAddress")
        val streetAddress: String = "",
        @SerializedName("zipcode")
        val zipcode: String = ""
    )

    data class NotificationSetting(
        @SerializedName("accountChange")
        val accountChange: Boolean = false,
        @SerializedName("chargingStatus")
        val chargingStatus: Boolean = false,
        @SerializedName("notReadyForDeparture")
        val notReadyForDeparture: Boolean = false,
        @SerializedName("paymentAndFees")
        val paymentAndFees: Boolean = false,
        @SerializedName("receiveDREvent")
        val receiveDREvent: Boolean = false,
        @SerializedName("reservations")
        val reservations: Boolean = false,
        @SerializedName("scheduleChargingConflict")
        val scheduleChargingConflict: Boolean = false,
        @SerializedName("smartChargingEvents")
        val smartChargingEvents: Boolean = false
    )

}
