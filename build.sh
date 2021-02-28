#!/usr/bin/env bash
docker-compose down
(cd ui && npm install && npm run build)
mvn clean install com.google.cloud.tools:jib-maven-plugin:dockerBuild