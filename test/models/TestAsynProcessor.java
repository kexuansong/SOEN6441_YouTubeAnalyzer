package models;

import services.AsynProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
/**
 * Test processAsync method
 * @throws ExecutionException,InterruptedException
 * @author Chenwen
 */
public class TestAsynProcessor {
//
//    AsynProcessor asynProcessor = new AsynProcessor();
//
//
//    /**
//     * Test processAsync method
//     * @throws ExecutionException,InterruptedException
//     * @author Chenwen
//     */
//    @Test
//    public void TestProcessAsync() throws ExecutionException, InterruptedException {
//        CompletableFuture<List<SearchingResults>> result = asynProcessor.processSearchAsync("java");
//        Assert.assertEquals(2,result.get().size());
//
//    }
//
//
//    /**
//     * Test profileAsync method
//     * @throws GeneralSecurityException,IOException,ExecutionException,InterruptedException
//     * @author Chenwen
//     */
//    @Test
//    public void TestProfileAsync() throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
//        CompletableFuture<ProfileImp> result = asynProcessor.processProfileAsync("UC_x5XG1OV2P6uZZ5FSM9Ttw");
//        Assert.assertNotNull(result.get());
//    }
//
//
//    /**
//     * Test playListAsync method
//     * @throws ParseException,GeneralSecurityException,IOException,ExecutionException,InterruptedException
//     * @author Chenwen
//     */
//    @Test
//    public void TestPlayListAsync() throws ParseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
//        CompletableFuture<List<Videos>> result = asynProcessor.processPlayListAsync("UC_x5XG1OV2P6uZZ5FSM9Ttw","java");
//        Assert.assertNotNull(result.get());
//    }
//
//
//    /**
//     * Test similarSearch method
//     * @throws ExecutionException,InterruptedException
//     * @author Chenwen
//     */
//    @Test
//    public void TestSimilarSearch() throws ExecutionException, InterruptedException {
//        CompletableFuture<Map<String,Integer>> result = asynProcessor.similarSearchAsync("vcRFkp8jHJ8");
//        Assert.assertNotNull(result.get());
//    }
//
//    @Test
//    public void TestViewCount() throws IOException {
//        BigInteger result = asynProcessor.getVideoView("vcRFkp8jHJ8");
//        Assert.assertNotNull(result);
//    }

}
