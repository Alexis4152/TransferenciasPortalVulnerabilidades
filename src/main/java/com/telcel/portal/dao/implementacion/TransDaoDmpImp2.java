package com.telcel.portal.dao.implementacion;


import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.sql.rowset.WebRowSet;

import org.apache.log4j.Logger;

import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.factory.vo.ParametrosVo;
import com.telcel.portal.util.PropertiesFiles;
import com.telcel.portal.dao.interfaces.TransDaoInterface2;
import com.telcel.portal.dao.interfaces.TransferenciasDaoConsultaInterface;

import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.CargaDocumentoPojo;
import com.telcel.portal.pojos.ConsultaPagosPojo;
import com.telcel.portal.pojos.HistorialPojo;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.util.Constantes;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.ConstantesTDDI;

public class TransDaoDmpImp2  implements TransDaoInterface2{

private static Logger  logger = Logger.getLogger(GeneralDAO.class);

//OK PROBADA N+
public String setDesglosado(Integer idTransferencia,Integer idEmpleado,String tipoCuentas,String correoElectronico) {
	 
	java.util.Date date;
		 
	date= new java.util.Date();
	Date dateSql = new Date(date.getTime());
	SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
	
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	try {
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(1); //xml/desglose
				
		//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=3,FECHATRANSFERENCIA=? ,TIPOPAGOS=? WHERE IDTRANSFERENCIA=?
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("U_PTRA_TRATPAGO");	
		params.addParam(formatoFinal.format(dateSql));
		params.addParam(tipoCuentas);
		params.addParam(correoElectronico);
		params.addParam(idTransferencia);
						 	  					
		lrequestvo.add(params);
				
		//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA) VALUES(historial_seq.nextval,SYSDATE,?,?,?)
		params  = new ParametrosVo();
		params.setNameQuery(ConstantesTDDI.IPTRAHISTORIAL);
		params.addParam(idEmpleado);
		params.addParam(Constantes.DESGLOSADA);
		params.addParam(idTransferencia);
				
		lrequestvo.add(params);
	
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			
		} catch (Exception e) { logger.info( e.getMessage());
			logger.debug(e.getMessage(),e);
			
		}
		return "Transferencia desglosada, enviada con exito";
}
//OK PROBADA
public String editarPago(PagoPojo pagoPojo, EmpleadoPojo usuario) {
	String resultado="Pago modificado";

		try {
			
			if(pagoPojo.getTipo()!=null && pagoPojo.getTipo().equals("CU")){
				
	    		try {
	    			
	   				ParametrosVo params = new ParametrosVo();
	   				params.setNameQuery("S_PTRA_VALIDA_USR");
	   				params.setRegion(pagoPojo.getRegion());
	   				params.addParam(pagoPojo.getRegion());
	   				params.addParam(pagoPojo.getCuenta().trim());
	   				PropertiesFiles prop = new PropertiesFiles();
	   				Properties p = prop.getPropertiesConf();
	   				
	   				GeneralDAO dao = new GeneralDAO(1);
	   				WebRowSet rowSet	= dao.selectSql(params, p.getProperty(ConstantesTDDI.APLMOBILE1));
					
				    String user;
					if(rowSet.next()){
				    				    	
						 user = rowSet.getString("SERV_CSR_ID");
						 int tipoPerfil = usuario.getIdPefril();
						 logger.info("PERFIL:"+tipoPerfil);
						 if(tipoPerfil != ConstantesNumeros.CINCO){ //Si es usuario de la gerencia de cobranza, no valida el etiquetado de la cta en M2K 
							 if(!(usuario.getPuesto().toLowerCase(Locale.getDefault()).trim().startsWith("gerente") || usuario.getPuesto().toLowerCase(Locale.getDefault()).trim().startsWith("subdirector"))){
							 
								 if(!user.trim().equals(usuario.getUsuario().trim()) && !user.trim().equals(usuario.getUsuarioJefeDirecto().trim()) ){
									
									 return ConstantesTDDI.CUENTA+pagoPojo.getCuenta()+" no se encuentran asignadas a tu usuario o al de tu jefe. ";
								 }
							 }
						 }
				    }else{  	
				    	
				    	return ConstantesTDDI.CUENTA+pagoPojo.getCuenta()+"  no existe, no corresponde a la region, no es de tipo corporativa.";
				    	
				    }
					
				} catch (Exception e) {
					logger.info("*******------------validaUsuario:"+e.getMessage());
					logger.debug(e.getMessage(),e);
					return "Error, la cuenta:"+pagoPojo.getCuenta()+" no existe o no pertenece a la region";
				}
				
				
				try {
					
					ParametrosVo params = new ParametrosVo();
	   				params.setNameQuery("S_TRAN_RESPAGO");
	   				params.setRegion(pagoPojo.getRegion());
	   				params.addParam(pagoPojo.getRegion());
	   				params.addParam(pagoPojo.getCuenta().trim());
	   				PropertiesFiles prop = new PropertiesFiles();
	   				Properties p = prop.getPropertiesConf();
	   				
	   				GeneralDAO dao = new GeneralDAO(1);
	   				WebRowSet rowSet	= dao.selectSql(params, p.getProperty(ConstantesTDDI.APLMOBILE1));
	   				rowSet.next();
					
					
					ConsultaPagosPojo pojoConsulta = new ConsultaPagosPojo();
										
					pojoConsulta.setCuenta(rowSet.getString("CTA_REG"));
					pojoConsulta.setCuentaCambio(rowSet.getString("CTA_NAC"));
					pojoConsulta.setRespuesta(rowSet.getString("RESPONS"));
					
					 if(pojoConsulta.getCuentaCambio()!=null){
							 
						 if(!pagoPojo.getCuenta().trim().equals(pojoConsulta.getCuentaCambio().trim())) {
							 pagoPojo.setCuenta(pojoConsulta.getCuentaCambio());
							 pagoPojo.setRegion("R09");
							 resultado=resultado+" cuenta cambiada por:"+pojoConsulta.getCuentaCambio();
						 }
							
						}else{
							
							if(!(pojoConsulta.getRespuesta()!=null && pojoConsulta.getRespuesta().equals("S"))){
								
								return "La cuenta "+pojoConsulta.getCuenta()+" no tiene responsabilidad de pagos";	
							}
							 
						}
				} catch (Exception e) {
					return "Error, la cuenta:"+pagoPojo.getCuenta()+" no existe o no pertenece a la region";
				}
				
			}
			
			
			//UPDATE TRA_PAGOS SET IMPORTE=?,CUENTA=?,REGION=?,TIPO='?',SAP='?'  WHERE IDPAGOS=?
			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			GeneralDAO dao = new GeneralDAO(1); //xml/desgloce
			
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("U_PTRA_TRANSEPAGO");	
			 
			params.addParam(pagoPojo.getImporte());
			params.addParam(pagoPojo.getCuenta());
			params.addParam(pagoPojo.getRegion());
			params.addParam(pagoPojo.getTipo());
			params.addParam(pagoPojo.getSap());
			params.addParam(pagoPojo.getIdPago());
			 
			dao.executeSql(params, p.getProperty(ConstantesTDDI.APLTIC),null);
		 
		
	} catch (Exception e) { logger.info( e.getMessage());
		resultado="Error al modificar el pago";
		logger.debug(e.getMessage(),e);
	}
	return resultado;
}
//OK PROBADA
public String eliminarPago(Integer idPago) {
 
	
	String resultado="Pago borrado";
	
	try {
		
		//DELETE  FROM TRA_PAGOS WHERE IDPAGOS=?
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(1); //xml/desgloce
		
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("D_PTRA_PAGOS");	
		params.addParam(idPago);
		 
		dao.executeSql(params, p.getProperty(ConstantesTDDI.APLTIC),null);
			
		} catch (Exception e) { 
			resultado="Error al borrar el pago";
			logger.info( e.getMessage());		
		}
		return resultado;
}
//OK PROBADA N+
public String regresarTransferencia(Integer idTransferencia,Integer idEmpleado,String comentario,Integer batch) {
	  
		 String resultado="Transferencia regresada";
		 
		 ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 GeneralDAO dao = new GeneralDAO(1); //xml/desglose	

			try {
				//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=?,COMENTARIO='?' WHERE IDTRANSFERENCIA=?
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("U_PTRA_TRANSREGRESA");
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

public String comentarTransferencia(Integer idTransferencia,Integer idEmpleado,String comentario) {
	  
	 String resultado="Transferencia comentada";
	 
	 ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 PropertiesFiles prop = new PropertiesFiles();
	 Properties p = prop.getPropertiesConf();
	 GeneralDAO dao = new GeneralDAO(1); //xml/desglose	

		try {
		   
			//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA,COMENTARIO) VALUES(historial_seq.nextval,?,?,?,?,?)
			 ParametrosVo params  = new ParametrosVo(); 	
			 params.setNameQuery(ConstantesTDDI.IPTRAHISTORIALCOME);	
			 params.addParam(idEmpleado);	
			 params.addParam(Constantes.ERRORDESGLOSE);	
			 params.addParam(idTransferencia);	
			 params.addParam(comentario);
			 
			 lrequestvo.add(params); 
			
			 dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			
	} catch (Exception e) { logger.info( e.getMessage());
		
		resultado="Error al comentar la transferencia";
		logger.debug(e.getMessage(),e);
	} 
	return resultado; 
}
//OK PROBADA
public String eliminarPagosByIdTransferencia(Integer idTransferencia) {

	String resultado="ok";
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();

	try {
		
		//DELETE FROM TRA_PAGOS WHERE IDTRANSFERENCIA=?
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(1); //xml/desgloce
		
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("D_PTRA_PAGOSBYTRANS");	
		params.addParam(idTransferencia);
		 	  					
		lrequestvo.add(params);
			
		params  = new ParametrosVo(); 
		params.setNameQuery("U_PTRA_TRAEMPNULL");
		params.addParam(idTransferencia);
		
		lrequestvo.add(params);
		 
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			
		} catch (Exception e) { 
			resultado="Error al borrar el pago";
			logger.info( e.getMessage());
			
		}
		return resultado;

}

	public String eliminarPagosOnlyByIdTransferencia(String[] idTransferencia) {

		String resultado = "Transferencia cambiada de estatus correctamente";
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		// DELETE FROM TRA_PAGOS WHERE IDTRANSFERENCIA=?
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(1); // xml/desgloce
		try {
			for (String idtrans : idTransferencia) {
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("D_PTRA_PAGOSBYTRANS");
				params.addParam(idtrans);

				lrequestvo.add(params);

			}
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		} catch (Exception e) {
			resultado = "Error al borrar el pago";
			logger.info(e.getMessage());

		}
		return resultado;

	}
//OK PROBADA N+
public String setCierraTransferencias(String[] lista, Integer idEmpleado, String coment) {
	 
		 java.util.Date date;	 
		 date= new java.util.Date();
		 Date dateSql = new Date(date.getTime());

		 SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesTDDI.FORMATOFECHA);
		 ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		 PropertiesFiles prop = new PropertiesFiles();
		 Properties p = prop.getPropertiesConf();
		 GeneralDAO dao = new GeneralDAO(1); //xml/desglose
		 	
			try {
				
				for(String idTrans:lista){
					
					//UPDATE TRA_TRANSFERENCIA SET IDESTATUS=9,FECHATRANSFERENCIA=? WHERE IDTRANSFERENCIA=?			
					ParametrosVo params = new ParametrosVo();
					params.setNameQuery("U_PTRA_TRACIERRA");					 
					params.addParam(formatoFinal.format(dateSql));
					params.addParam(Integer.parseInt(idTrans));
					
					lrequestvo.add(params);
					
					//INSERT INTO TRA_HISTORIAL(IDHISTORIAL,FECHA,IDEMPLEADO,IDESTATUS,IDTRANSFERENCIA) VALUES(historial_seq.nextval,SYSDATE,?,?,?)
					params  = new ParametrosVo(); 	
					params.setNameQuery("I_PTRA_HISTORIALCOME");		
					params.addParam(idEmpleado);	
					params.addParam(Constantes.CERRADA);	
					params.addParam(Integer.parseInt(idTrans));	
					params.addParam(coment);	
					
					lrequestvo.add(params); 
					
				}
				
				dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			
		} catch (Exception e) { logger.info( e.getMessage());
			logger.debug(e.getMessage(),e);
			
		}
		return "Transferencia Cerrada";
}
public TransferenciaPojo consultaTransferenciaxId(String idTransferencia) {
	TransferenciaPojo pojo= null;
	
	try {
		GeneralDAO dao = new GeneralDAO(3); // xml/general 
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();	
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_TRANSXID");
		params.addParam(idTransferencia);
		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));	
		while(rowSet.next()) {
			
			pojo=new TransferenciaPojo();		
			pojo.setIdBanco(Integer.parseInt(rowSet.getString("IDBANCO")));
			pojo.setDateFecha(rowSet.getDate("FECHA"));
			pojo.setCuenta(rowSet.getString("CUENTABANCO"));
			pojo.setImporte(rowSet.getDouble("IMPORTE"));
			pojo.setReferenciaBanco(rowSet.getString("REFERENCIABANCO"));
			pojo.setReferenciaCliente(rowSet.getString("REFERENCIACLIENTE"));
			pojo.setEstatus(rowSet.getInt("IDESTATUS"));
			pojo.setRegion("R0"+rowSet.getInt("ID_REGION"));
			
		}	
				
	} catch (Exception e) { 
		
		logger.info( e.getMessage());
		logger.debug(e.getMessage(),e);
		pojo = null;
	}
	return pojo;
}
public List<TransferenciaPojo> consultaTransferencias(List<TransferenciaPojo> transferencias,Integer idBanco, String region) {
	
	List<TransferenciaPojo> lista = null;
	try {
		
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal=Calendar.getInstance();
		region=region.replace("R0", "");
		/*SELECT * FROM TRA_TRANSFERENCIA WHERE  fecha >= TO_DATE ('03-09-2013', 'DD-MM-YYYY')
				 AND fecha < TO_DATE ('04-09-2013', 'DD-MM-YYYY') AND idbanco = 1 ORDER BY IDTRANSFERENCIA*/
	
		GeneralDAO dao = new GeneralDAO(1); // xml/desglose		 
	
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
			
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_CONSTRANS");
		
		cal.setTimeInMillis(transferencias.get(0).getDateFecha().getTime());
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND,0);
		params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		params.addParam(formatoFinal.format(new Date(cal.getTimeInMillis())));
		params.addParam(idBanco);
		params.addParam(region);
		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HHmmss");
		
		lista= new ArrayList<TransferenciaPojo>();
		
		while(rowSet.next()) {
			
			TransferenciaPojo pojo= new TransferenciaPojo();
			
			pojo.setIdBanco(Integer.parseInt(rowSet.getString("IDBANCO")));
			pojo.setFecha(formato.format(rowSet.getDate("FECHA")).trim());
			pojo.setImporte(rowSet.getDouble("IMPORTE"));
			pojo.setReferenciaBanco(rowSet.getString("REFERENCIABANCO"));
			pojo.setReferenciaCliente(rowSet.getString("REFERENCIACLIENTE"));
			pojo.setBanco(rowSet.getString("NOMBRE"));
			
			lista.add(pojo);
		}	
				
	} catch (Exception e) { 
		
		logger.info( e.getMessage());
		logger.debug(e.getMessage(),e);
		lista = null;
	}// TODO Apendice de metodo generado automaticamente
	return lista;
}

public List<TransferenciaPojo> identificaDuplicados(List<TransferenciaPojo> listaArchivo, List<TransferenciaPojo> listaConsulta, int region) {
	List<TransferenciaPojo> lista = null;
	
	try{
	
		lista = new ArrayList<TransferenciaPojo>();
		int index=0;
		
		for(TransferenciaPojo beanArchivo:listaArchivo ){
			index++;
			for(TransferenciaPojo beanConsulta:listaConsulta){
				
				double impArchivo = beanArchivo.getImporte();
				double impConsulta = beanConsulta.getImporte();
				
				BigDecimal impA = new BigDecimal(impArchivo);
				BigDecimal impC = new BigDecimal(impConsulta);
				impA = impA.setScale(0,BigDecimal.ROUND_CEILING);
				impC = impC.setScale(0,BigDecimal.ROUND_CEILING);
				
				if((impA.equals(impC)) && 
						(beanArchivo.getReferenciaBanco().equals(beanConsulta.getReferenciaBanco())) &&
						(beanArchivo.getReferenciaCliente().equals(beanConsulta.getReferenciaCliente()))){
					
					beanArchivo.setIdtransferencia(index);
					beanArchivo.setEsDuplicada(true);
					lista.add(beanArchivo);
										
					
					break;
				}
				
				//valida que la referencia del banco coincida para discriminar los registros
				if (region == 3)
					if((impA.equals(impC)) && 
							(
							beanConsulta.getReferenciaBanco().contains(beanArchivo.getReferenciaBanco()) ||
							beanArchivo.getReferenciaBanco().contains(beanConsulta.getReferenciaBanco())
							)){
						
						beanArchivo.setIdtransferencia(index);
						beanArchivo.setEsDuplicada(true);
						lista.add(beanArchivo);											
						
						break;
					}				
			}
		}
	}catch(Exception e){
		logger.debug(e.getMessage(),e); 
		lista = null;
	}
	
	return lista;
}

public boolean validaCargaTransferencias() {
	
	try {
		
		/*SELECT * FROM SIPAB_PARAMSCONEXION WHERE APLICACION='TIC' AND SERVIDOR='CARGATRANS' AND TIPO='AC'*/
	
		GeneralDAO dao = new GeneralDAO(1); // xml/desglose		 
	
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
			
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_VALCARGA");
		
		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
		
		if(rowSet.next()) {
			return true;
		}	
				
	} catch (Exception e) { 
		
		logger.info( e.getMessage());
		logger.debug(e.getMessage(),e);
		return false;
	}// TODO Apendice de metodo generado automaticamente
	return false;
}

public boolean consultaEstadoTomar(String[] lista) {
	 
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	boolean estado = true;	
	
	
    try {
		
	    //SELECT IDESTATUS, IDEMPLEADO, ALIAS FROM TRA_TRANSFERENCIA WHERE IDTRANSFERENCIA=?
		for(String idTrans:lista ){
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_VALEDO");
			params.addParam(idTrans);
			
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
			
			if(rowSet.next()) {
				int idEmpleado = rowSet.getString("IDEMPLEADO")!= null ? Integer.parseInt(rowSet.getString("IDEMPLEADO")): 0 ;
				
				if(rowSet.getInt("IDESTATUS")!= 2 ||  idEmpleado > 0){
					return false;
				}
			}	
			
		}
		
	} catch (Exception e) { 
		logger.info( e.getMessage());
		estado = false;
		logger.debug(e.getMessage(),e);
	}
	return estado;
}

public boolean consultaEstadoDesglose(int idTrans) {

	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	boolean estado = true;	
	
    try {
		
	    //SELECT IDESTATUS FROM TRA_TRANSFERENCIA WHERE IDTRANSFERENCIA=?
	
		ParametrosVo params = new ParametrosVo();
		params.setNameQuery("S_PTRA_VALEDO");
		params.addParam(idTrans);
			
		WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
			
		if(rowSet.next()) {			
				if(rowSet.getInt("IDESTATUS")!= 2){
					
					return false;
				}
		}	
		
	} catch (Exception e) { 
		logger.info( e.getMessage());
		estado = false;
		logger.debug(e.getMessage(),e);
	}
	return estado;
}

public boolean consultaEstadoAplicado(String[] lista) {

	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	boolean estado = true;	
	
    try {
		
	    //SELECT IDESTATUS, IDEMPLEADO, ALIAS FROM TRA_TRANSFERENCIA WHERE IDTRANSFERENCIA=?
		for(String idTrans:lista ){
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_VALEDO");
			
			params.addParam(idTrans.substring(0,idTrans.lastIndexOf("_") ));
			
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
			
			if(rowSet.next()) {	
				
				if(rowSet.getInt("IDESTATUS")!= 3){
					return false;
				}
			}	
			
		}
		
	} catch (Exception e) { 
		logger.info( e.getMessage());
		estado = false;
		logger.debug(e.getMessage(),e);
	}
	return estado;
}

public boolean consultaEstadoAplicado(String idTransferencia) {
	 
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	boolean estado = true;	
	
    try {
		
	    //SELECT IDESTATUS, IDEMPLEADO, ALIAS FROM TRA_TRANSFERENCIA WHERE IDTRANSFERENCIA=?

			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_VALEDO");
			params.addParam(idTransferencia);
			
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
			
			if(rowSet.next()) {
				
				if(rowSet.getInt("IDESTATUS")!= 3){
					return false;
				}
			}	
		
	} catch (Exception e) { 
		logger.info( e.getMessage());
		estado = false;
		logger.debug(e.getMessage(),e);
	}
	return estado;
}

public String actualizaDatoPromesaPago(List<PagoPojo> listaPagos, String tipo) {
	 
	String resultado="OK";
	
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 
    try {
			
    	for(PagoPojo pago : listaPagos){
    		
    		String estatus = "";
    		String descripcion = "";
    		
    		if(tipo.equals("BATCH")){
    			estatus = "PE";
    			descripcion = "PENDIENTE DE APLICAR";
    		}else{
    			estatus = pago.getEstatusPP() != null ? pago.getEstatusPP().trim():"";
    			descripcion = pago.getDescripcionPP() != null ? pago.getDescripcionPP().trim():"";
    		}
			
			//UPDATE TRA_PAGOS SET ESTATUSPP='?', DESCRIPCIONPP='?' WHERE IDPAGO= ?
			ParametrosVo params  = new ParametrosVo(); 
			params.setNameQuery("U_PTRA_PROMESAPAGO");
			params.addParam(estatus);
			params.addParam(descripcion); 
			params.addParam(pago.getIdPago());	
			lrequestvo.add(params);
		}
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		resultado="Error al actualizar la promesa de pago";
		logger.debug(e.getMessage(),e);  
	}
	return resultado;
}

public boolean validaPromesaPago() {
	 
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	boolean estado = true;	
	
    try {
		
	    //SELECT ESTATUS FROM SIPAB_PARAMSCONEXION WHERE APLICACION ='TICPP'

			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_VALIDAPROMESA");
			
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
			
			if(rowSet.next()) {
				if(rowSet.getString("ESTATUS").trim().equals("A")){
					return true;
				}else{
					return false;
				}
			}	
		
	} catch (Exception e) { 
		logger.info( e.getMessage());
		estado = false;
		logger.debug(e.getMessage(),e);
	}
	return estado;
}

public String validaLimitePromesaPago() {
	 
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	String limite = "0";	
	
    try {
		
	    //SELECT ESTATUS FROM SIPAB_PARAMSCONEXION WHERE APLICACION ='TICPP'

			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_VALIDAPROMESA");
			
			WebRowSet rowSet	= dao.selectSql(params,p.getProperty(ConstantesTDDI.APLTIC));
			
			if(rowSet.next()) {
				limite = rowSet.getString("PUERTO").trim();
			}	
		
	} catch (Exception e) { 
		logger.info( e.getMessage());
		limite="0";
		logger.debug(e.getMessage(),e);
	}
	return limite;
}

public String insertaProcesoPromesa(Integer idTransferencia, Integer idEmpleado) {
	 
	 String resultado="Proceso en espera";
		
	PropertiesFiles prop = new PropertiesFiles();
	Properties p = prop.getPropertiesConf();
	GeneralDAO dao = new GeneralDAO(1); //xml/desglose
	ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
	 
    try {
			//INSERT INTO SIPAB_PARAMS_TMP VALUES('','?','PEN','?','','R09',SYSDATE,'')
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("I_PTRA_PROCESOPROMESA");
			params.addParam(idTransferencia.toString());
			params.addParam(idEmpleado.toString());
			lrequestvo.add(params);				
		
		dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
		
	} catch (Exception e) {
		
		logger.info( e.getMessage());
		resultado="Error al aplicar el proceso de pp";
		logger.debug(e.getMessage(),e);  
	}
	return resultado;
}

	public List<Long> consultaNumerosDocumento(List<CargaDocumentoPojo> documentos) {
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(3); // xml/general
		StringBuilder paramsIn = new StringBuilder();
		List<Long> resultados = new ArrayList<>();
		for(CargaDocumentoPojo cargaDocumentoPojo : documentos) {
			paramsIn.append(cargaDocumentoPojo.getNumeroDocumento().longValue());
			paramsIn.append(",");
		}
		try {
			// SELECT NUMERO_DOCUMENTO FROM TRA_COMPENSACIONES WHERE NUMERO_DOCUMENTO IN (?)
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_VALIDA_NDOCUMENTO");
			params.addParam(paramsIn.toString().substring(0,paramsIn.toString().length()-1));

			WebRowSet ws = dao.selectSql(params, p.getProperty(ConstantesTDDI.APLTIC));
			
			while(ws.next()) {
				resultados.add(ws.getLong("NUMERO_DOCUMENTO"));
			}

		} catch (Exception e) {

			logger.info(e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		return resultados;
	}
	
	public void insertaNumerosDocumento(List<CargaDocumentoPojo> documentos) {
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(3); //xml/general
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		 
	    try {
	    	for(CargaDocumentoPojo cargaDocumentoPojo : documentos) {
				//INSERT INTO TRA_COMPENSACIONES (FECHA_DOCUMENTO,LOTE,NUMERO_DOCUMENTO,DIVISION) VALUES(TO_DATE('?','dd/MM/yyyy'),?,?,'?')
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("I_PTRA_NDOCUMENTO");
				params.addParam(cargaDocumentoPojo.getFechaLote());
				params.addParam(cargaDocumentoPojo.getLote());
				params.addParam(cargaDocumentoPojo.getNumeroDocumento());
				params.addParam(cargaDocumentoPojo.getDivision());
				params.addParam(cargaDocumentoPojo.getFechaLote());
				lrequestvo.add(params);		
	    	}
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			logger.debug(e.getMessage(),e);  
		}
	}
	
	@Override
	public boolean insertaPeticion(Integer idEmpleado, String horario, String periodo, String asistencia, String cantidadMensajes) {
		boolean resultado=false;
		
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(3); //xml/general
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		 
	    try {
				//INSERT INTO MBGWOWN.TRA_PETICIONES (IDPETICION, IDEMPLEADO, HORARIO, PERIODO, ASISTENCIA, CANTIDAD_DE_MENSAJES) VALUES(?, '?', '?', '?', '?')
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("I_PTRA_PETICION");
				params.addParam(idEmpleado);
				params.addParam(horario);
				params.addParam(periodo);
				params.addParam(asistencia);
				params.addParam(cantidadMensajes);
				lrequestvo.add(params);				
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			resultado = true;
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			resultado=false;
			logger.debug(e.getMessage(),e);  
		}
		return resultado;
	}
	
	@Override
	public boolean updatePeticion(Integer idPeticion, Integer idEmpleado, String horario, String periodo,
			String asistencia, String cantidadMensajes) {
		boolean resultado=false;
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(3); //xml/general
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		 
	    try {
				//		UPDATE MBGWOWN.TRA_PETICIONES SET IDEMPLEADO=?, HORARIO='?', PERIODO='?', ASISTENCIA='?', CANTIDAD_DE_MENSAJES='?'	WHERE IDPETICION=?	

				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("U_PTRA_PETICION");
				params.addParam(idEmpleado);
				params.addParam(horario);
				params.addParam(periodo);
				params.addParam(asistencia);
				params.addParam(cantidadMensajes);
				params.addParam(idPeticion);
				lrequestvo.add(params);				
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			resultado = true;
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			resultado=false;
			logger.debug(e.getMessage(),e);  
		}
		return resultado;
	}
	
	@Override
	public boolean insertaAlerta(String color, String tiempo, Integer idEstatus) {
		boolean resultado=false;
		
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(3); //xml/general
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		 
	    try {
				//		INSERT INTO TRA_ALERTAS (COLOR, TIEMPO, IDESTATUS) VALUES('?', '?', ?)
				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("I_PTRA_ALERTA");
				params.addParam(color);
				params.addParam(tiempo);
				params.addParam(idEstatus);
				lrequestvo.add(params);				
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			resultado = true;
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			resultado=false;
			logger.debug(e.getMessage(),e);  
		}
		return resultado;
	}
	
	@Override
	public boolean updateAlerta(Integer idAlerta, String color, String tiempo, Integer idEstatus) {
		boolean resultado=false;
		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(3); //xml/general
		ArrayList<ParametrosVo> lrequestvo = new ArrayList<ParametrosVo>();
		 
	    try {
				//		UPDATE TRA_ALERTAS SET COLOR='?', TIEMPO='?', IDESTATUS=? WHERE IDALERTA=?

				ParametrosVo params = new ParametrosVo();
				params.setNameQuery("U_PTRA_ALERTA");
				params.addParam(color);
				params.addParam(tiempo);
				params.addParam(idEstatus);
				params.addParam(idAlerta);
				lrequestvo.add(params);				
			
			dao.transactionListSql(lrequestvo, p.getProperty(ConstantesTDDI.APLTIC));
			resultado = true;
		} catch (Exception e) {
			
			logger.info( e.getMessage());
			resultado=false;
			logger.debug(e.getMessage(),e);  
		}
		return resultado;
	}

}
