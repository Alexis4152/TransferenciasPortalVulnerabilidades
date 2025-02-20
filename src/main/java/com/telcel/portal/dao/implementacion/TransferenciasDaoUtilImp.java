package com.telcel.portal.dao.implementacion;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.sql.rowset.WebRowSet;

import org.apache.log4j.Logger;

import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.factory.enumeration.TiposDatosEnum;
import com.telcel.portal.factory.vo.ParametrosVo;
import com.telcel.portal.dao.interfaces.TransferenciasDaoUtilInterface;
import com.telcel.portal.pojos.CicloPojo;
import com.telcel.portal.pojos.DetallePojo;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.PeticionPojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.ConstantesTDUI;
import com.telcel.portal.util.FactoryUtils;
import com.telcel.portal.util.PropertiesFiles;

public class TransferenciasDaoUtilImp  implements TransferenciasDaoUtilInterface {
	
	private static Logger  logger = Logger.getLogger(GeneralDAO.class);

	//OK PROBADA
	public String getCountTransferenciasRegresadas(Integer idEmpleado, String region) {
		
		 //SELECT COUNT(*) AS TOTAL from TRA_TRANSFERENCIA WHERE IDESTATUS=2 AND IDEMPLEADO=?
		String count="0";       
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
		region=region.replace("R0", "");
		try {
				
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				ParametrosVo params = new ParametrosVo();
			    params.setNameQuery("S_PTRA_TRANCOUNTRG");	
				params.addParam(idEmpleado);
				params.addParam(region);
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));

				while(rowSet.next()) {
					
					count= rowSet.getString("TOTAL");
				}
			 
		} catch (Exception e) { logger.info(e.getMessage());
			
		}
		return count;
	}

	//OK PROBADA
	public String getCountTransferenciasbyEstatus(Integer idEstatus,Date fecha,Date fecha2, String region) {
		
		String pojo = null;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
			
	    try {
	    	
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			//SELECT COUNT(*) AS TOTAL from TRA_TRANSFERENCIA WHERE IDESTATUS=? AND ID_REGION=?
		    params.setNameQuery("S_PTRA_TRANSCOUNTSF");	
			params.addParam(idEstatus);
			
			if(fecha!=null){
				//SELECT COUNT(*) AS TOTAL from TRA_TRANSFERENCIA WHERE IDESTATUS=? AND FECHA>=? AND FECHA<? AND ID_REGION=?
				params.setNameQuery("S_PTRA_TRANSCOUNT");	
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 
					cal.setTimeInMillis(fecha2.getTime());
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND,0);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));			 
					 
				 }
			 }
			 region=region.replace("R0", "");
			 params.addParam(region);
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			
			 while(rowSet.next()) {
					
				 pojo=  rowSet.getString("TOTAL");

			}
					
		} catch (Exception e) { logger.info(e.getMessage());
			logger.info(e.getMessage());
		}
		return pojo;
	}

	public String getCountTransferenciasbyEstatusCompensacion(Integer idEstatus,Date fecha,Date fecha2, String region) {
		
		String pojo = null;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
			
	    try {
	    	
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			//SELECT COUNT(*) AS TOTAL from TRA_TRANSFERENCIA WHERE IDESTATUS=? AND ID_REGION=?
			
			if(fecha!=null){
				//SELECT COUNT(*) AS TOTAL from TRA_TRANSFERENCIA WHERE IDESTATUS=? AND FECHA>=? AND FECHA<? AND ID_REGION=?
				params.setNameQuery("S_PTRA_TRANSCOUNT_COMPENSACION");	
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 
					cal.setTimeInMillis(fecha2.getTime());
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND,0);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));			 
					 
				 }
				region=region.replace("R0", "");
				params.addParam(region);
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));

				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 
					cal.setTimeInMillis(fecha2.getTime());
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND,0);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));			 
					 
				 }
				 
				region=region.replace("R0", "");
			 	params.addParam(region);
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));

				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 
					cal.setTimeInMillis(fecha2.getTime());
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND,0);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));			 
					 
				 }
			 }
			 region=region.replace("R0", "");
			 params.addParam(region);

			 

			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			
			 while(rowSet.next()) {
					
				 pojo=  rowSet.getString("TOTAL");

			}
					
		} catch (Exception e) { logger.info(e.getMessage());
			logger.info(e.getMessage());
		}
		return pojo;
	}

	//OK PROBADA
	public DetallePojo getDetallesTransferencia(Integer idTransferencia) {
 
		List<PagoPojo> lista = null;		
		DetallePojo detalle = new DetallePojo();
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
		
	    try {
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
		    params.setNameQuery("S_PTRA_PAGOSBYTRAN");	
			params.addParam(idTransferencia);
			
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
			SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
			lista = new ArrayList<PagoPojo>();
			while(rowSet.next()) {
					
					PagoPojo pojo= new PagoPojo();	
					
					pojo.setIdPago(rowSet.getInt("IDPAGOS"));
					pojo.setTipo(rowSet.getString("TIPO"));
					pojo.setCuenta(rowSet.getString("CUENTA"));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					pojo.setRegion(rowSet.getString("REGION"));
					pojo.setTipo(rowSet.getString("TIPO"));
					pojo.setSap(rowSet.getString("SAP")!=null ? rowSet.getString("SAP") : "");
					pojo.setLote(rowSet.getString("LOTE"));
					
					if(rowSet.getString("ESTATUSPAGO") == null){
						pojo.setEstatusPago("");
						pojo.setDescripcion("");
					} else{
						pojo.setEstatusPago(rowSet.getString("ESTATUSPAGO"));
						pojo.setDescripcion(rowSet.getString(ConstantesTDUI.DESCRIPCION));
					}
						
					if(rowSet.getDate("FECHALOTE")!=null){
						pojo.setFechaLoteString(formatoFinal.format(rowSet.getDate("FECHALOTE")));
					}
					
					
					if(pojo.getImporte()!=null){
						
						  DecimalFormat formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					pojo.setIdEmpleado(rowSet.getInt(ConstantesTDUI.IDEMPLEADO));
					pojo.setIdTrans(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setEstatusPP(rowSet.getString(ConstantesTDUI.ESTATUSPP)!= null ? rowSet.getString(ConstantesTDUI.ESTATUSPP):"");
					pojo.setDescripcionPP(rowSet.getString(ConstantesTDUI.DESCRIPCIONPP)!= null ? rowSet.getString(ConstantesTDUI.DESCRIPCIONPP):"");
							
					lista.add(pojo);
				}
				detalle.setListaPagos(lista);
				    
				params = new ParametrosVo();
			    params.setNameQuery("S_PTRA_PAGOS_SUM");	
				params.addParam(idTransferencia);
				   
				rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
				lista = new ArrayList<PagoPojo>();
					while(rowSet.next()) {
						
						PagoPojo pojo= new PagoPojo();	
						pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
						pojo.setRegion(rowSet.getString("REGION"));
						pojo.setLote(rowSet.getString("LOTE"));
						if(pojo.getImporte()!=null){
							
							  DecimalFormat formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
							  pojo.setImporteString(formateador.format(pojo.getImporte()));
						}
								
						lista.add(pojo);
					}
				
				detalle.setListaPagosSuma(lista);
					
				detalle.setTransferenciaPojo(this.getTransferencia(idTransferencia));
				
			
		} catch (Exception e) {
			
			logger.info(e.getMessage());
			
		}
		

		
		
		return detalle;
	}
	
	public DetallePojo getDetallesTransferenciaCompensacion(Integer idTransferencia) {
		 
		List<PagoPojo> lista = null;		
		DetallePojo detalle = new DetallePojo();
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
		
	    try {
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
		    params.setNameQuery("S_PTRA_PAGOSBYTRANCOMPENSACION");	
			params.addParam(idTransferencia);
			
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
			SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
			lista = new ArrayList<PagoPojo>();
			while(rowSet.next()) {
					
					PagoPojo pojo= new PagoPojo();	
					
					pojo.setIdPago(rowSet.getInt("IDPAGOS"));
					pojo.setTipo(rowSet.getString("TIPO"));
					pojo.setCuenta(rowSet.getString("CUENTA"));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					pojo.setRegion(rowSet.getString("REGION"));
					pojo.setTipo(rowSet.getString("TIPO"));
					pojo.setSap(rowSet.getString("SAP")!=null ? rowSet.getString("SAP") : "");
					pojo.setLote(rowSet.getString("LOTE"));
					pojo.setNumeroDocumento(rowSet.getLong("NUM_DOC"));
					pojo.setIdCompensacion(rowSet.getLong("ID_COMPENSACION"));
					pojo.setNumeroDocumentoCompensacion(rowSet.getString("DESCESTATUS"));
					
					if(rowSet.getString("ESTATUSPAGO") == null){
						pojo.setEstatusPago("");
						pojo.setDescripcion("");
					} else{
						pojo.setEstatusPago(rowSet.getString("ESTATUSPAGO"));
						pojo.setDescripcion(rowSet.getString(ConstantesTDUI.DESCRIPCION));
					}
						
					if(rowSet.getDate("FECHALOTE")!=null){
						pojo.setFechaLoteString(formatoFinal.format(rowSet.getDate("FECHALOTE")));
					}
					
					
					if(pojo.getImporte()!=null){
						
						  DecimalFormat formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					pojo.setIdEmpleado(rowSet.getInt(ConstantesTDUI.IDEMPLEADO));
					pojo.setIdTrans(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setEstatusPP(rowSet.getString(ConstantesTDUI.ESTATUSPP)!= null ? rowSet.getString(ConstantesTDUI.ESTATUSPP):"");
					pojo.setDescripcionPP(rowSet.getString(ConstantesTDUI.DESCRIPCIONPP)!= null ? rowSet.getString(ConstantesTDUI.DESCRIPCIONPP):"");
							
					lista.add(pojo);
				}
				detalle.setListaPagos(lista);
				    
				params = new ParametrosVo();
			    params.setNameQuery("S_PTRA_PAGOS_SUM");	
				params.addParam(idTransferencia);
				   
				rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
				lista = new ArrayList<PagoPojo>();
					while(rowSet.next()) {
						
						PagoPojo pojo= new PagoPojo();	
						pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
						pojo.setRegion(rowSet.getString("REGION"));
						pojo.setLote(rowSet.getString("LOTE"));
						if(pojo.getImporte()!=null){
							
							  DecimalFormat formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
							  pojo.setImporteString(formateador.format(pojo.getImporte()));
						}
								
						lista.add(pojo);
					}
				
				detalle.setListaPagosSuma(lista);
					
				detalle.setTransferenciaPojo(this.getTransferencia(idTransferencia));
				
			
		} catch (Exception e) {
			
			logger.info(e.getMessage());
			
		}
		

		
		
		return detalle;
	}

	//OK PROBADA
	public EmpleadoPojo getEmpleadoById(String idEmpleado) {
		EmpleadoPojo pojo = null;

		try {
			
			//SELECT * FROM TRA_EMPLEADO JOIN TRA_PERFIL ON TRA_EMPLEADO.IDPERFIL=TRA_PERFIL.IDPERFIL WHERE ESTATUSUSR = 1 AND TRA_EMPLEADO.USUARIO=?
			GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES); // xml/general
			
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_TRANS13");
			params.addParam(idEmpleado.trim());
			 
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));			
				
			while(rowSet.next()) {
				pojo= new EmpleadoPojo();
				pojo.setIdEmpleado(rowSet.getInt(ConstantesTDUI.IDEMPLEADO));
				pojo.setNombre(rowSet.getString("NOMBRE"));
				pojo.setPuesto(rowSet.getString("PUESTO"));
				pojo.setIdPefril(rowSet.getInt("IDPERFIL"));
				pojo.setAuditaUsuario(rowSet.getString(ConstantesTDUI.AUDITAUSR));
				pojo.setFechaAudita(rowSet.getString("AUDITAFECHA"));
				pojo.setEstatusUsuario(rowSet.getInt("ESTATUSUSR"));
				pojo.setNumeroEmpleado("");
				pojo.setDescripcionPerfil(rowSet.getString(ConstantesTDUI.DESCRIPCION));
				pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
				pojo.setIdRegion(rowSet.getInt("ID_REGION"));
				pojo.setDescRegion(rowSet.getString("DESC_REGION"));
			}
			 
		} catch (Exception e) { 		
			logger.info(e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		return pojo;
	}

	//OK PROBADA
	public List<EmpleadoPojo> getEmpleados(String auditauser, String descRegion) {
		
		logger.info("--- auditaUser: " + auditauser) ;
		EmpleadoPojo pojo = null;
		List<EmpleadoPojo> lista = null;
		String audita = auditauser; 	

		try {
		 	//SELECT * FROM TRA_EMPLEADO E JOIN TRA_PERFIL P ON E.IDPERFIL=P.IDPERFIL ORDER BY NOMBRE
			 GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES); // xml/general
				
			 PropertiesFiles prop = new PropertiesFiles();
			 Properties p = prop.getPropertiesConf();
			 ParametrosVo params = new ParametrosVo();
			 params.addParam(descRegion.replace("R0", ""));
			 params.setNameQuery("S_PTRA_EMPLEADOS");
			 	 
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));			
				 
			 lista = new ArrayList<EmpleadoPojo>();
			 while(rowSet.next()) {
					
					String temp = rowSet.getString(ConstantesTDUI.AUDITAUSR);
					temp = temp.replace(" ", "");
					audita = audita.replace(" ", "");
					if(audita.trim().equals(temp)){
											
						pojo= new EmpleadoPojo();			
						pojo.setIdEmpleado(rowSet.getInt(ConstantesTDUI.IDEMPLEADO));
						pojo.setNombre(rowSet.getString("NOMBRE"));
						pojo.setPuesto(rowSet.getString("PUESTO"));
						pojo.setIdPefril(rowSet.getInt("IDPERFIL"));
						pojo.setAuditaUsuario(rowSet.getString(ConstantesTDUI.AUDITAUSR));
						pojo.setFechaAudita(rowSet.getString("AUDITAFECHA"));
						pojo.setEstatusUsuario(rowSet.getInt("ESTATUSUSR"));
						pojo.setNumeroEmpleado("");
						pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
						pojo.setDescripcionPerfil(rowSet.getString(ConstantesTDUI.DESCRIPCION));
						lista.add(pojo);
					}
			 }
			 
		} catch (Exception e) { 
			logger.info(e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		return lista;
	}

	//OK PROBADA
	public List<EmpleadoPojo> getEmpleadosByAuditaUser(String auditaUsr, String descRegion){
		
		EmpleadoPojo pojo = null;
		List<EmpleadoPojo> lista = null;
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
		
		try {
		 	//SELECT * FROM TRA_EMPLEADO E JOIN TRA_PERFIL P ON E.IDPERFIL=P.IDPERFIL where e.AUDITAUSR=?
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_EMPLEADOS_AU");
			params.addParam(auditaUsr);
			params.addParam(descRegion.replace("R0", ""));
		 	 
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
		 	 
			lista = new ArrayList<EmpleadoPojo>();
			while(rowSet.next()) {
							
				pojo= new EmpleadoPojo();			

				pojo.setIdEmpleado(rowSet.getInt(ConstantesTDUI.IDEMPLEADO));
				pojo.setNombre(rowSet.getString("NOMBRE"));
				pojo.setPuesto(rowSet.getString("PUESTO"));
				pojo.setIdPefril(rowSet.getInt("IDPERFIL"));
				pojo.setAuditaUsuario(rowSet.getString(ConstantesTDUI.AUDITAUSR));
				pojo.setFechaAudita(rowSet.getString("AUDITAFECHA"));
				pojo.setEstatusUsuario(rowSet.getInt("ESTATUSUSR"));
				pojo.setNumeroEmpleado("");
				pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
				pojo.setDescripcionPerfil(rowSet.getString(ConstantesTDUI.DESCRIPCION));
				lista.add(pojo);		 	
			}
			
		} catch (Exception e) { 
			logger.info(e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		return lista;
	
	}

	//PENDIENTE, NO SE ENCUENTRAN LLAMADAS A ESTE METODO
	//NO SE PUDO PROBAR
	public List<TransferenciaPojo> getTransferenciasByBancoByFechaLote(Integer idBanco, Date fecha, Date fecha2, String region) {
		List<TransferenciaPojo> lista = new ArrayList<TransferenciaPojo>();
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("S_PTRA_REPORTE1_LIST");
								 
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				}else{	 
					cal.setTimeInMillis(fecha2.getTime());
					cal.set(Calendar.HOUR, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND,0);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }
//				Cambios Reporte Transferencias desglosadas
				String queryIdBanco;
				if (idBanco == 0) {
					queryIdBanco = "";
				}else {
					queryIdBanco="AND TT.IDBANCO='"+idBanco+"'";
				}
				params.addParam(queryIdBanco);
//				params.addParam(idBanco);
				params.addParam(region.replace("R0", ""));
			
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDUI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDUI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDUI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDUI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDUI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString("TIPOPAGOS"));
				   	pojo.setFecha(formatoFinal.format(rowSet.getDate("FECHATRANSFERENCIA")));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDUI.IDBANCO));
//					Cambios Reporte Transferencias Aplicadas
					pojo.setBanco(rowSet.getString("BANCO"));
					pojo.setAlias(rowSet.getString("ALIAS"));
					
					if(pojo.getImporte()!=null){
						  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			lista = null;
		}
		return lista;
	}
	
	//OK PROBADA
	//OK PROBADA
	public List<TransferenciaPojo> getTransferenciasByBancoByFechaTransferencia(Integer idBanco, Date fecha, Date fecha2, String region) {
		List<TransferenciaPojo> lista = null;
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("S_PTRA_TRANSBANC");
				params.addParam(idBanco);
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 cal.setTimeInMillis(fecha2.getTime());
					 cal.set(Calendar.HOUR, 0);
					 cal.set(Calendar.MINUTE, 0);
					 cal.set(Calendar.SECOND, 0);
					 cal.set(Calendar.MILLISECOND,0);
					 cal.add(Calendar.DAY_OF_YEAR, 1);
					 params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }
				region=region.replace("R0", "");
				params.addParam(region);
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
				lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDUI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDUI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDUI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDUI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDUI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString("TIPOPAGOS"));
				    pojo.setEstatusDescripcion(rowSet.getString(ConstantesTDUI.DESCRIPCION));
					pojo.setFecha(formatoFinal.format(rowSet.getDate("FECHATRANSFERENCIA")));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDUI.IDBANCO));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}
	
	public List<TransferenciaPojo> getTransferenciasByUserByFechaTransferencia(Date fecha, Date fecha2) {
		List<TransferenciaPojo> lista = null;
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("S_PTRA_TRANSUSER");
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 cal.setTimeInMillis(fecha2.getTime());
					 cal.set(Calendar.HOUR, 0);
					 cal.set(Calendar.MINUTE, 0);
					 cal.set(Calendar.SECOND, 0);
					 cal.set(Calendar.MILLISECOND,0);
					 params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }
			 
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
				lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
					pojo.setNombreUsuario(rowSet.getString(ConstantesTDUI.NOMBREUSUARIO));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}
	
	public List<TransferenciaPojo> getDetalleTransferenciasByUserByFechaTransferencia(Date fecha, Date fecha2) {
		List<TransferenciaPojo> lista = null;
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("S_PTRA_TRANSUSER_DETALLE");
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 cal.setTimeInMillis(fecha2.getTime());
					 cal.set(Calendar.HOUR, 0);
					 cal.set(Calendar.MINUTE, 0);
					 cal.set(Calendar.SECOND, 0);
					 cal.set(Calendar.MILLISECOND,0);
					 params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }
			 
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
				lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
					pojo.setNombreUsuario(rowSet.getString(ConstantesTDUI.NOMBREUSUARIO));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					pojo.setFecha(formatoFinal.format(rowSet.getDate("FECHATRANSFERENCIA")));
					pojo.setAlias(rowSet.getString("ALIAS"));
					pojo.setTipoPagos(rowSet.getString("TIPOPAGOS"));
					//pojo.setFechaHistorial(rowSet.getString("FECHA_RECHAZO"));
					pojo.setFechaHistorial(rowSet.getString("FECHA_DESGLOCE"));
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
		}
		return lista;
	}
	
	public List<TransferenciaPojo> getTransferenciasByEstadoDeCuenta(Integer idBanco, Date fecha, String importe, String marca, String region) {//validar importe nulo
		List<TransferenciaPojo> lista = null;
		List<TransferenciaPojo> listaMarcadaM2K = null;
		List<TransferenciaPojo> listaMarcadaSAP = null;
		HashMap<String,TransferenciaPojo> listaCompleta = null;
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				String query="";
				
				if(importe != null){
					query="S_PTRA_TRANSEDO1";
				}else {
					query="S_PTRA_TRANSEDO2";
				}
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery(query);
				params.addParam(idBanco);
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				
				cal.add(Calendar.DAY_OF_YEAR, 1);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				
				
				if(importe != null){
					double importeTrans = 0;
					try{
						importeTrans = Double.valueOf(importe);
					}catch(Exception e){
						logger.debug(e.getMessage(), e);
						importeTrans = -1;
					}
					params.addParam(importeTrans);
				}
				region=region.replace("R0", "");
				params.addParam(region);
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
				lista = new ArrayList<TransferenciaPojo>();
				listaMarcadaM2K = new ArrayList<TransferenciaPojo>();
				listaMarcadaSAP = new ArrayList<TransferenciaPojo>();
				listaCompleta = new HashMap<String, TransferenciaPojo>();
	
				while(rowSet.next()) {
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDUI.CUENTABANCO));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDUI.FECHA)));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					pojo.setTipoPagos(rowSet.getString("TIPOPAGO"));
					pojo.setEstatusDescripcion(rowSet.getString("ESTATUS"));
					pojo.setCliente(rowSet.getString("CLIENTE"));
					pojo.setEstatus(rowSet.getInt("ESTATUSHISTO"));
					pojo.setConcepto(rowSet.getString("COMENTARIO"));
					
					if(pojo.getImporte() != null){
						
						  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					if(pojo.getEstatus() == ConstantesNumeros.QUINCE){
						
						String comentario = pojo.getConcepto();
					
						comentario = (comentario.length()< ConstantesNumeros.ONCE) ? "TRANS DESMARCADA" : comentario;
						
						 if(comentario.substring(ConstantesNumeros.CERO,ConstantesNumeros.ONCE).matches("MARCADA M2K")){
							 pojo.setAlias("M2K");
							 pojo.setConcepto(comentario.substring(ConstantesNumeros.ONCE, comentario.length()));
							 listaMarcadaM2K.add(pojo);
						 }else if(comentario.substring(ConstantesNumeros.CERO,ConstantesNumeros.ONCE).matches("MARCADA SAP")){
							 pojo.setAlias("SAP");
							 pojo.setConcepto(comentario.substring(ConstantesNumeros.ONCE, comentario.length()));
							 listaMarcadaSAP.add(pojo);
						 }else{
							 //DESMARCADAS
							 pojo.setAlias("");
							 pojo.setConcepto("");
						 }
						 listaCompleta.put(String.valueOf(pojo.getIdtransferencia()), pojo);
					}else if(!listaCompleta.containsKey(String.valueOf(pojo.getIdtransferencia()))){
						
						listaCompleta.put(String.valueOf(pojo.getIdtransferencia()), pojo);
						
					}
				}
				lista.addAll(listaCompleta.values());
		} catch (Exception e) { logger.info( e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		
	
		if(marca.equals("M2K")){
			return listaMarcadaM2K;
		}else if(marca.equals("SAP")) {
			return listaMarcadaSAP;
		}
		
		return lista;
	}
	
	public TransferenciaPojo getTransferenciasByPagosNoIdent(String region, int lote, Date fecha, String idRegion) {
	
		TransferenciaPojo pojo = null;
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd/MM/yyyy");
		 	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
	
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("S_PTRA_TRANSPAGONO");
				params.addParam(region);
				params.addParam(lote);
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				idRegion=idRegion.replace("R0", "");
				params.addParam(idRegion);
			 
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
	
				if(rowSet.next()) {
					pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setFechaHistorial(formatoFinal.format(rowSet.getDate(ConstantesTDUI.FECHA)));
					pojo.setImporteTrans(rowSet.getDouble("IMPORTETRANS"));
					pojo.setBanco(rowSet.getString("BANCO"));
					pojo.setImporte(rowSet.getDouble("TOTAL"));
					
					
					if(pojo.getImporte() != null){
						
						  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
						  pojo.setImporteTransString(formateador.format(pojo.getImporteTrans()));
					}
					
				}

		} catch (Exception e) { logger.info( e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		
		
		return pojo;
	}


	//OK PROBADA
	public List<TransferenciaPojo> getTransferenciasByEmpleado(EmpleadoPojo empleado) {
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
		 
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 
		 FactoryUtils util = new FactoryUtils();	
		 ParametrosVo params = new ParametrosVo();
		 
		
		 if(empleado.getPuesto().indexOf("JEFE") >= 0){
			 
			 List<EmpleadoPojo> empleados = this.getEmpleadosByAuditaUser(empleado.getUsuario(), empleado.getDescRegion());
			 String[]  empleadosIN = new String[empleados.size()+1];
			 int i=0;
			 logger.info("*******------------empleados.size()+1:"+empleados.size());
			 logger.info("*******------------empleadosINSS:"+empleadosIN.length);
			 for(EmpleadoPojo emPojo:empleados){
				 logger.info( i);
				 empleadosIN[i]=emPojo.getIdEmpleado().toString();
				 i++;
			 }
			 
			 logger.info("*******------------acabo el for:");
			 logger.info("*******------------acabo el for:"+i);
			 empleadosIN[i]=empleado.getIdEmpleado().toString();
			 StringBuffer inEmpleados = util.getParamIn(empleadosIN, TiposDatosEnum.TYPE_NUMERIC.getType());
			 params.addParam(inEmpleados.toString());
			 
		 }else{
			 
			 EmpleadoPojo empleadoJefe = this.getEmpleadoById(empleado.getAuditaUsuario());
			 String[]  empleadosIN = new String[2];
			 empleadosIN[0]=empleado.getIdEmpleado().toString();
			 empleadosIN[1]=empleadoJefe.getIdEmpleado().toString();
			 StringBuffer inEmpleados = util.getParamIn(empleadosIN, TiposDatosEnum.TYPE_NUMERIC.getType());
			 params.addParam(inEmpleados.toString());
			 
		 }
		 String region=empleado.getDescRegion();
		 region=region.replace("R0", "");
		 params.addParam(region);
		try {
			params.setNameQuery("S_PTRA_TRANS_IN");
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
				
			lista = new ArrayList<TransferenciaPojo>();
			while(rowSet.next()) {
					
				TransferenciaPojo pojo= new TransferenciaPojo();			
				pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
				pojo.setCuenta(rowSet.getString(ConstantesTDUI.CUENTABANCO));
				pojo.setCliente(rowSet.getString(ConstantesTDUI.REFERENCIACLIENTE));
				pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
				pojo.setProceso(rowSet.getInt(ConstantesTDUI.IDPROCESO));
				pojo.setReferenciaBanco(rowSet.getString(ConstantesTDUI.REFERENCIABANCO));
				pojo.setEstatus(rowSet.getInt(ConstantesTDUI.IDESTATUS));
				pojo.setAlias(rowSet.getString("ALIAS"));				
				pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDUI.FECHA)));
				pojo.setIdBanco(rowSet.getInt(ConstantesTDUI.IDBANCO));
				pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
				if(pojo.getImporte()!=null){
						
					  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
					  pojo.setImporteString(formateador.format(pojo.getImporte()));
				}
				pojo.setPkIdReferencia(rowSet.getString("PK_ID_REFERENCIA"));	
				lista.add(pojo);
			}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}

	//OK PROBADA
	public List<TransferenciaPojo> getTransferenciasRegresadas(Integer idEmpleado, String region) {
		List<TransferenciaPojo> lista = null;
		DecimalFormat formateador;
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		region=region.replace("R0", "");
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general
		try {
			//SELECT * FROM TRA_TRANSFERENCIA WHERE  IDESTATUS=2 AND IDEMPLEADO=?	
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
		    params.setNameQuery("S_PTRA_TRANSRG");	
			params.addParam(idEmpleado);
			params.addParam(region);
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			lista = new ArrayList<TransferenciaPojo>();
			while(rowSet.next()) {
					
				TransferenciaPojo pojo= new TransferenciaPojo();			
				pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
				pojo.setCuenta(rowSet.getString(ConstantesTDUI.CUENTABANCO));
				pojo.setCliente(rowSet.getString(ConstantesTDUI.REFERENCIACLIENTE));
				pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
				pojo.setProceso(rowSet.getInt(ConstantesTDUI.IDPROCESO));
				pojo.setReferenciaBanco(rowSet.getString(ConstantesTDUI.REFERENCIABANCO));
				pojo.setEstatus(rowSet.getInt(ConstantesTDUI.IDESTATUS));
				pojo.setTipoPagos(rowSet.getString("TIPOPAGOS"));
				pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDUI.FECHA)));
				pojo.setIdBanco(rowSet.getInt(ConstantesTDUI.IDBANCO));
				pojo.setAlias(rowSet.getString("ALIAS"));
					
				if(pojo.getImporte()!=null){
						
					  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
					  pojo.setImporteString(formateador.format(pojo.getImporte()));
				}		
				lista.add(pojo);
			}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}

	//OK PROBADA
    public TransferenciaPojo getTransferencia(Integer idTransferencia) {
		
		DecimalFormat formateador;
		TransferenciaPojo pojo = null;		 
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  // xml/general		 
		
	    try {
	    	//SELECT TRA_TRANSFERENCIA.*,TRA_BANCO.NOMBRE AS NOMBREBANCO,TRA_ESTATUS.DESCRIPCION FROM TRA_TRANSFERENCIA JOIN TRA_BANCO ON TRA_TRANSFERENCIA.IDBANCO=TRA_BANCO.IDBANCO JOIN TRA_ESTATUS ON TRA_TRANSFERENCIA.IDESTATUS=TRA_ESTATUS.IDESTATUS WHERE IDTRANSFERENCIA=?
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
	    	params.setNameQuery("S_PTRA_TRAN1");	
			params.addParam(idTransferencia);
		
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			
			while(rowSet.next()) {
					
				pojo= new TransferenciaPojo();			
				pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
				pojo.setCuenta(rowSet.getString(ConstantesTDUI.CUENTABANCO));
				pojo.setCliente(rowSet.getString(ConstantesTDUI.REFERENCIACLIENTE));
				pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
				pojo.setProceso(rowSet.getInt(ConstantesTDUI.IDPROCESO));
				pojo.setReferenciaBanco(rowSet.getString(ConstantesTDUI.REFERENCIABANCO));
				pojo.setEstatus(rowSet.getInt(ConstantesTDUI.IDESTATUS));
				pojo.setAlias(rowSet.getString("ALIAS"));
				pojo.setRegion(String.valueOf(rowSet.getInt("ID_REGION")));
					
				pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDUI.FECHA)));
				pojo.setIdBanco(rowSet.getInt(ConstantesTDUI.IDBANCO));
				pojo.setBanco(rowSet.getString("NOMBREBANCO"));
				pojo.setEstatusDescripcion(rowSet.getString(ConstantesTDUI.DESCRIPCION));
				pojo.setTipoPagos(rowSet.getString(ConstantesTDUI.TIPOPAGOS));
				pojo.setIdEmpleado(rowSet.getInt(ConstantesTDUI.IDEMPLEADO));
				if(pojo.getImporte()!=null){
						
					  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
					  pojo.setImporteString(formateador.format(pojo.getImporte()));
				}
				pojo.setPkIdReferencia(rowSet.getString("PK_ID_REFERENCIA"));
			}
		} catch (Exception e) { 
			
			logger.info( e.getMessage());
			
		}
		return pojo;
	}

    public boolean insertaHistorial(Integer idEmpleado, Integer idEstatus,Integer idTransferencia, String comnt) {

    	boolean resultado=false;

    	try {
    		//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA,COMENTARIO) VALUES(historial_seq.nextval,SYSDATE,?,?,?,'?')
    		PropertiesFiles prop = new PropertiesFiles();
    		Properties p = prop.getPropertiesConf();
    		GeneralDAO dao = new GeneralDAO(1); //xml/desglose
    		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
    		
    		ParametrosVo params = new ParametrosVo();
			params.setNameQuery("D_PTRA_HISTORIALCOME");
			params.addParam(idTransferencia);
			params.addParam(idEstatus);	
    	   
			
			lrequestvo.add(params);	
    		
    		params = new ParametrosVo();
    		params.setNameQuery("I_PTRA_HISTORIALCOME");
    		params.addParam(idEmpleado);
    		params.addParam(idEstatus);	
    	    params.addParam(idTransferencia);
    	    params.addParam(comnt);
    	    
    	    lrequestvo.add(params);	

    	    dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDUI.APLTIC));
    	    
    		resultado=true;
    		
    	} catch (Exception e) { logger.info( e.getMessage());
    		
    	 	resultado=false;
    	}
    	
    	return resultado;

    }

    
    public boolean actualizaHistorial(Integer idEmpleado, Integer idEstatus,Integer idTransferencia, String comnt) {

    	boolean resultado=false;

    	try {
    		//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA,COMENTARIO) VALUES(historial_seq.nextval,SYSDATE,?,?,?,'?')
    		//UPDATE TRA_HISTORIAL SET FECHA=SYSDATE, IDEMPLEADO=?, COMENTARIO='?' WHERE IDTRANSFERENCIA=? AND IDESTATUS=? 
    		PropertiesFiles prop = new PropertiesFiles();
    		Properties p = prop.getPropertiesConf();
    		GeneralDAO dao = new GeneralDAO(2); //xml/reportes
    		
    		ParametrosVo params = new ParametrosVo();
    		params.setNameQuery("U_PTRA_HISTORIALCOME");
    		params.addParam(idEmpleado);
    		params.addParam(comnt);
    	    params.addParam(idTransferencia);
    	    params.addParam(idEstatus);
    	    
    	   
    	    dao.executeSql(params, p.getProperty(ConstantesTDUI.APLTIC), null);
    	    
    		resultado=true;
    		
    	} catch (Exception e) { logger.info( e.getMessage());
    		
    	 resultado=false;
    	}
    	
    	return resultado;

    }	
	
    
    public int getAccesFull(String sUser){
    	
    	int bStatus = 0;
	    try{
	    	GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);  
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
		    params.setNameQuery("S_PTRA_ACCESS_CAMPOS");	
			params.addParam(sUser);
    	
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
				
			 while(rowSet.next()) {
				 String sCve_User = rowSet.getString("ID2");
				 if(!sCve_User.equals("") && sCve_User.equals(sUser)){
					 bStatus = 1;
				 }
			}
	    }catch(Exception ex){
	    	ex.getMessage();
	    }	
    	return bStatus;
    }
    
    public List<TransferenciaPojo> getTransferenciasRechazadas(Date fecha, Date fecha2, String idBanco) {
		List<TransferenciaPojo> lista = null;
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("S_PTRA_TRANSRECHAZADAS");
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 cal.setTimeInMillis(fecha2.getTime());
					 cal.set(Calendar.HOUR, 0);
					 cal.set(Calendar.MINUTE, 0);
					 cal.set(Calendar.SECOND, 0);
					 cal.set(Calendar.MILLISECOND,0);
					 params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }
				String queryBanco="";
				if(!idBanco.equals("0")) {
					queryBanco+=" AND T.IDBANCO = "+idBanco+" ";
				}
				params.addParam(queryBanco);
			 
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
				lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
					pojo.setNombreUsuario(rowSet.getString(ConstantesTDUI.NOMBREUSUARIO));
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}
	
	public List<TransferenciaPojo> getDetalleTransferenciasRechazadas(Date fecha, Date fecha2, String idBanco) {
		List<TransferenciaPojo> lista = null;
		DecimalFormat formateador;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDUI.FORMATOFECHA);
		 	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
			try {
				PropertiesFiles prop = new PropertiesFiles();
				Properties p = prop.getPropertiesConf();
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("S_PTRA_TRANSRECHAZADAS_DETALLE");
				
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				
				if(fecha2==null){			
					cal.add(Calendar.DAY_OF_YEAR, 1);
					params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }else{
					 cal.setTimeInMillis(fecha2.getTime());
					 cal.set(Calendar.HOUR, 0);
					 cal.set(Calendar.MINUTE, 0);
					 cal.set(Calendar.SECOND, 0);
					 cal.set(Calendar.MILLISECOND,0);
					 params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
				 }
				String queryBanco="";
				if(!idBanco.equals("0")) {
					queryBanco+=" AND T.IDBANCO = "+idBanco+" ";
				}
				params.addParam(queryBanco);
				WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			 
				lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDUI.IDTRANSFERENCIA));
					pojo.setUsuario(rowSet.getString(ConstantesTDUI.USUARIO));
					pojo.setNombreUsuario(rowSet.getString(ConstantesTDUI.NOMBREUSUARIO));
					pojo.setImporte(rowSet.getDouble(ConstantesTDUI.IMPORTE));
					pojo.setFecha(formatoFinal.format(rowSet.getDate("FECHATRANSFERENCIA")));
					pojo.setAlias(rowSet.getString("ALIAS"));
					pojo.setTipoPagos(rowSet.getString("TIPOPAGOS"));
					pojo.setFechaHistorial(rowSet.getString("FECHA_RECHAZO"));
					pojo.setDescripcion(rowSet.getString("MOTIVO"));
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
		}
		return lista;
	}

	public List<CicloPojo> getReporteCiclos(Date fechaInicio, Date fechaFin) {
		List<CicloPojo> lista = null;
		GeneralDAO dao = new GeneralDAO(2);
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_PAGOS_CICLO");
		params.addParam(fechaInicio);
		params.addParam(fechaFin);
		
		try {
			WebRowSet rowSet = dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			lista = new ArrayList<CicloPojo>();
			while(rowSet.next()) {
				CicloPojo ciclo = new CicloPojo();
				ciclo.setCiclo(rowSet.getInt("CICLO"));
				ciclo.setCantidad(rowSet.getInt("CANTIDAD"));
				lista.add(ciclo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return lista;
	}
	
	public List<PeticionPojo> getReportePeticiones(Date fechaInicio, Date fechaFin) {
		List<PeticionPojo> lista = null;
		GeneralDAO dao = new GeneralDAO(2);
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_TRANSFER_MONTO");
		params.addParam(fechaInicio);
		params.addParam(fechaFin);
		
		try {
			WebRowSet rowSet = dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			lista = new ArrayList<PeticionPojo>();
			while(rowSet.next()) {
				PeticionPojo peticion = new PeticionPojo();
				peticion.setAcesor(rowSet.getString("NOMBRE"));
				peticion.setMonto(rowSet.getDouble("IMPORTE"));
				lista.add(peticion);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<TransferenciaPojo> getReporteTransIngresos(Date fechaInicio, Date fechaFin) {
		List<TransferenciaPojo> lista = null;
		GeneralDAO dao = new GeneralDAO(2);
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_TRANSFER_INGRESOS");
		params.addParam(fechaInicio);
		params.addParam(fechaFin);
		
		try {
			WebRowSet rowSet = dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));
			lista = new ArrayList<TransferenciaPojo>();
			while(rowSet.next()) {
				TransferenciaPojo transferencia = new TransferenciaPojo();
				transferencia.setUsuario(rowSet.getString("USUARIO"));
				transferencia.setNombreUsuario(rowSet.getString("NOMBRE"));
				transferencia.setFechaTransferencia(rowSet.getString("FECHA_TRANSFERENCIA"));
				transferencia.setImporte(rowSet.getDouble("IMPORTE"));
				transferencia.setAlias(rowSet.getString("ALIAS"));
				transferencia.setIdtransferencia(rowSet.getInt("IDTRANSFERENCIA"));
				transferencia.setTipoPagos(rowSet.getString("TIPOPAGOS"));
				transferencia.setFecha(rowSet.getString("FECHA_DESGLOCE"));//fecha desgloce
				transferencia.setIndicador(rowSet.getString("HORA_DESGLOCE"));//hora desglose
				transferencia.setReferenciaCliente(rowSet.getString("REFERENCIACLIENTE"));
				transferencia.setBanco(rowSet.getString("BANCO"));
				transferencia.setCuenta(rowSet.getString("CUENTA"));
				lista.add(transferencia);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		}
		return lista;
	}
}
