FROM eclipse-temurin:25

ARG BUILD_DATE=unknown
ARG BUILD_VERSION=unknown
ARG IMAGE_DESCRIPTION=unknown
ARG IMAGE_NAME="GameHub"
ARG UID=1010
ARG GID=1010

ENV MAVEN_HOME=/usr/share/maven
ENV MAVEN_CONFIG=/home/sebas/.m2

LABEL sebas.gamehub.build-date=$BUILD_DATE
LABEL sebas.gamehub.name=$IMAGE_NAME
LABEL sebas.gamehub.description=$IMAGE_DESCRIPTION
LABEL sebas.gamehub.base.image="eclipse-temurin:25"
LABEL sebas.gamehub.version=$BUILD_VERSION
LABEL maintainer="Sebastian O."

RUN groupadd -g "${GID}" sebas \
    && useradd --create-home --no-log-init -u "${UID}" -g sebas sebas

COPY --from=maven:3.9.12-eclipse-temurin-25 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.12-eclipse-temurin-25 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.12-eclipse-temurin-25 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

USER sebas

WORKDIR /opt/app

COPY --chown=sebas:sebas . .

RUN mvn dependency:go-offline \
    && mvn clean package -DskipTests

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]

CMD ["java","-jar","target/gamehub.jar"]
