package com.telcel.portal.servicios;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.sds.m2k.web.servicios.ControlWebService;
import com.telcel.sds.m2k.web.servicios.ControlWebServiceProxy;

public class CHIST {
	
	private final String usuario = "VI5SPAB";//PRODUCCION VI5SPAB 
	private static Logger  logger = Logger.getLogger(CHIST.class);
	
	
	public String consultaCHIST(String region, String cuenta){
		
		try{
					
			String endPoint="http://serviciosm2k.telcel.com:80/telcel-ws-web/services/ControlWebService";//PARAMETRIZAR
						
			ControlWebService service=new ControlWebServiceProxy(endPoint);
			String requestCHIST="<Request><user>?</user><region>?</region><function>I*45</function><action>C</action><inputParameters><servicio><p01telefono longitud=\"10\"></p01telefono><p02cuenta longitud=\"09\">?</p02cuenta></servicio></inputParameters></Request>";
			region=region.replace("R0", "");
			requestCHIST=requestCHIST.replaceFirst("\\?", usuario).replaceFirst("\\?", region).replaceFirst("\\?", cuenta);
			logger.info("REQUEST CONSULTA CHIST:"+requestCHIST);
			
			String responseCHIST=service.ejecutaServicio(requestCHIST); 
			logger.info("RESPONSE CONSULTA CHIST:"+responseCHIST);
			
			Document  xml=service.buildXML(responseCHIST);
			xml.getDocumentElement().normalize();
			
			try{
				logger.info("ENTRA A VALIDAR RESPUESTA OK - CONSULTA CHIST");
				NodeList nlistOK=xml.getElementsByTagName("RespuestaOK");
				Node node=nlistOK.item(0);
				Element element=(Element) node;
				
				String importe = validaNulos((String)element.getElementsByTagName("SaldoTotal").item(0).getNodeValue());	
				String fecha = validaNulos((String)element.getElementsByTagName("FecUltimaFac").item(0).getNodeValue());
				String mensaje = validaNulos((String)element.getElementsByTagName("MENSAJE").item(0).getNodeValue());
				logger.info("SALDO TOTAL:"+importe);
				logger.info("FECHA ULT FACT:"+fecha);
				logger.info("MENSAJE:"+mensaje);
				
				try{
					String mensajeError = validaNulos((String)element.getElementsByTagName("Mensaje").item(0).getNodeValue());
					String codigoError = validaNulos((String)element.getElementsByTagName("Codigo").item(0).getNodeValue()).trim();
					codigoError = codigoError.length() > 50 ? codigoError.substring(0, 50) : codigoError;
					logger.info("CODIGO ERROR:"+codigoError);
					logger.info("MENSAJE ERROR:"+mensajeError);
					
					return codigoError;
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				return "OK";
				
			}catch(Exception e){
				e.printStackTrace();
				logger.info("RESPUESTA ERROR - CONSULTA CHIST");
				try{
					NodeList nlistER=xml.getElementsByTagName("RespuestaError");
					Node node=nlistER.item(0);
					Element element=(Element) node;
					String codigoError =validaNulos((String)element.getElementsByTagName("MENSAJE").item(0).getNodeValue()).substring(0, 50);	
					
					return codigoError;
				}catch(Exception ex){
					logger.info("FALLA EN RESPUESTA ERROR - CONSULTA CHIST");
					ex.printStackTrace();
					return "ER";
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return "ER";
		}
					
	}

	
	public String altaCHIST(String region, String cuenta, String fecha){
		
		try{
			String endPoint="http://serviciosm2k.telcel.com:80/telcel-ws-web/services/ControlWebService";//PARAMETRIZAR
			ControlWebService service=new ControlWebServiceProxy(endPoint);
			String requestCHIST="<Request><user>?</user><region>?</region><function>I*45</function><action>A</action><inputParameters><servicio><p01telefono longitud=\"10\"></p01telefono><p02cuenta longitud=\"09\">?</p02cuenta><p03acc longitud=\"03\">PPA</p03acc><p04motivo longitud=\"05\">RECLI</p04motivo><p05impPromet longitud=\"12\"></p05impPromet><p06fecPromet longitud=\"08\">?</p06fecPromet><p07habloCon longitud=\"10\">VI5SPAB</p07habloCon><p08tipTel longitud=\"01\"></p08tipTel><p09telLlamado longitud=\"10\"></p09telLlamado><p10sigFecAcc longitud=\"08\"></p10sigFecAcc></servicio></inputParameters></Request>";
			region=region.replace("R0", "");
			//requestCHIST=requestCHIST.replaceFirst("\\?", usuario).replaceFirst("\\?", region).replaceFirst("\\?", cuenta).replaceFirst("\\?", importe).replaceFirst("\\?", fecha);
			requestCHIST=requestCHIST.replaceFirst("\\?", usuario).replaceFirst("\\?", region).replaceFirst("\\?", cuenta).replaceFirst("\\?", fecha);
			logger.info("REQUEST ALTA CHIST:"+requestCHIST);
			
			String responseCHIST=service.ejecutaServicio(requestCHIST); 
			logger.info("RESPONSE ALTA CHIST:"+responseCHIST);
			
			Document  xml=service.buildXML(responseCHIST);
			xml.getDocumentElement().normalize();
			
			try{
				logger.info("RESPUESTA OK - ALTA CHIST");
				NodeList nlistOK=xml.getElementsByTagName("RespuestaOK");
				Node node=nlistOK.item(0);
				Element element=(Element) node;
				String 	mensaje = validaNulos((String)element.getElementsByTagName("MENSAJE").item(0).getNodeValue());	
				
				logger.info("MENSAJE:"+mensaje);
				
				return "OK";
			
			}catch(Exception e){
				logger.info("RESPUESTA ERROR - ALTA CHIST");
				e.printStackTrace();
				
				try{
					NodeList nlistER=xml.getElementsByTagName("RespuestaError");
					Node node=nlistER.item(0);
					Element element=(Element) node;
					String codigoError =validaNulos((String)element.getElementsByTagName("MENSAJE").item(0).getNodeValue()).trim();
					codigoError = codigoError.length() > 50 ? codigoError.substring(0, 50) : codigoError;
					return codigoError;
					
				}catch(Exception ex){
					logger.info("FALLA EN RESPUESTA ERROR - ALTA CHIST");
					ex.printStackTrace();
					return "ER";
				}
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			return "ER";
		}
	}
		
	public String validaNulos(String cadena){
		String str = "";
		if( cadena != null) {
			str = cadena.trim();
		}
		return str;
	}
}
