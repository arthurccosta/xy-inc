package br.com.zup.xyinc.model.dto

import java.io.Serializable

class PoiResponse(val id: String, val name: String, val coordinateX: Int, val coordinateY: Int) : Serializable