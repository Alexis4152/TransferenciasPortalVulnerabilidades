
package com.telcel.portal.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;



/**
 * Clase que se encarga de configurar el log4j 
 * @author everis 05/08/2012
 * @version 1.0
 *
 */
public class Log4JServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException{

	      String log4jfile = getInitParameter("log4j-init-file");
	      if (log4jfile != null) {
	         String propfile = getServletContext().getRealPath(log4jfile);
	         PropertyConfigurator.configure(propfile);
	      }
	   }

}
