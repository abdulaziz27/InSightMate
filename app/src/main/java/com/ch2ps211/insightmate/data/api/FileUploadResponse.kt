package com.ch2ps211.insightmate.data.api

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(
    @field:SerializedName("feature_nominal")
    val featureNominal: FeatureNominal
)

data class FeatureNominal(
    @field:SerializedName("fitur")
    val fitur: String,
    @field:SerializedName("gambar")
    val gambar: String,
    @field:SerializedName("prediksi_akurasi")
    val prediksiAkurasi: String,
    @field:SerializedName("prediksi_kelas")
    val prediksiKelas: Int,
    @field:SerializedName("prediksi_nominal")
    val prediksiNominal: String
)

data class ColorUploadResponse(
    @field:SerializedName("fitur")
    val fitur: String,
    @field:SerializedName("gambar")
    val gambar: String,
    @field:SerializedName("prediksi_kelas_akurasi")
    val prediksiKelasAkurasi: Map<String, String>,
    @field:SerializedName("prediksi_kelas")
    val prediksiKelas: String,
    @field:SerializedName("prediksi_warna")
    val prediksiWarna: String
)

data class DocumentUploadResponse(
    @field:SerializedName("fitur")
    val fitur: String,
    @field:SerializedName("gambar")
    val gambar: String,
    @field:SerializedName("hasil_teks")
    val hasilTeks: String
)

