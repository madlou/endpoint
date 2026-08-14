package cloud.matthews.webhook.monitor.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Utility class for logging webhook request bodies.
 */
public class WebhookLogger {

    private static final Logger logger = LoggerFactory.getLogger(WebhookLogger.class);

    /**
     * Log the request body from a servlet request.
     * Note: This reads the input stream, which may not work with all servlet containers
     * if the stream has already been consumed.
     */
    public static void logRequestBody(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String body = requestBody.toString();
            if (body.isEmpty()) {
                logger.info("Request Body: (empty)");
            } else if (body.length() > 10000) {
                logger.info("Request Body: {}... (truncated, total length: {})",
                        body.substring(0, 10000), body.length());
            } else {
                logger.info("Request Body: {}", body);
            }
        } catch (IOException e) {
            logger.warn("Failed to read request body: {}", e.getMessage());
        }
    }
}
