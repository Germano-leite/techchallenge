Tech Challenge - Fase 2

Sistema para gestão de clientes, cartões bancários (crédito/débito) e contratação de produtos por meio de uma API RESTful.

Projeto desenvolvido como parte do desafio proposto pela FIAP, integrando conhecimentos em Banco de Dados, Orientação a Objetos, APIs e Design Patterns.

##  Funcionalidades  ##
Cadastro, edição, visualização e exclusão de clientes.

Cadastro, edição, visualização e exclusão de tipos de cartões.

Contratação de cartões por clientes, gerando um número de cartão simulado.

Acompanhamento e atualização do status da contratação (Ativo, Cancelado).

Validações de negócio para impedir CPFs duplicados e exclusão de entidades com vínculos.

Documentação interativa da API via Swagger.

## Tecnologias Utilizadas ##
Java 17

Spring Boot 3

Spring Data JPA

PostgreSQL (Banco de Dados Relacional)

Lombok

SpringDoc OpenAPI (Swagger) para documentação da API

Maven como gerenciador de dependências

## Como rodar o projeto localmente ##
Siga os passos abaixo para configurar e executar a aplicação em seu ambiente local.

Pré-requisitos
Java 17 ou superior

Maven 3.6 ou superior

PostgreSQL instalado e rodando localmente


1. Clone o repositório

Bash

git clone https://github.com/Germano-leite/techchallenge.git
cd techchallenge

2. Configure o banco de dados

Crie um banco de dados no PostgreSQL com o nome tech_challenge.

Abra o arquivo src/main/resources/application.properties.

Altere as propriedades spring.datasource.username e spring.datasource.password com as suas credenciais de acesso ao PostgreSQL.

##
//src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/tech_challenge
spring.datasource.username=seu_usuario_aqui
spring.datasource.password=sua_senha_aqui
##
Observação: Não é necessário criar as tabelas manualmente. O Spring Data JPA (com a configuração spring.jpa.hibernate.ddl-auto=update) irá criá-las ou atualizá-las automaticamente na primeira vez que a aplicação for iniciada.

3. Compile Execute a aplicação

//O comando mvn install vai baixar as dependias, compilar e rodar testes de integridade na aplicacao.
Bash

mnv install 

Você pode executar a aplicação de duas formas:

Via linha de comando (usando o Maven Wrapper):

Bash

./mvnw spring-boot:run //No Linux
.\mvnw spring-boot:run //No windows
Via sua IDE:

Importe o projeto como um projeto Maven.

Execute a classe principal TechchallengeApplication.java.

4. Verifique se a aplicação está no ar
Após a inicialização, a aplicação estará disponível em http://localhost:8080
