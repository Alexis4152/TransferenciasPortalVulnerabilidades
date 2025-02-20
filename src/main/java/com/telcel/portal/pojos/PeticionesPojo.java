package com.telcel.portal.pojos;

public class PeticionesPojo {
	
	private int idPeticion;
	private String nombre;
	private long idEmpleado;
	private String horarioIn;
	private String horarioOut;
	private String periodoIn;
	private String periodoOut;
	private String asistencia;
	private String cantidadMensajes;
	
	public long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}
	public String getCantidadMensajes() {
		return cantidadMensajes;
	}
	public void setCantidadMensajes(String cantidadMensajes) {
		this.cantidadMensajes = cantidadMensajes;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getHorarioIn() {
		return horarioIn;
	}
	public void setHorarioIn(String horarioIn) {
		this.horarioIn = horarioIn;
	}
	public String getHorarioOut() {
		return horarioOut;
	}
	public void setHorarioOut(String horarioOut) {
		this.horarioOut = horarioOut;
	}
	public String getPeriodoIn() {
		return periodoIn;
	}
	public void setPeriodoIn(String periodoIn) {
		this.periodoIn = periodoIn;
	}
	public String getPeriodoOut() {
		return periodoOut;
	}
	public void setPeriodoOut(String periodoOut) {
		this.periodoOut = periodoOut;
	}
	public int getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(int idPeticion) {
		this.idPeticion = idPeticion;
	}
	
}
