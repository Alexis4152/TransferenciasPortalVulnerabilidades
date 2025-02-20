<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

		mascara(false);
		Ext.DomHelper.overwrite('respuestaUsuario','<c:out value="${resultado}"/>');
	    Ext.get('respuestaUsuario').highlight();	
		
		ajaxDivUpdater('TemplateAction.action','parameter=listaUsuarios','listaTransferenciaUsuarios'); 
		
		try{
	
		
		
		}catch(e){
		
		}

  });
</script>