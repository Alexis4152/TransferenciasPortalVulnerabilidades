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
		            
		            	var redirect = 'Reportes.action?filtros=ok&parameter=transferenciasByUser&'+Ext.Ajax.serializeForm('listaTraExcelUsuario'); 
	                    window.open(redirect);
	                    
		            
		            
		               
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
 		new  Ext.form.DateField({
			id:'fechaFin<c:out value="${panelId}"/>Ext',
 			renderTo:'fechaFin<c:out value="${panelId}"/>',
 			name:'fecha',
 			editable:true	
 		});
 		
 		new  Ext.Button({
	      	   id:'botonBuscar<c:out value="${panelId}"/>Ext',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'buscar<c:out value="${panelId}"/>',
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value==null){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
	 					 return;
		            	}
		            	
		            	//alert(Ext.getCmp('bancoAplicaExt').value)
		            
		                ajaxDivUpdater('Reportes.action','parameter=transferenciasByUser&filtros=ok&fecha='+Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value+'&fecha2='+Ext.getCmp('fechaFin<c:out value="${panelId}"/>Ext').value,'concentradoTransferencia<c:out value="${panelId}"/>'); 
		            }
		    	}
	      });
		
     

  });
</script>
<div>
<div id="toolbar<c:out value="${panelId}"/>"></div>
<div>
<div class="instrucciones">
	<b>El reporte muestra las transferencias desglosadas por usuario.</b><br/>
	Instrucciones:<br/>		
	- Seleccione los criterios que requiera y de clic en Buscar (campos marcados con * son obligatorios).<br/>
	- La Fecha de busqueda es la Fecha de desglose de la transferencia.<br/>
	- Para exportar el reporte a EXCEL de clic sobre el icono ubicado en la parte superior izquierda de esta area.<br/>
</div>
			<table  cellspacing="5px" >
				<tr>
					<td width="100px">Fecha Inicio:</td>
					<td width="100px"><div id="fechaInicio<c:out value="${panelId}"/>"></div></td>
					<td width="100px">
				</tr>
				<tr>
					<td>Fecha Fin:</td>
					<td><div id="fechaFin<c:out value="${panelId}"/>"></div></td>
					<td align="right">  
					<div id="buscar<c:out value="${panelId}"/>"></div>
					</td>
				</tr>
			
							
			</table>
<div id="concentradoTransferencia<c:out value="${panelId}"/>" >
	
    </div>
</div>
</div>