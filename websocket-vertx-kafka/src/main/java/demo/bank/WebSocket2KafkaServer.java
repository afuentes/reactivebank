
package demo.bank;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocket2KafkaServer extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(WebSocket2KafkaServer.class);


  @Override
  public void start() {

      final Router router = Router.router(vertx);

      logger.info("WebSocket2KafkaServer Start ....");
      vertx.createHttpServer()
      .requestHandler(router)
      .webSocketHandler(this::handleWebSocket)
      .listen(8080, "localhost");

      logger.debug("WebSocket2KafkaServer Started ....");
      
  }

  private void handleWebSocket(final ServerWebSocket webSocket) {
    final EventBus eventBus = vertx.eventBus();

    webSocket.handler(buffer -> {
      logger.debug("WebSocket handler from ");
      logger.debug("TextHandlerID WebSocket ");

      final JsonObject message = buffer.toJsonObject();
         eventBus.send(webSocket.textHandlerID(), message);
    });

    webSocket.endHandler(ended -> {
      logger.debug("Producer WebSocket closed from {}");
    });
    webSocket.exceptionHandler(err -> {
      logger.debug("Producer WebSocket error", err);
    });

  }

}