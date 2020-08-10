package br.com.zup.xyinc.handler.exceptions

import br.com.zup.xyinc.handler.XyIncErrorCode

class PoiNotFound(private val errorCode: XyIncErrorCode) : RuntimeException() {

    val errorCodeMessage: String
        get() = errorCode.errorCode

}