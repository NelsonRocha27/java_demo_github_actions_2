package pt.rumos.workshop.cicd.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de domínio simples
 * 
 * Em aplicações reais, isto viria de base de dados.
 * Para o workshop, usamos dados em memória.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private Long id;
    private String name;
    private String email;
    private String role;    // "developer", "devops", "tester", etc.
    private Integer level;  // 1-10
}
