FROM maven:3.8.4-openjdk-11 as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
ADD . $HOME
RUN mvn clean package -DskipTests

FROM openjdk:11
RUN apt-get update \
 && DEBIAN_FRONTEND=noninteractive \
    apt-get install --no-install-recommends --assume-yes \
      postgresql-client
VOLUME /tmp
EXPOSE 8091
COPY --from=build /usr/app/target/loan-repay-0.0.1.jar loanrepay.jar
ENTRYPOINT ["java", "-jar","loanrepay.jar"]