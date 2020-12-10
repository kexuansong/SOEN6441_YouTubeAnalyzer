package models;
import com.google.api.client.util.DateTime;
import models.Videos;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
/**
 * Test Videos class
 *
 * @author Chenwen, Yuejun
 */
public class TestVideo {
    /**
     * Test Videos class
     *
     * @author Chenwen, Yuejun
     */
    @Test
    public void initialVideos(){
        String VideoTitle="java";
        String VideoId="1";
        String ChannelTitle="hello";
        BigInteger viewCount = BigInteger.ONE;
        String ChannelID = "aaa";
        String sentiment = "bbb";
        long dateTime=10L;
        String stringDate = "2017-09-01";
        Date date = new Date();

        Videos videos = new Videos();
        Videos videos1 = new Videos(VideoTitle,VideoId,ChannelTitle,ChannelID,viewCount,dateTime,sentiment);
        Videos videos2 = new Videos(ChannelTitle, VideoTitle, date, stringDate);
        Videos videos3 = new Videos(VideoTitle,"aaaa");

        //int result = videos2.getIntDate();
        //Assert.assertEquals(123679,result);
//        Assert.assertNotNull(videos1.getDate());
//        Assert.assertNotNull(videos1.getDateTime());
//        Assert.assertNotNull(videos1.getQuery());
        Assert.assertEquals(20170901,videos2.getIntDate());
        Assert.assertEquals(-1,videos1.getOccurenceTimesInTitle("java"));
        Assert.assertEquals("java",videos3.getVideoTitle());
        //Assert.assertEquals(new Date(),videos2.getDate());
        Assert.assertEquals("java",videos1.getVideoTitle());
        Assert.assertEquals("1",videos1.getVideoID());
        Assert.assertEquals("hello",videos1.getChannelTitle());
        Assert.assertEquals(BigInteger.ONE,videos1.getViewCount());
        Assert.assertEquals("aaa",videos1.getChannelID());
        Assert.assertEquals("bbb",videos1.getSentiment());
//      Assert.assertEquals("s",videos2.getVideoDescription());
//        Assert.assertEquals(10L,videos1.getDateTime());
        Assert.assertEquals("2017-09-01",videos2.getStringDate());



    }
}
