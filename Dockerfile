FROM openjdk:19
WORKDIR /taskService
COPY . /taskService
EXPOSE 80
RUN mvn clean package
CMD ["java", "-jar", "target/Task-Management-System-0.0.1-SNAPSHOT.jar"]