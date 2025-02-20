<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
 Ext.onReady(function(){
 
 		
        var grid = new Ext.ux.grid.TableGrid("the-table-Tomar", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();
        
        
    new  Ext.Button({
	      	   text:'Tomar',
	      	   width:'60',	      	                
	      	   applyTo:'botonActivaMultipleTomar',
	      	   listeners:{
		            'click': function(){
		           	
		            	//alert(Ext.Ajax.serializeForm('listaTomarForm'))
		            	try{
		            	if(Ext.Ajax.serializeForm('listaTomarForm')==''){
 						 Ext.MessageBox.alert('Estatus','No hay ninguna transacción para tomar' );
 						 return;
		            	}
		            	
		            	}catch(e){
		            	 Ext.MessageBox.alert('Estatus','No hay ninguna transacción para tomar' );
		            	 return;
		            	}
		            	if(Ext.getDom('alias').value==''){
 						 Ext.MessageBox.alert('Estatus','Ingresa una referencia' );
 						 return;
		            	}
		            	
		            	mascara(true);
		                ajaxDivUpdater('TemplateAction.action','parameter=tomarTransferenciaExecute&lista=lista&'+Ext.Ajax.serializeForm('listaTomarForm')+'&alias='+Ext.getDom('alias').value,'listaTransferenciaTomar'); 
		            }
		    	}
	      });     

  });
</script>
<div >     

<form id="listaTomarForm">
<table  width="100%" id="the-table-Tomar">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="55px" >Tomar</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
            
                <th>Cliente</th>
                
            </tr>
        </thead>

        <tbody>
        	 <c:forEach var="trans" items="${listaTrasferencia}">
	            <tr>
	                <td><input name="tomar" value="${trans.idtransferencia}" type="checkbox"/>  </td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>   
	             
	                <td>${trans.cliente}</td>
	                
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
    
      
            
        </tbody>
    </table>
</form>
		<br/>
	Referencia:<input maxlength="50" type="text" id="alias"  /><br/><br/>
     <div align="center"   id="botonActivaMultipleTomar"></div>
     <br/>
     <div id="listaTransferenciaTomar" ></div>
</div>


