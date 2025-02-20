package com.telcel.portal.pojos;

public class EmpleadoPojo {
	
	private Integer idEmpleado;
	private Integer estatusUsuario;
	private Integer idPefril;
	
	private String nombre;
	private String puesto;	
	private String auditaUsuario;
	private String fechaAudita;	
	private String descripcionPerfil;
	private String numeroEmpleado;
	private String usuario;
	private int idRegion;
	private String descRegion;
	

	private Integer esJefe;
	private String usuarioJefeDirecto;
	
	public EmpleadoPojo() {
	}
	
	public EmpleadoPojo(Integer idEmpleado, String nombre) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
	}
	
	public String getDescripcionPerfil() {
		return descripcionPerfil;
	}
	public void setDescripcionPerfil(String descripcionPerfil) {
		this.descripcionPerfil = descripcionPerfil;
	}
	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}
	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}
	public String getAuditaUsuario() {
		return auditaUsuario;
	}
	public void setAuditaUsuario(String auditaUsuario) {
		this.auditaUsuario = auditaUsuario;
	}
	public Integer getEstatusUsuario() {
		return estatusUsuario;
	}
	public void setEstatusUsuario(Integer estatusUsuario) {
		this.estatusUsuario = estatusUsuario;
	}
	public String getFechaAudita() {
		return fechaAudita;
	}
	public void setFechaAudita(String fechaAudita) {
		this.fechaAudita = fechaAudita;
	}
	public Integer getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public Integer getIdPefril() {
		return idPefril;
	}
	public void setIdPefril(Integer idPefril) {
		this.idPefril = idPefril;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Integer getEsJefe() {
		return esJefe;
	}
	public void setEsJefe(Integer esJefe) {
		this.esJefe = esJefe;
	}
	public String getUsuarioJefeDirecto() {
		return usuarioJefeDirecto;
	}
	public void setUsuarioJefeDirecto(String usuarioJefeDirecto) {
		this.usuarioJefeDirecto = usuarioJefeDirecto;
	}

	public String getDescRegion() {
		return descRegion;
	}
	public void setDescRegion(String descRegion) {
		this.descRegion = descRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public int getIdRegion() {
		return idRegion;
	}
			
}
