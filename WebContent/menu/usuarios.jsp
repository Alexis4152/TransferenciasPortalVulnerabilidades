<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
  Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 
 		
 
        
      new  Ext.Button({
	      	   text:'Agregar',
	      	   width:'60',	      	                
	      	   applyTo:'agregarUsiario',
	      	   listeners:{
		            'click': function(){
		            
		            
		              if(Ext.getDom('empleado').value==null || Ext.getDom('empleado').value=='No Empleado' ){
		            	
		            	 Ext.MessageBox.alert('Estatus','Ingresa un número de empleado válido' );
	 					 return;
		            	}
		              if(Ext.getCmp('perfilExt').value=='-1'  ){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona un perfil' );
	 					 return;
		            	}
		                Ext.MessageBox.show({
					           title:'Confirmación',
					           msg: '¿Esta seguro de agregar al usuario?',
					           buttons: Ext.MessageBox.YESNO,
					           fn: function(btn){
					           			
					           			if(btn=='yes'){
					           			
					           			mascara(true);
					           			
					           			ajaxDivUpdater('TemplateAction.action','parameter=agregarUsuario&perfil='+Ext.getCmp('perfilExt').value+'&empleado='+Ext.getDom('empleado').value,'dsplay_none'); 
		     
					           			}
					           	
					           },					         
					           icon: Ext.MessageBox.QUESTION
					       });
		               		
		            
	            	}
		    	}
	      });    
        
       new  Ext.Button({
	      	   text:'Baja Usuario',
	      	   width:'75',	      	                
	      	   applyTo:'bajaUsuario',
	      	   listeners:{
		            'click': function(){
		           		
		            	 mascara(true);
		            	// alert(Ext.Ajax.serializeForm('listaUsuariosForm'))
		                 ajaxDivUpdater('TemplateAction.action','parameter=bajaUsuario&'+Ext.Ajax.serializeForm('listaUsuariosForm'),'dsplay_none'); 
		            }
		    	}
	      });     
	      
        new  Ext.Button({
	      	   text:'Restablecer Usuario',
	      	   width:'120',	      	                
	      	   applyTo:'restablecerUsiario',
	      	   listeners:{
		            'click': function(){
		           		
		            	 mascara(true);
		            	// alert(Ext.Ajax.serializeForm('listaUsuariosForm'))
		                 ajaxDivUpdater('TemplateAction.action','parameter=restablecerUsuario&'+Ext.Ajax.serializeForm('listaUsuariosForm'),'dsplay_none'); 
		            }
		    	}
	      });      
	      
	  		// el combobos hace referencia al id del combo en html que se quiera transformar
		new Ext.form.ComboBox({
		    id:'perfilExt',
		   transform:'perfil',
		    triggerAction: 'all'
		});
		
		new Ext.form.ComboBox({
		    id:'perfilExt2',
		   transform:'perfil2',
		    triggerAction: 'all'
		});
		
		
	   new  Ext.Button({
	      	   text:'Cambia Perfil',
	      	   width:'95',	      	                
	      	   applyTo:'cambioPerfil',
	      	   listeners:{
		            'click': function(){
		           		
		           		 if(Ext.getCmp('perfilExt2').value=='-1'  ){
		            	
		            	 Ext.MessageBox.alert('Estatus','Selecciona un perfil' );
	 					 return;
		            	}
		            	 mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaUsuariosForm'))
		                 ajaxDivUpdater('TemplateAction.action','parameter=actualizaUsuario&'+Ext.Ajax.serializeForm('listaUsuariosForm')+'&perfil='+Ext.getCmp('perfilExt2').value,'dsplay_none'); 
		            }
		    	}
	      }); 
 ajaxDivUpdater('TemplateAction.action','parameter=listaUsuarios','listaTransferenciaUsuarios'); 

  });
</script>
<div style="padding:15px;margin:15px" >
	
<div>



<table id="uno" >
	<tr>
		<td style="font-weight:bold;" width="120px" >Alta de Usuario:</td>
		<td width="150px" ><input id="empleado" type="text" value="No Empleado" /> </td>
		<td>
			<select  id="perfil" >
				<option  value="-1"  >Selecciona perfil</option>
				<c:if test="${sessionScope.empleado.idPefril==1}">
				<option  value="1"  >Finanzas Administrador  </option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2}">
				<option  value="2"  >Finanzas </option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==3}">
				<option  value="3"  >Corporativo</option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==1}">
				<option  value="5"  >Cobranza Corporativa  </option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==5}">
				<option  value="5"  >Cobranza Corporativa  </option>
				</c:if>
			</select>
		 </td>
		<td>	
			<div id="agregarUsiario"></div>
			
		</td>
	</tr>
</table>
</div>
<br/>
<br/>
<form id="listaUsuariosForm">
 <div id="listaTransferenciaUsuarios" ></div>
</form>

		<br/>
<div id="respuestaUsuario"></div>		
		<br/>    
     <table id="dos" >
	     <tr>
	     <td width="100px" > <div align="center" id="bajaUsuario"></div> </td>
	     <td width="150px" > <div align="center" id="restablecerUsiario"></div> </td>
	     <td width="150px" > <div align="center" id="actualizarUsuario"></div>  </td>
	     </tr>
     </table>
     <br/>
     
     <table id="uno" >
	<tr>
		<td style="font-weight:bold;" width="120px" >Cambio de Perfil:</td>
		<td width="185px">
			<select  id="perfil2" >
				<option  value="-1"  >Selecciona perfil</option>
				<c:if test="${sessionScope.empleado.idPefril==1}">
				<option  value="1"  >Finanzas Administrador  </option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2}">
				<option  value="2"  >Finanzas </option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==1 ||sessionScope.empleado.idPefril==3}">
				<option  value="3"  >Corporativo</option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==1}">
				<option  value="5"  >Cobranza Corporativa  </option>
				</c:if>
				<c:if test="${sessionScope.empleado.idPefril==5}">
				<option  value="5"  >Cobranza Corporativa  </option>
				</c:if>
			</select>
		 </td>
		<td>	
			<div id="cambioPerfil"></div>
			
		</td>
	</tr>
</table>
    
</div>
