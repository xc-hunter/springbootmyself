FROM openjdk:8-jdk-alpine
VOLUME /tmp /home/log
COPY build/libs/*.jar /home/app.jar
WORKDIR /home
EXPOSE 8087
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar ${0} ${@}"]
