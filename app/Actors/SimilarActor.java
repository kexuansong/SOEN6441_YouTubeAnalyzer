package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import models.*;
import services.AsynProcessor;
import static akka.pattern.Patterns.pipe;


import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
/**
 * @author Geer Jiang
 */
public class SimilarActor extends AbstractActor{
    /**AsynProcessor from services*/
    private AsynProcessor asynProcessor;
    /**ActorRef of Actor*/
    private ActorRef actorRef;
    /**
     * Constructor
     */
    public SimilarActor() {
        this.asynProcessor = new AsynProcessor();
    }
    /**
     * Create an instance of the class
     * @return Props
     */
    public static Props props(){
        return Props.create(SimilarActor.class);
    }
    /**
     * Start Actor
     */
    @Override
    public void preStart(){
        System.out.println("Similar Actor Started");
    }
    /**
     * Similar Message
     */
    static public class SimilarRequest{
        String searchKey;
        public SimilarRequest(String searchKey) {
            this.searchKey = searchKey;
        }
    }
    /**
     * Handle the incoming messages
     * @return Receive receive
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(SimilarRequest.class,
                        similarRequest ->
                        {
                            CompletionStage<List<String>> completionStage = asynProcessor.similarSearchAsync(similarRequest.searchKey);
                            actorRef = getSender();
                            pipe(completionStage,getContext().dispatcher()).to(actorRef);
                        }
                ).build();
    }
}
