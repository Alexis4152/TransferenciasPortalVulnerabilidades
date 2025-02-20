<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript">
 Ext.onReady(function(){
 
 Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');

 	
 		var tb = new Ext.Toolbar({
		    items: [ 
		        { 
// 		            xtype: 'button', // default for Toolbars, same as 'tbbutton'
		            iconCls: '', 
		            text: ' ',
		            width: 40,
		            listeners:{
			            'click': function(){
			            
			            	
			            	var redirect = 'Reportes.action?parameter=transferenciasDesglosadas&'+Ext.Ajax.serializeForm('listaTraConcentrado'); 
// 		                   alert(redirect);
		                    document.location.href=redirect;
		                    
			            
			            
			               
				            }
			    	}      
		        }
		    ]
		});  
		tb.render('toolbar<c:out value="${panelId}"/>');
		
    
		new Ext.form.ComboBox({
 			id:'mes<c:out value="${panelId}"/>Ext',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'mes<c:out value="${panelId}"/>',
		    width:100,
		    forceSelection:true
		});
		
		new  Ext.Button({
			      	   id:'botonBuscar<c:out value="${panelId}"/>Ext',
			      	   text:'Buscar',
			      	   width:'60',	      	                
			      	   applyTo:'buscar<c:out value="${panelId}"/>',
			      	   listeners:{
				            'click': function(){
				            
				            	
				            	//alert(Ext.getCmp('bancoAplicaExt').value)
				            
				                ajaxDivUpdater('Reportes.action','parameter=transferenciasDesglosadas&busca=ok&mes='+Ext.getCmp('mes<c:out value="${panelId}"/>Ext').value+'&year='+Ext.getDom('year').value+'&correo='+Ext.getDom('correo').value,'concentradoTransferenciasReporte'); 
				            }
				    	}
			      });
		


  });
</script>
<div>
<div id="toolbar<c:out value="${panelId}"/>"></div>


<div class="instrucciones">
	<b>El reporte muestra el detalle de cada transferencia aplicada en MOBILE, para el calculo se usa la Fecha de los Lotes cerrados en MOBILE.</b><br/>
	Instrucciones:<br/>		
	- Seleccione el año y mes de calculo y su correo electronico de clic en Buscar.<br/>
	- Espere unos minutos y le llegara un correo con un archivo adjunto, donde estara la informacion de las transferencias solicitadas.
</div>
<br/>
	<table>
		<tr>
			<td width="40px" >Año:</td>
			<td  width="60px" > <input id="year" size="5" type="text" value="2010" />   </td>
			<td  width="40px" >Mes:</td>
			<td  width="100px" >
				<select id="mes<c:out value="${panelId}"/>" >
					<option value="0" >Enero</option>
					<option value="1" >Febrero</option>
					<option value="2" >Marzo</option>
					<option value="3" >Abril</option>
					<option value="4" >Mayo</option>
					<option value="5" >Junio</option>
					<option value="6" >Julio</option>
					<option value="7" >Agosto</option>
					<option value="8" >Septiembre</option>
					<option value="9" >Octubre</option>
					<option value="10" >Noviembre</option>
					<option value="11" >Diciembre</option>
				</select>
			 </td>
			 <td width="40px" >correo:</td>
			<td  width="60px" > <input id="correo" size="25" type="text" value="" />   </td>
			<td><div id="buscar<c:out value="${panelId}"/>"></div> </td>
			
		</tr>
	
	</table>


<div id="concentradoTransferenciasReporte">
<%-- <b> <c:out value="${mensaje}"/></b> --%>

</div>
</div>