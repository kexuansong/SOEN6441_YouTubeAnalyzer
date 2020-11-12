import com.google.api.client.util.DateTime;
import models.Videos;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Date;

public class TestVideo {
    @Test
    public void initialVideos(){
        String VideoTitle="java";
        String VideoId="1";
        String ChannelTitle="hello";
        BigInteger viewCount = BigInteger.ONE;
        String ChannelID = "aaa";
        String sentiment = "bbb";
        String descript = "s";
        DateTime dateTime = new DateTime("1969-12-31T19:00:01.000-05:00");

        Videos videos = new Videos();

        Videos videos1 = new Videos(VideoTitle,VideoId,ChannelTitle,ChannelID,viewCount,dateTime,sentiment);
        Videos videos2 = new Videos(VideoId,VideoTitle,dateTime,descript);
        Videos videos3 = new Videos(descript);
        Assert.assertEquals("java",videos1.getVideoTitle());
        Assert.assertEquals("1",videos1.getVideoID());
        Assert.assertEquals("hello",videos1.getChannelTitle());
        Assert.assertEquals(BigInteger.ONE,videos1.getViewCount());
        Assert.assertEquals("aaa",videos1.getChannelID());
        Assert.assertEquals("bbb",videos1.getSentiment());
        Assert.assertEquals("s",videos3.getVideoDescription());
        //Assert.assertEquals("1969-12-31T19:00:01.000-05:00",videos1.getDateTime());

    }
}
