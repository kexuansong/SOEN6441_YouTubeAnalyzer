package models;


import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class AsynProcessor {
    /** * initial youtube object */
    private static YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {
        }
    }).setApplicationName("example").build();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

    /** * Api key */
    private static final String APIKey = "AIzaSyAARU7Vm1p4xqzydOh6kCOdOnHanLMWY7A";
    /** * Video list */
    private List<Videos> list = new ArrayList<>();
    /** * Channel list */
    List<Channel> channelSearchList = null;
    /** * playlist items list */
    List<PlaylistItem> playlistItems = null;
    /** * channel Video list */
    private static List<Videos> channelVideoList = new ArrayList<>();



    /**
     * Process search with YouTube api
     * @param queryTerm search key
     * @return searchResult list
     */
    public List<SearchResult> searchVideo(String queryTerm) {

        List<SearchResult> searchResultList = null;
        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.

            // Prompt the user to enter a query term.

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            search.setType("video");
            search.setKey(APIKey);
            search.setQ(queryTerm);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/channelId,snippet/title,snippet/channelTitle,snippet/publishedAt)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();


            } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResultList;
    }



    /**
     * Process searching action with Asynchronous
     * @param searchKey query term
     * @return video list
     */

    public CompletableFuture<List<Videos>> processSearchAsync(String searchKey){
        return CompletableFuture.supplyAsync(()-> searchVideo(searchKey))
                .thenApplyAsync( searchResultList -> {
                    searchResultList.forEach(searchResult -> {
                                String videoName = searchResult.getSnippet().getTitle();
                                String videoId = searchResult.getId().getVideoId();
                                VideoImp videoImp = new VideoImp();
                                Comments comments = new Comments(videoId);
                                try {
                                    GetSearchInfo(searchResult, videoName, videoId, comments, videoImp, list);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            );

                    return list;
                }
        );
    }

    /**
     * Get wrapped in Videos model and save into list
     * @param searchResult YouTube.searchResult object
     * @param videoName video name
     * @param videoId video id
     * @param c comment object
     * @param videoImp video object
     * @param list return list
     * @throws throw IOException
     */

    public static void GetSearchInfo(SearchResult searchResult, String videoName, String videoId, Comments c, VideoImp videoImp, List<Videos> list) throws IOException {
        String ChannelTitle = searchResult.getSnippet().getChannelTitle();
        String channelID = searchResult.getSnippet().getChannelId();
        DateTime dateTime = searchResult.getSnippet().getPublishedAt();

        String sentiment = c.SearchComment(c.getComments(videoId));
        BigInteger viewCount = videoImp.getVideoView(videoId);

        Videos video = new Videos(videoName,videoId,ChannelTitle,channelID,viewCount,dateTime,sentiment);
        list.add(video);
    }

    /**
     * Get Channel information from YouTube API
     * @param ChannelId channel id
     * @return Channel list , but only get 1 value
     * @throws throw GeneralSecurityException
     * @throws throw IOException
     */

    public List<Channel> getChannelInfo(String ChannelId) throws GeneralSecurityException, IOException {

        YouTube.Channels.List search =  youtube.channels().list("snippet,contentDetails,statistics");
        search.setKey(APIKey);
        search.setId(ChannelId);

        ChannelListResponse channelListResponse = search.execute();

        channelSearchList = channelListResponse.getItems();


        return channelSearchList;

    }

    /**
     * Profile Async function for HomeController
     * @param ChannelId channel id
     * @return Wrapped with Profile Object
     * @throws throw GeneralSecurityException
     * @throws throw IOException
     */

    public CompletableFuture<ProfileImp> processProfileAsync(String ChannelId) throws GeneralSecurityException, IOException {
        return CompletableFuture.supplyAsync(()->{
            try {
                return getChannelInfo(ChannelId);
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
            return channelSearchList;
        }).thenApplyAsync(channelSearchList -> {

            Channel channel = channelSearchList.get(0);
            String title = channel.getSnippet().getTitle();
            String description = channel.getSnippet().getDescription();

            BigInteger totalViews = channel.getStatistics().getViewCount();
            BigInteger totalSubscribers = channel.getStatistics().getSubscriberCount();
            BigInteger totVideos = channel.getStatistics().getVideoCount();

            return new ProfileImp(title, description, totalViews, totalSubscribers, totVideos);
        });

    }

    /**
     * Get ten videos from upload playlist from YouTube API
     * @param ChannelId playlist id
     * @return playlist items list
     * @throws throw IOException
     */

    public List<PlaylistItem>getPlaylistItems(String ChannelId) throws IOException, GeneralSecurityException {

        //this.playlistId = playlistId;
        List<Channel> requiredInfo = new ArrayList<>();
        requiredInfo = getChannelInfo(ChannelId);
        Channel channel = channelSearchList.get(0);
        // Define and execute the API request
        YouTube.PlaylistItems.List request = youtube.playlistItems()
                .list("snippet,contentDetails");
        String uploadId = channel.getContentDetails().getRelatedPlaylists().getUploads();
        request.setPlaylistId(uploadId);
        request.setKey(APIKey);
        request.setFields("items(contentDetails/videoId,snippet/channelId,snippet/title,snippet/publishedAt,snippet/description)");
        PlaylistItemListResponse response = request.setMaxResults(NUMBER_OF_VIDEOS_RETURNED)
                .execute();

//        System.out.println(response);

        playlistItems = response.getItems();
        return playlistItems;

    }


    /**
     * Process search videos of channel action with Asynchronous
     * @param ChannelId channel id
     * @param keyword query term
     * @return video list
     */
    public CompletableFuture<List<Videos>> processPlayListAsync(String ChannelId,String keyword) throws GeneralSecurityException, IOException, ParseException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getPlaylistItems(ChannelId);
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
            return playlistItems;
        }).thenApplyAsync(playlistItems -> {
            playlistItems.forEach(p -> {
                String videoName = p.getSnippet().getTitle();
                DateTime datetime = p.getSnippet().getPublishedAt();
                Date date = new Date(p.getSnippet().getPublishedAt().getValue());
                try {
                    GetVideoInfo(p, videoName, datetime, date, keyword);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            });
            return channelVideoList;
        });
}

    /**
     * Get wrapped in Videos model and save into list
     * @param p YouTube.searchResult object
     * @param videoName video name
     * @param dateTime DateTime object
     * @param date Date object
     * @param keyword String object
     * @throws throw IOException,ParseException
     */

    public static void GetVideoInfo(PlaylistItem p, String videoName, DateTime dateTime,Date date,String keyword) throws IOException ,ParseException{
        String channelTitle = p.getSnippet().getChannelTitle();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        //get string date as yyyy-MM-dd
        String ndate = simpleDateFormat.format(date);
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdformat.parse(ndate);
        Videos video = new Videos(channelTitle,videoName,d,ndate);
        //System.out.println(video.getIntDate());
        List<Videos> cvList = new ArrayList<>();
        cvList.add(video);
        cvList.sort((t1, t2) ->
                t1.getVideoTitle().contains(keyword) ? 1 :
                        t2.getVideoTitle().contains(keyword) ? 1  : 0);
       channelVideoList =  cvList.stream().sorted(comparing(Videos::getIntDate)).collect(Collectors.toList());

    }}
