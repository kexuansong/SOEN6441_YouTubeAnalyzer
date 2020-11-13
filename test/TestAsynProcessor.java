import com.google.api.services.youtube.model.SearchResult;
import models.AsynProcessor;
import models.ProfileImp;
import models.Videos;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestAsynProcessor {

    AsynProcessor asynProcessor = new AsynProcessor();

    @Test
    public void TestProcessAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Videos>> result = asynProcessor.processSearchAsync("java");
        Assert.assertEquals(10,result.get().size());

    }

    @Test
    public void TestProfileAsync() throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        CompletableFuture<ProfileImp> result = asynProcessor.processProfileAsync("UC_x5XG1OV2P6uZZ5FSM9Ttw");
        Assert.assertNotNull(result.get());
    }

    @Test
    public void TestPlayListAsync() throws ParseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        CompletableFuture<List<Videos>> result = asynProcessor.processPlayListAsync("UC_x5XG1OV2P6uZZ5FSM9Ttw","java");
        Assert.assertNotNull(result.get());
    }

    @Test
    public void TestSimilarSearch() throws ExecutionException, InterruptedException {
        CompletableFuture<Map<String,Integer>> result = asynProcessor.similarSearchAsync("vcRFkp8jHJ8");
        Assert.assertNotNull(result.get());
    }

}
