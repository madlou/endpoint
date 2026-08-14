# Webhook Monitor

A Spring Boot application that monitors port 80 for incoming webhook events and logs all HTTP calls with their status codes to a log file.

## Features

- Monitors port 8080 for all incoming HTTP requests
- Logs all request details: method, URL, headers, body, timestamp
- Logs all response status codes
- Separates webhook logs and application logs into different files
- Automatic log rotation by size and date
- REST API endpoints for webhook events

## Project Structure

```
src/main/java/com/webhook/monitor/
├── WebhookMonitorApplication.java    # Main application class
├── config/
│   └── WebhookFilterConfig.java      # Filter configuration
├── controller/
│   └── WebhookController.java        # Webhook request handler
├── filter/
│   ├── WebhookLoggingFilter.java     # Logs all incoming requests/responses
│   └── WebhookLoggingResponseWrapper.java  # Captures response status codes
└── util/
    └── WebhookLogger.java            # Request body logging utility
```

## Log Files

- `logs/webhook-monitor.log` - All webhook request/response details
- `logs/application.log` - General application logs

## Building and Running

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Build

```bash
mvn clean package
```

### Run

```bash
# Using Maven
mvn spring-boot:run

# Or using the JAR
java -jar target/webhook-monitor-1.0.0.jar
```

### Run with sudo (for port 80)

Since port 80 is a privileged port, you may need to run with sudo:

```bash
java -jar target/webhook-monitor-1.0.0.jar
```

Or you can specify the port to monitor:

```bash
java -jar target/webhook-monitor-1.0.0.jar --server.port=9090
```

## Testing

Send a test webhook request:

```bash
# POST request
curl -X POST http://localhost/webhook \
  -H "Content-Type: application/json" \
  -d '{"event": "test", "data": "hello"}'

# GET request
curl http://localhost/health

# PUT request
curl -X PUT http://localhost/test \
  -H "Content-Type: application/json" \
  -d '{"action": "update"}'
```

## Configuration

Edit `src/main/resources/application.properties` to customize:

- `server.port` - Change the port (default: 80)
- `logging.config` - Point to a custom logback configuration
- Log file locations in `logback-spring.xml`

## License

MIT
