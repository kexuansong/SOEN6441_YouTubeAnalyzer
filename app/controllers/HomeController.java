package controllers;

import com.google.api.services.youtube.model.SearchResult;

import models.Comments;
import models.Profile;
import models.SearchImp;
import org.checkerframework.checker.units.qual.C;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;



import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

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
    public Result index() throws GeneralSecurityException, IOException {
        Comments comments = new Comments();

       comments.SearchComment();

        //Profile profile = new Profile();
        //profile.getChannelInfo();

        return ok(index.render("hee",assetsFinder));
    }




}
