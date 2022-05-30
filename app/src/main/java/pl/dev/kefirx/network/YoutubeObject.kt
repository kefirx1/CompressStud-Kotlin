package pl.dev.kefirx.network

import pl.dev.kefirx.StudyingActivity
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON
import pl.dev.kefirx.viewModels.StudyingViewModel

object YoutubeObject {


    private fun getSortedVideosIdList(
        channelsIdList: ArrayList<String>,
        videosIdList: ArrayList<String>,
        lesson: String,
        viewModel: StudyingViewModel,
        instance: StudyingActivity
    ): ArrayList<String> {

        viewModel.setListOfRecommendedChannels()

        val videoIdSortedList: ArrayList<String> = ArrayList()
        var recommendedLessonList: ArrayList<String> = ArrayList()
        val videosIdListCopy: ArrayList<String> = videosIdList.clone() as ArrayList<String>
        var i = -1

        viewModel.listOfChannelsResult.observe(instance){ result ->
            if(result != null){
                result.forEach{
                    if (it[0] == lesson) {
                        recommendedLessonList = it.clone() as ArrayList<String>
                    }
                }
                recommendedLessonList.removeAt(0)
            }else{
                //TODO
            }
        }



        channelsIdList.forEach { channel ->
            i++
            if (checkOnList(recommendedLessonList, channel)) {
                videoIdSortedList.add(videosIdList[i])
                videosIdListCopy.remove(videosIdList[i])
            }
        }

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


    fun getBestOfFive(responseObject: YoutubeResponseJSON, lesson: String, viewModel: StudyingViewModel, instance: StudyingActivity): ArrayList<String> {

        val channelsIdList: ArrayList<String> = ArrayList()
        val videosIdList: ArrayList<String> = ArrayList()

        responseObject.items.forEach {
            channelsIdList.add(it.snippet.channelId)
            videosIdList.add(it.id.videoId)
        }

        return getSortedVideosIdList(
            channelsIdList = channelsIdList,
            videosIdList = videosIdList,
            lesson = lesson,
            viewModel = viewModel,
            instance =  instance
        )
    }


}
