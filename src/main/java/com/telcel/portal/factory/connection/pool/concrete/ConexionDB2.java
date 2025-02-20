/**
 * 
 */
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
 * Realiza la conexion a base de datos DB2
 * @author everis 04/08/2012
 * @version 1.0
 *
 */
public class ConexionDB2 {

	private ConnectionParams paramCon;
	private static Logger  logger = Logger.getLogger(ConexionDB2.class);
	private static final int MIL = 1000;
	
	public ConexionDB2(ConnectionParams paramCon){
		this.paramCon = paramCon;
	}
	/**
	 * Obtiene la conexion de DB2 para cada una de las regiones, por medio 
	 * de los jndi asignados. 
	 * (9),(4 y 5) y (1,2,3,6,7,8)
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws Exception {
		Connection condb2 = null;
		logger.info(" - Conexion a DB2 para region "+paramCon.getRegion());
		condb2 = getConnectionDB2(paramCon.getJndiConnection());
		return condb2;
	}
	
	/**
	 * Metodo que obtiene la base de datos DB2 por medio de jndi
	 * @param jndi
	 * 			jndi por medio del cual se va a conectar a la base de datos DB2
	 * @param dbName
	 * 			Nombre de la base de datos a la cual se conecta
	 * @return Connection
	 * 			Regresa un objeto de tipo connecction
	 * @throws Exception
	 */
	private Connection getConnectionDB2(String jndi) throws Exception {
		
		long tiempoTotal 			= 0;
	    final Date inicioEjecucion	= Calendar.getInstance().getTime();
	    
		InitialContext ctx		= null;
		DataSource ds			= null;
	    Connection connection	= null;
	    try {
	        ctx = new InitialContext();
	        ds = (DataSource)ctx.lookup("java:comp/env/"+jndi);
	        logger.info(" - START Connection Data Base DB2: "+paramCon.getDbName());
	        connection = (Connection) ds.getConnection();
	    } catch (NamingException e) {
	    	logger.info(" - Unable to create connection: "+paramCon.getDbName()+": END Connection. - CAUSA: " + (e.getMessage()!=null?e.getMessage():"NamingException"));
	        e.printStackTrace();
	    } catch (SQLException sql) {
	    	logger.info(" - ERROR: Unable to create connection: "+paramCon.getDbName()+": END Connection - CAUSA: " + (sql.getMessage()!=null?sql.getMessage():"SQLException"));
	        sql.printStackTrace();	    	
	    }
		final Date finEjecucion = Calendar.getInstance().getTime();
		tiempoTotal = (finEjecucion.getTime() - inicioEjecucion.getTime());
		logger.info(" - END: "+paramCon.getDbName()+": - Tiempo total: " + ((tiempoTotal) < MIL ? tiempoTotal + "ms." : ((double)tiempoTotal/MIL) + "s."));		
	    return connection;
	}
	
}
