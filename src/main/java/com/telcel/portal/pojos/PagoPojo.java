package com.telcel.portal.pojos;

import java.util.Date;

public class PagoPojo implements Comparable  {
	
	private Integer idPago;
	private Integer idEmpleado;
	private Integer idTrans;
	
	private Double importe;
	
	private Date fechaLote;
	
	private String tipo;
	private String cuenta;	
	private String importeString;
	private String estatusPago;
	private String region;
	private String lote;	
	private String fechaLoteString;
	private String descripcion;
	private java.sql.Date fechaNumLote;
	private String sap;
	private String estatusPP;
	private String descripcionPP;
	private long numeroDocumento;
	private long idCompensacion;
	private String numeroDocumentoCompensacion;
	
	public PagoPojo(){
		region ="";
	}

	public java.sql.Date getFechaNumLote() {
		return fechaNumLote;
	}

	public void setFechaNumLote(java.sql.Date fechaNumLote) {
		this.fechaNumLote = fechaNumLote;
	}

	public String getFechaLoteString() {
		return fechaLoteString;
	}

	public void setFechaLoteString(String fechaLoteString) {
		this.fechaLoteString = fechaLoteString;
	}

	public Date getFechaLote() {
		return fechaLote;
	}

	public void setFechaLote(Date fechaLote) {
		this.fechaLote = fechaLote;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getEstatusPago() {
		return estatusPago;
	}

	public void setEstatusPago(String estatusPago) {
		this.estatusPago = estatusPago;
	}

	public Integer getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Integer getIdTrans() {
		return idTrans;
	}

	public void setIdTrans(Integer idTransferencia) {
		this.idTrans = idTransferencia;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getImporteString() {
		return importeString;
	}

	public void setImporteString(String importeString) {
		this.importeString = importeString;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getIdPago() {
		return idPago;
	}

	public void setIdPago(Integer idPago) {
		this.idPago = idPago;
	}

	public int compareTo(Object o) {
		    PagoPojo pago = (PagoPojo) o;
		    //podemos hacer esto porque String implementa Comparable
		    return region.compareTo(pago.getRegion());
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSap() {
		return sap;
	}

	public void setSap(String sap) {
		this.sap = sap;
	}

	public String getEstatusPP() {
		return estatusPP;
	}

	public void setEstatusPP(String estatusPP) {
		this.estatusPP = estatusPP;
	}

	public String getDescripcionPP() {
		return descripcionPP;
	}

	public void setDescripcionPP(String descripcionPP) {
		this.descripcionPP = descripcionPP;
	}
	
	public long getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public long getIdCompensacion() {
		return idCompensacion;
	}
	public void setIdCompensacion(long idCompensacion) {
		this.idCompensacion = idCompensacion;
	}
	public String getNumeroDocumentoCompensacion() {
		return numeroDocumentoCompensacion;
	}
	public void setNumeroDocumentoCompensacion(String numeroDocumentoCompensacion) {
		this.numeroDocumentoCompensacion = numeroDocumentoCompensacion;
	}

}
