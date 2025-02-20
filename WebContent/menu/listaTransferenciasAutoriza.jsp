<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		
     /*   var grid = new Ext.ux.grid.TableGrid("the-table", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/

 		new  Ext.Button({
	      	   id:'botonAutorizaExt',
	      	   text:'Autorizar',
	      	   width:'60',	      	                
	      	   applyTo:'botonAutoriza',
	      	   listeners:{
		            'click': function(){
		           		
		           		
		           		try{
						if(Ext.Ajax.serializeForm('listaAutorizaForm')==''){
						  Ext.MessageBox.alert('Estatus','No hay transferencias seleccionadas' );
						  return;
						 }
		           		}catch(e){
		           		
		           		 Ext.MessageBox.alert('Estatus','No hay transferencias disponibles' );
	 					 return;
		           		
		           		}
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAutorizaForm'))
		                ajaxDivUpdater('TemplateAction.action','parameter=autorizaTransferenciaExecute&'+Ext.Ajax.serializeForm('listaAutorizaForm'),'listaTransferenciaAutoriza'); 
		            }
		    	}
	      });
	      
	       		new  Ext.Button({
	      	   id:'botonCierraAutorizaExt',
	      	   text:'Cerrar',
	      	   width:'60',	      	                
	      	   applyTo:'botonAutorizaCerrar',
	      	   listeners:{
		            'click': function(){
		           		
		           		try{
						if(Ext.Ajax.serializeForm('listaAutorizaForm')==''){
						  Ext.MessageBox.alert('Estatus','No hay transferencias seleccionadas' );
						  return;
						 }
		           		}catch(e){
		           		
		           		 Ext.MessageBox.alert('Estatus','No hay transferencias disponibles' );
	 					 return;
		           		
		           		}
		           		
		           		if(Ext.getDom('comentaCierre').value==''){
		           			Ext.MessageBox.alert('Estatus','El comentario es obligatorio' );
		            		return;
		            	}
		           		
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAutorizaForm'))
		                ajaxDivUpdater('TemplateAction.action','parameter=cerrarPorFueraSinValidar&'+Ext.Ajax.serializeForm('listaAutorizaForm')+'&comentario='+Ext.getDom('comentaCierre').value,'listaTransferenciaAutoriza'); 
		            }
		    	}
	      });
	      
	      


  });
   function seleccionarTodoAutoriza(check){
   for (i=0;i<document.listaAutorizaFormCheck.elements.length;i++)
      if(document.listaAutorizaFormCheck.elements[i].type == "checkbox"){
      
      document.listaAutorizaFormCheck.elements[i].checked=check
      
      }
         
} 

	function cerrar(idtransferencia){
	   
	   mascara(true);
	   ajaxDivUpdater('TemplateAction.action','parameter=cerrarPorFuera&cerrar='+idtransferencia,'dsplay_none');
	   
	   
	  } 
</script>
<div> 
<a href="javascript:seleccionarTodoAutoriza(1)"> <span class="x-tree-node" >Marcar</span> </a> |
<a href="javascript:seleccionarTodoAutoriza(0)"> <span class="x-tree-node" >Desmarcar</span></a> 
<br/>

<form id="listaAutorizaForm" name="listaAutorizaFormCheck" >
<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="50px" >Autorizar</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Cliente</th>
            </tr>
        </thead>

        <tbody>
	        <c:forEach var="trans" items="${listaTrasferencia}">
	            <tr>
	                <td><input name="autoriza" value="${trans.idtransferencia}" type="checkbox"/>  </td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>   
	                <td>${trans.cliente}</td>
	               
	                
	            </tr>
			   			
			   			
	       </c:forEach>
    
        </tbody>
    </table>
    	      <c:if test="${listaTrasferencia=='[]'}">
	      
	      No hay transferencias con el criterio seleccionado
	      	    
	      
	      </c:if>  
</form>
    <br/>
   
    <table>
    <tr>
    <td> <div align="center" id="botonAutoriza"></div></td>
    <td> <div align="center" id="botonAutorizaCerrar"></div></td>
    </tr>
    <tr>
    	<td> Comentario cierre:</td>
    	<td><input id="comentaCierre" type="text" size="60" maxlength="100"></td>
    </tr>
    </table>
    
</div>
