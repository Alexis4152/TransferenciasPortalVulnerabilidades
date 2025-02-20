/**
 * ControlWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.telcel.sds.m2k.web.servicios;

public class ControlWebServiceServiceLocator extends org.apache.axis.client.Service implements com.telcel.sds.m2k.web.servicios.ControlWebServiceService {

    public ControlWebServiceServiceLocator() {
    }


    public ControlWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ControlWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ControlWebService
    private java.lang.String ControlWebService_address = "http://serviciosm2k.telcel.com:80/telcel-ws-web/services/ControlWebService";
   
    public java.lang.String getControlWebServiceAddress() {
        return ControlWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ControlWebServiceWSDDServiceName = "ControlWebService";

    public java.lang.String getControlWebServiceWSDDServiceName() {
        return ControlWebServiceWSDDServiceName;
    }

    public void setControlWebServiceWSDDServiceName(java.lang.String name) {
        ControlWebServiceWSDDServiceName = name;
    }

    public com.telcel.sds.m2k.web.servicios.ControlWebService getControlWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ControlWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getControlWebService(endpoint);
    }

    public com.telcel.sds.m2k.web.servicios.ControlWebService getControlWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.telcel.sds.m2k.web.servicios.ControlWebServiceSoapBindingStub _stub = new com.telcel.sds.m2k.web.servicios.ControlWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getControlWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setControlWebServiceEndpointAddress(java.lang.String address) {
        ControlWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.telcel.sds.m2k.web.servicios.ControlWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.telcel.sds.m2k.web.servicios.ControlWebServiceSoapBindingStub _stub = new com.telcel.sds.m2k.web.servicios.ControlWebServiceSoapBindingStub(new java.net.URL(ControlWebService_address), this);
                _stub.setPortName(getControlWebServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ControlWebService".equals(inputPortName)) {
            return getControlWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servicios.web.m2k.sds.telcel.com", "ControlWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servicios.web.m2k.sds.telcel.com", "ControlWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ControlWebService".equals(portName)) {
            setControlWebServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
