package com.asadkhan.global.domain


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class PostData(
        @SerializedName("account_id")
        val accountId: Int, // 34891093
        @SerializedName("account_url")
        val accountUrl: String, // catchmeifcan
        @SerializedName("ad_type")
        val adType: Int, // 0
        @SerializedName("ad_url")
        val adUrl: String,
        @SerializedName("comment_count")
        val commentCount: Int, // 9
        @SerializedName("cover")
        val cover: String, // jAPFHTx
        @SerializedName("cover_height")
        val coverHeight: Int, // 864
        @SerializedName("cover_width")
        val coverWidth: Int, // 960
        @SerializedName("datetime")
        val datetime: Int, // 1571495972
        @SerializedName("description")
        val description: String?, // null
        @SerializedName("downs")
        val downs: Int, // 1
        @SerializedName("favorite")
        val favorite: Boolean, // false
        @SerializedName("favorite_count")
        val favoriteCount: Int, // 18
        @SerializedName("id")
        val id: String, // KtcDnSD
        @SerializedName("images")
        val images: List<ImageData>,
        @SerializedName("images_count")
        val imagesCount: Int, // 1
        @SerializedName("in_gallery")
        val inGallery: Boolean, // true
        @SerializedName("in_most_viral")
        val inMostViral: Boolean, // false
        @SerializedName("include_album_ads")
        val includeAlbumAds: Boolean, // false
        @SerializedName("is_ad")
        val isAd: Boolean, // false
        @SerializedName("is_album")
        val isAlbum: Boolean, // true
        @SerializedName("layout")
        val layout: String, // blog
        @SerializedName("link")
        val link: String, // https://imgur.com/a/KtcDnSD
        @SerializedName("nsfw")
        val nsfw: Boolean, // false
        @SerializedName("points")
        val points: Int, // 48
        @SerializedName("privacy")
        val privacy: String, // hidden
        @SerializedName("score")
        val score: Int, // 48
        @SerializedName("section")
        val section: String,
        @SerializedName("title")
        val title: String, // Drones flying in formation acting as a 3D display
        @SerializedName("topic")
        val topic: String, // No Topic
        @SerializedName("topic_id")
        val topicId: Int, // 29
        @SerializedName("ups")
        val ups: Int, // 49
        @SerializedName("views")
        val views: Int, // 1357
        @SerializedName("vote")
        val vote: Int? // null
) : Parcelable