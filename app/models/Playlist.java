package models;


import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Playlist {

    /**
     * Define a global variable that identifies the name of a file that
     * contains the developer's API key.
     */
    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

    private static final String APIKey = "AIzaSyCfAeMlD340dw4KyJSH4Iq_nJBF0r7NNOg";

    private static final String APPLICATION_NAME = "API code samples";

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    private String channelId;
    private String playlistId;

    public List<PlaylistItem>getPlaylistItems(String playlistId) throws IOException{
        List<PlaylistItem> playlistItems = null;
        this.playlistId = playlistId;

        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        })
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Define and execute the API request
        YouTube.PlaylistItems.List request = youtube.playlistItems()
                .list("snippet,contentDetails");
        request.setPlaylistId(playlistId);
        request.setKey(APIKey);
        request.setFields("items(contentDetails/videoId,snippet/channelId,snippet/title,snippet/publishedAt,snippet/description)");
        PlaylistItemListResponse response = request.setMaxResults(NUMBER_OF_VIDEOS_RETURNED)
                .execute();
//        System.out.println("-----------------");
//        System.out.println("-----------------");
//        System.out.println("-----------------");
//        System.out.println(response);

        playlistItems = response.getItems();
        return playlistItems;

    }

    public void setPlaylist(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistId() { return playlistId; }

    public String getChannelId(){
        return channelId;
    }

    public void setChannelId(String channelId){
        this.channelId = channelId;
    }





}
