<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="ext/general.js"></script>
<script type="text/javascript">
 Ext.onReady(function(){
 
 	Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
	 
 	new  Ext.Button({
   	   text:'Nuevo',
   	   width:'60',	      	                
   	   applyTo:'nuevo',
   	   listeners:{
 	            'click': function(){
 	            	newAlerta();
 	            }
 	    	}
   }); 
	
  });
 
 function newAlerta(){
	  
	 var wn = new Ext.Window({
	  				id:'newAlertaWindowExt',
	  				title:'Nueva Alerta',                
	                layout:'fit',
	                width:800,
	            //    height:100, 
	                modal:true,
	                closable: false,
	                plain: true,
	                bbar: [
	                    {
	                        text: 'Cerrar',
	                        handler: function () { 
	                        	addPanel('getAlertas','TemplateAction.action',null,true); 
	                        	wn.close();
	                        	}
	                    }
	  					],
	                    autoShow: true,
	                 autoLoad:{
			                   url:'TemplateAction.action',
			                   	//el párametro "parameter" hace referencia a un metodo de Dispatch action
								params:'parameter=nuevaAlerta'
	                 }
	                }).show();
	  
	  }
 
 function editarAlerta(idAlerta,color,tiempo,idEstatus, descEstatus){
	  
	var we = new Ext.Window({
	  				id:'editAlertaWindowExt',
	  				title:'Editar Alerta',                
	                layout:'fit',
	                width:900,
	                closable: false,
	            //    height:100, 
	                modal:true,
	                
	                plain: true,
	                bbar: [
	                    {
	                        text: 'Cerrar',
	                        handler: function () { 
	                        	addPanel('getAlertas','TemplateAction.action',null,true); 
	                        	we.close();
	                        	}
	                    }
	  					],
	                    autoShow: true,
	                 autoLoad:{
			                   url:'TemplateAction.action',
			                   	//el párametro "parameter" hace referencia a un metodo de Dispatch action
								params:'parameter=editarAlerta&idAlerta='+idAlerta+'&color='+color+'&tiempo='+tiempo+'&idEstatus='+idEstatus+'&descEstatus='+descEstatus
	                 }
	                }).show();
	  
	  }
 
</script>
<div>
	<div id="tool<c:out value="${panelId}"/>"></div>
	<div>
		<div class="instrucciones">
			<b>Alertas.</b><br /> Instrucciones:<br /> - Color: Indicará el color
			con el cual se mostrará la petición en la bandeja de entrada del
			jefe.<br /> - Tiempo: Transcurrido. Indicará el tiempo que deberá
			pasar para que se active la alerta.<br /> - Estatus: Indicará el
			estatus de la transferencia.<br />
		</div>
		<table cellspacing="5px" width="100%" id="the-table-h">
			<thead>
				<tr style="background: #eeeeee;">
					<th>Color</th>
					<th>Tiempo Transcurrido</th>
					<th>Estatus</th>
					<th>Editar</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ale" items="${listaAlertas}" varStatus="a">
					<tr>
						<td>${ale.color}</td>
						<td>${ale.tiempo}</td>
						<td>${ale.descEstatus}</td>
						<td> <a href="javascript:editarAlerta('${ale.idAlerta}','${ale.color}','${ale.tiempo}','${ale.idEstatus}','${ale.descEstatus}')">Editar</a></td>		
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table>
			<tr>
				<td><div id="nuevo"></div></td>
			</tr>
		</table>
		<br />
	</div>
	<div id="deleteAlert">
	
	</div>
</div>