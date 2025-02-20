<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		Ext.DomHelper.overwrite('detalleTrasferenciaTexto','<c:out value="${resultado}"/>');			
		Ext.get('detalleTrasferenciaTexto').highlight();
 		
     /*   var grid = new Ext.ux.grid.TableGrid("the-table-detalle", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/
        
      var tb = new Ext.Toolbar({
		    items: [ 
		        { 
		            // xtype: 'button', // default for Toolbars, same as 'tbbutton'
		            iconCls: 'excel', 
		            text: ' ',
		            width: 40,
		            listeners:{
		            'click': function(){
		            
		            	
		            	var redirect = 'TemplateAction.action?parameter=detalleTransferencia&idTransferencia=<c:out value="${transferencia.idtransferencia}"/>&excel=excel'; 
	                 //   alert(redirect);
	                    document.location.href=redirect;
	                    
		            
		            
		               
			            }
			    	}   
		        }
		    ]
		});  
		tb.render('toolbarDetalle');
        
//

 		new  Ext.Button({
	      	   id:'mostrarBotonHistorialExt',
	      	   text:'Ver Historial',
	      	   width:'60',	      	                
	      	   applyTo:'mostrarBotonHistorial', 
	      	   listeners:{
		            'click': function(){
		           		
		                ajaxDivUpdater('TemplateAction.action','parameter=mostrarHistorial&idTransferencia=${transferencia.idtransferencia}','mostrarHistorial'); 
		            }
		    	}
	      });
	      

  <c:if test="${estatus == 3}">
 		new  Ext.Button({
	      	   id:'botonAplicaExt',
	      	   text:'Programar',
	      	   width:'60',	      	                
	      	   applyTo:'botonAplica',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4 }">
							 disabled:true,
							 hidden:true,
			   </c:if>	 
	      	   listeners:{
		            'click': function(){
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAplicaForm'))
		                ajaxDivUpdater('TemplateAction.action','parameter=detalleTransferenciaExecute&aplica=${transferencia.idtransferencia}_${tipoTrans}&pendiente=${pendiente}','dsplay_none'); 
		            }
		    	}
	      });
	      
	     
	</c:if>
	
  <c:if test="${estatus == 1}">
 		new  Ext.Button({
	      	   id:'botonAutorizarExt',
	      	   text:'Autorizar',
	      	   width:'60',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
			   </c:if>	      	                
	      	   applyTo:'botonAutorizaDetalle',
	      	   listeners:{
		            'click': function(){
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAplicaForm'))
		                ajaxDivUpdater('TemplateAction.action','parameter=autorizaTransferenciaExecute&autoriza=${transferencia.idtransferencia}&detalle=detalle','dsplay_none'); 
		            }
		    	}
	      });
	      
	     
	</c:if>
	
	<c:choose> 
  		<c:when test="${estatus==3}" > 
    		new  Ext.Button({
	      	   id:'botonCerrarExt',
	      	   text:'Cerrar',
	      	   width:'100',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
			   disabled:true,
			   hidden:true,
			   </c:if>	      	                
	      	   applyTo:'botonCerrarDetalle',
	      	   listeners:{
		            'click': function(){
		            
		            if(Ext.getDom('comentaCierre').value==''){
		            		
		            		 Ext.MessageBox.alert('Estatus','El comentario es obligatorio' );
		            		 return;
		            }
		            
		            
		            Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Desea cerrar la transferencia?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			
					           			mascara(true);
		            	
		               				    ajaxDivUpdater('TemplateAction.action','parameter=cerrarPorFuera&autoriza=${transferencia.idtransferencia}&detalle=detalle&pendiente=${pendiente}&comentario='+Ext.getDom('comentaCierre').value,'dsplay_none'); 
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		           		
		            }
		    	}
	      	});
  		</c:when> 
  		<c:otherwise> 
  		 <c:if test="${estatus == 1 || sessionScope.empleado.idPefril == 2}" > 
    		new  Ext.Button({
	      	   id:'botonCerrarExt',
	      	   text:'Cerrar',
	      	   width:'100',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
			   disabled:true,
			   hidden:true,
			   </c:if>	      	                
	      	   applyTo:'botonCerrarDetalle',
	      	   listeners:{
		            'click': function(){
		            
		            if(Ext.getDom('comentaCierre').value==''){
		            		
		            		 Ext.MessageBox.alert('Estatus','El comentario es obligatorio' );
		            		 return;
		            		}
		            
		            
		            Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Desea cerrar la transferencia?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			
					           			mascara(true);
		            	
		               				    ajaxDivUpdater('TemplateAction.action','parameter=cerrarPorFueraSinValidar&autoriza=${transferencia.idtransferencia}&detalle=detalle&pendiente=${pendiente}&comentario='+Ext.getDom('comentaCierre').value,'dsplay_none'); 
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		           		
		            }
		    	}
	      	});
  		  </c:if>
  		 </c:otherwise>  	 
	</c:choose> 
	
	<c:if test="${estatus == 7 && iAccess == 1 && tipoTrans == 'FA' }">  	 
  		new  Ext.Button({
	      	   id:'botonComentaExt',
	      	   text:'Comentar',
	      	   width:'60',	      	                
	      	   applyTo:'botonComentaDetalle',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
			   </c:if>	 
	      	   listeners:{
		            'click': function(){
		            
		            		if(Ext.getDom('comentaDetalle').value==''){
		            		
		            		 Ext.MessageBox.alert('Estatus','El texto es obligatorio' );
		            		 return;
		            		}
		            
		                   Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Desea comentar la transferencia?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			 mascara(true)
					           			 ajaxDivUpdater('TemplateAction.action','parameter=comentaTransferencia&idTransferencia=${transferencia.idtransferencia}&pendiente=${pendiente}&comentario='+Ext.getDom('comentaDetalle').value,'dsplay_none'); 
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		            }
		    	}
	      });
	    
	</c:if>   
	//
  <c:if test="${estatus == 3}">  
	 new  Ext.Button({
	      	   id:'botonRechazaExt',
	      	   text:'Rechazar',
	      	   width:'60',	      	                
	      	   applyTo:'botonRechaza',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
			   </c:if>	 
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getDom('rechazaDetalle').value==''){
		            		
		            		 Ext.MessageBox.alert('Estatus','El motivo es obligatorio' );
		            		 return;
		            		}   
		            
		                   Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Desea rechazar la transferencia?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			
					           			 ajaxDivUpdater('TemplateAction.action','parameter=rechazarExecute&rechaza=${transferencia.idtransferencia}&pendiente=${pendiente}&comentario='+Ext.getDom('rechazaDetalle').value,'dsplay_none'); 
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		            
		           		
		            }
		    	}
	      });
  new  Ext.Button({
	      	   id:'botonRegresaExt',
	      	   text:'Regresar',
	      	   width:'60',	      	                
	      	   applyTo:'botonRegresa',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
			   </c:if>	 
	      	   listeners:{
		            'click': function(){
		            
		            		if(Ext.getDom('rechazaDetalle').value==''){
		            		
		            		 Ext.MessageBox.alert('Estatus','El motivo es obligatorio' );
		            		 return;
		            		}
		            
		                   Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Desea regresar la transferencia?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			 mascara(true)
					           			 ajaxDivUpdater('TemplateAction.action','parameter=regresaTransferencia&idTransferencia=${transferencia.idtransferencia}&pendiente=${pendiente}&comentario='+Ext.getDom('rechazaDetalle').value,'dsplay_none'); 
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		            
		           		//mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAplicaForm'))
		                //ajaxDivUpdater('TemplateAction.action','parameter=detalleTransferenciaExecute&idTransferencia=1','dsplay_none'); 
		            }
		    	}
	      });
	      
	     </c:if>   
	  
	   <c:if test="${estatus==4 }">  
	      new  Ext.Button({
	      	   id:'botonRegresaExt',
	      	   text:'Regresar',
	      	   width:'60',	      	                
	      	   applyTo:'botonRegresa',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
			   </c:if>	 
	      	   listeners:{
		            'click': function(){
		            
		            		if(Ext.getDom('rechazaDetalle').value==''){
		            		
		            		 Ext.MessageBox.alert('Estatus','El motivo es obligatorio' );
		            		 return;
		            		}
		            
		                   Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Desea regresar la transferencia?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			 mascara(true)
					           			 ajaxDivUpdater('TemplateAction.action','parameter=regresaTransferenciaSinValidar&idTransferencia=${transferencia.idtransferencia}&comentario='+Ext.getDom('rechazaDetalle').value+'&batch=true','dsplay_none'); 
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		            
		           		//mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAplicaForm'))
		                //ajaxDivUpdater('TemplateAction.action','parameter=detalleTransferenciaExecute&idTransferencia=1','dsplay_none'); 
		            }
		    	}
	      });
	      
   </c:if>   
   
   // CAMBIO DE ESTATUS 
   
    <c:if test="${estatus == 7 && tipoTrans== 'FA'}">  
	 new  Ext.Button({
	      	   id:'botonEstatusExt',
	      	   text:'Cambiar estatus',
	      	   width:'60',	      	                
	      	   applyTo:'botonEstatus',
	      	   <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
			   </c:if>	 
	      	   listeners:{
		            'click': function(){
		            
		            	if(Ext.getDom('rechazaDetalle').value==''){
		            		
		            		 Ext.MessageBox.alert('Estatus','El motivo es obligatorio' );
		            		 return;
		            		}   
		            
		                   Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Desea realizar el cambio de estatus a Autorizada?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			
					           			 ajaxDivUpdater('TemplateAction.action','parameter=cambiarEstatusExecute&cambio=${transferencia.idtransferencia}&pendiente=${pendiente}&comentario='+Ext.getDom('rechazaDetalle').value,'dsplay_none'); 
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		            
		           		
		            }
		    	}
	      });
</c:if>

// TERMINA CAMBIO DE ESTATUS 
   
   
  });
</script>
<div id="detalleCompleto">
	<div id="toolbarDetalle"></div>
	<div class="instrucciones">
		<c:if
			test="${estatus == 1 && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2)}">
			<br />- <b>Autorizar: </b> Autoriza la transferencia para que Corporativo o Empresarial la pueda tomar y desglosar.
	</c:if>
		<c:if
			test="${estatus == 3 && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2)}">
			<br />- <b>Programar: </b> Manda la transferencia a lista de espera para ser aplicada en MOBILE, despues que se haya programado aparecera en la seccion de En Proceso.
	</c:if>
		<c:if
			test="${(estatus == 3 || estatus == 1) && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2)}">
			<br />- <b>Cerrar:</b> Manda la transferencia a un estatus terminal de Cerrado, esta transferencia ya no se podra trabajar en el Portal.
	</c:if>
		<c:if
			test="${(estatus == 3) && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2)}">
			<br />- <b>Rechazar:</b> Manda la transferencia a un estatus terminal de Rechazada, se requiere especificar un motivo,  esta transferencia ya no se podra trabajar en el Portal.
	</c:if>
	
	<c:if
			test="${(estatus == 7) && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2)}">
			<br />- <b>Cambiar estatus:</b> Regresa la transferencia a un estatus de Autorizada, se requiere especificar un motivo.
	</c:if>
	
		<c:if
			test="${(estatus == 4 || estatus == 3) && (sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2)}">
			<br />- <b>Regresar:</b> Regresa la transferencia al ejecutivo que la desgloso para su revision, se requiere especificar un motivo.
	</c:if>
	</div>
	<div style="padding: 15px; margin: 15px">
		<table cellpadding="1px" cellspacing="1px">
			<tr>
				<td width="60px" style="font-weight: bold;">Banco:</td>
				<td width="100px"><c:out value="${transferencia.banco}" /></td>

				<td width="70px" style="font-weight: bold;">Cuenta:</td>
				<td><c:out value="${transferencia.cuenta}" /></td>
			</tr>
			<tr>
				<td width="60px" style="font-weight: bold;">Fecha:</td>
				<td><c:out value="${transferencia.fecha}" /></td>
				<td width="60px" style="font-weight: bold;">Importe:</td>
				<td>$ <c:out value="${transferencia.importeString}" /></td>
			</tr>
			<tr>
				<td width="60px" style="font-weight: bold;">ID:</td>
				<td><c:out value="${transferencia.idtransferencia}" /></td>

				<td width="70px" style="font-weight: bold;">Alias</td>
				<td>${transferencia.alias}</td>
			</tr>
			<tr>
				<td width="60px" style="font-weight: bold;">Estatus:</td>
				<td><c:out value="${transferencia.estatusDescripcion}" /></td>
			</tr>
		</table>



	</div>
	<div style="padding: 15px; margin: 15px">
		Desglose: <br />
		<table width="100%" id="the-table-h">

			<thead>
				<tr style="background: #eeeeee;">
					<th width="55px">No</th>
					<th>Region</th>
					<th>Cuenta</th>
					<th>Importe</th>
					<th>Tipo</th>
					<th>Estatus</th>
					<th>Descripcion</th>
					<th>Lote</th>
					<th>Fecha lote</th>

				</tr>
			</thead>

			<tbody>
				<c:forEach var="pagos" items="${listaPagos}" varStatus="a">
					<tr>
						<td><c:out value="${a.index+1}"></c:out></td>
						<td>${pagos.region}</td>
						<td>${pagos.cuenta}</td>
						<td>$ ${pagos.importeString}</td>
						<td>${pagos.tipo}</td>
						<td>${pagos.estatusPago}</td>
						<td>${pagos.descripcion}</td>
						<td>${pagos.lote}</td>
						<td>${pagos.fechaLoteString}</td>


					</tr>

				</c:forEach>





			</tbody>
		</table>

		<div id="detalleMasivoBotones">
			<br />
			<table>

				<tr>
					<td>
						<div>

							<div align="center" id="botonAplica"></div>

							<div align="center" id="botonAutorizaDetalle"></div>

							<div align="center" id="botonSoltarDetalle"></div>

						</div>
					</td>
					<td>
						<div align="center" id="botonCerrarDetalle"></div>
					</td>

				</tr>
			</table>





			<div id="detalleTrasferenciaTexto"></div>




			<div style="padding: 15px; margin: 15px">



				<c:if test="${estatus == 3 || estatus == 4 || (estatus == 7 && tipoTrans== 'FA')}">
					<table>
						<tr>

							<td><c:if
									test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=5 && sessionScope.empleado.idPefril!=4}">  Motivo: </c:if>
							</td>
							<td width="70%"><c:if
									test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=5 && sessionScope.empleado.idPefril!=4}">
									<input id="rechazaDetalle" type="text" size="60" />
								</c:if></td>
								<td>
									<div id="botonRechaza"></div>
								</td>
								<td>
									<div id="botonEstatus"></div>
								</td>
								<td>
									<div id="botonRegresa"></div>
								</td>
								<td>
									<div id="botonReintento"></div>
								</td>
						</tr>
					</table>
				</c:if>


				<c:if test="${estatus == 7 && iAccess == 1 && tipoTrans == 'FA' }">

					<table>
						<tr>

							<td><c:if
									test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=5 && sessionScope.empleado.idPefril!=4}">  Texto: </c:if>
							</td>
							<td width="70%"><c:if
									test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=5 && sessionScope.empleado.idPefril!=4}">
									<input id="comentaDetalle" type="text" size="60"
										maxlength="100" />
								</c:if></td>

							<td>
								<div id="botonComentaDetalle"></div>
							</td>
						</tr>
					</table>
				</c:if>

				<c:if
					test="${estatus == 3 || estatus == 1 ||  sessionScope.empleado.idPefril == 2}">
					<table>
						<tr>

							<td><c:if
									test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=5 && sessionScope.empleado.idPefril!=4}">  Comentario cierre: </c:if>
							</td>
							<td width="70%"><c:if
									test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=5 && sessionScope.empleado.idPefril!=4}">
									<input id="comentaCierre" type="text" size="60" maxlength="100" />
								</c:if></td>
						</tr>
					</table>
				</c:if>

			</div>
		</div>

		<c:if test="${estatus == 7}">

			<div style="padding: 15px; margin: 15px">
				Subtotales por Región:
				<table width="300px">

					<thead>
						<tr style="background: #eeeeee;">

							<th>Region</th>
							<th>Lote</th>
							<th>Importe</th>


						</tr>
					</thead>

					<tbody>
						<c:forEach var="pagos" items="${transferenciaSuma}" varStatus="a">
							<tr>

								<td>${pagos.region}</td>
								<td><c:if test="${pagos.lote == 0}">FA </c:if> <c:if
										test="${pagos.lote != 0}">${pagos.lote}</c:if></td>
								<td>$ ${pagos.importeString}</td>


							</tr>

						</c:forEach>





					</tbody>
				</table>


			</div>
		</c:if>
		<div id="mostrarHistorial" style="padding: 15px; margin: 15px">

			<div id="mostrarBotonHistorial"></div>




		</div>


	</div>