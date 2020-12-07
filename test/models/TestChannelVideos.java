package models;

import models.Videos;
import org.junit.Before;

//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

public class TestChannelVideos {

    /**
     * Test channelVideo initialization
     * @throws ParseException
     * @author Chenwen
     */
    @Test
    public void channelVideosTest1() throws ParseException {
        List<Videos> vl = new ArrayList<>();

        String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
        String s1 = "2015-01-01 12:00:00";
        String s2 = "2018-07-08 12:00:00";
        String s3 = "2013-06-06 12:00:00";
        String s4 = "2014-01-01 12:00:00";
        String s5 = "2012-07-08 12:00:00";
        String s6 = "2019-05-05 12:00:00";
        String s7 = "2015-02-01 12:00:00";
        String s8 = "2013-10-08 12:00:00";
        String s9 = "2012-11-03 12:00:00";
        String s10 = "2017-12-06 12:00:00";
        DateFormat formatter = new SimpleDateFormat(DEFAULT_PATTERN);
        Date d1 = formatter.parse(s1);
        Date d2 = formatter.parse(s2);
        Date d3 = formatter.parse(s3);
        Date d4 = formatter.parse(s4);
        Date d5 = formatter.parse(s5);
        Date d6 = formatter.parse(s6);
        Date d7 = formatter.parse(s7);
        Date d8 = formatter.parse(s8);
        Date d9 = formatter.parse(s9);
        Date d10 = formatter.parse(s10);

        vl.add(new Videos("hello", "good", d1, "2015-01-01"));
        vl.add(new Videos("hello", "good", d2, "2015-01-01"));
        vl.add(new Videos("hello", "good", d3, "2015-01-01"));
        vl.add(new Videos("hello", "good", d4, "2015-01-01"));



    }



}
