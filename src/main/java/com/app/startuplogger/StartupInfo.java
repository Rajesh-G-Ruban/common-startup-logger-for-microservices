package com.app.startuplogger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
public class StartupInfo {

    private String configMessage;

    public String getConfigMessage() {
        return configMessage;
    }

    public void setConfigMessage(String configMessage) {
        this.configMessage = configMessage;
    }
}
