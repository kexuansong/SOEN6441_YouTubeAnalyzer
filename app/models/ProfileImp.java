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

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * Get and Store information of Profile
 */
public class ProfileImp {
    /**  APi key from Google APi    */
    private static final String APIKey = "AIzaSyBveIAOxWG_yuqZBnRKdXa7594gSomtb9s";
    /**  channel title   */
    private String title;
    /**  channel description   */
    private String description;
    /**  channel view number    */
    private BigInteger totalViews;
    /**  channel subscriber number  */
    private BigInteger totalSubscribers;
    /**  channel video number   */
    private BigInteger totVideos;
    /** channel upload id*/
    private String uploadId;

    private String country;
    private DateTime publish;
    private String url;

    /**
     * Default Constructor
     */
    public ProfileImp() {
    }

    /**
     * Constructor
     * @param title Channel Title
     * @param description Channel Description
     * @param totalViews View numbers of Channel
     * @param totalSubscribers Subscribers number of Channel
     * @param totVideos Total Video Numbers
     */
    public ProfileImp(String title, String description, BigInteger totalViews, BigInteger totalSubscribers, BigInteger totVideos,DateTime dateTime, String country, String link) {
        this.title = title;
        this.description = description;
        this.totalViews = totalViews;
        this.totalSubscribers = totalSubscribers;
        this.totVideos = totVideos;
        this.country = country;
        this.publish = dateTime;
        this.url  = link;
    }

    /**
     * Constructor
     * @param title channel title
     * @param uploadId upload id
     */

    public ProfileImp(String title,String uploadId){
        this.title = title;
        this.uploadId  = uploadId;
    }

    /**
     * get profile picture
     * @return link
     */
    public String getUrl() {
        return url;
    }

    /**
     * get channel location information
     * @return country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * get channel created date
     * @return date
     */
    public DateTime getPublish() {
        return publish;
    }

    /**
     * Getter
     * @return channel title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter
     * @param title set channel title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter
     * @return channel description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter view numbers
     * @return channel view number
     */
    public BigInteger getTotalViews() {
        return totalViews;
    }

    /**
     * Getter
     * @return get total number of subscribers
     */
    public BigInteger getTotalSubscribers() {
        return totalSubscribers;
    }

    /**
     * Getter
     * @return get channel videos number
     */
    public BigInteger getTotVideos() {
        return totVideos;
    }

    /**
     * Getter
     * @return get upload id
     */
    public String getUploadId(){
        return uploadId;
    }


//    public List<Channel> getChannelInfo(String ChannelId) throws GeneralSecurityException, IOException {
//        List<Channel> channelSearchList = null;
//
//        YouTube.Channels.List search =  youTube.channels().list("snippet,contentDetails,statistics");
//        search.setKey(APIKey);
//        search.setId(ChannelId);
//
//        ChannelListResponse channelListResponse = search.execute();
//
//        channelSearchList = channelListResponse.getItems();
//
//
//        /*for(Channel channel : channelSearchList){
//            title = channel.getSnippet().getTitle();
//            description = channel.getSnippet().getDescription();
//            totalViews = channel.getStatistics().getViewCount();
//            totalSubscribers = channel.getStatistics().getSubscriberCount();
//            totVideos = channel.getStatistics().getVideoCount();
//            uploadId = channel.getContentDetails().getRelatedPlaylists().getUploads();
//            //System.out.println("===========");
//
//            //System.out.println(" upload id is " + uploadId);
//
//            //System.out.println(title + " " + description + " " + totVideos + " " + totalSubscribers + " " + totalViews);
//        }*/
//
//        return channelSearchList;
//
//    }


}
