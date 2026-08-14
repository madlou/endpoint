{
  description = "Spring Boot Webhook Monitor";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in
      {
        devShells.default = pkgs.mkShell {
          name = "webhook-monitor-dev";

          buildInputs = with pkgs; [
            # Java 17 for Spring Boot
            jdk17
            
            # Maven for building
            maven
            
            # curl for testing webhooks
            curl
            
            # direnv for environment management
            direnv
          ];

          shellHook = ''
            # Set Java home
            export JAVA_HOME="${pkgs.jdk17}"
            
            # Display versions
            echo "=== Webhook Monitor Development Environment ==="
            echo "Java version: $(java --version | head -1)"
            echo "Maven version: $(mvn --version | head -1)"
            echo "=============================================="
          '';
        };
      }
    );
}
