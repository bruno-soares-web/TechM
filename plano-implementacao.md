# Plano de Implementação - TechManage API

## Visão Geral do Projeto

**Projeto:** Sistema TechManage - API para gerenciamento de usuários
**Tecnologia:** Spring Boot + Spring Data JPA
**Objetivo:** Implementar exatamente os requisitos do desafio

**⚠️ IMPORTANTE:** Commits serão realizados ao final de cada fase.

---

## FASE 1: Configuração Base do Projeto

### 1.1 Inicialização do Projeto Spring Boot
- [ ] Criar projeto Spring Boot com dependências:
  - Spring Web
  - Spring Data JPA
  - Spring Boot Starter Validation
  - H2 Database
  - Spring Boot Test

### 1.2 Estrutura de Pastas
- [ ] Configurar estrutura seguindo padrão Controller/Service/Repository
- [ ] Configurar application.properties para H2

**COMMIT 1:** "feat: configuração inicial do projeto Spring Boot"

---

## FASE 2: Entidade User e Enum

### 2.1 Enum UserType
- [ ] Criar enum UserType com valores: ADMIN, EDITOR, VIEWER

### 2.2 Entidade User
- [ ] Criar entidade User com atributos obrigatórios:
  - `id` (Long): @Id @GeneratedValue
  - `fullName` (String): @NotBlank
  - `email` (String): @Email @Column(unique = true)
  - `phone` (String): validação formato internacional
  - `birthDate` (Date): @Past
  - `userType` (String): @NotNull

**COMMIT 2:** "feat: implementação da entidade User e enum UserType"

---

## FASE 3: Camada Repository

### 3.1 UserRepository
- [ ] Criar interface UserRepository estendendo JpaRepository

**COMMIT 3:** "feat: implementação do UserRepository"

---

## FASE 4: Camada Service

### 4.1 UserService
- [ ] Criar UserService com métodos:
  - Criar novo usuário
  - Buscar todos os usuários
  - Buscar usuário por ID
  - Atualizar usuário
  - Excluir usuário

### 4.2 Validações de Negócio
- [ ] Validar email único
- [ ] Validar existência do usuário

**COMMIT 4:** "feat: implementação do UserService com regras de negócio"

---

## FASE 5: Camada Controller

### 5.1 UserController
- [ ] Implementar endpoints exigidos:
  - `POST /api/users`: Criar usuário (retorna 201 + usuário criado)
  - `GET /api/users`: Listar todos (retorna 200)
  - `GET /api/users/{id}`: Buscar por ID (retorna 200 ou 404)
  - `PUT /api/users/{id}`: Atualizar (retorna 200 ou 404)
  - `DELETE /api/users/{id}`: Excluir (retorna 204 ou 404)

**COMMIT 5:** "feat: implementação dos endpoints REST da API"

---

## FASE 6: Tratamento de Erros

### 6.1 Exception Handler
- [ ] Implementar tratamento de erros com retornos adequados:
  - HTTP 404 para ID não existente
  - HTTP 400 para dados inválidos
  - Respostas claras para erros

**COMMIT 6:** "feat: implementação do tratamento de erros da API"

---

## FASE 7: Testes

### 7.1 Testes Unitários
- [ ] Testes unitários para UserService

### 7.2 Testes de Integração
- [ ] Pelo menos um teste de integração para endpoints da API

**COMMIT 7:** "test: implementação de testes unitários e de integração"

---

## FASE 8: Configuração de Banco e Scripts

### 8.1 Scripts SQL
- [ ] Criar scripts para criação das tabelas
- [ ] Dados iniciais opcionais

**COMMIT 8:** "feat: configuração do banco de dados com scripts SQL"

---

## FASE 9: Documentação

### 9.1 README.md
- [ ] Passos para rodar o projeto localmente
- [ ] Exemplos de requisições para cada endpoint
- [ ] Instruções claras e completas

**COMMIT 9:** "docs: criação do README com instruções completas"

---

## FASE 10: Validação Final

### 10.1 Testes Finais
- [ ] Verificar funcionamento de todos os endpoints
- [ ] Validar execução local seguindo README
- [ ] Confirmar todos os requisitos atendidos

**COMMIT 10:** "chore: validação final e ajustes para entrega"

---

## Requisitos Técnicos Checklist

✅ **Entidade User** com todos os atributos obrigatórios
✅ **Operações REST** com endpoints corretos
✅ **Banco de Dados** relacional com Spring Data JPA
✅ **Validações** usando Bean Validation
✅ **Testes** unitários para serviços + integração para API
✅ **Documentação** README.md completo
✅ **Arquitetura** em camadas (Controller/Service/Repository)
✅ **Tratamento de Erros** com códigos HTTP adequados

## Dependências Mínimas Necessárias

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```