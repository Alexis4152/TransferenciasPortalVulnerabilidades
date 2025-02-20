<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript">
 Ext.onReady(function(){
 	
 	Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
 		var tb = new Ext.Toolbar({
		    items: [ 
		        { 
		            // xtype: 'button', // default for Toolbars, same as 'tbbutton'
		            iconCls: 'excel', 
		            text: ' ',
		            width: 40,
		            listeners:{
		            'click': function(){
		            
		            	
		            	var redirect = 'Reportes.action?filtros=ok&parameter=transferenciasByPagosNoIdent&'+Ext.Ajax.serializeForm('listaTraExcelBanco'); 
	                 //   alert(redirect);
	                    document.location.href=redirect;
	                    
		            
		            
		               
			            }
			    	}   
		        }
		    ]
		});  
		tb.render('toolbar<c:out value="${panelId}"/>');
		
 		
		new  Ext.form.DateField({
			id:'fechaInicio<c:out value="${panelId}"/>Ext',
 			renderTo:'fechaInicio<c:out value="${panelId}"/>',
 			name:'fecha',
 			editable:true		
 		});
 		
 		var lot = new  Ext.form.TextField({
			id:'lote<c:out value="${panelId}"/>Ext',
			renderTo:'lote<c:out value="${panelId}"/>',
 			name:'lote'
 			
 		});
 		
 		var imp = new  Ext.form.TextField({
			id:'importe<c:out value="${panelId}"/>Ext',
			renderTo:'importe<c:out value="${panelId}"/>',
 			name:'importe'
 			
 		});
 		
 		new Ext.form.ComboBox({
 			id:'tipoRegion<c:out value="${panelId}"/>Ext',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'tipoRegion<c:out value="${panelId}"/>',
		    forceSelection:true
		});
 		
 		
 		new  Ext.Button({
	      	   id:'botonBuscar<c:out value="${panelId}"/>Ext',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'buscar<c:out value="${panelId}"/>',
	      	   listeners:{
		            'click': function(){
		            
		            	Ext.DomHelper.overwrite('fileref','');
		            	Ext.DomHelper.overwrite('listaTransferencia','');
		            	
		            	if(Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value==null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
	 					 return;
		            	}
		            	if(imp.getValue() == null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Captura un importe' );
	 					 return;
		            	}
		            	if(lot.getValue() == null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Captura un lote' );
	 					 return;
		            	}
		            
		                //ajaxDivUpdater('Reportes.action','parameter=transferenciasByPagosNoIdent&filtros=ok&fecha='+Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value+'&importe='+imp.getValue()+'&lote='+lot.getValue()+'&region='+Ext.getCmp('tipoRegion<c:out value="${panelId}"/>Ext').value,'listaTransferencia<c:out value="${panelId}"/>');
		                
		               mascara(true);
		               ajaxDivUpdater('Reportes.action','parameter=transferenciasByPagosNoIdent&filtros=ok&fecha='+Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value+'&importe='+imp.getValue()+'&lote='+lot.getValue()+'&region='+Ext.getCmp('tipoRegion<c:out value="${panelId}"/>Ext').value,'listaTransferencia');
		              
		            }
		    	} 
	      });

  });
  function validaCargaDesglosa(){
 
 	var file;
 	var fileSplit;
 	
 	Ext.DomHelper.overwrite('fileref','');
	Ext.DomHelper.overwrite('listaTransferencia','');
 	
	 if(Ext.getDom('theFile').value==''){
	 
		 Ext.MessageBox.alert('Estatus','Selecciona un archivo' );
	 	 return;
	 }
 
    mascara(true);
 	document.getElementById('file_upload_form_desglose').submit();
 	//mascara(false);

 }
 
 function resultadoDesglosar(texto){
 	//alert('transBy');
 	mascara(false);
 	//Ext.MessageBox.alert('Estatus',texto );
 	ajaxDivUpdater('Reportes.action','parameter=transferenciasByPagosNoIdent&filtros=PA','listaTransferencia'); 
 
 }
 
 function resultado(texto){
 	mascara(false);
  	Ext.DomHelper.overwrite('fileref',texto);
 }
 
 document.getElementById('file_upload_form_desglose').target = 'upload_target_desglosa';
</script>

<div>
<div id="toolbar<c:out value="${panelId}"/>"></div>
<div>
<div class="instrucciones">
	<b>El reporte muestra la información de una transferencia a partir de un lote de pago.</b><br/>
	Instrucciones:<br/>		
	- Seleccione los criterios que requiera y de clic en Buscar. Todos los campos son obligatorios.<br/>
	- Para exportar el reporte a EXCEL de clic sobre el icono ubicado en la parte superior izquierda de esta area.<br/>
</div>
		<br>
		<label >Consulta Simple</label>
		<br><br>
			<table  cellspacing="5px" border="1">
				<tr>
		   			<td>Región:</td>
		   			<td>
		   			<select id="tipoRegion<c:out value="${panelId}"/>" >
		   			<option value="R09" SELECTED>R09
   					<option value="R08" >R08
   					<option value="R07" >R07
   					<option value="R06" >R06
   					<option value="R05" >R05
   					<option value="R04" >R04
   					<option value="R03" >R03
   					<option value="R02" >R02
   					<option value="R01" >R01
					</select>
					</td>
		   		</tr>
		   		<tr>
					<td>Lote:</td>
		   			<td width="100px"><div id="lote<c:out value="${panelId}"/>"></div></td>
		   		</tr>
				<tr>
					<td>Importe:</td>
		   			<td width="100px"><div id="importe<c:out value="${panelId}"/>"></div></td>
		   		</tr>
		   		<tr>
					<td>Fecha:</td>
					<td width="100px"><div id="fechaInicio<c:out value="${panelId}"/>"></div></td>
					
				</tr>
		   		<tr>
		   			<td><div id="buscar<c:out value="${panelId}"/>"></div></td>
		   		</tr>	 		
			</table>
			<br>
			<label >Consulta Multiple</label>
			<br>
			<br>
			<form   id="file_upload_form_desglose" method="post"   enctype="multipart/form-data" action="CargaArchivoLotes.action">
 			<input type="hidden" name="idTransferencia" value="${transferencia.idtransferencia}" >
			<table  width="500" border="1">
			<tr>
				<td width="20%">Archivo:</td>
				<td>
				<input name="theFile" id="theFile" size="20" type="file" /><br />	
				</td>
			</tr>
			<tr>
		
			<td align="center" colspan="2">
			<input type="button" onclick="validaCargaDesglosa()"  name="action" value="cargar" />
			</td>
			</tr>
			</table>
			<br />
			</form>
			
	<div id="fileref" style="color:#FF0000">
	</div>
<div id="listaTransferencia" >
	
    </div>
</div>
<iframe id="upload_target_desglosa" name="upload_target_desglosa" style="width:0;height:0;border:1px solid #fff;" src="" ></iframe>
</div>