package com.telcel.portal.util;

/**
 * 
 */

import java.util.Properties;

/**
 * Clase que obtiene las relaciones entre aplicacion, base de datos y jndi
 * @author everis
 * @version 1.0
 */
public class ConnectionParams {

	private int connectionType;
	private String application;
	private String connectionInfix;
	private String jndiConnection;
	private String region;
	private String dbName;
	
	private static final String APLTIC = "apl.TIC";
	private static final String APLMOBILE1 = "apl.MOBILE1";
	private static final String R09 = "R09";
	
    private PropertiesFiles prop = new PropertiesFiles();
    private Properties p = prop.getPropertiesConf();
	
	
    public ConnectionParams(String application,String region){
    	this.application = application;
    	this.region = region;
    }
    
    
	/**
	 * @return the connectionType
	 */
	public int getConnectionType() {
		this.connectionType = getConnectionType(this.application);
		return connectionType;
	}
	/**
	 * @param connectionType the connectionType to set
	 */
	public void setConnectionType(int connectionType) {
		this.connectionType = connectionType;
	}
	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}
	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the connectionInfix
	 */
	public String getConnectionInfix() {
		this.connectionInfix = getInfix();
		return connectionInfix;
	}


	/**
	 * @param connectionInfix the connectionInfix to set
	 */
	public void setConnectionInfix(String connectionInfix) {
		this.connectionInfix = connectionInfix;
	}


	/**
	 * @return the jndiConnection
	 */
	public String getJndiConnection() {
		this.jndiConnection = getJndi();
		return jndiConnection;
	}
	/**
	 * @param jndiConnection the jndiConnection to set
	 */
	public void setJndiConnection(String jndiConnection) {
		this.jndiConnection = jndiConnection;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}


	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}


	/**
	 * @return the dbName
	 */
	public String getDbName() {
		this.dbName = getNombreBaseDatos();
		return dbName;
	}


	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}


	/**
	 * Obtiene el tipo de base de datos a la cual conectarse por medio de
	 * la aplicacion que se envia
	 * 
	 * @param application:
	 * 				Nombre de la aplicacion a la cual se va a conectar.
	 * @return type:
	 * 				Tipo de base de datos a la cual se va a conectar 
	 * 				1-Oracle
	 * 				2-BD2
	 */
	private int getConnectionType(String application){
		int type = 0;
		//Corresponde a conexiones oracle
		if(application.equals(p.getProperty(this.APLTIC))){
			type = 1;
		}else //Corresponde a conexiones DB2 
			if(application.equals(p.getProperty(this.APLMOBILE1))){
			type = 2;
		}
		
		return type;
	}
	
	/**
	 * Regresa el tipo de conexion a la base de datos especifica
	 * @return String:
	 * 				infix.ora1=ORA
	 *				infix.mobile1=corporativo
	 */
	private String getInfix(){
		String infix = "";
		
		if(application.equals(p.getProperty(this.APLTIC))){
			infix = p.getProperty("infix.ora1");
		}else  if(application.equals(p.getProperty(this.APLMOBILE1))){
			infix = p.getProperty("infix.mobile1");
		}
		return infix;
	}
	
	/**
	 * Obtiene el nombre de la base de datos dependiendo del tipo de conexion la aplicacion y la region 
	 * @return String:
	 * 				Nombre de base de datos 
	 * 				MBGW01
	 * 				DB2PROD
	 * 				DB22PRD
	 */
	private String getNombreBaseDatos(){
		String nombre = "";
		
		if(application.equals(p.getProperty(this.APLTIC))){
			nombre = p.getProperty("db.name.ora1");
		}
		if(application.equals(p.getProperty(this.APLMOBILE1))){
			if(region.equals("R01") || region.equals("R02") || region.equals("R03")){
				nombre = p.getProperty("db.name.db22");
			}else if( region.equals("R06") || region.equals("R07") || region.equals("R08")){
				nombre = p.getProperty("db.name.db22");
			}else if(region.equals("R04") || region.equals("R05") || region.equals(this.R09) || region.equals("")){
				nombre = p.getProperty("db.name.db21");
			}else if(region.equals("R00")){
				nombre = p.getProperty("db.name.db21");
			}
		}
		
		return nombre;
	}
	
	/**
	 * Regresa el jndi de acuerdo a la aplicacion que se solicite dependiendo
	 * del infix por medio del cual se desea conectar.
	 * #INFIX-CONNECTION
	 * infix.ora1=ORA
	 * infix.mobile1=corporativo
	 * @return String 
	 * 			SIPAB (MBGW01)
	 * 			MOBILE1 (DB2PROD, DB22PRD)
	 */
	private String getJndi(){
		String jndi = "";
		StringBuffer buf = new StringBuffer();
		buf.append(getRegion(this.region));
		buf.append(".jndi.");
		if(application.equals(p.getProperty(this.APLTIC))){
			buf.append(getInfix());
			buf.append(".");
			buf.append(p.getProperty(this.APLTIC));
			jndi = buf.toString();
		}
		if(application.equals(p.getProperty(this.APLMOBILE1))){
			buf.append(getInfix());
			buf.append(".");
			buf.append(p.getProperty(this.APLMOBILE1));
			jndi = buf.toString();
		}
		
		return p.getProperty(jndi);
	}
	
	
	private String getRegion(String region){
		String reg = "";
		if(region == null){
			reg = this.R09;
		}else if(region.equals("")){
			reg = this.R09;
		}else if(region.equals("R00")){
			reg = this.R09;
		}else {
			reg = region;
		}
		
		return reg;
	}
	
}
