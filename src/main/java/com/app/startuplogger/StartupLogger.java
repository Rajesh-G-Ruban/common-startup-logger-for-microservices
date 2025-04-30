package com.app.startuplogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Logs startup info safely for JAR deployments.
 *
 * Created by Rajesh.G
 * Since 2025-04-29
 */
@Component
public class StartupLogger implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    private final Environment environment;
    private final StartupInfo startupInfo;

    public StartupLogger(Environment environment, StartupInfo startupInfo) {
        this.environment = environment;
        this.startupInfo = startupInfo;
    }

    @Override
    public void run(String... args) {
        String[] profiles = environment.getActiveProfiles();
        String activeProfiles = profiles.length > 0 ? String.join(", ", profiles) : "default";
        String serviceName = environment.getProperty("spring.application.name", "UnnamedService");
        String configMessage = startupInfo.getConfigMessage() != null
                ? startupInfo.getConfigMessage()
                : "No config message defined.";

        String port = environment.getProperty("server.port");
        String portMessage = (port != null) ? port : "Port not defined in configuration!";

        logger.info("========================================");
        logger.info(" Service Name      : {}", serviceName);
        logger.info(" Active Profiles   : {}", activeProfiles);
        logger.info(" Config Message    : {}", configMessage);
        logger.info(" Server Port       : {}", portMessage);

        // Environment-specific log
        if (activeProfiles.matches("(?i).*\\b(dev|local|qa)\\b.*")) {
            logger.info(" Environment Info  : DEV/LOCAL/QA mode - debugging features may be active.");
        }

        logger.info("========================================");
    }
}
