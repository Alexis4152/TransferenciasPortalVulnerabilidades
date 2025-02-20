package com.telcel.portal.dao.implementacion;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.sql.rowset.WebRowSet;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import com.telcel.portal.dao.interfaces.TransferenciasDaoConsultaInterface;
import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.factory.vo.ParametrosVo;
import com.telcel.portal.pojos.AlertasPojo;
import com.telcel.portal.pojos.BancoPojo;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.EstatusPojo;
import com.telcel.portal.pojos.HistorialPojo;
import com.telcel.portal.pojos.PeticionesPojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.util.Constantes;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.ConstantesTDDCI;
import com.telcel.portal.util.ConstantesTDDI;
import com.telcel.portal.util.ConstantesTDUI;
import com.telcel.portal.util.PropertiesFiles;

public class TransferenciasDaoDmpConsultaImp implements TransferenciasDaoConsultaInterface{
	//OK PROBADA
	
	private static Logger  logger = Logger.getLogger(TransferenciasDaoDmpConsultaImp.class);
	
	public List<BancoPojo> getBancos() {
		
		List<BancoPojo> lista = null;	
		
		/*
		 * SELECT * FROM TRA_BANCO ORDER BY IDBANCO
		 * */
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_BANCOS"); 
		
	    try {
	    	 
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 lista = new ArrayList<BancoPojo>();
				while(rowSet.next()) {
					
					BancoPojo pojo= new BancoPojo();			
					pojo.setIdbanco(rowSet.getString(ConstantesTDDCI.IDBANCO));
					pojo.setNombre(rowSet.getString(ConstantesTDDCI.NOMBRE));
					pojo.setNombreCorto("NOMBRECORTO");				
					lista.add(pojo);
				}
			 
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			
		}
		
		return lista;
	}

	//OK PROBADA N+ 
	public List<HistorialPojo> getHistorial(Integer idTransferencia) {
		 
		List<HistorialPojo> lista = null;			
		 
	    try {
	    	//SELECT * FROM TRA_HISTORIAL JOIN TRA_EMPLEADO ON TRA_HISTORIAL.IDEMPLEADO=TRA_EMPLEADO.IDEMPLEADO WHERE TRA_HISTORIAL.IDTRANSFERENCIA=? ORDER BY TRA_HISTORIAL.IDHISTORIAL ASC
	    	GeneralDAO dao = new GeneralDAO(2); // xml/reportes
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_HISTORIAL"); 
			params.addParam(idTransferencia);
	    				  
			logger.info("------------Historial");

			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 
			lista = new ArrayList<HistorialPojo>();
				while(rowSet.next()) {
					
					HistorialPojo pojo= new HistorialPojo();			
					pojo.setEmpleado(rowSet.getString(ConstantesTDDCI.NOMBRE));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setComentario(rowSet.getString("COMENTARIO"));
					pojo.setStatusString(rowSet.getString(ConstantesTDDCI.DESCRIPCION));
					String st= rowSet.getString(ConstantesTDDCI.FECHA).trim(); 
					pojo.setFecha(formatoFecha(st));
					lista.add(pojo);
				}
			 
				
		} catch (Exception e) { 
						
		}
		return lista;
	}
	//OK PROBADA N+
	public String formatoFecha2(String s){  //  21/02/13 10:09:08.206000   LOCAL
		String dia = s.substring(0,2);    
		String mes = s.substring(ConstantesNumeros.TRES,ConstantesNumeros.CINCO);
		String anio = s.substring(ConstantesNumeros.SEIS,ConstantesNumeros.OCHO);
		String hora = s.substring(ConstantesNumeros.NUEVE, ConstantesNumeros.DIECISIETE);
		String fechaFormato = "";
				
		fechaFormato = dia+"-"+mes+"-"+anio+" "+hora;
		return fechaFormato;
	}
	
	public String formatoFecha(String s){// 21-FEB-13 10.09.08 AM   PRODUCCION
		String dia = s.substring(0,2);
		String mes = s.substring(ConstantesNumeros.TRES,ConstantesNumeros.SEIS);
		String anio = s.substring(ConstantesNumeros.SIETE, ConstantesNumeros.NUEVE);
		String hora = s.substring(ConstantesNumeros.DIEZ, ConstantesNumeros.DOCE).trim();
		String minuto = s.substring(ConstantesNumeros.TRECE, ConstantesNumeros.QUINCE);
		String segundo = s.substring(ConstantesNumeros.DIECISEIS, ConstantesNumeros.DIECIOCHO);
		String fechaFormato = "";
		String m = s.substring(s.length()-2,s.length()).trim();
		
		
		if(mes.equals("ENE") || mes.equals("JAN")){
			mes = "01";
		}else if(mes.equals("FEB")){
			mes = "02";
		}else if(mes.equals("MAR")){
			mes = "03";
		}else if(mes.equals("ABR")|| mes.equals("APR")){
			mes = "04";
		}else if(mes.equals("MAY")){
			mes = "05";
		}else if(mes.equals("JUN")){
			mes = "06";
		}else if(mes.equals("JUL")){
			mes = "07";
		}else if(mes.equals("AGO") || mes.equals("AUG")){
			mes ="08";
		}else if(mes.equals("SEP")){
			mes = "09";
		}else if(mes.equals("OCT")){
			mes = "10";
		}else if(mes.equals("NOV")){
			mes = "11";
		}else if(mes.equals("DIC") || mes.equals("DEC")){
			mes = "12";
		}
		fechaFormato = dia+"-"+mes+"-"+anio+" "+hora+":"+minuto+":"+segundo+" "+m;
		return fechaFormato;
	}
	
	public boolean hayComentarioFA(Integer idTransferencia){
		
		List<HistorialPojo> listaHistorial = this.getHistorial(idTransferencia);
		
		for(HistorialPojo registro:listaHistorial){
			if(registro.getEstatus() == 16){
				return true;
			}
		}
		
		return false;
	}
	//OK PROBADA N+
	public List<EstatusPojo> getEstatus() {
		
		List<EstatusPojo> lista = null;	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_ESTATUS"); 	
		
	    try {

	    	WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			lista = new ArrayList<EstatusPojo>();
			while(rowSet.next()) {
					
					EstatusPojo pojo= new EstatusPojo();	
					pojo.setIdEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setDescripcion(rowSet.getString(ConstantesTDDCI.DESCRIPCION));				
					lista.add(pojo);
			} 
		} catch (Exception e) {
			logger.info( e.getMessage());	
		}
		return lista;
	}
		//OK PROBADA N+
	public List<TransferenciaPojo> getTransferenciasByEstatusByFechaReporte(Integer idEstatus, Date fecha, Date fecha2,Integer idEmpleado, String region) {
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		
		 Calendar cal=Calendar.getInstance();
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE  IDESTATUS=?  AND FECHA>=? AND FECHA<? 
		
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
			
			try {
				
			 params.setNameQuery("S_PTRA_TRANS10SF");
			
			 if(idEmpleado!=null){
				 params.addParam(idEmpleado);
			 }
			 
			 params.addParam(idEstatus);
			 
			 if(fecha!=null){
				 //S_PTRA_TRANS10
				 if(idEmpleado!=null){ 
					 params.setNameQuery("S_PTRA_REPCFEMPL");
				 }
				 else{
					 params.setNameQuery("S_PTRA_REPCF");
				 }
						 
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
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 
			  lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					pojo.setEstatusDescripcion(rowSet.getString(ConstantesTDDCI.DESCRIPCION));
					pojo.setBanco(rowSet.getString(ConstantesTDDCI.NOMBRE));
					pojo.setAlias(rowSet.getString("ALIAS"));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));// Se agrega nueva variable para identificar el tipo de trans
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}
	// OK PROBADA N+
	public List<TransferenciaPojo> getTransferenciasByEstatusByFechas(Integer idEstatus, Date fecha, Date fecha2,String region) {
		  
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		
		 Calendar cal=Calendar.getInstance();
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
		 
			try {
				
			if(idEstatus==Constantes.DESGLOSADA ){
			
				 params.setNameQuery("S_PTRA_PENDIENTES_T");
				 
			}else if(idEstatus==Constantes.APLICADA){
			
				 params.setNameQuery("S_PTRA_APLICADAS");
				 params.addParam(Constantes.APLICADA_PROCESO);
				
			}else{
				
				 params.setNameQuery("S_PTRA_TRANS10SF");
			}
				
			params.addParam(idEstatus);
			 
			 if(fecha!=null){
				 if(idEstatus!=Constantes.DESGLOSADA  && idEstatus!=Constantes.APLICADA){
					 params.setNameQuery("S_PTRA_TRANS10");
				 }
						 
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
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 
			 lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));
					pojo.setBanco(rowSet.getString(ConstantesTDDCI.NOMBRE));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					pojo.setAlias(rowSet.getString(ConstantesTDDCI.ALIAS));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					if(idEstatus==Constantes.DESGLOSADA || idEstatus==Constantes.APLICADA){
					
						java.util.Date date = rowSet.getDate("ULTIMAFECHA");
						pojo.setFechaHistorial(formatoFinal.format(date));
						pojo.setUsuario(rowSet.getString("USUARIO"));
						
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}
	
	public List<TransferenciaPojo> getTransferenciasByEstatusByFechasCompensacion(Integer idEstatus, Date fecha, Date fecha2,String region) {
		  
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		
		 Calendar cal=Calendar.getInstance();
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
		 
			try {
				
			if(idEstatus==Constantes.DESGLOSADA ){
			
				 params.setNameQuery("S_PTRA_PENDIENTES_T");
				 
			}else if(idEstatus==Constantes.APLICADA){
			
				 params.setNameQuery("S_PTRA_APLICADASCOMPENSA");
				 params.addParam(Constantes.APLICADA);
				
			}else{
				
				 params.setNameQuery("S_PTRA_TRANS10SF");
			}
			if(idEstatus!=Constantes.APLICADA){
				params.addParam(idEstatus);
			}
			 if(fecha!=null){
				 if(idEstatus!=Constantes.DESGLOSADA  && idEstatus!=Constantes.APLICADA){
					 params.setNameQuery("S_PTRA_TRANS10");
				 }
						 
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
			 if(idEstatus==Constantes.APLICADA){
				params.addParam(idEstatus);
				if(fecha!=null){
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
			   params.addParam(region);
			   params.addParam(idEstatus);
				if(fecha!=null){
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
			   params.addParam(region);
			}
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 
			 lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));
					pojo.setBanco(rowSet.getString(ConstantesTDDCI.NOMBRE));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					pojo.setAlias(rowSet.getString(ConstantesTDDCI.ALIAS));
					pojo.setIdCompensacion(rowSet.getLong("ID_COMPENSACION"));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					if(idEstatus==Constantes.DESGLOSADA || idEstatus==Constantes.APLICADA){
					
						java.util.Date date = rowSet.getDate("ULTIMAFECHA");
						pojo.setFechaHistorial(formatoFinal.format(date));
						pojo.setUsuario(rowSet.getString("USUARIO"));
						
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
			Set<TransferenciaPojo> unicos = new HashSet<>();

		    for(TransferenciaPojo pojo : lista) {
		    	if(!unicos.add(pojo)) {
		    		for (Iterator<TransferenciaPojo> it = unicos.iterator(); it.hasNext(); ) {
		    			TransferenciaPojo f = it.next();
		    	        if (f.equals(pojo)) {
		    	            f.getIdsCompensacion().add(pojo.getIdCompensacion());
		    	        	f.setIdsCompensacionString(Arrays.toString(f.getIdsCompensacion().toArray()));
		    	        }
		    	    }
		    	}
		    }
		    
			for (Iterator<TransferenciaPojo> it = unicos.iterator(); it.hasNext();) {
				TransferenciaPojo f = it.next();
				{
					f.getIdsCompensacion().add(f.getIdCompensacion());
					f.setIdsCompensacionString(Arrays.toString(f.getIdsCompensacion().toArray()));
					f.setIdsCompensacionString(f.getIdsCompensacionString().replace("[",""));
					f.setIdsCompensacionString(f.getIdsCompensacionString().replace("]",""));
				}
			}
		    
		    lista.clear();
		    
		    lista.addAll(unicos);
		return lista;
	}
	
	//OK PROBADA N+
	public List<TransferenciaPojo> getTransferenciasTomar(Integer idBanco, Date fecha, Date fecha2, String importe, String region) {
		  
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		 String query;
		 Calendar cal=Calendar.getInstance();	 
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=? AND IDESTATUS=2 AND IDEMPLEADO IS NULL AND FECHA>=? AND FECHA<? 
		 query="S_PTRA_TRANS7";
		 
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=? AND IDESTATUS=2 AND IDEMPLEADO IS NULL AND FECHA>=? AND FECHA<?  AND IMPORTE=? 
		 if(importe!=null){
			 query="S_PTRA_TRANS8";
			 
		 }
			 
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();

			try {
				
				params.setNameQuery(query);
				params.addParam(idBanco);
			 
					 
				cal.setTimeInMillis(fecha.getTime());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND,0);
			 
				params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));		
			
			 if(fecha2==null){			
				 cal.add(Calendar.DAY_OF_YEAR, 1);
				 logger.info(cal.getTime());
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
			 if(importe!=null){
				 params.addParam(importe+"%");
			 }
			 region=region.replace("R0", "");
			 params.addParam(region);
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 lista = new ArrayList<TransferenciaPojo>();
			  
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
				
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}

	//OK PROBADA N+
	public List<TransferenciaPojo> getTransferenciasBusqueda(Integer idBanco, Date fecha, String importe, String region) {
		  
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		 String query;
		 Calendar cal=Calendar.getInstance();
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=?  AND FECHA>=? AND FECHA<? 
		  query="S_PTRA_TRANS5";
		 
		  //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=?  AND FECHA>=? AND FECHA<? AND IMPORTE=? 
		 if(importe!=null){
			 query="S_PTRA_TRANS6";
		 }
		
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();

		try {
			  
			 params.setNameQuery(query);
			 params.addParam(idBanco);
			
			 cal.setTimeInMillis(fecha.getTime());
			 cal.set(Calendar.HOUR, 0);
			 cal.set(Calendar.MINUTE, 0);
			 cal.set(Calendar.SECOND, 0);
			 cal.set(Calendar.MILLISECOND,0);
			 
			 logger.info(cal.getTime());		
			 
			 params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
			 
			 cal.add(Calendar.DAY_OF_YEAR, 1);
			 logger.info(cal.getTime());
			 params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
			
			 
			 if(importe!=null){
				 params.addParam(importe+"%");
			 }
			 	region=region.replace("R0", "");
				params.addParam(region);
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					pojo.setEstatusDescripcion(rowSet.getString(ConstantesTDDCI.DESCRIPCION));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS)); //Se agrega el tipo de transferencia FA, CU o MIX
					pojo.setAlias(rowSet.getString(ConstantesTDDCI.ALIAS));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					lista.add(pojo);
				}
			
		} catch (Exception e) { 
			
			logger.info( e.getMessage());
			
		}
		return lista;
	}

	// OK PROBADA N+
	public List<TransferenciaPojo> getTransferencias(Integer idbanco,Date fecha,Date fecha2,Integer estatus,String importe, String region) {
		 
		  
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		 String query;
		 Calendar cal=Calendar.getInstance();	
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=? AND IDESTATUS=? AND FECHA>=? AND FECHA<? 
		 query="S_PTRA_TRANS1";
		 
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=? AND IDESTATUS=? AND FECHA>=? AND FECHA<?  AND IMPORTE=? 
		 if(importe!=null){
			 query="S_PTRA_TRANS2";
		 }
		
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
				
		 params.setNameQuery(query);
		 
	    try {
	    	 
	    	params.addParam(idbanco);	
	    	params.addParam(estatus);	
			 
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
			
			 if(importe!=null){
				 params.addParam(Double.parseDouble(importe));
			 }
			 
			 region=region.replace("R0", "");
			 params.addParam(region);
			 
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			
		}
		return lista;
	}
	public List<TransferenciaPojo> getTransferenciasCargadasAutorizadas(Integer idbanco,Date fecha,Date fecha2,Integer estatus,Integer estatusAutorizada, String importe, String region) {
		 
		  
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		 String query;
		 Calendar cal=Calendar.getInstance();	
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=? AND IDESTATUS=? AND FECHA>=? AND FECHA<? 
		 query="S_PTRA_TRANS_AUT1";
		 
		 //SELECT * FROM TRA_TRANSFERENCIA WHERE IDBANCO=? AND IDESTATUS=? AND FECHA>=? AND FECHA<?  AND IMPORTE=? 
		 if(importe!=null){
			 query="S_PTRA_TRANS_AUT2";
		 }
		
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
				
		 params.setNameQuery(query);
		 
	    try {
	    	 
	    	params.addParam(idbanco);	
	    	params.addParam(estatus);
	    	params.addParam(estatusAutorizada);
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
			
			 if(importe!=null){
				 params.addParam(Double.parseDouble(importe));
			 }
			 
			 region=region.replace("R0", "");
			 params.addParam(region);
			 
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			
		}
		return lista;
	}
	
	public List<TransferenciaPojo> getTransferenciasCompensar(Integer idbanco,Date fecha,Date fecha2,Integer estatus,String importe, String region) {
		 
		  
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		 String query;
		 Calendar cal=Calendar.getInstance();	
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		 query="S_PTRA_COMPENSA1";
		 
		 if(importe!=null){
			 query="S_PTRA_COMPENSA2";
		 }
		
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
				
		 params.setNameQuery(query);
		 
	    try {
	    	 
	    	params.addParam(idbanco);	
	    	params.addParam(estatus);	
			 
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
			
			 if(importe!=null){
				 params.addParam(Double.parseDouble(importe));
			 }
			 
			 region=region.replace("R0", "");
			 params.addParam(region);
			 
			 
			 params.addParam(idbanco);	
		    	params.addParam(estatus);	
				 
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
				
				 if(importe!=null){
					 params.addParam(Double.parseDouble(importe));
				 }
				 
				 region=region.replace("R0", "");
				 params.addParam(region);

				 params.addParam(idbanco);	
				 params.addParam(estatus);	
				  
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
				 
				  if(importe!=null){
					  params.addParam(Double.parseDouble(importe));
				  }
				  
				  region=region.replace("R0", "");
				  params.addParam(region);
			 
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaBanco(rowSet.getString(ConstantesTDDCI.REFERENCIABANCO));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					pojo.setIdCompensacion(rowSet.getLong("ID_COMPENSACION"));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					
					lista.add(pojo);
				}
			 
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			
		}
	    Set<TransferenciaPojo> unicos = new HashSet<>();

	    for(TransferenciaPojo pojo : lista) {
	    	if(!unicos.add(pojo)) {
	    		for (Iterator<TransferenciaPojo> it = unicos.iterator(); it.hasNext(); ) {
	    			TransferenciaPojo f = it.next();
	    	        if (f.equals(pojo)) {
	    	            f.getIdsCompensacion().add(pojo.getIdCompensacion());
	    	        	f.setIdsCompensacionString(Arrays.toString(f.getIdsCompensacion().toArray()));
	    	        }
	    	    }
	    	}
	    }
	    
		for (Iterator<TransferenciaPojo> it = unicos.iterator(); it.hasNext();) {
			TransferenciaPojo f = it.next();
			{
				f.getIdsCompensacion().add(f.getIdCompensacion());
				f.setIdsCompensacionString(Arrays.toString(f.getIdsCompensacion().toArray()));
				f.setIdsCompensacionString(f.getIdsCompensacionString().replace("[",""));
				f.setIdsCompensacionString(f.getIdsCompensacionString().replace("]",""));
			}
		}
	    
	    lista.clear();
	    
	    lista.addAll(unicos);
	    
		return lista;
	}

	@Override
	public List<TransferenciaPojo> getTransferenciasByReferenciaEstatus(Integer idEstatus, Date fecha, Date fecha2,
			Integer idEmpleado, String region, Integer esJefe) {
		 List<TransferenciaPojo> lista = null;
		 DecimalFormat formateador;
		
		 Calendar cal=Calendar.getInstance();
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
			
			try {
			 if(fecha!=null){
				 if (esJefe.equals(1)) {
					 params.setNameQuery("S_PTRA_REFCF");
				}else {
					 if(idEmpleado!=null){ 
						 params.setNameQuery("S_PTRA_REFCFEMPL");
						 params.addParam(idEmpleado);
					 }
					
				}
				 params.addParam(idEstatus);	 
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
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 
			  lista = new ArrayList<TransferenciaPojo>();
				while(rowSet.next()) {
					
					TransferenciaPojo pojo= new TransferenciaPojo();			
					pojo.setIdtransferencia(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA));
					pojo.setCuenta(rowSet.getString(ConstantesTDDCI.CUENTABANCO));
					pojo.setCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setImporte(rowSet.getDouble(ConstantesTDDCI.IMPORTE));
					pojo.setProceso(rowSet.getInt(ConstantesTDDCI.IDPROCESO));
					pojo.setReferenciaCliente(rowSet.getString(ConstantesTDDCI.REFERENCIACLIENTE));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));
					pojo.setFecha(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHA)));
					pojo.setIdBanco(rowSet.getInt(ConstantesTDDCI.IDBANCO));
					pojo.setEstatusDescripcion(rowSet.getString(ConstantesTDDCI.DESCRIPCION));
					pojo.setBanco(rowSet.getString(ConstantesTDDCI.NOMBRE));
					pojo.setAlias(rowSet.getString("ALIAS"));
					pojo.setTipoPagos(rowSet.getString(ConstantesTDDCI.TIPOPAGOS));// Se agrega nueva variable para identificar el tipo de trans
					pojo.setFechaTransferencia(formatoFinal.format(rowSet.getDate(ConstantesTDDCI.FECHATRANSFERENCIA)));
					pojo.setNombreUsuario(rowSet.getString("NOMBREUSUARIO"));
					
					if(pojo.getImporte()!=null){
						
						  formateador = new DecimalFormat(ConstantesTDDCI.FORMATODECIMAL);
						  pojo.setImporteString(formateador.format(pojo.getImporte()));
					}
					 if (esJefe.equals(1)) {
							HistorialPojo hPojo = getHistorialByFecha(rowSet.getInt(ConstantesTDDCI.IDTRANSFERENCIA), fecha, fecha2);
							List<AlertasPojo> aPojo = getAlertas();
							for (AlertasPojo alertasPojo : aPojo) {
								if (hPojo.getEstatus().equals(alertasPojo.getIdEstatus())) {
									
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
									java.util.Date date = simpleDateFormat.parse(hPojo.getFecha());
									java.util.Date dateHoy = simpleDateFormat.parse(simpleDateFormat.format(new java.util.Date()));
									DateTime dateTime = new DateTime(date);
									DateTime dateTimeToday = new DateTime(dateHoy);
									int dias = Days.daysBetween(dateTime, dateTimeToday).getDays() ;
									int horas = Hours.hoursBetween(dateTime, dateTimeToday).getHours() % 24;
									int minutos = Minutes.minutesBetween(dateTime, dateTimeToday).getMinutes() % 60;
									
									if (dias==0) {
										if (horas<=1) {
											if (minutos > Integer.valueOf(alertasPojo.getTiempo().replace("Min", "").trim()) ) {
												if (alertasPojo.getColor().equals("Rojo")) {
													pojo.setColor("#fd8b8b");
												}else if (alertasPojo.getColor().equals("Amarillo")) {
													pojo.setColor("#ffff54");
												}else if (alertasPojo.getColor().equals("Verde")) {
													pojo.setColor("#64f064");
												}
											}else {
												pojo.setColor("");
											}							
										}else {
											if (alertasPojo.getColor().equals("Rojo")) {
												pojo.setColor("#fd8b8b");
											}else if (alertasPojo.getColor().equals("Amarillo")) {
												pojo.setColor("#ffff54");
											}else if (alertasPojo.getColor().equals("Verde")) {
												pojo.setColor("#64f064");
											}
										}
									}else {
										if (alertasPojo.getColor().equals("Rojo")) {
											pojo.setColor("#fd8b8b");
										}else if (alertasPojo.getColor().equals("Amarillo")) {
											pojo.setColor("#ffff54");
										}else if (alertasPojo.getColor().equals("Verde")) {
											pojo.setColor("#64f064");
										}
									}

								}
							}
					 }
					lista.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return lista;
	}

	@Override
	public List<PeticionesPojo> getPeticiones(String region) {
		List<PeticionesPojo> lista = null;	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_PETICIONES"); 	
		try {
			params.addParam(region);
	    	WebRowSet rowSet = dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			lista = new ArrayList<PeticionesPojo>();
			while(rowSet.next()) {
					
					PeticionesPojo pojo= new PeticionesPojo();
					pojo.setIdPeticion(rowSet.getInt("IDPETICION"));
					pojo.setNombre(rowSet.getString("NOMBRE"));
					pojo.setIdEmpleado(rowSet.getLong("IDEMPLEADO"));
					pojo.setHorarioIn(rowSet.getString("HORARIO").split(" - ")[0].trim());	
					pojo.setHorarioOut(rowSet.getString("HORARIO").split(" - ")[1].trim());	
					String periodo = rowSet.getString("PERIODO");
					if (periodo.equals("NA")) {
						pojo.setPeriodoIn(rowSet.getString("PERIODO"));
						pojo.setPeriodoOut(rowSet.getString("PERIODO"));
					}else {
						pojo.setPeriodoIn(periodo.split("al")[0].trim());
						pojo.setPeriodoOut(periodo.split("al")[1].trim());
					}			
					pojo.setAsistencia(rowSet.getString("ASISTENCIA"));
					pojo.setCantidadMensajes(rowSet.getString("CANTIDAD_DE_MENSAJES"));
					lista.add(pojo);
			} 
		} catch (Exception e) {
			logger.info( e.getMessage());	
		}
		return lista;
	}

	@Override
	public List<EmpleadoPojo> getUsuariosPeticiones(String region) {
		List<EmpleadoPojo> pojos = new ArrayList<EmpleadoPojo>();

		try {
			
			GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES); // xml/general
			
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_EMPLEADOS_PETICIONES");
			params.addParam(region);
			 
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDUI.APLTIC));			
				
			while(rowSet.next()) {
				EmpleadoPojo pojo = new EmpleadoPojo();
				pojo= new EmpleadoPojo();
				pojo.setIdEmpleado(rowSet.getInt(ConstantesTDUI.IDEMPLEADO));
				pojo.setNombre(rowSet.getString("NOMBRE"));
				pojos.add(pojo);
			}
			 
		} catch (Exception e) { 		
			logger.info(e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		return pojos;
		
	}

	@Override
	public List<AlertasPojo> getAlertas() {
		List<AlertasPojo> lista = null;	
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_ALERTAS"); 	
		try {

	    	WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			lista = new ArrayList<AlertasPojo>();
			while(rowSet.next()) {	
					AlertasPojo pojo= new AlertasPojo();
					pojo.setIdAlerta(rowSet.getInt("IDALERTA"));
					pojo.setColor(rowSet.getString("COLOR"));
					pojo.setTiempo(rowSet.getString("TIEMPO"));
					pojo.setIdEstatus(rowSet.getInt("IDESTATUS"));
					pojo.setDescEstatus(rowSet.getString("DESCRIPCION"));
					lista.add(pojo);
			} 
		} catch (Exception e) {
			logger.info( e.getMessage());	
		}
		return lista;
	}

	@Override
	public HistorialPojo getHistorialByFecha(Integer idTransferencia, Date fecha, Date fecha2) {
		 List<HistorialPojo> pojos = null;
		 
		 Calendar cal=Calendar.getInstance();
		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDCI.FORMATOFECHA);
		
		 GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 ParametrosVo params = new ParametrosVo();
			
			try {
			 if(fecha!=null){
				params.setNameQuery("S_PTRA_HISTORIAL_BY_FECHA");
				params.addParam(idTransferencia);

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
			 WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			 
			  pojos = new ArrayList<HistorialPojo>();
			  
				while(rowSet.next()) {
					HistorialPojo pojo = new HistorialPojo();
					pojo = new HistorialPojo();			
					pojo.setEmpleado(rowSet.getString("IDEMPLEADO"));
					pojo.setEstatus(rowSet.getInt(ConstantesTDDCI.IDESTATUS));
//					String st= rowSet.getString(ConstantesTDDCI.FECHA).trim(); 
					pojo.setFecha(rowSet.getString(ConstantesTDDCI.FECHA).trim());	
					pojos.add(pojo);
				}
			 
		} catch (Exception e) { logger.info( e.getMessage());
			
		}
		return pojos.get(0);
	}

	@Override
	public boolean getPeticionById(Integer idEmpleado) {
		GeneralDAO dao = new GeneralDAO(3); // xml/general
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_COUNT_PETICIONES"); 	
		int numero = 0;
		boolean existe = false;
		try {
			params.addParam(idEmpleado);
	    	WebRowSet rowSet = dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			while(rowSet.next()) {
					numero = rowSet.getInt("NUMERO");
					if (numero > 0 ) {
						existe = true;
					}
			} 
		} catch (Exception e) {
			logger.info( e.getMessage());	
		}
		return existe;
	}

	@Override
	public boolean getAlertaByColor(String color) {
		GeneralDAO dao = new GeneralDAO(3); // xml/general
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_COUNT_ALERTAS"); 	
		int numero = 0;
		boolean existe = false;
		try {
			params.addParam(color);
	    	WebRowSet rowSet = dao.selectSql(params,p.getProperty(ConstantesTDDCI.APLTIC));
			while(rowSet.next()) {
					numero = rowSet.getInt("NUMERO");
					if (numero > 0 ) {
						existe = true;
					}
			} 
		} catch (Exception e) {
			logger.info( e.getMessage());	
		}
		return existe;
	}

	@Override
	public String eliminarAlerta(Integer idAlerta) {
		String resultado="Alerta borrada";
		
		try {
			
			//DELETE  FROM TRA_PAGOS WHERE IDPAGOS=?
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			GeneralDAO dao = new GeneralDAO(3); //xml/general
			
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("D_PTRA_DELETE_ALERTAS");	
			params.addParam(idAlerta);
			 
			dao.executeSql(params, p.getProperty(ConstantesTDDI.APLTIC),null);
				
			} catch (Exception e) { 
				resultado="Error al borrar la alerta";
				logger.info( e.getMessage());		
			}
			return resultado;
	}

	@Override
	public String eliminarPeticion(Integer idPeticion) {
String resultado="Peticion borrada";
		
		try {
			
			//DELETE  FROM TRA_PAGOS WHERE IDPAGOS=?
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			GeneralDAO dao = new GeneralDAO(3); //xml/general
			
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("D_PTRA_DELETE_PETICIONES");	
			params.addParam(idPeticion);
			 
			dao.executeSql(params, p.getProperty(ConstantesTDDI.APLTIC),null);
				
			} catch (Exception e) { 
				resultado="Error al borrar la peticion";
				logger.info( e.getMessage());		
			}
			return resultado;
	}
	
	
	
}
