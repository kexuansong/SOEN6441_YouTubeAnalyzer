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
    private String videoTitle;
    private String channelTitle;
    private BigInteger viewCount;
    private DateTime dateTime;
    private String channelID;

    public Video() {
    }

    public Video(String videoTitle, String channelTitle,String channelID,BigInteger viewCount,DateTime dateTime) {
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
        this.channelID = channelID;
        this.viewCount =viewCount;
        this.dateTime = dateTime;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public BigInteger getViewCount() {
        return viewCount;
    }

    public void setViewCount(BigInteger viewCount) {
        this.viewCount = viewCount;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }



    public String getChannelID() {
        return channelID;
    }

}
