package Actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import scala.concurrent.duration.Duration;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
//import java.time.Duration;
import akka.actor.OneForOneStrategy;
/**
 * @author Kexuan Song
 */
public class Supervisor extends AbstractActor {

    /**WebSocket*/
    private final ActorRef ws;
    /**ActorRef of Actor*/
    private ActorRef userActor;
    @Inject
    private ActorSystem actorSystem;

    /**
     * Constructor using {@link ActorRef} which represents an Actor Flow.
     *
     * @param wsOut - of type ActorRef.
     */
    public Supervisor(final ActorRef wsOut) {
        this.ws = wsOut;
    }

    /**
     * Create an instance of the class using {@link Props}.
     * @param wsOut - of type ActorRef, the actorFlow.
     * @return Props - an instance of {@link Supervisor}.
     */
    public static Props props(final ActorRef wsOut){
        return Props.create(Supervisor.class,wsOut);
    }
    /**
     * Gets called just before an actor class is created.
     */
    @Override
    public void preStart(){
        System.out.println("Supervisor Started");
        this.userActor = getContext().actorOf(UserActor.props());
        userActor.tell(new UserActor.RegisterSuperMsg(),self());
    }

    /**
     * Define the Strategy for Actor System.
     */

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                -1,
                Duration.Inf(),
                DeciderBuilder
                        .match(NullPointerException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.stop())
                        .match(GoogleJsonResponseException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.stop())
                        .build()
        );
    }


    /**
     * Handle the incoming messages
     * @return Receive receive
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Props.class, props -> {
            getSender().tell(getContext().actorOf(props),getSelf());
        }).match(JsonNode.class,msg->{
            System.out.println("**supervisor gets jsonNode**");
            ws.forward(msg,getContext());
        }).build();
    }


}
