# very-good-purchase
Example camel project that exposes a REST API to store requests in a CSV

# Build docker image
To build to a Docker daemon, use:
```
mvn compile com.google.cloud.tools:jib-maven-plugin:dockerBuild
```
The prior uses jib to create the image.
