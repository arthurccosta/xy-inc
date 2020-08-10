package br.com.zup.xyinc.model

import br.com.zup.xyinc.model.dto.PoiResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class Poi {

    @Id
    var id: String = ""
    var name: String = ""
    var coordinateX = 0
    var coordinateY = 0

    constructor() {}
    constructor(name: String,
                coordinateX: Int,
                coordinateY: Int) {
        this.id = UUID.randomUUID().toString()
        this.name = name
        this.coordinateX = coordinateX
        this.coordinateY = coordinateY
    }

    fun toPoiResponse(): PoiResponse {
        return PoiResponse(id, name, coordinateX, coordinateY)
    }
}