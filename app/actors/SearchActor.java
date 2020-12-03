package actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.inject.Inject;
import models.AsynProcessor;
import scala.concurrent.duration.Duration;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SearchActor extends AbstractActorWithTimers {

    @Inject
    private AsynProcessor asynProcessor;

    private ActorRef userActor;

    private String query;

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    //private Set<SearchingResults> results;

    // actor keeps a set of ActorRefs (registered UserActors)
    private Set<ActorRef> userActors;
    //1
    //建立新的time actor
    public static Props getProps(){
        System.out.println("TimeActor getProps");
        return Props.create(SearchActor.class);
    }

    private static final class Tick {
    }

    //2
    private SearchActor(){
        System.out.println("TimeActor initial");
        //这个地方要包东西吗
        //建立新的user actors
        this.userActors = new HashSet<>();
    }
    //4
    //建立返回的时间
    @Override
    public void preStart() {
        System.out.println("TimeActor preStart");
        getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(20, TimeUnit.SECONDS));
    }
    //3
    //建立收取机制
    @Override
    public Receive createReceive() {
        System.out.println("SearchActor createReceive");
        return receiveBuilder()
                .match(RegisterMsg.class, msg -> userActors.add(sender()))
                .match(Tick.class, msg -> notifyClients())
                .build();
    }

    static public class RegisterMsg {

    }

    private void notifyClients() {
        UserActor.TimeMessage tMsg = new UserActor.TimeMessage(LocalDateTime.now().toString());
        userActors.forEach(ar -> {
            System.out.println("notifyClients"+tMsg);
            ar.tell(tMsg, self());
        });
    }

}
