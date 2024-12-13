package com.example.padicare

import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<UploadResponse>

    @GET("images/{imageId}")
    fun getImageMetadata(
        @Path("imageId") imageId: String
    ): Call<ImageMetadataResponse>

    @POST("predict")
    fun predictImage(
        @Body request: PredictRequest
    ): Call<PredictResponse>

    @GET("predictions/{predictionId}")
    fun searchPrediction(
        @Path("predictionId") predictionId: String
    ): Call<PredictionSearchResponse>

    @GET("analysis/{diseaseName}")
    fun getDiseaseDatabase(
        @Path("diseaseName") diseaseName: String
    ): Call<DiseaseDatabaseResponse>
}