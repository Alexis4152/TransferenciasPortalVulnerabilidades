<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

  Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
 		
 		new Ext.form.ComboBox({
 			id:'bancoAutorizaExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'bancoAutoriza',
		    width:100,
		    forceSelection:true
		});
 		

		new  Ext.form.DateField({
			id:'fechaExt',
 			renderTo:'fechaAutoriza',
 			name:'fecha',
 			width:100,
 			editable:true		
 		});
 		
 		new  Ext.form.DateField({
			id:'fechaAttoFinExt',
 			renderTo:'fechaAutorizaFin',
 			name:'fecha',
 			width:100,
 			editable:true		
 		});
 		
 		
 		
 		new  Ext.Button({
	      	   id:'botonBuscarExt',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'buscarAutoriza',
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getCmp('fechaExt').value==null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
	 					 return;
		            	}
		            	
		            	//alert(Ext.getCmp('bancoAutorizaExt').value)
		            
		                ajaxDivUpdater('TemplateAction.action','parameter=listaTrasferenciaAutoriza&fecha2='+Ext.getCmp('fechaAttoFinExt').value+'&fecha='+Ext.getCmp('fechaExt').value+'&banco='+Ext.getCmp('bancoAutorizaExt').value+'&importe='+Ext.getDom('importeAutoriza').value,'listaTransferenciaAutoriza'); 
		            }
		    	}
	      });

  });
</script>
<div>
	<div class="instrucciones">	
		Instrucciones:<br/>
		- Seleccione los criterios que requiera y de clic en Buscar (campos marcados con * son obligatorios).<br/>
		- La Fecha de busqueda es la Fecha de la Transferencia.<br/>
		- <b>Autorizar:</b> Seleccione las transferencias y de clic en el boton de Autorizar.<br/> 
		- <b>Cerrar:</b> Seleccione las transferencias y de clic en el boton de Cerrar (Estas transferencias ya no seran usadas en el Portal).<br/> 
	</div>
	<div style="padding-left:15px;" >
	
			<table cellspacing="5px" >
				<tr>
					<td width="80px;"></td>
					<td width="120px;"></td>
					<td width="80px;"></td>
					<td width="120px;"></td>
				</tr>			
				<tr>
					<td >Banco*:</td>
					<td colspan="3">
						<select id="bancoAutoriza" >
				   			<c:forEach var="banco" items="${listaBancos}">
				   			<option value="${banco.idbanco}" >${banco.nombre}</option>
				   		    </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Fecha Ini*:</td>
					<td>
						<div id="fechaAutoriza"></div>
					</td>
					<td>Fecha Fin:</td>
					<td>
						<div id="fechaAutorizaFin"></div>
					</td>
				</tr>		
							
				<tr>
					<td>Importe:</td>
					<td colspan="3">
					<input  id="importeAutoriza"  class="x-form-text x-form-field" value="" type="text" maxlength="30"  />
					</td>
				</tr>
				<tr>
					<td></td>	
					<td align="right" colspan="3" >
					<div id="buscarAutoriza"></div>
					</td>
				</tr>
							
			</table>

	</div>
	<div id="listaTransferenciaAutoriza">
	
	</div>
</div>