package models;

import com.google.api.services.youtube.model.SearchResult;
import java.util.ArrayList;
import java.util.List;

public class YouTubeChannel {

    // List of ChannelID
    public List<String> getChannelId() {
        List<String> ChannelIdList = new ArrayList<>();
        SearchImp searchImp  = new SearchImp();

        List<SearchResult> searchResults= searchImp.SearchVideo("java");
        for (SearchResult s : searchResults) {
            String ChannelId = s.getSnippet().getChannelId();
            ChannelIdList.add(ChannelId);
        }

        return ChannelIdList;
    }


}
