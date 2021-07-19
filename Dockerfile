# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Add Maintainer Info
LABEL maintainer="little3201@gmail.com"

# Add param to use in anywhere
ARG JAR_NAME=leafage-gateway
ARG VERSION=0.1.0

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8763 available to the world outside this container
EXPOSE 8760

# The application's jar file
ARG JAR_FILE=target/${JAR_NAME}-${VERSION}.jar

# Add the application's jar to the container
ADD ${JAR_FILE} .

# Run the jar file
ENTRYPOINT ["java","-jar","leafage-gateway-0.1.0.jar","--spring.profiles.active=dev"]
