package controllers;

import javax.json.Json;
import play.mvc.Controller;

import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@ServerEndpoint("/ws")
public class wsController extends Controller {

    static Set<Session> sessionUsers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void open(Session session){
        sessionUsers.add(session);
        System.out.println("ws controller opened" + session.getId());
    }

    @OnMessage
    public void message(String message, Session session) throws IOException {
//            String username = (String)session.getUserProperties().get("username");
//            if(username == null){
//                session.getUserProperties().put("username", message);
                session.getBasicRemote().sendText(buildJsonData(message));

    }
//        }


    @OnClose
    public void close(Session session){
        sessionUsers.remove(session);
    }

    private String buildJsonData(String message){
        JsonObject jsonObject = Json.createObjectBuilder().add("message",message).build();
        StringWriter stringWriter = new StringWriter();
        try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){jsonWriter.write((jsonObject));}
        return stringWriter.toString();
    }
}
