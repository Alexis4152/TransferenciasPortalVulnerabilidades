<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript">
 Ext.onReady(function(){
 
 	Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
		
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
		            	
		            
		                ajaxDivUpdater('Reportes.action','parameter=reportePeticiones&filtros=ok&fecha='+Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value+'&fecha2='+Ext.getCmp('fechaFin<c:out value="${panelId}"/>Ext').value,'listaPeticiones<c:out value="${panelId}"/>'); 
		            }
		    	}
	      });
		
     

  });
</script>
<div>
<div id="toolbar<c:out value="${panelId}"/>"/>
<div>
<div class="instrucciones">
	<b>El reporte muestra el número de transferencias aplicadas por cada asesor y el monto de cada uno.</b><br/>
	Instrucciones:<br/>		
	- Seleccione los criterios que requiera y de clic en Buscar (campos marcados con * son obligatorios).<br/>
</div>
	<table cellspacing="5px" >
		<tr>
			<td width="100px">Fecha Inicio*:</td>
			<td width="100px"><div id="fechaInicio<c:out value="${panelId}"/>"></div></td>
			<td width="100px">
				<div id="buscar<c:out value="${panelId}"/>"></div>
			</td>
		</tr>
		<tr>
			<td>Fecha Fin:</td>
			<td><div id="fechaFin<c:out value="${panelId}"/>"></div></td>
		</tr>	
	</table>
	<div id="listaPeticiones<c:out value="${panelId}"/>"/>
</div>
</div>