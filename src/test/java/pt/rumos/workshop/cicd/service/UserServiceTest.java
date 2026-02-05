package pt.rumos.workshop.cicd.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pt.rumos.workshop.cicd.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Testes Unitários do UserService
 * 
 * Importante para CI/CD:
 * - Testes rápidos (unitários)
 * - Cobertura de lógica de negócio
 * - Edge cases e cenários de erro
 */
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    // ============================================
    // TESTES DE BUSCA (Query)
    // ============================================

    @Test
    @DisplayName("getAllUsers deve retornar todos os utilizadores")
    void whenGetAllUsers_thenReturnList() {
        List<User> users = userService.getAllUsers();
        
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(3, users.size());
    }

    @Test
    @DisplayName("getUserById com ID existente deve retornar utilizador")
    void whenGetUserByIdExists_thenReturnUser() {
        User user = userService.getUserById(1L);
        
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
    }

    @Test
    @DisplayName("getUserById com ID inexistente deve lançar exceção")
    void whenGetUserByIdNotExists_thenThrowException() {
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> userService.getUserById(999L)
        );
        
        assertTrue(exception.getMessage().contains("999"));
    }

    // ============================================
    // TESTES DE BUSCA POR EMAIL
    // ============================================

    @Test
    @DisplayName("findByEmail com email existente deve retornar Optional com User")
    void whenFindByEmailExists_thenReturnUser() {
        Optional<User> user = userService.findByEmail("ana.silva@rumos.pt");
        
        assertTrue(user.isPresent());
        assertEquals("ana.silva@rumos.pt", user.get().getEmail());
    }

    @Test
    @DisplayName("findByEmail com email inexistente deve retornar Optional vazio")
    void whenFindByEmailNotExists_thenReturnEmpty() {
        Optional<User> user = userService.findByEmail("nao@existe.pt");
        
        assertFalse(user.isPresent());
    }

    @Test
    @DisplayName("findByEmail é case-insensitive")
    void whenFindByEmailCaseInsensitive_thenReturnUser() {
        Optional<User> user1 = userService.findByEmail("ANA.SILVA@RUMOS.PT");
        Optional<User> user2 = userService.findByEmail("ana.silva@rumos.pt");
        
        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        assertEquals(user1.get().getId(), user2.get().getId());
    }

    // ============================================
    // TESTES DE BUSCA POR ROLE
    // ============================================

    @Test
    @DisplayName("findByRole deve retornar utilizadores com role especificada")
    void whenFindByRole_thenReturnUsersWithRole() {
        List<User> developers = userService.findByRole("developer");
        
        assertNotNull(developers);
        assertFalse(developers.isEmpty());
        developers.forEach(user -> 
            assertEquals("developer", user.getRole())
        );
    }

    @Test
    @DisplayName("findByRole com role inexistente deve retornar lista vazia")
    void whenFindByRoleNotExists_thenReturnEmptyList() {
        List<User> users = userService.findByRole("manager");
        
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    @DisplayName("findByRole é case-insensitive")
    void whenFindByRoleCaseInsensitive_thenReturnUsers() {
        List<User> users1 = userService.findByRole("DEVELOPER");
        List<User> users2 = userService.findByRole("developer");
        
        assertEquals(users1.size(), users2.size());
    }
}
