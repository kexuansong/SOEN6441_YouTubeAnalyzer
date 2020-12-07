package models;

import Actors.CommentsActor;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;



public class TestGetComments {
    @InjectMocks
    private String VideoId;

    @Spy
    private List<CommentThread> results = new ArrayList<>();

    /**
     * Test getComments method
     * @throws IOException
     * @author Kexuan Song
     */
    @Test
    public void TestGetComments() throws IOException {
        CommentsActor comments = mock(CommentsActor.class);
        MockitoAnnotations.initMocks(this);


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


        when(l.setKey(any()).setVideoId("1").setMaxResults(any())).thenReturn(l);
        l.setKey(any()).setVideoId("1").setMaxResults(any());
        assertEquals(l,l.setKey(any()).setVideoId("1").setMaxResults(any()));

        List<String> commentlist = new ArrayList<>();

        CommentThread t1 = mock(CommentThread.class, RETURNS_DEEP_STUBS);
        CommentThread t2 = mock(CommentThread.class, RETURNS_DEEP_STUBS);
        CommentThread t3 = mock(CommentThread.class, RETURNS_DEEP_STUBS);
        results.add(t1);
        results.add(t2);
        results.add(t3);
        when(r.getItems()).thenReturn(results);
        r.getItems();
        verify(r).getItems();
        assertEquals(results, r.getItems());


        for(CommentThread t : results){
            String comment = t.getSnippet().getTopLevelComment().getSnippet().getTextDisplay();
            commentlist.add(comment);
        }

        when(t3.getSnippet().getTopLevelComment().getSnippet().getTextDisplay()).thenReturn("t3");
        assertEquals(3, commentlist.size());



        when(comments.getComments("1")).thenReturn(commentlist);

        assertEquals(commentlist, comments.getComments("1"));

    }


    /**
     * Test getComments method
     * @throws IOException
     * @author Kexuan Song
     */
//    @Test
//    public void TestGetComments2() throws IOException {
//        Comments comments = new Comments("vcRFkp8jHJ8");
//
//        List<String> result = Arrays.asList("Hey there, I think you really need to normalize the audio volume, jumps up and down");
//
//        assertEquals(result, comments.getComments("vcRFkp8jHJ8"));
//    }

    /**
     * Test getComments method
     * @throws IOException
     * @author Kexuan Song
     */
//    @Test
//    public void TestGetComments3() throws IOException {
//        CommentsActor comments = new CommentsActor("NFYkApw5KtM");
//
//        List<String> result = comments.getComments("NFYkApw5KtM");
//        assertEquals(0, result.size());
//    }


}
