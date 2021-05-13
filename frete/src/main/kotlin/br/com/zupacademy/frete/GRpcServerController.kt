package br.com.zupacademy.frete

import io.micronaut.grpc.server.GrpcEmbeddedServer
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/grpc-server")
class GRpcServerController(private val grpcServer: GrpcEmbeddedServer) {

    @Get("/stop")
    fun stop(): HttpResponse<String> {

        grpcServer.stop()

        return HttpResponse.ok("is gRPC Server running? ${grpcServer.isRunning}")
    }
}