package models;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class VideoImp {
    /**  YouTube Model from Google APi    */
    private YouTube youTube;
    /**  APi key from Google APi    */
    private static final String APIKey = "AIzaSyD2wbGudy-BKrfstjvKIr2YUaNFMilmwDs";

    public VideoImp() {
    }

    public BigInteger getVideoView(String videoId) throws IOException {

        List<Video> videoList = new ArrayList<>();
        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("Channel").build();

        YouTube.Videos.List search = youTube.videos().list("statistics");
        search.setKey(APIKey);

        VideoListResponse videoListResponse = search.setId(videoId).execute();
        videoList = videoListResponse.getItems();

        BigInteger viewTotal = videoList.get(0).getStatistics().getViewCount();

        return viewTotal;
    }
}
