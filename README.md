# Exemplo de aplicação com gRPC

Este repositório apresenta um exemplo de criação de um serviço utilizando [gRPC](https://grpc.io/), [Micronaut](https://micronaut.io/) e [Kotlin](https://kotlinlang.org/). 

Há duas aplicações independentes: **frete** e **frete-rest**:
- **frete** é a aplicação que implementa um servidor gRPC;
- **frete-rest** é a aplicação que atende requisições REST e faz chamadas para o servidor gRPC. frete-rest é um cliente gRPC.

O que foi utilizado para a implementação deste exemplo:
- JDK 11;
- Intellij IDEA;
- Kotlin 1.5.0;
- Micronaut 2.5.3;
- gRPC.

*OBS.:* deve-se buildar a aplicação para se ter acesso às classes geradas pelo gRPC.
