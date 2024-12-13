package com.example.padicare

data class UploadResponse(
    val imageId: String,
    val message: String
)

data class ImageMetadataResponse(
    val imageId: String,
    val metadata: Map<String, Any>
)

data class PredictRequest(
    val imageId: String
)

data class PredictResponse(
    val prediction: String,
    val confidence: Float
)

data class PredictionSearchResponse(
    val imageId: String,
    val label: String,
    val timestamp: String
)

data class DiseaseDatabaseResponse(
    val diseaseName: String,
    val description: String,
    val symptoms: List<String>,
    val treatments: List<String>
)