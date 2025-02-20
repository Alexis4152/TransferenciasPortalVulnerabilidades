package com.telcel.portal.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telcel.portal.dao.implementacion.TransDaoDmpImp2;
import com.telcel.portal.dao.interfaces.TransDaoInterface2;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.RespuestaPagosPojo;
import com.telcel.portal.util.ConstantesTemplate;
import com.telcel.portal.util.ProcesoArchivoExcel;
import com.telcel.portal.util.UploadForm;

public class CargaArchivoDesglose extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		TransDaoInterface2 myDao4 = new TransDaoDmpImp2();
		
		if(!myDao4.consultaEstadoDesglose(Integer.parseInt(request.getParameter(ConstantesTemplate.IDTRANSFERENCIA)))){
			System.out.println("ENTRO A IF  DE EXECUTE");
			request.setAttribute(ConstantesTemplate.IDTRANSFERENCIA, request.getParameter(ConstantesTemplate.IDTRANSFERENCIA));
			request.setAttribute("guardar", 2);
			request.setAttribute("texto","No se puede cargar, la transferencia ya esta desglosada.");
			return mapping.findForward("cargaResultado");
		}
		
		ProcesoArchivoExcel procesoArchivo;
		String textoRespuesta="";
		
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
		String idTransferencia=request.getParameter("idTransferencia");
			
			 if (form instanceof UploadForm) {
				 
				 procesoArchivo= new ProcesoArchivoExcel();
				 Map map;
				
				 map=procesoArchivo.procesarExcel(form,Integer.parseInt(idTransferencia),empleado.getIdEmpleado());
				if(map.get("listaPagoPojo")!=null){
					
					ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			        TransferenciasDaoInterface myDao = (TransferenciasDaoInterface) context.getBean("TransferenciasDaoImp");
			        
			      
			       RespuestaPagosPojo respuesta;
				
			    	   respuesta=myDao.setInsertaPagos((List<PagoPojo>) map.get("listaPagoPojo"),empleado);
			    	   textoRespuesta=respuesta.getRespuesta();
			    	   request.setAttribute("cambios",respuesta.getCuetasCambiadas());
					
				}else{
					
					
					
					textoRespuesta=(String) map.get("resultado");  
					
				}
				 
			 }
		
		request.setAttribute("texto",textoRespuesta);

		return mapping.findForward("cargaResultado");

	}


}
