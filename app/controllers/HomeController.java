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
     * @author Ke Xuan, Chenwen
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



    /**
     * Async search process
     * @param searchKey query term
     * @return not found message if error occurred or return search result list to html
     * @author Ke Xuan, Chenwen
     */
    public CompletionStage<Result> search(String searchKey,Http.Request request) {
        AsynProcessor general = new AsynProcessor();
        Optional<String> userSession = request.session().get("Connected");

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


    /**
     * Async search process
     * @param keyword query term
     * @param channelID channel id
     * @return not found message if error occurred or return search result list to html
     * @author Yue Jun
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



    /**
     * Async process profile action
     * @param ChannelID channel id
     * @return not found message if error occurred or return profile object to html
     * @throws throw GeneralSecurityException
     * @throws throw IOException
     * @author Chenwen
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
    /**
     * Async process similar videos action
     * @param searchKey search key
     * @return not found message if error occurred or return profile object to html
     * @author Geer Jiang
     */

    public CompletionStage<Result> similar(String searchKey) {
        return CompletableFuture.supplyAsync(() -> general.similarSearchAsync(searchKey)).thenApply(results -> {
                    try {
                        Map<String, Integer> sMap = general.similarSearchAsync(searchKey).get();
                        return ok(similar.render(searchKey,sMap, assetsFinder));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return notFound("Error");
                    }
                }
        );
    }
}

