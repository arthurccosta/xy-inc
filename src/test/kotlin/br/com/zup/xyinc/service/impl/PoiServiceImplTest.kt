package br.com.zup.xyinc.service.impl

import br.com.zup.xyinc.model.Poi
import br.com.zup.xyinc.model.dto.PoiRequest
import br.com.zup.xyinc.repository.PoiRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [PoiServiceImpl::class, PoiRepository::class] )
class PoiServiceImplTest {

    @Autowired
    private val service: PoiServiceImpl? = null

    @MockBean
    private val repository: PoiRepository? = null

    @Test
    fun testCreatePoi() {
        `when`<Poi>(repository?.save(any())).thenReturn(getPoi())
        val poi = service?.createPoi(getPoiRequest())
        assertNotNull(poi)
        assertEquals("Bar", poi.name)
        assertEquals(10, poi.coordinateX)
        assertEquals(10, poi.coordinateY)
    }

    @Test
    fun testFindById() {
        `when`<Optional<Poi>>(repository?.findById(any())).thenReturn(Optional.of(getPoi()))
        val poi = service?.findById("111aaa");
        assertNotNull(poi)
        assertEquals(10, poi.coordinateX)
        assertEquals(10, poi.coordinateY)
    }

    @Test
    fun testFindByProximity() {
        `when`<List<Poi>>(repository?.findAll()).thenReturn(mutableListOf(getPoi()))
        val poiList = service?.findByProximity(10, 10, 10)
        assertNotNull(poiList)
        assertEquals(poiList.size, 1)
    }


    private fun getPoi(): Poi {
        return Poi("Bar", 10, 10)
    }

    private fun getPoiRequest(): PoiRequest {
        return PoiRequest(10, 10, "Bar")
    }
}