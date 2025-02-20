package com.telcel.portal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class LoginAction extends DispatchAction {
	
	public ActionForward template(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("loginResultado");

	}
	public ActionForward salir(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession sesion = request.getSession();
		sesion.setAttribute("empleado", null);

		return mapping.findForward("loginRedirect");

	}
	
}
