import akka.http.scaladsl.model.HttpHeader;
import controllers.AssetsFinder;
import controllers.HomeController;
import models.AsynProcessor;
import models.ProfileImp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

import static play.mvc.Http.Status.OK;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class TestController {
    private final AssetsFinder assetsFinder;
    @Inject
    public TestController(AssetsFinder assetsFinder) {
        this.assetsFinder = assetsFinder;
    }


}
