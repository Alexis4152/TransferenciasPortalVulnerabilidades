<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

		//Ext.DomHelper.overwrite('listaTransferenciaAutoriza','<c:out value="${resultado}"/>'+', puedes generar una busqueda nueva ');
				
		mascara(false);
		
      <c:if test="${detalle==false}">
		try{
	   ajaxDivUpdater('TemplateAction.action','parameter=listaTrasferenciaAutoriza&fecha='+Ext.getCmp('fechaExt').value+'&banco='+Ext.getCmp('bancoAutorizaExt').value+'&importe='+Ext.getDom('importeAutoriza').value,'listaTransferenciaAutoriza'); 
		
		}catch(e){
		
		}
		</c:if>
		<c:if test="${detalle==true}">
		
	    try{
	
		Ext.DomHelper.overwrite('listaTransferenciaAplica','Puedes generar una búsqueda nueva '); 
		
		}catch(e){
		
		}
		try{
	
		Ext.DomHelper.overwrite('listaTransferenciaAutoriza','Puedes generar una búsqueda nueva '); 
		
		}catch(e){
		
		}
		
         ajaxDivUpdater('TemplateAction.action','parameter=detalleTransferencia&idTransferencia=<c:out value="${idTransferencia}"/>&resultado=<c:out value="${resultado}"/>','detalleCompleto'); 
	    </c:if>
		
		
  });
</script>


