package controllers;

import akka.actor.ActorSystem;
import akka.stream.Materializer;

import models.*;
import play.cache.AsyncCacheApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.AsynProcessor;
import views.html.*;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
    private ActorSystem actorSystem;
    private final Materializer materializer;

    /**
     * Inject and
     * @param assetsFinder handle cached & find asstes
     * @param actorSystem actor system
     * @param materializer materializer
     */

    @Inject
    public HomeController(AssetsFinder assetsFinder, AsyncCacheApi cache, ActorSystem actorSystem, Materializer materializer) {
        this.assetsFinder = assetsFinder;
        this.cache = cache;
        this.actorSystem = actorSystem;
        this.materializer = materializer;
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
     * Async search recent videos in channel process
     * @param keyword query term
     * @param channelID channel id
     * @return not found message if error occurred or return search result list to html
     * @author YueJun Chen
     */
    public CompletionStage<Result> CVideos(String channelID,String keyword) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Videos> cv = general.processPlayListAsync(channelID,keyword).get();
                System.out.println(cv.size());
                return ok(channelVideos.render( cv, assetsFinder));
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
        CompletableFuture<ProfileImp> profileImp = new CompletableFuture<ProfileImp>();
        profileImp = general.processProfileAsync(ChannelID);
        return profileImp.thenApply(r -> ok(profile.render(r,assetsFinder)));

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

