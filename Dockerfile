FROM openjdk:11
VOLUME /tmp
EXPOSE 8091
COPY target/loan-repay-0.0.1.jar loanrepay.jar
ENTRYPOINT ["java", "-jar","loanrepay.jar"]