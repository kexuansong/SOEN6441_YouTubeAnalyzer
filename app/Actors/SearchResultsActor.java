package Actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import models.Videos;
import services.AsynProcessor;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class SearchResultsActor extends AbstractActorWithTimers {
    
    @Inject
    private AsynProcessor asynProcessor;

    private ActorRef userActor;

    private String query;

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private Set<Videos> results;

    /**
     * Dummy inner class used for the timer
     */
    public static final class Tick {
    }

    /**
     * Start the time, create a Tick every 5 seconds
     */
    @Override
    public void preStart() {
        getTimers().startTimerWithFixedDelay("Timer", new Tick(),
                Duration.create(5, TimeUnit.SECONDS));
    }

    /**
     * Constructor
     */
    public SearchResultsActor() {
        this.userActor = null;
        this.query = null;
        this.results = new HashSet<>();
    }

    /**
     * Handle the incoming messages
     * @return Receive receive
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.RegisterActor.class, message -> {
                    logger.info("Registering actor {}", message);
                    userActor = sender();
                    getSender().tell("UserActor registered", getSelf());
                })
                .match(Tick.class, message -> {
                    logger.info("Received message Tick {}", message);
                    if (query != null) {
                        tickMessage();
                    }
                })
                .match(Messages.WatchSearchResults.class, message -> {
                    logger.info("Received message WatchSearchResults {}", message);
                    if (message != null && message.searchKey != null) {
                        watchSearchResult(message);
                    }
                })
                .build();
    }

    /**
     * watchSearchResult message handling
     * @param message message to handle
     * @return CompletionStage of Void
     */
    public CompletionStage<Void> watchSearchResult(Messages.WatchSearchResults message) {
        // Set the query
        query = message.searchKey;

        return asynProcessor.processSearchAsync(query).thenAcceptAsync(searchResults -> {
            // This is the first time we want to watch a (new) query: reset the list
            this.results = new HashSet<>();

            // Add all the results to the list
            results.addAll(searchResults);

            searchResults.forEach(videos -> videos.setQuery(query));

            Messages.SearchingResults resultsMessage =
                    new Messages.SearchingResults(results, query);

            userActor.tell(resultsMessage, self());
        });
    }

    /**
     * watchSearchResult message handling
     * @return CompletionStage of void
     */
    public CompletionStage<Void> tickMessage() {
        // Every 5 seconds, check for new tweets if we have a query
        return asynProcessor.processSearchAsync(query).thenAcceptAsync(searchResults -> {
            // Copy the current state of results in a temporary variable
            Set<Videos> oldResults = new HashSet<>(results);

            // Add all the results to the list, now filtered to only add the new ones
            results.addAll(searchResults);

            // Copy the current state of results after addition in a temporary variable
            Set<Videos> newResults = new HashSet<>(results);

            // Get the new results only by doing new - old = what we have to display
            newResults.removeAll(oldResults);

            newResults.forEach(videos -> videos.setQuery(query));

            Messages.SearchingResults resultsMessage =
                    new Messages.SearchingResults(newResults, query);

            userActor.tell(resultsMessage, self());
        });
    }


}