package br.com.zup.xyinc.controller

import br.com.zup.xyinc.model.dto.PoiRequest
import br.com.zup.xyinc.model.dto.PoiResponse
import br.com.zup.xyinc.service.PoiService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api("Poi")
@RestController
@RequestMapping("/poi")
class PoiController(@Autowired
                    private val service: PoiService) {

    @ApiOperation("Create Poi")
    @PostMapping
    fun createPoi(@Valid @RequestBody request: PoiRequest): PoiResponse {
        return service.createPoi(request)
    }

    @ApiOperation("Find Poi by id")
    @GetMapping("/find-by-id/{id}")
    fun findById(@PathVariable id: String): PoiResponse? {
        return service.findById(id)
    }

    @ApiOperation("Find all Poi's")
    @GetMapping("/find-all")
    fun findAll(): List<PoiResponse?>? {
        return service.findAll()
    }

    @ApiOperation("Find Poi by proximity")
    @GetMapping("/find-by-proximity")
    fun findByProximity(@RequestParam coordinateX: Int, @RequestParam coordinateY: Int, @RequestParam distance: Int): List<PoiResponse?> {
        return service.findByProximity(coordinateX, coordinateY, distance)
    }

    @ApiOperation("Delete Poi")
    @DeleteMapping("/{id}")
    fun deletePoi(@PathVariable id: String) {
        service.deletePoi(id)
    }
}