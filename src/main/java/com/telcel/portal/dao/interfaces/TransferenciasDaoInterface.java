package com.telcel.portal.dao.interfaces;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import com.telcel.portal.pojos.*;

public interface TransferenciasDaoInterface {
	
	
	String setAutorizaTransferencias(String [] lista,Integer idEmpleado);
	
	String setRevierteTransferencias(String [] lista,Integer idEmpleado);
	
	RespuestaPagosPojo setInsertaPagos(List <PagoPojo> pagoPojoList,EmpleadoPojo empleado);
	
	String setAplicaTransferencias(List <String>lista,Integer idEmpleado);
	
	String setAplicaTransferenciasFA(List <String> lista,Integer idEmpleado);
	
	String setTomaTransferencias(String [] lista, Integer ideEmpleado,String alias);
	
	String setRechazaTransferencias(String [] lista,String comentario,Integer ideEmpleado); 
	
	String setCambioEstatusTransferencias(String [] lista,String comentario,Integer ideEmpleado); 
	
	String insertaTransferencias(List<TransferenciaPojo> transferencias,Integer idEstatus,String region);
	
	String agregarUsuario(EmpleadoPojo empleado,Integer perfil, String audita, String region);	
	
	String actualizaEmpleados(String [] empleados,Integer idPerfil,String usuario);
	
	String borrarEmpleados(String [] empleados,String usuario);
	
	List<Map<String, TransaccionReportePojo>> getReporte(String year,Integer mes, String region);
	
	List<ConsultaReportePojo> getReporteDetalle(String year,Integer mes, String region);
	
	String regresarTransferenciaBatch(Integer idTransferencia,Integer idEmpleado,String comentario,Integer batch);
	
	public boolean insertaTransferenciasDesglosadas(TransferenciasDesglosadasPojo pojoTransDes);
	
	public boolean ifExistTransferenciaDesglosadas(String correo, String paramtro, String estatus);
	
	String setAplicaTransferenciasCompensacion(Hashtable<String,String> lista,Integer idEmpleado, String tipo);
	
	String setRegresaTransferenciasCompensacion(List<String> lista,Integer idEmpleado);
	
	
}
