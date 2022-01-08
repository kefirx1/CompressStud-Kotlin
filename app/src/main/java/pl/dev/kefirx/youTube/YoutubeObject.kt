package pl.dev.kefirx.youTube

import pl.dev.kefirx.json.YoutubeResponseJSON

class YoutubeObject{

    companion object{
        private const val BASIC_YT_URL = "https://www.youtube.com/watch?v="

        private fun getVideoURL(videoId: String)  = BASIC_YT_URL + videoId

        //TODO
        private fun checkChannelValue(channelsIdList: ArrayList<String>, videosIdList: ArrayList<String>): ArrayList<String>{
            val videoIdSortedList: ArrayList<String> = ArrayList()

            return videosIdList
        }

        fun getBestOfFive(responseObject: YoutubeResponseJSON): ArrayList<String>{

            val channelsIdList: ArrayList<String> = ArrayList()
            val videosIdList: ArrayList<String> = ArrayList()

            responseObject.items.forEach{
                channelsIdList.add(it.snippet.channelId)
                videosIdList.add(it.id.videoId)
            }

            return checkChannelValue(channelsIdList,videosIdList)
        }
    }


}