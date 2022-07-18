package com.example.newsfeed.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Result(
    @PrimaryKey
    val id: String,
    val apiUrl: String,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String,
    val sectionId: String,
    val sectionName: String,
    val type: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    var isFav: Boolean = false
):Serializable