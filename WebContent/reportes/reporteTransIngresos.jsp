<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script type="text/javascript">
 Ext.onReady(function(){
 
 	Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	var tb = new Ext.Toolbar({
	    items: [ 
	        { 
	            iconCls: 'excel', 
	            text: ' ',
	            width: 40, 
	            listeners:{
		            'click': function(){
						var validaExcel = Ext.get('HdnValExcel').dom.value;
						if(validaExcel == "OK"){
			            	var redirect = 'Reportes.action?parameter=reporteTransIngresos&excel=true';//+Ext.Ajax.serializeForm('listaTransIngresosExcel');
			            	window.open(redirect);	
						}else{
							Ext.MessageBox.alert('Estatus',
							'Verifique que se haya generado el reporte.');
			            }
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
	            	var el = Ext.fly('fi-button-msg');
	            	el.update('');
	            
	            	if(Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value==null){
	            	
	            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
 					 return;
	            	}
	            	
	                ajaxDivUpdater('Reportes.action','parameter=reporteTransIngresos&filtros=ok&fecha='+Ext.getCmp('fechaInicio<c:out value="${panelId}"/>Ext').value+'&fecha2='+Ext.getCmp('fechaFin<c:out value="${panelId}"/>Ext').value,'listaTransIngresos<c:out value="${panelId}"/>'); 
	            }
	    	}
      });	
  });
	function resultado(texto) {
		//mascara(false);
		var el = Ext.fly('fi-button-msg');
		var hidn = Ext.get('HdnValExcel');
		if(texto!='OK'){
			Ext.MessageBox.alert('Estatus',texto);
			hidn.set({'value':''});
			Ext.DomHelper
			.overwrite('listaTransIngresos<c:out value="${panelId}"/>','');		
		}
		else{
			hidn.set({'value':'OK'});
		}
	}
</script>
<div>
	<div id="toolbar<c:out value="${panelId}"/>"></div>
	<div>
		<div class="instrucciones">
			<b>El reporte muestra el número de transferencias desglosadas en
				el periodo definido.</b><br /> Instrucciones:<br /> - Seleccione los
			criterios que requiera y de clic en Buscar (campos marcados con * son
			obligatorios).<br /> - El rango de fechas está limitado a un mes.<br />
			- La fecha fin debe ser menor o igual a la actual.<br />
		</div>
		<br>
		<table cellspacing="5px">
			<tr>
				<td width="100px">Fecha Inicio*:</td>
				<td width="100px"><div
						id="fechaInicio<c:out value="${panelId}"/>"></div></td>
				<td width="100px">
					<div id="buscar<c:out value="${panelId}"/>"></div>
				</td>
			</tr>
			<tr>
				<td>Fecha Fin:</td>
				<td><div id="fechaFin<c:out value="${panelId}"/>"></div></td>
			</tr>
		</table>
		<div align="center" id="fi-button-msg" style="display: none;"></div>
		<input type="hidden" id="HdnValExcel" value=".">
		<div align="center" id="listaTransIngresos<c:out value="${panelId}"/>"></div>
	</div>
</div>