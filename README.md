# POC GraphQL Spring DGS Application

## Implementation
* Spring Boot `2.7.5`
* Netflix DGS framework


## Features
* Security
* Subscriptions with WebSockets and SSE


## WebSockets and SSE Subscriptions
* In order to use WebSockets use `graphql-dgs-subscriptions-websockets-autoconfigure` in pom.xml
* In order to use SSE use `graphql-dgs-subscriptions-sse-autoconfigure` in pom.xml


## Issues
* `graphql-dgs-platform-dependencies` version bigger than `5.1` causes problem with subscriptions - they stop coming after few responses
* Websocket subscriptions don't work in GraphiQL but works in Playground
* SSE subscriptions can be tested through postman/curl

```
curl --location --request POST 'http://localhost:8080/subscriptions' \
--header 'Accept: application/json' \
--header 'Accept: text/event-stream' \
--header 'Cookie: JSESSIONID=5AD6380126CDE3872F6E99A525AE9A5A' \
--header 'Content-Type: application/json' \
--data-raw '{"query":"subscription {\n  showsSub {\n    title\n    releaseYear\n    author {\n      name\n    }\n  }\n}","variables":{}}'
```