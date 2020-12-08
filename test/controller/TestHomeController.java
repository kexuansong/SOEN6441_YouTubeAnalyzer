package controller;

import com.google.api.services.youtube.model.SearchResult;
import net.sf.ehcache.CacheManager;
import org.junit.After;
import org.junit.Test;

import org.mockito.Spy;
import play.Application;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.List;

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
//    public void TestSearch(){
//        Http.RequestBuilder request1 = Helpers.fakeRequest()
//                .method(GET)
//                .uri("/search");
//        Result result = Helpers.route(fakeApp2, request1);
//        assertNotNull(result);
//
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

