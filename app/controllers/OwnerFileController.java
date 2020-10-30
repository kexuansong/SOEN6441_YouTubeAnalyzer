package controllers;

import models.YouTubeChannel;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.video;

import javax.inject.Inject;

public class OwnerFileController extends Controller {
    private final AssetsFinder assetsFinder;


    @Inject
    public OwnerFileController(AssetsFinder assetsFinder) {
        this.assetsFinder = assetsFinder;
    }


    public Result video(){

        // create youtube chanel
        YouTubeChannel youtube = new YouTubeChannel();
        youtube.setChannelName("This is a test: YD");

         return ok(
                 video.render(youtube, assetsFinder)
         );

    }}
