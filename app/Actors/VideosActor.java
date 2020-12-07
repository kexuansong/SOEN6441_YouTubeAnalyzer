package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.google.api.services.youtube.model.Video;
import models.ProfileImp;
import models.Videos;
import services.AsynProcessor;

import java.util.List;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.pipe;

public class VideosActor extends AbstractActor {

    private AsynProcessor asynProcessor;
    private ActorRef actorRef;

    public VideosActor() {
        this.asynProcessor = new AsynProcessor();
    }

    public static Props props(){
        return Props.create(VideosActor.class);
    }

    @Override
    public void postStop(){
        System.out.println("Videos Actor Stopped");
    }

    static public class VideosRequest{
        String channelId;
        String keyword;

        public VideosRequest(String channelId, String keyword) {
            this.channelId = channelId;
            this.keyword = keyword;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(VideosActor.VideosRequest.class,
                        VideosRequest ->
                        {
                            CompletionStage<List<Videos>> completionStage = asynProcessor.processPlayListAsync(VideosRequest.channelId, VideosRequest.keyword);
                            actorRef = getSender();
                            pipe(completionStage,getContext().dispatcher()).to(actorRef);
                        }
                ).build();
    }

}