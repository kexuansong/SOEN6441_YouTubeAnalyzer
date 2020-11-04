package controllers;

import com.google.api.services.youtube.model.SearchResult;

import models.*;
import play.data.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.FormFactory;
import play.mvc.Controller;
import play.i18n.MessagesApi;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Scala.asScala;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final AssetsFinder assetsFinder;
    private MessagesApi messagesApi;
    private final Form<searchForm> form;
    private final List<Search> widgets;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public HomeController(AssetsFinder assetsFinder, MessagesApi messagesApi, FormFactory formFactory) {
        this.form = formFactory.form(searchForm.class);
        this.messagesApi = messagesApi;
        this.widgets = com.google.common.collect.Lists.newArrayList(
                new Search("Java"),
                new Search("PHP"),
                new Search("Python")
        );
        this.assetsFinder = assetsFinder;
    }
    public Result listKeyword(Http.Request request) {
        return ok(views.html.index.render(asScala(widgets), form, request, messagesApi.preferred(request)));
    }

    public Result createKeyword(Http.Request request) {
        final Form<searchForm> boundForm = form.bindFromRequest(request);

        if (boundForm.hasErrors()) {
            logger.error("errors = {}", boundForm.errors());
            return badRequest(views.html.index.render(asScala(widgets), boundForm, request, messagesApi.preferred(request)));
        } else {
            searchForm data = boundForm.get();
            widgets.add(new Search(data.getKeyword()));
            return redirect(routes.HomeController.listKeyword())
                    .flashing("info", "Widget added!");
        }
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public Result index() throws GeneralSecurityException, IOException {
        /*
        //Display comment
        /*Comments comments = new Comments();
        comments.SearchComment("WXVHcdRniWg"); */

        //Display Channel information
        //ProfileImp profileImp = new ProfileImp();
        //profileImp.getChannelInfo("UCLsChHb_H87b9nW_RGCb73g");

        /*SearchImp searchImp = new SearchImp();
        List<SearchResult> searchResults = searchImp.SearchVideo("java, python");

        for (SearchResult s : searchResults){
            System.out.println(s.getSnippet().getTitle());
        }
        return ok(views.html.index.render());

         */
        return ok(views.html.index.render());
    }






}
