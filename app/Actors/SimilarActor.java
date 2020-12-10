package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import models.*;
import services.AsynProcessor;
import static akka.pattern.Patterns.pipe;


import java.util.Map;
import java.util.concurrent.CompletionStage;
public class SimilarActor extends AbstractActor{
    private AsynProcessor asynProcessor;
    private ActorRef actorRef;
    public SimilarActor() {
        this.asynProcessor = new AsynProcessor();
    }

    public static Props props(){
        return Props.create(ProfileActor.class);
    }

    @Override
    public void preStart(){
        System.out.println("Profile Actor Started");
    }

    static public class SimilarRequest{
        String searchKey;
        public SimilarRequest(String searchKey) {
            this.searchKey = searchKey;
        }
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(SimilarRequest.class,
                        similarRequest ->
                        {
                            CompletionStage<Map<String, Integer>> completionStage = asynProcessor.similarSearchAsync(similarRequest.searchKey);
                            actorRef = getSender();
                            pipe(completionStage,getContext().dispatcher()).to(actorRef);
                        }
                ).build();
    }
}
