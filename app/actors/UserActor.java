package actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class UserActor extends AbstractActorWithTimers {
    private final ActorRef ws;

    public UserActor(final ActorRef wsout) {
        this.ws = wsout;
    }

    public static Props props(final ActorRef wsout){
        return Props.create(UserActor.class, wsout);
    }

    @Override
    public void preStart() {
        context().actorSelection("/user/timeActor/")
                .tell(new SearchActor.RegisterMsg(), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TimeMessage.class, this::sendTime)
                .build();
    }

    static public class TimeMessage {
        public final String time;
        public TimeMessage(String time) {
            this.time = time;
        }
    }

    private void sendTime(TimeMessage msg) {
        final ObjectNode response = Json.newObject();
        response.put("time", msg.time);
        ws.tell(response, self());
    }

}
