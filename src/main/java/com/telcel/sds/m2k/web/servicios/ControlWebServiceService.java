/**
 * ControlWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.telcel.sds.m2k.web.servicios;

public interface ControlWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getControlWebServiceAddress();

    public com.telcel.sds.m2k.web.servicios.ControlWebService getControlWebService() throws javax.xml.rpc.ServiceException;

    public com.telcel.sds.m2k.web.servicios.ControlWebService getControlWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
