package actors;

import Actors.ProfileActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import models.ProfileImp;
import org.junit.*;


public class ProfileActorTest {

    static ActorSystem system;

    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    @Test
    public void Test(){
        final TestKit testKit = new TestKit(system);
        ActorRef profileAc = system.actorOf(ProfileActor.props());
        profileAc.tell(new ProfileActor.ProfileRequest("UCLsChHb_H87b9nW_RGCb73g"),testKit.getRef());
        ProfileImp profileImp = testKit.expectMsgClass(ProfileImp.class);
        System.out.println(profileImp.getTitle());
        Assert.assertEquals("Yellow Dude", profileImp.getTitle());

    }
}
