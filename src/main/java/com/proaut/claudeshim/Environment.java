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

    /** Default constructor that null-checks the extra env vars map. */
    public Environment {
        extraEnvVars = extraEnvVars != null ? extraEnvVars : new LinkedHashMap<>();
    }
}
