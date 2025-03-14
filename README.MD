# Gen AI Training

## Overview
Gen AI Training is a Spring Boot application designed to integrate with OpenAI services using the Semantic Kernel framework. This project enables AI-powered chat interactions, leveraging OpenAI APIs and supporting technologies such as Qdrant, gRPC, and Protobuf.

## Project Structure
```
gen_ai_training/
│── src/
│   ├── main/
│   │   ├── java/com/epam/training/gen/ai/
│   │   │   ├── config/
│   │   │   │   ├── OpenAIClientConfig.java
│   │   │   │   ├── SwaggerConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── ChatBotController.java
│   │   │   ├── dto/
│   │   │   │   ├── ChatResponse.java
│   │   │   ├── service/
│   │   │   │   ├── AIProcessingService.java
│   ├── resources/
│   │   ├── application.yaml
│   ├── test/java/com/epam/training/gen/ai/service/
│   │   ├── AIProcessingServiceTest.java
│── pom.xml
│── README.md
```

## Dependencies
This project relies on the following key dependencies:
- Spring Boot 3.2.8
- Semantic Kernel (Microsoft) 1.4.2
- Qdrant Client 1.11.0
- Azure AI OpenAI 1.0.0-beta.11
- gRPC 1.68.0
- Protobuf 4.29.3
- Lombok 1.18.30
- SpringDoc OpenAPI 2.5.0

## Configuration
### Environment Variables
The application requires the following environment variables:
- `CLIENT_OPENAI_KEY`: API key for OpenAI access
- `CLIENT_OPENAI_ENDPOINT`: OpenAI endpoint URL


Configuration settings are managed in `application.yaml`:
```yaml
application-name: gen_ai_training
client:
  openai:
    key: ${CLIENT_OPENAI_KEY}
    endpoint: ${CLIENT_OPENAI_ENDPOINT}
    deployment-name: gpt-35-turbo
```

## Running the Application
### Prerequisites
- Java 21
- Maven

### Build and Run
To build and run the application, execute the following commands:
```sh
mvn clean install
mvn spring-boot:run
```

## API Endpoints
### AI Chat Processing
#### POST `/api/chat`
Processes a user's input via OpenAI and returns an AI-generated response.
##### Request Parameters:
- `input` (String) – The user’s message
##### Response:
```json
{
  "input": "Hello AI",
  "response": "Hello! How can I assist you today?"
}
```

## Testing
Unit tests for AI processing are included in `AIProcessingServiceTest.java`. Run tests using:
```sh
mvn test
```

