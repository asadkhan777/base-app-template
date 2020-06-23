package com.asadkhan.global.domain


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ImageData(
        @SerializedName("ad_type")
        val adType: Int, // 0
        @SerializedName("ad_url")
        val adUrl: String,
        @SerializedName("animated")
        val animated: Boolean, // true
        @SerializedName("bandwidth")
        val bandwidth: Long, // 80133980940
        @SerializedName("comment_count")
        val commentCount: Int?, // null
        @SerializedName("datetime")
        val datetime: Int, // 1571495737
        @SerializedName("description")
        val description: String?, // null
        @SerializedName("edited")
        val edited: String, // 0
        @SerializedName("favorite")
        val favorite: Boolean, // false
        @SerializedName("favorite_count")
        val favoriteCount: Int?, // null
        @SerializedName("gifv")
        val gifv: String, // https://i.imgur.com/jAPFHTx.gifv
        @SerializedName("has_sound")
        val hasSound: Boolean, // false
        @SerializedName("height")
        val height: Int, // 864
        @SerializedName("hls")
        val hls: String, // https://i.imgur.com/jAPFHTx.m3u8
        @SerializedName("id")
        val id: String, // jAPFHTx
        @SerializedName("in_gallery")
        val inGallery: Boolean, // false
        @SerializedName("in_most_viral")
        val inMostViral: Boolean, // false
        @SerializedName("is_ad")
        val isAd: Boolean, // false
        @SerializedName("link")
        val link: String?, // https://i.imgur.com/jAPFHTx.mp4
        @SerializedName("mp4")
        val mp4: String, // https://i.imgur.com/jAPFHTx.mp4
        @SerializedName("mp4_size")
        val mp4Size: Int, // 15034518
        @SerializedName("nsfw")
        val nsfw: Boolean?, // null
        @SerializedName("points")
        val points: Int?, // null
        @SerializedName("score")
        val score: Int?, // null
        @SerializedName("section")
        val section: String?, // null
        @SerializedName("size")
        val size: Int, // 15034518
        @SerializedName("title")
        val title: String?, // null
        @SerializedName("type")
        val type: String, // video/mp4
        @SerializedName("ups")
        val ups: Int?, // null
        @SerializedName("views")
        val views: Int, // 5330
        @SerializedName("vote")
        val vote: Int?, // null
        @SerializedName("width")
        val width: Int // 960
) : Parcelable