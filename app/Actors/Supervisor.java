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

public class Supervisor extends AbstractActor {


    private final ActorRef ws;

    private ActorRef userActor;
    @Inject
    private ActorSystem actorSystem;


    public Supervisor(final ActorRef wsOut) {
        this.ws = wsOut;
    }


    public static Props props(final ActorRef wsOut){
        return Props.create(Supervisor.class,wsOut);
    }

    @Override
    public void preStart(){
        System.out.println("Supervisor Started");
        this.userActor = getContext().actorOf(UserActor.props());
        userActor.tell(new UserActor.RegisterSuperMsg(),self());
    }

//    private static SupervisorStrategy strategy =
//            new OneForOneStrategy(
//                    10,
//                    Duration.ofMinutes(1),
//                    DeciderBuilder.match(ArithmeticException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.resume())
//                            .match(IllegalArgumentException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.stop())
//                            .match(GoogleJsonResponseException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.resume())
//                            .match(NullPointerException.class, e -> {
//                                System.out.println("throwing null pointer to supervisor");
//                                return (SupervisorStrategy.Directive) SupervisorStrategy.stop();})
//                            .matchAny(o -> (SupervisorStrategy.Directive) SupervisorStrategy.escalate())
//                            .build());



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



    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Props.class, props -> {
            getSender().tell(getContext().actorOf(props),getSelf());
        }).match(JsonNode.class,msg->{
            System.out.println("supervisor gets jsonnode");
            ws.forward(msg,getContext());
        }).build();
    }


}
