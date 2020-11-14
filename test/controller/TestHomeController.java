package controller;

import com.google.api.services.youtube.model.SearchResult;
import models.AsynProcessor;
import models.VideoImp;
import models.Videos;
import net.sf.ehcache.CacheManager;
import org.checkerframework.checker.units.qual.A;
import org.fluentlenium.core.search.Search;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.mockito.InjectMocks;

import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.Spy;
import play.Application;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static play.test.Helpers.*;

public class TestHomeController {

    @Spy
    private List<SearchResult> results = new ArrayList<>();
    Application fakeApp1 = Helpers.fakeApplication();
    Application fakeApp2 = Helpers.fakeApplication();
    Application fakeApp3 = Helpers.fakeApplication();
    Application fakeApp4 = Helpers.fakeApplication();


    @Test
    public void TestSearch(){
//        List<Videos> list = new ArrayList<>();
//
//        SearchResult s = Mockito.mock(SearchResult.class);
//        VideoImp videoImp = Mockito.mock(VideoImp.class);
//        AsynProcessor general = Mockito.mock(AsynProcessor.class);
//
//        results.add(s);
//
//        when(general.searchVideo(any())).thenReturn(results);
//        assertEquals(results,general.searchVideo(any()));
        Http.RequestBuilder request1 = Helpers.fakeRequest()
                .method(GET)
                .uri("/search");
        Result result = Helpers.route(fakeApp2, request1);
        assertNotNull(result);

    }

    @Test
    public void TestSimilar() throws IOException {

        Http.RequestBuilder request2 = Helpers.fakeRequest()
                .method(GET)
                .uri("/similar");
        Result result = Helpers.route(fakeApp1, request2);
        assertNotNull(result);
    }
    @Test
    public void TestProfile() throws IOException {
//        SearchResult s = Mockito.mock(SearchResult.class);
//        AsynProcessor general = Mockito.mock(AsynProcessor.class);
//
//        results.add(s);

        Http.RequestBuilder request3 = Helpers.fakeRequest()
                .method(GET)
                .uri("/profile");
        Result result = Helpers.route(fakeApp3, request3);
        assertNotNull(result);
    }
    @Test
    public void TestCVideo() throws IOException {
//        SearchResult s = Mockito.mock(SearchResult.class);
//        AsynProcessor general = Mockito.mock(AsynProcessor.class);
//
//        results.add(s);

        Http.RequestBuilder request4 = Helpers.fakeRequest()
                .method(GET)
                .uri("/CVideo");
        Result result = Helpers.route(fakeApp4, request4);
        assertNotNull(result);
    }
    @After
    public void shutdown(){
        CacheManager.getInstance().shutdown();
    }


}

