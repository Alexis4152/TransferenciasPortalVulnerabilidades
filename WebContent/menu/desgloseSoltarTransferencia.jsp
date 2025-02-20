<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

	//	Ext.DomHelper.overwrite('resultadoAddPago','<c:out value="${resultado}"/>');		
		
	   
	    <%if(request.getAttribute("resultado").toString().equals("ok")){ %>
	  
      
        Ext.DomHelper.overwrite('desgloseTransferenciasDetalle1','Transferencia Soltada, por favor cierra la pestaña');
        //tomarDivLista
          Ext.DomHelper.overwrite('desglosarDivLista','Transferencia Liberada, por favor cierra la pestaña');
		<%}else{ %>
		  	ajaxDivUpdater('TemplateAction.action','parameter=desgloseTransferenciasListaDetalle&idTransferencia=${idTransferencia}&resultado=${resultado}','listaPagosDesglose'); 
		<%}%>
		mascara(false);
		try{
		  Ext.get('estatusDesglose').highlight(); 
		}catch(e){
		
		
		}
			
  });
</script>

