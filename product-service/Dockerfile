# Create a runtime environment
FROM openjdk:8

# Define build-time arguments
ARG JAR_FILE=target/*.jar
ARG PORT=8085
ARG CONFIG_SERVER_URL="http://host.docker.internal:9296"
ARG EUREKA_SERVER_ADDRESS="http://host.docker.internal:8762/eureka"
ARG DB_HOST="host.docker.internal"

# Set the configuration and Eureka server addresses as environment variables
ENV CONFIG_SERVER_URL=${CONFIG_SERVER_ADDRESS}
ENV EUREKA_SERVER_ADDRESS=${EUREKA_SERVER_ADDRESS}
ENV DB_HOST=${DB_HOST}

# Copy the built JAR file from the previous stage
COPY ${JAR_FILE} /entry.jar

# Specify the entry point to run the Java application
ENTRYPOINT ["java", "-jar", "/entry.jar"]

# Expose the specified port
EXPOSE ${PORT}
