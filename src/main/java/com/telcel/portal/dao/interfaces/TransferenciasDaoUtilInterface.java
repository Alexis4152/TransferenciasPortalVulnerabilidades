package com.telcel.portal.dao.interfaces;

import java.sql.Date;
import java.util.List;

import com.telcel.portal.pojos.CicloPojo;
import com.telcel.portal.pojos.DetallePojo;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.PeticionPojo;
import com.telcel.portal.pojos.TransferenciaPojo;

 public interface TransferenciasDaoUtilInterface {
	
	List<TransferenciaPojo> getTransferenciasByUserByFechaTransferencia(Date fecha,Date fecha2); 
	
	List<TransferenciaPojo> getDetalleTransferenciasByUserByFechaTransferencia(Date fecha,Date fecha2);
	 
	List<TransferenciaPojo> getTransferenciasByBancoByFechaTransferencia(Integer idBanco,Date fecha,Date fecha2, String descRegion);
	
	List<TransferenciaPojo> getTransferenciasByEstadoDeCuenta(Integer idBanco,Date fecha, String importe, String marca, String idRegion);
	
	TransferenciaPojo getTransferenciasByPagosNoIdent(String region, int lote, Date fecha, String idRegion);
	
	List<TransferenciaPojo> getTransferenciasByBancoByFechaLote(Integer idBanco,Date fecha,Date fecha2, String region);
	
	List<TransferenciaPojo> getTransferenciasByEmpleado(EmpleadoPojo empleado);
	
	DetallePojo getDetallesTransferencia(Integer idTransferencia);
	
	DetallePojo getDetallesTransferenciaCompensacion(Integer idTransferencia);
	
	String getCountTransferenciasbyEstatus(Integer idEstatus,Date fecha,Date fecha2, String region);

	String getCountTransferenciasbyEstatusCompensacion(Integer idEstatus,Date fecha,Date fecha2, String region);
	
	EmpleadoPojo getEmpleadoById(String idEmpleado);	
	
	List<EmpleadoPojo> getEmpleados(String auditaUsr, String descRegion);
	
	List<EmpleadoPojo> getEmpleadosByAuditaUser(String auditaUsr, String descRegion);
	
	String getCountTransferenciasRegresadas(Integer idEmpleado, String region);
	
	List<TransferenciaPojo> getTransferenciasRegresadas(Integer idEmpleado, String region);
	
	TransferenciaPojo getTransferencia(Integer idTransferencia);
	
	boolean insertaHistorial(Integer idEmpleado, Integer idEstatus,Integer idTransferencia, String comnt);
	
	boolean actualizaHistorial(Integer idEmpleado, Integer idEstatus,Integer idTransferencia, String comnt);

	int getAccesFull(String sUser);
	
	List<TransferenciaPojo> getTransferenciasRechazadas(Date fecha,Date fecha2, String idBanco); 
	
	List<TransferenciaPojo> getDetalleTransferenciasRechazadas(Date fecha,Date fecha2, String idBanco);
	
	List<CicloPojo> getReporteCiclos(Date fechaInicio, Date fechaFin);
	
	List<PeticionPojo> getReportePeticiones(Date fechaInicio, Date fechaFin);
	
	List<TransferenciaPojo> getReporteTransIngresos(Date fechaInicio, Date fechaFin);
}
