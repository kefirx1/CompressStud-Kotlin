package pl.dev.kefirx.youTube

import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON

object YoutubeObject {


    private fun getSortedVideosIdList(
        channelsIdList: ArrayList<String>,
        videosIdList: ArrayList<String>,
        lesson: String
    ): ArrayList<String> {

        val videoIdSortedList: ArrayList<String> = ArrayList()

        channelsIdList.forEach { channel ->
            MainActivity.listOfRecommendedChannelsObject.forEach {
                if (it[0] == lesson) {
                    val channelsList = it.subList(1, it.size - 1)
                    if (checkOnList(channelsList, channel)) {
                        videoIdSortedList.add(videosIdList[channelsIdList.indexOf(channel)])
                        videosIdList.removeAt(channelsIdList.indexOf(channel))
                    }
                }
            }
        }

        videosIdList.forEach {
            videoIdSortedList.add(it)
        }

        return videoIdSortedList
    }

    private fun checkOnList(channelsList: MutableList<String>, channel: String): Boolean {
        channelsList.forEach {
            if (it == channel) {
                return true
            }
        }
        return false
    }


    fun getBestOfFive(responseObject: YoutubeResponseJSON, lesson: String): ArrayList<String> {

        val channelsIdList: ArrayList<String> = ArrayList()
        val videosIdList: ArrayList<String> = ArrayList()

        responseObject.items.forEach {
            channelsIdList.add(it.snippet.channelId)
            videosIdList.add(it.id.videoId)
        }

        return getSortedVideosIdList(channelsIdList, videosIdList, lesson)
    }


}
