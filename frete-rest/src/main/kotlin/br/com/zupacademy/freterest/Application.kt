package br.com.zupacademy.freterest

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.zupacademy.freterest")
		.start()
}

