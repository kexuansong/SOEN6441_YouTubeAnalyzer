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
 * @author Geer Jiang, Chenwen Wang, Kexuan Song
 */
    public class HomeControllerTest extends WithApplication {


        /**
         * Sets up twitterApi to TwitterTestApi
         * @author MinXue Sun
         */

//    @Test
//    public void testIndex() {
//        running(fakeApplication(), () -> {
//            Call action = routes.HomeController.index();
//            Http.RequestBuilder request = Helpers.fakeRequest(action);
//            Result result = route(fakeApplication(),request);
//            assertEquals(OK, result.status());
//        });
//    }
//
//        @Test
//        public void profile() {
//            Http.RequestBuilder request = new Http.RequestBuilder()
//                    .method(GET)
//                    .uri("/search");
//
//            Result result = route(app, request);
//            assertEquals(OK, result.status());
//        }

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






