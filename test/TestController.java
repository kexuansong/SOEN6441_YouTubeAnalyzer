import akka.http.scaladsl.model.HttpHeader;
import controllers.AssetsFinder;
import controllers.HomeController;
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

public class TestController {
//    private final AssetsFinder assetsFinder;
//    @Inject
//    public TestController(AssetsFinder assetsFinder) {
//        this.assetsFinder = assetsFinder;
//    }
//
//    @Test
//    public void TestProfile() throws GeneralSecurityException, IOException {
//        String channelID = "adadwc";
//        Http.Request request = null;
//        Result result = new HomeController(assetsFinder).profile(channelID);
//        //Assert.assertEquals(OK,result.status());
//    }

//    @Test
//    public void TestComment(){
//        JSONParser jsonParser = new JSONParser();
//        try {
//            Object obj = jsonParser.parse(new FileReader("/Users/OngChen/IdeaProjects/SOEN6441_YouTubeAnalyzer/test/tesefile.json"));
//
//            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
//            JSONObject jsonObject = (JSONObject) obj;
//
//            // A JSON array. JSONObject supports java.util.List interface.
//            JSONArray companyList = (JSONArray) jsonObject.get("items");
//
//            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
//            // Iterators differ from enumerations in two ways:
//            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
//            // 2. Method names have been improved.
//            for (Object o : companyList) {
//                System.out.println(o);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
