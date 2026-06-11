package com.proaut.claudeshim;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds the parsed configuration from the global config.properties file.
 */
public record Config(
        String https_proxy,
        String http_proxy,
        String no_proxy,
        Boolean disable_telemetry,
        Map<String, List<String>> envPaths
) {

    /** Default empty constructor used when no config file is found. */
    public Config() {
        this(null, null, null, null, new LinkedHashMap<>());
    }
}
