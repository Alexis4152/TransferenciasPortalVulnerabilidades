package com.telcel.portal.factory.connection.pool;

/**
 * 
 */

import java.sql.Connection;


/**
 * @author everis 04/09/2012
 * @version 1.0
 *
 */
public interface IConnectionFactory {

	/**
	 * Crea un aconexion hacia la base de datos configurada en el pool de conexiones del servidor
	 * para una region especifica
	 * @param tipoConexion
	 * @param region
	 * @return Connection
	 * @throws SQLException
	 */
	Connection crearConexion (String application, String region) throws Exception;
	
	/**
	 * Cierra una conexion establecida hacia la base de datos actual
	 * @param conexion
	 * @throws SQLException
	 */
	void cerrarConexion ();
	
}

