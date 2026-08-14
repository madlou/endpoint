# NixOS Development Environment

This project uses Nix flakes for development environment management.

## Prerequisites

### Enable Flakes

Add this to your `/etc/nix/nix.conf`:
```
experimental-features = nix-command flakes
```

Or enable it globally:
```bash
sudo nix-config-set experimental-features "nix-command flakes"
```

### Install Nix (if not already installed)

```bash
curl -L https://nixos.org/nix/install | sh
```

## Using the Development Environment

### Enter the shell

```bash
cd /home/lewis/dev/endpoint
nix develop
```

The environment will automatically provide:
- **Java 17** (JDK)
- **Maven** for building
- **curl** for testing webhooks
- **direnv** for environment management

### Exit the shell

```bash
exit
```

## Building the Project

```bash
# Enter the development environment
nix develop

# Build the project
mvn clean package

# Run the application
sudo java -jar target/webhook-monitor-1.0.0.jar
```

## Alternative: Using direnv with Nix

If you prefer using direnv with Nix:

1. Add to your `.envrc`:
   ```bash
   use flake
   ```

2. Allow the file:
   ```bash
   direnv allow
   ```

3. The Nix environment will automatically load when you enter the directory.

## Customizing the Environment

Edit `flake.nix` to add or remove packages:

```nix
buildInputs = with pkgs; [
  jdk17
  maven
  curl
  # Add more packages here
];
```

## Troubleshooting

### Flakes not recognized

Make sure flakes are enabled:
```bash
nix --version
# Should show "nix (Nix) X.X.X" with flakes support
```

### Java not found in shell

Ensure you're in the nix develop shell:
```bash
nix develop
java --version
```

### Permission denied on port 80

Run with sudo:
```bash
sudo java -jar target/webhook-monitor-1.0.0.jar
```
