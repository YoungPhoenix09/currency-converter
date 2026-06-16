FROM amazoncorretto:21
COPY . app
ENV SPRING_DATASOURCE_URL="jdbc:postgresql://dbhost:5432/postgres"
WORKDIR app
CMD ./gradlew bootRun
