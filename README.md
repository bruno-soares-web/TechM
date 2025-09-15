# TechManage API

API RESTful para gerenciamento de usuários desenvolvida com Spring Boot.

## 📋 Sobre o Projeto

O TechManage é uma aplicação backend que permite realizar operações CRUD (Create, Read, Update, Delete) para gerenciamento de usuários. A API foi desenvolvida seguindo boas práticas de arquitetura em camadas e inclui validações, tratamento de erros e testes automatizados.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
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
│   └── resources/
│       ├── application.properties
│       ├── schema.sql      # Script de criação das tabelas
│       └── data.sql        # Dados iniciais (opcional)
└── test/
    └── java/com/techmanage/
        ├── service/        # Testes unitários
        └── controller/     # Testes de integração
```

## 🏗️ Arquitetura

A aplicação segue o padrão de arquitetura em camadas:

- **Controller**: Responsável por receber as requisições HTTP e retornar as respostas
- **Service**: Contém a lógica de negócio e validações
- **Repository**: Interface para operações de persistência de dados
- **Entity**: Representação das entidades do banco de dados

## 📋 Pré-requisitos

- Java 17 ou superior
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

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| POST   | `/api/users` | Criar novo usuário | 201 |
| GET    | `/api/users` | Listar todos os usuários | 200 |
| GET    | `/api/users/{id}` | Buscar usuário por ID | 200/404 |
| PUT    | `/api/users/{id}` | Atualizar usuário | 200/404 |
| DELETE | `/api/users/{id}` | Excluir usuário | 204/404 |

## 📝 Modelo de Dados

### Entidade User

```json
{
  "id": 1,
  "fullName": "João Silva",
  "email": "joao@email.com",
  "phone": "+5511999999999",
  "birthDate": "1990-05-15",
  "userType": "ADMIN"
}
```

### Campos Obrigatórios

- **id**: Gerado automaticamente
- **fullName**: Nome completo (não pode estar vazio)
- **email**: Email único e válido
- **phone**: Telefone no formato internacional (ex: +55 11 99999-9999)
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
    "phone": "+5511999999999",
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
  "phone": "+5511999999999",
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
    "phone": "+5511999999999",
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
  "phone": "+5511999999999",
  "birthDate": "1990-05-15",
  "userType": "ADMIN"
}
```

**Usuário não encontrado (404 Not Found):**
```json
{
  "message": "Usuário não encontrado com ID: 999",
  "status": 404,
  "timestamp": "2024-01-15T10:30:00"
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
    "phone": "+5511888888888",
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
  "phone": "+5511888888888",
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

A API retorna códigos HTTP apropriados e mensagens de erro claras:

### Códigos de Status

- **200 OK**: Operação realizada com sucesso
- **201 Created**: Usuário criado com sucesso
- **204 No Content**: Usuário excluído com sucesso
- **400 Bad Request**: Dados inválidos ou malformados
- **404 Not Found**: Usuário não encontrado
- **409 Conflict**: Email já está em uso
- **500 Internal Server Error**: Erro interno do servidor

### Exemplo de Erro de Validação (400)

```json
{
  "message": "fullName: Nome completo é obrigatório, email: Email deve ter formato válido",
  "status": 400,
  "timestamp": "2024-01-15T10:30:00"
}
```

## 🧪 Validações Implementadas

- **fullName**: Não pode estar vazio
- **email**: Deve ter formato válido e ser único
- **phone**: Deve seguir padrão internacional (+XX XXXXXXXXX)
- **birthDate**: Deve ser uma data no passado
- **userType**: Deve ser ADMIN, EDITOR ou VIEWER

## 📊 Dados de Teste

A aplicação vem com alguns usuários pré-cadastrados para facilitar os testes:

1. **Admin do Sistema**
   - Email: admin@techmanage.com
   - Tipo: ADMIN

2. **Editor Principal**
   - Email: editor@techmanage.com
   - Tipo: EDITOR

3. **Visualizador Teste**
   - Email: viewer@techmanage.com
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