FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache bash procps curl tar openssh-client

LABEL org.opencontainers.image.title="Apache Maven"
LABEL org.opencontainers.image.source="https://github.com/carlossg/docker-maven"
LABEL org.opencontainers.image.url="https://github.com/carlossg/docker-maven"
LABEL org.opencontainers.image.description="Apache Maven is a software project management and comprehension tool."

ENV MAVEN_HOME=/usr/share/maven
ENV MAVEN_CONFIG=/root/.m2

COPY --from=maven:3.9.9-eclipse-temurin-17 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.9-eclipse-temurin-17 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.9-eclipse-temurin-17 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

WORKDIR /opt/app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]

CMD ["mvn", "spring-boot:run"]