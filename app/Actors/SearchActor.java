package Actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.Materializer;
import models.SearchingResults;
import models.commentsActor;
import scala.compat.java8.FutureConverters;
import scala.concurrent.duration.Duration;
import services.AsynProcessor;


import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

public class SearchActor extends AbstractActorWithTimers {

    @Inject
    AsynProcessor asynProcessor;

    private ActorRef userActor;

    private ActorRef commentsActor;

    private String query = "北京";

    private Set<SearchingResults> output;

    @Inject
    private ActorSystem actorSystem;
    @Inject
    private Materializer materializer;


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
        getTimers().startTimerWithFixedDelay("Timer", new Tick(), Duration.create(10, TimeUnit.SECONDS));
        this.commentsActor = getContext().actorOf(models.commentsActor.getProps());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterMsg.class, msg -> {
                    userActor = sender();
                })
                .match(UserActor.firstSearchMsg.class, firstSearchMsg ->
                        firstSearch(firstSearchMsg.key))
                .match(Tick.class, msg -> {
                    TickMessage();
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

//    private void firstSearch(String key) throws GeneralSecurityException, IOException {
//        this.output = new HashSet<>();
//        List<SearchingResults> searchingResults = asynProcessor.webSocketSearch(key);
//        output.addAll(searchingResults);
//        UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(output, Key);
//        userActors.forEach(actorRef -> actorRef.tell(searchMessage, self()));
//    }
    private CompletionStage<Void> firstSearch(String key) throws GeneralSecurityException, IOException {
        return asynProcessor.processSearchAsync(key).thenAcceptAsync(searchResults -> {

            // Copy the current state of results in a temporary variable
            Set<SearchingResults> Results = new HashSet<>(searchResults);

            UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(Results,key);

            userActor.tell(searchMessage,self());
        });

    }

//    public CompletionStage<Void> tickMessage() {
//        // Every 5 seconds, check for new tweets if we have a query
//        return twitterService.getTweets(query).thenAcceptAsync(searchResults -> {
//            // Copy the current state of statuses in a temporary variable
//            Set<Status> oldStatuses = new HashSet<>(statuses);
//
//            // Add all the statuses to the list, now filtered to only add the new ones
//            statuses.addAll(searchResults.getStatuses());
//
//            // Copy the current state of statuses after addition in a temporary variable
//            Set<Status> newStatuses = new HashSet<>(statuses);
//
//            // Get the new statuses only by doing new - old = what we have to display
//            newStatuses.removeAll(oldStatuses);
//
//            newStatuses.forEach(status -> status.setQuery(query));
//
//            Messages.StatusesMessage statusesMessage =
//                    new Messages.StatusesMessage(newStatuses, query);
//
//            userActor.tell(statusesMessage, self());
//        });
//    }

//    Set<SearchingResults> current = new HashSet<>(output);
//            output.addAll(searchingResults);
//    Set<SearchingResults> newResult = new HashSet<>(current);
//            newResult.removeAll(current);
//    UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(newResult,Key);

    public CompletionStage<Void> TickMessage() {
        System.out.println("Key = " + query);

        return asynProcessor.processSearchAsync(query).thenAcceptAsync(searchResults -> {
//            ActorRef actorRef = actorSystem.actorOf(commentsActor.getProps());
            for(SearchingResults i : searchResults){
                System.out.println(i.getVideoId());
                SearchActor.commentMessage commentMessage = new SearchActor.commentMessage(i.getVideoId());
                commentsActor.tell(commentMessage,self());
                CompletionStage<Object> sentiment = FutureConverters.toJava(
                        ask(commentsActor,new commentMessage(i.getVideoId()),10000)
                );
                System.out.println("comment message sent");


//                i.setSentiment();


            }

            // Copy the current state of results in a temporary variable
            Set<SearchingResults> oldResults = new HashSet<>(output);

            // Add all the results to the list, now filtered to only add the new ones
            output.addAll(searchResults);

            // Copy the current state of results after addition in a temporary variable
            Set<SearchingResults> newResults = new HashSet<>(output);

            // Get the new results only by doing new - old = what we have to display
            newResults.removeAll(oldResults);


            UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(newResults,query);

            userActor.tell(searchMessage,self());
        });

    }

//    private void TickMessage(){
//        String currentTime = LocalDateTime.now().toString();
//        UserActor.Time time = new UserActor.Time(currentTime);
//
//        userActors.forEach(actorRef -> actorRef.tell(time,self()));
//    }
}


