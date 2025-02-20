<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
 Ext.onReady(function(){
  		Ext.getCmp('pagosByEstatus').setTitle('Lista Tranferencias');
 	//	Ext.DomHelper.overwrite('detalleTrasferenciaTexto','<c:out value="${resultado}"/>');			
		//Ext.get('detalleTrasferenciaTexto').highlight();
 		
      /*  var grid = new Ext.ux.grid.TableGrid("listaPagosReporte", {
            stripeRows: true // stripe alternate rows
        });*/
       // grid.render();
		var tb = new Ext.Toolbar({
		    items: [ 
		        { 
		            // xtype: 'button', // default for Toolbars, same as 'tbbutton'
		            iconCls: 'excel', 
		            text: ' ',
		            width: 40, 
		            listeners:{
			            'click': function(){
			            
			            	
			            	var redirect = 'Reportes.action?parameter=pagosByEstatus&'+Ext.Ajax.serializeForm('listaPagosDetalleExcel')+'&nombreBanco=${nombreBanco}';
		                //    alert(redirect);
		                    document.location.href=redirect; 
		                    
			            
			            
			               
				            }
			    	}      
		        }
		    ]
		});  
		tb.render('toolbarDetalleConcentrdo');
		
		 new  Ext.Button({
	    	   id:'botonBuscar<c:out value="${panelId}"/>Ext',
	    	   text:'Enviar',
	    	   width:'100',	      	                
	    	   applyTo:'buscar<c:out value="${panelId}"/>',
	    	   <c:if test="${sessionScope.empleado.idPefril!=2}">
				 disabled:true,
				 hidden:true,
			   </c:if>	 
	    	   listeners:{
		            'click': function(){ 
// 		            	addPanel('busquedaReporte','TemplateAction.action','correo='+Ext.getDom('correo').value,'autoload')
		            	ajaxDivUpdater('TemplateAction.action','parameter=busquedaReporte&correo='+Ext.getDom('correo').value,'resultadoReporte');
		       	     }
		    	}
	    });  

  });
  

</script>
<div>
	<div id="toolbarDetalleConcentrdo"></div>
	<div class="instrucciones">
		Instrucciones:<br /> - Ingrese correo electronico de clic en Buscar.<br />
		- Espere unos minutos y le llegara un correo con un archivo adjunto,
		donde estara la informacion de las transferencias solicitadas.
	</div>
	<table>
		<tr>
			<c:if test="${sessionScope.empleado.idPefril==2}">
				<td width="40px">Correo:</td>
				<td width="60px"><input id="correo" size="25" type="text"
					value="" /></td>
			</c:if>
			<td><div id="buscar<c:out value="${panelId}"/>"></div></td>
		</tr>
	</table>
	<div id="resultadoReporte">
		<c:out value="${mensaje}" />
		</b>
	</div>
	<table width="100%" id="the-table-h">

		<thead>
			<tr style="background: #eeeeee;">
				<th>Id Transferencia</th>
				<th width="120px">Cuenta</th>
				<th width="100px">Fecha</th>
				<th>Importe</th>
				<th>Banco</th>
				<th>Cliente</th>
				<th>Alias</th>
				<th width="50px">Ver</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="trans" items="${listaTransferencias}">
				<tr>
					<td>${trans.idtransferencia}</td>
					<td>${trans.cuenta}</td>
					<td>${trans.fecha}</td>
					<td>$ ${trans.importeString}</td>
					<td>${trans.banco}</td>
					<td>${trans.cliente}</td>
					<td>${trans.alias}</td>
					<td><a
						href="javascript:addTabDetalle('TemplateAction.action','parameter=detalleTransferencia&idTransferencia=${trans.idtransferencia}')">Ver</a>
					</td>


				</tr>

			</c:forEach>


		</tbody>

	</table>

	<form id="listaPagosDetalleExcel">
		<input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
		<input name="banco" type="hidden" value="<c:out value="${banco}"/>" />
		<input name="excel" type="hidden" value="ok" />
	</form>

</div>


