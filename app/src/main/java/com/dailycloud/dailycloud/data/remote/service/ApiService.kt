package com.dailycloud.dailycloud.data.remote.service

import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.remote.response.AddUserResponse
import com.dailycloud.dailycloud.data.remote.response.UpdateUserResponse
import com.dailycloud.dailycloud.data.remote.response.UserDetailResponse
import com.google.firebase.Timestamp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @GET("api/journals")
    suspend fun getJournals(): List<Journal>

    @GET("api/users/details")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ): UserDetailResponse

    @FormUrlEncoded
    @POST("api/users/signup")
    suspend fun addUser(
        @Field("uid") uid: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("birthday") birthday: Timestamp,
        @Header("Authorization") token: String
    ): AddUserResponse

    @FormUrlEncoded
    @PUT("api/users/update")
    suspend fun updateUser(
        @Field("uid") uid: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("birthday") birthday: Timestamp,
        @Header("Authorization") token: String
    ): UpdateUserResponse

}