import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import models.Comments;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import com.google.gson.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestGetComments2 {
    @InjectMocks
    private String VideoId;

    @Spy
    private List<CommentThread> results = null;


    public List<CommentThread> TestComment(){
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader("C:\\Users\\kexua\\Desktop\\SOEN6441_YouTubeAnalyzer\\test\\tesefile.json"));
            JSONObject jsonObject = (JSONObject) obj;
//            JSONArray itemList = (JSONArray) jsonObject.get("items");
//
//            for (Object o : itemList) {
//                System.out.println(o);
//            }

//            CommentThreadListResponse r = mock(CommentThreadListResponse.class, RETURNS_DEEP_STUBS);

            CommentThreadListResponse r = new CommentThreadListResponse();
            Gson gson = new Gson();
            String json = gson.toJson(r);
            CommentThreadListResponse obj2 = gson.fromJson(json, CommentThreadListResponse.class);

            YouTube.CommentThreads.List l = mock(YouTube.CommentThreads.List.class, RETURNS_DEEP_STUBS);
            when(l.execute()).thenReturn(obj2);

            assertEquals(obj2,l.execute());

            CommentThread t1 = mock(CommentThread.class, RETURNS_DEEP_STUBS);
            results = obj2.getItems();

            when(obj2.getItems()).thenReturn(results);
            assertEquals(results,obj2.getItems());



        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
