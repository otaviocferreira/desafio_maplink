# desafio_maplink

Como usar
=======

Rodando via Docker e Docker Compose
-----------

Na pasta do projeto executar os comandos abaixo na ordem.

1) ***mvn clean package dockerfile:build*** - Esse comando irá criar a imagem da aplicação.
2) ***docker-compose up*** - Esse comando irá subir a aplicação, que ficará disponível para utilização.


Documentação
------------

Após executar os comandos acima, acesse o link abaixo e importe dentro do REST client de sua preferência
(Postman ou Insomnia), e já pode começar a utillizar a API de Agendamentos.

***Swagger*** - http://localhost:8080/v2/api-docs


Observações
-----------

A única parte não realizada do desafio foi a construção do endpoint de agrupamento por
data e valor. Essa parte não foi entendida pelo desenvolvedor, que preferiu não construí-la.


Obrigado pela oportunidade de realizar esse teste. Até breve.