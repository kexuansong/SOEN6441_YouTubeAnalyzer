package models;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import services.AsynProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * Test profile
 *
 * @author Chenwen
 */
public class TestProfile {

    /**
     * Test profile initialization
     *
     * @author Chenwen
     */
    @Test
    public void initialAndGetter(){

        String title = "java";
        String description = "hello";
        BigInteger totalViews = BigInteger.TEN;
        BigInteger totalSub = BigInteger.TEN;
        BigInteger totalVideo = BigInteger.TEN;
        String upload = "ssss";
        String url ="http";
        String country = "cn";
        DateTime dateTime = new DateTime("2019-03-27T10:00:00Z");



        ProfileImp profileImp = new ProfileImp(title,description,totalViews,totalSub,totalVideo,dateTime,country,url);
        ProfileImp profileImp1 = new ProfileImp();
        ProfileImp profileImp2 = new ProfileImp(title,upload);

        Assert.assertEquals("http",profileImp.getUrl());
        Assert.assertEquals("cn",profileImp.getCountry());
        Assert.assertEquals(new DateTime("2019-03-27T10:00:00Z"), profileImp.getPublish());
        Assert.assertEquals("java",profileImp.getTitle());
        Assert.assertEquals("hello",profileImp.getDescription());
        Assert.assertEquals(BigInteger.TEN,profileImp.getTotalViews());
        Assert.assertEquals(BigInteger.TEN,profileImp.getTotalSubscribers());
        Assert.assertEquals(BigInteger.TEN,profileImp.getTotVideos());
        Assert.assertEquals("ssss",profileImp2.getUploadId());

        profileImp2.setTitle("s");
        Assert.assertEquals("s",profileImp2.getTitle());

    }

    /**
     * Test getChannelInfo method
     * @throws GeneralSecurityException,IOException
     * @author Chenwen
     */
    @Test
    public void TestGetChannelInfo() throws GeneralSecurityException, IOException {

        YouTube youtube = mock(YouTube.class, RETURNS_DEEP_STUBS);
        Assert.assertTrue(youtube instanceof YouTube);

        YouTube.Channels.List channel = mock(YouTube.Channels.List.class, RETURNS_DEEP_STUBS);
        Assert.assertTrue(channel instanceof YouTube.Channels.List);

        ChannelListResponse response = mock(ChannelListResponse.class, RETURNS_DEEP_STUBS);
        Assert.assertTrue(response instanceof ChannelListResponse);

        List<Channel> r = new ArrayList<>();
        when(response.getItems()).thenReturn(r);

        Assert.assertEquals(response.getItems(),r);

        AsynProcessor asynProcessor = mock(AsynProcessor.class);
        MockitoAnnotations.initMocks(this);

        List<Channel> result = asynProcessor.getChannelInfo(any());

        when(asynProcessor.getChannelInfo(any())).thenReturn(result);

        Assert.assertEquals(result,result);





    }
}
