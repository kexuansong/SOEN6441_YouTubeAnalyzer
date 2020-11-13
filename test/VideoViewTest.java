import com.google.api.services.youtube.YouTube;
import models.VideoImp;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

public class VideoViewTest {
   @Test
    public void TestViewVideo() throws IOException {
       VideoImp videoImp = new VideoImp();
       BigInteger bigInteger = videoImp.getVideoView("Ks-_Mh1QhMc");

       Assert.assertEquals(BigInteger.valueOf(18745886),bigInteger);
   }


}


