package controller;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.google.api.services.youtube.model.SearchResult;
import controllers.AssetsFinder;
import controllers.HomeController;
import net.sf.ehcache.CacheManager;
import org.junit.After;
import org.junit.Test;

import org.mockito.Spy;
import play.Application;
import play.cache.AsyncCacheApi;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static play.test.Helpers.*;
/**
 * Test HomeController
 *
 * @author Geer Jiang, Chenwen Wang, Kexuan Song
 */
public class TestHomeController {

    @Spy
    private List<SearchResult> results = new ArrayList<>();
    Application fakeApp1 = Helpers.fakeApplication();
    Application fakeApp2 = Helpers.fakeApplication();
    Application fakeApp3 = Helpers.fakeApplication();
    Application fakeApp4 = Helpers.fakeApplication();

    /**
     * Test search method in HomeController
     *
     * @author Geer Jiang, Chenwen Wang, Kexuan Song
     */
//    @Test
//    public void testIndex() throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
//
//        ActorSystem system;
//        system = ActorSystem.create();
//
//        CompletionStage<Result> result = new HomeController(null,null,system,null).profile("aaaaa");
//        CompletableFuture<Result> resultCompletableFuture = result.toCompletableFuture();
//        assertEquals(OK, resultCompletableFuture.get().status());
//        assertEquals("text/html", resultCompletableFuture.get().contentType().get());
//        assertEquals("utf-8", resultCompletableFuture.get().charset().get());
//    }
//
//    /**
//     * Test search method in HomeController
//     *
//     * @author Geer Jiang, Chenwen Wang, Kexuan Song
//     */
//    @Test
//    public void TestSimilar(){
//
//        Http.RequestBuilder request2 = Helpers.fakeRequest()
//                .method(GET)
//                .uri("/similar");
//        Result result = Helpers.route(fakeApp1, request2);
//        assertNotNull(result);
//    }
//
//
//    /**
//     * Test search method in HomeController
//     *
//     * @author Geer Jiang, Chenwen Wang, Kexuan Song
//     */
//    @Test
//    public void TestProfile(){
//
//        Http.RequestBuilder request3 = Helpers.fakeRequest()
//                .method(GET)
//                .uri("/profile");
//        Result result = Helpers.route(fakeApp3, request3);
//        assertNotNull(result);
//    }


//    /**
//     * Test search method in HomeController
//     *
//     * @author Geer Jiang, Chenwen Wang, Kexuan Song
//     */
//    @Test
//    public void TestCVideo(){
//
//        Http.RequestBuilder request4 = Helpers.fakeRequest()
//                .method(GET)
//                .uri("/CVideo");
//        Result result = Helpers.route(fakeApp4, request4);
//        assertNotNull(result);
//    }

    /**
     * Clear cache after each test
     *
     * @author Geer Jiang, Chenwen Wang, Kexuan Song
     */
    @After
    public void shutdown(){
        CacheManager.getInstance().shutdown();
    }


}

