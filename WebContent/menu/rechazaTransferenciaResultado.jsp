<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

		//Ext.DomHelper.overwrite('listaTransferenciaAutoriza','<c:out value="${resultado}"/>'+', puedes generar una busqueda nueva ');
				
		mascara(false);
		ajaxDivUpdater('TemplateAction.action','parameter=detalleTransferencia&idTransferencia=<c:out value="${idTransferencia}"/>&resultado=<c:out value="${resultado}"/>','detalleCompleto'); 
		
		try{
	
		Ext.DomHelper.overwrite('listaTransferenciaAplica','Puedes generar una busqueda nueva '); 
		
		}catch(e){
		
		}

  });
</script>