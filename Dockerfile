FROM azul/zulu-openjdk:17.0.9-jre
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]


#docker run -d --network=mynetwork -p 8080:8080 --name=da-task -e SPRING_DATASOURCE_URL=jdbc:mysql://172.17.0.2:3306/da-task -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=password da-task-image
