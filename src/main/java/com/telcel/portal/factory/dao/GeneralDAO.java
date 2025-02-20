package com.telcel.portal.factory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.sql.rowset.WebRowSet;
import com.sun.rowset.WebRowSetImpl;

import oracle.jdbc.rowset.OracleWebRowSet;

import org.apache.log4j.Logger;

//import com.ibm.db2.jcc.b.DisconnectException;
import com.ibm.db2.jcc.am.DisconnectNonTransientException;
import com.telcel.portal.factory.connection.pool.IConnectionFactory;
import com.telcel.portal.factory.connection.pool.impl.ImplementsConnectionFactory;
import com.telcel.portal.factory.vo.ParametrosVo;
import com.telcel.portal.lector.ILectorSentenceSQL;
import com.telcel.portal.lector.impl.LectorSentenceXML;
import com.telcel.portal.util.PropertiesFiles;

/**
 * @author everis 12/09/2012
 * @version 1.0
 *
 */
public class GeneralDAO {

	private static Logger  logger = Logger.getLogger(GeneralDAO.class);
    private PropertiesFiles prop = new PropertiesFiles();
    private Properties p = prop.getPropertiesConf();
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet result;
	private Integer reconexionCount = 0;
	private Integer modulo;
	
	private static final int MIL = 1000;
	
	
	public GeneralDAO() {}
	
	public GeneralDAO(Integer modulo){
		this.modulo = modulo;
	}


	/**
	 * Metodo encargado de cerrar conexiones de base de datos
	 */
	public void cerrarConexion(){
		try {
			if (result != null){
				if(!result.isClosed()){
					result.close();
				}
			}
		} 
		catch(SQLException e){
						logger.debug(e.getMessage(), e);
						logger.info(" - ERROR al cerrar el resultset - CAUSA: " + (e.getMessage()!=null?e.getMessage():"RESULTSET"));
		}
		try {
			if (pstmt != null){
				if(!pstmt.isClosed()){
					pstmt.close();
				}
			}
		} 
		catch(SQLException e){
						logger.debug(e.getMessage(), e);
						logger.info(" - ERROR al cerrar PreparedStatement - CAUSA: " + (e.getMessage()!=null?e.getMessage():"STATEMENT"));
		}
		try {
			if (conn != null){
				if(!conn.isClosed()){
					conn.close();
				}
			} 
		}
		catch(SQLException e){
						logger.debug(e.getMessage(), e);
						logger.info(" - ERROR al cerrar la Conexion - CAUSA: " + (e.getMessage()!=null?e.getMessage():"CONNECTION"));
		}

	}
	
	
	public WebRowSet selectSql(ParametrosVo params, String application) throws Exception {
		IConnectionFactory factory	= new ImplementsConnectionFactory();
		ILectorSentenceSQL lector	= new LectorSentenceXML();
		String msgError				= "";
		long tiempoTotal 			= 0;
	    final Date inicioEjecucion	= Calendar.getInstance().getTime();
	    
		conn		= null;
		pstmt		= null;
		result		= null;
		Statement stmt = null;
		WebRowSet webRowSet 	= null;
		Boolean error = false;
		Random ran=new Random(Calendar.getInstance().getTimeInMillis());
		int idHilo=ran.nextInt();
		
		logger.info(" - >>> Inicia consulta");
		
		try{
			
			if(lector.getSentenceSql(params, this.modulo)==null?false:lector.getSentenceSql(params, this.modulo).equals("")?false:true){
				logger.info("Creando conexion de hilo:" + idHilo);
				conn	=	factory.crearConexion(application, params.getRegion());
				if(application.equals(p.getProperty("apl.MOBILE1"))){
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				webRowSet =  new WebRowSetImpl();
				}else{
				stmt = conn.createStatement();
				webRowSet = new OracleWebRowSet();
				}
				int time = Integer.valueOf(p.getProperty("Time.Out.statement").trim());
				logger.debug("Time Out statement: "+time);
				logger.debug("Starting timer.");
		    	result	=	stmt.executeQuery(lector.getSentenceSql(params, this.modulo).toString());
				logger.debug("Stoping timer.");
				webRowSet.populate(result);
				
				
			} else {
				logger.info(" - ERROR: El identificador del query no se ha encontrado en el archivo");
				throw new Exception(" - ERROR: IdQuery "+params.getNameQuery()+" no encontrado");
			}
		}catch(DisconnectNonTransientException ed){
			logger.error(ed);
			error = true;
			cerrarConexion();
			logger.info( " ERROR Cerrado conexion de Hilo: " + idHilo+ ":dentro de DisconnectException:" );

			if(reconexionCount==2){
				throw new Exception("ERROR AL HACER LA CONEXION CON LA BASE DE DATOS");
			}
			msgError = (ed.getMessage()!=null?ed.getMessage():ed.toString());
		}catch(Exception e){
			error = true;
			cerrarConexion();
			logger.info( " Cerrado conexion de Hilo: " + idHilo+ ":dentro de Exception:" );
			logger.error(e);
			if(reconexionCount==2){
				throw new Exception("ERROR AL HACER LA CONEXION CON LA BASE DE DATOS");
			}
			msgError = (e.getMessage()!=null?e.getMessage():e.toString());
		} finally {
			
			if(error){
				final Date finEjecucion = Calendar.getInstance().getTime();
				tiempoTotal = (finEjecucion.getTime() - inicioEjecucion.getTime());
				logger.info(" - ERROR al conectarse a la base de datos - CAUSA: " + msgError);
				logger.info(" ->>> Conexion corrompida - Tiempo total: " + ((tiempoTotal) < MIL ? tiempoTotal + "miliseg." : ((double)tiempoTotal/MIL) + "s."));
				
				if ((tiempoTotal>=40000) && (tiempoTotal<=60000)) {
					logger.info("Advertencia: Conexion corrompida en IdHilo: "+idHilo+" ->Consulta Interrumpida - Tiempo total: " + ((tiempoTotal) < 1000 ? tiempoTotal + "ms." : ((double)tiempoTotal/1000) + "s.")+" QUERY: "+params.getNameQuery());
				}else{	
					logger.info("ERROR: Conexion corrompida en IdHilo: "+idHilo+" ->Consulta Interrumpida - Tiempo total: " + ((tiempoTotal) < 1000 ? tiempoTotal + "ms." : ((double)tiempoTotal/1000) + "s.")+" QUERY: "+params.getNameQuery());
				}
				reconexionCount++;
				if(reconexionCount <= 2){
				logger.info(" ->>> Intentando conectarse......");
				logger.info(" ->>> Reconexion......"+reconexionCount);
				error = false;
				webRowSet = selectSql(params, application);
				}
			}else{
				cerrarConexion();
				logger.info( " Cerrado conexion de Hilo: " + idHilo+" Sin errores" );
			
			}
		}
		final Date finEjecucion = Calendar.getInstance().getTime();
		tiempoTotal = (finEjecucion.getTime() - inicioEjecucion.getTime());
		reconexionCount = 0;
		error = false;
		logger.info(" ->>> Termina Consulta Exitosa - Tiempo total: " + ((tiempoTotal) < MIL ? tiempoTotal + "mS." : ((double)tiempoTotal/MIL) + "s."));
		if ((tiempoTotal>=40000) && (tiempoTotal<=60000)) {
			logger.info("ADVERTENCIA de velocidad en Hilo: "+idHilo+"  ->Consulta Exitosa normal- Tiempo total: " + ((tiempoTotal) < 1000 ? tiempoTotal + "ms." : ((double)tiempoTotal/1000) + "s.")+" QUERY: "+params.getNameQuery());
		}	else if (tiempoTotal>60000) {
			logger.info("ERROR de velocidad en Hilo: "+idHilo+ " ->Consulta Exitosa normal- Tiempo total: " + ((tiempoTotal) < 1000 ? tiempoTotal + "ms." : ((double)tiempoTotal/1000) + "s.")+" QUERY: "+params.getNameQuery());
		}
		else{
			logger.info("Buena velocidad en Hilo: "+idHilo+" ->Consulta Exitosa normal- Tiempo total: " + ((tiempoTotal) < 1000 ? tiempoTotal + "ms." : ((double)tiempoTotal/1000) + "s.")+" QUERY: "+params.getNameQuery());
		}		
		return webRowSet;
	}
	
	public void executeSql(ParametrosVo params, String application, String folio) throws Exception {
		IConnectionFactory factory	= new ImplementsConnectionFactory();
		ILectorSentenceSQL lector	= new LectorSentenceXML();
		conn	= null;
		pstmt	= null;
		boolean isError = false;
		long tiempoTotal 			= 0;
	    final Date inicioEjecucion	= Calendar.getInstance().getTime();
		try{
			StringBuffer instruccion =null;
			instruccion = lector.getSentenceSql(params, this.modulo);
			if(instruccion==null?false:instruccion.equals("")?false:true){
				conn	=	factory.crearConexion(application, params.getRegion());
				conn.setAutoCommit(false);
				pstmt	= conn.prepareStatement(instruccion.toString());
				pstmt.execute();
			} else {
				isError = true;
				logger.info(" - ERROR: El identificador del query no se ha encontrado en el archivo");
				throw new Exception(" - ERROR: IdQuery "+params.getNameQuery()+" no encontrado");
			}
			
		} catch ( Exception e) {
			isError = true;
			logger.debug(e.getMessage(), e);			
			logger.info(" - ERROR al conectarse a la base de datos - CAUSA: " + (e.getMessage()!=null?e.getMessage():""));
			throw new Exception("ERROR AL HACER LA CONEXION CON LA BASE DE DATOS");
		} finally {
			if(!isError){
				logger.debug(" - commit....");
				conn.commit();
				cerrarConexion();
			}else{
				logger.debug(" - rollback....");
				if(conn != null){conn.rollback();}
				cerrarConexion();
			}						
			final Date finEjecucion = Calendar.getInstance().getTime();
			tiempoTotal = (finEjecucion.getTime() - inicioEjecucion.getTime());
			logger.info(" ->>> Termina Insert - Tiempo total: " + ((tiempoTotal) < MIL ? tiempoTotal + "MS." : ((double)tiempoTotal/MIL) + "s."));
		}
	}
	
	public void transactionListSql(List<ParametrosVo>  lista, String application) throws Exception {
		IConnectionFactory factory	= new ImplementsConnectionFactory();
		ILectorSentenceSQL lector	= new LectorSentenceXML();
		boolean isError = false;
		conn	= null;
		pstmt	= null;
		long tiempoTotal 			= 0;
	    final Date inicioEjecucion	= Calendar.getInstance().getTime();
		
		try{
			StringBuffer instruccion =null;
			for(ParametrosVo params:lista){
				instruccion = lector.getSentenceSql(params, this.modulo);
				if(instruccion == null?false:instruccion.equals("")?false:true){
					if(conn == null){
					conn	=	factory.crearConexion(application, params.getRegion());
					conn.setAutoCommit(false);
					}
					pstmt	= conn.prepareStatement(instruccion.toString());
					pstmt.execute();
					pstmt.close();
				} else {
					isError = true;
					logger.info(" - ERROR: IdQuery "+params.getNameQuery()+" no encontrado");
					break;
				}
			}
		} catch ( Exception e) {
			isError = true;
			logger.debug(e.getMessage(), e);			
			logger.info(" - ERROR al conectares a la base de datos - CAUSA: " + (e.getMessage()!=null?e.getMessage():""));
			throw new Exception("ERROR AL REALIZAR LA TRANSACCION HACIA LA BASE DE DATOS");
		} finally {
			if(!isError){
				logger.debug(" - commit....");
				conn.commit();
				cerrarConexion();
			}else{
				logger.debug(" - rollback....");
				if(conn != null){conn.rollback();}
				cerrarConexion();
			}
			final Date finEjecucion = Calendar.getInstance().getTime();
			tiempoTotal = (finEjecucion.getTime() - inicioEjecucion.getTime());
			logger.info(" ->>> Termina Transaccion - Tiempo total: " + ((tiempoTotal) < MIL ? tiempoTotal + "mseg." : ((double)tiempoTotal/MIL) + "s."));
		}
	}
		
	
}
