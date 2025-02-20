<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 

  Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
 		
 		new Ext.form.ComboBox({
 			id:'bancoRevierteExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'bancoRevierte',
		    forceSelection:true
		});
 		

		new  Ext.form.DateField({
			id:'fechaRevierteExt',
 			renderTo:'fechaRevierte',
 			name:'fecha',
 			editable:false		
 		});
 		
 		new  Ext.Button({
	      	   id:'botonBuscarExt',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'buscarRevierte',
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getCmp('fechaRevierteExt').value==null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
	 					 return;
		            	}
		            	
		            	//alert(Ext.getCmp('bancoRevierteExt').value)
		            
		                ajaxDivUpdater('TemplateAction.action','parameter=revierteListaTransferencias&fecha='+Ext.getCmp('fechaRevierteExt').value+'&banco='+Ext.getCmp('bancoRevierteExt').value+'&importe='+Ext.getDom('importeRevertir').value,'listaTransferenciaRevierte'); 
		            }
		    	}
	      });

  });
</script>
<div class="instrucciones">	
	Instrucciones:<br/>
	- Seleccione los criterios que requiera y de clic en Buscar (campos marcados con * son obligatorios).<br/>
	- La Fecha de busqueda es la Fecha de la Transferencia.<br/>
	- Seleccione las transferencias que quiera programar para su reversion.<br/>
	- <b>Programar:</b> La transferencia sera programada para que sea revertida en mobile, esta accion tardara de 15 a 30 min.<br/> 
</div>
<div>

	<div style="padding-left:15px;" >
	
			<table width="220" cellspacing="5px" >
				<tr>
					<td width="60" >Banco*:</td>
					<td>
						<select id="bancoRevierte" >
				   			<c:forEach var="banco" items="${listaBancos}">
				   			<option value="${banco.idbanco}" >${banco.nombre}</option>
				   		    </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Fecha*:</td>
					<td>
						<div id="fechaRevierte"></div>
					</td>
				</tr>			
				<tr>
					<td>Importe:</td>
					<td>
					<input  id="importeRevertir"  class="x-form-text x-form-field" value="" type="text" maxlength="30"  />
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2" >
					<div id="buscarRevierte"></div>
					</td>
				</tr>
							
			</table>

	</div>
	<div id="listaTransferenciaRevierte">
	
	</div>
</div>