package actors;
import Actors.ProfileActor;
import Actors.SearchActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import models.ProfileImp;
import org.junit.*;

import Actors.CommentsActor;
/**
 * Test CommentsActor
 *
 * @author Kexuan Song
 */
public class CommentsActorTest {
    /**
     * Initial actor system
     */
    static ActorSystem system;

    /**
     * create actor system
     */
    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    /**
     * Use TestKit to test Comments Actor message passing
     */

    @Test
    public void Test(){
        final TestKit testKit = new TestKit(system);
        ActorRef commentsAc = system.actorOf(CommentsActor.getProps());
        commentsAc.tell(new SearchActor.commentMessage("ch6EwsuBWGk"),testKit.getRef());
        String sentiment = testKit.expectMsgClass(String.class);
        Assert.assertEquals("\uD83D\uDC4D", sentiment);

    }
}
