//package actors;
//
//import Actors.UserActor;
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//import akka.actor.Props;
//import akka.testkit.javadsl.TestKit;
//import controllers.Assets;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//public class UserActorTest {
////
////    static ActorSystem system;
////
////    @Before
////    public void setUp(){
////        system = ActorSystem.create();
////    }
////
////    @Test
////    public void test(){
////        final TestKit testKit = new TestKit(system);
////        ActorRef user = system.actorOf(UserActor.props());
////        user.tell(new UserActor.RegisterSuperMsg(), testKit.getRef());
////
////        UserActor userActor = testKit.expectMsgClass(UserActor.class);
////        Assert.assertNotNull(userActor);
////    }
//}
