package com.khs.retrofitandokhttpexampleproject.model

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName

data class SolveAcGetUserDataModel (
        @SerializedName("handle")
        val handle: String,

        @SerializedName("bio")
        val bio: String,

        @SerializedName("organizations")
        val organizations: List<OrganizationsModel>,

        @SerializedName("background")
        val background: BackgroundData,

        @SerializedName("profileImageUrl")
        val profileImageUrl: String,

        @SerializedName("solvedCount")
        val solvedCount: Int,

        @SerializedName("tier")
        val tier: Int
) {
        var code: Int = 0
        var tierText: String = ""

        data class OrganizationsModel(
                @SerializedName("name")
                val name: String
        )

        data class BackgroundData(
                @SerializedName("backgroundImageUrl")
                val backgroundImageUrl: String,

                @SerializedName("displayName")
                val displayName: String,

                @SerializedName("displayDescription")
                val displayDescription: String
        )
}