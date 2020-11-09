import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import models.ProfileImp;
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

public class TestProfile {

    @Test
    public void initialAndGetter(){

        String title = "java";
        String description = "hello";
        BigInteger totalViews = BigInteger.TEN;
        BigInteger totalSub = BigInteger.TEN;
        BigInteger totalVideo = BigInteger.TEN;



        ProfileImp profileImp = new ProfileImp(title,description,totalViews,totalVideo,totalSub);
        ProfileImp profileImp1 = new ProfileImp();
        Assert.assertEquals("java",profileImp.getTitle());
        Assert.assertEquals("hello",profileImp.getDescription());
        Assert.assertEquals(BigInteger.TEN,profileImp.getTotalViews());
        Assert.assertEquals(BigInteger.TEN,profileImp.getTotalSubscribers());
        Assert.assertEquals(BigInteger.TEN,profileImp.getTotVideos());

    }
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

        ProfileImp profileImps = mock(ProfileImp.class);
        MockitoAnnotations.initMocks(this);

        List<Channel> result = profileImps.getChannelInfo(any());

        when(profileImps.getChannelInfo(any())).thenReturn(result);

        Assert.assertEquals(result,result);





    }
}
