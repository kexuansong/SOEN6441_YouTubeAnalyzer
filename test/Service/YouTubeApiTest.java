package Service;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;
import models.ProfileImp;
import models.SearchingResults;
import models.Videos;
import org.junit.Test;
import services.AsynProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Mock YouTube Api Test class
 * @author Chenwen Wang, Kexuan Song
 */
public class YouTubeApiTest {

    /**
     * mock api test method
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     * @throws ParseException exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testMockList() throws GeneralSecurityException, IOException, ParseException {

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
        when(mocked.searchSimilar("a")).thenReturn(searchResults);
        assertEquals(searchResults,mocked.searchSimilar("a"));

        CompletableFuture<List<SearchingResults>> listCompletableFuture = new CompletableFuture<>();
        when(mocked.processSearchAsync("sss")).thenReturn(listCompletableFuture);
        assertEquals(listCompletableFuture,mocked.processSearchAsync("sss"));


        CompletableFuture<ProfileImp> profileImpCompletableFuture = new CompletableFuture<>();
        when(mocked.processProfileAsync("aaa")).thenReturn(profileImpCompletableFuture);
        assertEquals(profileImpCompletableFuture,mocked.processProfileAsync("aaa"));

        CompletableFuture<List<String>> similar = new CompletableFuture<>();
        when(mocked.similarSearchAsync("a")).thenReturn(similar);
        assertEquals(similar,mocked.similarSearchAsync("a"));


        BigInteger view = BigInteger.ONE;
        when(mocked.getVideoView("a")).thenReturn(view);
        assertEquals(view,mocked.getVideoView("a"));

        List<PlaylistItem> playlistItems = new ArrayList<>();
        when(mocked.getPlaylistItems("a")).thenReturn(playlistItems);
        assertEquals(playlistItems,mocked.getPlaylistItems("a"));

        CompletableFuture<List<Videos>> playlist = new CompletableFuture<>();
        when(mocked.processPlayListAsync("a","w")).thenReturn(playlist);
        assertEquals(playlist,mocked.processPlayListAsync("a","w"));




    }
}
