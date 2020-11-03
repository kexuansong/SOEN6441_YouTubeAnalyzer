package models;

/**
 * @author Wang Chenwen
 * Get Channel information for creating Profile
 */

import com.google.api.client.auth.oauth2.Credential;
        import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
        import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
        import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
        import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
        import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
        import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
        import com.google.api.client.json.JsonFactory;
        import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
        import java.util.Collection;
import java.util.List;

public class ProfileImp {
    private static final String APIKey = "AIzaSyCDSxqEwVEt6PiATRyGqYm3_dYPFhsHERg";
    private String title;
    private String description;
    private BigInteger totalViews;
    private BigInteger totalSubscribers;
    private BigInteger totVideos;

    private YouTube youTube;

    /**
     * Get channel title, channel description, view number, video number and subscriber number
     * @param ChannelId channel id
     */

    public void getChannelInfo(String ChannelId) throws GeneralSecurityException, IOException {
        List<Channel> channelSearchList = null;
        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("Channel").build();

        YouTube.Channels.List search =  youTube.channels().list("snippet,statistics");
        search.setKey(APIKey);
        search.setId(ChannelId);

        ChannelListResponse channelListResponse = search.execute();

        channelSearchList = channelListResponse.getItems();


        for(Channel channel : channelSearchList){
            title = channel.getSnippet().getTitle();
            description = channel.getSnippet().getDescription();

            totalViews = channel.getStatistics().getViewCount();
            totalSubscribers = channel.getStatistics().getSubscriberCount();
            totVideos = channel.getStatistics().getVideoCount();


            System.out.println("===========");
            System.out.println(title + " " + description + " " + totVideos + " " + totalSubscribers + " " + totalViews);
        }
    }





}