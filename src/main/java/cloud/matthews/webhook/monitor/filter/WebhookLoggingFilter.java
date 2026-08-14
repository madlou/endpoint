package cloud.matthews.webhook.monitor.filter;

import cloud.matthews.webhook.monitor.util.WebhookLogger;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Servlet filter that captures and logs all incoming webhook requests
 * including method, URL, headers, body, and response status code.
 */
public class WebhookLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(WebhookLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();
        String url = httpRequest.getRequestURL().toString();
        String queryString = httpRequest.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            url += "?" + queryString;
        }

        // Log request details
        logger.info("=== WEBHOOK REQUEST ===");
        logger.info("Timestamp: {}", new Date());
        logger.info("Method: {}", method);
        logger.info("URL: {}", url);
        logger.info("Remote Address: {}", httpRequest.getRemoteAddr());
        logger.info("Protocol: {}", httpRequest.getProtocol());

        // Log all headers
        logger.info("--- Headers ---");
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpRequest.getHeader(headerName);
            logger.info("{}: {}", headerName, headerValue);
        }

        // Log content type and length
        logger.info("Content-Type: {}", httpRequest.getContentType());
        logger.info("Content-Length: {}", httpRequest.getContentLength());

        // Log request body for POST/PUT/PATCH requests
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) ||
                "PATCH".equalsIgnoreCase(method)) {
            WebhookLogger.logRequestBody(httpRequest);
        }

        logger.info("=== END REQUEST ===\n");

        // Store start time to calculate response time
        long startTime = System.currentTimeMillis();

        // Create a wrapper to capture the response status
        WebhookLoggingResponseWrapper responseWrapper = new WebhookLoggingResponseWrapper(httpResponse);

        // Continue the filter chain
        chain.doFilter(request, responseWrapper);

        // Get the status code after the request is processed
        int statusCode = responseWrapper.getStatus();
        if (statusCode == HttpServletResponse.SC_OK) {
            statusCode = 200; // Default if not set
        }

        // Log response details
        long duration = System.currentTimeMillis() - startTime;
        logger.info("=== WEBHOOK RESPONSE ===");
        logger.info("Status Code: {}", statusCode);
        logger.info("Duration: {} ms", duration);
        logger.info("=== END RESPONSE ===\n");
    }
}
