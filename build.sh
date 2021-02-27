#!/usr/bin/env bash
(cd ui && npm install && npm run build)
mvn install
mvn compile com.google.cloud.tools:jib-maven-plugin:dockerBuild