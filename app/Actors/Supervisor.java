package Actors;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import java.io.IOException;
import java.time.Duration;

public class Supervisor extends AbstractActor {

    public static Props props(){
        return Props.create(Supervisor.class);
    }

    @Override
    public void preStart(){
        System.out.println("Supervisor Started");
    }
    private static SupervisorStrategy strategy =
            new OneForOneStrategy(
                    10,
                    Duration.ofMinutes(1),
                    DeciderBuilder.match(ArithmeticException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.resume())
                            .match(NullPointerException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.restart())
                            .match(IllegalArgumentException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.stop())
                            .match(GoogleJsonResponseException.class, e -> {
                                System.out.println("***Api ERROR!!***");
                                return (SupervisorStrategy.Directive) SupervisorStrategy.resume();})
                            .matchAny(o -> (SupervisorStrategy.Directive) SupervisorStrategy.escalate())
                            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Props.class, props -> {
            getSender().tell(getContext().actorOf(props),getSelf());
        }).build();
    }
}
