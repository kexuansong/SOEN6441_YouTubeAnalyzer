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
    /**  publish date time */
    private DateTime dateTime;
    /**  publish date */
    private DateTime date;
    /**  channel id    */
    private String channelID;
    /**  video sentiment    */
    private String sentiment;
    /**  Description    */
    private String description;
    /**  String Date Time    */
    private String stringDate;


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

    public Videos(String channelTitle,String videoTitle, DateTime date,  String stringDate){
        // this.videoID = videoID;
        this.channelTitle = channelTitle;
        this.videoTitle= videoTitle;
        this.date = date;
        this.stringDate = stringDate;
//          this.sentiment = sentiment;
//          this.viewCount  = viewCount;
    }


    public Videos(String description){
        // this.videoID = videoID;
        this.description = description;
//          this.sentiment = sentiment;
//          this.viewCount  = viewCount;
    }

    /**
     * Getter
     * @return get videoID
     */
    public String getVideoID() {
        return videoID;
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
     * @return get video description
     */
    public String getVideoDescription() {
        return description;
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
     * @return get publish date
     */
    public DateTime getDate() {
        return date;
    }
    /**
     * Getter
     * @return get video title
     */
    public String getStringDate() {
        return stringDate;
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

    /**
     * Getter
     * @return get string date
     */
    public int getIntDate() {
        String intDate = stringDate.substring(0,3)+stringDate.substring(5,7)+stringDate.substring(8);
        return  Integer.parseInt(intDate);
    }
}
