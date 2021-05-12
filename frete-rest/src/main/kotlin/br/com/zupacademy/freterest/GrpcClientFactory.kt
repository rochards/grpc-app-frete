package br.com.zupacademy.freterest

import br.com.zupacademy.frete.FreteServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {

    // @Bean
    @Singleton // quem precisar de um FreteServiceBlockingStub, vai pegar a instância retornada por esse método
    fun freteClientStub(
        // frete é um channel definido em application.yml
        @GrpcChannel("frete") channel: ManagedChannel
    ): FreteServiceGrpc.FreteServiceBlockingStub? {

        /* não preciso criar esse channel manualmente. Essas configurações mais finais abaixo são feitas no
        application.yml

        val channel = ManagedChannelBuilder
            .forAddress("localhost", 50051)
            .usePlaintext() // não deve ser utilizado em produção
            .maxRetryAttempts(10) // opcional
            .build()
         */

        return FreteServiceGrpc.newBlockingStub(channel)
    }
}