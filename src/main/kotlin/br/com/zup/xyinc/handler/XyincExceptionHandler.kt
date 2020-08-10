package br.com.zup.xyinc.handler

import br.com.zup.xyinc.handler.exceptions.PoiNotFound
import br.com.zup.xyinc.model.dto.ErrorCodeResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

@ControllerAdvice
class XyincExceptionHandler @Autowired constructor(private val messageSource: MessageSource) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgument(exception: MethodArgumentNotValidException): ResponseEntity<ErrorCodeResponse> {
        log.error(exception.message, exception)
        val errors: MutableList<String> = ArrayList()
        for (error in exception.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in exception.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }
        return ResponseEntity(ErrorCodeResponse(errors.toString()), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PoiNotFound::class)
    fun handlePoiNotFound(exception: PoiNotFound, locale: Locale?): ResponseEntity<ErrorCodeResponse> {
        log.error(exception.message, exception)
        val errorMessage = messageSource.getMessage(exception.errorCodeMessage, arrayOf(), locale!!)
        return ResponseEntity(ErrorCodeResponse(errorMessage), HttpStatus.NOT_FOUND)
    }

    companion object {
        private val log = LoggerFactory.getLogger(XyincExceptionHandler::class.java)
    }

}