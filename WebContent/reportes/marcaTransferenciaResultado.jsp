<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

	//	Ext.DomHelper.overwrite('resultadoAddPago','<c:out value="${resultado}"/>');		
		
	   
	    
	    try{
	    Ext.getCmp('addPagoWindowExt').close();  

	    }catch(e){
				
		}
 		ajaxDivUpdater('Reportes.action','parameter=addComentario&filtro=add&fecha=${fecha}&banco=${banco}&importe=${importe}&marca=${marca}','listaTrans'); 
		
		 
		mascara(false);
		
			
  });
</script>

<form id="listaExcel" name="forma">
<input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
<input name="banco" type="hidden" value="<c:out value="${banco}"/>" />
<input name="excel" type="hidden" value="<c:out value="${estatus}"/>" />
<input name="importe" type="hidden" value="<c:out value="${importe}"/>" />
<input name="marca" type="hidden" value="<c:out value="${marca}"/>" />
</form>