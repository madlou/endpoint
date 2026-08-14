package cloud.matthews.webhook.monitor.controller;

import cloud.matthews.webhook.monitor.util.WebhookLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Controller to handle incoming webhook events.
 * All requests are logged by the WebhookLoggingFilter.
 * This controller provides a catch-all endpoint for webhook events.
 */
@RestController
@RequestMapping("/")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    /**
     * Catch-all endpoint for all HTTP methods.
     * This handles any incoming webhook request that hasn't been matched by other endpoints.
     */
    @RequestMapping(value = "/", method = {
            RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS,
            RequestMethod.HEAD
    })
    public ResponseEntity<Map<String, String>> handleWebhook(
            HttpServletRequest request) {

        String method = request.getMethod();
        String url = request.getRequestURL().toString();

        logger.info("Webhook received: {} {}", method, url);

        // Return appropriate response based on HTTP method
        if ("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method)) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "ok");
            response.put("message", "Webhook monitor is running");
            response.put("timestamp", new Date().toString());
            return ResponseEntity.ok(response);
        }

        // For POST, PUT, PATCH, DELETE, OPTIONS - return 200 OK
        Map<String, String> response = new HashMap<>();
        response.put("status", "received");
        response.put("method", method);
        response.put("timestamp", new Date().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "healthy");
        response.put("timestamp", new Date().toString());
        return ResponseEntity.ok(response);
    }
}
