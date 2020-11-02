package controllers;

import com.google.api.services.youtube.model.SearchListResponse;
import models.ChannelSearch;
import models.SearchImp;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;



import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;

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
    public Result index() {
        SearchImp searchImp  = new SearchImp();


        return ok(
            index.render(
                    searchImp.SearchVideo("java")
                ,
                assetsFinder
            ));
    }




}
