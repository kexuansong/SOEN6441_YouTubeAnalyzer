package Service;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.SearchResult;
import org.junit.Test;
import services.AsynProcessor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class YouTubeApiTest {
    @Test
    @SuppressWarnings("unchecked")
    public void testMockList() throws GeneralSecurityException, IOException {

        // #test-mockito
        // Create and train mock
        List<Channel> channelList = new ArrayList<>();
        AsynProcessor mocked = mock(AsynProcessor.class);
        when(mocked.getChannelInfo("sss")).thenReturn(channelList);

        // check value
        assertEquals(channelList, mocked.getChannelInfo("sss"));

        List<SearchResult> searchResults = new ArrayList<>();
        when(mocked.searchVideo("java")).thenReturn(searchResults);
        assertEquals(searchResults, mocked.searchVideo("java"));

        // verify interaction
//        verify(mocked.getChannelInfo("ssss")).get(0);
        // #test-mockito

    }
}
