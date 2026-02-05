# 🚀 Workshop CI/CD - Java Academy Demo

Aplicação Spring Boot + Gradle para demonstração de CI/CD.

## 📋 Sobre

Este projeto foi criado especificamente para o Workshop CI/CD da **Rumos - Academia Java**.

**Objectivo:** Demonstração prática de pipelines de CI/CD com código Java real.

## 🏗️ Stack Tecnológica

| Componente | Tecnologia | Versão |
|-------------|-----------|--------|
| Linguagem | Java | 17 |
| Framework | Spring Boot | 3.2.0 |
| Build Tool | Gradle | 8.x |
| Testes | JUnit 5 | 5.10.1 |
| CI/CD | GitHub Actions | - |

## 🚀 Como Correr Localmente

### Pré-requisitos
- JDK 17+
- Gradle (ou usar wrapper)

### Passos

```bash
# Clone do repositório
git clone <repository-url>
cd java-demo-project

# Executar a aplicação
./gradlew bootRun

# Ou via gradle instalado
gradle bootRun
```

## 🧪 Testes

```bash
# Executar todos os testes
./gradlew test

# Executar testes com debug
./gradlew test --info

# Gerar relatório de cobertura
./gradlew jacocoTestReport

# Verificar cobertura
./gradlew jacocoTestCoverageVerification
```

### Relatórios

- **Testes:** `build/reports/tests/test/index.html`
- **Cobertura:** `build/reports/jacoco/test/html/index.html`

## 🌐 Endpoints da API

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| GET | `/` | Homepage com informações |
| GET | `/actuator/health` | Health check (CI/CD) |
| GET | `/api/version` | Versão da aplicação |
| GET | `/api/users` | Lista todos os utilizadores |
| GET | `/api/users/{id}` | Detalhes do utilizador |
| GET | `/api/calculate/{a}/{b}` | Calculadora demo |
| GET | `/api/status/{code}` | Status response (demo) |
