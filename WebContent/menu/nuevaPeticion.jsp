<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
Ext.onReady(function(){
	
	
	new Ext.form.ComboBox({
		id:'nombre',
	    typeAhead: true,
	    triggerAction: 'all',
	    transform:'nombre<c:out value="${panelId}"/>',
	    forceSelection:true,
		editable : false
	    });
	
	new  Ext.form.TimeField({
		id:'horarioIn',
		renderTo:'horarioIn<c:out value="${panelId}"/>',
		name:'horarioIn',
	    minValue: '7:00 AM',
	    maxValue: '9:00 PM',
	    increment: 30,
	    format : 'g:i A',
		editable : false
	    });
 
 new  Ext.form.TimeField({
		id:'horarioOut',
		renderTo:'horarioOut<c:out value="${panelId}"/>',
		name:'horarioOut',
	    minValue: '7:00 AM',
	    maxValue: '9:00 PM',
	    increment: 30,
	    format : 'g:i A',
		editable : false
	});
 
 new Ext.form.ComboBox({
		id:'asistencia',
	    typeAhead: true,
	    triggerAction: 'all',
	    transform:'asistencia<c:out value="${panelId}"/>',
		editable : false
	    
	});
	
 new  Ext.form.DateField({
		id:'periodoInExt',
		renderTo:'periodoIn<c:out value="${panelId}"/>',
		name:'periodoIn',
		editable:false,
	});

new  Ext.form.DateField({
		id:'periodoOutExt',
		renderTo:'periodoOut<c:out value="${panelId}"/>',
		name:'periodoOut',
		editable:false,
	});

new Ext.form.ComboBox({
	id:'mensajes',
    typeAhead: true,
    triggerAction: 'all',
    transform:'mensajes<c:out value="${panelId}"/>',
    forceSelection:true,
	editable : false
});
 
 
	  new  Ext.Button({
     	   text:'Guardar',
     	   width:'60',	      	                
     	   applyTo:'guardar',
     	   listeners:{
	            'click': function(){
	
	            	var from = Date.parseDate (Ext.getCmp ('horarioIn'). value, "g:i A");
	    			var to = Date.parseDate (Ext.getCmp ('horarioOut'). value, "g:i A");
	    			if (from > to){
	    				Ext.MessageBox.alert('horario','El primer horario debe ser menor que el segundo horario.' );
	    				 return;
	    			}
	
	              	if((Ext.getCmp('horarioIn').value == null || Ext.getCmp('horarioOut').value == null) || (Ext.getCmp('horarioIn').value == 'undefined' || Ext.getCmp('horarioOut').value == 'undefined')){
		            	
		            	 Ext.MessageBox.alert('horario','Ningun horario puede esta vacio.' );
	 					 return;
		            	}
	              	
	              	if(Ext.getCmp('periodoInExt').value > Ext.getCmp('periodoOutExt').value){
		            	
		            	 Ext.MessageBox.alert('Periodo','El primer periodo no puede ser mayor al segundo periodo.' );
	 					 return;
		            	}
	            	
	                ajaxDivUpdater('TemplateAction.action','parameter=addPeticion&idEmpleado='+Ext.getCmp('nombre').value+'&horarioIn='+Ext.getCmp('horarioIn').value+'&horarioOut='+Ext.getCmp('horarioOut').value+'&asistencia='+Ext.getCmp('asistencia').value+'&periodoIn='+Ext.getCmp('periodoInExt').value+'&periodoOut='+Ext.getCmp('periodoOutExt').value+'&mensajes='+Ext.getCmp('mensajes').value,'addPet'); 
	            }
	    	}
     }); 
	  
	  new  Ext.Button({
   	   text:'Cancelar',
   	   width:'60',	      	                
   	   applyTo:'cancelar',
   	   listeners:{
	            'click': function(){
	            	 Ext.getCmp('newPeticionWindowExt').close();
	  	            }
	    	}
   });
	
});

</script>
<div>
	<div id="toolbar<c:out value="${panelId}"/>"></div>
	<div>
		<div class="instrucciones">
			<b>El administrador de peticiones puede programar las peticiones
				que estará recibiendo cada asesor.</b><br /> Instrucciones:<br /> 
			- Guardar: Registrará los cambios realizados en la tabla de asignación.<br /> 
			- Cancelar: Descartará los cambios realizados a la petición seleccionada. Esta acción solo tendra efecto antes de dar en el boton Guardar.<br />
			- Cerrar: Actualizará la tabla de asignación y cerrará el cuadro de edición o de nueva peticion.<br />
		</div>
		<form id="desglosePetForm">
			<table cellspacing="5px">
				<thead>
					<tr style="background: #eeeeee;">
						<th>Nombre</th>
						<th colspan="2">Horario</th>
						<th>Asistencia</th>
						<th colspan="2">Periodo</th>
						<th>Cantidad de mensajes</th>
						<th colspan="2">Accion</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><select id="nombre<c:out value="${panelId}"/>">
								<c:forEach var="emp" items="${nuevosEmpleados}">
									<option value="${emp.idEmpleado}">${emp.nombre}</option>
								</c:forEach>
						</select></td>
						<td><div id="horarioIn<c:out value="${panelId}"/>"></div></td>
						<td><div id="horarioOut<c:out value="${panelId}"/>"></div></td>
						<td><select id="asistencia<c:out value="${panelId}"/>">
								<option value="SI">SI</option>
								<option value="NO">NO</option>
						</select></td>
						<td><div id="periodoIn<c:out value="${panelId}"/>"></div></td>
						<td><div id="periodoOut<c:out value="${panelId}"/>"></div></td>
						<td><select id="mensajes<c:out value="${panelId}"/>">
								<c:forEach var="men" items="${cantidadMensajes}">
									<option value="${men}">${men}</option>
								</c:forEach>
						</select></td>
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

<div id="addPet">
	
	</div>
</div>