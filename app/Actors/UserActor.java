package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.serialization.JSerializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.SearchingResults;
import org.json.JSONArray;
import org.json.JSONObject;
import services.AsynProcessor;

import java.util.List;
import java.util.Set;
import javax.inject.Singleton;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;
/**
 * @author Chenwen Wang
 */
public class UserActor extends AbstractActor {
    /**ActorRef of Actor*/
    private ActorRef supervisor;
    /**Constructor*/
    public UserActor(){
        this.supervisor = null;
    }
    /**
     * Create an instance of the class using.
     * @return Props
     */
    public static Props props(){
        System.out.println("User Actor Started");
        return Props.create(UserActor.class);
    }
    /**
     * Handle the incoming messages
     * @return Receive receive
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchMessage.class, this::parseToJson)
                .match(RegisterSuperMsg.class, msg ->{supervisor = sender(); })
                .build();
    }
    /**
     * Start Actor
     */
    @Override
    public void preStart(){
        System.out.println("Sending Register Message from User Actor");
        context().actorSelection("/user/SearchActor").tell(new SearchActor.RegisterMsg(),self());
    }
    /**
     *Constructor
     */
    static public class RegisterSuperMsg {
    }
    /**
     * Constructor for messages
     */
    static public class SearchMessage{

        private Set<SearchingResults> results;
        private String searchKey;

        public SearchMessage(Set<SearchingResults> results,String key) {
            this.results = results;
            this.searchKey = key;
        }
    }

    /**
     * Process the searchMessage to Json.
     * @param searchMessage - of SearchMessage class.
     */
    public void parseToJson(SearchMessage searchMessage){
        Set<SearchingResults> results = searchMessage.results;
        for (SearchingResults r: results ) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode response = mapper.valueToTree(r);
            System.out.println("get res");
            supervisor.tell(response, self());

        }
    }

}
