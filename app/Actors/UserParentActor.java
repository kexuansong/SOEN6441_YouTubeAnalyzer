package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import play.libs.akka.InjectedActorSupport;

import javax.inject.Inject;
import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;


public class UserParentActor extends AbstractActor implements InjectedActorSupport {

    private final Duration duration = Duration.ofSeconds(10L);

    private final String query;

    private final UserActor.Factory childFactory;

    /**
     * Create the default UserParentActor
     * Called by the WebSocketController
     * Runs a default search on the keyword "test"
     * @param childFactory factory to create a UserActor
     */
    @Inject
    public UserParentActor(UserActor.Factory childFactory) {
        this.childFactory = childFactory;
        this.query = "北京"; // default keyword
    }

    /**
     * Receive Akka messages
     * @return Receive receive
     */
    @Override
    @SuppressWarnings("unchecked")
    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.UserParentActorCreate.class, create -> {
                    ActorRef child = injectedChild(() -> childFactory.create(create.id), "userActor-" + create.id);
                    CompletionStage<Object> future = ask(child, new Messages.WatchSearchResults(query), duration);
                    pipe(future, context().dispatcher()).to(sender());
                }).build();
    }

}