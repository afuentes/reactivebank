
package demo.reative.bank;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebSocket2KafkaServer extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(WebSocket2KafkaServer.class);

  @Override
  public void start() {
      final Router router = Router.router(vertx);

      vertx.createHttpServer()
      .requestHandler(router)
      .webSocketHandler(this::handleWebSocket)
      .listen(8080, "localhost");
      
  }

  private void handleWebSocket(final ServerWebSocket webSocket) {
    final EventBus eventBus = vertx.eventBus();

    webSocket.handler(buffer -> {
      final JsonObject message = buffer.toJsonObject();
        eventBus.send(null, message);
    });

    webSocket.endHandler(ended -> {
      logger.info("Producer WebSocket closed from {}", webSocket.remoteAddress().host());
      eventBus.send(Main.PERIODIC_PRODUCER_ADDRESS, new JsonObject().put(ACTION, STOP_ACTION));
      consumer.unregister();
    });
    webSocket.exceptionHandler(err -> {
      logger.error("Producer WebSocket error", err);
      eventBus.send(Main.PERIODIC_PRODUCER_ADDRESS, new JsonObject().put(ACTION, STOP_ACTION));
      consumer.unregister();
    });

  }

}