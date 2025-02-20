package com.telcel.portal.actions;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.telcel.portal.dao.implementacion.TransferenciasDaoImp3;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface3;
import com.telcel.portal.pojos.ValidadorCuentasPojo;
import com.telcel.portal.util.ProcesoArchivoReponsabilidad;
import com.telcel.portal.util.UploadForm;

public class CargaArchivoValidadorCuentas extends DispatchAction {
	private static final String FECHACREACION = "fechaCreacion";
	private static final String TEXTO = "textoMensaje";
	private static final String ERRORES = "erroresProceso";
	private static final String CARGARESULT = "cargaResultadoCuentas";
	public static final String FORMATO_FECHA = "dd/MM/yyyy";
	private Format formatter = new SimpleDateFormat(FORMATO_FECHA);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		int erroresProceso = 0;		
		String textoRespuesta = new String();
		String fechaCreacion = formatter.format(new Date());
		HttpSession sesion = request.getSession();

		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		TransferenciasDaoInterface3 myDao = (TransferenciasDaoImp3) context.getBean("TransferenciasDaoImp3");

		if (form instanceof UploadForm) {

			UploadForm theForm = (UploadForm) form;
			ProcesoArchivoReponsabilidad procesaExcel = new ProcesoArchivoReponsabilidad();
			List<ValidadorCuentasPojo> listaCuentas;

			try {

				listaCuentas = procesaExcel.cargarXLSX(theForm);
				textoRespuesta = procesaExcel.getMensajesProceso();
				erroresProceso = procesaExcel.getErroresProceso();
				if (erroresProceso != 100 && listaCuentas.size() > 0) {
					List<ValidadorCuentasPojo> listaCuentasResultado = myDao.consultaValidadorCuentas(listaCuentas);

					if (!listaCuentasResultado.isEmpty()) {
						request.getSession().setAttribute("listaValidadorCuentas", listaCuentasResultado);
					} else {
						textoRespuesta = "Hubo un problema al procesar el archivo.";
					}
				}
				else if(erroresProceso != -1){
					textoRespuesta = "Hubo un problema al leer el archivo. Verifique su contenido.";
					erroresProceso = 100;
				}
			} catch (IOException e) {
				textoRespuesta = "Hubo un problema al leer el archivo. Verifique su contenido.";
				erroresProceso = 100;
			} catch (Exception ex) {
				textoRespuesta = "Hubo un problema al leer el archivo. Verifique su contenido.";
				erroresProceso = 100;
			}

		}
		
		sesion.setAttribute(CargaArchivoValidadorCuentas.ERRORES, erroresProceso);
		sesion.setAttribute(CargaArchivoValidadorCuentas.TEXTO, textoRespuesta);
		sesion.setAttribute(FECHACREACION, fechaCreacion);
		return mapping.findForward(CargaArchivoValidadorCuentas.CARGARESULT);

	}

}
