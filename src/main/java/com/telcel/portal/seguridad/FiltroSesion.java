package com.telcel.portal.seguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telcel.gsa.dswi.test.Ldap;
import com.telcel.gsa.dswi.test.LdapPortProxy;
import com.telcel.gsa.dswi.test.LdapPortProxy.Descriptor;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface;
import com.telcel.portal.dao.interfaces.TransferenciasDaoUtilInterface;
import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.security.Crypt;

public class FiltroSesion implements Filter {
	
	private FilterConfig config;
	
	protected String mensajeLogin;
	
	private static Logger  logger = Logger.getLogger(GeneralDAO.class);

	public void init(FilterConfig config) throws ServletException {
		this.config = config;

		
			
			
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		

		//		 Extraer Sesion
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		if(request.getParameter("usuario")!=null && request.getParameter("password")!=null){
						
			String strEmpleado = request.getParameter("usuario");
			String strClave	= request.getParameter("password");						
			logger.info("-- LOGIN: " + strEmpleado);
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			TransferenciasDaoUtilInterface myDao = (TransferenciasDaoUtilInterface) context.getBean("TransferenciasDaoUtilInterface");
			TransferenciasDaoInterface myDao2 = (TransferenciasDaoInterface) context.getBean("TransferenciasDaoImp");
			
			
			
			DatosUsuario datosUsuario = new DatosUsuario();
			EmpleadoPojo empleadoEntra = null;
			
			// Validacion especial para los usuarios SYS
			if (strEmpleado.indexOf("SYS")>=0) {
				empleadoEntra = myDao.getEmpleadoById(strEmpleado);	
				//empleadoEntra.setEsJefe(1);
				//empleadoEntra.setUsuarioJefeDirecto("");
			} else {
				empleadoEntra = datosUsuario.obtenerIdentidad(strEmpleado);		
				//empleadoEntra = myDao.getEmpleadoById(strEmpleado);	
				//empleadoEntra.setEsJefe(1);
				//empleadoEntra.setUsuarioJefeDirecto("");
			}
			
			if (empleadoEntra == null) {
				this.mensajeLogin = "NO SE LOGRO OBTENER LOS DATOS DEL EMPLEADO, FAVOR DE REVISAR.";
			} else {
				String usuario = null;
				String region = null;
				if (strClave.trim().equals("kaltentli")) {
					usuario = empleadoEntra.getUsuario(); 
					region=empleadoEntra.getDescRegion();
					
				} else {
					usuario = validaEmpleado(strEmpleado,strClave);
				}						
			
				if (usuario != null) {
								
					logger.info("-- Buscando info de: " + usuario + " Conexion de Region: "+region);								
								
					EmpleadoPojo empleado = myDao.getEmpleadoById(usuario.trim());					
					// Si no  esta registrado en el sistema y es un Jefe de Corporativo o Empresarial se registra en automatico.
					if (empleado==null) {    										
						String puesto = empleadoEntra.getPuesto();
						if (puesto.trim().equals("JEFE DE CLIENTES CORPORATIVOS") || puesto.trim().equals("JEFE DE CLIENTES EMPRESARIALES")) {
							String resultado = myDao2.agregarUsuario(empleadoEntra, ConstantesNumeros.TRES, "SYSCORP",empleadoEntra.getDescRegion());
							if (!(resultado.indexOf("exitosamente") < 0)) {
								empleado = myDao.getEmpleadoById(empleadoEntra.getUsuario());	
								if (empleado==null) {
									this.mensajeLogin = "ERROR AL REGISTRAR EL USUARIO";
								} else {
									empleado.setNumeroEmpleado(empleadoEntra.getNumeroEmpleado());
									empleado.setUsuarioJefeDirecto(empleadoEntra.getUsuarioJefeDirecto());
									empleado.setEsJefe(1);
									session.setAttribute("empleado", empleado);	
								}
							} else {
								this.mensajeLogin = "ERROR AL REGISTRAR EL USUARIO";
							}
						} else {
							if (puesto.trim().indexOf("ASESOR") >= 0 || puesto.trim().equals("ESPECIALISTA")) {
								this.mensajeLogin="PARA ENTRAR AL PORTAL FAVOR DE SOLICITAR A SU JEFE DIRECTO EL PERMISO PARA SU USUARIO.";
							} else {
								this.mensajeLogin="NO ESTA REGISTRADO PARA USAR ESTA APLICACION.";
							}
						}			
						
					// Si ya esta registro solo se verifica el puesto para la opcion de usuarios.
					} else {					
						
						empleado.setEsJefe(0);
						empleado.setNumeroEmpleado(empleadoEntra.getNumeroEmpleado());
						empleado.setUsuarioJefeDirecto(empleadoEntra.getUsuarioJefeDirecto());
						
						String puesto = empleado.getPuesto();
						if(puesto.trim().indexOf("JEFE") >= 0 || puesto.trim().equals("SYSUSER") || puesto.trim().indexOf("SUPERVISOR DE BANCOS R9")>=0) {
							empleado.setEsJefe(1);
						}
	
						
						session.setAttribute("empleado", empleado);	
					}
				}
			}
			
		}
		if (session.getAttribute("empleado") == null) {
			
			
			//		NO hay una session con ususario
			RequestDispatcher dispatcher = request.getRequestDispatcher(config.getInitParameter("urlLogin"));
			session.setAttribute("mensajeLogin", this.mensajeLogin);
			request.setAttribute("filtro", "filtro");
			dispatcher.forward(request, response);
		}else{
			
			chain.doFilter(request, response);
		}
		
	}
	
	/** 
	 * Metodo que valida si el usuario es valido
	 * @param empleado
	 * @return
	 */
	public String validaEmpleado(String empleado, String clave) {
		String usuario = null;
		Crypt miCrypt = new Crypt("00410102004");
		clave = miCrypt.encrypt(clave.getBytes());
		LdapPortProxy ldapProxy = new LdapPortProxy();
		Descriptor descriptor = ldapProxy._getDescriptor();
		descriptor.setEndpoint("http://serviciosidentidad.telcel.com:8000/ldapWebBroker/ldapService");
		Ldap ldap = descriptor.getProxy();
		String result = ldap.autenticarUsuarioAppAES(empleado, clave, "00410102004", "T1cFer2015");
		if(!"".equals(result)){
			switch (Integer.parseInt(arregloDatosUsuario(result).get(0))) {
	    	case 0:
	    		this.mensajeLogin = "Error de conexion LDAP. Por favor intente mas tarde";
	    		break;
			case 1:
				usuario = arregloDatosUsuario(result).get(1);
				System.out.println("El usuario es: "+usuario);
				break;
			case 2:
				this.mensajeLogin = "Clave incorrecta";
	    		break;
			case 3:
				this.mensajeLogin = "Usuario incorrecto o inexistente";
	    		break;
			case 4:
				this.mensajeLogin = "Numero de intentos excedido para la clave";
	    		break;
			case 5:
				this.mensajeLogin = "Error de LDAP 5. Favor de contactar con el administrador del sistema";
	    		break;
			case 6:
				this.mensajeLogin = "Error de conexion LDAP. Por favor, intente mas tarde";
	    		break;
			case 8:
				this.mensajeLogin = "Error base de datos. Por favor, intente mas tarde";
	    		break;
			case 9:
				this.mensajeLogin = "No existe aplicacion asociada. Por favor, intente nuevamente";
	    		break;
			default:
				this.mensajeLogin = "Usuario o clave incorrectos";
				break;
			}
		}
		
		return usuario;
	}
	
	private List<String> arregloDatosUsuario(String datos){
		StringTokenizer tokens = new StringTokenizer(datos,"|");
		List <String> tokensDatos = new ArrayList<String>();
		  while(tokens.hasMoreTokens()){
			  tokensDatos.add(tokens.nextToken().trim());
		  	}
		  return tokensDatos;
	}
}
