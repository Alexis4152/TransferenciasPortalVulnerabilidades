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

public class TiempoRealAction extends DispatchAction {
	
	private static final String IDESTATUS = "idEstatus";
	private static final String LISTATRANS = "listaTransferencias";
	private static final String LISTATRANSCOMPENSA = "listaTransferenciasCompensacion";
	private static final String TITULO = "titulo";
	private static final String TDCI = "TransferenciasDaoConsultaImp";
	private static final String LISTATRAN = "listaTrasferencia";
	private static final String PANELID = "panelId";
	
    public ActionForward random(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String valor="0";
		String tipo = request.getParameter("tipo");
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
		if(request.getParameter(this.IDESTATUS)!=null){
			
			TransferenciasDaoUtilInterface myDao = (TransferenciasDaoUtilInterface) context.getBean("TransferenciasDaoUtilInterface");
			if(request.getParameter(this.IDESTATUS).equals("7") || request.getParameter(this.IDESTATUS).equals("17")){
				if(tipo != null && "compensacion".equals(tipo)) {
					valor = myDao.getCountTransferenciasbyEstatusCompensacion(Integer.parseInt(request.getParameter(this.IDESTATUS)),new java.sql.Date(new Date().getTime()),null,empleadoPojo.getDescRegion());
				}else{
					valor = myDao.getCountTransferenciasbyEstatus(Integer.parseInt(request.getParameter(this.IDESTATUS)),new java.sql.Date(new Date().getTime()),null,empleadoPojo.getDescRegion());
				}
			}else if(request.getParameter(this.IDESTATUS).equals("8")){
				
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
				valor = myDao.getCountTransferenciasRegresadas(empleado.getIdEmpleado(),empleado.getDescRegion());
			}else {
				valor = myDao.getCountTransferenciasbyEstatus(Integer.parseInt(request.getParameter(this.IDESTATUS)),null,null,empleadoPojo.getDescRegion());
				
				if (request.getParameter(this.IDESTATUS).equals("5")) {
					String val2 = myDao.getCountTransferenciasbyEstatus(Constantes.REINTENTO,null,null,empleadoPojo.getDescRegion());
					int tot = Integer.parseInt(valor) + Integer.parseInt(val2);
					valor = String.valueOf(tot);
				}
			}
		}

		
		
		request.setAttribute("valor",valor);
		
		return mapping.findForward("monitorTransferencias");
		
		}

    public ActionForward listaTransferencias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute(PANELID, this.LISTATRANS);  
		request.setAttribute(this.TITULO, "Pendientes");
		
		return mapping.findForward(this.LISTATRANS);

    }
    
	 public ActionForward listaPendientes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
	
			try {
				EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
				List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.DESGLOSADA,null, null,empleadoPojo.getDescRegion());
				request.setAttribute(this.LISTATRAN, listaTrasferencia);
				request.setAttribute(PANELID, "listaPendientes");  
				request.setAttribute(this.TITULO, "Pendientes");
				request.setAttribute("tituloFecha", "Fecha Pendiente");		
				request.setAttribute("pendiente", "OK");
			
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
				List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.APLICADA,null, null,empleadoPojo.getDescRegion());
				request.setAttribute(this.LISTATRAN, listaTrasferencia);
				request.setAttribute(PANELID, "listaPendientes");  
				request.setAttribute(this.TITULO, "Compensaciones pendientes");
				request.setAttribute("tituloFecha", "Fecha Pendiente");		
				request.setAttribute("pendiente", "OK");
			
				java.util.Date fecha= new java.util.Date();
				SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
				request.setAttribute("fechaCreacion", formatoFinal.format(fecha));
		
			
				if(request.getParameter("excel")!=null){			
					return mapping.findForward("listaTransferenciasExcelCompensacion");
				}
			 
			} catch (Exception e) {
				
			}
			
			return mapping.findForward(this.LISTATRANS);

	    }
	    
	    public ActionForward listaEnProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.APLICADA_PROCESO, null, null,empleadoPojo.getDescRegion());
			List<TransferenciaPojo> listaTrasferencia2 = myDao.getTransferenciasByEstatusByFechas(Constantes.REINTENTO, null, null,empleadoPojo.getDescRegion());
			listaTrasferencia.addAll(listaTrasferencia2);
			
			request.setAttribute(this.LISTATRAN, listaTrasferencia);
			request.setAttribute(PANELID, "listaEnProceso");  
			request.setAttribute(this.TITULO, "En Proceso");
	
			return mapping.findForward(this.LISTATRANS);

	    }
	    
	    public ActionForward listaCompensacionesEnProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.COMPENSACION_PROCESO, null, null,empleadoPojo.getDescRegion());
//			List<TransferenciaPojo> listaTrasferencia2 = myDao.getTransferenciasByEstatusByFechas(Constantes.REINTENTO, null, null,empleadoPojo.getDescRegion());
//			listaTrasferencia.addAll(listaTrasferencia2);
			
			request.setAttribute(this.LISTATRAN, listaTrasferencia);
			request.setAttribute(PANELID, "listaEnProceso");  
			request.setAttribute(this.TITULO, "Compensaciones en Proceso");
	
			return mapping.findForward(this.LISTATRANSCOMPENSA);

	    }

	    public ActionForward listaAplicadas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.APLICADA,new java.sql.Date((new Date()).getTime()), null,empleadoPojo.getDescRegion());
			request.setAttribute(this.LISTATRAN, listaTrasferencia);
			request.setAttribute(PANELID, "listaAplicadas");  
			request.setAttribute(this.TITULO, "Aplicadas");
			request.setAttribute("tituloFecha", "Fecha Programada");
			
			java.util.Date fecha= new java.util.Date();
			SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
			request.setAttribute("fechaCreacion", formatoFinal.format(fecha));
		
			
			if(request.getParameter("excel")!=null){			
				return mapping.findForward("listaTransferenciasExcel");
			}
			
			return mapping.findForward(this.LISTATRANS);

	    }
	    
	    public ActionForward listaCompensacionesAplicadas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.COMPENSADA,new java.sql.Date((new Date()).getTime()), null,empleadoPojo.getDescRegion());
			request.setAttribute(this.LISTATRAN, listaTrasferencia);
			request.setAttribute(PANELID, "listaAplicadas");  
			request.setAttribute(this.TITULO, "Compensaciones aplicadas");
			request.setAttribute("tituloFecha", "Fecha Programada");
			
			java.util.Date fecha= new java.util.Date();
			SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
			request.setAttribute("fechaCreacion", formatoFinal.format(fecha));
		
			
			if(request.getParameter("excel")!=null){			
				return mapping.findForward("listaTransferenciasExcelCompensacion");
			}
			
			return mapping.findForward(this.LISTATRANSCOMPENSA);

	    }

	    public ActionForward listaError(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.ERROR, null, null,empleadoPojo.getDescRegion());
			request.setAttribute(this.LISTATRAN, listaTrasferencia); 
			
			request.setAttribute(PANELID, "listaError");  
			request.setAttribute(this.TITULO, "Errores");
			
			return mapping.findForward(this.LISTATRANS);

	    }
	    
	    public ActionForward listaCompensacionesError(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(this.TDCI);
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao.getTransferenciasByEstatusByFechas(Constantes.ERROR, null, null,empleadoPojo.getDescRegion());
			request.setAttribute(this.LISTATRAN, listaTrasferencia); 
			
			request.setAttribute(PANELID, "listaError");  
			request.setAttribute(this.TITULO, "Compensaciones en error");
			
			return mapping.findForward(this.LISTATRANSCOMPENSA);

	    }
	    public ActionForward listaRegresada(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean("TransferenciasDaoUtilInterface");
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencia = myDao2.getTransferenciasRegresadas(empleado.getIdEmpleado(),empleado.getDescRegion());
			request.setAttribute(this.LISTATRAN, listaTrasferencia); 
			
			request.setAttribute(PANELID, "listaRegresada");  
			request.setAttribute(this.TITULO, "Regresadas");
			 
			return mapping.findForward(this.LISTATRANS);

	    }
    
}
