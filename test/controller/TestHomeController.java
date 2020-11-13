package controller;

import com.google.api.services.youtube.model.SearchResult;
import controllers.AssetsFinder;
import models.AsynProcessor;
import models.VideoImp;
import models.Videos;
import org.junit.Test;
import org.mockito.InjectMocks;

import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.Spy;
import play.api.Application;
import play.cache.AsyncCacheApi;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestHomeController {


    private AssetsFinder assetsFinder;

    @InjectMocks
    AsyncCacheApi cache;


    @Test
    public void TestIndex(){




    }

}
