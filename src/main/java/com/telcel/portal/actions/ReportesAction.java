package com.telcel.portal.actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telcel.portal.dao.interfaces.TransferenciasDaoConsultaInterface;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface;
import com.telcel.portal.dao.interfaces.TransferenciasDaoUtilInterface;
import com.telcel.portal.pojos.BancoPojo;
import com.telcel.portal.pojos.CicloPojo;
import com.telcel.portal.pojos.ConsultaReportePojo;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.PeticionPojo;
import com.telcel.portal.pojos.TransaccionReportePojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.pojos.TransferenciasDesglosadasPojo;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.ConstantesReportes;
import com.telcel.portal.util.DivisorTransferenciasUsuario;

public class ReportesAction extends DispatchAction {
	private static Logger  logger = Logger.getLogger(ReportesAction.class);
	private TransferenciasDaoInterface myDao3;
	
	public ActionForward transferenciasAplicadas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute(ConstantesReportes.PANELID, "transferenciasAplicadas");
		request.setAttribute(ConstantesReportes.TITULO, "Reporte Trans. Aplicadas");
		
		String year=request.getParameter(ConstantesReportes.YEAR);
		String mes=request.getParameter(ConstantesReportes.MES);
		request.setAttribute(ConstantesReportes.YEAR, year);
		request.setAttribute(ConstantesReportes.MES, mes);
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fecha));
		
		if(request.getParameter("busca")!=null){
			
			try {
				
				Integer.parseInt(year);
				
			} catch (Exception e) {
				year="2010";
			}
			EmpleadoPojo empleadoPojo = (EmpleadoPojo)request.getSession().getAttribute("empleado");
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoInterface myDao = (TransferenciasDaoInterface) context.getBean("TransferenciasDaoImp");
			
			List<Map<String, TransaccionReportePojo>> reporteList = myDao.getReporte(year,Integer.parseInt(mes), empleadoPojo.getDescRegion());
			
			request.setAttribute("reporteList", reporteList);
			if(request.getParameter(ConstantesReportes.EXCEL)==null){
				return mapping.findForward("concentradoTransferenciasAplicadas");
			}
			return mapping.findForward("concentradoTransferenciasAplicadasExcel");
		}
			
		return mapping.findForward("transferenciasAplicadas");

	}
	
	public ActionForward transferenciasDesglosadas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		List<ConsultaReportePojo> reporteList=new ArrayList<ConsultaReportePojo>();
		String mensaje="";
		request.setAttribute(ConstantesReportes.PANELID, "transferenciasDesglosadas");
		request.setAttribute(ConstantesReportes.TITULO, "Reporte Trans. Desglosadas");
		Integer nmes=15;
		String year=request.getParameter(ConstantesReportes.YEAR);
		String mes=request.getParameter(ConstantesReportes.MES);
		
		//Adaptamos  mes y anio  como parametro para poder insertar a la tabla
		String parametro ="";
		if(mes!=null){
		if(!mes.equals("") ){
			 nmes=Integer.parseInt(mes);
			}}
		nmes++;
		if (nmes >= 10) {
			 parametro=year+nmes;
		} else {
			 parametro=year+"0"+nmes;

		} //end adaptacion mes y anio
		
		log.info("Se procesa la peticion de anio: "+year+ "y mes : "+mes);
		request.setAttribute(ConstantesReportes.YEAR, year);
		request.setAttribute(ConstantesReportes.MES, mes);
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fecha));
		
		
		if(request.getParameter("busca")!=null){
			
			String correo =request.getParameter("correo");
			log.info("Correo procesado : "+ correo+ " YEAR: "+year+" MES: "+mes);
			if(correo!=null){
				if(!correo.equals("") ){		
			           if (correo.endsWith("@mail.telcel.com") || correo.endsWith("@telcel.com") ) {
			        	   //se ejecuta solo el correo pertenece  a telcel
			        	   try {
			                    Integer.parseInt(year);
			                    if (year.length()!=4) {
									throw new Exception();
								}
			        		   
			        	   } catch (Exception e) {
			        		   year="2010";
			        		   mensaje="El anio no cumple con el formato, favor de verificarlo ejemplo: 2018";
			        		   request.setAttribute("mensaje", mensaje);
			        		   return mapping.findForward("concentradoTransferenciasDesglosadas");
			        	   }
			        	   
			        	   EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			        	   ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			        	   TransferenciasDaoInterface myDao = (TransferenciasDaoInterface) context.getBean("TransferenciasDaoImp");
			        	
			        	
			        	   
			        	   Date inicio=new Date(); 
			        	   TransferenciasDesglosadasPojo ctd=new TransferenciasDesglosadasPojo();
			        	   
			        	   ctd.setCorreo(correo);
			        	   ctd.setParametro(parametro);
			        	   ctd.setEstatus("PE");
			        	   ctd.setRegion(empleadoPojo.getDescRegion().replace("R0", ""));
			        	   ctd.setInicio(formatoFinal.format(inicio));
			        	   
			        	   if (myDao.insertaTransferenciasDesglosadas(ctd)) {
			        		   mensaje="Su solicitud se proceso satisfactoriamente, se enviara correo en unos minutos con la informacion";
			        	   } else {
			        		   mensaje="No fue posible procesar su solicitud, favor de intentarlo de nuevo, si el problema persiste comuniquese al correo sisfactsipab@mail.telcel.com ";
			        	   }
			         } else{
			        	 mensaje="Error en la solicitud, el correo debe pertenecer a TELCEL (terminacion @telcel.com o @mail.telcel.com)";    
	                       }
			      }else{
			    	 mensaje="Error en la solicitud, el campo de correo no puede ir vacio";
			          }
			}
			
		   request.setAttribute("mensaje", mensaje);
		   if(request.getParameter(ConstantesReportes.EXCEL)==null){
			    return mapping.findForward("concentradoTransferenciasDesglosadas");
		   }
    	  
    	   
		   return mapping.findForward("concentradoTransferenciasDesglosadasExcel");
	    }
	
		return mapping.findForward("transferenciasDesglosadas");


	}	
		
	public ActionForward transferenciasByEstatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(ConstantesReportes.TDCI);
		
		request.setAttribute("listaEstatus", myDao.getEstatus());
		request.setAttribute(ConstantesReportes.CONCENTRADO, request.getParameter(ConstantesReportes.CONCENTRADO));
		request.setAttribute(ConstantesReportes.FECHA, request.getParameter(ConstantesReportes.FECHA));
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fecha));
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			
			request.setAttribute(ConstantesReportes.PANELID, "transferenciasByEstatus");
			request.setAttribute(ConstantesReportes.TITULO, "Reporte por Estatus");

		}
		return mapping.findForward("transferenciasByEstatus");

	}
		
	public ActionForward estatusTransferenciaLista(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);
		String estatus=request.getParameter("estatus");
		String byEmpleado=request.getParameter("bandera");
		
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
		request.setAttribute("estatus", estatus);
		request.setAttribute("bandera", byEmpleado);
		
		
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
		
		if (fecha != null) {
			fecha=fecha.replace("-", "/");
			try {

				fechaDate = new java.sql.Date((formatoFinal.parse(fecha))
						.getTime());
			} catch (Exception e) {

				fechaDate = new java.sql.Date(new Date().getTime());
			}

		} else {

			fechaDate = new java.sql.Date(new Date().getTime());
		}
		
		if (fecha2 != null) {

			
			try {

				fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
						.getTime());
			} catch (Exception e) {

				fechaDate2 = null;
			}

		} 
		
		if(estatus!=null){
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(ConstantesReportes.TDCI);
			request.setAttribute("listaEstatus", myDao.getEstatus());
			
			Integer idEmpleado=null;
			if(byEmpleado!=null && byEmpleado.equals("true")){
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
				idEmpleado=empleado.getIdEmpleado();
				
			}
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencias = myDao.getTransferenciasByEstatusByFechaReporte(Integer.parseInt(estatus), fechaDate, fechaDate2,idEmpleado,empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);	
		}
		 
		if(request.getParameter(ConstantesReportes.EXCEL)==null){
				
			return mapping.findForward("listaTransferenciasEstatus");
		}
		
		request.setAttribute("estatusInt", Integer.parseInt(estatus));
		return mapping.findForward("transferenciasByEstatusExcel");	
					
	}
	public ActionForward transferenciasByBanco(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		EmpleadoPojo empleadoPojo = (EmpleadoPojo)request.getSession().getAttribute("empleado");
		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);
		String banco=request.getParameter(ConstantesReportes.BANCO);
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			
			request.setAttribute(ConstantesReportes.PANELID, "transferenciasByBanco");
			request.setAttribute(ConstantesReportes.TITULO, "Reporte por Banco");

			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(ConstantesReportes.TDCI);
			List<BancoPojo> listaBancos = myDao.getBancos();
			
			request.setAttribute(ConstantesReportes.LISTABANCOS, listaBancos);
			return mapping.findForward("transferenciasByBanco");

		}

			
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
		request.setAttribute(ConstantesReportes.BANCO, banco);
			
			
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
			
		if (fecha != null) {

			try {

				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
				} catch (Exception e) {

					fechaDate = new java.sql.Date(new Date().getTime());
				}

		} else {

			fechaDate = new java.sql.Date(new Date().getTime());
		}
			
		if (fecha2 != null) {

				
				try {

					fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
							.getTime());
				} catch (Exception e) {

					fechaDate2 = null;
				}

		} 
			
		if(banco!=null){
				ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
				TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(ConstantesReportes.TDCI);
				List<BancoPojo> listaBancos = myDao.getBancos();
				request.setAttribute(ConstantesReportes.LISTABANCOS, listaBancos);
				TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
				List<TransferenciaPojo> listaTrasferencias = myDao2.getTransferenciasByBancoByFechaTransferencia(Integer.parseInt(banco), fechaDate, fechaDate2,empleadoPojo.getDescRegion());
				request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);	
		}
			 
		if(request.getParameter(ConstantesReportes.EXCEL)==null){
					
				return mapping.findForward("listaTransferenciasBanco");
		}
			
		request.setAttribute("bancoInt", Integer.parseInt(banco));
		return mapping.findForward("transferenciasByBancoExcel");
		
	}
	
	public ActionForward transferenciasByUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			request.getSession().removeAttribute(ConstantesReportes.LISTATRANS);
			request.getSession().removeAttribute("listaAcumuladaUsuario");
			request.getSession().removeAttribute(ConstantesReportes.LISTATRANSDETALLE);
			request.setAttribute(ConstantesReportes.PANELID, "transferenciasByUser");
			request.setAttribute(ConstantesReportes.TITULO, "Transferencias por Usuario");

			return mapping.findForward("transferenciasByUser");

		}
		
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
			
		if(request.getParameter(ConstantesReportes.EXCEL)!=null){
			generaExcel(request, response);
			//return mapping.findForward("transferenciasByUserExcel");
			return null;
		}	
		
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
			
		if (fecha != null) {

			try {

				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
				} catch (Exception e) {

					fechaDate = new java.sql.Date(new Date().getTime());
				}

		} else {

			fechaDate = new java.sql.Date(new Date().getTime());
		}
			
		if (fecha2 != null) {

				
				try {

					fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
							.getTime());
				} catch (Exception e) {

					fechaDate2 = null;
				}

		} 
		
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
		List<TransferenciaPojo> listaTrasferencias = myDao2.getTransferenciasByUserByFechaTransferencia(fechaDate, fechaDate2);
		List<TransferenciaPojo> listaDetalleTrasferencias = myDao2.getDetalleTransferenciasByUserByFechaTransferencia(fechaDate, fechaDate2);
		Collections.sort(listaDetalleTrasferencias, new Comparator<TransferenciaPojo>() {
			@Override
			public int compare(TransferenciaPojo o1, TransferenciaPojo o2) {
				 int c;
				 c = o1.getIdtransferencia().compareTo(o2.getIdtransferencia());
				 if (c == 0)
					 c = o1.getFechaHistorial().compareTo(o2.getFechaHistorial());
				return c;
			}
		});
		Map<String, List<TransferenciaPojo>> mapaTransferencias = DivisorTransferenciasUsuario.separaTransferencias(listaDetalleTrasferencias);
		DivisorTransferenciasUsuario.validaRechazadas(listaTrasferencias, mapaTransferencias);
		listaDetalleTrasferencias.clear();
		for(Map.Entry<String, List<TransferenciaPojo>> entry : mapaTransferencias.entrySet()) {
			listaDetalleTrasferencias.addAll(entry.getValue());
		}
		Collections.sort(listaDetalleTrasferencias, new Comparator<TransferenciaPojo>() {
			@Override
			public int compare(TransferenciaPojo o1, TransferenciaPojo o2) {
				return o1.getIdtransferencia().compareTo(o2.getIdtransferencia());
			}
		});
		request.getSession().setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);
		request.getSession().setAttribute("listaAcumuladaUsuario", listaDetalleTrasferencias);
		request.getSession().setAttribute(ConstantesReportes.LISTATRANSDETALLE, mapaTransferencias);
			 
					
		return mapping.findForward("concentradoTransferenciasUsuario");
			
	}
	
	public ActionForward transferenciasRechazadas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);

		String banco=request.getParameter(ConstantesReportes.BANCO);
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			request.getSession().removeAttribute(ConstantesReportes.LISTATRANS);
			request.getSession().removeAttribute("listaAcumuladaUsuario");
			request.getSession().removeAttribute(ConstantesReportes.LISTATRANSDETALLE);
			request.setAttribute(ConstantesReportes.PANELID, "transferenciasRechazadas");
			request.setAttribute(ConstantesReportes.TITULO, "Transferencias Rechazadas");
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(ConstantesReportes.TDCI);
			List<BancoPojo> listaBancos = new ArrayList<BancoPojo>();
			BancoPojo item = new BancoPojo();
			item.setIdbanco("0");
			item.setNombre("Seleccione Banco");
			item.setNombreCorto("Seleccione Banco");
			listaBancos.add(item);
			listaBancos.addAll(myDao.getBancos());
			
			request.setAttribute(ConstantesReportes.LISTABANCOS, listaBancos);

			return mapping.findForward("transferenciasRechazadas");

		}
		
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
		request.setAttribute(ConstantesReportes.BANCO, banco);
			
		if(request.getParameter(ConstantesReportes.EXCEL)!=null){
			generaExcelRechazados(request, response);
			//return mapping.findForward("transferenciasByUserExcel");
			return null;
		}	
		
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
			
		if (fecha != null) {

			try {

				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
				} catch (Exception e) {

					fechaDate = new java.sql.Date(new Date().getTime());
				}

		} else {

			fechaDate = new java.sql.Date(new Date().getTime());
		}
			
		if (fecha2 != null) {

				
				try {

					fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
							.getTime());
				} catch (Exception e) {

					fechaDate2 = null;
				}

		} 
		
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
		List<TransferenciaPojo> listaTrasferencias = myDao2.getTransferenciasRechazadas(fechaDate, fechaDate2,banco);
		List<TransferenciaPojo> listaDetalleTrasferencias = myDao2.getDetalleTransferenciasRechazadas(fechaDate, fechaDate2,banco);
		Collections.sort(listaDetalleTrasferencias, new Comparator<TransferenciaPojo>() {
			@Override
			public int compare(TransferenciaPojo o1, TransferenciaPojo o2) {
				 int c;
				 c = o1.getIdtransferencia().compareTo(o2.getIdtransferencia());
				 if (c == 0)
					 c = o1.getFechaHistorial().compareTo(o2.getFechaHistorial());
				return c;
			}
		});
		Map<String, List<TransferenciaPojo>> mapaTransferencias = DivisorTransferenciasUsuario.separaTransferencias(listaDetalleTrasferencias);
		//DivisorTransferenciasUsuario.validaRechazadas(listaTrasferencias, mapaTransferencias);
		listaDetalleTrasferencias.clear();
		for(Map.Entry<String, List<TransferenciaPojo>> entry : mapaTransferencias.entrySet()) {
			listaDetalleTrasferencias.addAll(entry.getValue());
		}
		Collections.sort(listaDetalleTrasferencias, new Comparator<TransferenciaPojo>() {
			@Override
			public int compare(TransferenciaPojo o1, TransferenciaPojo o2) {
				return o1.getIdtransferencia().compareTo(o2.getIdtransferencia());
			}
		});
		request.getSession().setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);
		request.getSession().setAttribute("listaAcumuladaUsuario", listaDetalleTrasferencias);
		request.getSession().setAttribute(ConstantesReportes.LISTATRANSDETALLE, mapaTransferencias);
			 
					
		return mapping.findForward("concentradoTransferenciasRechazadas");
			
	}
	
	public void generaExcel(HttpServletRequest request, HttpServletResponse response) {
		String fechaInicio = (String) request.getAttribute("fecha");
		String fechaFin = (String) request.getAttribute("fecha2");
		String fechaCreacion = (String) request.getAttribute("fechaCreacion");
		EmpleadoPojo empleado = (EmpleadoPojo) request.getSession().getAttribute("empleado");
		List<TransferenciaPojo> listaTrasferencias = (List<TransferenciaPojo>) request.getSession().getAttribute("listaTrasferencias");
		List<TransferenciaPojo> listaDetalleTrasferencias = (List<TransferenciaPojo>) request.getSession().getAttribute("listaAcumuladaUsuario");
		
		XSSFWorkbook libro = new XSSFWorkbook();
		Sheet hoja1 = libro.createSheet();
		Sheet hoja2 = libro.createSheet();
		Sheet hoja3 = libro.createSheet();
        libro.setSheetName(0, "General");
        libro.setSheetName(1, "Acumulado");
        libro.setSheetName(2, "Detalle");
        
        //hoja 1
        Row fila1 = hoja1.createRow(0);
        Cell celda = fila1.createCell(0);
        celda.setCellValue("Fecha de inicio");
        celda = fila1.createCell(1);
        celda.setCellValue(fechaInicio);
        Row fila2 = hoja1.createRow(1);
        celda = fila2.createCell(0);
        celda.setCellValue("Fecha de fin:");
        celda = fila2.createCell(1);
        celda.setCellValue(fechaFin);
        Row fila3 = hoja1.createRow(2);
        celda = fila3.createCell(0);
        celda.setCellValue("Creado por:");
        celda = fila3.createCell(1);
        celda.setCellValue(empleado.getNombre() + " el " + fechaCreacion);
        
        //hoja2
        Row cabecero = hoja2.getRow(0);
        String[] cabeceros = {"Usuario", "Nombre", "Total", "Numero de transferencias"};
		if (cabecero == null) {
			cabecero = hoja2.createRow(0);
			for (int i = 0; i < cabeceros.length; i++) {
				celda = cabecero.createCell(i);
				celda.setCellValue(cabeceros[i]);
			}
		}
		for (int i = 0; i < listaTrasferencias.size(); i++) {
			Row fila = hoja2.createRow(i+1);
			celda = fila.createCell(0);
			celda.setCellValue(listaTrasferencias.get(i).getUsuario());
			celda = fila.createCell(1);
			celda.setCellValue(listaTrasferencias.get(i).getNombreUsuario());
			celda = fila.createCell(2);
			celda.setCellValue("$" + listaTrasferencias.get(i).getImporteString());
			celda = fila.createCell(3);
			celda.setCellValue(listaTrasferencias.get(i).getIdtransferencia());
		}
		for (int i = 0; i < cabecero.getPhysicalNumberOfCells(); hoja2.autoSizeColumn(i++));
		
		//hoja3
        Row cabecero2 = hoja3.getRow(0);
        String[] cabeceros2 = {"Usuario", "Nombre", "Fecha Tranferencia", "Importe", "Alias", "Id Transferencia", "Tipo Pagos", "Fecha Desglose", "Rechazado"};
		if (cabecero2 == null) {
			cabecero2 = hoja3.createRow(0);
			for (int i = 0; i < cabeceros2.length; i++) {
				celda = cabecero2.createCell(i);
				celda.setCellValue(cabeceros2[i]);
			}
		}
		for (int i = 0; i < listaDetalleTrasferencias.size(); i++) {
			Row fila = hoja3.createRow(i+1);
			celda = fila.createCell(0);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getUsuario());
			celda = fila.createCell(1);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getNombreUsuario());
			celda = fila.createCell(2);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getFecha());
			celda = fila.createCell(3);
			celda.setCellValue("$" + listaDetalleTrasferencias.get(i).getImporteString());
			celda = fila.createCell(4);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getAlias());
			celda = fila.createCell(5);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getIdtransferencia());
			celda = fila.createCell(6);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getTipoPagos());
			celda = fila.createCell(7);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getFechaHistorial());
			celda = fila.createCell(8);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getRechazado());
		}
		for (int i = 0; i < cabecero2.getPhysicalNumberOfCells(); hoja3.autoSizeColumn(i++));

		SXSSFWorkbook libroDescarga = new  SXSSFWorkbook(libro,1000);
        response.setHeader("Content-Disposition", "attachment; filename=reporteUsuario.xlsx");
        ServletOutputStream out;
		try {
			out = response.getOutputStream();
			libroDescarga.write(out);
	        out.flush();
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generaExcelRechazados(HttpServletRequest request, HttpServletResponse response) {
		String fechaInicio = (String) request.getAttribute("fecha");
		String fechaFin = (String) request.getAttribute("fecha2");
		String fechaCreacion = (String) request.getAttribute("fechaCreacion");
		EmpleadoPojo empleado = (EmpleadoPojo) request.getSession().getAttribute("empleado");
		List<TransferenciaPojo> listaTrasferencias = (List<TransferenciaPojo>) request.getSession().getAttribute("listaTrasferencias");
		List<TransferenciaPojo> listaDetalleTrasferencias = (List<TransferenciaPojo>) request.getSession().getAttribute("listaAcumuladaUsuario");
		
		XSSFWorkbook libro = new XSSFWorkbook();
		Sheet hoja1 = libro.createSheet();
		Sheet hoja2 = libro.createSheet();
		Sheet hoja3 = libro.createSheet();
        libro.setSheetName(0, "General");
        libro.setSheetName(1, "Acumulado");
        libro.setSheetName(2, "Detalle");
        
        //hoja 1
        Row fila1 = hoja1.createRow(0);
        Cell celda = fila1.createCell(0);
        celda.setCellValue("Fecha de inicio");
        celda = fila1.createCell(1);
        celda.setCellValue(fechaInicio);
        Row fila2 = hoja1.createRow(1);
        celda = fila2.createCell(0);
        celda.setCellValue("Fecha de fin:");
        celda = fila2.createCell(1);
        celda.setCellValue(fechaFin);
        Row fila3 = hoja1.createRow(2);
        celda = fila3.createCell(0);
        celda.setCellValue("Creado por:");
        celda = fila3.createCell(1);
        celda.setCellValue(empleado.getNombre() + " el " + fechaCreacion);
        
        //hoja2
        Row cabecero = hoja2.getRow(0);
        String[] cabeceros = {"Usuario", "Nombre", "Numero de transferencias"};
		if (cabecero == null) {
			cabecero = hoja2.createRow(0);
			for (int i = 0; i < cabeceros.length; i++) {
				celda = cabecero.createCell(i);
				celda.setCellValue(cabeceros[i]);
			}
		}
		for (int i = 0; i < listaTrasferencias.size(); i++) {
			Row fila = hoja2.createRow(i+1);
			celda = fila.createCell(0);
			celda.setCellValue(listaTrasferencias.get(i).getUsuario());
			celda = fila.createCell(1);
			celda.setCellValue(listaTrasferencias.get(i).getNombreUsuario());
			celda = fila.createCell(2);
			celda.setCellValue(listaTrasferencias.get(i).getIdtransferencia());
		}
		for (int i = 0; i < cabecero.getPhysicalNumberOfCells(); hoja2.autoSizeColumn(i++));
		
		//hoja3
        Row cabecero2 = hoja3.getRow(0);
        String[] cabeceros2 = {"Usuario", "Nombre", "Fecha Tranferencia", "Importe", "Alias", "Id Transferencia", "Tipo Pagos", "Fecha Rechazo", "Motivo de Rechazo","Persona que rechazo"};
		if (cabecero2 == null) {
			cabecero2 = hoja3.createRow(0);
			for (int i = 0; i < cabeceros2.length; i++) {
				celda = cabecero2.createCell(i);
				celda.setCellValue(cabeceros2[i]);
			}
		}
		for (int i = 0; i < listaDetalleTrasferencias.size(); i++) {
			Row fila = hoja3.createRow(i+1);
			celda = fila.createCell(0);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getUsuario());
			celda = fila.createCell(1);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getNombreUsuario());
			celda = fila.createCell(2);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getFecha());
			celda = fila.createCell(3);
			celda.setCellValue("$" + listaDetalleTrasferencias.get(i).getImporteString());
			celda = fila.createCell(4);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getAlias());
			celda = fila.createCell(5);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getIdtransferencia());
			celda = fila.createCell(6);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getTipoPagos());
			celda = fila.createCell(7);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getFechaHistorial());
			celda = fila.createCell(8);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getDescripcion());
			celda = fila.createCell(9);
			celda.setCellValue(listaDetalleTrasferencias.get(i).getNombreUsuario());
		}
		for (int i = 0; i < cabecero2.getPhysicalNumberOfCells(); hoja3.autoSizeColumn(i++));

		SXSSFWorkbook libroDescarga = new  SXSSFWorkbook(libro,1000);
        response.setHeader("Content-Disposition", "attachment; filename=reporteUsuario.xlsx");
        ServletOutputStream out;
		try {
			out = response.getOutputStream();
			libroDescarga.write(out);
	        out.flush();
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ActionForward transferenciasByEstadoDeCuenta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String banco=request.getParameter(ConstantesReportes.BANCO);
		String importe = request.getParameter(ConstantesReportes.IMPORTE);
		String marca = request.getParameter(ConstantesReportes.MARCA);
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			
			request.setAttribute(ConstantesReportes.PANELID, "transferenciasByEstadoDeCuenta");
			request.setAttribute(ConstantesReportes.TITULO, "Reporte de Estados de Cuenta");

			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(ConstantesReportes.TDCI);
			List<BancoPojo> listaBancos = myDao.getBancos();
			request.setAttribute(ConstantesReportes.LISTABANCOS, listaBancos);
			return mapping.findForward("transferenciasByEstadoDeCuenta");

		}
			
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.BANCO, banco);
		request.setAttribute(ConstantesReportes.IMPORTE, importe);
		request.setAttribute(ConstantesReportes.MARCA, marca);
	
			
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate=null;
			
		if (fecha != null) {

			try {

				fechaDate = new java.sql.Date((formatoFinal.parse(fecha))
							.getTime());
			} catch (Exception e) {

				fechaDate = new java.sql.Date(new Date().getTime());
			}

		} else {

			fechaDate = new java.sql.Date(new Date().getTime());
		}
			
		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				parseado = Double.parseDouble(importe);
				DecimalFormat formateador = new DecimalFormat(ConstantesReportes.FORMATODECIMAL);
				importe=formateador.format(parseado);
			} catch (IllegalArgumentException e) {
				importe = null;
			}
		}

		if(banco!=null){
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoConsultaInterface myDao = (TransferenciasDaoConsultaInterface) context.getBean(ConstantesReportes.TDCI);
			List<BancoPojo> listaBancos = myDao.getBancos();
			request.setAttribute(ConstantesReportes.LISTABANCOS, listaBancos);
			EmpleadoPojo empleadoPojo = (EmpleadoPojo)request.getSession().getAttribute("empleado");
			TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
			List<TransferenciaPojo> listaTrasferencias = myDao2.getTransferenciasByEstadoDeCuenta(Integer.parseInt(banco), fechaDate, importe, marca, empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);	
		}
			 
		if(request.getParameter(ConstantesReportes.EXCEL)==null){
			return mapping.findForward("listaEstadoDeCuenta");
		}
		request.setAttribute("bancoInt", Integer.parseInt(banco));
		return mapping.findForward("transferenciasEstadoDeCuentaExcel");			
			
	}
	
	public ActionForward transferenciasByPagosNoIdent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String fecha=request.getParameter(ConstantesReportes.FECHA);
	
		String importe = request.getParameter(ConstantesReportes.IMPORTE) == null ? "0.0" : request.getParameter(ConstantesReportes.IMPORTE);
		String lote = request.getParameter(ConstantesReportes.LOTE) == null ? "0" : request.getParameter(ConstantesReportes.LOTE);
		String region = request.getParameter("region");
		
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			
			request.setAttribute(ConstantesReportes.PANELID, "transferenciasByPagosNoIdent");
			request.setAttribute(ConstantesReportes.TITULO, "Reporte de Pagos No Identificados");
			
			return mapping.findForward("transferenciasByPagosNoIdent");

		}else if(request.getParameter(ConstantesReportes.FILTROS).equals("PA")){
			
			request.setAttribute(ConstantesReportes.FECHA, fecha);
			request.setAttribute(ConstantesReportes.LOTE, lote);
			request.setAttribute(ConstantesReportes.IMPORTE, importe);
			request.setAttribute("region", region);
			request.setAttribute("multiple", "OK");
			
			Object lista = request.getSession().getAttribute("listaPagosNoIdent");
			
			if(lista !=null){
				request.setAttribute(ConstantesReportes.LISTATRANS, lista);
				return mapping.findForward("listaPagosNoIdentificados");
			}
			return mapping.findForward("listaPagosNoIdentificados");
			
		}else {
			request.setAttribute(ConstantesReportes.FECHA, fecha);
			request.setAttribute(ConstantesReportes.LOTE, lote);
			request.setAttribute(ConstantesReportes.IMPORTE, importe);
			request.setAttribute("region", region);
			boolean  multiple = region.equals("") ? true : false;
			
			if(multiple){
				
				Object lista = request.getSession().getAttribute("listaPagosNoIdent");
				request.setAttribute(ConstantesReportes.LISTATRANS, lista);
				return mapping.findForward("pagosNoIdentExcel");
			}
			
			SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
			java.sql.Date fechaDate=null;
			
			if (fecha != null) {

				try {

					fechaDate = new java.sql.Date((formatoFinal.parse(fecha))
							.getTime());
				} catch (Exception e) {

					fechaDate = new java.sql.Date(new Date().getTime());
				}

			} else {

				fechaDate = new java.sql.Date(new Date().getTime());
			}
			
		
			
				importe = importe.replace(",", "");
				importe = importe.replace("$", "");
				importe = importe.replace("-", "");
				importe =importe.trim();
				Double importeParseado = null;
				double importeLote=0.0;
				try {
					 importeParseado = Double.parseDouble(importe);
					 DecimalFormat formateador = new DecimalFormat(ConstantesReportes.FORMATODECIMAL);
					 importe=formateador.format(importeParseado);
					 importeLote = Double.valueOf(importe);
				} catch (IllegalArgumentException  e) {
					 importe = "0.0";
					 importeLote = -1.0;
				}
				int loteM2K;
				try{
					loteM2K = Integer.parseInt(lote);
				}catch(Exception e){
					loteM2K = 0;
				}
	
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			
			TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
			List<TransferenciaPojo> listaTrasferencias = new ArrayList <TransferenciaPojo>(); 
			EmpleadoPojo empleadoPojo = (EmpleadoPojo)request.getSession().getAttribute("empleado");
			TransferenciaPojo pago =	myDao2.getTransferenciasByPagosNoIdent(region, loteM2K, fechaDate,empleadoPojo.getDescRegion());
			
			if(pago == null){
        		pago = new TransferenciaPojo();
        		pago.setImporte(0.0);
        	}
			
			double importeTotal;
			try{
			 DecimalFormat formateador = new DecimalFormat(ConstantesReportes.FORMATODECIMAL);
			 String importeString = formateador.format(pago.getImporte());
			 importeTotal = Double.valueOf(importeString);
			}catch(Exception ex){
				importeTotal = 0.0;
			}
			 
			pago.setFecha(fecha);
			pago.setLote(lote);
			pago.setRegion(region);
			
			BigDecimal impT = new BigDecimal(importeTotal);
			BigDecimal impL = new BigDecimal(importeLote);
			impT = impT.setScale(0,BigDecimal.ROUND_CEILING);
			impL = impL.setScale(0,BigDecimal.ROUND_CEILING);
			
			if(!impT.equals(impL)){
				pago.setImporte(0.0);
				pago.setImporteTransString(importe);
			}
			
			listaTrasferencias.add(pago);
			request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);	
			 
			if(request.getParameter(ConstantesReportes.EXCEL)==null){
				return mapping.findForward("listaPagosNoIdentificados");
			}
			return mapping.findForward("pagosNoIdentExcel");		
						
		}

	}
	
	public ActionForward addComentario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		if(request.getParameter("filtro")==null){
			
			String idtransferencia = request.getParameter(ConstantesReportes.IDTRANSFERENCIA);
			
			String importe = (String)request.getParameter(ConstantesReportes.IMPORTE);
			String fecha = (String)request.getParameter(ConstantesReportes.FECHA);
			String marca = (String)request.getParameter(ConstantesReportes.MARCA);
			String banco = (String)request.getParameter(ConstantesReportes.BANCO);
			
			request.setAttribute(ConstantesReportes.IDTRANSFERENCIA, idtransferencia);
			request.setAttribute(ConstantesReportes.FECHA, fecha);
			request.setAttribute(ConstantesReportes.MARCA, marca);
			request.setAttribute(ConstantesReportes.IMPORTE, importe);
			request.setAttribute(ConstantesReportes.BANCO, banco);
			
		
			return mapping.findForward("marcaTransferencia");
			
		}else if(request.getParameter("filtro").equals("ok")){
			
			String tipoMarca = request.getParameter("tipoM");
			String idTransferencia = request.getParameter(ConstantesReportes.IDTRANSFERENCIA);
			String comentario = "MARCADA "+ tipoMarca + " " + request.getParameter("comentario");		
			
			String fecha = (String)request.getParameter(ConstantesReportes.FECHA);
			String banco = (String)request.getParameter(ConstantesReportes.BANCO);
			String importe = (String)request.getParameter(ConstantesReportes.IMPORTE);
			String marca = (String)request.getParameter(ConstantesReportes.MARCA);
			
			request.setAttribute(ConstantesReportes.FECHA, fecha);
			request.setAttribute(ConstantesReportes.BANCO, banco);
			request.setAttribute(ConstantesReportes.IMPORTE, importe);
			request.setAttribute(ConstantesReportes.MARCA, marca);
			
			
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
			Integer idEmpleado=empleado.getIdEmpleado();
				
			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
			
			myDao2.insertaHistorial(idEmpleado, ConstantesNumeros.QUINCE,Integer.parseInt(idTransferencia), comentario);
			
			return mapping.findForward("marcaTransferenciaResultado");	
			
		}
			
		String fecha = (String)request.getParameter(ConstantesReportes.FECHA);
		String banco = (String)request.getParameter(ConstantesReportes.BANCO);
		String importe = (String)request.getParameter(ConstantesReportes.IMPORTE);
		String marca = (String)request.getParameter(ConstantesReportes.MARCA);
			
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.BANCO, banco);
		request.setAttribute(ConstantesReportes.IMPORTE, importe);
		request.setAttribute(ConstantesReportes.MARCA, marca);

			
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate=null;
			
		if (fecha != null) {

			try {

				fechaDate = new java.sql.Date((formatoFinal.parse(fecha))
							.getTime());
			} catch (Exception e) {

				fechaDate = new java.sql.Date(new Date().getTime());
			}

		} else {

			fechaDate = new java.sql.Date(new Date().getTime());
		}
			
		if(importe.equals("")){
			importe = null;
		}
			
			
		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				parseado = Double.parseDouble(importe);
				DecimalFormat formateador = new DecimalFormat(ConstantesReportes.FORMATODECIMAL);
				importe=formateador.format(parseado);
			} catch (IllegalArgumentException e) {
				importe = null;
			}
		}
			
			
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
		EmpleadoPojo empleadoPojo = (EmpleadoPojo)request.getSession().getAttribute("empleado");
		List<TransferenciaPojo> listaTrasferencias = myDao2.getTransferenciasByEstadoDeCuenta(Integer.parseInt(banco), fechaDate, importe, marca,empleadoPojo.getDescRegion());
		request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);
	
		return mapping.findForward("listaEstadoDeCuenta");
			
	}

	public ActionForward deleteComentario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			
			String idTransferencia = request.getParameter(ConstantesReportes.IDTRANSFERENCIA);
			String fecha = request.getParameter(ConstantesReportes.FECHA);
			String banco = request.getParameter(ConstantesReportes.BANCO);
			String importe = request.getParameter(ConstantesReportes.IMPORTE);
			String marca = request.getParameter(ConstantesReportes.MARCA);
			String comentario = "Desmarcada";
			
			request.setAttribute(ConstantesReportes.FECHA, fecha);
			request.setAttribute(ConstantesReportes.BANCO, banco);
			request.setAttribute(ConstantesReportes.IMPORTE, importe);
			request.setAttribute(ConstantesReportes.MARCA, marca);
			
			
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
			Integer idEmpleado=empleado.getIdEmpleado();
			
			SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
			java.sql.Date fechaDate=null;
			
			if (fecha != null) {

				try {

					fechaDate = new java.sql.Date((formatoFinal.parse(fecha))
							.getTime());
				} catch (Exception e) {

					fechaDate = new java.sql.Date(new Date().getTime());
				}

			} else {

				fechaDate = new java.sql.Date(new Date().getTime());
			}

			
			ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
			
			myDao2.actualizaHistorial(idEmpleado, ConstantesNumeros.QUINCE,Integer.parseInt(idTransferencia), comentario);
				
			if(importe.equals("")){
					importe = null;
			}
				
			if (importe != null) {

				importe = importe.replace(",", "");
				importe = importe.replace("$", "");
				importe =importe.trim();
				Double parseado = null;
				try {
					parseado = Double.parseDouble(importe);
					DecimalFormat formateador = new DecimalFormat(ConstantesReportes.FORMATODECIMAL);
					importe=formateador.format(parseado);
				} catch (IllegalArgumentException  e) {
					importe = null;
				}
			}

			List<TransferenciaPojo> listaTrasferencias = myDao2.getTransferenciasByEstadoDeCuenta(Integer.valueOf(banco), fechaDate, importe, marca,empleado.getDescRegion());
			request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);
				
			return mapping.findForward("listaEstadoDeCuenta");
	}

	
	public ActionForward pagosByEstatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		request.setAttribute(ConstantesReportes.CONCENTRADO, request.getParameter(ConstantesReportes.CONCENTRADO));
		request.setAttribute(ConstantesReportes.FECHA, request.getParameter(ConstantesReportes.FECHA));
		
		String fecha=request.getParameter(ConstantesReportes.FECHA);			
		String banco=request.getParameter(ConstantesReportes.BANCO);	
		
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute("nombreBanco", request.getParameter("nombreBanco"));
		request.setAttribute(ConstantesReportes.BANCO, banco);
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy");
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
		
		if (fecha != null) {

			try {

				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
				
			} catch (Exception e) {
				fechaDate = new java.sql.Date(new Date().getTime());
			}
		} 
			
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		EmpleadoPojo empleadoPojo = (EmpleadoPojo)request.getSession().getAttribute("empleado");	
		TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
		List<TransferenciaPojo> listaTransferencias = myDao2.getTransferenciasByBancoByFechaLote(Integer.parseInt(banco),fechaDate, fechaDate2, empleadoPojo.getDescRegion());
			
		sesion.setAttribute("listaTransferencias", listaTransferencias);
		sesion.setAttribute("bancoReporte", Integer.parseInt(banco));
		sesion.setAttribute("fechaDateReporte", fechaDate);
		sesion.setAttribute("regionReporte", empleadoPojo.getIdRegion());
			
		if(request.getParameter(ConstantesReportes.EXCEL)==null){
			return mapping.findForward("listaPagosReporte");
		}
		
		return mapping.findForward("listaPagosReporteExcel");

	}
	
	public ActionForward busquedaReporte(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Entro++++++");
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		String correo =request.getParameter("correo");
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
		log.info("correo "+correo);
		log.info("bancoReporte "+sesion.getAttribute("bancoReporte"));
		log.info("fechaDateReporte "+sesion.getAttribute("fechaDateReporte"));
		log.info("regionReporte "+ sesion.getAttribute("regionReporte"));
		log.info("nombreUsuarioReporte "+empleadoPojo.getNombre());
		String idBanco = sesion.getAttribute("bancoReporte").toString();
		String fecha = sesion.getAttribute("fechaDateReporte").toString();
		String idRegion = sesion.getAttribute("regionReporte").toString();
		String usuario = empleadoPojo.getNombre().toString();
		String parametro = idBanco+"/"+fecha+"/"+idRegion+"/"+usuario;
		log.info("Parametro "+parametro);
		Date inicio=new Date(); 
		String mensaje="";
 	   	if (!correo.equals("")) {
	 	   	if (correo.endsWith("@mail.telcel.com") || correo.endsWith("@telcel.com")) {
		 	   	TransferenciasDesglosadasPojo ctd=new TransferenciasDesglosadasPojo();
		 	   	ctd.setCorreo(correo);
		 	   	ctd.setParametro(parametro);
		 	   	ctd.setEstatus("PR");
		 	   	ctd.setRegion(idRegion);
		 	   	ctd.setInicio(formatoFinal.format(inicio));
		 	   if (myDao3.insertaTransferenciasDesglosadas(ctd)) {
				   mensaje="Su solicitud se proceso satisfactoriamente, se enviara correo en unos minutos con la informacion";
			   }else {
				   mensaje="No fue posible procesar su solicitud, favor de intentarlo de nuevo, si el problema persiste comuniquese al correo sisfactsipab@mail.telcel.com ";
			   }
			}else {
				mensaje="Error en la solicitud, el correo debe pertenecer a TELCEL (terminacion @telcel.com o @mail.telcel.com)";    
			}
		}else {
			mensaje="Error en la solicitud, el campo de correo no puede ir vacio";
		}
 	   log.info("Mensaje "+mensaje);
 	   request.setAttribute("mensaje", mensaje);
 	   return mapping.findForward("resultadoListaTransferencia");

	}
	
	public ActionForward reporteCiclo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Entro a reporteCiclo");
		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			request.getSession().removeAttribute(ConstantesReportes.LISTACICLOS);
			request.setAttribute(ConstantesReportes.PANELID, "reporteCiclo");
			request.setAttribute(ConstantesReportes.TITULO, "Reporte por ciclo");
			return mapping.findForward("reporteCiclo");
		}
		log.info("Grafica ciclos");
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
		
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
		
		if (fecha != null) {
			try {
				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
				} catch (Exception e) {
					fechaDate = new java.sql.Date(new Date().getTime());
				}
		} 
		if (fecha2 != null) {
				try {
					if (fecha2.equals("undefined")) {
						fechaDate2 = new java.sql.Date(new Date().getTime());
					}
					else {
						fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2)).getTime());
					}
				} catch (Exception e) {
					fechaDate2 = null;
				}
		} else {
			fechaDate2 = new java.sql.Date(new Date().getTime());
		}
		
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
		
		List<CicloPojo> ciclos = myDao2.getReporteCiclos(fechaDate, fechaDate2);
		int total = 0;
		String data = "[";
		for (CicloPojo ciclo : ciclos) {
			total += ciclo.getCantidad();
			data += "{x: '" + ciclo.getCiclo() + "', value: " + ciclo.getCantidad() + "},"; 
		}
		for (CicloPojo ciclo : ciclos) {
			ciclo.setPorcentaje((double)Math.round((ciclo.getCantidad() * 100 / total) * 100d) / 100d);
		}
		
		data += "]";
		request.setAttribute("data", data);
		request.setAttribute(ConstantesReportes.LISTACICLOS, ciclos);
		return mapping.findForward("graficaCiclos");
	}
	
	public ActionForward reportePeticiones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Entro a reportePeticiones");
		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);
		
		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			request.getSession().removeAttribute(ConstantesReportes.LISTAPETICIONES);
			request.setAttribute(ConstantesReportes.PANELID, "reportePeticiones");
			request.setAttribute(ConstantesReportes.TITULO, "Reporte de peticiones");
			return mapping.findForward("reportePeticiones");
		}
		log.info("Lista peticiones");
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
		
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
		
		if (fecha != null) {
			try {
				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
				} catch (Exception e) {
					fechaDate = new java.sql.Date(new Date().getTime());
				}
		} 
		if (fecha2 != null) {
				try {
					if (fecha2.equals("undefined")) {
						fechaDate2 = new java.sql.Date(new Date().getTime());
					}
					else {
						fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2)).getTime());
					}
				} catch (Exception e) {
					fechaDate2 = null;
				}
		} else {
			fechaDate2 = new java.sql.Date(new Date().getTime());
		}
		
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
		
		List<PeticionPojo> listaPeticiones = myDao2.getReportePeticiones(fechaDate, fechaDate2);
		List<PeticionPojo> peticiones = new ArrayList<PeticionPojo>();
		PeticionPojo peticionAgrupada = null;
		for (PeticionPojo peticion : listaPeticiones) {
			if (peticionAgrupada == null) {
				peticionAgrupada = peticion;
				peticionAgrupada.setCantidad(1);
			}
			else {
				if (peticionAgrupada.getAcesor().equals(peticion.getAcesor())) {
					peticionAgrupada.setCantidad(peticionAgrupada.getCantidad() + 1);
					peticionAgrupada.setMonto(peticionAgrupada.getMonto() + peticion.getMonto()); 
				}
				else {
					peticiones.add(peticionAgrupada);
					peticionAgrupada = peticion;
					peticionAgrupada.setCantidad(1);
				}
			}
		}
		peticiones.add(peticionAgrupada);
		
		for(PeticionPojo peticion : peticiones) {
			peticion.setMonto(new BigDecimal(peticion.getMonto()).setScale(2,RoundingMode.HALF_UP).doubleValue());
		}
		
		request.setAttribute(ConstantesReportes.LISTAPETICIONES, peticiones);
		return mapping.findForward("listaPeticiones");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward pagosByUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String usuario=request.getParameter(ConstantesReportes.USUARIO);
		Map<String, List<TransferenciaPojo>> mapaTransferencias = (Map<String, List<TransferenciaPojo>>) request.getSession().getAttribute(ConstantesReportes.LISTATRANSDETALLE);
		List<TransferenciaPojo> listaTrasferencias = mapaTransferencias.get(usuario);
		request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);
		return mapping.findForward("listaTransferenciasByUser");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward pagosByUsuarioRechazos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String usuario=request.getParameter(ConstantesReportes.USUARIO);
		Map<String, List<TransferenciaPojo>> mapaTransferencias = (Map<String, List<TransferenciaPojo>>) request.getSession().getAttribute(ConstantesReportes.LISTATRANSDETALLE);
		List<TransferenciaPojo> listaTrasferencias = mapaTransferencias.get(usuario);
		request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);
		return mapping.findForward("listaTransferenciasRechazadas");
	}
	
	public ActionForward reporteTransIngresos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Entro a reporte Trans Ingresos");
		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);			
		Calendar calendarAux = Calendar.getInstance();

		if (request.getParameter(ConstantesReportes.EXCEL) != null) {
			if (request.getSession().getAttribute("mensaje").equals("OK")) {
				
				java.util.Date fechaCreacion= new java.util.Date();
				SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
				request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fechaCreacion));
				
				return mapping.findForward("listaTransIngresosExcel");
			}
		}
		
		if(request.getParameter(ConstantesReportes.FILTROS)==null){
			request.getSession().removeAttribute(ConstantesReportes.LISTATRANSINGRESOS);
			request.setAttribute(ConstantesReportes.PANELID, "reporteTransIngresos");
			request.setAttribute(ConstantesReportes.TITULO, "Reporte de tranferencias ingresos");
			return mapping.findForward("reporteTransIngresos");
		}		
		
		log.info("Lista TransIngresos");
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
		
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATOFECHA);
		java.sql.Date fechaDate2=null;
		java.sql.Date fechaDate=null;
		
		if (fecha != null) {
			try {
				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
				} catch (Exception e) {
					fechaDate = new java.sql.Date(new Date().getTime());
				}
		} 
		if (fecha2 != null) {
				try {
					if (fecha2.equals("undefined")) {
						fechaDate2 = new java.sql.Date(new Date().getTime());
					}
					else {
						fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2)).getTime());
					}
				} catch (Exception e) {
					fechaDate2 = null;
				}
		} else {
			fechaDate2 = new java.sql.Date(new Date().getTime());
		}
		
		long diferenciaFechas  = (fechaDate2.getTime() - fechaDate.getTime())/86400000;
		
		if(fechaDate.compareTo(new Date()) > 0) {
			request.setAttribute("mensaje", "La fecha Inicio no debe ser mayor a la actual.");
			return mapping.findForward("listaTransIngresos");
		}
		
		if(fechaDate2.compareTo(new Date()) > 0) {
			request.setAttribute("mensaje", "La fecha fin no debe ser mayor a la actual.");
			return mapping.findForward("listaTransIngresos");
		}
				
		if(diferenciaFechas > 31) {
			request.setAttribute("mensaje", "El periodo de fechas no debe ser mayor a 31 dias.");
			return mapping.findForward("listaTransIngresos");
		}
		
		if(fechaDate.compareTo(fechaDate2) > 0) {
			request.setAttribute("mensaje", "La fecha Inicio no debe ser mayor a la fecha fin.");
			return mapping.findForward("listaTransIngresos");
		}
		
		calendarAux.setTimeInMillis(fechaDate2.getTime());
		calendarAux.add(Calendar.DATE,1);
		fechaDate2 = new java.sql.Date(calendarAux.getTime().getTime());
			
		ApplicationContext  context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean(ConstantesReportes.TDUI);
		
		List<TransferenciaPojo> listaTransIngresos = myDao2.getReporteTransIngresos(fechaDate, fechaDate2);
		
		if(listaTransIngresos == null) {
			request.setAttribute("mensaje", "Hubo un problema al recuperar la informacion. Intente de nuevo o mas tarde.");
			return mapping.findForward("listaTransIngresos");
		}
		
		if(listaTransIngresos.size() == 0) {
			request.setAttribute("mensaje", "Los criterios de busqueda no arrojaron resultados.");
			return mapping.findForward("listaTransIngresos");
		}
		
		request.getSession().setAttribute(ConstantesReportes.LISTATRANSINGRESOS, listaTransIngresos);
		request.getSession().setAttribute("mensaje", "OK");
		
		return mapping.findForward("listaTransIngresos");
	}	
		
}
