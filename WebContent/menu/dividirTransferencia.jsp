<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

  Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
 		
 		new Ext.form.ComboBox({
 			id:'bancoDivideExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'bancoDivide',
		    width:100,
		    forceSelection:true
		});

 		

		new  Ext.form.DateField({
			id:'fechaInicial',
 			renderTo:'fechaDividirInicio',
 			name:'fechaIni',
 			width:100,
 			editable:true		
 		});
 		
 		new  Ext.form.DateField({
			id:'fechaFinal',
 			renderTo:'fechaDividirFinal',
 			name:'fechaFin',
 			width:100,
 			editable:true		
 		});
 		
 		
 		
 		new  Ext.Button({
	      	   id:'botonBuscarExt',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'buscarDesglose',
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getCmp('fechaInicial').value==null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
	 					 return;
		            	}
		            	
		            	//alert(Ext.getCmp('bancoAutorizaExt').value)
		           
		                ajaxDivUpdater('TemplateAction.action','parameter=listaTrasferenciaDividida&fechaFin='+Ext.getCmp('fechaFinal').value+'&fechaIni='+Ext.getCmp('fechaInicial').value+'&banco='+Ext.getCmp('bancoDivideExt').value+'&importe='+Ext.getDom('importeDividir').value,'listaTransferenciaDividida'); 
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
		- <b>Dividir:</b> Seleccione la transferencia y de clic en el boton Dividir Transferencia.<br/> 
		- <b>Dividir y Cerrar:</b> Seleccione la transferencia e ingrese los montos a desglosar y de clic en el boton Dividir y Cerrar Transferencia (Esta transferencia se cerrara y creara las nuevas trasnferencias en su lugar).<br/> 
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
						<select id="bancoDivide" >
				   			<c:forEach var="banco" items="${listaBancos}">
				   			<option value="${banco.idbanco}" >${banco.nombre}</option>
				   		    </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Fecha Ini*:</td>
					<td>
						<div id="fechaDividirInicio"></div>
					</td>
					<td>Fecha Fin:</td>
					<td>
						<div id="fechaDividirFinal"></div>
					</td>
				</tr>		
							
				<tr>
					<td>Importe:</td>
					<td colspan="3">
					<input  id="importeDividir"  class="x-form-text x-form-field" value="" type="text" maxlength="30"  />
					</td>
				</tr>
				<tr>
						
					</td>
					
					<td align="right" colspan="3" >
					<div id="buscarDesglose"></div>
					</td>
				</tr>
							
			</table>

	</div>

		<div id="listaTransferenciaDividida">
	
	</div>
	
	
</div>