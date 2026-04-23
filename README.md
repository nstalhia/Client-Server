Name: Janefa Jeba
Student ID: w2080916


## API Overview

This Smart Campus API is a RESTful web service built using JAX-RS and deployed on Apache Tomcat.

It manages:
- Rooms
- Sensors linked to rooms
- Sensor readings as a nested sub-resource

The API demonstrates core REST principles including:
- Resource-based design
- Query parameter filtering
- Sub-resource locators
- Exception handling with custom mappers
- In-memory data storage using HashMap and ArrayList

## How to Build and Run

### Prerequisites
- Java 17
- Apache Tomcat (via NetBeans)
- NetBeans IDE

### Steps

1. Open the project in NetBeans
2. Right-click project → Clean and Build
3. Deploy using Tomcat server in NetBeans
4. Ensure server starts successfully
5. Open browser:

http://localhost:8080/api/v1

## Sample CURL Commands

### Create Room
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1"}'

### Get Rooms
curl http://localhost:8080/api/v1/rooms

### Create Sensor
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S1","roomId":"R1","type":"CO2"}'

### Filter Sensors
curl http://localhost:8080/api/v1/sensors?type=CO2

### Add Reading
curl -X POST http://localhost:8080/api/v1/sensors/S1/readings \
-H "Content-Type: application/json" \
-d '{"value":12.5,"timestamp":"now"}'

## Report: 

Name: Janefa Jeba
Student ID: w2080916

Part 1: Service Architecture & Setup

1.1 Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a
new instance instantiated for every incoming request, or does the runtime treat it as a
singleton? Elaborate on how this architectural decision impacts the way you manage and
synchronize your in-memory data structures (maps/lists) to prevent data loss or race con-
ditions.

1.1 Answer: In JAX-RS, resource classes may be created per request or managed in a way that behaves 
similarly to a singleton, depending on the runtime implementation. Because of this, 
instance variables cannot be reliably used to store application state.
Instead, static data structures such as HashMap and ArrayList are used to ensure that 
data persists across multiple requests. This ensures that room and sensor data remain 
available throughout the lifecycle of the application. However, since multiple requests
 may be processed concurrently, there is a potential risk of race conditions when modifying 
shared data. In larger systems, thread-safe collections or proper synchronisation would be required, 
but for this coursework, static collections are sufficient.

1.2 Question: Why is the provision of ”Hypermedia” (links and navigation within responses)
considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach
benefit client developers compared to static documentation?

1.2 Answer: Hypermedia, or HATEOAS, is a REST principle where API responses include links to related resources, 
allowing clients to navigate the API dynamically without prior knowledge of all endpoints.
This improves flexibility because clients are not tightly coupled to fixed URL structures. 
It also makes the API easier to extend, since new functionality can be exposed through links rather than 
requiring changes to client-side documentation or hardcoded paths.

Part 2: Room Management 

2.1 Question: When returning a list of rooms, what are the implications of returning only
IDs versus returning the full room objects? Consider network bandwidth and client side
processing.

2.1 Answer: Returning only resource identifiers reduces payload size and improves network efficiency, especially in large-scale systems. 
However, it requires clients to make additional requests to retrieve full resource details.Returning full objects simplifies client-side development 
by providing all necessary information in a single response, but it increases response size and bandwidth usage. 
The choice depends on performance requirements and system design priorities.

2.2 Question: Is the DELETE operation idempotent in your implementation? Provide a detailed
justification by describing what happens if a client mistakenly sends the exact same DELETE
request for a room multiple times.

2.2 Answer: The DELETE method is idempotent because multiple identical requests produce the same result. 
Once a resource has been deleted, subsequent DELETE requests will not change the system state.
Repeated DELETE requests typically return either a success response or a not-found response,
 but the overall system state remains unchanged. This is important in distributed systems where requests may be retried.

Part 3: Sensor Operations & Linking 

3.1 Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on
the POST method. Explain the technical consequences if a client attempts to send data in
a different format, such as text/plain or application/xml. How does JAX-RS handle this
mismatch?

3.1 Answer: When a method is annotated with @Consumes(MediaType.APPLICATION_JSON), the API expects JSON-formatted input. 
If a client sends data in another format such as text/plain or application/xml, JAX-RS will reject the request before it reaches the resource method.
In such cases, the framework returns a 415 Unsupported Media Type response. This ensures that only valid and expected data formats are processed.

3.2: Question: You implemented this filtering using @QueryParam. Contrast this with an alterna-
tive design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why
is the query parameter approach generally considered superior for filtering and searching
collections?

3.2 Answer: Query parameters are more appropriate for filtering because they allow flexible and optional criteria 
without changing the endpoint structure. For example, /sensors?type=CO2 allows filtering while keeping the base resource consistent. 
In contrast, using path parameters such as /sensors/type/CO2 creates a rigid structure that does not scale well when multiple filters are required.
Therefore, query parameters are generally preferred for filtering and searching in RESTful APIs.

Part 4: Deep Nesting with Sub - Resources

4.1 Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How
does delegating logic to separate classes help manage complexity in large APIs compared
to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive con-
troller class?

4.1 Answer: The Sub-Resource Locator pattern improves modularity by delegating nested resource responsibilities to separate classes 
rather than handling all endpoints within a single controller.In this coursework, sensor readings are handled by a separate SensorReadingResource, 
which is returned from the SensorResource. This separation ensures that sensor management and sensor reading logic are clearly isolated.
This approach improves maintainability because each class has a single responsibility, making the code easier to understand and debug. 
It also improves scalability, as additional nested resources can be added without increasing the complexity of a single large controller.
Compared to defining all nested routes (such as sensors/{id}/readings/{rid}) in one class, this pattern reduces coupling and 
keeps the API structure clean and modular.

Part 5: Advanced Error Handling, Exception Mapping & Logging

5.1 Question: Why is HTTP 422 often considered more semantically accurate than a standard
404 when the issue is a missing reference inside a valid JSON payload?

5.1 Answer: HTTP 422 is more semantically accurate than 404 because the request itself is valid and the endpoint exists, 
but the data inside the request is logically incorrect. In this case, a sensor is being created with a roomId that does 
not exist in the system. A 404 error would normally indicate that a resource or endpoint cannot be found, whereas 
422 indicates that the server understands the request but cannot process it due to semantic issues in the data.

5.2 Question: From a cybersecurity standpoint, explain the risks associated with exposing
internal Java stack traces to external API consumers. What specific information could an
attacker gather from such a trace?

5.2 Answer: From a cybersecurity perspective, exposing stack traces is dangerous because it reveals internal 
implementation details of the application. This may include class names, file paths, framework versions, and internal logic flow.
An attacker can use this information to identify vulnerabilities and plan targeted attacks against the system. 
Therefore, stack traces should never be exposed to external users.

5.3 Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like
logging, rather than manually inserting Logger.info() statements inside every single re-
source method?

5.3 Answer: Using JAX-RS filters is more efficient than placing logging statements in every resource method because 
logging is a cross-cutting concern that applies to all endpoints.Filters centralise logging logic in one place, ensuring 
consistency across the API. This reduces code duplication, improves maintainability, and ensures that all 
requests and responses are logged automatically without relying on manual logging in each method.
