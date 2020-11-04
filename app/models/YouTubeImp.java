package models;

import com.google.api.services.youtube.model.SearchResult;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Chenwen
 * YouTube Search function based on YouTube APi
 * */

public class YouTubeImp {

    /**
     * Get channel id based on search result
     * @param keyword search key
     */

    // List of ChannelID
    public List<String> getChannelId(String keyword) {
        List<String> ChannelIdList = new ArrayList<>();
        SearchImp searchImp  = new SearchImp();

        List<SearchResult> searchResults= searchImp.SearchVideo(keyword);
        for (SearchResult s : searchResults) {
            String ChannelId = s.getSnippet().getChannelId();
            ChannelIdList.add(ChannelId);
        }

        return ChannelIdList;
    }

    /**
     * Get profile id based on search result
     * @param keyword search key
     */
    // List of Video id
    public List<String> getVideoId(String keyword){
        List<String> VideoIdList = new ArrayList<>();
        SearchImp searchImp  = new SearchImp();

        List<SearchResult> searchResults= searchImp.SearchVideo(keyword);
        for (SearchResult s : searchResults) {
            String  VideoId = s.getId().getVideoId();
            VideoIdList.add(VideoId);
        }

        return VideoIdList;
    }

}



