package models;

import java.math.BigInteger;

/**
 * @author Chenwen Wang
 */

public class SearchingResults {
    /**
    video title
     */
    private String videoTitle;
    /**
    channel title
    */
    private String channelTitle;
    /**
    timeAgo
    */
    private Long timeAgo;
    /**
    channel id
    */
    private String ChannelID;
    /**
    query
    */
    private String query;
    /**
    video id
    */
    private String videoId;
    /**
    sentiment
    */
    private String sentiment;
    /**
    view count
    */
    private BigInteger viewCount;

/**
 * Constructor
 * @param videoTitle  String
 * @param title       String
 * @param publishDate long
 * @param view        BigInteger
 * @param videoId     videoId
 * @param channel     channel query
 * @param query       query
 */
    public SearchingResults(String videoTitle, String title, Long publishDate,BigInteger view ,String videoId, String channel, String query) {
        this.videoTitle = videoTitle;
        this.channelTitle = title;
        this.timeAgo = publishDate;
        this.viewCount =view;
        this.videoId = videoId;
        this.ChannelID = channel;
        this.query = query;
    }

//    public void setQuery(String query) {
//        this.query = query;
//    }
    /**
     * Getter
     * @return get channel id
     */
    public String getChannelID(){return ChannelID;}

    /**
     * Getter
     * @return get video title
     */
    public String getVideoTitle() {
        return videoTitle;
    }
    /**
     * Getter
     * @return get channel title
     */
    public String getChannelTitle() {
        return channelTitle;
    }
    /**
     * Setter
     * @param  sentiment
     * set sentiment
     */
    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    /**
     * Getter
     * @return get sentiment
     */
    public String getSentiment() {
        return sentiment;
    }
    /**
     * Getter
     * @return get timeAgo
     */
    public Long getTimeAgo() {
        return timeAgo;
    }
    /**
     * Getter
     * @return get view count
     */
    public BigInteger getViewCount() {
        return viewCount;
    }
    /**
     * Getter
     * @return get video id
     */
    public String getVideoId(){ return videoId;}

    /**
     * Getter
     * @return get query
     */
    public String getQuery() {
        return query;
    }
}
