package br.com.zupacademy.freterest

import br.com.zupacademy.frete.CalculaFreteRequest
import br.com.zupacademy.frete.FreteServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller("/api/fretes")
class CalculadoraFreteController(private val grpcClient: FreteServiceGrpc.FreteServiceBlockingStub) {
    // esse FreteServiceBlockingStub vem lá da classe GrpcClientFactory

    @Get
    fun calcula(@QueryValue cep: String): HttpResponse<FreteResponse> {
        val request = CalculaFreteRequest.newBuilder()
            .setCep(cep)
            .build()

        val response = grpcClient.calculaFrete(request)

        return HttpResponse.ok(FreteResponse(response.cep, response.valor))
    }
}

data class FreteResponse(val cep: String, val valor: Double)
