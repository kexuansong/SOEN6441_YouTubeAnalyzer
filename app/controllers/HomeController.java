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
import views.html.index;
import views.html.profile;
import views.html.search;


import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final AssetsFinder assetsFinder;

    @Inject
    public HomeController(AssetsFinder assetsFinder) {
        this.assetsFinder = assetsFinder;
    }

    @Inject
    FormFactory formFactory;

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

        Optional<String> userSession = request.session().get("Connected");
        if(!userSession.isPresent()){
            return redirect("/").addingToSession(request,"Connected","MySession");

        } else {
            return ok(index.render(assetsFinder));}
    }

    /**
     * Search video function
     * @param searchKey query term key
     * @return pass result list to views
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Result search(String searchKey) throws GeneralSecurityException, IOException {
        List<Videos> list = new ArrayList<>();
        SearchImp searchImp = new SearchImp();
        VideoImp videoImp = new VideoImp();
//        Comments searchComments = new Comments();

        List<SearchResult> searchResults =searchImp.SearchVideo(searchKey);
        System.out.println(searchResults);


        for(SearchResult s : searchResults){
            String videoName = s.getSnippet().getTitle();
            //System.out.println("videoname：" + videoName);
            String videoID = s.getId().getVideoId();
            //System.out.println("videoid:" + videoID);
            Comments searchComments = new Comments(videoID);
            String channelTitle = s.getSnippet().getChannelTitle();
            String channelID = s.getSnippet().getChannelId();
            DateTime dateTime = s.getSnippet().getPublishedAt();
            String sentiment = searchComments.SearchComment();
            BigInteger viewCount = videoImp.getVideoView(videoID);
            //System.out.println("sentiment： "+ sentiment);
            Videos video = new Videos(videoName,videoID,channelTitle,channelID,viewCount,dateTime,sentiment);
            list.add(video);
        }





        return ok(search.render(list,assetsFinder));

    }

    //Owner/Channel Videos: Display the info for ten latest videos posted by the same owner/
    //channel as the one from the search results (where available, linked through owner field).
    //The videos must be sorted by upload date followed by the search terms.
    public Result ownerVideos(String channelID) throws GeneralSecurityException, IOException {

        List<Channel> requiredInfo = new ArrayList<>();
        ProfileImp profileImp = new ProfileImp();

        requiredInfo = profileImp.getChannelInfo(channelID);
        Channel channel = requiredInfo.get(0);

       // Playlist playlist = new Playlist();

        String title = channel.getSnippet().getTitle();
        String uploadId = channel.getContentDetails().getRelatedPlaylists().getUploads();
        String channelId = channel.getId();
        //playlist.setPlaylist(uploadId);
        //playlist.setChannelId(channelId);
        String contentOwner =  channel.getContentOwnerDetails().getContentOwner();
        String description = channel.getSnippet().getDescription();

        BigInteger totalViews = channel.getStatistics().getViewCount();
        BigInteger totalSubscribers = channel.getStatistics().getSubscriberCount();
        BigInteger totVideos = channel.getStatistics().getVideoCount();

        ProfileImp imp = new ProfileImp(title, description, totalViews, totalSubscribers, totVideos);

        // render list
        return ok(
                profile.render(imp, assetsFinder)
        );
    }

    /**
     * Perform profile request
     * @param channelID channel id for get information of required channel
     * @return pass result list to views
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Result profile(String channelID) throws GeneralSecurityException, IOException {
        List<Channel> requiredInfo = new ArrayList<>();
        ProfileImp profileImp = new ProfileImp();


        requiredInfo = profileImp.getChannelInfo(channelID);
        Channel channel = requiredInfo.get(0);

        String title = channel.getSnippet().getTitle();
        String description = channel.getSnippet().getDescription();
        String uploadId = channel.getContentDetails().getRelatedPlaylists().getUploads();
        System.out.println("-------------");
        System.out.println("upload id is ~~~~~~"+ uploadId);
        System.out.println("-------------");


        BigInteger totalViews = channel.getStatistics().getViewCount();
        BigInteger totalSubscribers = channel.getStatistics().getSubscriberCount();
        BigInteger totVideos = channel.getStatistics().getVideoCount();

        ProfileImp imp = new ProfileImp(title, description, totalViews, totalSubscribers, totVideos);

        Playlist playlist = new Playlist();
        List<PlaylistItem> OneChannelVideos = playlist.getPlaylistItems(uploadId);
        System.out.println("====================");
        System.out.println("====================");
        System.out.println("====================");
        System.out.println("====================");
        System.out.println(OneChannelVideos);
        System.out.println("====================");
        System.out.println("====================");
        System.out.println("====================");
        System.out.println("====================");


        // render list
        return ok(
                profile.render(imp, assetsFinder)
        );
    }

}

