Desafio de Desenvolvimento Back-End com Spring Boot

Contexto

Você foi contratado para desenvolver a API de backend do sistema TechManage, uma aplicação para gerenciar usuários. O objetivo é criar uma API RESTful utilizando Spring Boot que permita realizar operações básicas de gerenciamento de usuários, conectando a aplicação a um banco de dados relacional.

 

Objetivos do Desafio

Criar uma API RESTful com Spring Boot que implemente as seguintes operações:
Criar um novo usuário.
Buscar todos os usuários.
Buscar um usuário por ID.
Atualizar as informações de um usuário existente.
Excluir um usuário.
Conectar a aplicação a um banco de dados relacional de sua preferência (ex.: MySQL, PostgreSQL, H2, etc.).
Implementar validações para os dados recebidos na API.
Configurar o projeto para ser executado e testado localmente, com instruções no README.md.
Publicar o projeto em um repositório público no GitHub.
 

Requisitos Técnicos

Entidade User:
Atributos obrigatórios:
id (Long): Gerado automaticamente.
fullName (String): Nome completo do usuário.
email (String): Único, validado como um e-mail.
phone (String): Número de telefone no formato internacional (ex.: +55 11 99999-9999).
birthDate (Date): Data de nascimento.
userType (String): Enum com valores possíveis: ADMIN, EDITOR, VIEWER.
Operações REST:
POST /api/users: Adiciona um novo usuário.
O corpo da requisição deve conter os dados do usuário.
Deve retornar o usuário criado, incluindo o ID gerado.
GET /api/users: Retorna todos os usuários.
GET /api/users/{id}: Retorna os dados de um usuário específico.
Deve retornar erro HTTP 404 caso o ID não exista.
PUT /api/users/{id}: Atualiza os dados de um usuário.
Deve validar os campos enviados.
DELETE /api/users/{id}: Exclui um usuário.
Deve retornar erro HTTP 404 caso o ID não exista.
Banco de Dados:
Use um banco de dados relacional da sua preferência.
Configure a aplicação para rodar com Spring Data JPA.
Inclua scripts SQL para criar as tabelas e, opcionalmente, dados iniciais no banco.
Validações:
Utilize Bean Validation para validar os dados recebidos na API (ex.: e-mail válido, nome não vazio, etc.).
Testes:
Crie testes unitários para os serviços.
Crie ao menos um teste de integração para os endpoints da API.
Documentação:
Inclua no README.md:
Passos para rodar o projeto localmente.
Exemplos de requisições para cada endpoint.
 

Critérios de Avaliação

Qualidade do Código:
Boas práticas de Java e Spring Boot.
Uso correto de camadas (Controller, Service, Repository).
Organização e modularização do código.
A entrega de testes unitários será um diferencial.
Banco de Dados:
Estrutura das tabelas.
Uso correto do JPA.
Validações e Tratamento de Erros:
Implementação de validações.
Respostas claras e adequadas para erros (ex.: 404, 400).
Documentação:
Clareza e completude do README.md.
Facilidade para configurar e rodar o projeto.
GitHub:
Estrutura do repositório.
Histórico de commits (commits organizados e mensagens claras).
Envie o link do repositório no GitHub como resposta a esse e-mail.
 

Instruções de Entrega

Crie um repositório público no GitHub.
Implemente a solução seguindo os requisitos.
Garanta que o projeto possa ser executado localmente seguindo as instruções do README.md.
Envie o link do repositório no GitHub.