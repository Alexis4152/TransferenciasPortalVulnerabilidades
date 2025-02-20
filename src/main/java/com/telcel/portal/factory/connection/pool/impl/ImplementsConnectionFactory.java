package com.telcel.portal.factory.connection.pool.impl;

/**
 * 
 */


import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.telcel.portal.factory.connection.pool.IConnectionFactory;
import com.telcel.portal.factory.connection.pool.concrete.ConexionDB2;
import com.telcel.portal.factory.connection.pool.concrete.ConexionOracle;
import com.telcel.portal.util.ConnectionParams;


/**
 * @author everis 04/08/2012
 * @version 1.0
 * 
 * Implementacion de la fabrica de conexiones, se encarga de crear conexiones a diferentes tipos de 
 * bases de datos creando productos concretos para cada tipo de base de datos
 */
public class ImplementsConnectionFactory implements IConnectionFactory {

  
	private static final  int ORA_TYPE		= 1;
	private static final  int DB2_TYPE		= 2;
	
	private static Logger  logger = Logger.getLogger(ImplementsConnectionFactory.class);
	private Connection conn = null;
	
	public ImplementsConnectionFactory(){

	}
	/* (non-Javadoc)
	 * @see com.telcel.factory.conection.pool.IConnectionFactory#cerrarConexion(java.sql.Connection)
	 */
	public void cerrarConexion() {
		try {
			if (this.conn != null){ 
				this.conn.close();
			}
		} 
		catch(SQLException e){
			logger.debug(e.getMessage(), e);
			logger.info(" - ERROR al cerrar la Conexion - CAUSA: " + (e.getMessage()!=null?e.getMessage():"CONNECTION"));
		}
	}

	/* (non-Javadoc)
	 * @see com.telcel.factory.conection.pool.IConnectionFactory#crearConexion(java.lang.Integer)
	 */
	public Connection crearConexion(String application, String region) throws Exception {
		ConnectionParams paramConn = new ConnectionParams(application.trim(),(region==null?"":region.trim()));
		switch(paramConn.getConnectionType()){
		case ORA_TYPE:
			logger.info(" - Solicita Conexion a Oracle - Region: " + (region==null?"":region));
			ConexionOracle connOra = crearConexionOracle(paramConn);
			conn = connOra.getConnection();
			logger.info(" - Finaliza solicitud de conexion a Oracle - Region: " + (region==null?"":region));
			break;
		case DB2_TYPE:
			logger.info(" - Solicita Conexion DB2 - Region: " + (region==null?"":region));
			ConexionDB2 connDb2 = crearConexionDB2(paramConn);
			conn = connDb2.getConnection();
			logger.info(" - Finaliza solicitud de conexion a DB2 - Region: " + (region==null?"":region));
			break;
		default:
			logger.info(" - Tipo de Base de datos no definido");
			conn= null;
		}
		return conn;
	}

	/**
	 * Crea una conexion oracle para la region especificada
	 * @param region
	 * @return ConexionOracle
	 * @throws SQLException
	 */
	private ConexionOracle crearConexionOracle(ConnectionParams paramCon) throws SQLException {
		return new ConexionOracle(paramCon);
	}
	
	/**
	 * Crea una conexion DB2 para la region especificada
	 * (9),(4 y 5) y (1,2,3,6,7,8)
	 * @param region
	 * @return ConexionDB2
	 * @throws SQLException
	 */
	private ConexionDB2 crearConexionDB2(ConnectionParams paramCon)  throws SQLException {
		return new ConexionDB2(paramCon);
	}
	
}

