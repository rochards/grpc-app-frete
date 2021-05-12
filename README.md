# Exemplo de aplicação com gRPC

Este repositório apresenta um exemplo de criação de um serviço utilizando [gRPC](https://grpc.io/), [Micronaut](https://micronaut.io/) e [Kotlin](https://kotlinlang.org/). 

Há duas aplicações independentes: **frete** e **frete-rest**
- **frete** é a aplicação que implementa um servidor gRPC;
- **frete-rest** é a aplicação que atende requisições REST e faz chamadas para o servidor gRPC. frete-rest é um cliente gRPC.

*OBS.:* deve-se buildar a aplicação para se ter acesso às classes geradas pelo gRPC.
