package Actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.SearchingResults;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

public class UserActor extends AbstractActor {
    private final ActorRef ws;

    public UserActor(final ActorRef wsOut){
        this.ws = wsOut;
    }

    public static Props props(final ActorRef wsOut){
        return Props.create(UserActor.class,wsOut);

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SearchMessage.class, this::parseToJson).build();
    }

    @Override
    public void preStart(){
        context().actorSelection("/user/SearchActor/").tell(new SearchActor.RegisterMsg(),self());
    }

    static public class SearchMessage{
        private Set<SearchingResults> results;
        private String searchKey;

        public SearchMessage(Set<SearchingResults> results,String key) {
            this.results = results;
            this.searchKey = key;
        }
    }

    static public class firstSearchMsg{
        public String key;

        public firstSearchMsg(String key) {
            this.key = key;
        }
    }

    public void parseToJson(SearchMessage searchMessage){
        Set<SearchingResults> results = searchMessage.results;
        for (SearchingResults r: results ) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode response = mapper.valueToTree(r);
            ws.tell(response, self());

        }




    }
}
