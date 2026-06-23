package com.proaut.claudeshim;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a named environment with its configuration and extra environment variables.
 */
public record Environment(
        String name,
        Config config,
        Map<String, String> extraEnvVars,
        String color
) {

    public Environment {
        extraEnvVars = extraEnvVars != null ? extraEnvVars : new LinkedHashMap<>();
    }

    public Environment(String name, Config config, Map<String, String> extraEnvVars) {
        this(name, config, extraEnvVars, null);
    }
}
