import com.google.api.services.youtube.model.SearchResult;
import models.AsynProcessor;
import models.VideoImp;
import models.Videos;
import org.fluentlenium.core.search.Search;
import org.junit.Test;
import org.mockito.InjectMocks;

import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.Spy;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestHomeController {

    @Spy
     private List<SearchResult> results = new ArrayList<>();


    @Test
    public void TestSearch(){
        List<Videos> list = new ArrayList<>();

        SearchResult s = Mockito.mock(SearchResult.class);
        VideoImp videoImp = Mockito.mock(VideoImp.class);
        AsynProcessor general = Mockito.mock(AsynProcessor.class);

        results.add(s);

        when(general.searchVideo(any())).thenReturn(results);
        assertEquals(results,general.searchVideo(any()));




    }

}
