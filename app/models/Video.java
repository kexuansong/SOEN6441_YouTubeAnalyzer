package models;
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
    private Date dateTime;
    private String channelID;

    public Video() {
    }

    public Video(String videoTitle, String channelTitle,String channelID) {
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
        this.channelID = channelID;
    }

    public Video(String videoTitle, String channelTitle, BigInteger viewCount, Date dateTime) {
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
        this.viewCount = viewCount;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getChannelID() {
        return channelID;
    }
}
