# record

This is the Java Spring Boot back-end REST API service for the application. 

This back-end uses a Model-Service-Controller structure for every dedicated module of code, where the Model would handle data structure, Service would handle business logic, and Controller would handle URL mapping and API Request-Response options. 

For deployment, this service is built into a JAR file and served from within a container. 

## Commands

### Running the Service

Run the command `./mvnw spring-boot:run`

### Building the Service into a JAR File

Run the command `./mvnw clean install`