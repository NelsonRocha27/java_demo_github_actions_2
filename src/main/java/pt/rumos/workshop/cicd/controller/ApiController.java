package pt.rumos.workshop.cicd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pt.rumos.workshop.cicd.config.VersionConfig;
import pt.rumos.workshop.cicd.model.User;
import pt.rumos.workshop.cicd.service.UserService;

import java.util.List;
import java.util.Map;

@Slf4j

/**
 * API REST para demonstração de CI/CD
 * 
 * Estes endpoints são usados para:
 * 1. Testes automatizados (JUnit)
 * 2. Health checks em pipelines
 * 3. Verificação pós-deploy
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    
    private final UserService userService;
    private final VersionConfig versionConfig;
    
    /**
     * Endpoint de versão - importante para CI/CD
     * Permite verificar qual versão está em produção
     */
    @GetMapping("/version")
    public Map<String, Object> getVersion() {
        return Map.of(
            "version", versionConfig.getVersion(),
            "environment", versionConfig.getEnvironment(),
            "gitCommit", versionConfig.getGitCommit(),
            "gitBranch", versionConfig.getGitBranch()
        );
    }
    
    /**
     * CRUD simples para demonstração
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    /**
     * Endpoint calculadora para demonstrar testes
     * Vamos usar nos testes JUnit para validar lógica
     */
    @GetMapping("/calculate/{a}/{b}")
    public Map<String, Object> calculate(
            @PathVariable Integer a,
            @PathVariable Integer b) {
        
        int sum = a + b;
        int product = a * b;
        
        return Map.of(
            "a", a,
            "b", b,
            "sum", sum,
            "product", product
        );
    }
    
    /**
     * Endpoint para simular diferentes respostas HTTP
     * Útil para testes de CI/CD (health checks, etc.)
     */
    @GetMapping("/status/{code}")
    public Map<String, Object> status(@PathVariable Integer code) {
        return Map.of(
            "statusCode", code,
            "message", "Status response for CI/CD testing"
        );
    }
    
    /**
     * Tratamento de exceções global para este controller
     * Retorna 404 quando o usuário não é encontrado
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleUserNotFound(RuntimeException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return Map.of(
            "error", ex.getMessage(),
            "status", HttpStatus.NOT_FOUND.value()
        );
    }
}
