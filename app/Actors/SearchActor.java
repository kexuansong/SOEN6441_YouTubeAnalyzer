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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class SearchActor extends AbstractActorWithTimers {
    @Inject
    AsynProcessor asynProcessor;

    private Set<ActorRef> userActors;

//    private String Key = "test";

    private Set<SearchingResults> output;


    public static Props getProps() {
        return Props.create(SearchActor.class);
    }

    public static final class Tick {
    }

    public SearchActor() {
        this.userActors = new HashSet<>();
    }

    @Override
    public void preStart() {
        System.out.println("SearchActor Start");
        //getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(2, TimeUnit.SECONDS));
        getTimers().startTimerWithFixedDelay("Timer", new Tick(), Duration.create(5, TimeUnit.SECONDS));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterMsg.class, msg -> {
                    userActors.add(sender());
                })
//                .match(UserActor.firstSearchMsg.class, firstSearchMsg ->
//                        firstSearch(firstSearchMsg.key))
                .match(Tick.class, msg -> {
                    TickMessage();
                }).build();

    }

    static public class RegisterMsg {
    }

//    private void firstSearch(String key) throws GeneralSecurityException, IOException {
//        this.output = new HashSet<>();
//        List<SearchingResults> searchingResults = asynProcessor.webSocketSearch(key);
//        output.addAll(searchingResults);
//        UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(output, Key);
//        userActors.forEach(actorRef -> actorRef.tell(searchMessage, self()));
//    }

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

//    private CompletionStage<Void> TickMessage() {
//        return asynProcessor.processSearchAsync("test").thenAcceptAsync(searchResults -> {
//            // Copy the current state of results in a temporary variable
//            Set<SearchingResults> oldResults = new HashSet<>(output);
//
//            // Add all the results to the list, now filtered to only add the new ones
//            output.addAll(searchResults);
//
//            // Copy the current state of results after addition in a temporary variable
//            Set<SearchingResults> newResults = new HashSet<>(output);
//
//            // Get the new results only by doing new - old = what we have to display
//            newResults.removeAll(oldResults);
//
//            UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(newResults,"JAVA");
//
//            userActors.forEach(actorRef -> actorRef.tell(searchMessage,self()));
//        });
//
//    }

    private void TickMessage(){
        String currentTime = LocalDateTime.now().toString();
        UserActor.Time time = new UserActor.Time(currentTime);

        userActors.forEach(actorRef -> actorRef.tell(time,self()));

    }
}


