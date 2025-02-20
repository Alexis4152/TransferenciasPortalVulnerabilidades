<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

		try{
		//detalleMasivoBotones//
		
		var pes=Ext.getCmp('detalle');
		pes.destroy();
		}catch(e){
		
		}

		Ext.DomHelper.overwrite('listaTransferenciaAplica','<c:out value="${resultado}"/>'+', puedes generar una busqueda nueva ');		
		mascara(false);
		Ext.get('listaTransferenciaAplica').highlight();
  });
</script>