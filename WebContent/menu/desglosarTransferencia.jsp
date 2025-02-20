	<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

  Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
 		
 		new Ext.form.ComboBox({
 			id:'bancoTomarExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'bancoTomar',
		    forceSelection:true
		});
 		

		new  Ext.form.DateField({
			id:'fechaInicioExt',
 			renderTo:'fechaInicio',
 			name:'fecha',
 			editable:true		
 		});
 		new  Ext.form.DateField({
			id:'fechaFinExt',
 			renderTo:'fechaFin',
 			name:'fecha',
 			editable:true		
 		});
 		
 		new  Ext.Button({
	      	   id:'botonBuscarTomatExt',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'buscarTomar',
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getCmp('fechaInicioExt').value==null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
	 					 return;
		            	}
		            	
		            	//alert(Ext.getCmp('bancoAplicaExt').value)
		            
		                ajaxDivUpdater('TemplateAction.action','parameter=tomarTransferenciaLista&fecha='+Ext.getCmp('fechaInicioExt').value+'&banco='+Ext.getCmp('bancoTomarExt').value+'&importe='+Ext.getDom('importeTomar').value+'&fecha2='+Ext.getCmp('fechaFinExt').value,'listaTransferenciaToma'); 
		            }
		    	}
	      });

  });
</script>
<div>
<div class="instrucciones">	
	Instrucciones:<br/>
	- Seleccione los criterios que requiera y de clic en Buscar (campos marcados con * son obligatorios).<br/>
	- Seleccione las transferencias que quiera tomar y capture la referencia.<br/>
	- <b>Tomar:</b> Al tomar las transferencias estas seran asignadas a su usuario y podra verlas en Desglosar Transferencias.<br/> 
</div>

	<div style="padding-left:15px;" >
	
		<table cellspacing="5px" >
				<tr>
					<td width="70px;"></td>
					<td width="100px;"></td>
					<td width="70px;"></td>
					<td width="100px;"></td>
				</tr>
				<tr>
					<td >Banco*:</td>
					<td colspan="2">
						<select id="bancoTomar" >
				   			<c:forEach var="banco" items="${listaBancos}">
				   			<option value="${banco.idbanco}" >${banco.nombre}</option>
				   		    </c:forEach>
						</select>
					</td>
					<td align="right"  >
						<div id="buscarTomar"></div>
					</td>
				</tr>
				<tr>
					<td>Fecha Ini*:</td>
					<td><div id="fechaInicio"></div></td>
					<td>Fecha Fin:</td>
					<td><div id="fechaFin"></div></td>
				</tr>
				<tr>
					<td>Importe :</td>
					<td colspan="3" class="left"> 
					<input  id="importeTomar"  class="x-form-text x-form-field" value="" type="text" size="15" maxlength="30"  />
					</td>
				</tr>
				<tr>
					
				</tr>
							
			</table>

	</div>
	<div id="listaTransferenciaToma">
	
	</div>
	
</div>