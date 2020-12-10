package actors;

import Actors.SearchActor;
import Actors.UserActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * This class is used to unit test the SearchActor class.
 * @author Chenwen Wang
 *
 */
public class SearchActorTest {
    static ActorSystem system;
    /**
     * Set up the test system.
     */
    @Before
    public void setUp(){
        system = ActorSystem.create();
    }
    /**
     * Use TestKit to test search actor.
     */
    @Test
    public void testSearch(){
        final TestKit testKit = new TestKit(system);
        ActorRef user = system.actorOf(SearchActor.getProps());
        user.tell(new SearchActor.SearchRequest("java"),testKit.getRef());
        user.tell(new SearchActor.commentMessage("aaa"), testKit.getRef());
//        UserActor.SearchMessage searchMessage = testKit.expectMsgClass(UserActor.SearchMessage.class);
//        SearchActor.commentMessage commentMessage = testKit.expectMsgClass(SearchActor.commentMessage.class);
//        Assert.assertNotNull(commentMessage);
//        Assert.assertNotNull(searchMessage);

    }
}
