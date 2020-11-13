package models;

import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import models.AsynProcessor;
import models.Videos;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

public class TestAsynProcessor {
    @Mock
    Video video;


    @Test
    public void TestSearchVideo(){
        AsynProcessor asynProcessor = mock(AsynProcessor.class);
        List<SearchResult> searchResults = new ArrayList<>();
        when(asynProcessor.searchVideo("1")).thenReturn(searchResults);
        Assert.assertEquals(searchResults,asynProcessor.searchVideo("1"));
    }

    @Test
    public void TestProcessAsync(){
        AsynProcessor asynProcessor = mock(AsynProcessor.class);
        CompletableFuture<List<Videos>> list = new CompletableFuture<>();
        when(asynProcessor.processSearchAsync("1")).thenReturn(list);
        Assert.assertEquals(list, asynProcessor.processSearchAsync("1"));
    }

}
