package br.com.zupacademy.frete

import com.google.rpc.Code
import io.grpc.Status
import io.grpc.protobuf.StatusProto
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import javax.inject.Singleton
import kotlin.random.Random

@Singleton // para que essa classe seja reconhecida pelo Micronaut
class FreteGrpcServer : FreteServiceGrpc.FreteServiceImplBase() {

    private val logger = LoggerFactory.getLogger(FreteGrpcServer::class.java)

    override fun calculaFrete(request: CalculaFreteRequest?, responseObserver: StreamObserver<CalculaFreteResponse>?) {

        logger.info("calculando frete para request - $request")

        val cep = request?.cep
        if (cep.isNullOrBlank()) {
            val error = Status.INVALID_ARGUMENT
                .withDescription("cep deve ser informado")
                .asRuntimeException()
            responseObserver?.onError(error)
        } else if (!cep.matches("[0-9]{5}-[0-9]{3}".toRegex())) {
            val error = Status.INVALID_ARGUMENT
                .withDescription("cep deve estar em formato válido")
                .augmentDescription("formato esperado deve ser 99999-999")
                .asRuntimeException()
            responseObserver?.onError(error)
        }

        var randomValue = 0.0
        try {
            // esse try é só para simular como tratar um possível error qualquer
            randomValue = Random.nextDouble(from = 0.0, until = 140.0)
            if (randomValue > 100) {
                throw IllegalStateException("Erro aleatório de regra de negócio!")
            }
        } catch (e: Exception) {
            responseObserver?.onError(
                Status.INTERNAL
                    .withDescription(e.message)
                    .withCause(e) // anexado ao Status, mas não enviado ao Client
                    .asRuntimeException()
            )
        }
        val response = CalculaFreteResponse.newBuilder()
            .setCep(cep)
            .setValor(randomValue)
            .build()

        logger.info("frete calculado - $response")

        responseObserver?.onNext(response)
        responseObserver?.onCompleted() // fecha conexão
    }
}