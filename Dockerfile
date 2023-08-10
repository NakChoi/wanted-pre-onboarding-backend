FROM adoptopenjdk/openjdk11 AS builder

WORKDIR /app

ARG JWT_SECRET_KEY=17c69fb1-e009-4054-a04a-7dda84e9269d-a6eb1577-3e43-4d3d-834d-8ded2c59a310-a2c2d3a7-b51c-448c-a5cb-a7fb66807d7a
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}
ENV MYSQL_URL=${MYSQL_URL}
ENV MYSQL_URL=${MYSQL_PASSWORD}

COPY assignment .

RUN chmod +x ./gradlew

RUN ./gradlew build

FROM adoptopenjdk/openjdk11

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

CMD ["sh", "-c", "java", "-Dspring.profiles.active=server" ,"-jar", "app.jar"]
