package com.telcel.portal.actions;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.telcel.portal.dao.implementacion.TransDaoDmpImp2;
import com.telcel.portal.dao.implementacion.TransferenciasDaoDmpImp;
//import com.telcel.dsc.sipab3.bsnGUI.RevisarLotes;
import com.telcel.portal.dao.interfaces.TransDaoInterface2;
import com.telcel.portal.dao.interfaces.TransferenciasDaoConsultaInterface;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface;
import com.telcel.portal.dao.interfaces.TransferenciasDaoUtilInterface;
import com.telcel.portal.pojos.AlertasPojo;
import com.telcel.portal.pojos.DetallePojo;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.EstatusPojo;
import com.telcel.portal.pojos.HistorialPojo;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.PeticionesPojo;
import com.telcel.portal.pojos.RespuestaPagosPojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.pojos.TransferenciasDesglosadasPojo;
import com.telcel.portal.seguridad.DatosUsuario;
import com.telcel.portal.servicios.PromesaPago;
import com.telcel.portal.util.Constantes;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.ConstantesReportes;
import com.telcel.portal.util.ConstantesTemplate;
import com.telcel.portal.util.TransferenciasUtil;


/**
 * @version 1.0
 * @author
 */
public class TemplateAction extends DispatchAction {
	Logger log = Logger.getLogger(TemplateAction.class);
	private TransferenciasDaoConsultaInterface myDao;
	private TransferenciasDaoUtilInterface myDao2;
	private TransferenciasDaoInterface myDao3;
	private TransDaoInterface2 myDao4;

	public ActionForward template(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());

		return mapping.findForward("viewport");
	}

	public ActionForward cargaTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){

		request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());

		request.setAttribute(ConstantesTemplate.PANELID, "cargaTransferencia");
		request.setAttribute(ConstantesTemplate.TITULO, "Carga Transferencia");

		return mapping.findForward("carga");

	}

	public ActionForward autorizaTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());
		request.setAttribute(ConstantesTemplate.PANELID, "autorizaTransferencia");
		request.setAttribute(ConstantesTemplate.TITULO, "Autoriza Transferencia");
		
		return mapping.findForward("autorizaTransferencia");

	}
	public ActionForward dividirTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());
		request.setAttribute(ConstantesTemplate.PANELID, "dividirTransferencia");
		request.setAttribute(ConstantesTemplate.TITULO, "Dividir Transferencia");
	
		return mapping.findForward("dividirTransferencia");

	}

	public ActionForward listaTrasferenciaAutoriza(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		
		String fecha;
		String fecha2;
		String banco;
		SimpleDateFormat formatoFinal;
		List<TransferenciaPojo> listaTrasferencia;
		String importe;
		java.sql.Date fechaDate;

		fecha = request.getParameter(ConstantesTemplate.FECHA);
		fecha2=request.getParameter("fecha2");
		banco = request.getParameter(ConstantesTemplate.BANCO);
		importe = request.getParameter(ConstantesTemplate.IMPORTE);

		formatoFinal = new SimpleDateFormat(ConstantesTemplate.DDMMYYYY);

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
		
		
		java.sql.Date fechaDate2;
		if (fecha2 != null) {

			try {

				fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
						.getTime());
			} catch (Exception e) {

				fechaDate2 = null;
			}

		} else {

			fechaDate2 = null;
		}

		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				 parseado = Double.parseDouble(importe);
				 DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				 importe=formateador.format(parseado);
			} catch (IllegalArgumentException  e) {
				importe = null;
			}
		}

		if (banco != null) {
			EmpleadoPojo empleadoPojo=(EmpleadoPojo) request.getSession().getAttribute("empleado");
			listaTrasferencia = myDao.getTransferencias(
					Integer.parseInt(banco), fechaDate,fechaDate2, Constantes.CARGADA, importe,empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesTemplate.LISTATRANSFERENCIA, listaTrasferencia);
		}

	
	return mapping.findForward("listaTransferenciasAutoriza");

	}
	
	public ActionForward listaTrasferenciaDividida(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String fecha;
		String fecha2;
		String banco;
		SimpleDateFormat formatoFinal;
		List<TransferenciaPojo> listaTrasferencia;
		String importe;
		java.sql.Date fechaDate;
		fecha = request.getParameter("fechaIni");
		fecha2=request.getParameter("fechaFin");
		banco = request.getParameter(ConstantesTemplate.BANCO);
		importe = request.getParameter(ConstantesTemplate.IMPORTE);
		formatoFinal = new SimpleDateFormat(ConstantesTemplate.DDMMYYYY);
		if (fecha != null) {
			try {
				fechaDate = new java.sql.Date((formatoFinal.parse(fecha)).getTime());
			} catch (Exception e) {
                 fechaDate = new java.sql.Date(new Date().getTime());
			}

		} else {

			fechaDate = new java.sql.Date(new Date().getTime());
		}
		java.sql.Date fechaDate2;
		if (fecha2 != null) {
			try {
				fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
						.getTime());
			} catch (Exception e) {
				fechaDate2 = null;
			}

		} else {

			fechaDate2 = null;
		}

		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				 parseado = Double.parseDouble(importe);
				 DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				 importe=formateador.format(parseado);
			} catch (IllegalArgumentException  e) {
				importe = null;
			}
		}
		if (banco != null) {
			EmpleadoPojo empleadoPojo=(EmpleadoPojo) request.getSession().getAttribute("empleado");
			listaTrasferencia = myDao.getTransferenciasCargadasAutorizadas(
					Integer.parseInt(banco), fechaDate,fechaDate2, Constantes.CARGADA, Constantes.AUTORIZADA,importe,empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesTemplate.LISTATRANSFERENCIA, listaTrasferencia);
		}

		return mapping.findForward("listaTransferenciaDividida");


	}
	public ActionForward dividirTransferenciaExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id_Transferencia=request.getParameter("id_Transferencia");
		String importe=request.getParameter("importe"+id_Transferencia);
		request.setAttribute("importe", importe);
		request.setAttribute("id_Transferencia", id_Transferencia);
		List<Integer> listaCantidad=new ArrayList<Integer>();
		int cantidad=Integer.parseInt(request.getParameter("cantidadDividir"));
		for(int i=1;i<=cantidad;i++){
			listaCantidad.add(i);
		}
		request.setAttribute("listaCantidad", listaCantidad);
		return mapping.findForward("cantidadDivision");	
	}
	
	public ActionForward dividirCerrarTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		log.info("JLM:3");
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
		String mensaje="";
		String id_Transferencia=request.getParameter("id_Transferencia");
		int cantidad=Integer.parseInt(request.getParameter("total"));
		String comentario=request.getParameter("comentario");
		double suma=0.00d;
		TransDaoDmpImp2 dao2=new TransDaoDmpImp2();
		
		
	
		double impDoublePeqSuma=0.0;
		int impEnteroSuma=0;
		double impDoublePeq=0.0;
		int impEntero=0;

		try{
			TransferenciaPojo pojo=dao2.consultaTransferenciaxId(id_Transferencia);
			if (pojo==null) {
				throw new Exception("El pojo es null");
		     }else{
		    	 double total=pojo.getImporte();
		         List<TransferenciaPojo> lista=TransferenciasUtil.clonarTransferenciaPojo(pojo, cantidad);
		         int n=1;
		         for (TransferenciaPojo transferenciaPojo : lista) {
		        	 
		        	 String importString=request.getParameter("numero"+n++); 	 
		        	 double importe=Double.parseDouble(importString);	
		        	
		        	int boton=importString.indexOf(".");
		        	log.info("BOTON EN: "+boton);
		        	if (boton==-1) {
		        		impEntero=Integer.parseInt(importString);
		        		log.info("ENTERO impEntero No hay decimales: "+impEntero);
					}else{
						impEntero=Integer.parseInt(importString.substring(0,boton ));
						impDoublePeq=Double.parseDouble(importString.substring(boton));
						log.info("ENTERO impEntero: "+impEntero +" Decimal: "+impDoublePeq);
					}
		        	 
		         	 impDoublePeqSuma=impDoublePeqSuma+impDoublePeq;
		     		 impEnteroSuma=impEnteroSuma+impEntero;       
		        	 
		        	 if(importe<0.01){
		        		 log.info("EL IMPORTE DE UNO DE LOS CAMPOS EN EL DESGLOSE ES MENOR O IGUAL A CERO");
		        		throw new Exception("NO DEBE HABER CANTIDADES EN EL DESGLOSE MENOR O IGUAL A CERO");
		        	 }
		        	 transferenciaPojo.setImporte(importe);
			         suma=suma+importe;
		          }
		         double sumaX=impDoublePeqSuma+impEnteroSuma;
		         log.info("la ANTES suma: "+suma);
		         log.info("la NUEVO sumaX: "+sumaX);
	        	 log.info("total: "+total);
	        	 if (sumaX!=suma) {
	        		 log.info(" JLM_SUMA_ADVERTENCIA: Sumas Diferentes, Esto nunca deberia pasar ");	 
				}
	        
	        	 
	        	
		         if (sumaX==total) {
		        	 log.info("la suma coincide con el importe "+sumaX);
			         String[] ids={id_Transferencia};
			         if (!("Transferencia Cerrada".equals(dao2.setCierraTransferencias(ids, empleado.getIdEmpleado(), comentario)))) {
			        	 mensaje="ERROR:No se pudo cerrar la transferencia y tampoco dividir favor de intentar de nuevo";
			          } else {
			        	  TransferenciasDaoDmpImp dao=new TransferenciasDaoDmpImp();
			        	  if(!("Transacciones Cargadas correctamente".equals(dao.insertaTransferencias(lista, pojo.getEstatus(), pojo.getRegion())))) {	        				      
			        	  dao2.regresarTransferencia(Integer.parseInt(id_Transferencia), empleado.getIdEmpleado(), comentario, pojo.getEstatus());
			        		  mensaje="ERROR: Ocurrio un error al Intentar insertar las nuevas referencias";
			        	   } else {
			        		   mensaje="RESULTADO EXITOSO: Se cerro la transferencia y creo la division correctamente";
			        	 	}
		              }
			        } else {
			        	log.info("ERROR: La suma NO coincide VALOR SUMA:"+sumaX+" VALOR IMPORTE TOTAL:"+total);
			        	mensaje="ERROR:La suma de los desgloses deben coincidir con el importe total";
			         }
		         }//END ELSE SE EJECUTA SI EL POJO NO ES NULL
			}catch(Exception e){
				log.info("ERROR: Ocurrio una falla tecnica en el proceso, favor de intentar de nuevo: "+e.getMessage());
				mensaje="ERROR: Ocurrio una falla tecnica en el proceso, favor de intentar de nuevo: "+e.getMessage();
		    }
		request.setAttribute("mensaje", mensaje);
		return mapping.findForward("resultadoDividirCerrar");
		
		
	}
	
	public ActionForward autorizaTransferenciaExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] transferenciasIds;
		String resultado = "";
		HttpSession sesion;
		EmpleadoPojo empleado;

		transferenciasIds = request.getParameterValues(ConstantesTemplate.AUTORIZA);
		
		resultado = "Autorizacion efectuada con exito";

		if (transferenciasIds != null && transferenciasIds.length >= 1) {

			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, transferenciasIds[0]);
			sesion=request.getSession();
			empleado=(EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			myDao3.setAutorizaTransferencias(transferenciasIds,empleado.getIdEmpleado());
		}

		request.setAttribute(ConstantesTemplate.RESULTADO, resultado);

		if (request.getParameter(ConstantesTemplate.DETALLE) != null) {

			return (mapping.findForward("detalleTransferenciaResultado"));
		}
		return mapping.findForward("autorizaTransferenciaResultado");

	}

	public ActionForward aplicaTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());
		request.setAttribute(ConstantesTemplate.PANELID, "aplicaTransferencia");
		request.setAttribute(ConstantesTemplate.TITULO, "Aplica Transferencia");

		return mapping.findForward("aplicaTransferencia");

	}

	public ActionForward aplicaListaTransferencias(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String fecha;
		String fecha2;
		String banco;
		SimpleDateFormat formatoFinal;
		List<TransferenciaPojo> listaTrasferencia;
		String importe;
		java.sql.Date fechaDate;

		fecha = request.getParameter(ConstantesTemplate.FECHA);
		fecha2 = request.getParameter("fecha2");
		banco = request.getParameter(ConstantesTemplate.BANCO);
		importe = request.getParameter(ConstantesTemplate.IMPORTE);

		formatoFinal = new SimpleDateFormat(ConstantesTemplate.DDMMYYYY);

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
		java.sql.Date fechaDate2;
		if (fecha2 != null) {

			
			try {

				fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
						.getTime());
			} catch (Exception e) {

				fechaDate2 = null;
			}

		} else {

			fechaDate2 =null;
		}
		

		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				parseado = Double.parseDouble(importe);
				
				DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				importe=formateador.format(parseado);
			} catch (IllegalArgumentException  e) {
				importe = null;
			}
		}

		if (banco != null) {
			EmpleadoPojo empleadoPojo=(EmpleadoPojo) request.getSession().getAttribute("empleado");
			listaTrasferencia = myDao.getTransferencias(
					Integer.parseInt(banco), fechaDate,fechaDate2,Constantes.DESGLOSADA, importe,empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesTemplate.LISTATRANSFERENCIA, listaTrasferencia);
		}


		request.setAttribute(ConstantesTemplate.FECHA, fecha);
		request.setAttribute(ConstantesTemplate.BANCO, banco);
		request.setAttribute(ConstantesTemplate.IMPORTE, importe);
		return mapping.findForward("aplicaTransferenciaLista");

	}
	
	public ActionForward compensaListaTransferencias(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String fecha;
		String fecha2;
		String banco;
		SimpleDateFormat formatoFinal;
		List<TransferenciaPojo> listaTrasferencia;
		String importe;
		java.sql.Date fechaDate;

		fecha = request.getParameter(ConstantesTemplate.FECHA);
		fecha2 = request.getParameter("fecha2");
		banco = request.getParameter(ConstantesTemplate.BANCO);
		importe = request.getParameter(ConstantesTemplate.IMPORTE);

		formatoFinal = new SimpleDateFormat(ConstantesTemplate.DDMMYYYY);

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
		java.sql.Date fechaDate2;
		if (fecha2 != null) {

			
			try {

				fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2))
						.getTime());
			} catch (Exception e) {

				fechaDate2 = null;
			}

		} else {

			fechaDate2 =null;
		}
		

		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				parseado = Double.parseDouble(importe);
				
				DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				importe=formateador.format(parseado);
			} catch (IllegalArgumentException  e) {
				importe = null;
			}
		}

		if (banco != null) {
			EmpleadoPojo empleadoPojo=(EmpleadoPojo) request.getSession().getAttribute("empleado");
			listaTrasferencia = myDao.getTransferenciasCompensar(
					Integer.parseInt(banco), fechaDate,fechaDate2,Constantes.APLICADA, importe,empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesTemplate.LISTATRANSFERENCIA, listaTrasferencia);
		}


		request.setAttribute(ConstantesTemplate.FECHA, fecha);
		request.setAttribute(ConstantesTemplate.BANCO, banco);
		request.setAttribute(ConstantesTemplate.IMPORTE, importe);
		return mapping.findForward("compensaTransferenciaLista");

	}
	
	public ActionForward mostrarHistorial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
	    	String idTransferencia = request.getParameter(ConstantesTemplate.IDTRANSFERENCIA);
			if (idTransferencia != null) {
			
				List<HistorialPojo> listaHistorial = myDao.getHistorial(Integer.parseInt(idTransferencia));
				request.setAttribute("listaHistorial", listaHistorial);
			}
			
		return mapping.findForward("verHistorial");
		
	}
	
	public ActionForward detalleTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "";
		String idTransferencia;
		List<PagoPojo> listaPagos = null;
		DetallePojo detalle = null;
		TransferenciaPojo transferencia = null;
		List<PagoPojo> transferenciaSuma = null;
		java.util.Date fecha= new java.util.Date();
		HttpSession sesion;
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		
		
		try {

		sesion=request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
		int iAccess = myDao2.getAccesFull(empleado.getUsuario());
		request.setAttribute("iAccess", iAccess);
		request.setAttribute(ConstantesTemplate.FECHA, formatoFinal.format(fecha));
		
		idTransferencia = request.getParameter(ConstantesTemplate.IDTRANSFERENCIA);
		String tipoTrans = request.getParameter("tipoTrans"); //Se recupera la variable para el tipo de transferencia
		
		String pendiente = request.getParameter("pendiente");//Se agrega para validar en el monitor de lista pendientes
		if(pendiente != null){
			request.setAttribute("pendiente", pendiente); 
		}
		
		if(tipoTrans != null){
			request.setAttribute("tipoTrans", tipoTrans); //Se guarda la variable para el tipo de transferencia
		}
		
		if (idTransferencia != null) {
			
			detalle = myDao2.getDetallesTransferencia(Integer.parseInt(idTransferencia));
			listaPagos = detalle.getListaPagos();
			transferencia = detalle.getTransferenciaPojo();
			transferenciaSuma = detalle.getListaPagosSuma();
			request.setAttribute("estatus", transferencia.getEstatus());
			
		}

		if (request.getParameter(ConstantesTemplate.RESULTADO) != null){
			resultado = request.getParameter(ConstantesTemplate.RESULTADO);
		}
		request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
		request.setAttribute(ConstantesTemplate.LISTAPAGOS, listaPagos);
		request.setAttribute("transferenciaSuma", transferenciaSuma);		
		request.setAttribute("transferencia", transferencia);
				
		if(request.getParameter("excel")!=null){
			return mapping.findForward("detalleTransferenciaExcel");	
		}
		
	} catch (Exception e) {
			
			
	}
		return mapping.findForward("detalleTransferencia"); 

	}
	
	public ActionForward detalleTransferenciaCompensacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "";
		String idTransferencia;
		List<PagoPojo> listaPagos = null;
		DetallePojo detalle = null;
		TransferenciaPojo transferencia = null;
		List<PagoPojo> transferenciaSuma = null;
		java.util.Date fecha= new java.util.Date();
		HttpSession sesion;
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		
		
		try {

		sesion=request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
		int iAccess = myDao2.getAccesFull(empleado.getUsuario());
		request.setAttribute("iAccess", iAccess);
		request.setAttribute(ConstantesTemplate.FECHA, formatoFinal.format(fecha));
		
		idTransferencia = request.getParameter(ConstantesTemplate.IDTRANSFERENCIA);
		String tipoTrans = request.getParameter("tipoTrans"); //Se recupera la variable para el tipo de transferencia
		
		String pendiente = request.getParameter("pendiente");//Se agrega para validar en el monitor de lista pendientes
		if(pendiente != null){
			request.setAttribute("pendiente", pendiente); 
		}
		
		if(tipoTrans != null){
			request.setAttribute("tipoTrans", tipoTrans); //Se guarda la variable para el tipo de transferencia
		}
		
		if (idTransferencia != null) {
			
			detalle = myDao2.getDetallesTransferenciaCompensacion(Integer.parseInt(idTransferencia));
			listaPagos = detalle.getListaPagos();
			transferencia = detalle.getTransferenciaPojo();
			transferenciaSuma = detalle.getListaPagosSuma();
			request.setAttribute("estatus", transferencia.getEstatus());
			
		}

		if (request.getParameter(ConstantesTemplate.RESULTADO) != null){
			resultado = request.getParameter(ConstantesTemplate.RESULTADO);
		}
		List<Long> idsCompensacion = new ArrayList<>();
		boolean documentosCompletos = true;
		for(PagoPojo pagoPojo : listaPagos) {
			idsCompensacion.add(pagoPojo.getIdCompensacion());
			if(pagoPojo.getNumeroDocumento() <= 0) {
				documentosCompletos = false;
			}
		}
		
		transferencia.setIdsCompensacion(idsCompensacion);
		transferencia.setIdsCompensacionString(Arrays.toString(idsCompensacion.toArray()).replace("[", "").replace("]", ""));
		
		request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
		request.setAttribute(ConstantesTemplate.LISTAPAGOS, listaPagos);
		request.setAttribute("transferenciaSuma", transferenciaSuma);		
		request.setAttribute("transferencia", transferencia);
		request.setAttribute("documentosCompletos", documentosCompletos);

		if(request.getParameter("excel")!=null){
			return mapping.findForward("detalleTransferenciaCompensacionExcel");	
		}
		
	} catch (Exception e) {
			
			
	}
		return mapping.findForward("detalleTransferenciaCompensacion"); 

	}
	
	public ActionForward detalleTransferenciaCompleto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession sesion = request.getSession();
		List<TransferenciaPojo> listaTrasferencia = new ArrayList<TransferenciaPojo>();
		listaTrasferencia = (List<TransferenciaPojo>) sesion.getAttribute("listaTransferencia");
		
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
		int iAccess = myDao2.getAccesFull(empleado.getUsuario());
		request.setAttribute("iAccess", iAccess);
		request.setAttribute(ConstantesTemplate.FECHA, formatoFinal.format(fecha));
		
		List<DetallePojo> listaDetalle = new ArrayList<DetallePojo>();
		
		for (TransferenciaPojo transferenciaPojo : listaTrasferencia) {
			String idTransferencia;
			DetallePojo detalle = new DetallePojo();
			idTransferencia = transferenciaPojo.getIdtransferencia().toString();
			detalle = myDao2.getDetallesTransferencia(Integer.parseInt(idTransferencia));
			listaDetalle.add(detalle);
		}
		
		
		request.setAttribute("listaDetalle", listaDetalle);
		return mapping.findForward("detalleTransferenciaExcelCompleto");	

	}

	public ActionForward detalleTransferenciaExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] aplicaIds;
		String lista;
		String resultado;

		lista = request.getParameter("lista");
		aplicaIds = request.getParameterValues("aplica");
		
		if(!myDao4.consultaEstadoAplicado(aplicaIds)){
			
			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al aplicar la Transferencia");
			
			if(request.getParameter("pendiente")!=null){
				return mapping.findForward("detalleTransferenciaResultadoPendiente");
			}
			
			return mapping.findForward("detalleTransferenciaResultadoLista");
		}
	
		if (aplicaIds != null && aplicaIds.length >= 1) {
			
			//Se filtran las tranferencias tipo FA para que solo se envien al procesos las CU y MIX
			ArrayList <String>listaFA = new  ArrayList<String>();
			ArrayList <String>listaCUMIX = new  ArrayList<String>();
			
			for(int i=0; i<aplicaIds.length; i++){
				Scanner expr = new Scanner(aplicaIds[i]);
				expr.useDelimiter("_");
				String idTrans = expr.next();
				String tipoPago = expr.next();
						
				if(tipoPago.equals("FA")){
					listaFA.add(idTrans);
				}else{
					 listaCUMIX.add(idTrans);
				}
			}
				
			if(listaFA.size() > 0){
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				myDao3.setAplicaTransferenciasFA(listaFA, empleado.getIdEmpleado());
				request.setAttribute(ConstantesTemplate.RESULTADO, "Transferencia(s) aplicada");
				request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, listaFA.get(0));
			}
			
			if(listaCUMIX.size() > 0){
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				resultado = myDao3.setAplicaTransferencias(listaCUMIX,empleado.getIdEmpleado());
				request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
				request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, listaCUMIX.get(0));
			}

		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al aplicar la Transferencia");

		}
		
		if(request.getParameter("pendiente")!=null){
			return mapping.findForward("detalleTransferenciaResultadoPendiente");
		}
		
		if (lista == null){
			return mapping.findForward("detalleTransferenciaResultado");
		}
	
		return mapping.findForward("detalleTransferenciaResultadoLista");

	}
	
	public ActionForward detalleTransferenciaExecuteCompensacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] aplicaIds;
		String lista;
		String resultado;

		lista = request.getParameter("lista");
		aplicaIds = request.getParameterValues("aplica");
		
	
		if (aplicaIds != null && aplicaIds.length >= 1) {
			
			//Se filtran las tranferencias tipo FA para que solo se envien al procesos las CU y MIX
			Hashtable<String,String>listaFA = new  Hashtable<String,String>();
			Hashtable<String,String>listaCU = new Hashtable<String,String>();
			Hashtable<String,String>listaMIX = new Hashtable<String,String>();
			
			for(int i=0; i<aplicaIds.length; i++){
				Scanner expr = new Scanner(aplicaIds[i]);
				expr.useDelimiter("_");
				String idTrans = expr.next();
				String tipoPago = expr.next();
				String idsCompensacion = expr.next();
						
				if(tipoPago.equals("FA")){
					listaFA.put(idTrans,idsCompensacion);
				}else if(tipoPago.equals("MIX")) {
					listaMIX.put(idTrans,idsCompensacion);
				}else{
					listaCU.put(idTrans,idsCompensacion);
				}
			}
				
			if(listaFA.size() > 0){
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				myDao3.setAplicaTransferenciasCompensacion(listaFA, empleado.getIdEmpleado(), "CF");
				request.setAttribute(ConstantesTemplate.RESULTADO, "Transferencia(s) programada(s)");
				request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, listaFA.get(0));
			}
			
			if(listaCU.size() > 0){
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				resultado = myDao3.setAplicaTransferenciasCompensacion(listaCU,empleado.getIdEmpleado(), "CC");
				request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
				request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, listaCU.get(0));
			}
			
			if(listaMIX.size() > 0){
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				resultado = myDao3.setAplicaTransferenciasCompensacion(listaMIX,empleado.getIdEmpleado(), "CM");
				request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
				request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, listaMIX.get(0));
			}

		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al aplicar la Transferencia");

		}
		
		if(request.getParameter("pendiente")!=null){
			return mapping.findForward("detalleTransferenciaResultadoPendiente");
		}
		
		if (lista == null){
			return mapping.findForward("detalleTransferenciaResultado");
		}
	
		return mapping.findForward("detalleTransferenciaResultadoLista");

	}
	
	public ActionForward detalleTransferenciaExecuteCompensacionError(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] aplicaIds;
		String lista;
		String resultado;

		lista = request.getParameter("lista");
		aplicaIds = request.getParameterValues("aplica");
		
	
		if (aplicaIds != null && aplicaIds.length >= 1) {
			
			//Se filtran las tranferencias tipo FA para que solo se envien al procesos las CU y MIX
			ArrayList<String>listaT = new  ArrayList<String>();
			
			for(int i=0; i<aplicaIds.length; i++){
				Scanner expr = new Scanner(aplicaIds[i]);
				expr.useDelimiter("_");
				String idTrans = expr.next();
				String tipoPago = expr.next();
						
				listaT.add(idTrans);
			}
				
			if(listaT.size() > 0){
				HttpSession sesion = request.getSession();
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				myDao3.setRegresaTransferenciasCompensacion(listaT, empleado.getIdEmpleado());
				request.setAttribute(ConstantesTemplate.RESULTADO, "Transferencia(s) regresada(s)");
				request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, listaT.get(0));
			}
			

		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al regresar la Transferencia");

		}
		
		if(request.getParameter("pendiente")!=null){
			return mapping.findForward("detalleTransferenciaResultadoPendiente");
		}
		
		if (lista == null){
			return mapping.findForward("detalleTransferenciaResultado");
		}
	
		return mapping.findForward("detalleTransferenciaResultadoLista");

	}

	public ActionForward rechazarExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String[] idTrans;
		idTrans = request.getParameterValues("rechaza");
		
		if(!myDao4.consultaEstadoAplicado( idTrans[0])){
			
			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al rechazar la Transferencia");
			
			if(request.getParameter("pendiente")!=null){
				return mapping.findForward("detalleTransferenciaResultadoPendiente");
			}
			
			return mapping.findForward("detalleTransferenciaResultadoLista");
		}

		String[] aplicaIds;
		String resultado;
		String comentario;

		comentario = request.getParameter("comentario");
		aplicaIds = request.getParameterValues("rechaza");
		if (aplicaIds != null && aplicaIds.length >= 1) {

			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			resultado = myDao3.setRechazaTransferencias(aplicaIds, comentario,empleado.getIdEmpleado());
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, aplicaIds[0]);
		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al rechazar la Transferencia");

		}

		return mapping.findForward("rechazaTransferenciaResultado");

	}
	
	
	
	public ActionForward cambiarEstatusExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] idTrans;
		idTrans = request.getParameterValues("cambio");


		String[] aplicaIds;
		String resultado;
		String comentario;

		comentario = request.getParameter("comentario");
		aplicaIds = request.getParameterValues("cambio");
		if (aplicaIds != null && aplicaIds.length >= 1) {

			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			resultado = myDao3.setCambioEstatusTransferencias(aplicaIds, comentario, empleado.getIdEmpleado());
			if("Transferencia cambiada de estatus correctamente".equals(resultado)) {
				resultado=myDao4.eliminarPagosOnlyByIdTransferencia(aplicaIds);
			}
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, aplicaIds[0]);
		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO, "Error al realizar cambio de la Transferencia");

		}

		return mapping.findForward("rechazaTransferenciaResultado");

	}
	
	
	public ActionForward regresaTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String idTrans = request.getParameter(ConstantesTemplate.IDTRANSFERENCIA);
		
		if(!myDao4.consultaEstadoAplicado( idTrans)){
			
			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al Regresar la Transferencia");
			
			if(request.getParameter("pendiente")!=null){
				return mapping.findForward("detalleTransferenciaResultadoPendiente");
			}
			
			return mapping.findForward("detalleTransferenciaResultadoLista");
		}

		String idTransferencia;
		String resultado;
		String comentario;

		comentario = request.getParameter("comentario");
		idTransferencia = request.getParameter(ConstantesTemplate.IDTRANSFERENCIA);
		if (idTransferencia != null) {

			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			
			if(request.getParameter("batch")==null){
				resultado = myDao4.regresarTransferencia(Integer.parseInt(idTransferencia),empleado.getIdEmpleado(),comentario,Constantes.AUTORIZADA);
			}
			else{
			resultado = myDao3.regresarTransferenciaBatch(Integer.parseInt(idTransferencia),empleado.getIdEmpleado(),comentario,Constantes.AUTORIZADA);
			}
			
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, idTransferencia);
		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al Regresar la Transferencia");

		}

		return mapping.findForward("rechazaTransferenciaResultado");

	}
	
	public ActionForward comentaTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String idTransferencia;
		String resultado = "";
		String comentario;

		comentario = request.getParameter("comentario");
		idTransferencia = request.getParameter(ConstantesTemplate.IDTRANSFERENCIA);
		if (idTransferencia != null) {
			
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			
			if(myDao.hayComentarioFA(Integer.parseInt(idTransferencia))){
				resultado = "Error, la transferencia ya fue comentada";
			}else{
				resultado = myDao4.comentarTransferencia(Integer.parseInt(idTransferencia),empleado.getIdEmpleado(),comentario);
			}
			
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, idTransferencia);
		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al comentar la Transferencia");

		}

		return mapping.findForward("rechazaTransferenciaResultado");

	}
	
	public ActionForward regresaTransferenciaSinValidar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idTransferencia;
		String resultado;
		String comentario;

		comentario = request.getParameter("comentario");
		idTransferencia = request.getParameter(ConstantesTemplate.IDTRANSFERENCIA);
		if (idTransferencia != null) {

			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			
			if(request.getParameter("batch")==null){
				resultado = myDao4.regresarTransferencia(Integer.parseInt(idTransferencia),empleado.getIdEmpleado(),comentario,Constantes.AUTORIZADA);
			}
			else{
			resultado = myDao3.regresarTransferenciaBatch(Integer.parseInt(idTransferencia),empleado.getIdEmpleado(),comentario,Constantes.AUTORIZADA);
			}
			
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, idTransferencia);
		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al Regresar la Transferencia");

		}

		return mapping.findForward("rechazaTransferenciaResultado");

	}
	
	public ActionForward busqueda(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		String fecha;
		String banco;
		SimpleDateFormat formatoFinal;
		List<TransferenciaPojo> listaTrasferencia;
		String importe;
		java.sql.Date fechaDate;
		HttpSession sesion = request.getSession();
		fecha = request.getParameter(ConstantesTemplate.FECHA);
		banco = request.getParameter(ConstantesTemplate.BANCO);
		importe = request.getParameter(ConstantesTemplate.IMPORTE);

		formatoFinal = new SimpleDateFormat(ConstantesTemplate.DDMMYYYY);

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
				
				 DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				 importe=formateador.format(parseado);
			} catch (IllegalArgumentException  e) {
				importe = null;
			}
		}

		if (banco != null) {
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			listaTrasferencia = myDao.getTransferenciasBusqueda(Integer.parseInt(banco), fechaDate, importe,empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesTemplate.LISTATRANSFERENCIA, listaTrasferencia);
			sesion.setAttribute("listaTransferencia", listaTrasferencia);
			sesion.setAttribute("idBanco", banco);
			sesion.setAttribute("fechaReporte", fechaDate);
			sesion.setAttribute("importeReporte", importe);
			sesion.setAttribute("regionReporte", empleadoPojo.getDescRegion());
			sesion.setAttribute("nombreUsuarioReporte", empleadoPojo.getNombre());
		}

		return mapping.findForward("busquedaListaTransferencias");
	}
	
	public ActionForward busquedaReporte(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Inicia Busqueda Reporte");
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		String correo =request.getParameter("correo");
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
		String idBanco = sesion.getAttribute("bancoReporte").toString();
		String fecha = sesion.getAttribute("fechaDateReporte").toString();
		String idRegion = sesion.getAttribute("regionReporte").toString();
		String usuario = empleadoPojo.getNombre().toString();
		String parametro = idBanco+"/"+fecha+"/"+idRegion+"/"+usuario;
		Date inicio=new Date(); 
		String mensaje="";
 	   	if (!correo.equals("")) {
	 	   	if (correo.endsWith("@mail.telcel.com") || correo.endsWith("@telcel.com")) {
		 	   	TransferenciasDesglosadasPojo ctd=new TransferenciasDesglosadasPojo();
		 	   	ctd.setCorreo(correo);
		 	   	ctd.setParametro(parametro);
		 	   	ctd.setEstatus("PE");
		 	   	ctd.setRegion(idRegion);
		 	   	ctd.setInicio(formatoFinal.format(inicio));
		 	   if (!myDao3.ifExistTransferenciaDesglosadas(correo, parametro, "'PE','PS'")) {
		 		  if (myDao3.insertaTransferenciasDesglosadas(ctd)) {
					   mensaje="Su solicitud se proceso satisfactoriamente, se enviara correo en unos minutos con la informacion";
				   }else {
					   mensaje="No fue posible procesar su solicitud, favor de intentarlo de nuevo, si el problema persiste comuniquese al correo sisfactsipab@mail.telcel.com ";
				   }
		 	   }else {
		 		  mensaje="No fue posible procesar su solicitud, ya se esta procesando este reporte, se enviara correo en unos minutos con la informacion";
		 	   }
			}else {
				mensaje="Error en la solicitud, el correo debe pertenecer a TELCEL (terminacion @telcel.com o @mail.telcel.com)";    
			}
		}else {
			mensaje="Error en la solicitud, el campo de correo no puede ir vacio";
		}
 	   log.info("Termina Busqueda Reporte");
 	   request.setAttribute("mensaje", mensaje);
 	   return mapping.findForward("resultadoListaTransferencia");

	}

	public ActionForward reportes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute(ConstantesTemplate.PANELID, "reportes");
		request.setAttribute(ConstantesTemplate.TITULO, "Reportes");

		return mapping.findForward("pestana");

	}

	public ActionForward envioSap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		request.setAttribute(ConstantesTemplate.PANELID, "envioSap");
		request.setAttribute(ConstantesTemplate.TITULO, "Envio SAP");

		return mapping.findForward("pestana");

	}

	public ActionForward usuarios(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){


		request.setAttribute(ConstantesTemplate.PANELID, ConstantesTemplate.USUARIOS);
		request.setAttribute(ConstantesTemplate.TITULO, "Usuarios");

		return mapping.findForward(ConstantesTemplate.USUARIOS);

	}

	public ActionForward listaUsuarios(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
		List<EmpleadoPojo> lista = myDao2.getEmpleados(empleado.getUsuario(),empleado.getDescRegion());
		
		request.setAttribute("list", lista);


		return mapping.findForward("usuariosLista");

	}

	public ActionForward agregarUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = ((HttpServletRequest) request).getSession();
		EmpleadoPojo empleadoSession = (EmpleadoPojo)session.getAttribute(ConstantesTemplate.EMPLEADO);
		
		String numeroEmpleado = request.getParameter(ConstantesTemplate.EMPLEADO);
		String perfil = request.getParameter("perfil");

		if (numeroEmpleado != null && perfil != null) {

			DatosUsuario datosUsuario = new DatosUsuario();
			EmpleadoPojo empleadoNuevo = datosUsuario.obtenerIdentidad(numeroEmpleado);
			
			if (empleadoNuevo==null) {
				request.setAttribute(ConstantesTemplate.RESULTADO,datosUsuario.getMensaje());
			} else {
				
				// Validamos si el usuario ya existe
				String resultado = "";
				EmpleadoPojo empleadoC = myDao2.getEmpleadoById(empleadoNuevo.getUsuario());
				
				if( empleadoC == null) {
					resultado = myDao3.agregarUsuario(empleadoNuevo, Integer.parseInt(perfil), empleadoSession.getUsuario(),empleadoSession.getDescRegion());
				} else {
					String [] arrEmpleados = new String[1];					
					arrEmpleados[0] = String.valueOf(empleadoC.getIdEmpleado());
					resultado = myDao3.actualizaEmpleados(arrEmpleados, empleadoC.getIdPefril(),empleadoSession.getUsuario());
				}
				request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			}
		}


		return mapping.findForward(ConstantesTemplate.USUARIOAGREGAR);

	}

	public ActionForward actualizaUsuario(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){


		String[] idEmpleados = request.getParameterValues(ConstantesTemplate.USUARIOS);
		String idPerfil = request.getParameter("perfil");
		
		if (idEmpleados != null && idEmpleados.length >= 1 && idPerfil != null) {

			boolean usrvalidos = true;			
			for(String idEmpleado:idEmpleados ){
				int idE  = Integer.parseInt(idEmpleado);
				if(idE <= ConstantesNumeros.TRES) {
					usrvalidos = false;
				}
			}
			HttpSession session = ((HttpServletRequest) request).getSession();
			EmpleadoPojo empleado = (EmpleadoPojo)session.getAttribute(ConstantesTemplate.EMPLEADO);
			
			if (usrvalidos) {
				String resultado = myDao3.actualizaEmpleados(idEmpleados, Integer.parseInt(idPerfil),empleado.getUsuario());
				request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			} else {
				request.setAttribute(ConstantesTemplate.RESULTADO, "NO SE PUEDEN MODIFICAR LOS USUARIOS SYS.");
			}
		} else {

			request
					.setAttribute(ConstantesTemplate.RESULTADO,
							"No se selecciono ningun usuario");
		}


		return mapping.findForward(ConstantesTemplate.USUARIOAGREGAR);

	}

	public ActionForward restablecerUsuario(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {


		String[] idEmpleados = request.getParameterValues(ConstantesTemplate.USUARIOS);
		

		if (idEmpleados != null && idEmpleados.length >= 1) {

			HttpSession session = ((HttpServletRequest) request).getSession();
			EmpleadoPojo empleado = (EmpleadoPojo)session.getAttribute(ConstantesTemplate.EMPLEADO);
			
			
			String resultado = myDao3.actualizaEmpleados(idEmpleados,null,empleado.getUsuario());
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
		} else {

			request
					.setAttribute(ConstantesTemplate.RESULTADO,
							"No se selecciono ningun usuario");
		}


		return mapping.findForward(ConstantesTemplate.USUARIOAGREGAR);

	}

	public ActionForward bajaUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		String[] idEmpleados = request.getParameterValues(ConstantesTemplate.USUARIOS);

		if (idEmpleados != null && idEmpleados.length >= 1) {

			boolean usrvalidos = true;			
			for(String idEmpleado:idEmpleados ){
				int idE  = Integer.parseInt(idEmpleado);
				if(idE <= ConstantesNumeros.TRES) {
					usrvalidos = false;
				}
			}
			HttpSession session = ((HttpServletRequest) request).getSession();
			EmpleadoPojo empleado = (EmpleadoPojo)session.getAttribute(ConstantesTemplate.EMPLEADO);
			
			if (usrvalidos) {
				String resultado = myDao3.borrarEmpleados(idEmpleados,empleado.getUsuario());
				request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			} else {
				request.setAttribute(ConstantesTemplate.RESULTADO, "NO SE PUEDEN ELIMINAR LOS USUARIOS SYS.");
			}
		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"No se selecciono ningun usuario");
		}


		return  mapping.findForward(ConstantesTemplate.USUARIOAGREGAR);

	}
	
	public ActionForward tomarTransferencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

	    request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());		
		request.setAttribute(ConstantesTemplate.PANELID, "tomarTransferencia");
		request.setAttribute(ConstantesTemplate.TITULO, "Tomar Transferencia");

		return mapping.findForward("desglosarTransferencia");

	}
	
	public ActionForward tomarTransferenciaLista(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String fecha;
		String fecha2;
		String banco;
		SimpleDateFormat formatoFinal;
		List<TransferenciaPojo> listaTrasferencia;
		String importe;
		java.sql.Date fechaDate=null;
		java.sql.Date fechaDate2 = null;
		
		try {
		
		fecha = request.getParameter(ConstantesTemplate.FECHA);
		fecha2 = request.getParameter("fecha2");  
		banco = request.getParameter(ConstantesTemplate.BANCO);
		importe = request.getParameter(ConstantesTemplate.IMPORTE);
		EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");

		formatoFinal = new SimpleDateFormat(ConstantesTemplate.DDMMYYYY);

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

		if (fecha2 != null) {

			
			try {

				fechaDate2 = new java.sql.Date((formatoFinal.parse(fecha2)).getTime());
			} catch (Exception e) {

				fechaDate2 = null;
			}

		}
		
		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				 parseado = Double.parseDouble(importe);
		
				 DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				 importe=formateador.format(parseado);
			} catch (Exception e) {
				importe = null;
			}
		}

		if (banco != null) {

				listaTrasferencia = myDao.getTransferenciasTomar(Integer.parseInt(banco), fechaDate,fechaDate2, importe,empleadoPojo.getDescRegion());
				request.setAttribute(ConstantesTemplate.LISTATRANSFERENCIA, listaTrasferencia);			
		}
		
		
	} catch (IllegalArgumentException  e) {
			
	}
		return mapping.findForward("desgloseListaTransferencias");

	}
	
	public ActionForward tomarTransferenciaExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String[] aplicaIds;
		String resultado;
		String alias;
		HttpSession sesion;
		EmpleadoPojo empleado;

		alias = request.getParameter("alias");
		aplicaIds = request.getParameterValues("tomar");
		if (aplicaIds != null && aplicaIds.length >= 1) {

			sesion = request.getSession();
			empleado=(EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			
			if(!myDao4.consultaEstadoTomar(aplicaIds)){
				request.setAttribute(ConstantesTemplate.RESULTADO,"Error al tomar las Transferencias");
				return mapping.findForward("desgloseTomarResultado");
			}
			
			resultado = myDao3.setTomaTransferencias(aplicaIds,empleado.getIdEmpleado(),alias);
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, aplicaIds[0]);
		} else {

			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al tomar las Transferencias");

		}
		

		return mapping.findForward("desgloseTomarResultado");

	}
	
	public ActionForward transferenciasByUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession sesion;
		EmpleadoPojo empleado;
		List<TransferenciaPojo> listaTrasferencias;

        sesion = request.getSession();
		empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
		
		if(request.getParameter("regresada")!=null){
			listaTrasferencias = myDao2.getTransferenciasRegresadas(empleado.getIdEmpleado(),empleado.getDescRegion());
		}else{
			listaTrasferencias = myDao2.getTransferenciasByEmpleado(empleado);
		}
		
		request.setAttribute("listaTrasferencias", listaTrasferencias);

		request.setAttribute(ConstantesTemplate.PANELID, "transferenciasByUsuario");
		request.setAttribute(ConstantesTemplate.TITULO, "Desglosar");
		return mapping.findForward("desgloseUsuario");

	}
	
	public ActionForward desgloseTransferencias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		request.setAttribute(ConstantesTemplate.FECHA, formatoFinal.format(fecha));
		TransferenciaPojo transferencia = myDao2.getTransferencia(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
		request.setAttribute("transferencia", transferencia);
		request.setAttribute("idReferencia", transferencia.getPkIdReferencia());
		 List<HistorialPojo> listaHistorial = myDao.getHistorial(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
		 
		 request.setAttribute("listaHistorial", listaHistorial);
		 
		if(request.getParameter("excel")==null){ 
			return mapping.findForward("detalleDesgloseUsuario");
		}
	
		DetallePojo listaTransferencias = myDao2.getDetallesTransferencia(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA))); 
		List<PagoPojo> listaPagos = listaTransferencias.getListaPagos();
			 
		request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
		request.setAttribute(ConstantesTemplate.RESULTADO, request.getParameter(ConstantesTemplate.RESULTADO));
		request.setAttribute(ConstantesTemplate.IMPORTE, listaTransferencias.getTransferenciaPojo().getImporte());			
		request.setAttribute(ConstantesTemplate.LISTAPAGOS,listaPagos );
		request.setAttribute("estatus", listaTransferencias.getTransferenciaPojo().getEstatus());
			
		return mapping.findForward("detalleDesgloseUsuarioExcel");

	}
	
	public ActionForward desgloseTransferenciasPromesa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		request.setAttribute(ConstantesTemplate.FECHA, formatoFinal.format(fecha));
		TransferenciaPojo transferencia = myDao2.getTransferencia(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
		request.setAttribute("transferencia", transferencia);
		
		List<HistorialPojo> listaHistorial = myDao.getHistorial(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
		request.setAttribute("listaHistorial", listaHistorial);
		
		DetallePojo listaTransferencias = myDao2.getDetallesTransferencia(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA))); 
		List<PagoPojo> listaPagos = listaTransferencias.getListaPagos();
		
		String limite="0";
		if(myDao4.validaPromesaPago()){
			if(!transferencia.getTipoPagos().equals("FA") && transferencia.getRegion().equals("9")){
				
				limite = myDao4.validaLimitePromesaPago();
				
				if(listaPagos.size()>Integer.valueOf(limite)){//PROCESO BATCH
					System.out.print("ENTRA A PROCESO:"+limite);
					myDao4.insertaProcesoPromesa(transferencia.getIdtransferencia(), transferencia.getIdEmpleado());
					myDao4.actualizaDatoPromesaPago(listaPagos,"BATCH");
					
				}else{//PROCESO EN LINEA
					System.out.print("LLEGA A PROMESA EN LINEA");
					PromesaPago promesa = new PromesaPago();
					promesa.insertaPromesaPago(transferencia, listaPagos, 5);
		
					myDao4.actualizaDatoPromesaPago(listaPagos,"ONLINE");
				}
					
			}
		}
		
		
		
		 
		 return mapping.findForward("detalleDesgloseUsuario");
	}
	
	public ActionForward desgloseTransferenciasListaDetalle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
				boolean banderaEstatus = false;
			try {
	
			 DetallePojo listaTransferencias = myDao2.getDetallesTransferencia(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
			 String idReferencia = request.getParameter("idReferencia");
			 List<PagoPojo> listaPagos = listaTransferencias.getListaPagos();
			 request.setAttribute("idReferencia", idReferencia);
			 double suma=0;
			 for(PagoPojo pago:listaPagos){
				 
				 suma=suma+pago.getImporte();
				 if("CL".equals(pago.getEstatusPago())) {
						banderaEstatus = true;
				 }
			 }
			  DecimalFormat formateador = new DecimalFormat("#,#00.00#;(-#,#00.00#)");
			  String totalSuma = formateador.format(suma);
			  
			 Boolean bandera=false;
			 String importeTotal = formateador.format(listaTransferencias.getTransferenciaPojo().getImporte().doubleValue());
			 
			 if(importeTotal.equals(totalSuma)){
				 bandera=true;
			 }
			 
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
			request.setAttribute(ConstantesTemplate.RESULTADO, request.getParameter(ConstantesTemplate.RESULTADO));
			request.setAttribute(ConstantesTemplate.IMPORTE, listaTransferencias.getTransferenciaPojo().getImporte());
			request.setAttribute("lista", listaPagos.size());
			request.setAttribute("suma", totalSuma);
			request.setAttribute("bandera", bandera);
			request.setAttribute("banderaEstatus", banderaEstatus);
			
			request.setAttribute(ConstantesTemplate.LISTAPAGOS,listaPagos );
			request.setAttribute("estatus", listaTransferencias.getTransferenciaPojo().getEstatus());
	} catch (ArithmeticException e) {
		
	}
		return mapping.findForward("desgloseListaPagos");

	}
	
	public ActionForward addPago(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		if(!myDao4.consultaEstadoDesglose(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)))){
			
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
			request.setAttribute("guardar", 2);
			return mapping.findForward(ConstantesTemplate.DESGLOSEADDPAGORES);
		}
		
		String jsp="desgloseAddPago";
		String importe=request.getParameter(ConstantesTemplate.IMPORTE);
		String idReferencia=request.getParameter("idReferencia");
		request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
		boolean mte = false;
		int val = 0;
		
		if (idReferencia != null && !idReferencia.isEmpty() ) {
			mte = true;
			val = (mte) ? 1 : 0;
		}
		request.setAttribute("mte", val);
		if (importe != null) {

			importe = importe.replace(",", "");
			importe = importe.replace("$", "");
			importe =importe.trim();
			Double parseado = null;
			try {
				 parseado = Double.parseDouble(importe);
				 DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				 importe=formateador.format(parseado);
				 
			} catch (IllegalArgumentException  e) {
				importe = null;
			}
		}
		String cuenta=request.getParameter(ConstantesTemplate.CUENTA);
		if (cuenta!= null) {

			
			try {
				Double.parseDouble(cuenta);
				
			} catch (Exception e) {
				cuenta = null;
			}

		}
		
		String	esporadico = request.getParameter(ConstantesTemplate.SAP)!= null ? request.getParameter(ConstantesTemplate.SAP).trim(): "";
		
		 HttpSession sesion = request.getSession();
		
		request.setAttribute(ConstantesTemplate.IDPAGO, request.getParameter(ConstantesTemplate.IDPAGO));
		request.setAttribute(ConstantesTemplate.REGION, request.getParameter(ConstantesTemplate.REGION));
		request.setAttribute(ConstantesTemplate.CUENTA, request.getParameter(ConstantesTemplate.CUENTA));
		request.setAttribute(ConstantesTemplate.IMPORTE, request.getParameter(ConstantesTemplate.IMPORTE));
		request.setAttribute(ConstantesTemplate.TIPO, request.getParameter(ConstantesTemplate.TIPO));
		request.setAttribute(ConstantesTemplate.SAP, esporadico);
		
		if(request.getParameter(ConstantesTemplate.ADD)!=null && request.getParameter(ConstantesTemplate.ADD).equals(ConstantesTemplate.ADD) ){
			if(importe!=null && cuenta!=null){
				ArrayList<PagoPojo> array= new	ArrayList<PagoPojo>();
				PagoPojo pojo= new PagoPojo();
				
			   	EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				
				pojo.setCuenta(cuenta);
				pojo.setIdTrans(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
				pojo.setImporte(Double.parseDouble(importe));
				pojo.setIdEmpleado(empleado.getIdEmpleado());
				pojo.setRegion(request.getParameter(ConstantesTemplate.REGION));
				pojo.setRegion(pojo.getRegion().toUpperCase(Locale.getDefault()));	
				pojo.setTipo(request.getParameter(ConstantesTemplate.TIPO));
				pojo.setSap(esporadico);
				array.add(pojo);
				String resultado;
				
				RespuestaPagosPojo respuesta = myDao3.setInsertaPagos(array,empleado);
				resultado=respuesta.getRespuesta();
				request.setAttribute("cambios", respuesta.getCuetasCambiadas());
				request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			}else{
				request.setAttribute(ConstantesTemplate.IMPORTE, ConstantesTemplate.IMPORTE);
				if(importe==null){
					request.setAttribute(ConstantesTemplate.RESULTADO, "El importe no es numerico");
				}else if(cuenta==null){
			    	request.setAttribute(ConstantesTemplate.RESULTADO, "La cuenta no es numerica");
				}
			}
			jsp=ConstantesTemplate.DESGLOSEADDPAGORES;
		}else if (request.getParameter(ConstantesTemplate.ADD)!=null && request.getParameter(ConstantesTemplate.ADD).equals("edit") ) {
			
			if(cuenta!=null){
				EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
				
				
			
			PagoPojo pago = new PagoPojo();
			
			pago.setIdPago(Integer.parseInt(request.getParameter(ConstantesTemplate.IDPAGO)));
			pago.setCuenta(request.getParameter(ConstantesTemplate.CUENTA));
			pago.setImporte(Double.parseDouble(importe));
			pago.setRegion(request.getParameter(ConstantesTemplate.REGION).toUpperCase(Locale.getDefault()));
			pago.setTipo(request.getParameter(ConstantesTemplate.TIPO));
			pago.setSap(esporadico);
			
			String resultado=    myDao4.editarPago(pago,empleado);
			
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			
			}else{
				request.setAttribute(ConstantesTemplate.RESULTADO, "La cuenta no es numerica");
			}
				
			jsp=ConstantesTemplate.DESGLOSEADDPAGORES;
		}else if (request.getParameter(ConstantesTemplate.ADD)!=null && request.getParameter(ConstantesTemplate.ADD).toString().equals("eliminar") ) {
			
			
			String resultado=   myDao4.eliminarPago(Integer.parseInt(request.getParameter(ConstantesTemplate.IDPAGO)));
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
			
			jsp=ConstantesTemplate.DESGLOSEADDPAGORES;
		}
	
		return mapping.findForward(jsp);

	}
	
	public ActionForward setDesglosado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
//				
		String tipoPagos[]= request.getParameterValues("tipoPagos");
		String tipoString="";
//		/*Validacion para evitar el doble desglose*/
		if(!myDao4.consultaEstadoDesglose(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)))){
			
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
			
			request.setAttribute("guardar", 2);
			return mapping.findForward(ConstantesTemplate.DESGLOSEADDPAGORES);
		}
		
		if(tipoPagos==null){
			
			DetallePojo listaTransferencias = myDao2.getDetallesTransferencia(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
			 
			 List<PagoPojo> listaPagos = listaTransferencias.getListaPagos();
			
			
			 Map hash= new HashMap();
				for(PagoPojo tipo:listaPagos){
					
					hash.put(tipo.getTipo(),tipo.getTipo());
					   
				}
				if(hash.get("CU")!=null && hash.get("FA")!=null){
					tipoString="MIX";
				}else if(hash.get("CU")!=null){
					tipoString="CU";
					
				}else if(hash.get("FA")!=null){
					tipoString="FA";
				}
			
			
			
		}else{
			
			Map hash= new HashMap();
			for(String tipo:tipoPagos){
				
				hash.put(tipo,tipo);
				   
			}
			if(hash.get("CU")!=null && hash.get("FA")!=null){
				tipoString="MIX";
			}else if(hash.get("CU")!=null){
				tipoString="CU";
				
			}else if(hash.get("FA")!=null){
				tipoString="FA";
			}
			
		}
		
		String correoElectronico = request.getParameter("correoElectronico");
		String resultado = myDao4.setDesglosado(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)),empleado.getIdEmpleado(),tipoString,correoElectronico);
		 
		request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
		request.setAttribute(ConstantesTemplate.LISTAPAGOS, resultado);
		request.setAttribute("guardar", 1);

		return mapping.findForward(ConstantesTemplate.DESGLOSEADDPAGORES);

	}
	
	public ActionForward soltarTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		/*Validacion para evitar liberacion de trans cuando ya esta desglosada*/
		if(!myDao4.consultaEstadoDesglose(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)))){
			
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
			request.setAttribute("guardar", 2);
			return mapping.findForward(ConstantesTemplate.DESGLOSEADDPAGORES);
		}
		
		if(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)!=null){
			
			String resultado=myDao4.eliminarPagosByIdTransferencia(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)));
			request.setAttribute(ConstantesTemplate.RESULTADO, resultado);
		}
		
		return mapping.findForward("desgloseSoltarTransferencia");

	}
	
	public ActionForward revertirTransferencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());
		request.setAttribute(ConstantesTemplate.PANELID, "revertirTransferencia");
		request.setAttribute(ConstantesTemplate.TITULO, "Revierte Transferencia");

		return mapping.findForward("revertirTransferencia");

	}
		
	public ActionForward revierteListaTransferencias(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String fecha;
		String banco;
		SimpleDateFormat formatoFinal;
		List<TransferenciaPojo> listaTrasferencia;
		String importe;
		java.sql.Date fechaDate;

		fecha = request.getParameter(ConstantesTemplate.FECHA);
		banco = request.getParameter(ConstantesTemplate.BANCO);
		importe = request.getParameter(ConstantesTemplate.IMPORTE);

		formatoFinal = new SimpleDateFormat(ConstantesTemplate.DDMMYYYY);

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
		
				 DecimalFormat formateador = new DecimalFormat(ConstantesTemplate.FORMATODECIMAL);
				 importe=formateador.format(parseado);
			} catch (IllegalArgumentException  e) {
				importe = null;
			}
		}
		if (banco != null) {
			EmpleadoPojo empleadoPojo=(EmpleadoPojo) request.getSession().getAttribute("empleado");
			listaTrasferencia = myDao.getTransferencias(Integer.parseInt(banco), fechaDate,null, Constantes.APLICADA, importe,empleadoPojo.getDescRegion());
			request.setAttribute(ConstantesTemplate.LISTATRANSFERENCIA, listaTrasferencia);
		}

		return mapping.findForward("revierteListaTransferencias");

	}
		
	public ActionForward cerrarPorFuera(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {	
		
		String[] valor = request.getParameterValues(ConstantesTemplate.AUTORIZA);
		String coment = request.getParameter(ConstantesTemplate.COMENT);
		
		
		if(!myDao4.consultaEstadoAplicado( valor[0])){
			
			request.setAttribute(ConstantesTemplate.RESULTADO,"Error al cerrar la Transferencia");
			
			if(request.getParameter("pendiente")!=null){
				return mapping.findForward("detalleTransferenciaResultadoPendiente");
			}
			
			return mapping.findForward("detalleTransferenciaResultadoLista");
		}
	
		if(request.getParameter(ConstantesTemplate.DETALLE)!=null){
			
			request.setAttribute(ConstantesTemplate.DETALLE, true);
			 String[] valores = request.getParameterValues(ConstantesTemplate.AUTORIZA);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, Integer.parseInt(valores[0]));
		}else{
			request.setAttribute(ConstantesTemplate.DETALLE, false);
		}
		String resultado="";
		if(request.getParameterValues(ConstantesTemplate.AUTORIZA)!=null){
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			resultado=myDao4.setCierraTransferencias(request.getParameterValues(ConstantesTemplate.AUTORIZA),empleado.getIdEmpleado(),coment);
		}
		
		
		request.setAttribute(ConstantesTemplate.RESULTADO, resultado);

		return mapping.findForward("cierraResultado");

	}
	
	public ActionForward cerrarPorFueraSinValidar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {	
	
		if(request.getParameter(ConstantesTemplate.DETALLE)!=null){
			
			request.setAttribute(ConstantesTemplate.DETALLE, true);
			 String[] valores = request.getParameterValues(ConstantesTemplate.AUTORIZA);
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, Integer.parseInt(valores[0]));
		}else{
			request.setAttribute(ConstantesTemplate.DETALLE, false);
		}
		String resultado="";
		if(request.getParameterValues(ConstantesTemplate.AUTORIZA)!=null){
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			
			String coment = request.getParameter(ConstantesTemplate.COMENT);
			
			resultado=myDao4.setCierraTransferencias(request.getParameterValues(ConstantesTemplate.AUTORIZA),empleado.getIdEmpleado(), coment);
		}
		
		
		request.setAttribute(ConstantesTemplate.RESULTADO, resultado);

		return mapping.findForward("cierraResultado");

	}
	
	public ActionForward revierteListaTransferenciaExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){

		String[] transId = request.getParameterValues("revierte");
		
		String resultado="Error al revertir transferencias";
		if(transId!=null){
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute(ConstantesTemplate.EMPLEADO);
			resultado=myDao3.setRevierteTransferencias(transId, empleado.getIdEmpleado());
			
		}
		
		request.setAttribute(ConstantesTemplate.RESULTADO, resultado);

		return mapping.findForward("revertirTransferenciaResultado");

	}
	
	public ActionForward cargarNumeroDocumento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		request.setAttribute(ConstantesTemplate.PANELID, "cargarNumeroDocumento");
		request.setAttribute(ConstantesTemplate.TITULO, "Cargar numero de documento");

		return mapping.findForward("cargarNumeroDocumento");

	}
	
	public ActionForward compensarTransferencias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute(ConstantesTemplate.LISTABANCOS, myDao.getBancos());
		request.setAttribute(ConstantesTemplate.PANELID, "compensarTransferencias");
		request.setAttribute(ConstantesTemplate.TITULO, "Compensar transferencias");

		return mapping.findForward("compensarTransferencias");

	}
	
	public ActionForward validadorCuentas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession sesion = request.getSession();
		request.setAttribute(ConstantesReportes.PANELID, "validadorCuentas");
		request.setAttribute(ConstantesReportes.TITULO, "Validador de Cuentas");
		if (request.getParameter("reporte") != null) {
			if (!request.getParameter("reporte").equals("excel")) 
				return mapping.findForward("validadorCuentasReporte");
			
			else
				return mapping.findForward("validadorCuentasExcel");
		}

		return mapping.findForward("validadorCuentas");

	}	
	
	public ActionForward getBandeja(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		List<EstatusPojo> estatus = new ArrayList<EstatusPojo>();
		for (EstatusPojo estatusPojo :  myDao.getEstatus()) {
			if (estatusPojo.getIdEstatus() >= 1 && estatusPojo.getIdEstatus()<=9) {
				estatus.add(estatusPojo);
				}
		}
		
		request.setAttribute("listaEstatus", estatus);
//		request.setAttribute("listaEstatus", myDao.getEstatus());
		request.setAttribute(ConstantesReportes.FECHA, request.getParameter(ConstantesReportes.FECHA));
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fecha));
		request.setAttribute(ConstantesReportes.PANELID, "getBandeja");
		request.setAttribute(ConstantesReportes.TITULO, "Bandeja de entrada");

		return mapping.findForward("getBandeja");

	}
	
	public ActionForward estatusTransferenciaLista(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		java.util.Date fechaCreacion= new java.util.Date();
		SimpleDateFormat formatofechaCreacion = new SimpleDateFormat(ConstantesReportes.FORMATO);
		
		request.setAttribute(ConstantesReportes.FECHACREACION, formatofechaCreacion.format(fechaCreacion));
		String fecha=request.getParameter(ConstantesReportes.FECHA);
		String fecha2=request.getParameter(ConstantesReportes.FECHA2);
		String estatus=request.getParameter("estatus");
		
		request.setAttribute(ConstantesReportes.FECHA, fecha);
		request.setAttribute(ConstantesReportes.FECHA2, fecha2);
		request.setAttribute("estatus", estatus);
		
		
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
			Integer idEmpleado=null;
			request.setAttribute("listaEstatus", myDao.getEstatus());
			HttpSession sesion = request.getSession();
			EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
			idEmpleado=empleado.getIdEmpleado();
			
			EmpleadoPojo empleadoPojo=(EmpleadoPojo)request.getSession().getAttribute("empleado");
			List<TransferenciaPojo> listaTrasferencias = myDao.getTransferenciasByReferenciaEstatus(Integer.parseInt(estatus), fechaDate, fechaDate2,idEmpleado,empleadoPojo.getDescRegion(), empleado.getEsJefe());
			request.setAttribute(ConstantesReportes.LISTATRANS, listaTrasferencias);
			
		}
		 
		request.setAttribute("estatusInt", Integer.parseInt(estatus));
		return mapping.findForward("transferenciasByReferencia");	
					
	}
	
	public ActionForward getPeticiones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{	
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
		List<PeticionesPojo> peticionesPojos = myDao.getPeticiones(empleado.getDescRegion().replace("R0", ""));
		request.setAttribute("listaPeticiones", peticionesPojos);
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fecha));
		request.setAttribute(ConstantesReportes.PANELID, "getPeticiones");
		request.setAttribute(ConstantesReportes.TITULO, "Administrador de peticiones");
		return mapping.findForward("getPeticiones");

	}
	
	public ActionForward editarPeticion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{	
		
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
		List<EmpleadoPojo> empleados = myDao.getUsuariosPeticiones(empleado.getDescRegion().replace("R0", ""));
		List<String> cantidadMensajes = new ArrayList<String>();
		empleados.add(new EmpleadoPojo(Integer.valueOf(request.getParameter("idEmpleado")), request.getParameter("nombre")));
		request.setAttribute("empleados", empleados);
		request.setAttribute("idPeticion", request.getParameter("idPeticion"));
		request.setAttribute("nombre", request.getParameter("nombre") + "," + request.getParameter("idEmpleado"));
		request.setAttribute("horarioIn", request.getParameter("horarioIn"));
		request.setAttribute("horarioOut", request.getParameter("horarioOut"));
		request.setAttribute("asistencia", request.getParameter("asistencia"));
		request.setAttribute("periodoIn", request.getParameter("periodoIn"));
		request.setAttribute("periodoOut", request.getParameter("periodoOut"));
		request.setAttribute("mensajes", request.getParameter("mensajes"));
		cantidadMensajes.add("TODOS");
		for (int i = 1; i < 51; i++) {
			cantidadMensajes.add(String.valueOf(i));
		}
		request.setAttribute("cantidadMensajes", cantidadMensajes);
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fecha));
		request.setAttribute(ConstantesReportes.PANELID, "editPeticiones");
		request.setAttribute(ConstantesReportes.TITULO, "Editar Peticiones");
		return mapping.findForward("editPeticion");

	}
	
	public ActionForward nuevaPeticion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{	
		
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
		List<EmpleadoPojo> empleados = myDao.getUsuariosPeticiones(empleado.getDescRegion().replace("R0", ""));
		List<String> cantidadMensajes = new ArrayList<String>();
		request.setAttribute("nuevosEmpleados", empleados);
		cantidadMensajes.add("TODOS");
		for (int i = 1; i < 51; i++) {
			cantidadMensajes.add(String.valueOf(i));
		}
		request.setAttribute("cantidadMensajes", cantidadMensajes);
		java.util.Date fecha= new java.util.Date();
		SimpleDateFormat formatoFinal = new SimpleDateFormat(ConstantesReportes.FORMATO);
		request.setAttribute(ConstantesReportes.FECHACREACION, formatoFinal.format(fecha));
		request.setAttribute(ConstantesReportes.PANELID, "nuevaPeticion");
		request.setAttribute(ConstantesReportes.TITULO, "Nueva Peticion");
		return mapping.findForward("nuevaPeticion");

	}
	
	public ActionForward addPeticion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		String periodo = "";
		String mensaje = "";
		String id=request.getParameter("idEmpleado");
		String horario=request.getParameter("horarioIn") + " - "+ request.getParameter("horarioOut");
		String asistencia=request.getParameter("asistencia");
		String periodoIn=request.getParameter("periodoIn");
		String periodoOut=request.getParameter("periodoOut");
		if (!periodoIn.equals("undefined") && !periodoOut.equals("undefined")) {
			periodo = periodoIn + " al " +periodoOut;
		}else {
			periodo = "NA";
		}
		String cantidadMensajes=request.getParameter("mensajes");	
		
		if (!myDao.getPeticionById(Integer.valueOf(id))) {
			if (myDao4.insertaPeticion(Integer.valueOf(id),horario,periodo,asistencia,cantidadMensajes)) {
				mensaje = "Se registro con exito la peticion para el usuario: "+id;
				log.info("Se registro con exito la peticion para el usuario: "+id);
			}else {
				mensaje = "Hubo un error al intentar registrar la peticion para el usuario: "+id;
				log.info("Hubo un error al intentar registrar la peticion para el usuario: "+id);
			}
		}else {
			mensaje = "Ya existe una peticion con este usuario: ";
			log.info("Ya existe una peticion con este usuario: ");
		}
		
		request.setAttribute("mensaje", mensaje);
		return mapping.findForward("resultadoDividirCerrar");
		
	}
	
	public ActionForward updatePeticion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		String periodo = "";
		String mensaje = "";
		String idPeticion=request.getParameter("idPeticion");
		String id=request.getParameter("idEmpleado");
		if (id.contains(",")) {
			id = id.split(",")[1].trim();
		}
		String horario=request.getParameter("horarioIn") + " - "+ request.getParameter("horarioOut");
		String asistencia=request.getParameter("asistencia");
		String periodoIn=request.getParameter("periodoIn");
		String periodoOut=request.getParameter("periodoOut");
		if (!periodoIn.equals("undefined") && !periodoOut.equals("undefined")) {
			periodo = periodoIn + " al " +periodoOut;
		}else {
			periodo = "NA";
		}
		String cantidadMensajes=request.getParameter("mensajes");	
		if (myDao4.updatePeticion(Integer.valueOf(idPeticion),Integer.valueOf(id),horario,periodo,asistencia,cantidadMensajes)) {
			mensaje = "Se actualizo correctamente la peticion";
			log.info("Se actualizo correctamente la peticion"+id);
		}else {
			mensaje = "Hubo un error al intentar actualizar la peticion para el usuario: "+id;
			log.info("Hubo un error al intentar actualizar la peticion para el usuario: "+id);
		}
		request.setAttribute("mensaje", mensaje);
		return mapping.findForward("resultadoDividirCerrar");
		
	}
	
	public ActionForward getAlertas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){		
		List<AlertasPojo> alertasPojos = myDao.getAlertas();
		request.setAttribute("listaAlertas", alertasPojos);
		request.setAttribute(ConstantesReportes.PANELID, "getAlertas");
		request.setAttribute(ConstantesReportes.TITULO, "Alertas");
		return mapping.findForward("alertas");

	}
	
	public ActionForward nuevaAlerta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{	
		List<EstatusPojo> estatusPojos = new ArrayList<EstatusPojo>();
		List<String> colores = new ArrayList<String>();
		List<String> tiempo = new ArrayList<String>();
		colores.add("Rojo");
		colores.add("Amarillo");
		colores.add("Verde");
		for (int i = 1; i <= 60; i++) {
			tiempo.add(String.valueOf(i));
		}
		List<EstatusPojo> estatus = myDao.getEstatus();
		for (EstatusPojo estatusPojo : estatus) {
			if (estatusPojo.getIdEstatus().equals(1) || estatusPojo.getIdEstatus().equals(2) || estatusPojo.getIdEstatus().equals(3)) {
				estatusPojos.add(estatusPojo);
			}
		}
		request.setAttribute("listaEstatus", estatusPojos);
		request.setAttribute("colores", colores);
		request.setAttribute("tiempos", tiempo);
		request.setAttribute(ConstantesReportes.PANELID, "nuevaAlerta");
		request.setAttribute(ConstantesReportes.TITULO, "Nueva Alerta");
		return mapping.findForward("addAlerta");

	}
	
	public ActionForward addAlerta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		String mensaje = "";
		String color=request.getParameter("color");
		String tiempo=request.getParameter("tiempo") + " Min";
		String idEstatus=request.getParameter("estatus");
		
		if (!myDao.getAlertaByColor(color)) {
			if (myDao4.insertaAlerta(color,tiempo,Integer.valueOf(idEstatus))) {
				mensaje = "Se registro con exito la nueva alerta: ";
				log.info("Se registro con exito la nueva alerta: ");
			}else {
				mensaje = "Hubo un error al intentar registrar la alerta: ";
				log.info("Hubo un error al intentar registrar la alerta: ");
			}
		}else {
			mensaje = "Ya existe una alerta con ese color, edite o elimine la alerta existente.";
			log.info("Ya existe una alerta con ese color, edite o elimine la alerta existente.");
		}
		
		request.setAttribute("mensaje", mensaje);
		return mapping.findForward("resultadoDividirCerrar");
		
	}
	
	public ActionForward editarAlerta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{	
		List<String> colores = new ArrayList<String>();
		List<String> tiempo = new ArrayList<String>();
		List<EstatusPojo> estatusPojos = new ArrayList<EstatusPojo>();
		request.setAttribute("idAlerta", request.getParameter("idAlerta"));
		request.setAttribute("color", request.getParameter("color"));
		request.setAttribute("tiempo", request.getParameter("tiempo"));
		request.setAttribute("idEstatus", request.getParameter("idEstatus"));
		request.setAttribute("descEstatus", request.getParameter("descEstatus"));
		List<EstatusPojo> estatus = myDao.getEstatus();
		for (EstatusPojo estatusPojo : estatus) {
			if (estatusPojo.getIdEstatus().equals(1) || estatusPojo.getIdEstatus().equals(2) || estatusPojo.getIdEstatus().equals(3)) {
				estatusPojos.add(estatusPojo);
			}
		}
		request.setAttribute("listaEstatus", estatusPojos);
		colores.add("Rojo");
		colores.add("Amarillo");
		colores.add("Verde");
		for (int i = 1; i <= 60; i++) {
			tiempo.add(String.valueOf(i));
		}
		request.setAttribute("colores", colores);
		request.setAttribute("tiempos", tiempo);
		request.setAttribute(ConstantesReportes.PANELID, "editPeticiones");
		request.setAttribute(ConstantesReportes.TITULO, "Editar Alerta");
		return mapping.findForward("editAlerta");

	}
	
	public ActionForward updateAlerta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		String mensaje = "";
		String idAlerta=request.getParameter("idAlerta");
		String color=request.getParameter("color");
		String tiempo=request.getParameter("tiempo");
		if (!tiempo.contains("Min")) {
			tiempo=request.getParameter("tiempo") + " Min";
		}
		String idEstatus=request.getParameter("idEstatus");	
		if (myDao4.updateAlerta(Integer.valueOf(idAlerta),color,tiempo,Integer.valueOf(idEstatus))) {
			mensaje = "Se actualizo correctamente la alerta";
			log.info("Se actualizo correctamente la alerta");
		}else {
			mensaje = "Hubo un error al intentar actualizar la alerta: ";
			log.info("Hubo un error al intentar actualizar la alerta: ");
		}
		request.setAttribute("mensaje", mensaje);
		return mapping.findForward("resultadoDividirCerrar");
		
	}
	
	public ActionForward eliminarAlerta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{	
		String idAlerta = request.getParameter("idAlerta");
		String estatus = myDao.eliminarAlerta(Integer.valueOf(idAlerta));
		request.setAttribute("mensaje", estatus);
		return mapping.findForward("resultadoDividirCerrar");

	}
	
	public ActionForward eliminarPeticion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{	
		String idPeticion = request.getParameter("idPeticion");
		String estatus = myDao.eliminarPeticion(Integer.valueOf(idPeticion));
		request.setAttribute("mensaje", estatus);
		return mapping.findForward("resultadoDividirCerrar");

	}
	
	public void setMyDao(TransferenciasDaoConsultaInterface myDao) {
		this.myDao = myDao;
	}

	public void setMyDao2(TransferenciasDaoUtilInterface myDao2) {
		this.myDao2 = myDao2;
	}

	public void setMyDao3(TransferenciasDaoInterface myDao3) {
		this.myDao3 = myDao3;
	}
	
	public void setMyDao4(TransDaoInterface2 myDao4) {
		this.myDao4 = myDao4;
	}
	
}
