package br.com.zup.xyinc.model.dto

import br.com.zup.xyinc.model.Poi
import java.io.Serializable
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class PoiRequest(@field:Min(0) var coordinateX: Int, @field:Min(0) var coordinateY: Int, @field:NotNull var name: String) : Serializable {


    fun toPoi(): Poi {
        return Poi(name, coordinateX, coordinateY)
    }


}

