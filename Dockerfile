FROM openjdk:11
COPY ./build/libs/reactive-stream.jar reactive-stream.jar
EXPOSE 9090
CMD ["java","-jar","reactive-stream.jar"]