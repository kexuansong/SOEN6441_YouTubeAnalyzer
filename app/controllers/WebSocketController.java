package controllers;

import Actors.Messages;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.stream.javadsl.Flow;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import play.libs.F;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import static akka.pattern.Patterns.*;

public class WebSocketController extends Controller {

    private final Duration d = java.time.Duration.ofSeconds(10);
    private final Logger logger = org.slf4j.LoggerFactory.getLogger("controllers.WebSocketController");
    private final ActorRef userParentActor;

    /**
     * Constructor
     * @param userParentActor userParentActor provided by Guice
     */
    @Inject
    public WebSocketController(@Named("userParentActor") ActorRef userParentActor) {
        this.userParentActor = userParentActor;
    }

    /**
     * Generate the WebSocket
     * @return WebSocket
     */
    public WebSocket ws() {
        return WebSocket.Json.acceptOrResult(request -> {
            if (sameOriginCheck(request)) {
                final CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> future = wsFutureFlow(request);
                return future.thenApply(F.Either::Right);
            } else {
                return forbiddenResult();
            }
        });
    }

    /**
     * Create a UserParentActor with a given ID
     * @param request Request to handle
     * @return CompletionStage
     */
    @SuppressWarnings("unchecked")
    private CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> wsFutureFlow(Http.RequestHeader request) {
        long id = request.asScala().id();
        Messages.UserParentActorCreate create = new Messages.UserParentActorCreate(Long.toString(id));

        return ask(userParentActor, create, d).thenApply((Object flow) -> {
            final Flow<JsonNode, JsonNode, NotUsed> f = (Flow<JsonNode, JsonNode, NotUsed>) flow;
            return f.named("websocket");
        });
    }

    /**
     * Return a Forbidden result if the same origin check fails
     * @return CompletionStage fail
     */
    private CompletionStage<F.Either<Result, Flow<JsonNode, JsonNode, ?>>> forbiddenResult() {
        final Result forbidden = Results.forbidden("forbidden");
        final F.Either<Result, Flow<JsonNode, JsonNode, ?>> left = F.Either.Left(forbidden);

        return CompletableFuture.completedFuture(left);
    }

    /**
     * Checks that the WebSocket comes from the same origin.  This is necessary to protect
     * against Cross-Site WebSocket Hijacking as WebSocket does not implement Same Origin Policy.
     * <p>
     * See https://tools.ietf.org/html/rfc6455#section-1.3 and
     * http://blog.dewhurstsecurity.com/2013/08/30/security-testing-html5-websockets.html
     * @param rh Http Request header
     * @return boolean
     */
    private boolean sameOriginCheck(Http.RequestHeader rh) {
        final Optional<String> origin = rh.header("Origin");

        if (originMatches(origin.get())) {
            logger.debug("originCheck: originValue = " + origin);
            return true;
        } else {
            logger.error("originCheck: rejecting request because Origin header value " + origin + " is not in the same origin");
            return false;
        }
    }

    /**
     * Validate the origin
     * @param origin origin to validate
     * @return true if origin matches localhost:9000 or localhost:19001
     */
    private boolean originMatches(String origin) {
        return origin.contains("localhost:9000") || origin.contains("localhost:19001");
    }
}
