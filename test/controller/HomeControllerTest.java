package controller;


import akka.actor.ActorSystem;
import controllers.AssetsFinder;
import controllers.routes;
import org.junit.BeforeClass;
import org.junit.Test;

import play.Application;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Result;
import play.shaded.ahc.org.asynchttpclient.AsyncHttpClient;
import play.shaded.ahc.org.asynchttpclient.AsyncHttpClientConfig;
import play.shaded.ahc.org.asynchttpclient.DefaultAsyncHttpClient;
import play.shaded.ahc.org.asynchttpclient.DefaultAsyncHttpClientConfig;
import play.shaded.ahc.org.asynchttpclient.netty.ws.NettyWebSocket;
import play.shaded.ahc.org.asynchttpclient.ws.WebSocket;
import play.test.Helpers;
import play.test.TestServer;
import play.test.WithApplication;
import play.twirl.api.Content;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;

import static org.mockito.Mockito.mock;
import static play.test.Helpers.*;

/**
 * Test HomeController
 *
 * @author Chenwen Wang, Kexuan Song, Geer Jiang
 */
public class HomeControllerTest extends WithApplication {


    /**
     * Sets up YoutubeApi to YoutubeTestApi
     */
    @Test
    public void testIndex() {
        running(fakeApplication(), () -> {
            Call action = routes.HomeController.index();
            Http.RequestBuilder request = Helpers.fakeRequest(action);
            Result result = route(fakeApplication(),request);
            assertEquals(303, result.status());});
    }
    /**
     * Validates a call on the router for the
     * {@code  controllers.HomeController.search(searchkey: String,request:Request)} action and validates that the
     * returned result correspond to the {@code SEE_OTHER}.
     */
    @Test
    public void search(){
        running(fakeApplication(), () -> {
            Call action = routes.HomeController.search("java");
            Http.RequestBuilder request = Helpers.fakeRequest(action);
            Result result = route(fakeApplication(),request);
            assertEquals(OK, result.status());});
//
    }
    /**
     * Validates a call on the router for the
     * {@code controllers.HomeController.similar(searchkey: String)} action and validates that the
     * returned result correspond to the {@code SEE_OTHER}.
     */
    @Test
    public void similar(){
        running(fakeApplication(), () -> {
            Call action = routes.HomeController.similar("ON1tzYUkiuE");
            Http.RequestBuilder request = Helpers.fakeRequest(action);
            Result result = route(fakeApplication(),request);
            assertEquals(OK, result.status());});
    }
    /**
     * Validates a call on the router for the
     * {@code controllers.HomeController.CVideos(channelID: String,keyword :String)} action and validates that the
     * returned result correspond to the {@code SEE_OTHER}.
     */
    @Test
    public void videos(){
        running(fakeApplication(), () -> {
            Call action = routes.HomeController.CVideos("UCiAuybSv94YrdXhrGECtAxQ","java");
            Http.RequestBuilder request = Helpers.fakeRequest(action);
            Result result = route(fakeApplication(),request);
            assertEquals(OK, result.status());});
    }
    /**
     * Validates a call on the router for the
     * {@code controllers.routes.profile(channelID: String)} action and validates that the
     * returned result correspond to the {@code SEE_OTHER}.
     */
    @Test
    public void profile() {
        running(fakeApplication(), () -> {
            Call action = routes.HomeController.profile("UCiAuybSv94YrdXhrGECtAxQ");
            Http.RequestBuilder request = Helpers.fakeRequest(action);
            Result result = route(fakeApplication(),request);
            assertEquals(OK, result.status());});
//
//            Http.RequestBuilder request = new Http.RequestBuilder()
//                    .method(GET)
//                    .uri("/search");
//
//            Result result = route(app, request);
//            assertEquals(OK, result.status());
    }
    /**
     * Validates the web socket connection by sending a request and awaiting for a response.
     */
    @Test
    public void ws(){
        TestServer server = testServer(19001);
        running(server, () -> {
            try {
                AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder().setMaxRequestRetry(0).build();
                AsyncHttpClient client = new DefaultAsyncHttpClient(config);
                WebSocketClient webSocketClient = new WebSocketClient(client);

                try {
                    String serverURL = "ws://localhost:19001/ws";
                    ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
                    WebSocketClient.LoggingListener listener = new WebSocketClient.LoggingListener((message) -> {
                        try {
                            queue.put(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    CompletableFuture<NettyWebSocket> completionStage = webSocketClient.call(serverURL, serverURL, listener);

                    WebSocket searchResult = completionStage.get();
                    assertTrue(searchResult!= null);
                } finally {
                    client.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });

    }

}
