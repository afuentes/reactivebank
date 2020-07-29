
package demo.reative.bank;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;
import io.vertx.core.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

  @Override
  public void start() {
      final Router router = Router.router(vertx);

      vertx.createHttpServer()
      .requestHandler(router)
      .webSocketHandler(this::handleWebSocket)
      .listen(8080, "localhost");

      logger.debug("Started server");
      
  }

  private void handleWebSocket(final ServerWebSocket webSocket) {
    final EventBus eventBus = vertx.eventBus();

    webSocket.handler(buffer -> {
      final JsonObject message = buffer.toJsonObject();
        eventBus.send( null, message);
    });

  }

}