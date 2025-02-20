package com.telcel.portal.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.telcel.portal.dao.interfaces.TransDaoInterface2;
import com.telcel.portal.pojos.CargaDocumentoPojo;
import com.telcel.portal.pojos.EmpleadoPojo;
import com.telcel.portal.util.ProcesoArchivoExcel;
import com.telcel.portal.util.UploadForm;

public class CargaArchivoCompensacion extends DispatchAction {

	private static final String TEXTO = "texto";
	private static final String CARGARESULT = "cargaResultado";
	private static Logger logger = Logger.getLogger(CargaArchivoCompensacion.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String textoRespuesta = "";
		List<CargaDocumentoPojo> transferencias = null;
		HttpSession sesion = request.getSession();
		EmpleadoPojo empleado = (EmpleadoPojo) sesion.getAttribute("empleado");

		if (form instanceof UploadForm) {

			UploadForm theForm = (UploadForm) form;
			ProcesoArchivoExcel procesaExcel = new ProcesoArchivoExcel();

			if (theForm.getTheFile().getFileName().endsWith(".xlsx")) {
				transferencias = procesaExcel.procesaArchivoCompensacion(theForm.getTheFile());
				if(transferencias == null || transferencias.isEmpty()) {
					textoRespuesta = "No se encontraron numeros de documento por cargar.";
				}else {
					Set<CargaDocumentoPojo> set = new HashSet<>();
					set.addAll(transferencias);
					if(set.size()<transferencias.size()) {
						log.info("El archivo contiene registros duplicados");
						textoRespuesta = String.format("Se detectaron registros duplicados en las lineas %s", Arrays.toString(findDuplicates(transferencias).toArray()));
					}else {
						ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
						TransDaoInterface2 myDao2 = (TransDaoInterface2) context.getBean("TransferenciasDaoImp2");
						List<Long> repetidos = myDao2.consultaNumerosDocumento(transferencias);
						if(!repetidos.isEmpty()) {
							textoRespuesta = String.format("Los numeros de documento %s ya se encuentran registrados", Arrays.toString(repetidos.toArray()));
						}else {
							myDao2.insertaNumerosDocumento(transferencias);
							textoRespuesta = "Se registraron con exito los numeros de documento";
						}
					}
				}
			} else {
				textoRespuesta = "Es necesario cargar un archivo .xlsx";
			}

			if (transferencias != null) {

			}

		} else {

			textoRespuesta = "Hubo un error en el formato del archivo";

		}

		request.setAttribute(this.TEXTO, textoRespuesta);

		return mapping.findForward(this.CARGARESULT);

	}
	
	private List<CargaDocumentoPojo> findDuplicates(List<CargaDocumentoPojo> listContainingDuplicates)
	{ 
	  final List<CargaDocumentoPojo> setToReturn = new ArrayList<CargaDocumentoPojo>(); 
	  final Set<CargaDocumentoPojo> set1 = new HashSet<>();

	  for (CargaDocumentoPojo yourInt : listContainingDuplicates)
	  {
	   if (!set1.add(yourInt))
	   {
	    setToReturn.add(yourInt);
	   }
	  }
	  return setToReturn;
	}

}
