package demo.bank;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

      // Set vertx timeout to deal with slow DNS connections
      Vertx vertx = Vertx.vertx(
        new VertxOptions()
          .setWarningExceptionTime(10).setWarningExceptionTimeUnit(TimeUnit.SECONDS)
          .setMaxEventLoopExecuteTime(20).setMaxEventLoopExecuteTimeUnit((TimeUnit.SECONDS)));
  
      vertx.deployVerticle("demo.bank.WebSocket2KafkaServer");

      logger.info("Main Started ...");
  
    }


    
}