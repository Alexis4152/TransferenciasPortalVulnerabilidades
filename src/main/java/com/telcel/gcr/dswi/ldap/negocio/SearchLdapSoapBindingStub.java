/**
 * SearchLdapSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.telcel.gcr.dswi.ldap.negocio;

import com.telcel.portal.util.ConstantesLdap;
import com.telcel.portal.util.ConstantesNumeros;

public class SearchLdapSoapBindingStub extends org.apache.axis.client.Stub implements com.telcel.gcr.dswi.ldap.negocio.SearchLdap {

    public static org.apache.axis.description.OperationDesc [] operations;

    static {
        operations = new org.apache.axis.description.OperationDesc[ConstantesNumeros.CINCO];
        initOperationDesc1();
    }

    private static void initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscaJefeInmediato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.IDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.PASSWORDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.NUMEMP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "buscaJefeInmediatoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscaGerente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.IDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.PASSWORDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.NUMEMP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "buscaGerenteReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscaSubdirector");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.IDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.PASSWORDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.NUMEMP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "buscaSubdirectorReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscaDirector");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.IDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.PASSWORDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.NUMEMP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "buscaDirectorReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[ConstantesNumeros.TRES] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("buscaEmpleado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.IDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.PASSWORDAPP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", ConstantesLdap.NUMEMP), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(ConstantesLdap.URLSCHEMA, ConstantesLdap.STRING));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "buscaEmpleadoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[ConstantesNumeros.CUATRO] = oper;

    }

    public SearchLdapSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SearchLdapSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SearchLdapSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call call = super._createCall();
            if (super.maintainSessionSet) {
                call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                call.setProperty(key, super.cachedProperties.get(key));
            }
            return call;
        }
        catch (java.lang.Throwable t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", t);
        }
    }

    public java.lang.String buscaJefeInmediato(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call call = createCall();
        call.setOperation(operations[0]);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI("buscaJefeInmediato");
        call.setEncodingStyle(null);
        call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        call.setOperationName(new javax.xml.namespace.QName(ConstantesLdap.URLNEGOCIO, "buscaJefeInmediato"));

        setRequestHeaders(call);
        setAttachments(call);
      
			 java.lang.Object resp = call.invoke(new java.lang.Object[] {idApp, passwordApp, numeroDeEmpleado});
		
		        if (resp instanceof java.rmi.RemoteException) {
		            throw (java.rmi.RemoteException)resp;
		        }
		        else {
		            extractAttachments(call);
		            try {
		                return (java.lang.String) resp;
		            } catch (java.lang.Exception exception) {
		                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(resp, java.lang.String.class);
		            }
		        }
    }

    public java.lang.String buscaGerente(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call call = createCall();
        call.setOperation(operations[1]);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI("buscaGerente");
        call.setEncodingStyle(null);
        call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        call.setOperationName(new javax.xml.namespace.QName(ConstantesLdap.URLNEGOCIO, "buscaGerente"));

        setRequestHeaders(call);
        setAttachments(call);     
		java.lang.Object resp = call.invoke(new java.lang.Object[] {idApp, passwordApp, numeroDeEmpleado});
					
		if (resp instanceof java.rmi.RemoteException) {
					            throw (java.rmi.RemoteException)resp;
		}else {
            extractAttachments(call);
            try {
                return (java.lang.String) resp;
            } catch (java.lang.Exception exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(resp, java.lang.String.class);
            }
        }			        
	
    }

    public java.lang.String buscaSubdirector(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call call = createCall();
        call.setOperation(operations[2]);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI("buscaSubdirector");
        call.setEncodingStyle(null);
        call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        call.setOperationName(new javax.xml.namespace.QName(ConstantesLdap.URLNEGOCIO, "buscaSubdirector"));

        setRequestHeaders(call);
        setAttachments(call);
       
	 	java.lang.Object resp = call.invoke(new java.lang.Object[] {idApp, passwordApp, numeroDeEmpleado});

        if (resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)resp;
        }
        else {
            extractAttachments(call);
            try {
                return (java.lang.String) resp;
            } catch (java.lang.Exception exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(resp, java.lang.String.class);
            }
        }
 
    }

    public java.lang.String buscaDirector(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call call = createCall();
        call.setOperation(operations[ConstantesNumeros.TRES]);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI("buscaDirector");
        call.setEncodingStyle(null);
        call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        call.setOperationName(new javax.xml.namespace.QName(ConstantesLdap.URLNEGOCIO, "buscaDirector"));

        setRequestHeaders(call);
        setAttachments(call);
     
	 	java.lang.Object resp = call.invoke(new java.lang.Object[] {idApp, passwordApp, numeroDeEmpleado});

        if (resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)resp;
        }
        else {
            extractAttachments(call);
            try {
                return (java.lang.String) resp;
            } catch (java.lang.Exception exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(resp, java.lang.String.class);
            }
        }
  
    }

    public java.lang.String buscaEmpleado(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call call = createCall();
        call.setOperation(operations[ConstantesNumeros.CUATRO]);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI("buscaEmpleado");
        call.setEncodingStyle(null);
        call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        call.setOperationName(new javax.xml.namespace.QName(ConstantesLdap.URLNEGOCIO, "buscaEmpleado"));

        setRequestHeaders(call);
        setAttachments(call);
       
	 	java.lang.Object resp = call.invoke(new java.lang.Object[] {idApp, passwordApp, numeroDeEmpleado});

        if (resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)resp;
        }
        else {
            extractAttachments(call);
            try {
                return (java.lang.String) resp;
            } catch (java.lang.Exception exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(resp, java.lang.String.class);
            }
        }

    }

}
