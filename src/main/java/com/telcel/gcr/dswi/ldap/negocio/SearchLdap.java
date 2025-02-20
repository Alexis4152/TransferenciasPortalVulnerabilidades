/**
 * SearchLdap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.telcel.gcr.dswi.ldap.negocio;

public interface SearchLdap extends java.rmi.Remote {
   java.lang.String buscaJefeInmediato(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException;
   java.lang.String buscaGerente(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException;
   java.lang.String buscaSubdirector(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException;
   java.lang.String buscaDirector(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException;
   java.lang.String buscaEmpleado(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException;
}
