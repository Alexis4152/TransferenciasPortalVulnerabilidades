package com.telcel.portal.actions;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telcel.portal.dao.implementacion.TransferenciasDaoDmpConsultaImp;
import com.telcel.portal.dao.interfaces.TransDaoInterface2;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.pojos.TransferenciaPojo;
import com.telcel.portal.util.CargaArchivosTexto;
import com.telcel.portal.util.Constantes;
import com.telcel.portal.util.ProcesoArchivoExcel;
import com.telcel.portal.util.UploadForm;

public class CargaArchivoAction extends DispatchAction {
	
	private static final String TEXTO = "texto";
	private static final String CARGARESULT = "cargaResultado";
	private static Logger  logger = Logger.getLogger(CargaArchivoAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		CargaArchivosTexto procesoArchivo;
		String textoRespuesta="";
		List<TransferenciaPojo> transferencias=null;
		String banco = request.getParameter("banco");
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");
		boolean esCargaDiariaExcelBancomer=false;
		boolean esEstadoDeCuenta = false;
		StringBuffer reg = new StringBuffer();
		List<TransferenciaPojo> listaTransAux = null;		
		
		if(banco!=null){
			
			Integer idBanco=Integer.parseInt(banco);
			
			 if (form instanceof UploadForm) {
				 
				 procesoArchivo= new CargaArchivosTexto();
				 UploadForm theForm = (UploadForm) form;
				 ProcesoArchivoExcel procesaExcel = new ProcesoArchivoExcel (); 
				 
				 if(idBanco==Constantes.BANCOMER){
					 if ( theForm.getTheFile().getFileName().endsWith(".xlsx")  && empleado.getIdRegion()== 3) {
						 logger.info("ES EXCEL  Y REGION 3, Procesara carga diaria de bancomer");
						 esCargaDiariaExcelBancomer=true;
						 transferencias = procesaExcel.cargaExcelBancomer(theForm.getTheFile(), idBanco, empleado.getIdEmpleado(),empleado.getIdRegion());
						 esEstadoDeCuenta = true;
					} else {
						transferencias = procesoArchivo.cargaBancomer(theForm.getTheFile(),idBanco,empleado.getIdEmpleado(), empleado.getIdRegion());
						esEstadoDeCuenta = true;
					} 
					  
				 }
				 else if(idBanco==Constantes.BANCOMER_NUEVO_LAYOUT){
					 transferencias = procesoArchivo.cargaBancomerNuevoLayout(theForm.getTheFile(),idBanco,empleado.getIdEmpleado(), empleado.getIdRegion());
				 }
				 else if(idBanco==Constantes.HSBC){
					 transferencias = procesaExcel.cargaExcelHSBC(theForm.getTheFile(),idBanco,empleado.getIdEmpleado());
				 }else if(idBanco==Constantes.BANAMEX){
					 transferencias = procesaExcel.cargaExcelBanamex(theForm.getTheFile(),idBanco,empleado.getIdEmpleado());
				 }else if(idBanco==Constantes.BANORTE){
					 transferencias = procesaExcel.cargaExcelBanorte(theForm.getTheFile(),idBanco,empleado.getIdEmpleado());
				 }else if(idBanco==Constantes.SANTANDER){
					 transferencias = procesaExcel.cargaExcelSantander(theForm.getTheFile(),idBanco,empleado.getIdEmpleado());
				 }else if(idBanco==Constantes.INBURSA){
					 transferencias = procesaExcel.cargaExcelInbursa(theForm.getTheFile(),idBanco,empleado.getIdEmpleado());
				 }else{			 
					 transferencias = procesoArchivo.cargaOtrosBancos(theForm.getTheFile(),idBanco,empleado.getIdEmpleado()); 	 
				}
				
					 
				if(transferencias!=null){
					
					ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
			        TransferenciasDaoInterface myDao = (TransferenciasDaoInterface) context.getBean("TransferenciasDaoImp");
			        
			        TransDaoInterface2 myDao2 = (TransDaoInterface2) context.getBean("TransferenciasDaoImp2");
			        
			        if(myDao2.validaCargaTransferencias()){
			        	if (idBanco == Constantes.BANCOMER_NUEVO_LAYOUT) {
							idBanco = Constantes.BANCOMER;
						}
			        	
			        	List<TransferenciaPojo> listaTrans;// = myDao2.consultaTransferencias(transferencias, idBanco,empleado.getDescRegion());
			        	listaTransAux = new ArrayList<TransferenciaPojo>();
			        	
			        	if(esEstadoDeCuenta) {			        					        					        					        	
			        		
			        		for(int i = 0; i < transferencias.size(); i++) 
			        			if(transferencias.get(i).isEsAgregada())
			        				listaTransAux.add(transferencias.get(i));
			        		
			        					        		
			        		listaTrans = myDao2.consultaTransferencias(listaTransAux, idBanco,empleado.getDescRegion());
			        		
			        	}else
			        		listaTrans = myDao2.consultaTransferencias(transferencias, idBanco,empleado.getDescRegion());
			        	
			        	if(listaTrans != null){
			        					        			
			        			List<TransferenciaPojo> listaDuplicados = null;        			
			        			listaDuplicados = myDao2.identificaDuplicados(transferencias, listaTrans,empleado.getIdRegion());
			        			
			        			listaTransAux.clear();
			        			
			        			
	        					if(esEstadoDeCuenta)
	        						transferencias = filtraLista(transferencias,1);
	        						
	        					
			        			if (listaDuplicados != null){
			        				if(listaDuplicados.size() > 0){			        					
			        			        				        				        			        			        				
			        					if (esCargaDiariaExcelBancomer) {  

			        						log.info("Total transferencias ANTES incluye duplicadas: "+transferencias.size());
			        						//transferencias.removeAll(listaDuplicados);
			        						transferencias = filtraLista(transferencias,2);
			        						log.info("Total transferencias DESPUES Quitanldo las duplicadas: "+transferencias.size());
			        						log.info("Total duplicados: "+listaDuplicados.size());
			        						for(TransferenciaPojo bean:listaDuplicados){
				        						reg.append(bean.getIdtransferencia()+",");
				        						
				        					}
			        						log.info("esCargaDiariaExcelBancomer: Estos registros ya estaban cargados: "+reg +":FIN");
			        						if(transferencias.size()==0){
			        						textoRespuesta="No se inserto ningun registro.";
						        			request.setAttribute(this.TEXTO,textoRespuesta);
						        			return mapping.findForward(this.CARGARESULT);
			        						}
											
										} else {											
											
											for(TransferenciaPojo bean:listaDuplicados)
				        						reg.append(bean.getIdtransferencia()+",");				        						
											
											if(!esEstadoDeCuenta) {	
					        					textoRespuesta="No se puede continuar, los siguientes registros ya se cargaron: "+reg;
							        			request.setAttribute(this.TEXTO,textoRespuesta);
							        			return mapping.findForward(this.CARGARESULT);
											}
											else {
												
												if(esEstadoDeCuenta)
													transferencias = filtraLista(transferencias,2);																        			        	
				        			        	
				        			        	if(transferencias.size() == 0) {
				        			        		textoRespuesta = String.format("No se insertaron registros. Los registros %s del archivo ya se encuentran registrados.",reg);
				        			        		request.setAttribute(this.TEXTO,textoRespuesta);
								        			return mapping.findForward(this.CARGARESULT);
				        			        	}
											}									
										}
			        				}
			        				
			        			}else{
			        				textoRespuesta="Error al validar carga de transferencias";
				        			request.setAttribute(this.TEXTO,textoRespuesta);
				        			return mapping.findForward(this.CARGARESULT);
			        			}
			        			
			        		
			        	}else{
			        		textoRespuesta="Error al validar carga de transferencias";
		        			request.setAttribute(this.TEXTO,textoRespuesta);
		        			return mapping.findForward(this.CARGARESULT);
			        	}
			        	
			        }
			        			        			        			      			        
			        if(request.getParameter("automatico")!=null && request.getParameter("automatico").equals("true")){
			        		textoRespuesta= myDao.insertaTransferencias(transferencias,Constantes.AUTORIZADA,empleado.getDescRegion());
			        }else{
			        		textoRespuesta= myDao.insertaTransferencias(transferencias,Constantes.CARGADA,empleado.getDescRegion());
			        }
			        
			        if(!textoRespuesta.contains("Error")) {
			        	int listaSize = transferencias.size();
			        	textoRespuesta = textoRespuesta.concat(String.format(". Se insertaron %s registro%s", listaSize,listaSize > 1?"s.":"."));
			        }
			        
		        	if(reg.length() > 0) {
		        		textoRespuesta = String.format("%s Los registros %s del archivo ya se encuentran registrados.", textoRespuesta,reg);
		        	}		        	
					
				}else{
					
					textoRespuesta="Hubo un error en el formato del archivo";  
					
				}
				 
			 }
		}else{
			
			textoRespuesta="Selecciona un banco";
		}
		request.setAttribute(this.TEXTO,textoRespuesta);

		return mapping.findForward(this.CARGARESULT);

	}

	private List<TransferenciaPojo> filtraLista(List<TransferenciaPojo> transferencias, int bandera) {		
		
		List<TransferenciaPojo> transferenciasAux = new ArrayList<>();
		
		for(int i = 0; i < transferencias.size(); i++) {
			TransferenciaPojo tranAux = transferencias.get(i); 
			if((bandera == 1 && tranAux.isEsAgregada()) || (bandera == 2 && !tranAux.isEsDuplicada()))	        								
					transferenciasAux.add(tranAux);
		}				
		
		return transferenciasAux;
	}


}
