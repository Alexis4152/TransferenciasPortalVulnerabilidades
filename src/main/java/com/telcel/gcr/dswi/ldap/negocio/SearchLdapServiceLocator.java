/**
 * SearchLdapServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.telcel.gcr.dswi.ldap.negocio;

import com.telcel.portal.util.ConstantesLdap;

public class SearchLdapServiceLocator extends org.apache.axis.client.Service implements com.telcel.gcr.dswi.ldap.negocio.SearchLdapService {

    public SearchLdapServiceLocator() {
    }


    public SearchLdapServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SearchLdapServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SearchLdap
    private java.lang.String searchLdapAddress = "http://serviciosidentidad.telcel.com:8000/DswiSearchLdap/services/SearchLdap";

    public java.lang.String getSearchLdapAddress() {
        return searchLdapAddress;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String searchLdapWSDDServiceName = ConstantesLdap.SEARCHLDAP;

    public java.lang.String getSearchLdapWSDDServiceName() {
        return searchLdapWSDDServiceName;
    }

    public void setSearchLdapWSDDServiceName(java.lang.String name) {
    	searchLdapWSDDServiceName = name;
    }

    public com.telcel.gcr.dswi.ldap.negocio.SearchLdap getSearchLdap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(searchLdapAddress);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSearchLdap(endpoint);
    }

    public com.telcel.gcr.dswi.ldap.negocio.SearchLdap getSearchLdap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.telcel.gcr.dswi.ldap.negocio.SearchLdapSoapBindingStub stub = new com.telcel.gcr.dswi.ldap.negocio.SearchLdapSoapBindingStub(portAddress, this);
            stub.setPortName(getSearchLdapWSDDServiceName());
            return stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSearchLdapEndpointAddress(java.lang.String address) {
    	searchLdapAddress = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.telcel.gcr.dswi.ldap.negocio.SearchLdap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.telcel.gcr.dswi.ldap.negocio.SearchLdapSoapBindingStub stub = new com.telcel.gcr.dswi.ldap.negocio.SearchLdapSoapBindingStub(new java.net.URL(searchLdapAddress), this);
                stub.setPortName(getSearchLdapWSDDServiceName());
                return stub;
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
        if (ConstantesLdap.SEARCHLDAP.equals(inputPortName)) {
            return getSearchLdap();
        }
        else  {
            java.rmi.Remote stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) stub).setPortName(portName);
            return stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://negocio.ldap.dswi.gcr.telcel.com", "SearchLdapService");
    }

    private java.util.Set ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://negocio.ldap.dswi.gcr.telcel.com", ConstantesLdap.SEARCHLDAP));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if (ConstantesLdap.SEARCHLDAP.equals(portName)) {
            setSearchLdapEndpointAddress(address);
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
