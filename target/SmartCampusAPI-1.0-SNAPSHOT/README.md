Part 1: Setup & Discovery
This project is a REST API built using JAX-RS (Jersey) and deployed on Apache Tomcat 9 using Maven.
All endpoints are grouped under: http://localhost:8080/api/v1
This is set using @ApplicationPath("/api/v1"), which defines the base path for the whole API.
Instead of using a database, the project stores data in in-memory HashMaps, meaning everything resets when the server restarts. This was done to match the coursework requirements.

Part 1.2 Discovery Endpoint
The base endpoint (GET /api/v1) acts as a simple “entry point” to the API. It returns basic information about what resources are available.
This helps clients understand how to use the API without needing external documentation, making it slightly more self-explanatory and aligned with REST ideas like discoverability.

Part 2: Room Management