package pt.rumos.workshop.cicd.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de Integração da API
 * 
 * Estes testes são o "gate" de CI/CD:
 * - Se falham, a pipeline para
 * - Cobertura mínima é exigida
 * - Health checks garantem qualidade
 * 
 * Importante: Usamos MockMvc para simular HTTP sem servidor real
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ============================================
    // TESTES DE VERIFICAÇÃO (CI/CD Gate)
    // ============================================

    @Test
    @DisplayName("GET /api/version deve retornar informações de versão")
    void whenGetVersion_thenReturnVersionInfo() throws Exception {
        mockMvc.perform(get("/api/version"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.environment").exists())
            .andExpect(jsonPath("$.gitCommit").exists())
            .andExpect(jsonPath("$.gitBranch").exists());
    }

    @Test
    @DisplayName("GET /api/users deve retornar lista de utilizadores")
    void whenGetAllUsers_thenReturnUserList() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("GET /api/users/{id} deve retornar utilizador específico")
    void whenGetUserById_thenReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.role").exists());
    }

    @Test
    @DisplayName("GET /api/users/{id} com ID inexistente deve retornar 404")
    void whenGetUserByIdNotExists_thenReturnError() throws Exception {
        mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.status").value(404));
    }

    // ============================================
    // TESTES DE LÓGICA (Cálculos)
    // ============================================

    @Test
    @DisplayName("GET /api/calculate/{a}/{b} deve somar corretamente")
    void whenCalculate_thenReturnCorrectSum() throws Exception {
        mockMvc.perform(get("/api/calculate/5/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.a").value(5))
            .andExpect(jsonPath("$.b").value(3))
            .andExpect(jsonPath("$.sum").value(8))
            .andExpect(jsonPath("$.product").value(15));
    }

    @Test
    @DisplayName("GET /api/calculate/{a}/{b} com números negativos")
    void whenCalculateNegativeNumbers_thenReturnCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculate/-5/10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sum").value(5))
            .andExpect(jsonPath("$.product").value(-50));
    }

    @Test
    @DisplayName("GET /api/calculate/{a}/{b} com zero")
    void whenCalculateWithZero_thenReturnCorrectResult() throws Exception {
        mockMvc.perform(get("/api/calculate/0/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sum").value(5))
            .andExpect(jsonPath("$.product").value(0));
    }

    // ============================================
    // TESTES DE EDGE CASES
    // ============================================

    @Test
    @DisplayName("GET /api/status/{code} com código válido")
    void whenGetStatus_thenReturnStatusCode() throws Exception {
        mockMvc.perform(get("/api/status/200"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    @DisplayName("GET /api/status/{code} com 404 deve funcionar")
    void whenGetStatus404_thenReturnStatusCode() throws Exception {
        mockMvc.perform(get("/api/status/404"))
            .andExpect(status().isOk()) // 200 do endpoint, não 404
            .andExpect(jsonPath("$.statusCode").value(404));
    }
}
