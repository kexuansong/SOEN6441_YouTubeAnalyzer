package Actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;
import models.SearchingResults;
import scala.concurrent.duration.Duration;
import services.AsynProcessor;


import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;


public class SearchActor extends AbstractActorWithTimers {

    @Inject
    AsynProcessor asynProcessor;

    private ActorRef userActor;

    private ActorRef commentsActor;


    private String query;

    private Set<SearchingResults> output;


    public static Props getProps() {
        System.out.println("SearchActor Start");
        return Props.create(SearchActor.class);
    }

    public static final class Tick {
    }


    public SearchActor() {
        this.userActor = null;
        this.output = new HashSet<>();
        this.asynProcessor = new AsynProcessor();

    }

    @Override
    public void preStart() {
        //getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(2, TimeUnit.SECONDS));
        getTimers().startTimerWithFixedDelay("Timer", new Tick(), Duration.create(5, TimeUnit.SECONDS));
        this.commentsActor = getContext().actorOf(CommentsActor.getProps());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterMsg.class, msg -> {
                    userActor = sender();
                })
                .match(SearchRequest.class, firstSearchMsg ->{
                    query = firstSearchMsg.searchKey;
                    firstSearch(firstSearchMsg.searchKey);}
                )
                .match(Tick.class, msg -> {
                    if(query !=null){
                        TickMessage();}
                }).build();

    }

    static public class RegisterMsg {
    }

    static public class commentMessage{
        private String videoId;

        public commentMessage(String videoId) {
            this.videoId = videoId;
        }

        public String getVideoId() {
            return videoId;
        }

    }

    static public class SearchRequest{
        private String searchKey;

        public SearchRequest(String searchKey) {
            this.searchKey = searchKey;
        }
    }
//    private void firstSearch(String key) throws GeneralSecurityException, IOException {
//        this.output = new HashSet<>();
//        List<SearchingResults> searchingResults = asynProcessor.webSocketSearch(key);
//        output.addAll(searchingResults);
//        UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(output, Key);
//        userActors.forEach(actorRef -> actorRef.tell(searchMessage, self()));
//    }

    private void firstSearch(String key) throws GeneralSecurityException, IOException {
        asynProcessor.processSearchAsync(key).thenAcceptAsync(searchResults -> {

            // Copy the current state of results in a temporary variable
            Set<SearchingResults> Results = new HashSet<>(searchResults);

            SendWithCommentActor(searchResults);

            UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(Results, key);

            userActor.tell(searchMessage, self());
        });

    }

    private void SendWithCommentActor(List<SearchingResults> searchResults){
        for(SearchingResults i : searchResults){
            commentMessage commentMessage = new commentMessage(i.getVideoId());
            commentsActor.tell(commentMessage,self());
            CompletableFuture<Object> sentiment = ask(commentsActor,new commentMessage(i.getVideoId()),java.time.Duration.ofMillis(10000)).toCompletableFuture();
            String s = (String)sentiment.join();
            i.setSentiment(s);

        }
    }


    public void TickMessage() {
        System.out.println("Key = " + query);

        asynProcessor.processSearchAsync(query).thenAcceptAsync(searchResults -> {


            SendWithCommentActor(searchResults);

            // Copy the current state of results in a temporary variable
            Set<SearchingResults> oldResults = new HashSet<>(output);

            // Add all the results to the list, now filtered to only add the new ones
            output.addAll(searchResults);

            // Copy the current state of results after addition in a temporary variable
            Set<SearchingResults> newResults = new HashSet<>(output);

            // Get the new results only by doing new - old = what we have to display
            newResults.removeAll(oldResults);


            UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(newResults, query);

            userActor.tell(searchMessage, self());
        });

    }
}
