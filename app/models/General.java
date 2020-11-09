package models;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class General {
    private SearchImp searchImp;
    private List<Videos> list = new ArrayList<>();

    Comments comments = new Comments();



    public CompletableFuture<List<Videos>> processSearchAsync(String searchKey){
        return CompletableFuture.supplyAsync(()-> searchImp.SearchVideo(searchKey))
                .thenApplyAsync( searchResultList -> {


                    SearchResult searchResult = new SearchResult();
                    String videoName = searchResult.getSnippet().getTitle();
                    String videoId = searchResult.getId().getVideoId();

                    //initial model
                    //GetChannelTitle(searchResult, videoName, videoId, comments.getComments(videoId), videoImp, list);

                    return list;
                }
        );
    }

    public static void GetSearchInfo(SearchResult searchResult, String videoName, String videoId, Comments c, VideoImp videoImp, List<Videos> list) throws IOException {
        String ChannelTitle = searchResult.getSnippet().getChannelTitle();
        String channelID = searchResult.getSnippet().getChannelId();
        DateTime dateTime = searchResult.getSnippet().getPublishedAt();

        String sentiment = c.SearchComment(c.getComments(videoId));
        BigInteger viewCount = videoImp.getVideoView(videoId);

        Videos video = new Videos(videoName,videoId,ChannelTitle,channelID,viewCount,dateTime,sentiment);
        list.add(video);
    }


}
