import controllers.AssetsFinder;
import controllers.HomeController;
import org.junit.Assert;
import org.junit.Test;
import play.cache.AsyncCacheApi;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class TestController{
    private final AssetsFinder assetsFinder;
    private AsyncCacheApi cacheApi;
    private Http.Request request;

    @Inject
    public TestController(AssetsFinder assetsFinder , AsyncCacheApi asyncCacheApi) {
        this.cacheApi = asyncCacheApi;
        this.assetsFinder = assetsFinder;
    }


}
