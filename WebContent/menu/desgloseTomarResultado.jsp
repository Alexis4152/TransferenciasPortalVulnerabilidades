<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

		Ext.DomHelper.overwrite('listaTransferenciaToma','<c:out value="${resultado}"/>'+', puedes generar una busqueda nueva ');		
		mascara(false);
		Ext.get('listaTransferenciaToma').highlight();
  });
</script>