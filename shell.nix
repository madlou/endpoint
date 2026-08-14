{
  buildJdk = pkgs: pkgs.jdk17;

  buildMaven = pkgs: pkgs.maven;

  buildInputs = pkgs: with pkgs; [
    jdk17
    maven
    curl
  ];

  shellHook = ''
    export JAVA_HOME=${pkgs.jdk17}
  '';
}
