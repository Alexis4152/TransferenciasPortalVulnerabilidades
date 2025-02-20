package com.telcel.portal.util;

/**
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Clase que permite cargar los archivos de propiedades
 * @author everis 04/08/2012
 * @version 1.0
 *
 */
public class PropertiesFiles {
	
	private static Logger  logger = Logger.getLogger(PropertiesFiles.class);
	
	// CONFIGURAR RUTA PARA LOCAL
	

	
	  public Properties getPropertiesConf() {
			Properties propiedades = new Properties();
			InputStream in = null;
			try {
	            in = getClass().getResourceAsStream("/propiedades_tic.properties");
	            propiedades.load(in);
			} catch (FileNotFoundException e) {
				logger.error("No se encontro el archivo propiedades_sipab.properties.", e);
			} catch(IOException e){
				logger.error("Error al leer el archivo propiedades_sipab.properties", e);
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Bloque catch generado automaticamente
					logger.debug(e.getMessage(), e);
				}
			}
			return propiedades;
	   }
	  
	  public Properties getPropertiesMsg() {
			Properties propiedades = new Properties();
			InputStream in = null;
			try {
	            in = getClass().getResourceAsStream("/msg_sipab.properties");
	            propiedades.load(in);
	            
			} catch (FileNotFoundException e) {
				logger.error("No se encontro el archivo msg_sipab.properties.", e);
			} catch(IOException e){
				logger.error("Error al leer el archivo msg_sipab.properties", e);
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Bloque catch generado automaticamente
					logger.debug(e.getMessage(), e);
				}
			}
			return propiedades;
	   }
}
