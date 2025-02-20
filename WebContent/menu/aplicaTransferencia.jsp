<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

  Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
 		
 		new Ext.form.ComboBox({
 			id:'bancoAplicaExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    width:100,
		    transform:'bancoAplica',
		    forceSelection:true
		});
 		

		new  Ext.form.DateField({
			id:'fechaAplicaExt',
 			renderTo:'fechaAplica',
 			name:'fecha',
 			width:100,
 			editable:true		
 		});
 		
 		new  Ext.form.DateField({
			id:'fechaAplicaFinExt',
 			renderTo:'fechaAplicaFin',
 			name:'fecha',
 			width:100,
 			editable:true		
 		});
 		
 		new  Ext.Button({
	      	   id:'botonBuscarExt',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'buscarAplica',
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getCmp('fechaAplicaExt').value==null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
	 					 return;
		            	}
		            	
		            	//alert(Ext.getCmp('bancoAplicaExt').value)
		            
		                ajaxDivUpdater('TemplateAction.action','parameter=aplicaListaTransferencias&fecha='+Ext.getCmp('fechaAplicaExt').value+'&banco='+Ext.getCmp('bancoAplicaExt').value+'&importe='+Ext.getDom('importe').value+'&fecha2='+Ext.getCmp('fechaAplicaFinExt').value,'listaTransferenciaAplica'); 
		            }
		    	}
	      });

  });
</script>
<div class="instrucciones">	
	Instrucciones:<br/>
	- Seleccione los criterios que requiera y de clic en Buscar (campos marcados con * son obligatorios).<br/>
	- La Fecha de busqueda es la Fecha de la Transferencia.<br/>
	- Seleccione las transferencias que quiera programar (<b>CU->Cuentas, FA->Facturas, MIX-Mixtas</b>).<br/>
	- <b>Programar:</b> La transferencia sera programada para su aplicación en mobile. Podra visualizarla en las transferencias EN PROCESO, una vez sea aplicada se vera reflejada en APLICADAS o si ocurrio algun error en ERROR.<br/> 
</div>

<div> 

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
					<td colspan="3" >
						<select id="bancoAplica" >
				   			<c:forEach var="banco" items="${listaBancos}">
				   			<option value="${banco.idbanco}" >${banco.nombre}</option>
				   		    </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Fecha Ini*:</td>
					<td>
						<div id="fechaAplica"></div>
					</td>
					<td>Fecha Fin:</td>
					<td>
						<div id="fechaAplicaFin"></div>
					</td>
				</tr>		
				<tr>
					<td>Importe:</td>
					<td colspan="3">
					<input  id="importe"  class="x-form-text x-form-field" value="" type="text" maxlength="30"  />
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="right" colspan="3" >
					<div id="buscarAplica"></div>
					</td>
				</tr>
							
			</table>

	</div>
	<div id="listaTransferenciaAplica">
	
	</div>
</div>