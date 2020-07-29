
package demo.reative.bank;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;
import io.vertx.core.json.JsonObject;


public class WebSocketServer extends AbstractVerticle {

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
        eventBus.send( null, message);
    });

  }

}