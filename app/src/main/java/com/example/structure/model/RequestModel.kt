package com.example.structure.model

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class SignInRequest {

    @Expose
    @SerializedName("email")
    var email: String = ""

    @Expose
    @SerializedName("password")
    var password: String = ""

    @Expose
    @SerializedName("deviceDetails")
    var deviceDetails: DeviceDetails = DeviceDetails()
}

class SocialLoginRequestModel {

    @Expose
    @SerializedName("email")
    var email: String = ""

    @Expose
    @SerializedName("socialID")
    var socialID: String = ""

    @Expose
    @SerializedName("deviceDetails")
    var deviceDetails: DeviceDetails = DeviceDetails()
}

class SignUpRequest {

    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("surname")
    var surname: String = ""

    @Expose
    @SerializedName("region")
    var region: String = ""

    @Expose
    @SerializedName("countryID")
    var countryID: String = ""

    @Expose
    @SerializedName("email")
    var email: String = ""

    @Expose
    @SerializedName("password")
    var password: String = ""

    @Expose
    @SerializedName("deviceDetails")
    var deviceDetails: DeviceDetails = DeviceDetails()
}


class ChangeContactRequest {

    @Expose
    @SerializedName("type")
    var type: String? = null

    @Expose
    @SerializedName("email")
    var email: String? = null

    @Expose
    @SerializedName("mobile")
    var mobile: String? = null

    @Expose
    @SerializedName("countryCode")
    var countryCode: String? = null

    @Expose
    @SerializedName("verificationCode")
    var verificationCode: Int? = null
}

class UpdateProfileRequest {

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("surname")
    var surname: String? = null

    @Expose
    @SerializedName("userType")
    var userType: String? = null

    @Expose
    @SerializedName("countryID")
    var countryID: String? = null

    @Expose
    @SerializedName("languageCode")
    var languageCode: String? = null

    @Expose
    @SerializedName("avatar")
    var avatar: String? = null

    @Expose
    @SerializedName("noticationEnabled")
    var notificationEnabled: Boolean? = null

    @Expose
    @SerializedName("streetAddress")
    var streetAddress: String? = null

    @Expose
    @SerializedName("city")
    var city: String? = null

    @Expose
    @SerializedName("state")
    var state: String? = null

    @Expose
    @SerializedName("zipcode")
    var zipcode: String? = null

    @Expose
    @SerializedName("promotionalVoltpoint")
    var promotionalVoltPoint: Boolean? = null

    @Expose
    @SerializedName("promotionalOtherCompany")
    var promotionalOtherCompany: Boolean? = null

    @Expose
    @SerializedName("profiling")
    var profiling: Boolean? = null
}

class CreditAmountRequest {

    @Expose
    @SerializedName("amount")
    var amount: Float? = null

    @Expose
    @SerializedName("transactionResponse")
    var transactionResponse: String = ""

    @Expose
    @SerializedName("transactionId")
    var transactionId: String = ""


}

class DeviceDetails {

    @Expose
    @SerializedName("deviceName")
    var deviceName: String = ""

    @Expose
    @SerializedName("deviceType")
    var deviceType: String = ""

    @Expose
    @SerializedName("deviceToken")
    var deviceToken: String = ""

    @Expose
    @SerializedName("deviceID")
    var deviceID: String = ""

    @Expose
    @SerializedName("appVersion")
    var appVersion: String = ""
}

class UpdateNotificationSettingRequest {

    @Expose
    @SerializedName("accountChange")
    var accountChange: Boolean? = null
    @Expose
    @SerializedName("paymentAndFees")
    var paymentAndFees: Boolean? = null
    @Expose
    @SerializedName("reservations")
    var reservations: Boolean? = null
    @Expose
    @SerializedName("chargingStatus")
    var chargingStatus: Boolean? = null
    @Expose
    @SerializedName("smartChargingEvents")
    var smartChargingEvents: Boolean? = null
    @Expose
    @SerializedName("scheduleChargingConflict")
    var scheduleChargingConflict: Boolean? = null
    @Expose
    @SerializedName("notReadyForDeparture")
    var notReadyForDeparture: Boolean? = null
    @Expose
    @SerializedName("receiveDREvent")
    var receiveDREvent: Boolean? = null
    @Expose
    @SerializedName("chargingStationRadar")
    var chargingStationRadar: Boolean? = null
    @Expose
    @SerializedName("radarAddress")
    var radarAddress: String? = null
    @Expose
    @SerializedName("radarLatitude")
    var radarLatitude: Double? = null
    @Expose
    @SerializedName("radarLongitude")
    var radarLongitude: Int? = null
    @Expose
    @SerializedName("radarRadius")
    var radarRadius: Int? = null
}


class AddVehicleRequest {

    @Expose
    @SerializedName("type")
    var type: String? = null

    @Expose
    @SerializedName("vehicleID")
    var vehicleID: String? = null

    @Expose
    @SerializedName("makerID")
    var makerID: String? = null

    @Expose
    @SerializedName("countryID")
    var countryID: String? = null

    @Expose
    @SerializedName("model")
    var model: String? = null

    @Expose
    @SerializedName("version")
    var version: String? = null

    @Expose
    @SerializedName("connectorID")
    var connectorID: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("vehiclePlatNumber")
    var vehiclePlatNumber: String? = null

    @Expose
    @SerializedName("chassisNumber")
    var chassisNumber: String? = null

    @Expose
    @SerializedName("maxChargingRateAC")
    var maxChargingRateAC: Int? = 0

    @Expose
    @SerializedName("maxChargingRateDC")
    var maxChargingRateDC: Int? = 0

    @Expose
    @SerializedName("batteryCapacity")
    var batteryCapacity: Int? = 0


}





data class ResetPasswordRequest(
    val email: String
)

data class UpdatePasswordRequest(
    val email: String,
    val password: String,
    val verificationCode: String,
)