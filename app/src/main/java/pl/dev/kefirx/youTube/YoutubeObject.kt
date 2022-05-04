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
        val recommendedChannelsList = MainActivity.listOfRecommendedChannelsObject
        var recommendedLessonList: ArrayList<String> = ArrayList()
        val videosIdListCopy: ArrayList<String> = videosIdList.clone() as ArrayList<String>
        var i = -1

        recommendedChannelsList.forEach{
            if (it[0] == lesson) {
                recommendedLessonList = it.clone() as ArrayList<String>
            }
        }

        println(recommendedChannelsList)
        println(recommendedLessonList)

        recommendedLessonList.removeAt(0)


        channelsIdList.forEach { channel ->
            i++
            if (checkOnList(recommendedLessonList, channel)) {
                videoIdSortedList.add(videosIdList[i])
                videosIdListCopy.remove(videosIdList[i])
            }
        }

        println(videoIdSortedList)

        videosIdListCopy.forEach {
            println(it)
        }
        println(videosIdListCopy)


        videosIdListCopy.forEach {
            videoIdSortedList.add(it)
        }

        return videoIdSortedList
    }

    private fun checkOnList(channelsList: ArrayList<String>, channel: String): Boolean {
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
