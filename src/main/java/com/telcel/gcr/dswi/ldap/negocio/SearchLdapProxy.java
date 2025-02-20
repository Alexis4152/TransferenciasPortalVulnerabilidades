package com.telcel.gcr.dswi.ldap.negocio;

public class SearchLdapProxy implements com.telcel.gcr.dswi.ldap.negocio.SearchLdap {
  private String endpoint = null;
  private com.telcel.gcr.dswi.ldap.negocio.SearchLdap searchLdap = null;
  
  public SearchLdapProxy() {
    initSearchLdapProxy();
  }
  
  private void initSearchLdapProxy() {
    try {
      searchLdap = (new com.telcel.gcr.dswi.ldap.negocio.SearchLdapServiceLocator()).getSearchLdap();
      if (searchLdap != null) {
        if (endpoint != null){
          ((javax.xml.rpc.Stub)searchLdap)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
        }
        else{
          endpoint = (String)((javax.xml.rpc.Stub)searchLdap)._getProperty("javax.xml.rpc.service.endpoint.address");
        }
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
    if (searchLdap != null){
      ((javax.xml.rpc.Stub)searchLdap)._setProperty("javax.xml.rpc.service.endpoint.address", endpoint);
    }
    
  }
  
  public com.telcel.gcr.dswi.ldap.negocio.SearchLdap getSearchLdap() {
    if (searchLdap == null){
      initSearchLdapProxy();
    }
    return searchLdap;
  }
  
  public java.lang.String buscaJefeInmediato(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException{
    if (searchLdap == null){
      initSearchLdapProxy();
    }
    return searchLdap.buscaJefeInmediato(idApp, passwordApp, numeroDeEmpleado);
  }
  
  public java.lang.String buscaGerente(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException{
    if (searchLdap == null){
      initSearchLdapProxy();
    }
    return searchLdap.buscaGerente(idApp, passwordApp, numeroDeEmpleado);
  }
  
  public java.lang.String buscaSubdirector(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException{
    if (searchLdap == null){
      initSearchLdapProxy();
    }
    return searchLdap.buscaSubdirector(idApp, passwordApp, numeroDeEmpleado);
  }
  
  public java.lang.String buscaDirector(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException{
    if (searchLdap == null){
      initSearchLdapProxy();
    }
    return searchLdap.buscaDirector(idApp, passwordApp, numeroDeEmpleado);
  }
  
  public java.lang.String buscaEmpleado(java.lang.String idApp, java.lang.String passwordApp, java.lang.String numeroDeEmpleado) throws java.rmi.RemoteException{
    if (searchLdap == null){
      initSearchLdapProxy();
    }
    return searchLdap.buscaEmpleado(idApp, passwordApp, numeroDeEmpleado);
  }
  
  
}