
package demo.bank;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;

public class WebSocket2KafkaServer extends AbstractVerticle {

  private static final Logger LOGGER = Logger.getLogger("WebSocket2KafkaServer");

  @Override
  public void start() {
      final Router router = Router.router(vertx);

      LOGGER.info("WebSocket2KafkaServer Start ....");
      vertx.createHttpServer()
      .requestHandler(router)
      .webSocketHandler(this::handleWebSocket)
      .listen(8080, "localhost");

      LOGGER.debug("WebSocket2KafkaServer Started ....");
      
  }

  private void handleWebSocket(final ServerWebSocket webSocket) {
    final EventBus eventBus = vertx.eventBus();

    webSocket.handler(buffer -> {
      LOGGER.debug("WebSocket handler from ");
      LOGGER.debug("TextHandlerID WebSocket ");

      final JsonObject message = buffer.toJsonObject();
         eventBus.send(webSocket.textHandlerID(), message);
    });

    webSocket.endHandler(ended -> {
      LOGGER.debug("Producer WebSocket closed from {}");
    });
    webSocket.exceptionHandler(err -> {
      LOGGER.debug("Producer WebSocket error", err);
    });

  }

}