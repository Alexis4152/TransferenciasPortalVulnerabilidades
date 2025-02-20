<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">

 Ext.onReady(function(){ 
 	
		var marca = new Ext.form.ComboBox({
 			id:'addtipoExt',
		    typeAhead: true,
		    triggerAction: 'all',
		    transform:'addtipo',
		    width:80,
		    forceSelection:true
		});     
	      
	 
		
 
		
		    new  Ext.Button({
		    
	      	   text:'Añadir',
	      	   width:'60',	      	                
	      	   applyTo:'addPagoDao',
	      	   listeners:{
		            'click': function(){		          
		           
		           var coment = Ext.getDom('addComentario').value.trim();
		           //if(Ext.getDom('addComentario').value==''){
		            if(coment == ''){
		            Ext.MessageBox.alert('Estatus','Ingrese un Comentario' );
		            return;
		            }
		            
		            mascara(true);
		            var bandera='add';
		           //	alert(comboMarca.getValue());
		           
		            ajaxDivUpdater('Reportes.action','parameter=addComentario&comentario='+Ext.getDom('addComentario').value+'&tipoM='+marca.getValue()+'&idTransferencia=${idTransferencia}'+'&filtro=ok&importe=${importe}&marca=${marca}&banco=${banco}&fecha=${fecha}'+'&'+Ext.Ajax.serializeForm('formaAddPago'),'dsplay_none');
		           
		           
		            }
		    	}
	      }); 
	      
  });
</script>



<div>
<form id="formaAddPago">
<table width="100%">
	
	<tr>
		<td  width="80">Comentario</td>
		<td><input name="comentario" maxlength="100" id="addComentario" type="text"  value="<c:out value="${comentario}"/>" ></td>

	</tr>
	
	<tr>
			<td  width="80">Tipo de Marca</td>
	
		
		
		<td>
		<div>
		<select  name="tipo" id="addtipo"/>" >
		
		 <option value="M2K" selected >M2K</option>
		 <option value="SAP" >SAP</option>

		 
		</select>
		</div>
		
		</td>
	
	</tr>
	
</table>
<div id="addPagoDao" name="forma"></div>
<input type="hidden" name="idTransferencia" value="${idTransferencia}" />
</form>
</div>
