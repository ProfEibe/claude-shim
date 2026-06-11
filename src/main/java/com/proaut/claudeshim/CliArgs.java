package com.proaut.claudeshim;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses CLI arguments, extracting {@code --env}, {@code --version},
 * and {@code --v} flags while collecting the rest for forwarding.
 */
final class CliArgs {

    /** The optional environment name from {@code --env <name>}. */
    private final String envName;

    /** Whether {@code --version} or {@code --v} was requested. */
    private final boolean versionFlag;

    /** Arguments to forward to the real Claude binary. */
    private final List<String> forwardArgs;

    private CliArgs(String envName, boolean versionFlag, List<String> forwardArgs) {
        this.envName = envName;
        this.versionFlag = versionFlag;
        this.forwardArgs = forwardArgs;
    }

    /**
     * Parse raw CLI arguments.
     */
    static CliArgs from(String[] args) {
        String envName = null;
        boolean versionFlag = false;
        List<String> forwardArgs = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            if ("--env".equals(args[i]) && i + 1 < args.length) {
                envName = args[++i];
            } else if ("--version".equals(args[i]) || "--v".equals(args[i])) {
                versionFlag = true;
            } else {
                forwardArgs.add(args[i]);
            }
        }
        return new CliArgs(envName, versionFlag, forwardArgs);
    }

    String envName() {
        return envName;
    }

    boolean versionFlag() {
        return versionFlag;
    }

    List<String> forwardArgs() {
        return forwardArgs;
    }
}
