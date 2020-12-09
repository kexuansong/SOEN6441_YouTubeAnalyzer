package models;
import com.google.api.client.util.DateTime;
import models.SearchingResults;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
/**
 * Test Searching Results
 *
 * @ Yuejun
 */
public class TestSearchingResults {

    @Test
    public void initialSearchingResults(){
        String videoTitle = "BEIJING";
        String title = "bj";
        Long publishDate = 10L;
        String videoId = "1";
        String channel = "countries";


        SearchingResults s1 = new SearchingResults(videoTitle,title,publishDate,videoId,channel);

        Assert.assertEquals("BEIJING",s1.getVideoTitle());
        Assert.assertEquals("bj",s1.getChannelTitle());
        Assert.assertEquals("1",s1.getVideoId());
        //Assert.assertEquals("countries",s1.getChannelTitle());
        //Assert.assertEquals(10L,s1.getTimeAgo());
        s1.setSentiment("good");
        Assert.assertEquals("good",s1.getSentiment());


    }
}
