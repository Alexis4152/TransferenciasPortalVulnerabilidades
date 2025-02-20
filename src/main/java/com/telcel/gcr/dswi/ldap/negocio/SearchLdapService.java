/**
 * SearchLdapService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.telcel.gcr.dswi.ldap.negocio;

public interface SearchLdapService extends javax.xml.rpc.Service {
	java.lang.String getSearchLdapAddress();

    com.telcel.gcr.dswi.ldap.negocio.SearchLdap getSearchLdap() throws javax.xml.rpc.ServiceException;

    com.telcel.gcr.dswi.ldap.negocio.SearchLdap getSearchLdap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
