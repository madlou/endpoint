package cloud.matthews.webhook.monitor.filter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

/**
 * Wrapper for HttpServletResponse to capture the status code
 * before the response is committed.
 */
public class WebhookLoggingResponseWrapper extends HttpServletResponseWrapper {

    private int status = 200;

    public WebhookLoggingResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void setStatus(int sc) {
        this.status = sc;
        super.setStatus(sc);
    }

    public int getStatus() {
        return status;
    }
}
