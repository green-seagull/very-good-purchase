# very-good-purchase
Example camel project that exposes a REST API to store requests in a CSV

# Manual test
```
curl --request PUT -H "Content-Type: application/json" \
  --data '{"amountDollars": 12.50, "title": "Renovators guide", "date": "2021-01-01", "purchaseType": "Book"}' \
   "http://localhost:8080/api/purchases"
```

# Build docker image
To build to a Docker daemon, use:
```
mvn compile com.google.cloud.tools:jib-maven-plugin:dockerBuild
```
The prior uses jib to create the image.
