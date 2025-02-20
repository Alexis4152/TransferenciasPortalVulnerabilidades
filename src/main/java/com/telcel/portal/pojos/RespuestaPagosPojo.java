package com.telcel.portal.pojos;

import java.util.Map;

public class RespuestaPagosPojo {
	
	private String respuesta;
	private Map<String,String> cuetasCambiadas;
	
	
	
	public Map<String, String> getCuetasCambiadas() {
		return cuetasCambiadas;
	}
	public void setCuetasCambiadas(Map<String, String> cuetasCambiadas) {
		this.cuetasCambiadas = cuetasCambiadas;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	

}
