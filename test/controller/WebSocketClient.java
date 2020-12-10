package controller;

import play.shaded.ahc.org.asynchttpclient.AsyncHttpClient;
import play.shaded.ahc.org.asynchttpclient.BoundRequestBuilder;
import play.shaded.ahc.org.asynchttpclient.ListenableFuture;
import play.shaded.ahc.org.asynchttpclient.netty.ws.NettyWebSocket;
import play.shaded.ahc.org.asynchttpclient.ws.WebSocket;
import play.shaded.ahc.org.asynchttpclient.ws.WebSocketListener;
import play.shaded.ahc.org.asynchttpclient.ws.WebSocketUpgradeHandler;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
/**
 * Test HomeController
 * @author Chenwen Wang
 */
public class WebSocketClient {

    private AsyncHttpClient client;

    public WebSocketClient(AsyncHttpClient c) {
        this.client = c;
    }

    /**
     * B
     * @param url
     * @param listener
     * @param origin
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public CompletableFuture<NettyWebSocket> call(String url, String origin, WebSocketListener listener) throws ExecutionException, InterruptedException {
        final BoundRequestBuilder requestBuilder = client.prepareGet(url).addHeader("Origin", origin);

        final WebSocketUpgradeHandler handler = new WebSocketUpgradeHandler.Builder().addWebSocketListener(listener).build();
        final ListenableFuture<NettyWebSocket> future = requestBuilder.<NettyWebSocket>execute(handler);
        return future.toCompletableFuture();
    }

    /**
     * This inner class is used to handle web socket connection events once a connection is established to the server.
     */
    static class LoggingListener implements WebSocketListener {
        private final Consumer<String> onMessageCallback;

        public LoggingListener(Consumer<String> onMessageCallback) {
            this.onMessageCallback = onMessageCallback;
        }

        private Logger logger = org.slf4j.LoggerFactory.getLogger(LoggingListener.class);

        private Throwable throwableFound = null;

        public Throwable getThrowable() {
            return throwableFound;
        }

          /**
          * Gets called on opening the web socket connection.
          */
        public void onOpen(WebSocket websocket) {
            // do nothing
        }
        /**
         * Gets called before closing the connection.
         */
        @Override
        public void onClose(WebSocket webSocket, int i, String s) {
            // do nothing
        }
        /**
         * Handle the error exception
         */
        public void onError(Throwable t) {
            // do nothing
            throwableFound = t;
        }
        /**
         * Invoked when a text frame is received.
         */
        @Override
        public void onTextFrame(String payload, boolean finalFragment, int rsv) {
            //logger.info("onMessage: s = " + s);
            onMessageCallback.accept(payload);
        }
    }

}