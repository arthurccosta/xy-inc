package br.com.zup.xyinc.repository

import br.com.zup.xyinc.model.Poi
import org.springframework.data.mongodb.repository.MongoRepository

interface PoiRepository : MongoRepository<Poi, String>