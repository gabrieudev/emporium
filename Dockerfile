FROM quay.io/keycloak/keycloak:latest

ENV KEYCLOAK_USER=${KEYCLOAK_USER}
ENV KEYCLOAK_PASSWORD=${KEYCLOAK_PASSWORD}

ENV DB_VENDOR=${DB_VENDOR}
ENV DB_ADDR=${DB_ADDR}
ENV DB_PORT=${DB_PORT}
ENV DB_DATABASE=${DB_DATABASE}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}

EXPOSE 8080

ENTRYPOINT ["java", "-Dkeycloak.profile.feature.upload_scripts=disabled", "-Dkeycloak.profile.feature.realm_imports=enabled", "-Dkeycloak.profile.feature.sessions=enabled", "-Dkeycloak.profile.feature.admin_fine_grained_authz=enabled", "-jar", "/opt/keycloak/keycloak.jar", "-b", "0.0.0.0"]