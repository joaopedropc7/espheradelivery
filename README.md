# SISTEMA DE RESTAURANTE

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](	https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-00FFFF?style=for-the-badge&logo=Docker&logoColor=white)
![GoogleMaps](https://img.shields.io/badge/Google%20Maps-4285F4?style=for-the-badge&logo=google%20maps&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Hateoas](https://img.shields.io/badge/HATEOAS-00FFFF?style=for-the-badge&logo=HATEOAS&logoColor=white)
![Restful](https://img.shields.io/badge/RESTful-00FFFF?style=for-the-badge&logo=RESTful&logoColor=white)
![CORs](https://img.shields.io/badge/CORs-00FFFF?style=for-the-badge&logo=CORs&logoColor=white)

Projeto de gerenciamento de restaurante, tem o objetivo de fornecer um PDV para fazer pedidos, entregas, compras, motoboys, cupons de desconto e calculo de valor da entrega a partir da distancia e valor por KM cadastrado.

## Objetivo

Tem como intuito fornecer um sistema onde o cliente pode fazer o pedido a partir do site e também terá um PDV para o restaurante, as compras é o módulo onde será registrado as compras do estabelecimento, possiblitando dar saldo aos produtos.
O módulo de delivery será utilizado quando o pedido for delivery, assim o estabelecimento poderá selecionar o motoboy para entrega, no módulo de configurações será possível colocar a taxa por KM, foi usado duas APIS do google, uma para obter o PLACEID a partir do endereço fornecido pelo cliente e outra para a partir desse PLACEID obter a distancia e duração da viagem, enviando para API o PLACEID do destino e do estabelecimento.
Conta também com um módulo para descontos, onde o estabelecimento pode cadastrar um cupom, com a porcentagem do desconto, data de inicio e data de expiração.

## Arquitetura

Este projeto contará com um banco de dados centralizado para armazenar os dados de todas as empresas, onde cada registro no banco de dados contará com o ID da empresa para diferenciar os dados, foi optado por essa arquitetura para facilidade de manutenção e versionamento, sendo assim cada requisição tem de ser passado o ID da empresa, mas quando a autenticação for implementada será armazenado o ID da empresa através do token JWT.

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/joaopedropc7/espheradelivery.git
```

2. Instale as dependências com o Maven

3. Instale [PostgreSql](https://www.postgresql.org/download/)


## Inicio

1. Gere o .jar ou compile o projeto usando o maven.
2. A API ficará acessível em http://localhost:8080
3. Teste os endpoints através do swagger, ficará disponível em http://localhost:8080/swagger-ui/index.html

## Autenticação

A autenticação é feita através do JWT, para gerar o token é necessário fazer uma requisição POST para o endpoint /api/authentication/login, passando o email e senha do usuário, o token gerado tem validade de 12 horas, após esse tempo é necessário gerar um novo token.
Apenas usuários ADMIN podem cadastrar novos usuários, para isso é necessário fazer uma requisição POST para o endpoint /api/authentication/register, passando o nome, email, senha e o ID da empresa.   
