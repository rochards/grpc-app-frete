package br.com.zupacademy.freterest

import br.com.zupacademy.frete.CalculaFreteRequest
import br.com.zupacademy.frete.ErrorDetails
import br.com.zupacademy.frete.FreteServiceGrpc
import com.google.protobuf.Any
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.exceptions.HttpStatusException

@Controller("/api/fretes")
class CalculadoraFreteController(private val grpcClient: FreteServiceGrpc.FreteServiceBlockingStub) {
    // esse FreteServiceBlockingStub vem lá da classe GrpcClientFactory

    @Get
    fun calcula(@QueryValue cep: String): HttpResponse<FreteResponse> {
        val request = CalculaFreteRequest.newBuilder()
            .setCep(cep)
            .build()

        try {
            val response = grpcClient.calculaFrete(request)

            return HttpResponse.ok(FreteResponse(response.cep, response.valor))
        } catch (e: StatusRuntimeException) {

            val statusCode = e.status.code
            val description = e.status.description

            if (statusCode == Status.Code.INVALID_ARGUMENT) {
                throw HttpStatusException(HttpStatus.BAD_REQUEST, description)
            }

            if (statusCode == Status.Code.PERMISSION_DENIED) {

                val statusProto =
                    StatusProto.fromThrowable(e) ?: throw HttpStatusException(HttpStatus.FORBIDDEN, description)

                // esse Any é do protobuf
                val details: Any = statusProto.detailsList[0]
                val errorDetails = details.unpack(ErrorDetails::class.java)

                throw HttpStatusException(HttpStatus.FORBIDDEN, "${errorDetails.code}: ${errorDetails.message}")
            }

            throw HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, description)
        }

    }
}

data class FreteResponse(val cep: String, val valor: Double)
