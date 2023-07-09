package com.example.myapplication

import com.google.gson.annotations.SerializedName

//data class per il token di idealista

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String,
    val token_type: String,
    val expires_in: Long,
    val scope: String,
    val jti: String
)