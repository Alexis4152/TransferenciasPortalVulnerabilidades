package com.telcel.portal.servicios;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.sds.m2k.web.servicios.ControlWebService;
import com.telcel.sds.m2k.web.servicios.ControlWebServiceProxy;

public class CSMAN {
	
	private final String usuario = "VI5SPAB";//PRODUCCION VI5SPAB 
	private static Logger  logger = Logger.getLogger(CSMAN.class);
	
	public String altaCSMAN(String region, String cuenta){
		
		try{
			String endPoint="http://serviciosm2k.telcel.com:80/telcel-ws-web/services/ControlWebService";//PARAMETRIZAR
			
			ControlWebService service=new ControlWebServiceProxy(endPoint);
			String requestCSMAN="<Request><user>?</user><region>?</region><function>I*43</function><action>A</action><inputParameters><servicio><p01telefono longitud=\"10\"></p01telefono><p02cuenta longitud=\"08\">?</p02cuenta><p03clvMotivoAccion longitud=\"03\">MCO</p03clvMotivoAccion><p04accionRecom longitud=\"03\">CON</p04accionRecom><p05asiGrepCob longitud=\"08\">VI5SPAB</p05asiGrepCob></servicio></inputParameters></Request>";
			region=region.replace("R0", "");
			requestCSMAN = requestCSMAN.replaceFirst("\\?", usuario).replaceFirst("\\?", region).replaceFirst("\\?", cuenta);
			logger.info("REQUEST ALTA CSMAN:"+requestCSMAN);
			
			String responseCSMAN=service.ejecutaServicio(requestCSMAN); 
			logger.info("RESPONSE ALTA CSMAN:"+responseCSMAN);
			
			Document  xml=service.buildXML(responseCSMAN);
			xml.getDocumentElement().normalize();
			
			try{
				logger.info("RESPUESTA OK - ALTA CSMAN");
				NodeList nlistOK=xml.getElementsByTagName("RespuestaOK");
				Node node=nlistOK.item(0);
				Element element=(Element) node;
				String saldo = validaNulos((String)element.getElementsByTagName("SaldoTotal").item(0).getNodeValue());	
				String fechaAccion = validaNulos((String)element.getElementsByTagName("SiguienteFechaAccion").item(0).getNodeValue());
				
				String mensaje = validaNulos((String)element.getElementsByTagName("MENSAJE").item(0).getNodeValue());
				String mensajeError = validaNulos((String)element.getElementsByTagName("Mensaje").item(0).getNodeValue());
				String codigoError = validaNulos((String)element.getElementsByTagName("Codigo").item(0).getNodeValue()).trim();
				codigoError = codigoError.length() > 50 ? codigoError.substring(0, 50) : codigoError;
				
				logger.info("SALDO TOTAL:"+saldo);
				logger.info("FECHA ACCION:"+fechaAccion);
				logger.info("CODIGO ERROR:"+codigoError);
				logger.info("MENSAJE ERROR:"+mensajeError);
				logger.info("MENSAJE:"+mensaje);
				
				return "OK";
				
			}catch(Exception e){
				logger.info("RESPUESTA ERROR - ALTA CSMAN");
				e.printStackTrace();
				try{
					NodeList nlistER=xml.getElementsByTagName("RespuestaError");
					Node node=nlistER.item(0);
					Element element=(Element) node;
					String codigoError =validaNulos((String)element.getElementsByTagName("MENSAJE").item(0).getNodeValue()).trim();
					codigoError = codigoError.length() > 50 ? codigoError.substring(0, 50) : codigoError;
					return codigoError;
				}catch(Exception ex){
					logger.info("FALLA EN RESPUESTA ERROR - ALTA CSMAN");
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
