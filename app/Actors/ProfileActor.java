package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import models.ProfileImp;
import services.AsynProcessor;
import static akka.pattern.Patterns.pipe;


import java.util.concurrent.CompletionStage;

public class ProfileActor extends AbstractActor {
    private AsynProcessor asynProcessor;
    private ActorRef actorRef;

    public ProfileActor() {
        this.asynProcessor = new AsynProcessor();
    }

    public static Props props(){
        return Props.create(ProfileActor.class);
    }

    @Override
    public void preStart(){
        System.out.println("Profile Actor Started");
    }

    @Override
    public void postStop(){
        System.out.println("Profile Actor Stopped");
    }


    static public class ProfileRequest{
        String channelId;
        public ProfileRequest(String channelId) {
            this.channelId = channelId;
        }
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(ProfileRequest.class,
                        profileRequest ->
                        {
                CompletionStage<ProfileImp> completionStage = asynProcessor.processProfileAsync(profileRequest.channelId);
                actorRef = getSender();
                pipe(completionStage,getContext().dispatcher()).to(actorRef);
        }
        ).build();
    }



}
