# Tech Challenge - Fase 2 

Sistema para **gestão de clientes, cartões bancários (crédito/débito)** e **contratação de produtos** por meio de uma API RESTful.

Projeto desenvolvido como parte do desafio proposto pela FIAP, integrando conhecimentos em Banco de Dados, Orientação a Objetos, APIs e Design Patterns.

---

## 📌 Funcionalidades

- Cadastro, edição e exclusão de **clientes**
- Cadastro e gerenciamento de **cartões**
- **Contratação de cartões** por clientes
- Acompanhamento do **status da contratação**
- Integração com banco de dados PostgreSQL
- Documentação interativa via Swagger

---

## 🧪 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **SpringDoc OpenAPI (Swagger)**
- **Maven**

---

## 🔧 Como rodar o projeto localmente

### Pré-requisitos
- Java 17
- PostgreSQL rodando localmente
- Maven ou suporte via sua IDE (IntelliJ, VSCode)

### 1. Clone o repositório

git clone https://github.com/Germano-leite/techchallenge.git

### 2. Configure o banco de dados

Crie um banco chamado techchallenge no PostgreSQL e configure o application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/techchallenge
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

### 3. Execute a aplicação

./mvnw spring-boot:run
ou use sua IDE para rodar a classe TechChallengeApplication.
