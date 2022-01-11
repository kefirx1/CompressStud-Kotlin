package pl.dev.kefirx.json.ytResponse

import pl.dev.kefirx.json.ytResponse.Thumbnails

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val liveBroadcastContent: String,
    val publishTime: String,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String
)