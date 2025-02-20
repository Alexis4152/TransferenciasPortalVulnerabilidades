package com.telcel.portal.factory.connection.pool.concrete;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

import com.telcel.portal.util.ConnectionParams;


/**
 * Clase encargada de crear una conexion a base de datos Oracle por medio de jndi
 * @author everis 05/08/2012
 * @version 1.0
 *
 */
public class ConexionOracle {

	private ConnectionParams paramCon;
	private static Logger  logger = Logger.getLogger(ConexionOracle.class);
	private static final int MIL = 1000;
	
	public ConexionOracle(ConnectionParams paramCon) {
		this.paramCon = paramCon;
	}

	/**
	 * Metodo que devuelve la conexion de la base de datos Oracle
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		Connection conora = null;
		logger.info(" - Conexion a Oracle para region "+paramCon.getRegion());
		conora = getConnectionOracle(paramCon.getJndiConnection());
		return conora;
	}
	
	/**
	 * Metodo que obtiene la conexion a base de datos Oracle por medio de jndi
	 * @param jndi
	 * 			SIPAB,TAXI
	 * @param dbName
	 * 			Nombre de la base de datos a la cual se va a conectar
	 * @return Connection
	 * 			Objeto que regresa la connection
	 * @throws Exception
	 */
	private Connection getConnectionOracle(String jndi) throws Exception{
		
		long tiempoTotal 			= 0;
	    final Date inicioEjecucion	= Calendar.getInstance().getTime();
	    
		InitialContext ctx = null;
	    DataSource ds = null;
	    Connection connection = null;
	    try {
	        ctx = new InitialContext();
	        ds = (DataSource)ctx.lookup("java:comp/env/"+jndi);
	        logger.info(" - START: Connection Data Base Oracle: "+paramCon.getDbName());
	        connection = ds.getConnection();
	    } catch (NamingException e) {
	    	logger.info(" - ERROR: Unable to create connection: "+paramCon.getDbName()+": END Connection - CAUSA: " + (e.getMessage()!=null?e.getMessage():"NamingException"));
	        e.printStackTrace();
	    } catch (SQLException sql) {
	    	logger.info(" - ERROR: Unable to create connection: "+paramCon.getDbName()+": END Connection - CAUSA: " + (sql.getMessage()!=null?sql.getMessage():"SQLException"));
	        sql.printStackTrace();	    	
	    }
		final Date finEjecucion = Calendar.getInstance().getTime();
		tiempoTotal = (finEjecucion.getTime() - inicioEjecucion.getTime());
		logger.info(" - END: "+paramCon.getDbName()+" - Tiempo total: " + ((tiempoTotal) < MIL ? tiempoTotal + "ms." : ((double)tiempoTotal/MIL) + "s."));
	    return connection;
	}
}
