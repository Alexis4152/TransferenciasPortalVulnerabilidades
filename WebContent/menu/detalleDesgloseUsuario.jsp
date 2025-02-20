<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
 Ext.onReady(function(){ 

  Ext.getCmp('detalle').setTitle('Desglosar Transferencia');
  var tb = new Ext.Toolbar({
		    items: [ 
		        { 
		            // xtype: 'button', // default for Toolbars, same as 'tbbutton'
		            iconCls: 'excel', 
		            text: ' ',
		            width: 40,
		            listeners:{
		            'click': function(){
		            
		                  
		            	var redirect = 'TemplateAction.action?parameter=desgloseTransferencias&idTransferencia=<c:out value="${transferencia.idtransferencia}"/>&excel=excel';
	                    document.location.href=redirect;
	                    
		            
		            
		               
			            }
			    	}   
		        }
		    ]
		});  
		tb.render('toolbarDetalleDesglosa');
	 


   ajaxDivUpdater('TemplateAction.action','parameter=desgloseTransferenciasListaDetalle&idTransferencia=${transferencia.idtransferencia}&idReferencia=${idReferencia}','listaPagosDesglose'); 
  });
  

 function resultadoUsuario(texto){
 
 var el = Ext.fly('fi-button-msg-desglose');
                el.update(texto);
                if(!el.isVisible()){
                    el.slideIn('t', {
                        duration: .2,
                        easing: 'easeIn',
                        callback: function(){
                            el.highlight();
                        }
                    });
                }else{
                    el.highlight();
                }
  
 }
 function validaCargaDesglosa(){
 
 	var file;
 	var fileSplit;
 
	 if(Ext.getDom('theFile').value==''){
	 
		 Ext.MessageBox.alert('Estatus','Selecciona un archivo' );
	 	 return;
	 }else{
	 

	 
	 }
 
    mascara(true);
 	document.getElementById('file_upload_form_desglose').submit();
  	 mascara(false);
 }
 
 function resultadoDesglosar(texto){
 mascara(false);
 Ext.MessageBox.alert('Estatus',texto );
 ajaxDivUpdater('TemplateAction.action','parameter=desgloseTransferenciasListaDetalle&idTransferencia=${transferencia.idtransferencia}&idReferencia=${idReferencia}','listaPagosDesglose'); 
 
 }
 <c:if test="${transferencia.estatus == 2}">
 document.getElementById('file_upload_form_desglose').target = 'upload_target_desglosa';
 </c:if>
</script>

<div id="desgloseTransferenciasDetalle1">
<div id="toolbarDetalleDesglosa"></div>
<div class="instrucciones">	
	Instrucciones:
	<c:if test="${transferencia.estatus == 2 && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5)}">
	<br/>- <b>Browse/Cargar: </b> Selecciona y carga una archivo con el desglose de las cuentas (el formato lo puedes descargar del menu Formato Desglose).
	</c:if>
	<c:if test="${transferencia.estatus == 2 && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5)}">
	<br/>- <b>Agregar Pago: </b> Agrega un pago individualmente ingresando la informacion de Region, Cuenta, Monto y Tipo.
	</c:if>
	<c:if test="${transferencia.estatus == 2 && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5)}">
	<br/>- <b>Guardar:</b> Guarda la transferencia y la transfiere a Finanzas.
	</c:if>
	<c:if test="${transferencia.estatus == 2 && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5)}">
	<br/>- <b>Liberar Transferencia:</b> Libera la transferencia para que otro ejecutivo la pueda tomar, los pagos desglosados seran eliminados.
	</c:if>
</div>
<div style="padding:15px;margin:15px">
	<table cellpadding="1px" cellspacing="1px" >
		<tr>
			<td width="60px" style="font-weight:bold;" >Banco:</td>
			<td width="100px" ><c:out value="${transferencia.banco}" /></td>

			<td width="70px" style="font-weight:bold;"  >Cuenta:</td>
			<td><c:out value="${transferencia.cuenta}" /></td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >Fecha:</td>
			<td><c:out value="${transferencia.fecha}" /></td>
			<td width="60px" style="font-weight:bold;"  >Importe:</td>
			<td>$ <c:out value="${transferencia.importeString}" /></td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >ID:</td>
			<td><c:out value="${transferencia.idtransferencia}" /></td>

			<td width="75px" style="font-weight:bold;" >Ref:</td>
			<td><c:out value="${transferencia.alias}" /></td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >Estatus:</td>
			<td><div id="estatusDesglose" > <c:out value="${transferencia.estatusDescripcion}" /></div></td>
		</tr>
	</table>
	
	<br/>
<br/>
 <c:if test="${transferencia.estatus == 2}">
 <form   id="file_upload_form_desglose" method="post"   enctype="multipart/form-data" action="CargaArchivoDesglose.action">
 <input type="hidden" name="idTransferencia" value="${transferencia.idtransferencia}" >
<table  width="500" border="0">
	<tr>
		<td width="20%">Archivo:</td>
		<td>
		<input name="theFile" id="theFile" size="20" type="file" /><br />	
		</td>
	</tr>
	<tr>
		
		<td align="center" colspan="2">
		<input type="button" onclick="validaCargaDesglosa()"  name="action" value="cargar" />
		</td>
	</tr>
</table>

	
	<br />
	
</form>
</c:if>
Detalle de cuentas:
<div id="listaPagosDesglose"></div>
<div   align="center" id="fi-button-msg-desglose" style="display:none;"></div>
	

</div>

<div style="padding:15px;margin:15px">
	Historial:
	 <table>
		  <c:forEach var="h" items="${listaHistorial}">
			 <tr>
			 
				 <td  <c:if test="${h.estatus==4 || h.estatus==8}">style="color: red;"</c:if><c:if test="${h.estatus==7}">style="color: gray;" </c:if><c:if test="${h.estatus==2 && h.empleado=='SISTEMA'}">style="color: gray;"</c:if> >
				 	${h.fecha}-${h.empleado}-${h.statusString} <c:if test="${h.comentario!=null}">  ${h.comentario}</c:if> 
				 </td>
		 
			 
			 </tr>
		 </c:forEach> 
	 </table>
</div>
<iframe id="upload_target_desglosa" name="upload_target_desglosa" style="width:0;height:0;border:1px solid #fff;" src="" ></iframe>
</div>