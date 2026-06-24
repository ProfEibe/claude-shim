package com.proaut.claudeshim;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a named environment with its configuration and extra environment variables.
 */
public record Environment(
        String name,
        Config config,
        Map<String, String> extraEnvVars
) {

    public Environment {
        extraEnvVars = extraEnvVars != null ? extraEnvVars : new LinkedHashMap<>();
    }
}
