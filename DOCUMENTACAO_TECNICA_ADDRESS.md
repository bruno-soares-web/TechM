# DOCUMENTAÇÃO TÉCNICA
## Adição do Campo `address` (String) na Entidade User

**Projeto:** TechManage
**Data:** 2025-10-07
**Versão:** 1.0
**Autor:** Equipe de Desenvolvimento

---

## 📋 RESUMO EXECUTIVO

**Objetivo:** Adicionar o campo `address` do tipo String na entidade User

**Impacto:** 9 classes (5 produção + 4 testes)

**Complexidade:** Média

**Estimativa de Desenvolvimento:** 2-3 horas

**Estimativa de Testes:** 1-2 horas

**Total Estimado:** 3-5 horas

---

## 🎯 CLASSES IMPACTADAS

### **PRODUÇÃO (5 classes)**

#### 1. **User.java** (Entidade Principal)
- **Caminho:** `src\main\java\com\techmanage\entity\User.java`
- **Tipo:** Entidade JPA
- **Impacto:** ALTO
- **Descrição:** Classe principal que representa o usuário no sistema

#### 2. **UserServiceImpl.java** (Lógica de Negócio)
- **Caminho:** `src\main\java\com\techmanage\service\UserServiceImpl.java`
- **Tipo:** Service Layer
- **Impacto:** MÉDIO
- **Descrição:** Implementação da lógica de negócio para operações de usuário

#### 3. **UserController.java** (API REST)
- **Caminho:** `src\main\java\com\techmanage\controller\UserController.java`
- **Tipo:** REST Controller
- **Impacto:** NENHUM (apenas validação indireta via @Valid)
- **Descrição:** Controlador REST que expõe endpoints da API

#### 4. **UserRepository.java** (Persistência)
- **Caminho:** `src\main\java\com\techmanage\repository\UserRepository.java`
- **Tipo:** JPA Repository
- **Impacto:** NENHUM (JPA auto-gerencia colunas)
- **Descrição:** Interface de acesso a dados

#### 5. **UserService.java** (Interface)
- **Caminho:** `src\main\java\com\techmanage\service\UserService.java`
- **Tipo:** Interface
- **Impacto:** NENHUM
- **Descrição:** Contrato de serviço de usuário

---

### **TESTES (4 classes)**

#### 6. **UserTest.java**
- **Caminho:** `src\test\java\com\techmanage\entity\UserTest.java`
- **Tipo:** Teste Unitário
- **Impacto:** ALTO
- **Descrição:** Testes da entidade User

#### 7. **UserServiceTest.java**
- **Caminho:** `src\test\java\com\techmanage\service\UserServiceTest.java`
- **Tipo:** Teste Unitário com Mocks
- **Impacto:** MÉDIO
- **Descrição:** Testes do serviço de usuário

#### 8. **UserControllerIntegrationTest.java**
- **Caminho:** `src\test\java\com\techmanage\controller\UserControllerIntegrationTest.java`
- **Tipo:** Teste de Integração
- **Impacto:** MÉDIO
- **Descrição:** Testes end-to-end do controller

#### 9. **UserRepositoryTest.java**
- **Caminho:** `src\test\java\com\techmanage\repository\UserRepositoryTest.java`
- **Tipo:** Teste de Repositório (@DataJpaTest)
- **Impacto:** BAIXO
- **Descrição:** Testes do repositório JPA

#### 10. **UserControllerUnitTest.java**
- **Caminho:** `src\test\java\com\techmanage\controller\UserControllerUnitTest.java`
- **Tipo:** Teste Unitário
- **Impacto:** MÉDIO
- **Descrição:** Testes unitários do controller

---

## 🔧 ALTERAÇÕES DETALHADAS POR CLASSE

### **1. User.java** - `src\main\java\com\techmanage\entity\User.java`

#### **📍 Alteração 1: Linha 11 - Atualizar @JsonPropertyOrder**

**Descrição:** Adicionar campo `address` na ordem de serialização JSON

```java
// ❌ ANTES (linha 11):
@JsonPropertyOrder({"id", "fullName", "email", "phone", "birthDate", "userType"})

// ✅ DEPOIS:
@JsonPropertyOrder({"id", "fullName", "email", "phone", "birthDate", "userType", "address"})
```

**Motivo:** Garantir que o campo `address` apareça na ordem correta no JSON de resposta da API.

---

#### **📍 Alteração 2: Após linha 40 - Adicionar campo address**

**Descrição:** Declarar novo atributo privado com anotação JPA

```java
// ✅ ADICIONAR APÓS linha 40:
    @Column(name = "address")
    private String address;
```

**Motivo:** Criar o novo campo no modelo de dados. A anotação `@Column` permite que o JPA mapeie para a coluna do banco.

**Observações:**
- Campo é **nullable** (permite valores null)
- Não há validações (@NotBlank, @Size, etc.) - adicionar se necessário
- Nome da coluna no banco será "address"

---

#### **📍 Alteração 3: Linha 45 - Atualizar construtor parametrizado**

**Descrição:** Incluir parâmetro `address` no construtor

```java
// ❌ ANTES (linha 45):
    public User(String fullName, String email, String phone, LocalDate birthDate, UserType userType) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.userType = userType;
    }

// ✅ DEPOIS:
    public User(String fullName, String email, String phone, LocalDate birthDate, UserType userType, String address) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.userType = userType;
        this.address = address;
    }
```

**Motivo:** Permitir inicialização do objeto User com todos os campos, incluindo address.

**⚠️ ATENÇÃO:** Esta alteração irá quebrar TODOS os testes que usam este construtor!

---

#### **📍 Alteração 4: Após linha 111 - Adicionar getter e setter**

**Descrição:** Criar métodos de acesso ao campo address

```java
// ✅ ADICIONAR APÓS linha 111:
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
```

**Motivo:** Permitir leitura e escrita do campo conforme padrão JavaBean.

---

#### **📍 Alteração 5: Linhas 114-123 - Atualizar método toString()**

**Descrição:** Incluir campo address na representação String do objeto

```java
// ❌ ANTES (linhas 114-123):
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", userType=" + userType +
                '}';
    }

// ✅ DEPOIS:
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", userType=" + userType +
                ", address='" + address + '\'' +
                '}';
    }
```

**Motivo:** Manter consistência do método toString() com todos os campos da entidade.

---

### **2. UserServiceImpl.java** - `src\main\java\com\techmanage\service\UserServiceImpl.java`

#### **📍 Alteração 1: Linha 60 - Adicionar set de address no método updateUser**

**Descrição:** Atualizar campo address durante update de usuário

**Contexto completo (linhas 56-62):**

```java
// ❌ ANTES:
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setUserType(user.getUserType());

        return userRepository.save(existingUser);

// ✅ DEPOIS:
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setUserType(user.getUserType());
        existingUser.setAddress(user.getAddress());  // ⬅️ NOVA LINHA

        return userRepository.save(existingUser);
```

**Motivo:** Garantir que o campo address seja atualizado quando um usuário for editado via PUT /api/users/{id}.

**Impacto:** Sem esta linha, o campo address nunca seria atualizado em operações de update.

---

### **3. UserTest.java** - `src\test\java\com\techmanage\entity\UserTest.java`

#### **📍 Alteração 1: Linha 27 - Teste do construtor padrão**

```java
// ✅ ADICIONAR APÓS linha 27:
        assertNull(newUser.getAddress());
```

**Motivo:** Validar que o construtor padrão inicializa address como null.

---

#### **📍 Alteração 2: Linha 33 - Teste do construtor parametrizado**

```java
// ❌ ANTES (linha 33):
        User newUser = new User("João Silva", "joao@email.com", "+5511999999999", birthDate, UserType.ADMIN);

// ✅ DEPOIS:
        User newUser = new User("João Silva", "joao@email.com", "+5511999999999", birthDate, UserType.ADMIN, "Rua Teste, 123");
```

---

#### **📍 Alteração 3: Linha 40 - Assert do campo address**

```java
// ✅ ADICIONAR APÓS linha 40:
        assertEquals("Rua Teste, 123", newUser.getAddress());
```

**Motivo:** Validar que o construtor parametrizado inicializa address corretamente.

---

#### **📍 Alteração 4: Linha 52 - Teste de setters**

```java
// ✅ ADICIONAR APÓS linha 52:
        user.setAddress("Rua Teste, 123");
```

---

#### **📍 Alteração 5: Linha 59 - Assert do getter**

```java
// ✅ ADICIONAR APÓS linha 59:
        assertEquals("Rua Teste, 123", user.getAddress());
```

**Motivo:** Validar funcionamento correto dos getters e setters de address.

---

#### **📍 Alteração 6: Linha 106 - Preparar toString test**

```java
// ✅ ADICIONAR APÓS linha 106:
        user.setAddress("Rua Teste, 123");
```

---

#### **📍 Alteração 7: Linha 108 - Atualizar expected string**

```java
// ❌ ANTES (linha 108):
        String expected = "User{id=1, fullName='João Silva', email='joao@email.com', phone='+5511999999999', birthDate=1990-05-15, userType=ADMIN}";

// ✅ DEPOIS:
        String expected = "User{id=1, fullName='João Silva', email='joao@email.com', phone='+5511999999999', birthDate=1990-05-15, userType=ADMIN, address='Rua Teste, 123'}";
```

---

#### **📍 Alteração 8: Linha 114 - ToString com valores null**

```java
// ❌ ANTES (linha 114):
        String expected = "User{id=null, fullName='null', email='null', phone='null', birthDate=null, userType=null}";

// ✅ DEPOIS:
        String expected = "User{id=null, fullName='null', email='null', phone='null', birthDate=null, userType=null, address='null'}";
```

**Motivo:** Validar que o método toString() funciona corretamente com o novo campo.

---

### **4. UserServiceTest.java** - `src\test\java\com\techmanage\service\UserServiceTest.java`

#### **📍 Alterações nos construtores User - Múltiplas linhas**

**Total de alterações:** Aproximadamente 12 ocorrências

#### **Linha 39-40:** Setup - testUser

```java
// ❌ ANTES:
        user = new User("João Silva", "joao@email.com", "+5511999999999",
                       LocalDate.of(1990, 5, 15), UserType.ADMIN);

// ✅ DEPOIS:
        user = new User("João Silva", "joao@email.com", "+5511999999999",
                       LocalDate.of(1990, 5, 15), UserType.ADMIN, "Rua A, 100");
```

#### **Linha 43-44:** Setup - existingUser

```java
// ❌ ANTES:
        existingUser = new User("Maria Santos", "maria@email.com", "+5511888888888",
                               LocalDate.of(1985, 8, 20), UserType.EDITOR);

// ✅ DEPOIS:
        existingUser = new User("Maria Santos", "maria@email.com", "+5511888888888",
                               LocalDate.of(1985, 8, 20), UserType.EDITOR, "Rua B, 200");
```

#### **Outras linhas a serem atualizadas:**

| Linha | Teste | Descrição |
|-------|-------|-----------|
| 65-66 | createUser_EmailAlreadyExists | Criar usuário com email duplicado |
| 109-110 | updateUser_Success | Dados de atualização |
| 129-130 | updateUser_UserNotFound | Dados para update de usuário inexistente |
| 159-160 | updateUser_EmailAlreadyExistsForDifferentUser | Outro usuário |
| 163-164 | updateUser_EmailAlreadyExistsForDifferentUser | Dados de update |
| 178-179 | updateUser_SameEmailForSameUser | Dados de update |
| 196-197 | createUser_PhoneAlreadyExists | Novo usuário com telefone duplicado |
| 208-209 | updateUser_PhoneAlreadyExistsForDifferentUser | Outro usuário |
| 212-213 | updateUser_PhoneAlreadyExistsForDifferentUser | Dados de update |
| 227-228 | updateUser_SamePhoneForSameUser | Dados de update |

**Padrão de alteração:**

```java
// ❌ ANTES:
User newUser = new User("Nome", "email@test.com", "+5511777777777",
                       LocalDate.of(1995, 3, 10), UserType.VIEWER);

// ✅ DEPOIS:
User newUser = new User("Nome", "email@test.com", "+5511777777777",
                       LocalDate.of(1995, 3, 10), UserType.VIEWER, "Rua X, 999");
```

**Motivo:** Ajustar todos os testes para o novo construtor com 6 parâmetros.

---

### **5. UserControllerIntegrationTest.java** - `src\test\java\com\techmanage\controller\UserControllerIntegrationTest.java`

#### **📍 Alteração 1: Linhas 51-52 - Setup testUser**

```java
// ❌ ANTES:
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                           LocalDate.of(1990, 5, 15), UserType.ADMIN);

// ✅ DEPOIS:
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                           LocalDate.of(1990, 5, 15), UserType.ADMIN, "Rua Teste, 100");
```

---

#### **📍 Alteração 2: Linha 66 - Assert JSON response**

```java
// ✅ ADICIONAR APÓS linha 66:
                .andExpect(jsonPath("$.address", is("Rua Teste, 100")))
```

**Motivo:** Validar que o campo address é retornado corretamente na API REST.

---

#### **Outras linhas a serem atualizadas:**

| Linha | Teste | User |
|-------|-------|------|
| 71-72 | createUser_InvalidData | invalidUser |
| 87-88 | createUser_DuplicateEmail | duplicateUser |
| 140-141 | updateUser_Success | updatedUser |
| 188-189 | updateUser_DuplicateEmail | otherUser |
| 192-193 | updateUser_DuplicateEmail | updatedUser |
| 219-220 | createUser_DuplicatePhone | duplicatePhoneUser |
| 238-239 | updateUser_DuplicatePhone | otherUser |
| 242-243 | updateUser_DuplicatePhone | updatedUser |

**Padrão:** Adicionar último parâmetro com endereço apropriado para cada teste.

---

### **6. UserRepositoryTest.java** - `src\test\java\com\techmanage\repository\UserRepositoryTest.java`

#### **📍 Alteração 1: Linhas 29-30 - Setup testUser**

```java
// ❌ ANTES:
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                          LocalDate.of(1990, 5, 15), UserType.ADMIN);

// ✅ DEPOIS:
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                          LocalDate.of(1990, 5, 15), UserType.ADMIN, "Rua Teste, 100");
```

---

#### **📍 Alteração 2: Linha 43 - Assert address**

```java
// ✅ ADICIONAR APÓS linha 43:
        assertEquals("Rua Teste, 100", savedUser.getAddress());
```

**Motivo:** Validar que o campo address é persistido corretamente no banco de dados.

---

#### **Outras linhas a serem atualizadas:**

| Linha | Teste | User |
|-------|-------|------|
| 67-68 | testFindAll | user2 |
| 129-130 | testCount | user2 |
| 146-147 | testDeleteAll | user2 |

---

### **7. UserControllerUnitTest.java** - `src\test\java\com\techmanage\controller\UserControllerUnitTest.java`

#### **📍 Total de alterações: Aproximadamente 15 construtores**

**Linhas principais:**

| Linha | Teste | Variável |
|-------|-------|----------|
| 37-38 | setUp | testUser |
| 45-46 | createUser_shouldReturnCreated | inputUser |
| 74-75 | getAllUsers_shouldReturnOkWithUsers | user2 |
| 154-155 | updateUser_shouldReturnOkWithUpdatedUser | updateData |
| 156-157 | updateUser_shouldReturnOkWithUpdatedUser | updatedUser |
| 178-179 | updateUser_withNullId_shouldCallService | updateData |
| 255-256 | createUser_withDifferentUserTypes_shouldWork | adminUser |
| 263-264 | createUser_withDifferentUserTypes_shouldWork | editorUser |
| 271-272 | createUser_withDifferentUserTypes_shouldWork | viewerUser |
| 287-288 | updateUser_withDifferentUserTypes_shouldWork | adminUpdate |
| 297-298 | updateUser_withDifferentUserTypes_shouldWork | editorUpdate |
| 312-313 | getAllUsers_withMultipleUsersOfDifferentTypes_shouldReturnAll | admin |
| 316-317 | getAllUsers_withMultipleUsersOfDifferentTypes_shouldReturnAll | editor |
| 320-321 | getAllUsers_withMultipleUsersOfDifferentTypes_shouldReturnAll | viewer |

**Exemplo padrão:**

```java
// ❌ ANTES (linhas 37-38):
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                          LocalDate.of(1990, 5, 15), UserType.ADMIN);

// ✅ DEPOIS:
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                          LocalDate.of(1990, 5, 15), UserType.ADMIN, "Rua Teste, 100");
```

---

## 📊 RESUMO QUANTITATIVO DE ALTERAÇÕES

### **Produção**

| Arquivo | Linhas Alteradas | Linhas Adicionadas | Total |
|---------|------------------|-------------------|-------|
| User.java | 3 | 10 | 13 |
| UserServiceImpl.java | 0 | 1 | 1 |
| **TOTAL PRODUÇÃO** | **3** | **11** | **14** |

### **Testes**

| Arquivo | Construtores | Asserts | Total Alterações |
|---------|--------------|---------|------------------|
| UserTest.java | 2 | 5 | 7 |
| UserServiceTest.java | 12 | 0 | 12 |
| UserControllerIntegrationTest.java | 9 | 1 | 10 |
| UserRepositoryTest.java | 4 | 1 | 5 |
| UserControllerUnitTest.java | 15 | 0 | 15 |
| **TOTAL TESTES** | **42** | **7** | **49** |

### **TOTAL GERAL: 63 alterações**

---

## 📊 RESUMO DE IMPACTO POR ARQUIVO

| # | Arquivo | Alterações | Complexidade | Prioridade |
|---|---------|-----------|--------------|------------|
| 1 | **User.java** | 6 pontos principais | ⭐⭐⭐ Alta | 🔴 Crítica |
| 2 | **UserServiceImpl.java** | 1 linha | ⭐ Baixa | 🔴 Crítica |
| 3 | **UserTest.java** | 7 pontos | ⭐⭐ Média | 🟡 Alta |
| 4 | **UserServiceTest.java** | ~12 construtores | ⭐⭐⭐ Alta | 🟡 Alta |
| 5 | **UserControllerIntegrationTest.java** | ~9 construtores + 1 assert | ⭐⭐ Média | 🟡 Alta |
| 6 | **UserRepositoryTest.java** | ~4 construtores + 1 assert | ⭐ Baixa | 🟢 Média |
| 7 | **UserControllerUnitTest.java** | ~15 construtores | ⭐⭐ Média | 🟢 Média |

---

## 🗄️ MIGRAÇÃO DE BANCO DE DADOS

### **Script SQL Necessário**

```sql
-- Migration: Adicionar coluna address na tabela users
-- Versão: V2__add_address_to_users.sql
-- Data: 2025-10-07

ALTER TABLE users
ADD COLUMN address VARCHAR(255);

-- Comentário explicativo
COMMENT ON COLUMN users.address IS 'Endereço completo do usuário';
```

### **Localização sugerida (se usar Flyway):**

```
src/main/resources/db/migration/V2__add_address_to_users.sql
```

### **Localização sugerida (se usar Liquibase):**

```xml
<changeSet id="2" author="dev-team">
    <addColumn tableName="users">
        <column name="address" type="VARCHAR(255)">
            <constraints nullable="true"/>
        </column>
    </addColumn>
</changeSet>
```

### **Comportamento para Dados Existentes:**

- **Registros existentes:** Campo `address` será **NULL**
- **Novos registros:** Campo `address` pode ser NULL ou preenchido
- **Recomendação:** Considerar valor padrão ou migração de dados se necessário

---

## ✅ CHECKLIST DE IMPLEMENTAÇÃO

### **Fase 1: Código de Produção** ⏱️ 1-2 horas

- [ ] 1.1. Criar branch de desenvolvimento: `feature/add-user-address`
- [ ] 1.2. Adicionar campo `address` em User.java (linha 40+)
- [ ] 1.3. Atualizar `@JsonPropertyOrder` em User.java (linha 11)
- [ ] 1.4. Atualizar construtor parametrizado em User.java (linha 45)
- [ ] 1.5. Adicionar getter de `address` em User.java
- [ ] 1.6. Adicionar setter de `address` em User.java
- [ ] 1.7. Atualizar método `toString()` em User.java (linhas 114-123)
- [ ] 1.8. Adicionar `setAddress` no UserServiceImpl.java (linha 60)
- [ ] 1.9. Compilar código (`mvn clean compile`)

### **Fase 2: Testes Unitários da Entidade** ⏱️ 30 min

- [ ] 2.1. Atualizar UserTest.java - teste do construtor padrão (linha 27)
- [ ] 2.2. Atualizar UserTest.java - teste do construtor parametrizado (linhas 33, 40)
- [ ] 2.3. Atualizar UserTest.java - teste de setters/getters (linhas 52, 59)
- [ ] 2.4. Atualizar UserTest.java - teste toString com valores (linhas 106, 108)
- [ ] 2.5. Atualizar UserTest.java - teste toString com null (linha 114)
- [ ] 2.6. Executar UserTest: `mvn test -Dtest=UserTest`
- [ ] 2.7. Verificar: todos os testes passam ✅

### **Fase 3: Testes de Serviço** ⏱️ 45 min

- [ ] 3.1. Atualizar UserServiceTest.java - setUp (linhas 39-40, 43-44)
- [ ] 3.2. Atualizar todos os construtores User em UserServiceTest.java (~10 ocorrências)
- [ ] 3.3. Executar UserServiceTest: `mvn test -Dtest=UserServiceTest`
- [ ] 3.4. Verificar: todos os testes passam ✅

### **Fase 4: Testes de Repositório** ⏱️ 15 min

- [ ] 4.1. Atualizar UserRepositoryTest.java - setUp (linhas 29-30)
- [ ] 4.2. Adicionar assert de address (linha 43)
- [ ] 4.3. Atualizar construtores restantes (~3 ocorrências)
- [ ] 4.4. Executar UserRepositoryTest: `mvn test -Dtest=UserRepositoryTest`
- [ ] 4.5. Verificar: todos os testes passam ✅

### **Fase 5: Testes de Controller** ⏱️ 1 hora

- [ ] 5.1. Atualizar UserControllerIntegrationTest.java - setUp (linhas 51-52)
- [ ] 5.2. Adicionar assert JSON de address (linha 66)
- [ ] 5.3. Atualizar todos os construtores (~8 ocorrências)
- [ ] 5.4. Atualizar UserControllerUnitTest.java - setUp (linhas 37-38)
- [ ] 5.5. Atualizar todos os construtores (~14 ocorrências)
- [ ] 5.6. Executar testes: `mvn test -Dtest=UserController*Test`
- [ ] 5.7. Verificar: todos os testes passam ✅

### **Fase 6: Integração Completa** ⏱️ 30 min

- [ ] 6.1. Executar todos os testes: `mvn test`
- [ ] 6.2. Verificar cobertura de código: `mvn jacoco:report`
- [ ] 6.3. Garantir cobertura > 90%
- [ ] 6.4. Executar build completo: `mvn clean install`
- [ ] 6.5. Verificar: build passa sem erros ✅

### **Fase 7: Banco de Dados** ⏱️ 15 min

- [ ] 7.1. Criar script de migração SQL
- [ ] 7.2. Testar migration em banco local
- [ ] 7.3. Validar schema atualizado
- [ ] 7.4. Documentar rollback se necessário

### **Fase 8: Testes Manuais** ⏱️ 30 min

- [ ] 8.1. Iniciar aplicação: `mvn spring-boot:run`
- [ ] 8.2. Testar POST /api/users com campo address
- [ ] 8.3. Testar GET /api/users (verificar address no JSON)
- [ ] 8.4. Testar PUT /api/users/{id} atualizando address
- [ ] 8.5. Testar GET /api/users/{id} (verificar address atualizado)
- [ ] 8.6. Validar que registros antigos retornam address: null

### **Fase 9: Revisão e Documentação** ⏱️ 30 min

- [ ] 9.1. Revisar todas as alterações no código
- [ ] 9.2. Atualizar README.md se necessário
- [ ] 9.3. Atualizar documentação da API (Swagger/OpenAPI)
- [ ] 9.4. Revisar commits: mensagens descritivas
- [ ] 9.5. Preparar Pull Request

### **Fase 10: Code Review e Merge** ⏱️ variável

- [ ] 10.1. Criar Pull Request no GitHub/GitLab
- [ ] 10.2. Solicitar code review
- [ ] 10.3. Ajustar feedback recebido
- [ ] 10.4. Merge para branch principal (main/master)
- [ ] 10.5. Deploy em ambiente de desenvolvimento
- [ ] 10.6. Validação final em DEV

---

## 🧪 ESTRATÉGIA DE TESTES

### **Testes Unitários**

| Classe de Teste | Cenários Cobertos | Linhas Afetadas |
|-----------------|-------------------|-----------------|
| UserTest | Construtores, getters, setters, toString | 8 testes |
| UserServiceTest | Criação e atualização de usuários | 12 testes |
| UserControllerUnitTest | Endpoints REST | 15 testes |

### **Testes de Integração**

| Classe de Teste | Cenários Cobertos | Tipo |
|-----------------|-------------------|------|
| UserControllerIntegrationTest | API end-to-end | Integration |
| UserRepositoryTest | Persistência JPA | DataJpaTest |

### **Comandos de Teste**

```bash
# Executar todos os testes
mvn test

# Executar apenas testes da entidade User
mvn test -Dtest=UserTest

# Executar testes de serviço
mvn test -Dtest=UserServiceTest

# Executar testes de controller
mvn test -Dtest=UserController*Test

# Executar testes com relatório de cobertura
mvn clean test jacoco:report

# Ver relatório de cobertura
# Abrir: target/site/jacoco/index.html
```

### **Meta de Cobertura**

- **Atual:** > 90%
- **Após alterações:** Manter > 90%
- **Classes afetadas:** User, UserServiceImpl

---

## ⚠️ RISCOS E CONSIDERAÇÕES

### **1. Breaking Changes**

**🔴 ALTO RISCO**

- **Problema:** Alteração na assinatura do construtor User
- **Impacto:** Quebra TODOS os testes existentes (42 construtores)
- **Mitigação:** Manter construtor padrão sem parâmetros funcionando

**Solução alternativa (NÃO recomendada):**

```java
// Criar sobrecarga do construtor (manter ambos)
public User(String fullName, String email, String phone, LocalDate birthDate, UserType userType) {
    this(fullName, email, phone, birthDate, userType, null);
}

public User(String fullName, String email, String phone, LocalDate birthDate, UserType userType, String address) {
    this.fullName = fullName;
    this.email = email;
    this.phone = phone;
    this.birthDate = birthDate;
    this.userType = userType;
    this.address = address;
}
```

**⚠️ Atenção:** Esta abordagem pode causar confusão. Recomenda-se atualizar todos os testes.

---

### **2. Dados Legados**

**🟡 MÉDIO RISCO**

- **Problema:** Usuários existentes no banco terão address = NULL
- **Impacto:** API retornará `"address": null` para usuários antigos
- **Mitigação:**
  - Aceitar como comportamento normal
  - OU implementar valor padrão ("Não informado")
  - OU criar script de migração de dados

---

### **3. Validações**

**🟢 BAIXO RISCO**

- **Problema:** Campo address não possui validações
- **Impacto:** Usuário pode enviar strings vazias ou muito longas
- **Mitigação (opcional):** Adicionar validações

```java
@Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
@Column(name = "address", length = 500)
private String address;
```

---

### **4. Performance**

**🟢 BAIXO RISCO**

- **Problema:** Aumento no tamanho dos objetos User em memória
- **Impacto:** Desprezível (um campo String adicional)
- **Mitigação:** Não necessária

---

### **5. Compatibilidade de API**

**🟢 BAIXO RISCO**

- **Problema:** Clientes antigos da API não enviarão o campo address
- **Impacto:** Jackson irá deixar address como null (comportamento padrão)
- **Mitigação:** Não necessária (retrocompatível)

---

## 📱 EXEMPLOS DE USO DA API

### **POST - Criar usuário COM address**

**Request:**

```http
POST /api/users HTTP/1.1
Content-Type: application/json

{
  "fullName": "João Silva",
  "email": "joao@email.com",
  "phone": "+55 11 99999-9999",
  "birthDate": "1990-05-15",
  "userType": "ADMIN",
  "address": "Rua das Flores, 123 - São Paulo/SP"
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "fullName": "João Silva",
  "email": "joao@email.com",
  "phone": "+55 11 99999-9999",
  "birthDate": "1990-05-15",
  "userType": "ADMIN",
  "address": "Rua das Flores, 123 - São Paulo/SP"
}
```

---

### **POST - Criar usuário SEM address (retrocompatível)**

**Request:**

```http
POST /api/users HTTP/1.1
Content-Type: application/json

{
  "fullName": "Maria Santos",
  "email": "maria@email.com",
  "phone": "+55 11 88888-8888",
  "birthDate": "1985-08-20",
  "userType": "EDITOR"
}
```

**Response (201 Created):**

```json
{
  "id": 2,
  "fullName": "Maria Santos",
  "email": "maria@email.com",
  "phone": "+55 11 88888-8888",
  "birthDate": "1985-08-20",
  "userType": "EDITOR",
  "address": null
}
```

---

### **GET - Buscar todos os usuários**

**Request:**

```http
GET /api/users HTTP/1.1
```

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "fullName": "João Silva",
    "email": "joao@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-05-15",
    "userType": "ADMIN",
    "address": "Rua das Flores, 123 - São Paulo/SP"
  },
  {
    "id": 2,
    "fullName": "Maria Santos",
    "email": "maria@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1985-08-20",
    "userType": "EDITOR",
    "address": null
  }
]
```

---

### **PUT - Atualizar usuário incluindo address**

**Request:**

```http
PUT /api/users/2 HTTP/1.1
Content-Type: application/json

{
  "fullName": "Maria Santos Silva",
  "email": "maria.santos@email.com",
  "phone": "+55 11 88888-8888",
  "birthDate": "1985-08-20",
  "userType": "EDITOR",
  "address": "Av. Paulista, 1000 - São Paulo/SP"
}
```

**Response (200 OK):**

```json
{
  "id": 2,
  "fullName": "Maria Santos Silva",
  "email": "maria.santos@email.com",
  "phone": "+55 11 88888-8888",
  "birthDate": "1985-08-20",
  "userType": "EDITOR",
  "address": "Av. Paulista, 1000 - São Paulo/SP"
}
```

---

## 🔍 VALIDAÇÕES E REGRAS DE NEGÓCIO

### **Regras Atuais (não afetadas)**

| Campo | Validação | Mensagem |
|-------|-----------|----------|
| fullName | @NotBlank | "Nome completo é obrigatório" |
| email | @Email, @NotBlank, unique | "Email deve ter um formato válido" |
| phone | @NotBlank, @Pattern | "Telefone deve estar no formato internacional" |
| birthDate | @Past, @NotNull | "Data de nascimento deve estar no passado" |
| userType | @NotNull | "Tipo de usuário é obrigatório" |

### **Novo Campo**

| Campo | Validação | Mensagem |
|-------|-----------|----------|
| address | Nenhuma (nullable) | - |

**Validações opcionais a considerar:**

```java
// Opção 1: Tamanho máximo
@Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
private String address;

// Opção 2: Não vazio quando informado
@Pattern(regexp = "^$|.{5,}", message = "Endereço deve ter pelo menos 5 caracteres quando informado")
private String address;

// Opção 3: Tornar obrigatório
@NotBlank(message = "Endereço é obrigatório")
private String address;
```

---

## 📊 IMPACTO NO SCHEMA DO BANCO DE DADOS

### **Antes da Alteração**

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    user_type VARCHAR(50) NOT NULL
);
```

### **Depois da Alteração**

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    address VARCHAR(255) NULL  -- ⬅️ NOVA COLUNA
);
```

### **Análise de Impacto**

- **Tamanho do registro:** +255 bytes (máximo)
- **Índices afetados:** Nenhum
- **Chaves estrangeiras:** Nenhuma
- **Triggers:** Nenhum
- **Views:** Revisar se existem views que selecionam users.*

---

## 🔄 PLANO DE ROLLBACK

### **Em caso de problemas após deploy:**

#### **1. Rollback de Código**

```bash
# Reverter commits
git revert <commit-hash>

# OU voltar para versão anterior
git checkout <tag-anterior>

# Rebuild e redeploy
mvn clean install
```

#### **2. Rollback de Banco de Dados**

```sql
-- Remover coluna address
ALTER TABLE users DROP COLUMN address;
```

**⚠️ ATENÇÃO:**
- Rollback de banco APAGA dados do campo address
- Fazer backup antes de qualquer alteração em produção
- Considerar manter coluna e apenas fazer rollback do código

#### **3. Estratégia Recomendada**

1. Deploy apenas do código (SEM migration de DB)
2. Validar em produção por 24h
3. Se tudo OK, executar migration de DB
4. Validar novamente

---

## 📈 MÉTRICAS DE SUCESSO

### **Critérios de Aceitação**

- [ ] ✅ Todos os testes unitários passam (> 90% cobertura)
- [ ] ✅ Todos os testes de integração passam
- [ ] ✅ Build Maven completa sem erros
- [ ] ✅ Campo address aparece no JSON da API
- [ ] ✅ POST /api/users aceita campo address
- [ ] ✅ PUT /api/users atualiza campo address
- [ ] ✅ GET /api/users retorna campo address
- [ ] ✅ Registros antigos retornam address: null
- [ ] ✅ Registros novos salvam address corretamente
- [ ] ✅ Migration de banco executa sem erros

### **Métricas Técnicas**

| Métrica | Valor Esperado | Como Medir |
|---------|----------------|------------|
| Cobertura de Testes | > 90% | `mvn jacoco:report` |
| Tempo de Build | < 5 min | GitHub Actions / Jenkins |
| Tempo de Testes | < 2 min | `mvn test` |
| Tamanho do código | +63 linhas | `git diff --stat` |
| Classes modificadas | 7 | Git changes |

---

## 📚 REFERÊNCIAS TÉCNICAS

### **Documentação Utilizada**

- Spring Boot Documentation: https://spring.io/projects/spring-boot
- JPA/Hibernate: https://hibernate.org/orm/documentation/
- Jackson JSON: https://github.com/FasterXML/jackson-docs
- JUnit 5: https://junit.org/junit5/docs/current/user-guide/
- Mockito: https://javadoc.io/doc/org.mockito/mockito-core/latest/

### **Padrões Seguidos**

- Clean Code (Robert C. Martin)
- RESTful API Best Practices
- Test-Driven Development (TDD)
- Java Naming Conventions

---

## 👥 EQUIPE E RESPONSABILIDADES

| Papel | Responsável | Tarefas |
|-------|-------------|---------|
| **Developer** | [Nome] | Implementação do código |
| **Tester** | [Nome] | Validação de testes |
| **Tech Lead** | [Nome] | Code review |
| **DBA** | [Nome] | Migration de banco |
| **DevOps** | [Nome] | Deploy e monitoramento |

---

## 📅 CRONOGRAMA SUGERIDO

| Dia | Atividade | Duração |
|-----|-----------|---------|
| **D1** | Implementação código produção + testes entidade | 2h |
| **D1** | Atualização testes de serviço e repositório | 1h |
| **D2** | Atualização testes de controller | 1h |
| **D2** | Testes manuais e ajustes | 1h |
| **D3** | Code review e ajustes | 2h |
| **D3** | Migration de banco + deploy DEV | 1h |
| **D4** | Validação em DEV | 2h |
| **D5** | Deploy PROD (se aprovado) | 1h |

**Total estimado: 11 horas (distribuídas em 5 dias)**

---

## ✉️ COMUNICAÇÃO

### **Stakeholders a Notificar**

- [ ] Equipe de Desenvolvimento
- [ ] Equipe de QA
- [ ] Product Owner
- [ ] Equipe de DevOps
- [ ] DBA (para migration)
- [ ] Time de Suporte (sobre nova funcionalidade)

### **Canais de Comunicação**

- Slack: #tech-team
- JIRA: TECH-XXX
- Confluence: Documentação técnica
- Email: tech-team@company.com

---

## 🎯 CONCLUSÃO

A adição do campo `address` na entidade User é uma alteração de **complexidade média** que requer atenção especial aos testes (42 construtores a serem atualizados).

**Principais pontos de atenção:**
1. ✅ Atualizar construtor parametrizado (quebra compatibilidade de testes)
2. ✅ Garantir que UserServiceImpl.updateUser() inclua setAddress()
3. ✅ Atualizar TODOS os testes (63 alterações no total)
4. ✅ Criar e testar migration de banco de dados
5. ✅ Validar comportamento com dados legados (address = null)

**Recomendação:** Implementar em etapas seguindo o checklist, validando cada fase antes de prosseguir.

---

**Documento gerado em:** 2025-10-07
**Versão:** 1.0
**Status:** 📝 Em revisão
**Próxima revisão:** Após implementação

---

## 📎 ANEXOS

### **Anexo A: Exemplo de Commit Message**

```
feat: adiciona campo address na entidade User

- Adiciona campo address (String) na entidade User
- Atualiza UserServiceImpl para persistir address no update
- Atualiza todos os testes unitários e de integração
- Adiciona migration V2__add_address_to_users.sql

BREAKING CHANGE: Construtor User agora requer parâmetro address

Refs: TECH-XXX
```

### **Anexo B: Exemplo de Pull Request**

**Título:** `[TECH-XXX] Adiciona campo address na entidade User`

**Descrição:**

```markdown
## 📝 Descrição

Adiciona o campo `address` (String, nullable) na entidade User para permitir armazenamento de endereço dos usuários.

## 🔧 Alterações

- ✅ User.java: novo campo + getter/setter
- ✅ UserServiceImpl.java: atualização do método update
- ✅ Migration SQL: V2__add_address_to_users.sql
- ✅ Todos os testes atualizados (63 alterações)

## 🧪 Testes

- ✅ Todos os testes unitários passam
- ✅ Todos os testes de integração passam
- ✅ Cobertura de código: 92%

## 📊 Impacto

- 7 arquivos modificados
- +11 linhas de código de produção
- +52 linhas de testes atualizadas

## 🔍 Checklist

- [x] Código implementado
- [x] Testes atualizados
- [x] Build passa
- [x] Migration criada
- [x] Documentação atualizada

## 👀 Reviewers

@tech-lead @senior-dev
```

---

**FIM DO DOCUMENTO**
