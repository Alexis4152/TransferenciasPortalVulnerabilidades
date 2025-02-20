<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		
        var grid = new Ext.ux.grid.TableGrid("revierteLista", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();

 		new  Ext.Button({
	      	   id:'botonRevierteListaExt',
	      	   text:'Programar',
	      	   width:'60',	      	                
	      	   applyTo:'botonRevierteLista',
	      	   listeners:{
		            'click': function(){
		           		
		           		
		           		try{
						if(Ext.Ajax.serializeForm('listaRevierteListaForm')==''){
						  Ext.MessageBox.alert('Estatus','No hay transferencias seleccionadas' );
						  return;
						 }
		           		}catch(e){
		           		
		           		 Ext.MessageBox.alert('Estatus','No hay transferencias disponibles' );
	 					 return;
		           		
		           		}
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaRevierteListaForm'))
		                ajaxDivUpdater('TemplateAction.action','parameter=revierteListaTransferenciaExecute&'+Ext.Ajax.serializeForm('listaRevierteListaForm'),'dsplay_none'); 
		            }
		    	}
	      });


  });
  function seleccionarTodoRevierte(check){
   for (i=0;i<document.listaRevierteListaForm.elements.length;i++)
      if(document.listaRevierteListaForm.elements[i].type == "checkbox")
         document.listaRevierteListaForm.elements[i].checked=check
} 
</script>
<div id="revierteListaTransferencias" > 
<a href="javascript:seleccionarTodoRevierte(1)"><span class="x-tree-node" >Marcar</span></a> |
<a href="javascript:seleccionarTodoRevierte(0)"><span class="x-tree-node" >Desmarcar</span></a> 
<form id="listaRevierteListaForm" name="listaRevierteListaForm" >
<table  width="100%" id="revierteLista">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="55px" >Revertir</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Cliente</th>

            </tr>
        </thead>

        <tbody>
	        <c:forEach var="trans" items="${listaTrasferencia}">
	            <tr>
	                <td><input name="revierte" value="${trans.idtransferencia}" type="checkbox"/>  </td>
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
    <div align="center" id="botonRevierteLista"></div>
</div>
