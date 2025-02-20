package com.telcel.portal.dao.interfaces;

import java.sql.Date;
import java.util.List;

import com.telcel.portal.pojos.AlertasPojo;
import com.telcel.portal.pojos.BancoPojo;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.EstatusPojo;
import com.telcel.portal.pojos.HistorialPojo;
import com.telcel.portal.pojos.PeticionesPojo;
import com.telcel.portal.pojos.TransferenciaPojo;

public interface TransferenciasDaoConsultaInterface {
	
	List<BancoPojo> getBancos();
	
	List<EstatusPojo> getEstatus();
	
	List<HistorialPojo> getHistorial(Integer idTransferencia);
	
	public boolean hayComentarioFA(Integer idTransferencia);
		
	List<TransferenciaPojo> getTransferencias(Integer idBanco,Date fecha,Date fecha2,Integer estatus,String importe, String descRegion);
	
	List<TransferenciaPojo> getTransferenciasBusqueda(Integer idBanco,Date fecha,String importe, String region);
	
	List<TransferenciaPojo> getTransferenciasTomar(Integer idBanco,Date fecha,Date fecha2,String importe, String region);	
	
	List<TransferenciaPojo> getTransferenciasByEstatusByFechas(Integer idEstatus,Date fecha,Date fecha2, String region);
	
	List<TransferenciaPojo> getTransferenciasByEstatusByFechasCompensacion(Integer idEstatus,Date fecha,Date fecha2, String region);
	
	List<TransferenciaPojo> getTransferenciasByEstatusByFechaReporte(Integer idEstatus,Date fecha,Date fecha2,Integer idEmpleado, String region);
	
	public List<TransferenciaPojo> getTransferenciasCargadasAutorizadas(Integer idbanco,Date fecha,Date fecha2,Integer estatus,Integer estatusAutorizada, String importe, String region);
	
	List<TransferenciaPojo> getTransferenciasCompensar(Integer idBanco,Date fecha,Date fecha2,Integer estatus,String importe, String descRegion);
	
	List<TransferenciaPojo> getTransferenciasByReferenciaEstatus(Integer idEstatus,Date fecha,Date fecha2,Integer idEmpleado, String region, Integer esJefe);
	
	List<PeticionesPojo> getPeticiones(String region);
	
	List<EmpleadoPojo> getUsuariosPeticiones(String region);
	
	List<AlertasPojo> getAlertas();
	
	HistorialPojo getHistorialByFecha(Integer idTransferencia, Date fecha, Date fecha2);
	
	boolean getPeticionById(Integer idEmpleado);
	
	boolean getAlertaByColor(String color);
	
	String eliminarAlerta(Integer idAlerta);
	
	String eliminarPeticion(Integer idPeticion);

}
