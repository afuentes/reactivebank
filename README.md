
# Reactive Bank

Event Driven Architecture

## Prepared Enviroment

1- Download Tools https://quarkus.io/get-started/

* VSCode from https://code.visualstudio.com/Download
* JDK    from https://adoptopenjdk.net/ ( Download for macOS x64 - Java version: 11.0.8, vendor: AdoptOpenJDK  )
* Maven from https://maven.apache.org/
* Kafka from https://kafka.apache.org/downloads

2- Install maven + kafka  

```shell
$ cd $HOME/toolset
$ unzip apache-maven-3.6.3-bin.zip
$ tar xvfz kafka_2.12-2.3.0.tar.gz  
```
Note : Java was installed using .dmg file
 
3- Enviroment Profile .bash_profile 

```shell
# TOOLSET 
export TOOLSET=$HOME/toolset
export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8-openj9.jdk/Contents/Home/ 
export MAVEN_HOME=${TOOLSET}/apache-maven-3.6.3
export KAFKA_HOME=${TOOLSET}/kafka_2.12-2.3.0
export KAFKA_CONFIG=${TOOLSET}/kafka_2.12-2.3.0/config
export PATH=${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${KAFKA_HOME}/bin:${PATH}
```

## Startup Enviroment

2. Startup Kafka Cluster 

```shell
$ zookeeper-server-start.sh $KAFKA_CONFIG/zookeeper.properties
$ kafka-server-start.sh $KAFKA_CONFIG/server.properties
```
Note:  open different terminal for each process 

3. Download Code & Run  

```shell
$ cd $HOME
$ git clone  https://github.com/afuentes/realtimebusiness.git
$ cd  realtimebusiness/kafka-quickstart
$ mvn compile quarkus:dev
```

## Architecture  

4. Comming Soon ... 

### Reference

* https://kafka.apache.org/
* https://quarkus.io/guides/getting-started-reactive
* https://quarkus.io/guides
* https://quarkus.io/guides/kafka-guide



