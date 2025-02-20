<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 
 		
		 new Ext.form.ComboBox({
 			id:'addtipoExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'addtipo',
		    width:80,
		    forceSelection:true,
		    listeners:{
		            'select': function(){
		            	if(Ext.getCmp('addtipoExt').value=='CU'){
		            		Ext.getDom('addSap').value='';
		            		Ext.getDom('addSap').disabled=true;
		            	}else{
		            		Ext.getDom('addSap').disabled=false;
		            	}
		            }
		           }
		});     
	      
	 new Ext.form.ComboBox({
 			id:'addRegionExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'addRegion',
		    width:145,
		    forceSelection:true
		});     
		
		    new  Ext.Button({
	         
	      	   
	      	   <c:if test="${region!=null}">text:'Editar',</c:if>
	      	   <c:if test="${region==null}">text:'Añadir',</c:if>
	      	   width:'60',	      	                
	      	   applyTo:'addPagoDao',
	      	   listeners:{
		            'click': function(){
		            
		            
		            var texto =Ext.getDom('addMonto').value;
		            texto = texto.replace(' ', '');
		            texto= texto.replace(/ /g, '');
		           
		            
		          
		           	if(Ext.getCmp('addRegionExt').value==''){
		             Ext.MessageBox.alert('Estatus','Ingrese una Region' );
		            return;
		            }
		            if(Ext.getDom('addCuenta').value==''){
		            Ext.MessageBox.alert('Estatus','Ingrese una Cuenta' );
		            return;
		            }
		            if(/^\d+\.?\d*$/.test(''+Ext.getDom('addCuenta').value)==false){
		            Ext.MessageBox.alert('Estatus','Ingrese una Cuenta Numérica' );
		           
		            return;
		            }
		            
		            if(Ext.getDom('addMonto').value==''){
		            Ext.MessageBox.alert('Estatus','Ingrese un Monto' );
		           
		            return;
		            }
		            var numeroX=parseFloat(Ext.getDom('addMonto').value,10);
		            //numeroX=Math.abs(numeroX);
		            
		            if(numeroX==0){
		             Ext.MessageBox.alert('Estatus','Ingrese un Monto mayor a cero' );
		           
		            return;
		            }
		           
		          	if(numeroX<0){
		             Ext.MessageBox.alert('Estatus','Ingrese un Monto positivo' );
		           
		            return;
		            }
		          	
		          	<c:if test="${mte==1}">
		            if(Ext.getCmp('addtipoExt').value=='FA'){
		            	Ext.MessageBox.alert('Tipo','No se permite seleccionar el tipo FA porque es un pago MTE' );
		            	return;
		            }
		          	</c:if>
		          	
		            if(Ext.getCmp('addtipoExt').value=='FA' && Ext.getDom('addSap').value.trim()==''){
		            	Ext.MessageBox.alert('Estatus','Ingrese el esporadico SAP' );
		            	return;
		            }
		            
		            mascara(true);
		            var bandera='add';
		            <c:if test="${region!=null}">bandera='edit'</c:if>
		            ajaxDivUpdater('TemplateAction.action','parameter=addPago&add='+bandera+'&importe='+texto+'&'+Ext.Ajax.serializeForm('formaAddPago'),'dsplay_none'); 
		           
		            }
		    	}
	      }); 
	      
	    if(Ext.getCmp('addtipoExt').value=='CU'){   
 		   Ext.getDom('addSap').value=''; 		
		   Ext.getDom('addSap').disabled=true;
		}
	      
  });
  
</script>



<div>
<form id="formaAddPago">
<table width="100%">
	<tr>
		<td width="80" >Region</td>
		<td>
		<select  name="region" id="addRegion" >
		
		 <option value="R01" <c:if test="${region=='R01'}"> selected="selected" </c:if> >R01</option>
		 <option value="R02" <c:if test="${region=='R02'}"> selected="selected" </c:if> >R02</option>
		 <option value="R03" <c:if test="${region=='R03'}"> selected="selected" </c:if> >R03</option>
		 <option value="R04" <c:if test="${region=='R04'}"> selected="selected" </c:if> >R04</option>
		 <option value="R05" <c:if test="${region=='R05'}"> selected="selected" </c:if> >R05</option>
		 <option value="R06" <c:if test="${region=='R06'}"> selected="selected" </c:if> >R06</option>
		 <option value="R07" <c:if test="${region=='R07'}"> selected="selected" </c:if> >R07</option>
		 <option value="R08" <c:if test="${region=='R08'}"> selected="selected" </c:if> >R08</option>
		 <option value="R09" <c:if test="${region=='R09'}"> selected="selected" </c:if> >R09</option>
		 
		</select>
		</td>
	
	</tr>
	<tr>
		<td  width="80">Cuenta</td>
		<td><input name="cuenta" maxlength="20" id="addCuenta" type="text"  value="<c:out value="${cuenta}"/>" ></td>

	</tr>
	<tr>
		<td  width="80">Monto</td>
		<td><input name="importe2" maxlength="20" id="addMonto" type="text" value="<c:out value="${importe}"/>"  ></td>
	
	</tr>
	<tr>
			<td  width="80">Tipo</td>
	
		
		
		<td>
		<div>
		<select  name="tipo" id="addtipo" >
		
		 <option value="CU" <c:if test="${tipo=='CU'}"> selected="selected" </c:if> >CU</option>
		 <option value="FA" <c:if test="${tipo=='FA'}"> selected="selected" </c:if> >FA</option>

		 
		</select>
		</div>
		
		</td>
	
	</tr>
	<tr>
		<td  width="80">Esporadico SAP</td>
		<td><input name="sap" maxlength="30" id="addSap" type="text" value="<c:out value="${sap}"/>"  ></td>
	
	</tr>
	
</table>
<div id="addPagoDao"></div>
<input type="hidden" name="idTransferencia" value="${idTransferencia}" />
<input type="hidden" name="idPago" value="${idPago}" />
</form>
</div>
