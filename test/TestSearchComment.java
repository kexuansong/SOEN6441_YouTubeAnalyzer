import models.Comments;
import org.junit.Before;

//import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

public class TestSearchComment {


    @Test
    public void SearchCommentTest1(){
        Comments comments = new Comments("1");
        Comments comments2 = new Comments("1");


        List<String> a = Arrays.asList("we have \uD83D\uDE05","all of the\uD83D\uDE06");
        List<String> b = Arrays.asList("we have \u2639\uFE0F","all of the \uD83D\uDE41","and \uD83D\uDE04");
        List<String> c = Arrays.asList("we have \uD83E\uDDB5","all of the \uD83D\uDC40");

        String result1 = comments.SearchComment(a);
//        String result2 = comments2.SearchComment(b);
//        String result3 = comments.SearchComment(c);


        assertAll(
                () -> assertEquals("\uD83D\uDC4D", result1),
//                () -> assertEquals("\uD83D\uDC4E", result2),
//                () -> assertEquals("\uD83D\uDE10", result3),
                () -> assertEquals("CommentDisabled", comments.SearchComment(null))
        );


    }

    }
