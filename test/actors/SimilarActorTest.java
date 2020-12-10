package actors;

import Actors.ProfileActor;
import Actors.SimilarActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import models.ProfileImp;
import org.checkerframework.checker.units.qual.K;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class SimilarActorTest {
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
     * Use TestKit to test Profile Actor message passing
     */

    @Test
    public void Test(){
        final TestKit testKit = new TestKit(system);
        ActorRef SimilarAc = system.actorOf(SimilarActor.props());
        SimilarAc.tell(new SimilarActor.SimilarRequest("9P7P1lxwZm"),testKit.getRef());
    }
}
