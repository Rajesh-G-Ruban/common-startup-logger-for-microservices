package com.app.startuplogger;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


@Component
public class StartupLogger implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    private final Environment environment;
    private final StartupInfo startupInfo;

    @Value("${server.port}")
    private int serverPort;


    public StartupLogger(Environment environment, StartupInfo startupInfo) {
        this.environment = environment;
        this.startupInfo = startupInfo;
    }

    @Override
    public void run(String... args) {
        String[] profiles = environment.getActiveProfiles();
        String activeProfiles = String.join(", ", profiles);

        List<String> validProfiles = Arrays.asList("local", "dev", "qa");
        boolean showEmojis = Arrays.stream(profiles)
                .anyMatch(p -> validProfiles.stream().anyMatch(valid -> valid.equalsIgnoreCase(p)));

        String fire = showEmojis ? "🔥" : "";
        String box = showEmojis ? "📦" : "";
        String puzzle = showEmojis ? "🧩" : "";

        String serviceName = environment.getProperty("spring.application.name", "UnknownService");

        logger.info("========================================");
        logger.info("  {} Service Name        : {}", puzzle, serviceName);
        logger.info("  {} Active Profiles     : {}", fire, activeProfiles);
        logger.info("  {} Config Server Msg   : {}", box, startupInfo.getConfigMessage() != null ?
                startupInfo.getConfigMessage() : "no messge set!");
        logger.info("  {} Server Port         : {}", "::", serverPort);  // Replaced with server port
        logger.info("========================================");

    }
}
