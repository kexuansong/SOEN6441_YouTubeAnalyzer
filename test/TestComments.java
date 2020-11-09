import com.google.api.client.json.GenericJson;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.inject.Inject;
import models.*;
import com.google.api.services.youtube.YouTube;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.plugins.MockMaker;


import org.mockito.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import static org.mockito.Mockito.*;


public class TestComments {
    @InjectMocks
    private String VideoId;

    @Test
    public void TestGetComments() throws IOException {



        YouTube youtube = mock(YouTube.class, RETURNS_DEEP_STUBS);
        Assert.assertTrue(youtube instanceof YouTube);

        YouTube.CommentThreads c = mock(YouTube.CommentThreads.class, RETURNS_DEEP_STUBS);
        Assert.assertTrue(c instanceof YouTube.CommentThreads);

        YouTube.CommentThreads.List l = mock(YouTube.CommentThreads.List.class, RETURNS_DEEP_STUBS);
        Assert.assertTrue(l instanceof YouTube.CommentThreads.List);

        CommentThreadListResponse r = mock(CommentThreadListResponse.class, RETURNS_DEEP_STUBS);
        Assert.assertTrue(r instanceof CommentThreadListResponse);



        when(youtube.commentThreads().list(any())).thenReturn(l);

        youtube.commentThreads().list(any());
        assertEquals(l,youtube.commentThreads().list(any()));


        when(l.setKey(any()).setVideoId(any()).setMaxResults(any()).execute()).thenReturn(r);
        assertEquals(r,l.setKey(any()).setVideoId(any()).setMaxResults(any()).execute());


        CommentThread commentThread = mock(CommentThread.class, RETURNS_DEEP_STUBS);
        List<CommentThread> list = new ArrayList<>();
        when(r.getItems()).thenReturn(list);
        assertEquals(list, r.getItems());





        Comments comments = mock(Comments.class);
        MockitoAnnotations.initMocks(this);


//        List<String> a = Arrays.asList("we have","all of the");
        List<String> a = new ArrayList<>();

        List<String> result = comments.getComments(any());

        when(comments.getComments(any())).thenReturn(a);

        assertEquals(a, result);



//        try {
//            YouTube.CommentThreads c = mock(YouTube.CommentThreads.class);
////            Assert.assertTrue(c instanceof YouTube);
//            Assert.assertTrue(c instanceof YouTube.CommentThreads);
//
////

////            commentsList.add(commentThread);
//
//
//            doReturn(c).when(youtube).commentThreads()
//                    .list("a").setKey("ags").setVideoId("aaa")
//                    .setMaxResults(10L).execute().getItems();


//            List<String> s = Arrays.asList("ok");
//
//            when(comments.getComments("1")).thenReturn(s);
//
//            comments.getComments("1");
//
//            verify(comments).getComments("1");

//            assertEquals(commentsList, youtube.commentThreads()
//                    .list("a").setKey("ags").setVideoId("aaa")
//                    .setMaxResults(10L).execute().getItems());

//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
    }


}
