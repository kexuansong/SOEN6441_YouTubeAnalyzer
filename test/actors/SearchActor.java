//package actors;
//
//import static org.junit.Assert.assertThat;
//
//import Actors.SearchActor;
//import org.hamcrest.CoreMatchers;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//import actors.SearchActor.TickMessage;
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//import akka.testkit.javadsl.TestKit;
//import models.ProfileImp;
//import models.Videos;
//
///**
// * This class is used to unit test the TweetSearchActor class.
// * @author Chenwen Wang
// *
// */
//public class SearchActorTest {
//
//    static ActorSystem system;
//
//    /**
//     * Create an actor system for the unit tests.
//     */
//    @BeforeClass
//    public static void setup() {
//        system = ActorSystem.create();
//    }
//
//    /**
//     * Destroys the actor system.
//     */
//    @AfterClass
//    public static void teardown() {
//        TestKit.shutdownActorSystem(system);
//        system = null;
//    }}
//
//    /**
//     * Validates that search returns at least one tweet by calling the {@code RequestTweetMessage} message protocol and using the
//     * {@code TweetSearchActor} actor.
//     */
//    @Test
//    public void testSearch() {
//        TestKit probe = new TestKit(system);
//        ActorRef testActor = probe.getTestActor();
//        ActorRef searchActor = system.actorOf(SearchActor.props(testActor));
//        SearchActor.RegisterMsg requestMessage = new SearchActor.RegisterMsg(new MockServiceImpl(),
//                null, "bank", null, null);
//        tweetActor.tell(requestTweetMessage, probe.getRef());
//        ObjectNode objectNode = probe.expectMsgClass(ObjectNode.class);
//        assertThat(objectNode.size(), CoreMatchers.is(1));
//    }}
//
