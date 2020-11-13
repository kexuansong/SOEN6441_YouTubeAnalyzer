package controllers;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;

import models.*;
import play.cache.AsyncCacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
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
    private AsyncCacheApi cache;

    /**
     * Inject and
     * @param assetsFinder handle cached & find asstes
     */

    @Inject
    public HomeController(AssetsFinder assetsFinder, AsyncCacheApi cache) {
        this.assetsFinder = assetsFinder;
        this.cache = cache;
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
    public CompletionStage<Result> index(Http.Request request) throws GeneralSecurityException, IOException {
        String value = LocalTime.now().toString();
        Optional<String> userSession = request.session().get("Connected");

        AsynProcessor asynProcessor = new AsynProcessor();


        if (userSession.isEmpty()) {
            return CompletableFuture.supplyAsync(() -> (redirect("/").addingToSession(request, "Connected", value)));
        }
        else{

            CompletionStage<Optional<List<Videos>>> optionalCompletionStage = cache.get("Connected");

            return optionalCompletionStage.thenApply(list -> {
                list.ifPresent(videos -> ok(search.render("", videos, assetsFinder)));
                return ok(index.render(assetsFinder));
            });
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
    public CompletionStage<Result> search(String searchKey,Http.Request request) {
        AsynProcessor general = new AsynProcessor();
        Optional<String> userSession = request.session().get("Connected");
        System.out.println("search" + userSession.toString());
        CompletableFuture<List<Videos>> searchResult = general.processSearchAsync(searchKey);

        CompletionStage<Optional<List<Videos>>> cacheResult = cache.get(userSession.toString());

        return searchResult.thenCombine(cacheResult,(searchR, cacheR) -> {
            List<Videos> videosList = new ArrayList<>();
            if(!cacheR.isEmpty()){
                videosList = cacheR.get();
            }
            videosList.addAll(searchR);

            System.out.println(userSession.toString());
            cache.set(userSession.toString(),videosList);

            return ok(search.render(searchKey,videosList,assetsFinder));
        });


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
//    public Result CVideos(String channelID,String keyword) throws GeneralSecurityException, IOException, ParseException {
//        List<Channel> requiredInfo = new ArrayList<>();
//        AsynProcessor asynProcessor = new AsynProcessor();
//
//
//        requiredInfo = asynProcessor.getChannelInfo(channelID);
//        Channel channel = requiredInfo.get(0);
//
//        String title = channel.getSnippet().getTitle();
//        String description = channel.getSnippet().getDescription();
//        String uploadId = channel.getContentDetails().getRelatedPlaylists().getUploads();
//
//        Playlist playlist = new Playlist();
//        List<PlaylistItem> OneChannelVideos = playlist.getPlaylistItems(uploadId);
//        List<Videos> channelVideolist = new ArrayList<>();
//        VideoImp videoImp = new VideoImp();
//
//        for (PlaylistItem p : OneChannelVideos) {
//            String videoName = p.getSnippet().getTitle();
//            //get date time
//            DateTime datetime = p.getSnippet().getPublishedAt();
//            //get date
//            Date date= new Date(p.getSnippet().getPublishedAt().getValue());
//            String pattern = "yyyy-MM-dd";
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//            //get string date as yyyy-MM-dd
//            String ndate = simpleDateFormat.format(date);
//            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
//            Date d = sdformat.parse(ndate);
//            Videos video = new Videos(title,videoName,d,ndate);
//            //System.out.println(video.getIntDate());
//            channelVideolist.add(video);
//        }
////        channelVideolist.sort((t1,t2) ->
////                t1.getVideoTitle().contains(keyword) ? 1 :
////                        t2.getVideoTitle().contains(keyword) ? 1  : 0);
//
//        Comparator<String> comparator = Comparator.<String, Boolean>comparing(s -> s.contains(keyword)).reversed()
//                .thenComparing(Comparator.naturalOrder());
//        //Collections.reverse(channelVideolist);
//        List<Videos> sortedDateList =  channelVideolist.stream().sorted(comparing(Videos::getIntDate)).collect(Collectors.toList());
//
//        // render list
//        return ok(
//                channelVideos.render(channelID,sortedDateList,assetsFinder)
//        );
//    }

    /**
     * Async search process
     * @param keyword query term
     * @param channelID channel id
     * @return not found message if error occurred or return search result list to html
     */
    public CompletionStage<Result> CVideos(String channelID,String keyword) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Videos> cv = general.processPlayListAsync(channelID,keyword).get();
                System.out.println(cv.size());
                return ok(channelVideos.render( channelID, cv, assetsFinder));
            } catch (Exception e) {
                e.printStackTrace();
                return notFound("Error");
            }
        });}

//    public CompletionStage<Result> CVideos(String channelID,String keyword) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                return general.processPlayListAsync(channelID,keyword);
//            } catch (GeneralSecurityException|IOException| ParseException e) {
//                e.printStackTrace();
//                return notFound("Error");
//            }
//        }).thenApply(results -> {
//                    try {
//                        return ok(channelVideos.render( channelID, results, assetsFinder));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return notFound("Error");
//                    }
//                }
//        );
//    }



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


    public CompletionStage<Result> similar(String searchKey) {
        return CompletableFuture.supplyAsync(() -> general.similarSearchAsync(searchKey)).thenApply(results -> {
                    try {
                        return ok(similar.render(searchKey,results.get(), assetsFinder));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return notFound("Error");
                    }
                }
        );
    }
}

