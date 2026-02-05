package pt.rumos.workshop.cicd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pt.rumos.workshop.cicd.config.VersionConfig;

import java.util.Map;

/**
 * Controller da Homepage
 * 
 * Este endpoint é simplesmente para demostração.
 * Em CI/CD, endpoints de health check são mais importantes.
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    
    private final VersionConfig versionConfig;
    
    @GetMapping
    public Map<String, Object> home() {
        return Map.of(
            "message", "🚀 Bem-vindo ao Workshop CI/CD!",
            "application", "Java Academy Demo",
            "version", versionConfig.getVersion(),
            "environment", versionConfig.getEnvironment(),
            "gitCommit", versionConfig.getGitCommit(),
            "buildTimestamp", versionConfig.getBuildTimestamp(),
            "status", "running",
            "repository", "https://github.com/rumos/workshop-cicd"
        );
    }
}
