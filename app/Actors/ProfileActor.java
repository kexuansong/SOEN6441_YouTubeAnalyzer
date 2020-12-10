package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import models.ProfileImp;
import services.AsynProcessor;
import static akka.pattern.Patterns.pipe;


import java.util.concurrent.CompletionStage;
/**
 * @author Chenwen Wang
 */
public class ProfileActor extends AbstractActor {
    /**AsynProcessor from services*/
    private AsynProcessor asynProcessor;
    /**ActorRef of Actor*/
    private ActorRef actorRef;
    /**
     * Constructor
     */
    public ProfileActor() {
        this.asynProcessor = new AsynProcessor();
    }
    /**
     * Create an instance of the class using {@link Props}.
     */
    public static Props props(){
        return Props.create(ProfileActor.class);
    }
    /**
     * Start Actor
     */
    @Override
    public void preStart(){
        System.out.println("Profile Actor Started");
    }
    /**
     * Profile Message
     */
    static public class ProfileRequest{
        String channelId;
        public ProfileRequest(String channelId) {
            this.channelId = channelId;
        }
    }

    /**
     * Handle the incoming messages
     * @return Receive receive
     */
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
