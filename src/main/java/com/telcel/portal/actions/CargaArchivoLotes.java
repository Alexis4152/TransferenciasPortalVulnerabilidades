package com.telcel.portal.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telcel.portal.dao.interfaces.TransferenciasDaoUtilInterface;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.util.ProcesoArchivoExcelLotes;
import com.telcel.portal.util.UploadForm;

public class CargaArchivoLotes extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ProcesoArchivoExcelLotes procesoArchivo;
		String textoRespuesta="";
		ActionForward forward = new ActionForward();
				
			 if (form instanceof UploadForm) {
				 
				 procesoArchivo= new ProcesoArchivoExcelLotes();
				 Map map;
				 
				 map=procesoArchivo.procesarExcel(form);
				if(map.get("listaPagoPojo")!=null){
					
					ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			        
			        TransferenciasDaoUtilInterface myDao2 = (TransferenciasDaoUtilInterface) context.getBean("TransferenciasDaoUtilInterface");
			        List<TransferenciaPojo> listaTrasferencias = new ArrayList <TransferenciaPojo>();
			        
			        List<PagoPojo> listaLotes = (List<PagoPojo>) map.get("listaPagoPojo");
			        EmpleadoPojo empleadoPojo = (EmpleadoPojo)request.getSession().getAttribute("empleado");
			        for(PagoPojo registro:listaLotes){
			        
			        	TransferenciaPojo pago = myDao2.getTransferenciasByPagosNoIdent(registro.getRegion(), Integer.parseInt(registro.getLote()), registro.getFechaNumLote(),empleadoPojo.getDescRegion());
			        	
			        	if(pago == null){
			        		pago = new TransferenciaPojo();
			        		pago.setImporte(0.0);
			        	}
			        	
			        	double importeLote = registro.getImporte();
			        	double importeTotal = pago.getImporte();
			        	BigDecimal impL = new BigDecimal(importeLote);
			        	BigDecimal impT = new BigDecimal(importeTotal);
			        	impL = impL.setScale(0,BigDecimal.ROUND_CEILING);
			        	impT = impT.setScale(0,BigDecimal.ROUND_CEILING);
			        	
			        	if(!impL.equals(impT)){
			        		pago.setImporte(0.0);
			        		pago.setImporteTransString(String.valueOf(registro.getImporte()));
			        	}
			        	
						pago.setFecha(registro.getFechaLoteString());
						pago.setLote(registro.getLote());
						pago.setRegion(registro.getRegion());
						
					
						listaTrasferencias.add(pago);
			        }
					
					request.getSession().setAttribute("listaPagosNoIdent", listaTrasferencias);	
			    	   
			    	request.setAttribute("texto",textoRespuesta);
			    	forward = mapping.findForward("cargaResultadoPagos");
					
				}else{
					
					textoRespuesta=(String) map.get("resultado");  
					request.setAttribute("texto",textoRespuesta);
					forward = mapping.findForward("cargaResultadoError");
				}
				 
			 }

		return (forward);

	}


}
