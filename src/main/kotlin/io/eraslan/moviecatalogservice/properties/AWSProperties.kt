package io.eraslan.moviecatalogservice.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "loadbalancer")
class AWSProperties {
    lateinit var uri: String
}