<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
Ext.onReady(function(){
	 
 	Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
 	new  Ext.Button({
  	   text:'Nuevo',
  	   width:'60',	      	                
  	   applyTo:'nuevoPet',
  	   listeners:{
	            'click': function(){
	            	newPeticion();
	            }
	    	}
  }); 
 	 
 	
});
function editarPeticion(idPeticion,idEmpleado,nombre,horarioIn,horarioOut,asistencia,periodoIn,periodoOut,mensajes){
	  
	var wep =  new Ext.Window({
	  				id:'editPeticionWindowExt',
	  				title:'Editar peticion',                
	                layout:'fit',
	                width: 1400,
	                closable: false,
	            //    height:100, 
	                modal:true,                
	                plain: true,
	                bbar: [
	                    {
	                        text: 'Cerrar',
	                        handler: function () { 
	                        	addPanel('getPeticiones','TemplateAction.action',null,true); 
	                        	wep.close();
	                        	}
	                    }
	  					],
	                    autoShow: true,
	                 autoLoad:{
			                   url:'TemplateAction.action',
			                   	//el párametro "parameter" hace referencia a un metodo de Dispatch action
								params:'parameter=editarPeticion&idPeticion='+idPeticion+'&idEmpleado='+idEmpleado+'&nombre='+nombre+'&horarioIn='+horarioIn+'&horarioOut='+horarioOut+'&asistencia='+asistencia+'&periodoIn='+periodoIn+'&periodoOut='+periodoOut+'&mensajes='+mensajes
	                 }
	                }).show();
	  
	  }
	  
function newPeticion(){
	  
	var w =  new Ext.Window({
	  				id:'newPeticionWindowExt',
	  				title:'Nueva Peticion',                
	                layout:'fit',
	                width:1360,
	                closable: false,
	            //    height:100, 
	                modal:true,
	                plain: true,
	                bbar: [
	                    {
	                        text: 'Cerrar',
	                        handler: function () { 
	                        	w.close();
	                        	addPanel('getPeticiones','TemplateAction.action',null,true); 
	                        	
	                        	}
	                    }
	  					],
	                    autoShow: true,
	                 autoLoad:{
			                   url:'TemplateAction.action',
			                   	//el párametro "parameter" hace referencia a un metodo de Dispatch action
								params:'parameter=nuevaPeticion'
	                 }
	                }).show();
	  
	  }

</script>
<div>
	<div id="toolbar<c:out value="${panelId}"/>"></div>
<div>
<div class="instrucciones">
	<b>El administrador de peticiones puede programar las peticiones que estará recibiendo cada asesor.</b><br/>
	Instrucciones:<br/>		
	- Nuevo: Permitirá agregar un registro para dar de alta a un asesor.<br/>
</div>
<!-- 	<form id="desglosePagosForm"> -->
	<table  cellspacing="5px" width="100%" id="the-table-h" >
			<thead>
				<tr style="background: #eeeeee;">
					<th>Nombre</th>
					<th colspan="2">Horario</th>
					<th>Asistencia</th>
					<th colspan="2">Periodo</th>
					<th>Cantidad de mensajes</th>
					<th>Editar</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach var="pet" items="${listaPeticiones}">
	            <tr>
	            	<td>${pet.nombre}</td>
					<td>${pet.horarioIn}</td>
					<td>${pet.horarioOut}</td>
					<td>${pet.asistencia}</td>
					<td>${pet.periodoIn}</td>
					<td>${pet.periodoOut}</td>
					<td>${pet.cantidadMensajes}</td>
					<td> <a href="javascript:editarPeticion('${pet.idPeticion}','${pet.idEmpleado}','${pet.nombre}','${pet.horarioIn}','${pet.horarioOut}','${pet.asistencia}','${pet.periodoIn}','${pet.periodoOut}','${pet.cantidadMensajes}')">Editar</a></td>
	            </tr>		   			
			 	       </c:forEach>  			
        </tbody>	
			</table>
<!-- </form>	  -->
</div>  
	<table>	
	<tr>
	<td><div id="nuevoPet"></div></td>
	</tr>	
	</table> 
	 
</div>