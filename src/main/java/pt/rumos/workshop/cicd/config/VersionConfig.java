package pt.rumos.workshop.cicd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * Configuração de versão da aplicação
 * 
 * Importante para CI/CD: Permite traceabilidade entre:
 * - Código fonte (Git)
 * - Artefacto construído (JAR/Docker)
 * - Deploy em produção
 */
@Getter
@Configuration
public class VersionConfig {
    
    @Value("${app.version:${spring.application.version:1.0.0}}")
    private String version;
    
    @Value("${spring.profiles.active:development}")
    private String environment;
    
    @Value("${git.commit:${GIT_COMMIT:unknown}}")
    private String gitCommit;
    
    @Value("${git.branch:${GIT_BRANCH:unknown}}")
    private String gitBranch;
    
    @Value("${build.timestamp:${BUILD_TIMESTAMP:#{new java.util.Date().getTime()}}}")
    private String buildTimestamp;
}
