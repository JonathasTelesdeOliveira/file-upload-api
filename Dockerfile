FROM eclipse-temurin:21-jre

WORKDIR /app

COPY  target/transaction_input_api-0.0.1-SNAPSHOT.jar /app/transaction_input_api.jar

EXPOSE 8081


CMD ["java", "-jar", "/app/transaction_input_api.jar"]

