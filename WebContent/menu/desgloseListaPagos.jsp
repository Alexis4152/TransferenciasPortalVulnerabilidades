<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
var total=0;
 Ext.onReady(function(){
 
 	//	Ext.DomHelper.overwrite('detalleTrasferenciaTexto','<c:out value="${resultado}"/>');			
		//Ext.get('detalleTrasferenciaTexto').highlight();
 	<c:if test="${lista <= 50}">
        var grid = new Ext.ux.grid.TableGrid("the-table-detalleDesglose", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();
   </c:if>	

	   <c:if test="${estatus == 2}">
	    new  Ext.Button({
	      	   text:'Agregar Pagos',
	      	   width:'80',	      	                
	      	   applyTo:'addPagos',
	      	   listeners:{
		            'click': function(){
		           	addPago();
		            }
		    	}
	      });  

	    <c:if test="${!banderaEstatus}">
	    new  Ext.Button({
	      	   text:'Liberar Transferencia',
	      	   width:'140',	      	                
	      	   applyTo:'soltarPagos',
	      	   listeners:{
		            'click': function(){
		           	
		           	        Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: 'Esta acción borrara todos los pagos ¿Esta Seguro?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			
					           			mascara(true);
					           			
					           			ajaxDivUpdater('TemplateAction.action','parameter=soltarTransferencia&idTransferencia=${idTransferencia}','dsplay_none'); 
		     
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		           	
		            }
		    	}
	      });  
	      
	    </c:if>
	    new  Ext.form.TextField({
			id:'capturaCorreoExt',
 			renderTo:'capturaCorreo',
 			name:'correo',
 			editable:true		
 		});
	    new  Ext.Button({
	      	   text:'Guardar',
	      	   width:'60',	      	                
	      	   applyTo:'guardarPagos',
	      	   listeners:{
		            'click': function(){
		            
		            	//capturaCorreo
		            	var el = Ext.fly('capturaCorreoExt');
		                var correo = el.getValue(); 
		                
		                if(!correo || correo.length === 0){
		                	Ext.MessageBox.alert('Estatus','Debe capturar el correo electronico de notificaci&oacute;n.' );
			            	 return;
		                }else if(!correo.match('[a-z,.,_,\-,A-z,0-9]+(@mail.telcel.com|@telcel.com)')){
		                	Ext.MessageBox.alert('Estatus','El correo electr&oacute;nico debe ser de dominio telcel y v&aacute;lido.' );
			            	 return;
		                }
		                
		            	if('<c:out value="${bandera}"/>'=='false'){
		            	
		            	 Ext.MessageBox.alert('Estatus','El importe no coincide con la suma de pagos' );
		            	 return;
		            	}
		           	 	mascara(true);


		            	//ajaxDivUpdater('TemplateAction.action','parameter=desgloseTransferenciasListaDetalle&idTransferencia=${idTransferencia}','listaPagosDesglose');
		            	ajaxDivUpdater('TemplateAction.action','parameter=setDesglosado&idTransferencia=${idTransferencia}&correoElectronico='+correo+'&'+Ext.Ajax.serializeForm('tipoPagosHidden'),'dsplay_none');  

		            }
		    	}
	      });    

</c:if>

  try{
		  Ext.get('resultadoAddPago').highlight(); 
		}catch(e){
		
		
		}
	
		
		
  });
  
  function addPago(){
  
  new Ext.Window({
  				id:'addPagoWindowExt',
  				title:'Agregar Pago',                
                layout:'fit',
                width:300,
              //  height:100, 
                modal:true,
                
                plain: true,
                 autoLoad:{
		                   url:'TemplateAction.action',
		                   	//el párametro "parameter" hace referencia a un metodo de Dispatch action
							params:'parameter=addPago&idTransferencia=${idTransferencia}&idReferencia=${idReferencia}'
		                   }
                }).show();
  
  }
   function editarPago(region,cuenta,monto,pago,tipo,sap){
  
  new Ext.Window({
  				id:'addPagoWindowExt',
  				title:'Agregar Pago',                
                layout:'fit',
                width:300,
            //    height:100, 
                modal:true,
                
                plain: true,
                 autoLoad:{
		                   url:'TemplateAction.action',
		                   	//el párametro "parameter" hace referencia a un metodo de Dispatch action
							params:'parameter=addPago&idTransferencia=${idTransferencia}&region='+region+'&cuenta='+cuenta+'&importe='+monto+'&idPago='+pago+'&tipo='+tipo+'&sap='+sap
		                   }
                }).show();
  
  }
  function eliminarPago(pago){
  
  	            mascara(true);

		            	
	          	ajaxDivUpdater('TemplateAction.action','parameter=addPago&add=eliminar&idTransferencia=${idTransferencia}&idPago='+pago,'dsplay_none');  
 
  
  }
</script>

<div>

	Suma de los pagos:$ ${suma}
	<form id="desglosePagosForm">
	<table  width="100%" id="the-table-detalleDesglose">
	
	        <thead>
	            <tr style="background:#eeeeee;">
	            	<th width="55px" >No</th>
	                <th>Region</th>
	                <th>Cuenta</th>
	                <th>Importe</th>	
	                <th>Tipo</th>	               
	                <th>Esporadico SAP</th>	
	                
	                <th>Editar</th>
	                <th>Eliminar</th>  
	                
	                 <th>Estatus PP</th>
	                <th>Descripcion PP</th>                    
	                	
	            </tr>
	        </thead>
	
	        <tbody>
		       <c:forEach var="pagos" items="${listaPagos}" varStatus="a"  >
		            <tr>
		                <td ><c:out value="${a.index+1}"/></td>
		                <td>${pagos.region}</td>
		                <td>${pagos.cuenta}</td>   
		                <td>$ ${pagos.importeString}</td>  	
		                <td>${pagos.tipo} </td>  
		                <td>${pagos.sap} </td>  
		                <td> <c:if test="${pagos.estatusPago != 'CL' }">   <c:if test="${estatus == 2}"> <a href="javascript:editarPago('${pagos.region}','${pagos.cuenta}','${pagos.importe}','${pagos.idPago}','${pagos.tipo}','${pagos.sap}')">Editar</a> </c:if>  </c:if></td>	
		                <td> <c:if test="${pagos.estatusPago != 'CL' }">  <c:if test="${estatus == 2}"> <a href="javascript:eliminarPago('${pagos.idPago}')">Eliminar</a>  </c:if> </c:if>  </td>	                	                
		                
		                 <td>${pagos.estatusPP} </td>  
		                <td>${pagos.descripcionPP} </td>  
		                
		            </tr>				   			
				   			
		       </c:forEach>
		       
	     
	            
	            
	            
	        </tbody>
	    </table>
	    
	    <div id="resultadoAddPago" > ${resultado}  </div>
</form>	
 <br/>
		<table>
			<tr>
			<c:if test="${estatus == 2}">
			<td>Correo electronico a notificar:</td>
			</c:if>
			<td colspan="2"> <div align="center" id="capturaCorreo"></div></td>
			</tr>
			<tr>
			<td><div align="center" id="addPagos"></div></td>
			<td> <div align="center" id="guardarPagos"></div></td>
			<td> <div align="center" id="soltarPagos"></div></td>
			</tr>
		</table>
	   
	    
	    
	    
	    <br/>
<form id="tipoPagosHidden">	 
 <c:forEach var="pagos" items="${listaPagos}" varStatus="a"  >   
 
	<input type="hidden" name="tipoPagos" value="${pagos.tipo}">

 </c:forEach>	    
</form>	   
	 </div>  
	 
	 
