import models.Comments;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

public class TestSearchComment {
    @InjectMocks
    private String VideoId;



    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void SearchCommentTest(){
        Comments c = new Comments(VideoId);
        Comments spyC = spy(c);

        MockitoAnnotations.initMocks(this);

        List<String> a = Arrays.asList("we have \uD83E\uDD21","all of the \uD83D\uDC7B");;


        assertEquals("\uD83D\uDE10", spyC.SearchComment(a));
        assertEquals("CommentDisabled", spyC.SearchComment(null));



    }



    }
