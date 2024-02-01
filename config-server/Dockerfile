# Create a runtime environment
FROM openjdk:8

# Define build-time arguments
ARG JAR_FILE=target/*.jar
ARG PORT=9296
ARG EUREKA_SERVER_ADDRESS="http://host.docker.internal:8762/eureka"

# Set environment variable for the Eureka server address
ENV EUREKA_SERVER_ADDRESS=${EUREKA_SERVER_ADDRESS}

# Copy the built JAR file from the previous stage
COPY ${JAR_FILE} /entry.jar

# Specify the entry point to run the Java application
ENTRYPOINT ["java", "-jar", "/entry.jar"]

# Expose the specified port
EXPOSE ${PORT}
