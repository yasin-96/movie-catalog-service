package io.eraslan.moviecatalogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient


@SpringBootApplication
class MovieCatalogServiceApplication{

	@Bean
	fun getCircuitBreaker() : ReactiveResilience4JCircuitBreakerFactory {
		return ReactiveResilience4JCircuitBreakerFactory()
	}

	@Primary
	@Bean
	fun getWebClientBuilder() : WebClient.Builder {
		return WebClient.builder()
	}
}

fun main(args: Array<String>) {
	runApplication<MovieCatalogServiceApplication>(*args)
}
