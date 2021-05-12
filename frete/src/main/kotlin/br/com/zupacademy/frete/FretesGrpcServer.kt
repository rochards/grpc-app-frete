package br.com.zupacademy.frete

import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import kotlin.random.Random

@Singleton // para que essa classe seja reconhecida pelo Micronaut
class FreteGrpcServer : FreteServiceGrpc.FreteServiceImplBase() {

    private val logger = LoggerFactory.getLogger(FreteGrpcServer::class.java)

    override fun calculaFrete(request: CalculaFreteRequest?, responseObserver: StreamObserver<CalculaFreteResponse>?) {

        logger.info("calculando frete para request - $request")

        val response = CalculaFreteResponse.newBuilder()
            .setCep(request?.cep)
            .setValor(Random.nextDouble(from = 0.0, until = 140.0))
            .build()

        logger.info("frete calculado - $response")

        responseObserver?.onNext(response)
        responseObserver?.onCompleted() // fecha conexão
    }
}