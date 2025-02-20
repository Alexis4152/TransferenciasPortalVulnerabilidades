package com.telcel.sds.m2k.web.servicios;

import java.io.StringReader;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ControlWebServiceProxy implements com.telcel.sds.m2k.web.servicios.ControlWebService {
  private String _endpoint = null;
  private com.telcel.sds.m2k.web.servicios.ControlWebService controlWebService = null;
  
  public ControlWebServiceProxy() {
    _initControlWebServiceProxy();
  }
  
  public ControlWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initControlWebServiceProxy();
  }
  
  private void _initControlWebServiceProxy() {
    try {
      controlWebService = (new com.telcel.sds.m2k.web.servicios.ControlWebServiceServiceLocator()).getControlWebService();
      if (controlWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)controlWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)controlWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (controlWebService != null)
      ((javax.xml.rpc.Stub)controlWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.telcel.sds.m2k.web.servicios.ControlWebService getControlWebService() {
    if (controlWebService == null)
      _initControlWebServiceProxy();
    return controlWebService;
  }
  
  public String ejecutaServicio(String xml) throws java.rmi.RemoteException{
	  String resultado="";
	  if (controlWebService == null){
      _initControlWebServiceProxy();
    }else {
		resultado=getControlWebService().ejecutaServicio(xml);
		 
	}
    return resultado;
  }


public org.w3c.dom.Document buildXML(String xml) {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	DocumentBuilder builder;  
	Document document=null;	
	try {  
		builder=factory.newDocumentBuilder();  
		document=builder.parse(new InputSource(new StringReader(xml)));
	   
	} catch (Exception e) {  
	    e.printStackTrace();  
	} 
	
	return document;
}


  
}