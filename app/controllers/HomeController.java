package controllers;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.channelVideos;
import views.html.index;
import views.html.profile;
import views.html.search;


import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    /**
     * initial assetFinder
     */
    private final AssetsFinder assetsFinder;

    /**
     * Inject and
     * @param assetsFinder handle cached & find asstes
     */
    @Inject
    public HomeController(AssetsFinder assetsFinder) {
        this.assetsFinder = assetsFinder;
    }

    /**
     * initial AsynProcessor
     */
    AsynProcessor general = new AsynProcessor();


    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) throws GeneralSecurityException, IOException {
//        //Form<Search> searchForm = formFactory.form(Search.class);
//
//        //Search search = searchForm.get();
//
//        //Display comment
//        Comments comments = new Comments();
//        comments.SearchComment("WXVHcdRniWg");
//
//        //Display Channel information
//        //ProfileImp profileImp = new ProfileImp();
//        //profileImp.getChannelInfo("UCLsChHb_H87b9nW_RGCb73g");
//        //List<SearchResult> list = null;
//
//        /*SearchImp searchImp = new SearchImp();
//        List<SearchResult> searchResults = searchImp.SearchVideo("java, python");
//
//        for (SearchResult s : searchResults){
//            System.out.println(s.getSnippet().getTitle());
//        } */

        AsynProcessor asynProcessor = new AsynProcessor();

        Optional<String> userSession = request.session().get("Connected");
        if (userSession.isPresent()) {
            return redirect("/").addingToSession(request, "Connected", "MySession");
        } else {
            return ok(index.render(asynProcessor.getList(),assetsFinder));
        }
    }

    /*/**
     * Search video function
     * @param searchKey query term key
     * @return pass result list to views
     * @throws GeneralSecurityException
     * @throws IOException
     */
//    public Result search(String searchKey) throws GeneralSecurityException, IOException {
//        List<Videos> list = new ArrayList<>();
//        VideoImp videoImp = new VideoImp();
//        General general = new General();
//
//        List<SearchResult> searchResults =general.searchVideo(searchKey);
//        System.out.println(searchResults);
//
//
//        for(SearchResult s : searchResults){
//            String videoName = s.getSnippet().getTitle();
//            //System.out.println("videoname：" + videoName);
//            String videoID = s.getId().getVideoId();
//            //System.out.println("videoid:" + videoID);
//            Comments c = new Comments(videoID);
//            General.GetSearchInfo(s, videoName, videoID, c, videoImp, list);
//        }
//
//
//
//        return ok(search.render(searchKey,list,assetsFinder));
//
//    }

    /**
     * Async search process
     * @param searchKey query term
     * @return not found message if error occurred or return search result list to html
     */
    public CompletionStage<Result> search(String searchKey) {
        return CompletableFuture.supplyAsync(() -> general.processSearchAsync(searchKey)).thenApply(results -> {
                    try {
                        return ok(search.render(searchKey, results.get(), assetsFinder));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return notFound("Error");
                    }
                }
        );
    }


    //Owner/Channel Videos: Display the info for ten latest videos posted by the same owner/
    //channel as the one from the search results (where available, linked through owner field).
    //The videos must be sorted by upload date followed by the search terms.

    /*/**
     * Search top ten videos in one channel
     * @param channelID channel id for get information of required channel
     * @return pass result list to views
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Result CVideos(String channelID,String keyword) throws GeneralSecurityException, IOException {
        List<Channel> requiredInfo = new ArrayList<>();
        AsynProcessor asynProcessor = new AsynProcessor();


        requiredInfo = asynProcessor.getChannelInfo(channelID);
        Channel channel = requiredInfo.get(0);

        String title = channel.getSnippet().getTitle();
        String description = channel.getSnippet().getDescription();
        String uploadId = channel.getContentDetails().getRelatedPlaylists().getUploads();

        Playlist playlist = new Playlist();
        List<PlaylistItem> OneChannelVideos = playlist.getPlaylistItems(uploadId);
        List<Videos> channelVideolist = new ArrayList<>();
        VideoImp videoImp = new VideoImp();

        for (PlaylistItem p : OneChannelVideos) {
            String videoName = p.getSnippet().getTitle();
//            System.out.println("videoname：" + videoName);
//            System.out.println("================");
//            System.out.println("================");
            String videoID = p.getId();
//            System.out.println("videoid:" + videoID);
//            System.out.println("================");
//            System.out.println("================");

            String channelTitle = p.getSnippet().getChannelTitle();
            String videoDescription = p.getSnippet().getDescription();
            //System.out.println("================");
            DateTime dateTime = p.getSnippet().getPublishedAt();
            //System.out.println(dateTime);
            //System.out.println(videoID);

            //Comments c = new Comments(videoID);

            //System.out.println("sentiment： "+ sentiment);
            Videos video = new Videos(videoID, videoName, dateTime, videoDescription);
            channelVideolist.add(video);
        }

        // render list
        return ok(
                channelVideos.render(channelID, channelVideolist, assetsFinder)
        );
    }


//    /**
//     * Perform profile request
//     * @param channelID channel id for get information of required channel
//     * @return pass result list to views
//     * @throws GeneralSecurityException
//     * @throws IOException
//     */
//    public Result profile(String channelID) throws GeneralSecurityException, IOException {
//        List<Channel> requiredInfo;
//        AsynProcessor asynProcessor = new AsynProcessor();
//
//
//        requiredInfo = asynProcessor.getChannelInfo(channelID);
//        Channel channel = requiredInfo.get(0);
//
//        String title = channel.getSnippet().getTitle();
//        String description = channel.getSnippet().getDescription();
//
//        BigInteger totalViews = channel.getStatistics().getViewCount();
//        BigInteger totalSubscribers = channel.getStatistics().getSubscriberCount();
//        BigInteger totVideos = channel.getStatistics().getVideoCount();
//
//        ProfileImp imp = new ProfileImp(title, description, totalViews, totalSubscribers, totVideos);
//
//        // render list
//        return ok(
//                profile.render(imp, assetsFinder)
//        );

    /**
     * Async process profile action
     * @param ChannelID channel id
     * @return not found message if error occurred or return profile object to html
     * @throws throw GeneralSecurityException
     * @throws throw IOException
     */

    public CompletionStage<Result> profile(String ChannelID) throws GeneralSecurityException, IOException {
        return CompletableFuture.supplyAsync(() -> {
            CompletableFuture<ProfileImp> profileImp = new CompletableFuture<ProfileImp>();
            try {
                profileImp = general.processProfileAsync(ChannelID);
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
            try {
                return ok(profile.render(profileImp.get(),assetsFinder));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return notFound("Error");
        });
    }

}

