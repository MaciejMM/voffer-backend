# Freight Application

## Description
Freight Application is a Spring Boot-based application that manages several truck offers on Timocon, Teleroute and Trans.eu platforms.
The project uses Java, Kotlin, and Gradle.

## Technologies
- Java 21
- Kotlin
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Web
- Spring WebFlux
- Okta Spring Boot Starter
- Kinde Autg
- PostgreSQL
- JUnit 5

## Project Structure
- `src/main/java/com/example/freight` - application source code
- `src/test/java/com/example/freight` - unit tests

## Configuration
Application requires creating **credentials.yaml** inside resources folder with following content:
```yaml
database:
  password: # database password
  username: # database username

auth0:
  client_id: # client id for JWT token
  client_secret: # client secret for JWT token
  audience: # audience for JWT token
  grant_type: client_credentials
  issuer:  # issuer for JWT token
jwt:
  secret:  # secret for JWT token
  expirationTime: # expiration time for JWT token in seconds
```

### Gradle
The `build.gradle.kts` file contains the Gradle configuration, including dependencies and build settings.

### Database
The application uses a PostgreSQL database. Ensure that the database is properly configured and accessible.

## Running the Application
To run the application, follow these steps:
1. Clone the repository: `git clone https://github.com/MaciejMM/voffer-backend`
2. Navigate to the project directory: `cd voffer-backend`
3. Run the application: `./gradlew bootRun`

## Testing
To run unit tests, execute:
```sh
./gradlew test
