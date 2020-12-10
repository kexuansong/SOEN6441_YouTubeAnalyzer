//package actors;
//
//import Actors.SimilarActor;
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//import akka.testkit.javadsl.TestKit;
//import services.AsynProcessor;
//import org.junit.*;
//
//import java.io.IOException;
//
///**
// * Profile Actor Test
// */
//public class SimilarActorTest {
//    /**
//     * Initial actor system
//     */
//    static ActorSystem system;
//
//    /**
//     * create actor system
//     */
//    @Before
//    public void setup() {
//        system = ActorSystem.create();
//    }
//
//    /**
//     * Use TestKit to test Similar Actor message passing
//     */
//
//    @Test
//    public void Test() throws IOException {
//        final TestKit testKit = new TestKit(system);
//        ActorRef similarAc = system.actorOf(SimilarActor.props());
//        similarAc.tell(new SimilarActor.SimilarRequest("9P7P1lxwZms"),testKit.getRef());
//        AsynProcessor asynProcessor = testKit.expectMsgClass(AsynProcessor.class);
//        System.out.println(asynProcessor.searchSimilar("9P7P1lxwZms"));
//        Assert.assertEquals("King Von - Wayne's Story (Official Video)", asynProcessor.searchSimilar("9P7P1lxwZms"));
//
//    }
//}
//
