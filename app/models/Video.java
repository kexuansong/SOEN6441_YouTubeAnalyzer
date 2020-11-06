package models;
import com.google.api.client.util.DateTime;

import java.math.BigInteger;
import java.util.Date;

/**
 * Video Model class
 * @author Wang Chenwen
 *
 */

public class Video {
    /**  video title   */
    private String videoTitle;
    /**  Channel title   */
    private String channelTitle;
    /**   view number   */
    private BigInteger viewCount;
    /**  publish date  */
    private DateTime dateTime;
    /**  channel id    */
    private String channelID;

    /**
     * Default Constructor
     */
    public Video() {
    }

    /**
     * Constructor
     * @param videoTitle video title
     * @param channelTitle channel title
     * @param channelID channel id
     * @param dateTime publish date
     */
    public Video(String videoTitle, String channelTitle,String channelID,DateTime dateTime) {
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
        this.channelID = channelID;
        this.dateTime = dateTime;
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

}
