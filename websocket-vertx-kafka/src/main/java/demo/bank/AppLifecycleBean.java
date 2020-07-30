package demo.bank;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = Logger.getLogger("AppLifecycleBean");

    @Inject Vertx vertx;

    void onStart(@Observes StartupEvent ev) {               
        LOGGER.info("The application is starting...");
        final Router router = Router.router(vertx);

        LOGGER.info("WebSocket2KafkaServer Start ....");
        vertx.createHttpServer()
        .requestHandler(router)
        .webSocketHandler(this::handleWebSocket)
        .listen(8080, "localhost");
  
        LOGGER.info("WebSocket2KafkaServer Started ....");
    }

    private void handleWebSocket(final ServerWebSocket webSocket) {
        final EventBus eventBus = vertx.eventBus();
    
        webSocket.handler(buffer -> {
          LOGGER.info("WebSocket handler from ");
          LOGGER.info("TextHandlerID WebSocket ");
    
          final JsonObject message = buffer.toJsonObject();
             eventBus.send(webSocket.textHandlerID(), message);
        });
    
        webSocket.endHandler(ended -> {
          LOGGER.info("Producer WebSocket closed from {}");
        });
        webSocket.exceptionHandler(err -> {
          LOGGER.info("Producer WebSocket error", err);
        });
    
      }

    void onStop(@Observes ShutdownEvent ev) {               
        LOGGER.info("The application is stopping...");
    }

    
}