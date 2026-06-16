# Install Claude Shim

This guide shows how to get the native `claude` shim on your `PATH` so your shell runs the shim before the real Claude CLI.

## Prerequisites

- The real `claude` CLI already installed somewhere else on your `PATH`

## Getting the binary

You have two options: download a pre-built release, or compile from source.

### Option A: Download a pre-built release (recommended)

Pre-built native binaries are published on the [GitHub Releases page](https://github.com/holtakj/claude-shim/releases).

**Linux** — download the `claude` binary:

```bash
curl -L -o claude https://github.com/holtakj/claude-shim/releases/download/v0.0.1-alpha4/claude
chmod +x claude
```

**Windows** — download the `claude.exe` binary from the [GitHub Releases page](https://github.com/holtakj/claude-shim/releases).

### Option B: Compile from source

If you need a custom or unreleased version, build the binary yourself.

From the repository root, build the native binary.

Linux/macOS:

```bash
./gradlew clean test nativeCompile
```

Windows PowerShell:

```powershell
.\gradlew.bat clean test nativeCompile
```

The binary is written to:

- Linux/macOS: `build/native/nativeCompile/claude`
- Windows: `build/native/nativeCompile/claude.exe`

## 2. Put the shim first on `PATH`

The shim works by being found before the real Claude CLI. When you run `claude`, the shim starts first, then it searches the rest of `PATH` for the next `claude` executable and forwards the call to it.

That means:

- the shim directory must be **before** the real Claude CLI on `PATH`
- the real Claude CLI must still remain on `PATH` somewhere later

### Linux/macOS (`zsh`)

Add the release binary directory to the front of `PATH`:

```bash
export PATH="/path/to/claude-shim/build/native/nativeCompile:$PATH"
```

Persist it in `~/.zshrc`:

```bash
echo 'export PATH="/path/to/claude-shim/build/native/nativeCompile:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Optional copy-based setup:

```bash
mkdir -p ~/.local/bin
cp /path/to/claude-shim/build/native/nativeCompile/claude ~/.local/bin/claude
chmod +x ~/.local/bin/claude
echo 'export PATH="$HOME/.local/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

### Windows (PowerShell)

Add the build output directory for the current session:

```powershell
$env:PATH = "C:\path\to\claude-shim\build\native\nativeCompile;$env:PATH"
```

Persist it for the current user:

```powershell
$userPath = [Environment]::GetEnvironmentVariable("Path", "User")
[Environment]::SetEnvironmentVariable("Path", "C:\path\to\claude-shim\build\native\nativeCompile;" + $userPath, "User")
```

After updating user-level `Path`, open a new terminal.

## 3. Verify the shim is found first

Linux/macOS:

```bash
which claude
```

Windows PowerShell:

```powershell
Get-Command claude
```

It should point to your shim location first.

## 4. Optional configuration

If you want proxy or telemetry settings, create `config.properties` in the default OS-specific config location:

- Linux: `~/.config/claude-shim/config.properties` (or `$XDG_CONFIG_HOME/claude-shim/config.properties`)
- macOS: `~/Library/Application Support/claude-shim/config.properties`
- Windows: `%APPDATA%\claude-shim\config.properties`

Example:

```properties
https_proxy=http://127.0.0.1:8080
disable_telemetry=true
```

## Troubleshooting

### `claude` still resolves to the original CLI

Your shim directory is not first on `PATH`. Move the shim directory earlier, open a new shell/session, and verify command resolution again.

### The shim cannot find the real Claude CLI

Make sure the original Claude installation is still present later on `PATH`. The shim intentionally skips its own executable and then searches for the next `claude` entry.

### Native build is slow the first time

The first `nativeCompile` run may download GraalVM and build the binary from scratch, so it will be noticeably slower than later runs.
