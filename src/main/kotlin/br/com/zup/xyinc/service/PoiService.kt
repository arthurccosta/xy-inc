package br.com.zup.xyinc.service

import br.com.zup.xyinc.model.dto.PoiRequest
import br.com.zup.xyinc.model.dto.PoiResponse

interface PoiService {
    fun createPoi(request: PoiRequest): PoiResponse
    fun findAll(): List<PoiResponse?>?
    fun findByProximity(coordinateX: Int, coordinateY: Int, distance: Int): List<PoiResponse?>
    fun deletePoi(id: String)
    fun findById(id: String): PoiResponse?
}