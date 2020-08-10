package br.com.zup.xyinc.controller

import br.com.zup.xyinc.model.Poi
import br.com.zup.xyinc.model.dto.PoiRequest
import br.com.zup.xyinc.model.dto.PoiResponse
import br.com.zup.xyinc.repository.PoiRepository
import br.com.zup.xyinc.service.PoiService
import br.com.zup.xyinc.service.impl.PoiServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.annotations.Extension
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.ArgumentMatchers.*
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.security.Provider
import javax.management.loading.ClassLoaderRepository
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
@WebMvcTest(PoiController::class)
class PoiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var poiService: PoiServiceImpl

    private val objectMapper: ObjectMapper = ObjectMapper()

    @Test
    fun createPoi() {
        `when`<PoiResponse>(poiService.createPoi(getValidPoiRequest())).thenReturn(getValidPoiResponse())

        mockMvc.perform(post("/poi")
                .content(objectMapper.writeValueAsString(getValidPoiRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value("111aaa"))
                .andExpect(jsonPath("$.name").value("Bar"))
                .andExpect(jsonPath("$.coordinateX").value(10))
                .andExpect(jsonPath("$.coordinateY").value(10))
                .andReturn().response.contentAsString

    }

    @Test
    fun createPoiWithInvalidCoordinate() {

        mockMvc.perform(post("/poi")
                .content(objectMapper.writeValueAsString(getInvalidPoiRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)

    }

    @Test
    fun findByProximity() {
        `when`<List<PoiResponse?>>(poiService.findByProximity(anyInt(), anyInt(), anyInt())).thenReturn(mutableListOf(getValidPoiResponse()))

        mockMvc.perform(get("/poi/find-by-proximity")
                .param("distance", "10")
                .param("coordinateX", "10")
                .param("coordinateY", "10"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.[0].name").value("Bar"))

    }

    @Test
    @Throws(Exception::class)
    fun findAll() {
        mockMvc.perform(MockMvcRequestBuilders.get("/find-all"))
                .andReturn()
    }


    private fun getValidPoiRequest(): PoiRequest {
        return PoiRequest(10, 10, "Bar")
    }

    private fun getValidPoiResponse(): PoiResponse {
        return PoiResponse("111aaa", "Bar", 10, 10)
    }

    private fun getInvalidPoiRequest(): PoiRequest {
        return PoiRequest(-1, 10, "Bar")
    }

}