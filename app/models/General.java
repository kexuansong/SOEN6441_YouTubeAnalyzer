package models;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class General {
    private static YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {
        }
    }).setApplicationName("example").build();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

    private static final String APIKey = "AIzaSyD2wbGudy-BKrfstjvKIr2YUaNFMilmwDs";
    private List<Videos> list = new ArrayList<>();



    public List<SearchResult> searchVideo(String queryTerm) {

        List<SearchResult> searchResultList = null;
        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.

            // Prompt the user to enter a query term.

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            search.setType("video");
            search.setKey(APIKey);
            search.setQ(queryTerm);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/channelId,snippet/title,snippet/channelTitle,snippet/publishedAt)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();


            } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResultList;
    }

    public CompletableFuture<List<Videos>> processSearchAsync(String searchKey){
        return CompletableFuture.supplyAsync(()-> searchVideo(searchKey))
                .thenApplyAsync( searchResultList -> {

                    SearchResult searchResult = new SearchResult();
                    String videoName = searchResult.getSnippet().getTitle();
                    String videoId = searchResult.getId().getVideoId();
                    VideoImp videoImp = new VideoImp();
                    Comments comments = new Comments(videoId);
                    //initial model
                    try {
                        GetSearchInfo(searchResult, videoName, videoId, comments, videoImp, list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
