package tech.filatov.auth.common.exception

import graphql.ErrorType
import graphql.GraphQLError
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class GlobalExceptionHandler {

    @GraphQlExceptionHandler
    fun handle(ex: NotFoundException?): GraphQLError? {
        return GraphQLError.newError().errorType(ErrorType.DataFetchingException).message(ex?.message).build()
    }

    @GraphQlExceptionHandler
    fun handle(ex: NumberFormatException?): GraphQLError? {
        return GraphQLError.newError().errorType(ErrorType.DataFetchingException).message(ex?.message).build()
    }

    @GraphQlExceptionHandler
    fun handle(ex: IllegalArgumentException?): GraphQLError? {
        return GraphQLError.newError().errorType(ErrorType.DataFetchingException).message(ex?.message).build()
    }
}