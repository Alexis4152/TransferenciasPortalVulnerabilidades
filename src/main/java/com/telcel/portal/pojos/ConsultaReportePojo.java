package com.telcel.portal.pojos;

public class ConsultaReportePojo {
	private String banco;
	private String fecha;
	private String total;
	private String transacciones;
	private String usuario;
	private String region;
	private String cuenta;
	private String importe;
	private String empresa;
	
	public String getImporte() {
		return importe;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTransacciones() {
		return transacciones;
	}
	public void setTransacciones(String transacciones) {
		this.transacciones = transacciones;
	}
	
	
	

}
