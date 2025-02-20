
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
	       		new  Ext.Button({
	      	   id:'dividirCerrarTransferencias',
	      	   text:'Dividir y Cerrar Transferencia',
	      	   width:'60',	      	                
	      	   applyTo:'dividirCerrarTransferencia',
	      	   listeners:{
		            'click': function(){
		               		if(Ext.getDom('comentaCierre').value==''){
		           			Ext.MessageBox.alert('Estatus','El comentario es obligatorio' );
		            		return;
		            	}
		           		<c:forEach var="cantidad" items="${listaCantidad}">
		           		if(Ext.getDom('numero${cantidad}').value==''){
		           			Ext.MessageBox.alert('Estatus','El campo Desglose numero ${cantidad} es obligatorio' );
		            		return;
		            	}
		            		
		           		</c:forEach>
// 		           			if(sumaIncorrecta()){
// 		           			Ext.MessageBox.alert('Estatus','Los valores del desglose deben coincidir con el importe' );
// 		            		return;
// 		            	}
// 		           		mascara(true); numero${cantidad}

		                ajaxDivUpdater('TemplateAction.action','parameter=dividirCerrarTransferencia&'+Ext.Ajax.serializeForm('dividirCerrarForm')+'&comentario='+Ext.getDom('comentaCierre').value,'listaTransferenciaDividida'); 
		            }
		    	}
	      });
	      
	      


  });

</script>


<form id="dividirCerrarForm" name="dividirCerrarForm" >


	        <c:forEach var="cantidad" items="${listaCantidad}">
	          <br>
	                 Desglose numero ${cantidad}: <input name="numero${cantidad}" id="numero${cantidad}" value="" type="text"/>                 
	           	<br>
			</c:forEach>
			
	       <input type="hidden" name="total" value="${listaCantidad.size()}" />
	        <input type="hidden" name="id_Transferencia" value="${id_Transferencia}" />
	        <input type="hidden" id="importe" name="importe" value="${importe}" />
	    <br>
	     Comentario cierre: <input id="comentaCierre" type="text" size="60" maxlength="100">
        <br><br>
				<div id="dividirCerrarTransferencia" ></div>
</form> 
<br><br>

   

    

