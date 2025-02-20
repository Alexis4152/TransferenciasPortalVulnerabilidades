package com.telcel.portal.dao.implementacion;


import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.rowset.WebRowSet;

import org.apache.log4j.Logger;

import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.factory.enumeration.TiposDatosEnum;
import com.telcel.portal.factory.vo.ParametrosVo;
import com.telcel.portal.util.FactoryUtils;
import com.telcel.portal.util.PropertiesFiles;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface;

import com.telcel.portal.pojos.BancosPojo;
import com.telcel.portal.pojos.ConsultaReportePojo;

import com.telcel.portal.pojos.EmpleadoPojo;


import com.telcel.portal.pojos.ConsultaPagosPojo;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.RespuestaPagosPojo;
import com.telcel.portal.pojos.TransaccionReportePojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.pojos.TransferenciasDesglosadasPojo;
import com.telcel.portal.util.Constantes;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.ConstantesTDDI;
import com.telcel.portal.util.DivisorCuentas;
import com.telcel.portal.util.ValidadorCuentasPojo;

public class TransferenciasDaoDmpImp  implements TransferenciasDaoInterface{

	private static Logger  logger = Logger.getLogger(GeneralDAO.class);
	

public String setAplicaTransferencias(List <String>lista, Integer idEmpleado) {
	 
	
	String resultado="Transferencia(s) en espera";
	 
	 java.util.Date date;
	 
	 date= new java.util.Date();
	 Date dateSql = new Date(date.getTime());
	 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 
    try {
		
				
		for(String idtrans:lista ){
			
			int anioSys = new GregorianCalendar().get(GregorianCalendar.YEAR);
			Long increment = generaSecuencia(ConstantesTDDI.INCREMENTO);
			//INSERT INTO TRA_PROCESO VALUES(?,?,null,'ESP',?,?,?)
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("I_PTRA_PROCESOTEMP");
			params.addParam(increment);
			params.addParam(formatoFinal.format(dateSql));	
			params.addParam(idEmpleado);
			params.addParam(anioSys);
			params.addParam("PA");
			
			lrequestvo.add(params);				
			
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=5,FECHATRANSFERENCIA=?,IDPROCESO=? WHERE IDTRANSFERENCIA=?
			params  = new ParametrosVo(); 
			params.setNameQuery("U_PTRA_TRANSPROC");
			params.addParam(formatoFinal.format(dateSql));
			params.addParam(increment);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
			//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA) VALUES(historial_seq.nextval,SYSDATE,?,?,?)
			params  = new ParametrosVo(); 
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
			params.addParam(idEmpleado);
			params.addParam(Constantes.APLICADA_PROCESO);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		resultado="Error al aplicar la(s) Transferencia(s)";
		logger.debug(e.getMessage(),e);  
	}
	return resultado;
}

public String setAplicaTransferenciasCompensacion(Hashtable <String,String> lista,Integer idEmpleado, String tipo) {
	 
	
	String resultado="Transferencia(s) en espera de compensacion";
	java.util.Date date;
	date= new java.util.Date();
	Date dateSql = new Date(date.getTime());
	SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 
    try {
    	
    	for(Map.Entry<String, String> entry : lista.entrySet()) {
    		int anioSys = new GregorianCalendar().get(GregorianCalendar.YEAR);
			Long increment = generaSecuencia(ConstantesTDDI.INCREMENTO);
			
			//INSERT INTO TRA_PROCESO VALUES(?,?,null,'ESP',?,?,?)
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("I_PTRA_PROCESOTEMP");
			params.addParam(increment);
			params.addParam(formatoFinal.format(dateSql));	
			params.addParam(idEmpleado);
			params.addParam(anioSys);
			params.addParam(tipo);
			
			lrequestvo.add(params);				
			
			
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=7,FECHATRANSFERENCIA=?,IDPROCESO=? WHERE IDTRANSFERENCIA=?
			params  = new ParametrosVo(); 
			params.setNameQuery("U_PTRA_TRANSPROCCOMPENSA");
			params.addParam(formatoFinal.format(dateSql));
			params.addParam(increment);
			params.addParam(Integer.parseInt(entry.getKey()));
			
			lrequestvo.add(params);
			
			//UPDATE TRA_COMPENSACIONES SET ID_TRANSFERENCIA = ? WHERE ID_COMPENSACION IN (?)
			params  = new ParametrosVo(); 
			params.setNameQuery("U_PTRA_COMPENSATRANS");
			params.addParam(entry.getKey());
			params.addParam(increment);
			params.addParam(entry.getValue());
			
			lrequestvo.add(params);
			
			//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA) VALUES(historial_seq.nextval,SYSDATE,?,?,?)
			params  = new ParametrosVo(); 
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
			params.addParam(idEmpleado);
			params.addParam(Constantes.COMPENSACION_PROCESO);
			params.addParam(Integer.parseInt(entry.getKey()));
			
			lrequestvo.add(params);
				
    	}
				
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		
		resultado="Error al aplicar la(s) Transferencia(s)";
		logger.debug(e.getMessage(),e);
	}
	return resultado;
}

public String setRegresaTransferenciasCompensacion(List<String> lista,Integer idEmpleado) {
	 
	
	String resultado="Transferencia(s) en espera de compensacion";
	java.util.Date date;
	date= new java.util.Date();
	Date dateSql = new Date(date.getTime());
	SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 
    try {
    	
    	for(String entry : lista) {
			
			//INSERT INTO TRA_PROCESO VALUES(?,?,null,'ESP',?,?,?)
			ParametrosVo params = new ParametrosVo();
			
			
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=7,FECHATRANSFERENCIA=?,IDPROCESO=? WHERE IDTRANSFERENCIA=?
			params.setNameQuery("U_PTRA_TRANSPROCCOMPENSATRA");
			params.addParam(entry);
			
			lrequestvo.add(params);
			
				
    	}
				
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		
		resultado="Error al aplicar la(s) Transferencia(s)";
		logger.debug(e.getMessage(),e);
	}
	return resultado;
}

public String setAplicaTransferenciasFA(List <String> lista,Integer idEmpleado) {
	 
	
	String resultado="Transferencia(s) en espera";
	java.util.Date date;
	date= new java.util.Date();
	Date dateSql = new Date(date.getTime());
	SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 
    try {
				
		for(String idtrans:lista ){
			
			int anioSys = new GregorianCalendar().get(GregorianCalendar.YEAR);
			Long increment = generaSecuencia(ConstantesTDDI.INCREMENTO);
			
			//INSERT INTO TRA_PROCESO VALUES(?,?,SYSDATE,'OK',?,?,?)
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("I_PTRA_PROCESOFA");
			params.addParam(increment);
			params.addParam(formatoFinal.format(dateSql));	
			params.addParam(idEmpleado);
			params.addParam(anioSys);
			params.addParam("PA");
			
			lrequestvo.add(params);				
			
			
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=7,FECHATRANSFERENCIA=?,IDPROCESO=? WHERE IDTRANSFERENCIA=?
			params  = new ParametrosVo(); 
			params.setNameQuery("U_PTRA_TRANSPROCFA");
			params.addParam(formatoFinal.format(dateSql));
			params.addParam(increment);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
			//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA) VALUES(historial_seq.nextval,SYSDATE,?,?,?)
			params  = new ParametrosVo(); 
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
			params.addParam(idEmpleado);
			params.addParam(Constantes.APLICADA);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
			//UPDATE TRA_PAGOS SET ESTATUSPAGO ='CL', FECHALOTE = SYSDATE, LOTE = 0 WHERE IDTRANSFERENCIA = ? AND TIPO='FA'
			params  = new ParametrosVo(); 
			params.setNameQuery("U_SIBAT_PAGTRFA");
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		
		resultado="Error al aplicar la(s) Transferencia(s)";
		logger.debug(e.getMessage(),e);
	}
	return resultado;
}

public String setAutorizaTransferencias(String [] lista,Integer idEmpleado) {
	 
	String resultado="Transferencia(s) Autorizada(s) con Exito";

	 java.util.Date date;
	 
	 date= new java.util.Date();
	 Date dateSql = new Date(date.getTime());
	 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	 
	 PropertiesFiles prop = new PropertiesFiles();
	 Properties p = prop.getPropertiesConf();
	 GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	 ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 
    try {
		
		for(String idtrans:lista ){
		
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=2,FECHATRANSFERENCIA=? WHERE IDTRANSFERENCIA=?
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("U_PTRA_TRANSAUTO");
			
			params.addParam(formatoFinal.format(dateSql));
			params.addParam(Integer.parseInt(idtrans));
				
			lrequestvo.add(params);
			
			params  = new ParametrosVo(); 
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
			params.addParam(idEmpleado);
			params.addParam(Constantes.AUTORIZADA);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);   
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) { 
		
		resultado="Error al Autorizar Transferencia(s)";
		logger.info( e.getMessage());
		
	}
	return resultado;
}

public String setRechazaTransferencias(String [] lista, String comentario,Integer idEmpleado) {
	 
	
	String resultado="Transferencia rechazada correctamente";
	 

	 java.util.Date date;	 
	 date= new java.util.Date();	
	 Date dateSql = new Date(date.getTime());
	 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	 
	 PropertiesFiles prop = new PropertiesFiles();
	 Properties p = prop.getPropertiesConf();
	 GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	 ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		
    try {
		
		for(String idtrans:lista ){
			
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=6,COMENTARIO=?,FECHATRANSFERENCIA=? WHERE IDTRANSFERENCIA=?			
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("U_PTRA_TRANSRECHAZA");
			params.addParam(comentario);
			params.addParam(formatoFinal.format(dateSql));
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
			
			params  = new ParametrosVo(); 
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIALCOME); 
			params.addParam(idEmpleado);
			params.addParam(Constantes.RECHAZADA);
			params.addParam(Integer.parseInt(idtrans));
			params.addParam(comentario);
			
			lrequestvo.add(params);
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		resultado="Error al rechazar la Transferencia";
	}
	return resultado;
}
//OK PROBADA


public String setCambioEstatusTransferencias(String [] lista, String comentario,Integer idEmpleado) {
	 
	
	String resultado="Transferencia cambiada de estatus correctamente";
	 

	 PropertiesFiles prop = new PropertiesFiles();
	 Properties p = prop.getPropertiesConf();
	 GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	 ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		
    try {
		
		for(String idtrans:lista ){
			
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=2,COMENTARIO=? WHERE IDTRANSFERENCIA=?			
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("U_PTRA_TRANSCAMBIOEST");
			params.addParam(comentario);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
			
			params  = new ParametrosVo(); 
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIALCOME); 
			params.addParam(idEmpleado);
			params.addParam(Constantes.AUTORIZADA);
			params.addParam(Integer.parseInt(idtrans));
			params.addParam(comentario);
			
			lrequestvo.add(params);
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		resultado="Error al cambiar el estatus la Transferencia";
	}
	return resultado;
}


public String actualizaEmpleados(String [] empleado,Integer idPerfil,String usuario) {
	
	
	String resultado="";	
	java.util.Date date = new java.util.Date();	
	Date dateSql = new Date(date.getTime());
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	
	    try {
			
			//U_PTRA_EMPAUDITA
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES); //xml/general
	    	
			for(String idEmpleado:empleado ){
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("U_PTRA_EMPAUDITA");	
				params.addParam(usuario);
				params.addParam(dateSql.toString());
				params.addParam(Integer.parseInt(idEmpleado));
				 	  					
				lrequestvo.add(params);
				 
				params = new ParametrosVo();
				//UPDATE TRA_EMPLEADO SET IDPERFIL=? WHERE IDEMPLEADO=?
				
				if(idPerfil!=null){
					params.setNameQuery("U_PTRA_EMPLEADO");
					params.addParam(idPerfil);
					params.addParam(Integer.parseInt(idEmpleado));
					
				}else{
					
					params.setNameQuery("U_PTRA_EMPLEADORES");
					params.addParam(Integer.parseInt(idEmpleado));
					
				}
				
				lrequestvo.add(params);
			
			}
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));

			resultado="Usuarios actualizados exitosamente";
			
		} catch (Exception e) { 
			resultado="Error al actualizar los usuarios";
			logger.info( e.getMessage());
		}
		return resultado;
}
// OK PROBADA
public String borrarEmpleados(String[] empleados,String usuario) {
	 
	
	    String resultado="";

		java.util.Date date = new java.util.Date();	
		Date dateSql = new Date(date.getTime());

		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		
	    try {
	    	PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES); //xml/general
			
			for(String idEmpleado:empleados ){
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("U_PTRA_EMPAUDITA");	
				params.addParam(usuario);
				params.addParam(dateSql.toString());
				params.addParam(Integer.parseInt(idEmpleado));
				
				lrequestvo.add(params);
				 
				params = new ParametrosVo();
				//DELETE  FROM TRA_EMPLEADO WHERE IDEMPLEADO=?
				params.setNameQuery("U_PTRA_EMPLEADOIN");
				params.addParam(Integer.parseInt(idEmpleado));
			
				lrequestvo.add(params);	
			}
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));

			resultado="Usuarios borrados exitosamente";
			
		} catch (Exception e) { 
			resultado="Error al borrar los usuarios";
			logger.info( e.getMessage());
			
		}
		return resultado;
}
//PENDIENTE, PROBAR EN QA N
public String agregarUsuario(EmpleadoPojo empleado,Integer perfil, String audita, String region) {
	
	String resultado="";
	 region=region.replace("R0", "");
	try {
		//INSERT INTO TRA_EMPLEADO VALUES(empleado_seq.NEXTVAL,?,?,1,null,null,?)
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES); //xml/general
		
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("I_PTRA_EMPLEADO");
	    params.addParam(empleado.getNombre());	
	    params.addParam(empleado.getPuesto());
	    params.addParam(audita);
	    params.addParam(perfil);
	    params.addParam(empleado.getUsuario());
	    params.addParam(Integer.valueOf(region));
	    dao.executeSql(params, p.getProperty(ConstantesTDDI.APLTIC), null);
	    
		resultado="Usuario agregado exitosamente";
		
	} catch (Exception e) { logger.info( e.getMessage());
		
	 resultado="Error al agregar empleado";
	}
	

	
	return resultado;
}
//OK PROBADA 
public List<Map<String, TransaccionReportePojo>> getReporte(String year, Integer mes, String region) {
	
	List<Map<String, String>> transaccionesMes;
	List<Map<String, TransaccionReportePojo>> listaFinalPojo=null;
	List<ConsultaReportePojo> lista = null;
	List<BancosPojo> listaBancos = null;
	HashMap<String, String> hash;
	HashMap<String, String> totalBancos;
	String llave;
	Calendar calendar;
	Calendar parametros;
	String fecha;
	String fechaInicio;
	String fechaFin;
	String idBanco;
	Double totalDia;
	Integer transaccionesAplicadads;
	HashMap<String,ConsultaReportePojo> bancoFecha;
	int mesActual;
	
	try {
		
		 calendar = Calendar.getInstance();
		 parametros=Calendar.getInstance();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy");
		calendar.set(Integer.parseInt(year),mes, 1);
		parametros.set(Integer.parseInt(year), mes,1);
		
		fechaInicio=formatoFinal.format(parametros.getTime());
		logger.info("fechaInicio:"+fechaInicio);
		parametros.add(Calendar.MONTH, 1);
		
		fechaFin=formatoFinal.format(parametros.getTime());
		logger.info("fechaFin:"+fechaFin);
		logger.info("Region: "+region);
		region=region.replace("R0", "");
		lista = getListaBancos1(fechaInicio, fechaFin, region);
		
		listaBancos = getListaBancos2();
		
	    bancoFecha = new HashMap<String,ConsultaReportePojo>();
		for(ConsultaReportePojo consulta:lista){
			
			llave=consulta.getFecha()+"_"+consulta.getBanco();
			bancoFecha.put(llave, consulta);
		}
		
		mesActual=calendar.get(Calendar.MONTH);
		
		transaccionesMes= new ArrayList<Map<String, String>>();
		totalBancos= new HashMap<String,String>();
		
		for(BancosPojo banco:listaBancos){				
			totalBancos.put(banco.getIdBank(),"0 | 0");
		}
		
		 DecimalFormat formateador = new DecimalFormat("#,#00.00#;(-#,#00.00#)");
		 
		 
		 int transaccionesTotalDia=0;
		 double importeTotalDia=0;
		while(true){ 
			
		     fecha=formato.format(calendar.getTime());
		     
		     hash= new HashMap<String, String>();
		     hash.put(ConstantesTDDI.FECHA, formatoFinal.format(calendar.getTime()));

		     for(BancosPojo banco:listaBancos)	{
					
				hash.put(banco.getIdBank(),"0  |  $0");	
					
			}		     
		   
			totalDia=0d;
			transaccionesAplicadads=0;
			for(BancosPojo banco:listaBancos){
				
				 idBanco=banco.getIdBank();
				 ConsultaReportePojo transaccionBanco = bancoFecha.get(fecha+"_"+idBanco);
				 
				 if(transaccionBanco!=null){
					 
					 hash.put(idBanco, transaccionBanco.getTransacciones()+ConstantesTDDI.PESOS+formateador.format(Double.parseDouble(transaccionBanco.getTotal())));
					 
					 totalDia=totalDia+Double.parseDouble(transaccionBanco.getTotal());
					 transaccionesAplicadads=transaccionesAplicadads+Integer.parseInt(transaccionBanco.getTransacciones());
					 
					  String parsing=totalBancos.get(idBanco);
					  parsing=parsing.trim();
					  parsing=parsing.replace('|', '_');
					  parsing=parsing.replace('$', 'x');
					  parsing=parsing.replaceAll("x", "");
					  parsing=parsing.replaceAll(" ", "");
					 
					  String[] totalPorBanco = parsing.split("_");
					 
					  if(totalPorBanco!=null && totalPorBanco.length>=1){
						  
						  totalPorBanco[1]=totalPorBanco[1].replaceAll(",", "");
						  totalPorBanco[1]=totalPorBanco[1].replaceAll("$", "");
						  
						  totalBancos.put(idBanco, ((Integer)(Integer.parseInt(totalPorBanco[0])+Integer.parseInt(transaccionBanco.getTransacciones()))).toString()+ConstantesTDDI.PESOS+formateador.format(Double.parseDouble(totalPorBanco[1])+Double.parseDouble(transaccionBanco.getTotal())));
						  
					  }
				 }
				
			}
			
			 transaccionesTotalDia=transaccionesTotalDia+transaccionesAplicadads;
			 importeTotalDia=importeTotalDia+totalDia;
			hash.put("total", transaccionesAplicadads.toString()+ConstantesTDDI.PESOS+formateador.format(totalDia));
			transaccionesMes.add(hash);
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			if(mesActual!=calendar.get(Calendar.MONTH)){
				break;
			}
		}
		
		
		totalBancos.put(ConstantesTDDI.FECHA, "total");
		totalBancos.put("total", transaccionesTotalDia+ConstantesTDDI.PESOS+formateador.format(importeTotalDia));
		transaccionesMes.add(totalBancos);
		
		listaFinalPojo = new ArrayList<Map<String, TransaccionReportePojo>> ();
		listaFinalPojo = getListaFinal(transaccionesMes);
		
	} catch (Exception e) { 
		
		logger.info( e.getMessage());
		logger.debug(e.getMessage(),e);
	}// TODO Apendice de metodo generado autometicamente
	return listaFinalPojo;
}

private List<ConsultaReportePojo> getListaBancos1(String fechaInicio, String fechaFin, String region){
	
	List<ConsultaReportePojo> lista1 = null;
	try{
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_REPORTE1"); 
		params.addParam(fechaInicio);
		params.addParam(fechaFin);
		params.addParam(region);
		
		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
		
		lista1= new ArrayList<ConsultaReportePojo>();
		
		while(rowSet.next()) {
			
			ConsultaReportePojo pojo= new ConsultaReportePojo();			
			pojo.setBanco("BANCO"+rowSet.getString("BANCO"));
			pojo.setFecha(rowSet.getString("FECHA"));
			pojo.setTotal(rowSet.getString("SUMA"));
			pojo.setTransacciones(rowSet.getString("TOTAL"));
			lista1.add(pojo);
			
			
		}
	} catch (Exception e) { 	
		logger.info( e.getMessage());
		logger.debug(e.getMessage(),e);
	}
	
	return lista1;
}

private List<BancosPojo> getListaBancos2(){
	
	List<BancosPojo> listaBancos = null;
	try{
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_BANCOS");
	
		
		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
		
		listaBancos= new ArrayList<BancosPojo>();
		
		while(rowSet.next()) {
			
			BancosPojo pojo= new BancosPojo();		
			pojo.setIdBank("BANCO"+rowSet.getString("IDBANCO"));
			pojo.setAlias(rowSet.getString("NOMBRECORTO"));
			pojo.setNombre(rowSet.getString("NOMBRE"));

			listaBancos.add(pojo);
			
		}
	} catch (Exception e) { 	
		logger.info( e.getMessage());
		logger.debug(e.getMessage(),e);
	}
	
	return listaBancos;
}

private List<Map<String, TransaccionReportePojo>> getListaFinal(List<Map<String, String>> transaccionesMes){
	
	List<Map<String, TransaccionReportePojo>>  listaFinalPojo = new ArrayList<Map<String, TransaccionReportePojo>> ();
	TransaccionReportePojo transaccionReportePojo;
	HashMap<String, TransaccionReportePojo> hashPojo;
	
	try{
		for(Map<String, String> fila:transaccionesMes){
			
			    Set entries = fila.entrySet();
			    Iterator it = entries.iterator();
			    hashPojo = new HashMap<String, TransaccionReportePojo>();
			    while (it.hasNext()) {
			      Map.Entry entry = (Map.Entry) it.next();
			      String value=entry.getValue().toString().trim();
			      transaccionReportePojo= new TransaccionReportePojo();
			      
			      if(!entry.getKey().toString().equals(ConstantesTDDI.FECHA)){
			    	  value=value.replace('|', '_');
					  value=value.replaceAll(" ", "");
					  String[] transMonto = value.split("_");
					  transaccionReportePojo.setTransaccion(transMonto[0]);
					  transaccionReportePojo.setMonto(transMonto[1]);
					  hashPojo.put(entry.getKey().toString(), transaccionReportePojo);
			      }else if(entry.getKey().toString().equals(ConstantesTDDI.FECHA)){
			    	  transaccionReportePojo.setFecha(value);
			    	  hashPojo.put(entry.getKey().toString(), transaccionReportePojo);
			      }
			    }
		     
			    listaFinalPojo.add(hashPojo);
		}
	}catch(Exception e){
		logger.info(e.getMessage());
		listaFinalPojo=null;
	}
	
	return listaFinalPojo;
}

//OK PROBADA 
public List<ConsultaReportePojo> getReporteDetalle(String year, Integer mes,String region) {
	
	logger.info("anio="+year);
	logger.info("mes="+mes);
	List<ConsultaReportePojo> lista = null;
	Calendar calendar;
	Calendar parametros;
	String fechaInicio;
	String fechaFin;
	
	try {
		
		calendar = Calendar.getInstance();
		parametros=Calendar.getInstance();
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy");
		calendar.set(Integer.parseInt(year),mes, 1);
		parametros.set(Integer.parseInt(year), mes,1);
		DecimalFormat formateador = new DecimalFormat("#,#00.00#;(-#,#00.00#)");
		
		fechaInicio=formatoFinal.format(parametros.getTime());
		logger.info("fechaInicio:"+fechaInicio);
		parametros.add(Calendar.MONTH, 1);
		
		fechaFin=formatoFinal.format(parametros.getTime());
		logger.info("fechaFin:"+fechaFin);
		 
		/*SELECT E.USUARIO AS USUARIO, H.FECHA AS FECHA_DESGLOCE, P.REGION AS REGION,P.CUENTA AS CUENTA,  P.IMPORTE AS IMPORTE, T.IMPORTE AS IMPORTE_TRANS, T.ALIAS AS EMPRESA
			FROM TRA_TRANSFERENCIA T, TRA_PAGOS P, TRA_HISTORIAL H, TRA_EMPLEADO E WHERE 
			T.IDTRANSFERENCIA = P.IDTRANSFERENCIA AND T.IDTRANSFERENCIA = H.IDTRANSFERENCIA AND T.IDESTATUS = 7 AND 
			T.FECHATRANSFERENCIA >= TO_DATE ('?', 'DD-MM-YYYY')  AND T.FECHATRANSFERENCIA < TO_DATE ('?', 'DD-MM-YYYY') AND 
			H.IDESTATUS=3 AND H.IDEMPLEADO = E.IDEMPLEADO AND E.ESTATUSUSR >0 ORDER BY FECHA_DESGLOCE*/
		
		GeneralDAO dao = new GeneralDAO(2); // xml/reportes		 
	
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
			
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_TRANSDETALLE");
		params.addParam(fechaInicio);
		params.addParam(fechaFin);
		region=region.replace("R0", "");
		params.addParam(region);
		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
		lista= new ArrayList<ConsultaReportePojo>();
		
		while(rowSet.next()) {
			
			ConsultaReportePojo pojo= new ConsultaReportePojo();
			
			pojo.setUsuario((rowSet.getString("USUARIO") != null) ? rowSet.getString("USUARIO").trim() : "");
			pojo.setFecha((rowSet.getString("FECHA_DESGLOCE") != null) ? rowSet.getString("FECHA_DESGLOCE").trim() : "");
			pojo.setRegion((rowSet.getString("REGION") != null) ? rowSet.getString("REGION") : "");
			pojo.setCuenta((rowSet.getString("CUENTA") != null) ? rowSet.getString("CUENTA").trim() : ""); 
			pojo.setImporte(formateador.format(Double.parseDouble(rowSet.getString("IMPORTE").trim())));
			pojo.setTotal(formateador.format(Double.parseDouble(rowSet.getString("IMPORTE_TRANS").trim())));
			pojo.setEmpresa((rowSet.getString("EMPRESA") != null) ? rowSet.getString("EMPRESA").trim() : "");
			
			lista.add(pojo);
		}	
				
	} catch (Exception e) { 
		
		logger.info( e.getMessage());
		logger.debug(e.getMessage(),e);
	}// TODO Apendice de metodo generado automaticamente
	return lista;
}
//OK PROBADA N+
public Long generaSecuencia(String secuencia) {
	
	Long sec = null;
	GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES); //xml/desglose
	try {
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("Q_ORA_SEQ_NEXTVAL");
		params.addParam(secuencia);

		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
		if (rowSet != null && rowSet.next()) {
			sec = rowSet.getLong(1);
		} else {
			sec = null;
		}
	} catch (Exception e) {
		sec = null;
		logger.debug(e.getMessage(),e);
	}

	return sec;
}
//OK PROBADA N+
public String insertaTransferencias(List<TransferenciaPojo> transferencias,Integer idEstatus, String region) {
	 
		java.util.Date date;
		 
		date= new java.util.Date();
		Date sqlDate = new Date(date.getTime());
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
		 
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(1); //xml/desglose
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		region=region.replace("R0", "");
		
	    try {
	    	
	    	//INSERT INTO TRA_TRANSFERENCIA(IDTRANSFERENCIA,FECHA,IMPORTE,CUENTABANCO,REFERENCIABANCO,REFERENCIACLIENTE,IDBANCO,IDESTATUS,FECHATRANSFERENCIA) VALUES(?,?,?,?,?,?,?,?,?)
			String queryTrans="I_PTRA_TRANS";
			
			//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA) VALUES(historial_seq.nextval,SYSDATE,?,?,?)
			String  queryHist = ConstantesTDDI.IPTRAHISTORIAL;
			
			
			for(TransferenciaPojo pojoTrans:transferencias ){
											
				Long increment = generaSecuencia("transferencia_seq");
				
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery(queryTrans);
				params.addParam(increment);
				logger.info("FECHA TRANS:"+pojoTrans.getDateFecha());
				params.addParam(formatoFinal.format(pojoTrans.getDateFecha()));
				params.addParam(pojoTrans.getImporte());
				params.addParam(pojoTrans.getCuenta());
				params.addParam(pojoTrans.getReferenciaBanco());
				params.addParam(pojoTrans.getReferenciaCliente());
				params.addParam(pojoTrans.getIdBanco());
				params.addParam(idEstatus);
				params.addParam(formatoFinal.format(sqlDate));
				params.addParam(region);
				lrequestvo.add(params);

				params  = new ParametrosVo(); 
				params.setNameQuery(queryHist);
				params.addParam(pojoTrans.getIdEmpleado());
				params.addParam(Constantes.CARGADA);
				params.addParam(increment);
				lrequestvo.add(params);
				
				if(idEstatus.intValue() == Constantes.AUTORIZADA){
					params  = new ParametrosVo(); 
					params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
					params.addParam(pojoTrans.getIdEmpleado());
					params.addParam(Constantes.AUTORIZADA);
					params.addParam(increment);
					lrequestvo.add(params);
				}
				
			}
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			
		} catch (Exception e) { 
			logger.info( e.getMessage());
			return "Error al Insertar Transferencias";
		}
		return "Transacciones Cargadas correctamente";
}
//OK PROBADA N+
public String setTomaTransferencias(String[] lista,Integer idEmpleado,String alias) {
	 
	
	String resultado="Transferencia Tomadas con Exito";
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	
    try {
		
	    //UPDATE TRA_TRANSFERENCIA SET IDEMPLEADO=?,ALIAS=? WHERE IDTRANSFERENCIA=?
		for(String idtrans:lista ){
				
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("U_PTRA_TRANSEMPLEADO");
			params.addParam(idEmpleado);
			params.addParam(alias);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
			params  = new ParametrosVo();
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
			params.addParam(idEmpleado);
			params.addParam(Constantes.TOMADA);
			params.addParam(Integer.parseInt(idtrans));
			
			 lrequestvo.add(params);
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) { 
		logger.info( e.getMessage());
		resultado="Error al Tomar las Transferencias";
		logger.debug(e.getMessage(),e);
	}
	return resultado;
}
//OK PROBADO
public RespuestaPagosPojo setInsertaPagos(List <PagoPojo> pagoPojoList,EmpleadoPojo usuarioSession) {
	 
	HashMap<String,String> cuentasCambios = new HashMap<String,String>();
	String cuenta="No disponible";
	Map hash;
	RespuestaPagosPojo result= new RespuestaPagosPojo();
		
	try {
   	 DivisorCuentas divisorCuentas= new DivisorCuentas();
   		
   	 List<ValidadorCuentasPojo> listaDeCuentas = divisorCuentas.divideCuentas(pagoPojoList);
   	 if(listaDeCuentas!=null){
   		 for(ValidadorCuentasPojo pojoValidador:listaDeCuentas){
   		  		 
   			 try {
   				logger.info("--- REGION: " + pojoValidador.getRegion());
		    			
   				 for(String cuentasP:pojoValidador.getCuentasParaValidar()){
		    				
   					logger.info("--- cUENTAS A VALIDAR: " +cuentasP);	
		    				
   				 }
		    	
   				hash = consultaCuentas(pojoValidador);
   					
   				 for(String cuentaArreglo:pojoValidador.getCuentasParaValidar()){
							
   					if(!hash.containsKey(cuentaArreglo)){
								
   						result.setRespuesta(ConstantesTDDI.CUENTA+cuentaArreglo+"  no existe, no corresponde a la region, no es de tipo corporativa.");
						return result;
								
					}else{
					     	int tipoPerfil = usuarioSession.getIdPefril();
					     	logger.info("PERFIL INSERTA:"+tipoPerfil);
							if((tipoPerfil != ConstantesNumeros.CINCO) && !(usuarioSession.getPuesto().toLowerCase(Locale.getDefault()).trim().startsWith("gerente") || usuarioSession.getPuesto().toLowerCase(Locale.getDefault()).trim().startsWith("subdirector"))){//VALIDACION PARA ETIQUETADO DE CTAS, SI ES USUARIO DE COBRANZA NO VALIDA LAS CTAS
												
									String userTemp = (String) hash.get(String.valueOf(cuentaArreglo));
									logger.info("------- ********|" + userTemp + "|" + usuarioSession.getUsuario().trim() + "|" + usuarioSession.getUsuarioJefeDirecto().trim());
									if(!userTemp.trim().equals(usuarioSession.getUsuario().trim()) && !userTemp.trim().equals(usuarioSession.getUsuarioJefeDirecto().trim()) ){
										
										result.setRespuesta(ConstantesTDDI.CUENTA+cuentaArreglo+" no se encuentran asignadas a tu usuario o al de tu jefe. ");
										return result;
									}
							}
								
						}	
				} 					
   					
   			 	} catch (Exception e) {
   			 			logger.info("*******------------validaUsuario:"+e.getMessage());
						logger.debug(e.getMessage(),e);
						result.setRespuesta("Error Insperado en la validacion de las cuentas con el usuario");
						return result;
				}
   		 
		    		
					try {
   					
   					
   					FactoryUtils util = new FactoryUtils();
   	   				ParametrosVo params = new ParametrosVo();
   	   				StringBuffer inCuentasAux = util.getParamIn(pojoValidador.getCuentasParaValidarString(), TiposDatosEnum.TYPE_NUMERIC.getType());
   	   				params.setNameQuery("S_TRAN_RESPAGO_IN");
   	   				params.setRegion(pojoValidador.getRegion());
   	   				params.addParam(pojoValidador.getRegion());
   	   				params.addParam(inCuentasAux.toString());
   	   				PropertiesFiles prop = new PropertiesFiles();
   	   				Properties p = prop.getPropertiesConf();
   	   				
   	   				GeneralDAO dao = new GeneralDAO(1);
   	   				WebRowSet rowSet	= dao.selectSql(params, p.getProperty(ConstantesTDDI.APLMOBILE1));
   					
   					
   					
   					hash= new HashMap();
   					ConsultaPagosPojo pojoConsulta;
   					while(rowSet.next()) {
   						
   						pojoConsulta=new ConsultaPagosPojo();
   						
   						
   						pojoConsulta.setCuenta(rowSet.getString("ACCT_NUM"));
   						pojoConsulta.setCuentaCambio(rowSet.getString("REGN_ACCT_NUM"));
   						pojoConsulta.setRespuesta(rowSet.getString("BILL_RESP_IND"));
   						hash.put(rowSet.getString("ACCT_NUM"),pojoConsulta);
   						logger.info("*******------------pojoConsulta	:"+pojoConsulta.getCuentaCambio());
   						logger.info("*******------------pojoConsulta	:"+pojoConsulta.getCuenta());
   						logger.info("*******------------pojoConsulta	:"+pojoConsulta.getRespuesta());
   						 if(pojoConsulta.getCuentaCambio()!=null){
   							 if (!pojoConsulta.getCuenta().trim().equals(pojoConsulta.getCuentaCambio())) {
   								 cuentasCambios.put(pojoConsulta.getCuenta(), pojoConsulta.getCuentaCambio());
   							 }	    									    									
   						}else{ 								
   								if(!(pojoConsulta.getRespuesta()!=null && pojoConsulta.getRespuesta().equals("S"))){
   								
   								 result.setRespuesta("La cuenta "+pojoConsulta.getCuenta()+" no tiene responsabilidad de pago");
   							     return result;	
   							     
   								}
   							}
   					}
						
					} catch (Exception e) {
						logger.info("*******------------setInsertaPagos:"+e.getMessage());
						logger.debug(e.getMessage(),e);
						
						result.setRespuesta("2)Error al insertar los pagos en la cuenta:"+cuenta);
						return result;
					}
					
					for(String cuentaArreglo:pojoValidador.getCuentasParaValidar()){
						
						if(!hash.containsKey(cuentaArreglo)){
							
							result.setRespuesta("Error, la cuenta:"+cuentaArreglo+" no existe o no pertenece a la region");
							return result;
							
						}
						
					}
   	 }
	}else{
			result.setRespuesta("Error Insperado en la validacion de las cuentas por bloque");
			return result;
	 }
   	 	
   	 	insertaPagos(pagoPojoList,cuenta,cuentasCambios);

	} catch (Exception e) {
	
		logger.info("*******------------setInsertaPagos:"+e.getMessage());
		logger.debug(e.getMessage(),e);
		result.setRespuesta("Error al insertar los pagos en la cuenta:"+cuenta);
		return result;
	}
	
	if(cuentasCambios.isEmpty()){
		cuentasCambios=null;
	}
	
	result.setCuetasCambiadas(cuentasCambios);
	result.setRespuesta("Pagos insertados con exito");
	return result;
}

private Map consultaCuentas(ValidadorCuentasPojo pojoValidador) throws Exception{
		Map hash = new HashMap();
		FactoryUtils util = new FactoryUtils();
		ParametrosVo params = new ParametrosVo();
		StringBuffer inCuentasAux = util.getParamIn(pojoValidador.getCuentasParaValidarString(), TiposDatosEnum.TYPE_NUMERIC.getType());
		params.setNameQuery("S_TIC_DES_VALUSER"); 
		params.setRegion(pojoValidador.getRegion());
		params.addParam(pojoValidador.getRegion());
		params.addParam(inCuentasAux.toString());
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		
		GeneralDAO dao = new GeneralDAO(1);//MODULO DE CAPTURA
		WebRowSet rowSet	= dao.selectSql(params, p.getProperty(ConstantesTDDI.APLMOBILE1));
		 //////////////////////////////////////
		 while(rowSet.next()) {
				
			 String acount = rowSet.getString("ACCT_NUM");
			 String user = rowSet.getString("SERV_CSR_ID");
				 
			 hash.put(acount, user.trim());
			logger.info("///////////*******------------acount:"+acount);
			logger.info("///////////*******------------user:"+user);
				 
		 }
		 
		 return hash;
}

private void insertaPagos(List <PagoPojo> pagoPojoList, String cuenta, HashMap<String,String> cuentasCambios) throws Exception{
	//INSERT INTO TRA_PAGOS (IDPAGOS,TIPO,CUENTA,IMPORTE,IDEMPLEADO,IDTRANSFERENCIA,REGION) values (pagos_seq.nextval,?,?,?,?,?,?)
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(1); //xml/desgloce
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		
		for(PagoPojo pagoPojo:pagoPojoList ){
			
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("I_PTRA_PAGOS");
			params.addParam(pagoPojo.getTipo());
			
			cuenta=pagoPojo.getCuenta();
			
			if(!cuentasCambios.containsKey(pagoPojo.getCuenta())){
				
				params.addParam(pagoPojo.getCuenta());
				params.addParam(pagoPojo.getImporte());
				params.addParam(pagoPojo.getIdEmpleado());
				params.addParam(pagoPojo.getIdTrans());
				params.addParam(pagoPojo.getRegion());
			}else {//Cambia la cta padre por la cta nac y la region la cambia a R09 porque todas las ctas nac son de esta region.
				params.addParam(cuentasCambios.get(pagoPojo.getCuenta()));
				params.addParam(pagoPojo.getImporte());
				params.addParam(pagoPojo.getIdEmpleado());
				params.addParam(pagoPojo.getIdTrans());
				params.addParam("R09");
			}
			params.addParam(pagoPojo.getSap());
			lrequestvo.add(params);
		
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
}



//OK PROBADA N+
public String regresarTransferenciaBatch(Integer idTransferencia,Integer idEmpleado,String comentario,Integer batch) {
	  
	String resultado="Transferencia regresada";
	 
	 java.util.Date date;
	 date= new java.util.Date();
	 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	 ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 PropertiesFiles prop = new PropertiesFiles();
	 Properties p = prop.getPropertiesConf();
	 GeneralDAO dao = new GeneralDAO(1); //xml/desglose

		try {
			
			
			int anioSys = new GregorianCalendar().get(GregorianCalendar.YEAR);
			Long increment = generaSecuencia(ConstantesTDDI.INCREMENTO);
			Date dateSql = new Date(date.getTime());
			
			//INSERT INTO TRA_PROCESO VALUES(?, TO_DATE ('?', 'DD-MM-YYYY'),null,'ESP',?,?,'?')
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("I_PTRA_PROCESOTEMP");
			params.addParam(increment);
			params.addParam(formatoFinal.format(dateSql));	
			params.addParam(idEmpleado);
			params.addParam(anioSys);
			params.addParam("RE");
			lrequestvo.add(params);
			
			//UPDATE TRA_TRANSFERENCIA SET IDPROCESO=?,IDESTATUS=?,COMENTARIO='?' WHERE IDTRANSFERENCIA=?
			params  = new ParametrosVo();
			params.setNameQuery("U_PTRA_TRANSREGRESAB");
			params.addParam(increment);
			params.addParam(batch);
			params.addParam(comentario);	
			params.addParam(idTransferencia);		
			lrequestvo.add(params);
			
			//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA,COMENTARIO) VALUES(historial_seq.nextval,?,?,?,?,?)
			params  = new ParametrosVo(); 	
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIALCOME);	
			params.addParam(idEmpleado);	
			params.addParam(Constantes.REGRESADA);	
			params.addParam(idTransferencia);	
			params.addParam(comentario);	
			lrequestvo.add(params); 
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			
	} catch (Exception e) { logger.info( e.getMessage());
		
		resultado="Error al regresar transferencia";
		logger.debug(e.getMessage(),e);
	} 
	return resultado; 
}


//OK PROBADA N+
public String setRevierteTransferencias(String[] lista, Integer idEmpleado) {
	String resultado="Transferencia(s) progamada para revertir";
	 
	java.util.Date date; 
	date= new java.util.Date();
	Date dateSql = new Date(date.getTime());
	SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	  
   try {
		
		int anioSys = new GregorianCalendar().get(GregorianCalendar.YEAR);
		for(String idtrans:lista ){
			
			Long increment = generaSecuencia(ConstantesTDDI.INCREMENTO);
			//INSERT INTO TRA_PROCESO VALUES(?,?,null,'ESP',?,?,?) (?, TO_DATE ('?', 'DD-MM-YYYY'),null,'ESP',?,?,'?')
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("I_PTRA_PROCESOTEMP");
			params.addParam(increment);
			params.addParam(formatoFinal.format(dateSql));	
			params.addParam(idEmpleado);
			params.addParam(anioSys);
			params.addParam("RV");
			
			lrequestvo.add(params);				
			
			//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=10,FECHATRANSFERENCIA= TO_DATE ('?', 'DD-MM-YYYY'),IDPROCESO=? WHERE IDTRANSFERENCIA=?
			params  = new ParametrosVo(); 
			params.setNameQuery("U_PTRA_TRANSPROCR");
			params.addParam(formatoFinal.format(dateSql));
			params.addParam(increment);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
			
			//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA) VALUES(historial_seq.nextval,SYSDATE,?,?,?)
			params  = new ParametrosVo(); 
			params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
			params.addParam(idEmpleado);
			params.addParam(Constantes.REVERTIDA);
			params.addParam(Integer.parseInt(idtrans));
			
			lrequestvo.add(params);
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		
		resultado="Error al programar la(s) Transferencia(s)";
		logger.debug(e.getMessage(),e);
	}
	return resultado;
}
//OK PROBADA

 public boolean insertaTransferenciasDesglosadas(TransferenciasDesglosadasPojo pojoTransDes) {
	logger.info( "Inicia metodo insertaTransferenciasDesglosadas");
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
    try {
    	    String queryTrans="I_PTRA_BATCH";
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery(queryTrans);
			params.addParam(pojoTransDes.getCorreo());
			params.addParam(pojoTransDes.getRegion());
			params.addParam(pojoTransDes.getParametro());
			params.addParam(pojoTransDes.getEstatus());
			params.addParam(pojoTransDes.getInicio());
			lrequestvo.add(params);
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			 logger.info( "Finaliza exitosamente insertaTransferenciasDesglosadas");
		
	} catch (Exception e) { 
		logger.info( "Excepcion en dao query I_PTRA_BATCH");
		return false;
	}
	return true;
}
 
 public boolean ifExistTransferenciaDesglosadas(String correo, String parametro, String estatus) {
		logger.info( "Inicia metodo insertaTransferenciasDesglosadas");
		try{
			GeneralDAO dao = new GeneralDAO(2); // xml/reportes
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_BATCH"); 
			params.addParam(correo);
			params.addParam(parametro);
			params.addParam(estatus);
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
			if (rowSet.size()>0) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) { 	
			logger.info("ERROR: "+e.getMessage());
			logger.info( "Excepcion en dao query I_PTRA_BATCH");
			return true;
		}
	}

//OK PROBADA
}
