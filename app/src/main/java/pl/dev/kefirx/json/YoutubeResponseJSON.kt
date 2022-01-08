package pl.dev.kefirx.json

data class YoutubeResponseJSON(
    val etag: String,
    val items: List<Items>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)