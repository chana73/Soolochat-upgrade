FROM openjdk:17
LABEL maintainer="soolo"
VOLUME /main-app
ADD build/libs/SooloChat-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5501
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb+srv://soolo:soolo@cluster0.nnm1vfo.mongodb.net/sooloChat?retryWrites=true&w=majority","-jar","app.jar"]