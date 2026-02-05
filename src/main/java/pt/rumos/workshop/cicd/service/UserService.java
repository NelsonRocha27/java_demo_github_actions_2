package pt.rumos.workshop.cicd.service;

import org.springframework.stereotype.Service;

import pt.rumos.workshop.cicd.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service para demonstração
 * 
 * Em produção real:
 * - Repository com JPA/Hibernate
 * - Transações com @Transactional
 * - Validações com Jakarta Validation
 * 
 * Para workshop: Simples, em memória
 */
@Service
public class UserService {
    
    // Simula base de dados em memória
    private static final List<User> USERS = new ArrayList<>();
    
    static {
        USERS.add(User.builder()
            .id(1L)
            .name("Ana Silva")
            .email("ana.silva@rumos.pt")
            .role("developer")
            .level(3)
            .build());
        
        USERS.add(User.builder()
            .id(2L)
            .name("Bruno Santos")
            .email("bruno.santos@rumos.pt")
            .role("devops")
            .level(5)
            .build());
        
        USERS.add(User.builder()
            .id(3L)
            .name("Carla Ferreira")
            .email("carla.ferreira@rumos.pt")
            .role("tester")
            .level(2)
            .build());
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(USERS);
    }
    
    public User getUserById(Long id) {
        return USERS.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
    
    public Optional<User> findByEmail(String email) {
        return USERS.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }
    
    public List<User> findByRole(String role) {
        return USERS.stream()
            .filter(u -> u.getRole().equalsIgnoreCase(role))
            .toList();
    }
}
