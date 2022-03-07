package com.bignerdranch.android.photogallery

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class GalleryItem(
    val title: String = "",
    val id: String = "",
    @SerializedName("url_s") val url: String = "",
    @SerializedName("owner") val owner: String = ""
) {
    val photoPageUri: Uri
        get() {
            return Uri.parse("https://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(owner)
                .appendPath(id)
                .build()
        }
}