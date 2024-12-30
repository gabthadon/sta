# Use a base image with Java 8 and Maven pre-installed
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven project file(s)
COPY pom.xml .

# Build an empty project to resolve dependencies and create initial project structure
RUN mvn -B dependency:resolve-plugins dependency:resolve dependency:go-offline

# Copy the source code
COPY . .
# Print the contents of the current directory (for debugging purposes)
RUN ls -al

RUN echo "Copied from Maven"

# Build the application without running tests
RUN mvn clean package -DskipTests

# Use a minimal base image for running the Spring Boot application
FROM openjdk:17-jdk
#TEST
# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder image
COPY --from=builder /app/target/sta-service.jar .
# Print the contents of the current directory (for debugging purposes)
RUN ls -al
RUN echo "Copied from Builder"
# Expose the port on which the Spring Boot application will listen
EXPOSE 7001

# Set the command to run the Spring Boot application
CMD ["java", "-jar", "sta-service.jar"]
#docker run -p 9004:9004 softnet/user-service
