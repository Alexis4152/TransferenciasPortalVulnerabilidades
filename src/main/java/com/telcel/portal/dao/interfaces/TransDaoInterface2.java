package com.telcel.portal.dao.interfaces;

import java.util.List;
import com.telcel.portal.pojos.*;

public interface TransDaoInterface2 {
	
	
	String setDesglosado(Integer idTransferencia,Integer idEmpleado,String tipoCuentas, String correoElectronico);
	
	String editarPago(PagoPojo pagoPojo,EmpleadoPojo usuario);	
	
	String eliminarPago(Integer idPago);
	
	String regresarTransferencia(Integer idTransferencia,Integer idEmpleado,String comentario,Integer batch);
	
	String comentarTransferencia(Integer idTransferencia,Integer idEmpleado,String comentario);
	
	String eliminarPagosByIdTransferencia(Integer idTransferencia);
	
	String eliminarPagosOnlyByIdTransferencia(String[] idTransferencia);
	
	String setCierraTransferencias(String [] lista,Integer idEmplead, String coment);
	
	List<TransferenciaPojo> consultaTransferencias(List<TransferenciaPojo> transferencias,Integer idBanco, String region);
	
	List<TransferenciaPojo> identificaDuplicados(List<TransferenciaPojo> listaArchivo, List<TransferenciaPojo> listaConsulta, int region);

	boolean validaCargaTransferencias();
	 
	boolean consultaEstadoTomar(String[] lista);
	
	boolean consultaEstadoDesglose(int idTrans);
	
	boolean consultaEstadoAplicado(String[] lista);
	
	boolean consultaEstadoAplicado(String idTransferencia);
	
	String actualizaDatoPromesaPago(List<PagoPojo> listaPagos, String tipo);
	
	String insertaProcesoPromesa(Integer idTransferencia, Integer idEmpleado);
	
	boolean validaPromesaPago();
	
	String validaLimitePromesaPago();
	
	public TransferenciaPojo consultaTransferenciaxId(String idTransferencia);
	
	public List<Long> consultaNumerosDocumento(List<CargaDocumentoPojo> documentos);
	
	public void insertaNumerosDocumento(List<CargaDocumentoPojo> documentos);
	
	boolean insertaPeticion(Integer idEmpleado, String horario, String periodo, String asistencia, String cantidadMensajes);
	
	boolean updatePeticion(Integer idPeticion, Integer idEmpleado, String horario, String periodo, String asistencia, String cantidadMensajes);
	
	boolean insertaAlerta(String color, String tiempo, Integer idEstatus);
	
	boolean updateAlerta(Integer idAlerta, String color, String tiempo, Integer idEstatus);


	
}
