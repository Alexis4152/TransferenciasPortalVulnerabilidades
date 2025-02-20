<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript">
 Ext.onReady(function(){
 
 	Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
//  		var tb = new Ext.Toolbar({
// 		    items: [ 
// 		        { 
// 		            // xtype: 'button', // default for Toolbars, same as 'tbbutton'
// 		            iconCls: 'excel', 
// 		            text: ' ',
// 		            width: 40,
// 		            listeners:{
// 		            'click': function(){
		            
		            	
// 		            	var redirect = 'Reportes.action?parameter=estatusTransferenciaLista&'+Ext.Ajax.serializeForm('listaTraExcelEstatus'); 
// 	                 //   alert(redirect);
// 	                    document.location.href=redirect;
	                    
		            
		            
		               
// 			            }
// 			    	}   
// 		        }
// 		    ]
// 		});  
// 		tb.render('toolbar<c:out value="${panelId}"/>');
		
		new Ext.form.ComboBox({
 			id:'estatus<c:out value="${panelId}"/>',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'estatus<c:out value="${panelId}"/>',
		    forceSelection:true
		});
 		

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
		            	var bandera='';
		            	try{
		            	
		            	bandera='&bandera='+Ext.getDom('banderaId').checked
		            	}catch(e){
		            	
		            	bandera='';
		            	}
		            	
		            	
		            	//alert(Ext.getCmp('bancoAplicaExt').value)
		                ajaxDivUpdater('TemplateAction.action','parameter=estatusTransferenciaLista&fecha='+Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value+'&fecha2='+Ext.getCmp('fechaFin<c:out value="${panelId}"/>Ext').value+'&estatus='+Ext.getCmp('estatus<c:out value="${panelId}"/>').value+bandera,'listaTransferenciaEstatus'); 

		            }
		    	}
	      });
		
  });
</script>
<div>
	<div id="toolbar<c:out value="${panelId}"/>"></div>
<div>
<div class="instrucciones">
	<b>El reporte muestra las transferencias con la Fecha de Transferencia y Estatus seleccionado.</b><br/>
	Instrucciones:<br/>		
	- Seleccione los criterios que requiera y de clic en Buscar (campos marcados con * son obligatorios).<br/>
	- La Fecha de busqueda es la Fecha de la Transferencia.<br/>
	- Para ver el desglose de una transferencia de clic en Seleccionar.<br/>
	- Para exportar el reporte a EXCEL de clic sobre el icono ubicado en la parte superior izquierda de esta area.<br/>
</div>
			<table  cellspacing="5px" >
				<tr>
					<td width="100px">Fecha Inicio*:</td>
					<td width="100px"><div id="fechaInicio<c:out value="${panelId}"/>"></div></td>
					<td width="100px">
						<select   name="estatus<c:out value="${panelId}"/>" id="estatus<c:out value="${panelId}"/>" >
				   			<c:forEach var="estatus" items="${listaEstatus}" >
				   				 <c:if test="${estatus.descripcion ne 'Cargada'}">
							        <option value="${estatus.idEstatus}" >${estatus.descripcion}</option>
							     </c:if>
				   			</c:forEach>				   						   		   
						</select>
					</td>
				</tr>
				<tr>
					<td>Fecha Fin:</td>
					<td><div id="fechaFin<c:out value="${panelId}"/>"></div></td>
					<td align="right">  
					<div id="buscar<c:out value="${panelId}"/>"></div>
					</td>
				</tr>
							
			</table>
<div id="listaTransferenciaEstatus" >
	
    </div>
</div>
</div>