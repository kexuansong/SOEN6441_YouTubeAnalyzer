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
import java.util.concurrent.TimeUnit;

public class SearchActor extends AbstractActorWithTimers {
    @Inject
    AsynProcessor asynProcessor;

    private Set<ActorRef> userActors;

    private String Key;

    private Set<SearchingResults> output;


    public static Props getProps(){
        return Props.create(SearchActor.class);
    }

    public static final class Tick{}

    public SearchActor() {
        this.Key = "test";
        this.userActors = new HashSet<>();
    }

    @Override
    public void preStart(){
        System.out.println("SearchActor Start");
        //getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(2, TimeUnit.SECONDS));
        getTimers().startTimerWithFixedDelay("Timer", new Tick(),Duration.create(5, TimeUnit.SECONDS));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterMsg.class, msg -> {
                    userActors.add(sender());
                })
                .match(UserActor.firstSearchMsg.class, firstSearchMsg ->
                        firstSearch(firstSearchMsg.key))
                .match(Tick.class, msg ->{
                        TickMessage();
                }).build();

    }

    static public class RegisterMsg{}

    private void firstSearch(String key) throws GeneralSecurityException, IOException {
        this.output = new HashSet<>();
        List<SearchingResults> searchingResults = asynProcessor.webSocketSearch(key);
        output.addAll(searchingResults);
        UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(output,Key);
        userActors.forEach(actorRef -> actorRef.tell(searchMessage,self()));
    }

    private void TickMessage() throws GeneralSecurityException, IOException {
        List<SearchingResults> searchingResults = asynProcessor.webSocketSearch("test");
        Set<SearchingResults> current = new HashSet<>(output);
        output.addAll(searchingResults);
        Set<SearchingResults> newResult = new HashSet<>(current);
        newResult.removeAll(current);
        UserActor.SearchMessage searchMessage = new UserActor.SearchMessage(newResult,Key);
        userActors.forEach(actorRef -> actorRef.tell(searchMessage,self()));
        }
    }


