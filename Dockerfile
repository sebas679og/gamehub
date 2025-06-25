FROM    eclipse-temurin:17-jdk

ARG     BUILD_DATE=unknown
ARG     BUILD_VERSION=unknown
ARG     IMAGE_DESCRIPTION=unknown
ARG     IMAGE_NAME=unknown
ARG     UID=1010
ARG     GID=1010

ENV     MAVEN_HOME=/usr/share/maven
ENV     MAVEN_CONFIG=/home/group4/.m2

LABEL   group4.game-hub-api.build-date=$BUILD_DATE
LABEL   group4.game-hub-api.name=$IMAGE_NAME
LABEL   group4.game-hub-api.description=$IMAGE_DESCRIPTION
LABEL   group4.game-hub-api.base.image="eclipse-temurin:17-jdk"
LABEL   group4.game-hub-api.version=$BUILD_VERSION
LABEL   maintainer="GROUP4"

RUN     apt-get update \
        && apt-get install -y --no-install-recommends \
        curl libev4 libev-dev bash procps tar openssh-client \
        && groupadd -g "${GID}" group4 \
        && useradd --create-home --no-log-init -u "${UID}" -g group4 group4 \
        && apt-get clean \
        && rm -rf /var/lib/apt/lists/*

COPY    --from=maven:3.9.9-eclipse-temurin-17 ${MAVEN_HOME} ${MAVEN_HOME}
COPY    --from=maven:3.9.9-eclipse-temurin-17 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY    --from=maven:3.9.9-eclipse-temurin-17 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

RUN     ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

WORKDIR /opt/app

COPY    --chown=group4 . .

RUN     mvn dependency:go-offline && \
        mvn clean package -DskipTests

USER    group4

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]