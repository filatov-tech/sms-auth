package tech.filatov.auth.config

import graphql.scalars.ExtendedScalars
import graphql.schema.idl.RuntimeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class AppConfig {
    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer? {
        return RuntimeWiringConfigurer {
                wiringBuilder: RuntimeWiring.Builder -> wiringBuilder.scalar(ExtendedScalars.GraphQLLong)
        }
    }
}