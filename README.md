# TechManage API

API RESTful para gerenciamento de usuários desenvolvida com Spring Boot.

## 📋 Sobre o Projeto

O TechManage é uma aplicação backend que permite realizar operações CRUD (Create, Read, Update, Delete) para gerenciamento de usuários. A API foi desenvolvida seguindo boas práticas de arquitetura em camadas e inclui validações, tratamento de erros e testes automatizados.

## 🚀 Tecnologias Utilizadas

- **Java 8**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **Spring Validation**
- **H2 Database** (em memória)
- **Maven**
- **JUnit 5**
- **Mockito**

## 📦 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/techmanage/
│   │   ├── controller/     # Controladores REST
│   │   ├── service/        # Lógica de negócio
│   │   ├── repository/     # Camada de acesso aos dados
│   │   ├── entity/         # Entidades JPA
│   │   └── exception/      # Tratamento de exceções
│   │       ├── GlobalExceptionHandler.java
│   │       ├── UserNotFoundException.java
│   │       ├── EmailAlreadyExistsException.java
│   │       └── PhoneAlreadyExistsException.java
│   └── resources/
│       ├── application.properties
│       ├── schema.sql      # Script de criação das tabelas
│       └── data.sql        # Dados iniciais (opcional)
└── test/
    └── java/com/techmanage/
        ├── TechManageApplicationTest.java           # Testes da aplicação
        ├── TechManageApplicationContextTest.java    # Testes de contexto
        ├── TechManageApplicationIntegrationTest.java # Testes de integração da aplicação
        ├── service/        # Testes unitários
        │   └── UserServiceTest.java
        ├── controller/     # Testes de integração
        │   ├── UserControllerIntegrationTest.java
        │   └── ComprehensiveUserApiTest.java
        └── exception/      # Testes de exceções
```

## 🏗️ Arquitetura

A aplicação segue o padrão de arquitetura em camadas:

- **Controller**: Responsável por receber as requisições HTTP e retornar as respostas
- **Service**: Contém a lógica de negócio e validações
- **Repository**: Interface para operações de persistência de dados
- **Entity**: Representação das entidades do banco de dados

## 📋 Pré-requisitos

- Java 8 ou superior (JDK recomendado)
- Maven 3.6+

## 🔧 Como Executar o Projeto

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd TechManage
```

### 2. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 3. Acesse o Console H2 (opcional)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:techmanage`
- Username: `sa`
- Password: (deixe em branco)

## 🧪 Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar apenas testes unitários
mvn test -Dtest="*Test"

# Executar apenas testes de integração
mvn test -Dtest="*IntegrationTest"
```

## 📚 Endpoints da API

### Base URL: `http://localhost:8080/api/users`

| Método | Endpoint | Descrição | Status Sucesso | Status Erro |
|--------|----------|-----------|----------------|-------------|
| POST   | `/api/users` | Criar novo usuário | 201 Created | 400 Bad Request |
| GET    | `/api/users` | Listar todos os usuários | 200 OK | - |
| GET    | `/api/users/{id}` | Buscar usuário por ID | 200 OK | 404 Not Found |
| PUT    | `/api/users/{id}` | Atualizar usuário | 200 OK | 400 Bad Request / 404 Not Found |
| DELETE | `/api/users/{id}` | Excluir usuário | 204 No Content | 404 Not Found |

## 📝 Modelo de Dados

### Entidade User

```json
{
  "id": 1,
  "fullName": "João Silva",
  "email": "joao@email.com",
  "phone": "+55 11 99999-9999",
  "birthDate": "1990-05-15",
  "userType": "ADMIN"
}
```

### Campos Obrigatórios

- **id**: Gerado automaticamente
- **fullName**: Nome completo (não pode estar vazio)
- **email**: Email único e válido
- **phone**: Telefone único no formato internacional obrigatório (ex: +55 11 99999-9999)
- **birthDate**: Data de nascimento (deve ser no passado)
- **userType**: Tipo do usuário (ADMIN, EDITOR, VIEWER)

## 🔍 Exemplos de Uso

### 1. Criar Usuário

**Requisição:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "João Silva",
    "email": "joao@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-05-15",
    "userType": "ADMIN"
  }'
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "fullName": "João Silva",
  "email": "joao@email.com",
  "phone": "+55 11 99999-9999",
  "birthDate": "1990-05-15",
  "userType": "ADMIN"
}
```

### 2. Listar Todos os Usuários

**Requisição:**
```bash
curl -X GET http://localhost:8080/api/users
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "fullName": "João Silva",
    "email": "joao@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-05-15",
    "userType": "ADMIN"
  }
]
```

### 3. Buscar Usuário por ID

**Requisição:**
```bash
curl -X GET http://localhost:8080/api/users/1
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "fullName": "João Silva",
  "email": "joao@email.com",
  "phone": "+55 11 99999-9999",
  "birthDate": "1990-05-15",
  "userType": "ADMIN"
}
```

**Usuário não encontrado (404 Not Found):**
```json
{
  "fieldErrors": {
    "id": "Usuário não encontrado com ID: 999"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 404,
  "error": "Recurso não encontrado",
  "path": "/api/users/999"
}
```

### 4. Atualizar Usuário

**Requisição:**
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "João Santos",
    "email": "joao.santos@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1990-05-15",
    "userType": "EDITOR"
  }'
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "fullName": "João Santos",
  "email": "joao.santos@email.com",
  "phone": "+55 11 88888-8888",
  "birthDate": "1990-05-15",
  "userType": "EDITOR"
}
```

### 5. Excluir Usuário

**Requisição:**
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

**Resposta (204 No Content)**

## ⚠️ Tratamento de Erros

A API retorna códigos HTTP apropriados e mensagens de erro claras em inglês, seguindo um padrão consistente:

### Códigos de Status

#### Sucesso
- **200 OK**: Operação de consulta ou atualização realizada com sucesso
- **201 Created**: Usuário criado com sucesso
- **204 No Content**: Usuário excluído com sucesso

#### Erro
- **400 Bad Request**: Dados inválidos, malformados ou duplicados
- **404 Not Found**: Recurso não encontrado
- **500 Internal Server Error**: Erro interno do servidor

### Formato Padrão de Erro

Todos os erros seguem o mesmo formato com `fieldErrors`, `timestamp` e `path`:

```json
{
  "fieldErrors": {
    "campo": "Mensagem específica do erro"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

### Cenários de Erro Completos

#### 1. Validação de Campos Obrigatórios (400 Bad Request)

```json
{
  "fieldErrors": {
    "fullName": "Nome completo é obrigatório",
    "email": "Email deve ter um formato válido",
    "phone": "Telefone deve estar no formato internacional (ex: +55 11 99999-9999)",
    "birthDate": "Data de nascimento deve estar no passado",
    "userType": "Tipo de usuário é obrigatório"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### 2. Email Já Existe (400 Bad Request)

```json
{
  "fieldErrors": {
    "email": "Email já está em uso"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### 3. Telefone Já Existe (400 Bad Request)

```json
{
  "fieldErrors": {
    "phone": "Telefone já está em uso"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### 4. Tipo de Usuário Inválido (400 Bad Request)

```json
{
  "fieldErrors": {
    "userType": "Tipo de usuário inválido. Valores aceitos: ADMIN, EDITOR, VIEWER"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### 5. Usuário Não Encontrado (404 Not Found)

```json
{
  "fieldErrors": {
    "id": "Usuário não encontrado com ID: 999"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 404,
  "error": "Recurso não encontrado",
  "path": "/api/users/999"
}
```

#### 6. JSON Malformado (400 Bad Request)

```json
{
  "fieldErrors": {
    "request": "Formato JSON inválido"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

## 🧪 Validações Implementadas

### Campos Obrigatórios e Regras

| Campo | Validação | Mensagem de Erro (Português) |
|-------|-----------|---------------------------|
| **fullName** | Não pode estar vazio | "Nome completo é obrigatório" |
| **email** | Formato válido e único | "Email deve ter um formato válido" / "Email já está em uso" |
| **phone** | Formato internacional obrigatório e único: +XX XX XXXXX-XXXX | "Telefone deve estar no formato internacional (ex: +55 11 99999-9999)" / "Telefone já está em uso" |
| **birthDate** | Deve ser uma data no passado | "Data de nascimento deve estar no passado" |
| **userType** | Deve ser ADMIN, EDITOR ou VIEWER | "Tipo de usuário é obrigatório" / "Tipo de usuário inválido. Valores aceitos: ADMIN, EDITOR, VIEWER" |
| **id** | Deve existir para operações de busca/atualização/exclusão | "Usuário não encontrado com ID: {id}" |

### Regras de Formatação

#### Telefone
- **Formato obrigatório**: `+XX XX XXXXX-XXXX`
- **Exemplo válido**: `+55 11 99999-9999`
- Código do país obrigatório
- Espaços obrigatórios entre código do país e área
- Hífen obrigatório antes dos últimos 4 dígitos

#### UserType
- **Valores aceitos**: `ADMIN`, `EDITOR`, `VIEWER`
- Sensível a maiúsculas/minúsculas
- Qualquer outro valor retorna erro de validação

#### JSON Response Order
Os campos no JSON de resposta sempre aparecem na ordem:
1. `id`
2. `fullName`
3. `email`
4. `phone` (sempre formatado como +XX XX XXXXX-XXXX)
5. `birthDate`
6. `userType`

### Tratamento de Erros por Campo
Os erros de validação sempre retornam todos os campos inválidos de uma vez, organizados na ordem: `fullName`, `email`, `phone`, `birthDate`, `userType`.

## 📋 Exemplos Completos de Cenários

### Cenários de Sucesso

#### ✅ Criar usuário válido
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Maria Silva",
    "email": "maria@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1985-03-20",
    "userType": "EDITOR"
  }'
```

**Resposta (201 Created):**
```json
{
  "id": 2,
  "fullName": "Maria Silva",
  "email": "maria@email.com",
  "phone": "+55 11 88888-8888",
  "birthDate": "1985-03-20",
  "userType": "EDITOR"
}
```

#### ✅ Listar usuários (lista vazia)
```bash
curl -X GET http://localhost:8080/api/users
```

**Resposta (200 OK):**
```json
[]
```

#### ✅ Atualizar usuário existente
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "João Santos Updated",
    "email": "joao.updated@email.com",
    "phone": "+55 11 77777-7777",
    "birthDate": "1990-05-15",
    "userType": "VIEWER"
  }'
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "fullName": "João Santos Updated",
  "email": "joao.updated@email.com",
  "phone": "+55 11 77777-7777",
  "birthDate": "1990-05-15",
  "userType": "VIEWER"
}
```

### Cenários de Erro

#### ❌ Dados inválidos em múltiplos campos
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "",
    "email": "email-invalido",
    "phone": "telefone-invalido",
    "birthDate": "2030-01-01",
    "userType": null
  }'
```

**Resposta (400 Bad Request):**
```json
{
  "fieldErrors": {
    "fullName": "Nome completo é obrigatório",
    "email": "Email deve ter um formato válido",
    "phone": "Telefone deve estar no formato internacional (ex: +55 11 99999-9999)",
    "birthDate": "Data de nascimento deve estar no passado",
    "userType": "Tipo de usuário é obrigatório"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### ❌ Email duplicado
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Outro Usuario",
    "email": "maria@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-01-01",
    "userType": "ADMIN"
  }'
```

**Resposta (400 Bad Request):**
```json
{
  "fieldErrors": {
    "email": "Email já está em uso"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### ❌ Telefone duplicado
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Novo Usuario",
    "email": "novo@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1992-06-15",
    "userType": "VIEWER"
  }'
```

**Resposta (400 Bad Request):**
```json
{
  "fieldErrors": {
    "phone": "Telefone já está em uso"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### ❌ Tipo de usuário inválido
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@email.com",
    "phone": "+55 11 66666-6666",
    "birthDate": "1995-12-25",
    "userType": "INVALID_TYPE"
  }'
```

**Resposta (400 Bad Request):**
```json
{
  "fieldErrors": {
    "userType": "Tipo de usuário inválido. Valores aceitos: ADMIN, EDITOR, VIEWER"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 400,
  "error": "Erro de validação",
  "path": "/api/users"
}
```

#### ❌ Buscar usuário inexistente
```bash
curl -X GET http://localhost:8080/api/users/999
```

**Resposta (404 Not Found):**
```json
{
  "fieldErrors": {
    "id": "Usuário não encontrado com ID: 999"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 404,
  "error": "Recurso não encontrado",
  "path": "/api/users/999"
}
```

#### ❌ Atualizar usuário inexistente
```bash
curl -X PUT http://localhost:8080/api/users/999 \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test Update",
    "email": "update@email.com",
    "phone": "+55 11 55555-5555",
    "birthDate": "1988-07-10",
    "userType": "ADMIN"
  }'
```

**Resposta (404 Not Found):**
```json
{
  "fieldErrors": {
    "id": "Usuário não encontrado com ID: 999"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 404,
  "error": "Recurso não encontrado",
  "path": "/api/users/999"
}
```

#### ❌ Excluir usuário inexistente
```bash
curl -X DELETE http://localhost:8080/api/users/999
```

**Resposta (404 Not Found):**
```json
{
  "fieldErrors": {
    "id": "Usuário não encontrado com ID: 999"
  },
  "timestamp": "2024-01-15T10:30:45.123",
  "status": 404,
  "error": "Recurso não encontrado",
  "path": "/api/users/999"
}
```

## 📊 Dados de Teste

A aplicação vem com alguns usuários pré-cadastrados para facilitar os testes:

1. **Admin do Sistema**
   - Email: admin@techmanage.com
   - Phone: +55 11 99999-9999
   - Tipo: ADMIN

2. **Editor Principal**
   - Email: editor@techmanage.com
   - Phone: +55 11 88888-8888
   - Tipo: EDITOR

3. **Visualizador Teste**
   - Email: viewer@techmanage.com
   - Phone: +55 11 77777-7777
   - Tipo: VIEWER

## 🚀 Build e Empacotamento

```bash
# Gerar o JAR da aplicação
mvn clean package

# Executar o JAR gerado
java -jar target/techmanage-api-0.0.1-SNAPSHOT.jar
```

## 🛠️ Configuração do Banco de Dados

Por padrão, a aplicação usa H2 Database em memória. Para usar outro banco de dados, atualize o `application.properties`:

```properties
# Exemplo para MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/techmanage
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## 📄 Licença

Este projeto foi desenvolvido como parte de um desafio técnico.

---

**Desenvolvido com ❤️ usando Spring Boot**