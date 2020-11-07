package models;
import com.google.api.client.util.DateTime;

import java.math.BigInteger;
import java.util.Date;

/**
 * Video Model class
 * @author Wang Chenwen
 *
 */

public class Videos {
    /**  video title   */
    private String videoTitle;
    /**  video ID   */
    private String videoID;
    /**  Channel title   */
    private String channelTitle;
    /**   view number   */
    private BigInteger viewCount;
    /**  publish date  */
    private DateTime dateTime;
    /**  channel id    */
    private String channelID;
    /**  video sentiment    */
    private String sentiment;


    /**
     * Default Constructor
     */
    public Videos() {
    }

    /**
     * Constructor
     * @param videoTitle video title
     * @param channelTitle channel title
     * @param channelID channel id
     * @param dateTime publish date
     */
    public Videos(String videoTitle, String videoID, String channelTitle, String channelID,BigInteger viewCount ,DateTime dateTime,String sentiment) {
        this.videoTitle = videoTitle;
        this.videoID = videoID;
        this.channelTitle = channelTitle;
        this.channelID = channelID;
        this.dateTime = dateTime;
        this.sentiment = sentiment;
        this.viewCount  = viewCount;
    }

    /**
     * Getter
     * @return get video title
     */
    public String getVideoTitle() {
        return videoTitle;
    }

    /**
     * Getter
     * @return get video title
     */
    public String getVideoID() {
        return videoID;
    }

    /**
     * Getter
     * @return get channel title
     */
    public String getChannelTitle() {
        return channelTitle;
    }

    /**
     * Getter
     * @return get view number
     */
    public BigInteger getViewCount() {
        return viewCount;
    }

    /**
     * Getter
     * @return get publish date
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * Getter
     * @return get channel id
     */
    public String getChannelID() {
        return channelID;
    }

    /**
     * Getter
     * @return get video sentiment
     */
    public String getSentiment() {
        return sentiment;
    }

}
