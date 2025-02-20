package com.telcel.portal.seguridad;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.telcel.gcr.dswi.ldap.negocio.SearchLdapProxy;
import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.util.ConstantesNumeros;

public class DatosUsuario {

	private String mensaje;
	private static Logger  logger = Logger.getLogger(GeneralDAO.class);
	
	public EmpleadoPojo obtenerIdentidad(String numeroEmpleado) {
		logger.info("--- Busca Identidad: "  + numeroEmpleado);
		EmpleadoPojo usuario = null;
		try {
									
			SearchLdapProxy	nom = new SearchLdapProxy();
			nom.setEndpoint("http://serviciosidentidad.telcel.com:8000/DswiSearchLdap/services/SearchLdap");
			
			String res = nom. buscaEmpleado ("00410102004","T1cFer2015",numeroEmpleado.trim());
			logger.info("-->> res: "+res); 
			
			List<String> dataUsuario = formatRes(res);
			
			if (dataUsuario!=null) {							
				usuario = new EmpleadoPojo();
				usuario.setNumeroEmpleado(dataUsuario.get(1));
				usuario.setNombre(dataUsuario.get(2));
				usuario.setUsuario(dataUsuario.get(ConstantesNumeros.VEINTE));
				usuario.setPuesto(dataUsuario.get(ConstantesNumeros.DIECISIETE));
				
				String resJefe = nom.buscaJefeInmediato("00410102004","T1cFer2015", numeroEmpleado.trim());
				logger.info("-->> resJefe: "+resJefe); 
				List<String> dataJefe = formatRes(resJefe);
				if (dataJefe != null) {
					usuario.setUsuarioJefeDirecto(dataJefe.get(ConstantesNumeros.VEINTE));
				} else {
					usuario.setUsuarioJefeDirecto("");
				}
				logger.info(" jefe: " + usuario.getUsuarioJefeDirecto());
			} 
			
		} catch( Exception e) {
			usuario = null;
			this.mensaje = "OCURRIO UN PROBLEMA AL OBTENER LA IDENTIDAD DEL USUARIO, FAVOR DE VOLVER A INTENTARLO. " + e.getMessage();
			logger.debug(e.getMessage(), e);
		}
		return usuario;		
	}
	
	/**
	 * Formate la respuesta
	 * @param res
	 * @return
	 */
	private List<String> formatRes(String res) {
		
		ArrayList<String> arregloCuenta= null;
		
			if (res.equals("1")) {
				this.mensaje = "Usuario no registrado en los servicios de Identidad de Telcel";
			} else if (res.equals("2")) {
				this.mensaje = "No se encontro la informacion del usuario solicitado";
			} else if (res.equals("3")) {
				this.mensaje = "No se identifico al empleado jerarquico solicitado";
			} else if (res.equals("4")) {
				this.mensaje = "No se encontro al empleado jerarquico solicitado";				
			} else if (res.equals("5")) {
				this.mensaje = "El nivel jerarquico a buscar es el mismo empleado";
			} else if (res.equals("6")) {
				this.mensaje = "El nivel jerarquico a buscar no aplica";
			} else if (res.equals("-1")) {
				this.mensaje = "Error de Ejecucion";
			} else if (res.equals("-2")) {
				this.mensaje = "Error de Conexion";
			} else if (res.equals("-3")) {
				this.mensaje = "Error al validar permisos de uso";
			} else if (res.equals("-4")) {
				this.mensaje = "Permiso denegado ";
			} else {
			
				arregloCuenta=new ArrayList<String>();
				String respuesta = res;
				int ind = respuesta.indexOf("||");
				while( ind > 0) {
					respuesta = respuesta.substring(0,ind) + "| |" + respuesta.substring(ind+2);
					ind = respuesta.indexOf("||");
				}
	
				StringTokenizer stoken = new StringTokenizer(respuesta,"|");							
				while (stoken.hasMoreElements()) {
					String aux = (String)stoken.nextElement();
					arregloCuenta.add(aux);
				}
		
		}
		return arregloCuenta;
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
}
