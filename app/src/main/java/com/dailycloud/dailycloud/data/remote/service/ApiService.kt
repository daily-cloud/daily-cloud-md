package com.dailycloud.dailycloud.data.remote.service

import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.remote.response.AddUserResponse
import com.dailycloud.dailycloud.data.remote.response.ArticlesResponse
import com.dailycloud.dailycloud.data.remote.response.JournalsResponse
import com.dailycloud.dailycloud.data.remote.response.QuotesResponse
import com.dailycloud.dailycloud.data.remote.response.UpdateUserResponse
import com.dailycloud.dailycloud.data.remote.response.UploadImageResponse
import com.dailycloud.dailycloud.data.remote.response.UserDetailResponse
import com.google.firebase.Timestamp
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @GET("api/journals")
    suspend fun getJournals(
        @Header("Authorization") token: String
    ): JournalsResponse

    @GET("api/journals/{id}")
    suspend fun getJournal(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Journal

    @FormUrlEncoded
    @POST("api/journals")
    suspend fun addJournal(
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("mood") mood: String,
        @Field("prediction") prediction: String,
        @Header("Authorization") token: String
    ): AddUserResponse

    @GET("api/users/details")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ): UserDetailResponse

    @FormUrlEncoded
    @POST("api/users/signup")
    suspend fun addUser(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("birthday") birthday: String,
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

    @Multipart
    @POST("api/users/uploadImage")
    suspend fun uploadImage(
        @Part("photo") photo: MultipartBody.Part,
        @Header("Authorization") token: String
    ): UploadImageResponse

    @GET("api/quotes")
    suspend fun getQuote(
        @Header("Authorization") token: String
    ): QuotesResponse

    @GET("api/articles")
    suspend fun getContents(
        @Header("Authorization") token: String
    ): ArticlesResponse

    @GET("api/articles/{id}")
    suspend fun getContent(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Content
}