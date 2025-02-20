<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

	//	Ext.DomHelper.overwrite('resultadoAddPago','<c:out value="${resultado}"/>');		
		
	   
	    <%if(request.getAttribute("guardar")==null){ %>
	    try{
	    Ext.getCmp('addPagoWindowExt').close();  
	    
	    
	    <c:if test="${cambios!=null}">
	     // Ext.MessageBox.alert('Estatus','Hola' );
	    
	    new Ext.Window({
  				id:'cambioCuentasExt',
  				title:'Cuentas Cambiadas',                
                layout:'fit',
                width:300,
              //  height:100, 
                modal:true,                
                plain: true,
                contentEl:'cuantasCambiadasDiv'
                }).show();
        </c:if>
	    }catch(e){
		
		
		}
                
 		ajaxDivUpdater('TemplateAction.action','parameter=desgloseTransferenciasListaDetalle&idTransferencia=${idTransferencia}&resultado=${resultado}','listaPagosDesglose'); 
		<%}else if(((Integer)request.getAttribute("guardar")) == 1){ %>
			ajaxDivUpdater('TemplateAction.action','parameter=desgloseTransferenciasPromesa&idTransferencia=${idTransferencia}','desgloseTransferenciasDetalle1');
			
		<%}else {%>
			ajaxDivUpdater('TemplateAction.action','parameter=desgloseTransferencias&idTransferencia=${idTransferencia}','desgloseTransferenciasDetalle1');
			Ext.MessageBox.alert('Error','No se pudo concluir la operación, intente de nuevo.' );
		<%}%>
		mascara(false);
		try{
		  Ext.get('estatusDesglose').highlight(); 
		}catch(e){
		
		
		}
			
  });
</script>

<div id="cuantasCambiadasDiv">

<c:forEach items="${cambios}" var="entry">
    ${entry.value}    
</c:forEach>


</div>

