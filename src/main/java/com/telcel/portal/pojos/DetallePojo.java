package com.telcel.portal.pojos;

import java.util.List;

public class DetallePojo {
	
	private TransferenciaPojo transferenciaPojo;
	private List<PagoPojo> listaPagos;
	private List<PagoPojo> listaPagosSuma;
	
	
	public List<PagoPojo> getListaPagosSuma() {
		return listaPagosSuma;
	}
	public void setListaPagosSuma(List<PagoPojo> listaPagosSuma) {
		this.listaPagosSuma = listaPagosSuma;
	}
	public List<PagoPojo> getListaPagos() {
		return listaPagos;
	}
	public void setListaPagos(List<PagoPojo> listaPagos) {
		this.listaPagos = listaPagos;
	}
	public TransferenciaPojo getTransferenciaPojo() {
		return transferenciaPojo;
	}
	public void setTransferenciaPojo(TransferenciaPojo transferenciaPojo) {
		this.transferenciaPojo = transferenciaPojo;
	}
	
	
	

}
