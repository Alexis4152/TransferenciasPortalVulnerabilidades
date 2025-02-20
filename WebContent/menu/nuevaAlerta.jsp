<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
Ext.onReady(function(){
	
	
	new Ext.form.ComboBox({
		id:'color',
	    typeAhead: true,
	    triggerAction: 'all',
	    transform:'color<c:out value="${panelId}"/>',
	    forceSelection:true
	    });
	
 
 new Ext.form.ComboBox({
		id:'tiempo',
	    typeAhead: true,
	    triggerAction: 'all',
	    transform:'tiempo<c:out value="${panelId}"/>'
	    
	});
	
new Ext.form.ComboBox({
	id:'estatus',
    typeAhead: true,
    triggerAction: 'all',
    transform:'estatus<c:out value="${panelId}"/>',
    forceSelection:true
});
 
 
	  new  Ext.Button({
     	   text:'Guardar',
     	   width:'60',	      	                
     	   applyTo:'guardar',
     	   listeners:{
	            'click': function(){

	                ajaxDivUpdater('TemplateAction.action','parameter=addAlerta&color='+Ext.getCmp('color').value+'&tiempo='+Ext.getCmp('tiempo').value+'&estatus='+Ext.getCmp('estatus').value,'addAlert'); 
	            }
	    	}
     }); 
	  
	  new  Ext.Button({
   	   text:'Cancelar',
   	   width:'60',	      	                
   	   applyTo:'cancelar',
   	   listeners:{
	            'click': function(){
	            	 Ext.getCmp('newAlertaWindowExt').close();
	  	            }
	    	}
   });
	
});

</script>
<div>
	<div id="toolbar<c:out value="${panelId}"/>"></div>
	<div>
		<div class="instrucciones">
			<b>Se podrá configurar alarmas en caso de que una petición permanezca cierto tiempo en el mismo estatus</b>
			<br /> Instrucciones:<br /> 
			- Guardar: Registrará los cambios realizados en la tabla de asignación.<br />
			- Cancelar: Descartará los cambios realizados a la nueva alerta. Esta acción solo tendra efecto antes de dar en el boton Guardar.<br />
			- Cerrar: Actualizará la tabla de asignación y cerrará el cuadro de edición o de nueva alerta.<br />
		</div>
		<form id="desglosePetForm">
			<table cellspacing="5px">
				<thead>
					<tr style="background: #eeeeee;">
						<th>Color</th>
						<th>Tiempo</th>
						<th>Estatus</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><select id="color<c:out value="${panelId}"/>">
								<c:forEach var="col" items="${colores}">
									<option value="${col}">${col}</option>
								</c:forEach>
						</select></td>
						<td><select id="tiempo<c:out value="${panelId}"/>">
								<c:forEach var="tiem" items="${tiempos}">
									<option value="${tiem}">${tiem} Min</option>
								</c:forEach>
						</select></td>
						<td width="100px">
						<select   name="estatus<c:out value="${panelId}"/>" id="estatus<c:out value="${panelId}"/>" >
				   			<c:forEach var="estatus" items="${listaEstatus}" >
				   			<option value="${estatus.idEstatus}" >${estatus.descripcion}</option>				   			
				   			</c:forEach>				   						   		   
						</select>
						</td>
						<td>
							<div align="right" id="guardar"></div>
						</td>
						<td>
							<div align="right" id="cancelar"></div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

<div id="addAlert">
	
	</div>
</div>