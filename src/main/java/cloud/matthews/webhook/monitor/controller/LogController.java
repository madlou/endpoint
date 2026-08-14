package cloud.matthews.webhook.monitor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/log")
public class LogController {

    private static final String LOG_FILE = "logs/webhook-monitor.log";
    private static final int LINES = 100;

    @GetMapping("/tail")
    public ResponseEntity<List<String>> tailLog() {
        Path logPath = Paths.get(LOG_FILE);
        if (!Files.exists(logPath)) {
            return ResponseEntity.ok(List.of("Log file not found."));
        }
        try {
            List<String> allLines = Files.readAllLines(logPath);
            int fromIndex = Math.max(0, allLines.size() - LINES);
            List<String> tail = allLines.subList(fromIndex, allLines.size());
            // Reverse so newest lines appear at the top
            java.util.Collections.reverse(tail);
            return ResponseEntity.ok(tail);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(List.of("Error reading log file: " + e.getMessage()));
        }
    }
}
