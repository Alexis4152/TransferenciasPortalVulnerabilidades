package com.telcel.portal.pojos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaPojo {

	private Integer idtransferencia;
	private Integer estatus;
	private Integer proceso;
	private Integer flujo;
	private Integer idBanco;
	private Integer idProceso;
	private Integer idEmpleado;
	
	private Double importe;
	private Double importeTrans;
	
	private String cuenta;
	private String fecha;	
	private String cliente;
	private String nombreUsuario;
	private String referenciaBanco;
	private String referenciaCliente;
	private String importeString;
	private String importeTransString;
	
	private String banco;
	private String estatusDescripcion;
	private String concepto;
	private String indicador;
	private String tipoMoneda;
	private String alias;
	private String tipoPagos;
	private Date dateFecha;	
	private String fechaHistorial;
	private String usuario;
	private String region;
	private String lote;
	private String sap;
	
	private String rechazado;
	private String descripcion;
	
	private long idCompensacion;
	private List<Long> idsCompensacion = new ArrayList<Long>();
	private String idsCompensacionString;
	private String fechaTransferencia;	
	private String color;
	private String pkIdReferencia;
	
	private boolean esAgregada;
	private boolean esDuplicada;

	public String getFechaTransferencia() {
		return fechaTransferencia;
	}
	public void setFechaTransferencia(String fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}
	public Double getImporteTrans() {
		return importeTrans;
	}
	public void setImporteTrans(Double importeTrans) {
		this.importeTrans = importeTrans;
	}
	public String getImporteTransString() {
		return importeTransString;
	}
	public void setImporteTransString(String importeTransString) {
		this.importeTransString = importeTransString;
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getFechaHistorial() {
		return fechaHistorial;
	}
	public void setFechaHistorial(String fechaHistorial) {
		this.fechaHistorial = fechaHistorial;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTipoPagos() {
		return tipoPagos;
	}
	public void setTipoPagos(String tipoPagos) {
		this.tipoPagos = tipoPagos;
	}
	public Date getDateFecha() {
		return dateFecha;
	}
	public void setDateFecha(Date dateFecha) {
		this.dateFecha = dateFecha;
	}
	public Integer getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTipoMoneda() {
		return tipoMoneda;
	}
	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	public String getIndicador() {
		return indicador;
	}
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getEstatusDescripcion() {
		return estatusDescripcion;
	}
	public void setEstatusDescripcion(String estatusDescripcion) {
		this.estatusDescripcion = estatusDescripcion;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public Integer getIdBanco() {
		return idBanco;
	}
	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}
	public Integer getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}
	public Integer getIdtransferencia() {
		return idtransferencia;
	}
	public void setIdtransferencia(Integer idtransferencia) {
		this.idtransferencia = idtransferencia;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	public Integer getFlujo() {
		return flujo;
	}
	public void setFlujo(Integer flujo) {
		this.flujo = flujo;
	}
	public Integer getProceso() {
		return proceso;
	}
	public void setProceso(Integer proceso) {
		this.proceso = proceso;
	}
	public String getReferenciaBanco() {
		return referenciaBanco;
	}
	public void setReferenciaBanco(String referenciaBanco) {
		this.referenciaBanco = referenciaBanco;
	}
	public String getImporteString() {
		return importeString;
	}
	public void setImporteString(String importeString) {
		this.importeString = importeString;
	}
	public String getReferenciaCliente() {
		return referenciaCliente;
	}
	public void setReferenciaCliente(String referenciaCliente) {
		this.referenciaCliente = referenciaCliente;
	}
	public String getSap() {
		return sap;
	}
	public void setSap(String sap) {
		this.sap = sap;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getRechazado() {
		return rechazado;
	}
	public void setRechazado(String rechazado) {
		this.rechazado = rechazado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<Long> getIdsCompensacion() {
		return idsCompensacion;
	}
	public void setIdsCompensacion(List<Long> idsCompensacion) {
		this.idsCompensacion = idsCompensacion;
	}
	public long getIdCompensacion() {
		return idCompensacion;
	}
	public void setIdCompensacion(long idCompensacion) {
		this.idCompensacion = idCompensacion;
	}
	public String getIdsCompensacionString() {
		return idsCompensacionString;
	}
	public void setIdsCompensacionString(String idsCompensacionString) {
		this.idsCompensacionString = idsCompensacionString;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idtransferencia == null) ? 0 : idtransferencia.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferenciaPojo other = (TransferenciaPojo) obj;
		if (idtransferencia == null) {
			if (other.idtransferencia != null)
				return false;
		} else if (!idtransferencia.equals(other.idtransferencia))
			return false;
		return true;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPkIdReferencia() {
		return pkIdReferencia;
	}
	public void setPkIdReferencia(String pkIdReferencia) {
		this.pkIdReferencia = pkIdReferencia;
	}
	public boolean isEsAgregada() {
		return esAgregada;
	}
	public void setEsAgregada(boolean esAgregada) {
		this.esAgregada = esAgregada;
	}
	public boolean isEsDuplicada() {
		return esDuplicada;
	}
	public void setEsDuplicada(boolean esDuplicada) {
		this.esDuplicada = esDuplicada;
	}
	
	
}
