package pt.rumos.workshop.cicd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

/**
 * Aplicação Demo para Workshop CI/CD
 * 
 * Esta aplicação demonstra:
 * - REST API simples
 * - Health checks (essencial para CI/CD)
 * - Versionamento
 * - Configuração por ambiente
 * 
 * Objetivo: Servir como base para pipelines de CI/CD
 */
@Slf4j
@SpringBootApplication
public class CicdWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CicdWorkshopApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        
        String port = env.getProperty("server.port", "8080");
        String profile = env.getProperty("spring.profiles.active", "default");
        String version = env.getProperty("app.version", "1.0.0");

        log.info("""

╔═════════════════════════════════════════════════════════════╗
║                                                               ║
║     🚀 WORKSHOP CI/CD - Aplicação Demo                        ║
║                                                               ║
║     Versão:      {:<24} ║
║     Ambiente:    {:<24} ║
║     Porta:       {:<24} ║
║                                                               ║
║     Endpoints disponíveis:                                     ║
║     ├─ GET  /                   (Homepage)                    ║
║     ├─ GET  /actuator/health    (Health Check - CI/CD)        ║
║     ├─ GET  /api/version        (Versão atual)               ║
║     ├─ GET  /api/users          (Lista utilizadores)         ║
║     ├─ GET  /api/users/{id}     (Detalhes)                   ║
║     └─ GET  /api/calculate/{a}/{b}  (Calculadora demo)        ║
║                                                               ║
║     📖 Acesso: http://localhost:{}                           ║
║                                                               ║
╚═════════════════════════════════════════════════════════════╝
            """.trim(), version, profile, port, port);
    }
}
