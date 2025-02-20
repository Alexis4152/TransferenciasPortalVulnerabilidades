package com.telcel.portal.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telcel.portal.dao.interfaces.TransferenciasDaoConsultaInterface;
import com.telcel.portal.dao.interfaces.TransferenciasDaoUtilInterface;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.util.Constantes;

public class ListaTransferenciasAction  extends DispatchAction  {
	
	private static final String TDCI = "TransferenciasDaoConsultaImp";
	private static final String LISTATRA = "listaTrasferencia";
	private static final String PANELID = "panelId";
	private static final String  TITULO = "titulo";
	private static final String LISTATRANS = "listaTransferencias"; 
	private static final String LISTATRANSCOMPENSA = "listaTransferenciasCompensacion";

	
	 public ActionForward listaPendientes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
	
			try {
				EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
				List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.DESGLOSADA,null, null,empleadoPojo.getDescRegion());
				request.setAttribute(this.LISTATRA, listaTrasferencia);
				request.setAttribute(this.PANELID, "listaPendientes");  
				request.setAttribute(this.TITULO, "Pendientes");
				request.setAttribute("tituloFecha", "Fecha Pendiente");
			
				java.util.Date fecha= new java.util.Date();
				SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
				request.setAttribute("fechaCreacion", formatoFinal.format(fecha));
		
			
				if(request.getParameter("excel")!=null){			
					return mapping.findForward("listaTransferenciasExcel");
				}
			 
			} catch (Exception e) {
				
				
			}
			return mapping.findForward(this.LISTATRANS);

	    }
	 
	 public ActionForward listaCompensacionesPendientes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
	
			try {
				EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
				List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechasCompensacion(Constantes.APLICADA,new java.sql.Date((new Date()).getTime()), null,empleadoPojo.getDescRegion());
				request.setAttribute(this.LISTATRA, listaTrasferencia);
				request.setAttribute(this.PANELID, "listaPendientes");  
				request.setAttribute(this.TITULO, "Pendientes");
				request.setAttribute("tituloFecha", "Fecha Pendiente");
			
				java.util.Date fecha= new java.util.Date();
				SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
				request.setAttribute("fechaCreacion", formatoFinal.format(fecha));
		
			
				if(request.getParameter("excel")!=null){			
					return mapping.findForward("listaTransferenciasExcelCompensacion");
				}
			 
			} catch (Exception e) {
				
				
			}
			return mapping.findForward(this.LISTATRANSCOMPENSA);

	    }
	 
	 
	    
	    public ActionForward listaEnProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.APLICADA_PROCESO, null, null,empleadoPojo.getDescRegion());
			List<TransferenciaPojo> listaTrasferencia2 = myDao.getTransferenciasByEstatusByFechas(Constantes.REINTENTO, null, null,empleadoPojo.getDescRegion());
			listaTrasferencia.addAll(listaTrasferencia2);
			
			request.setAttribute(this.LISTATRA, listaTrasferencia);
			request.setAttribute(this.PANELID, "listaEnProceso");  
			request.setAttribute(this.TITULO, "En Proceso");
			
			return mapping.findForward(this.LISTATRANS);

	    }
	    
	    public ActionForward listaCompensacionesEnProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			log.info("**************************************************************************************************************************************");
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.COMPENSACION_PROCESO, null, null,empleadoPojo.getDescRegion());
//			List<TransferenciaPojo> listaTrasferencia2 = myDao.getTransferenciasByEstatusByFechas(Constantes.REINTENTO, null, null,empleadoPojo.getDescRegion());
//			listaTrasferencia.addAll(listaTrasferencia2);
			
			request.setAttribute(this.LISTATRA, listaTrasferencia);
			request.setAttribute(this.PANELID, "listaEnProceso");  
			request.setAttribute(this.TITULO, "En Proceso");
			
			return mapping.findForward(this.LISTATRANSCOMPENSA);

	    }

	    public ActionForward listaAplicadas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	    	
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			
			try {
				EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
				List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.APLICADA,new java.sql.Date((new Date()).getTime()), null,empleadoPojo.getDescRegion());
				request.setAttribute(this.LISTATRA, listaTrasferencia);
				request.setAttribute(this.PANELID, "listaAplicadas");  
				request.setAttribute(this.TITULO, "Aplicadas");
				request.setAttribute("tituloFecha", "Fecha Programada");
			
			 
				java.util.Date fecha= new java.util.Date();
				SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
				request.setAttribute("fechaCreacion", formatoFinal.format(fecha));
		
			
				if(request.getParameter("excel")!=null){			
					return mapping.findForward("listaTransferenciasExcel");
				}
			} catch (Exception e) {
				
				
			}
			return mapping.findForward(this.LISTATRANS);

	    }
	    
	    public ActionForward listaCompensacionesAplicadas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	    	
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			
			try {
				EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
				List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.COMPENSADA,new java.sql.Date((new Date()).getTime()), null,empleadoPojo.getDescRegion());
				request.setAttribute(this.LISTATRA, listaTrasferencia);
				request.setAttribute(this.PANELID, "listaAplicadas");  
				request.setAttribute(this.TITULO, "Aplicadas");
				request.setAttribute("tituloFecha", "Fecha Programada");
			
			 
				java.util.Date fecha= new java.util.Date();
				SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
				request.setAttribute("fechaCreacion", formatoFinal.format(fecha));
		
			
				if(request.getParameter("excel")!=null){			
					return mapping.findForward("listaTransferenciasExcelCompensacion");
				}
			} catch (Exception e) {
				
				
			}
			return mapping.findForward(this.LISTATRANSCOMPENSA);

	    }

	    public ActionForward listaError(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.ERROR, null, null,empleadoPojo.getDescRegion());
			request.setAttribute(this.LISTATRA, listaTrasferencia); 
			
			request.setAttribute(this.PANELID, "listaError");  
			request.setAttribute(this.TITULO, "Errores");
			
			return mapping.findForward(this.LISTATRANS);

	    }

	    public ActionForward listaCompensacionesError(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.COMPENSACION_ERROR, null, null,empleadoPojo.getDescRegion());
			request.setAttribute(this.LISTATRA, listaTrasferencia); 
			
			request.setAttribute(this.PANELID, "listaError");  
			request.setAttribute(this.TITULO, "Errores");
			
			return mapping.findForward(this.LISTATRANSCOMPENSA);

	    }
	    
	    public ActionForward listaRegresada(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean("TransferenciasDaoUtilInterface");
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao2.getTransferenciasRegresadas(empleado.getIdEmpleado(),empleado.getDescRegion());
			request.setAttribute(this.LISTATRA, listaTrasferencia); 
			
			request.setAttribute(this.PANELID, "listaRegresada");  
			request.setAttribute(this.TITULO, "Regresadas");
			
			return mapping.findForward(this.LISTATRANS);

	    }
	    

}
