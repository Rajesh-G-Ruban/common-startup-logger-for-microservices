package com.app.startuplogger;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StartupLogger implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    private final Environment environment;

    @Value("${custom.configMessage:⚠️ Config message not set}")
    private String configMessage;

    @Value("${spring.application.name:UnknownService}")
    private String serviceName;

    public StartupLogger(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) {
        String[] profiles = environment.getActiveProfiles();
        String activeProfiles = String.join(", ", profiles);

        // Define a list of profiles we want to check against
        List<String> validProfiles = Arrays.asList("dev", "qa", "local");

        // Check if any profile in the array matches "dev" or "qa"
        boolean showEmojis = Arrays.stream(profiles)
                .anyMatch(p -> validProfiles.stream()
                        .anyMatch(valid -> valid.equalsIgnoreCase(p)));


        String fire = showEmojis ? "🔥" : "";
        String box = showEmojis ? "📦" : "";
        String puzzle = showEmojis ? "🧩" : "";

        // Log message
        logger.info("========================================");
        logger.info("  {} Service Name        : {}", puzzle, serviceName);
        logger.info("  {} Active Profiles     : {}", fire, activeProfiles);
        logger.info("  {} Config Server Msg   : {}", box, configMessage);
        logger.info("========================================");
    }
}
