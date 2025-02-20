package com.telcel.portal.pojos;

import java.math.BigDecimal;

public class CargaDocumentoPojo {
	@Override
	public String toString() {
		return  ""+(index+1);
	}
	private String fechaDocumento;
	private BigDecimal numeroDocumento;
	private long lote;
	private String division;
	private int index;
	private String fechaLote;
	public String getFechaDocumento() {
		return fechaDocumento;
	}
	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}
	public BigDecimal getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(BigDecimal numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public long getLote() {
		return lote;
	}
	public void setLote(long lote) {
		this.lote = lote;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getFechaLote() {
		return fechaLote;
	}
	public void setFechaLote(String fechaLote) {
		this.fechaLote = fechaLote;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
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
		CargaDocumentoPojo other = (CargaDocumentoPojo) obj;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		return true;
	}
	
	
}
