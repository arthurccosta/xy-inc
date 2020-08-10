package br.com.zup.xyinc.service.impl

import br.com.zup.xyinc.handler.XyIncErrorCode
import br.com.zup.xyinc.handler.exceptions.PoiNotFound
import br.com.zup.xyinc.model.Poi
import br.com.zup.xyinc.model.dto.PoiRequest
import br.com.zup.xyinc.model.dto.PoiResponse
import br.com.zup.xyinc.repository.PoiRepository
import br.com.zup.xyinc.service.PoiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class PoiServiceImpl(@Autowired private val repository: PoiRepository) : PoiService {
    override fun createPoi(request: PoiRequest): PoiResponse {
        return (repository.save(request.toPoi())).toPoiResponse()
    }

    override fun findById(id: String): PoiResponse? {
        return repository.findById(id)
                .orElseThrow { PoiNotFound(XyIncErrorCode.PoiNotFound) }
                .toPoiResponse()
    }

    override fun findAll(): List<PoiResponse?>? {
        return repository.findAll()
                .map { poi -> poi?.toPoiResponse() }
    }

    override fun findByProximity(coordinateX: Int, coordinateY: Int, distance: Int): List<PoiResponse?> {
        return repository.findAll()
                .filter { poi: Poi? -> coordinatesDistance(poi, coordinateX, coordinateY)!! <= distance }
                .map { poi -> poi?.toPoiResponse() }
    }

    override fun deletePoi(id: String) = repository.deleteById(id)

    private fun coordinatesDistance(poi: Poi?, x: Int, y: Int): Double? {
        if (poi != null) {
            return sqrt((poi.coordinateX - x.toDouble()).pow(2.0) + (poi.coordinateY - y.toDouble()).pow(2.0))
        }
        return null;
    }

}