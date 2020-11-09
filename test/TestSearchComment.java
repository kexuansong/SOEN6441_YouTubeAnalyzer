import com.google.inject.Inject;
import models.*;
import com.google.api.services.youtube.YouTube;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


import org.mockito.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.*;

public class TestSearchComment {
    @InjectMocks
    private String VideoId;

//    @Before
//    public void setUp(){
//        Comments c = spy(new Comments(VideoId));
//        MockitoAnnotations.initMocks(this);
//    }


    @Test
    public void SearchCommentTest1(){
        Comments comments = spy(new Comments(VideoId));
        MockitoAnnotations.initMocks(this);
        List<String> a = Arrays.asList("we have \uD83D\uDE04","all of the");


        String resultA = comments.SearchComment(a);


        verify(comments).SearchComment(a);
        assertEquals("\uD83D\uDC4D", resultA);


    }

//    @Test
//    public void SearchCommentTest2(){
//        Comments comments2 = spy(new Comments(VideoId));
//        MockitoAnnotations.initMocks(this);
//
//        List<String> b = Arrays.asList("we have \uD83D\uDE41","all of the");
//
//        String resultB = comments2.SearchComment(b);
//
//        verify(comments2).SearchComment(b);
//        assertEquals("\uD83D\uDC4E", resultB);
//
//
//    }

//    @Test
//    public void SearchCommentTest3(){
//        Comments comments3 = spy(new Comments(VideoId));
//        MockitoAnnotations.initMocks(this);
//
//        List<String> c = Arrays.asList("we have \uD83E\uDD0F","all of the");
//
//        String resultC = comments3.SearchComment(c);
//
//        verify(comments3).SearchComment(c);
//        assertEquals("\uD83D\uDE10", resultC);
//
//    }



    }
