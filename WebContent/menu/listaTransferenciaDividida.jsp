<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 		new  Ext.Button({
	      	   id:'botonDividirTransacciones',
	      	   text:'Dividir Transferencia',
	      	   width:'60',	      	                
	      	   applyTo:'botonDividirTrans',
	      	   listeners:{
		            'click': function(){
		           		
		           		
		           		try{
						if(Ext.Ajax.serializeForm('listaDividirForm')==''){
						  Ext.MessageBox.alert('Estatus','No hay transferencias seleccionadas' );
						  return;
						 }
		           		}catch(e){
		           	
		           		 Ext.MessageBox.alert('Estatus','No hay transferencias disponibles' );
	 					 return;
		           		
		           		}
		           				     
		                ajaxDivUpdater('TemplateAction.action','parameter=dividirTransferenciaExecute&'+Ext.Ajax.serializeForm('listaDividirForm'),'dividir'); 
	            }
		    	}
	      });
	      
	          });
 
</script>


    <form id="listaDividirForm" name="listaDividirForm" >
       <table  width="100%" id="the-table-h">
        <thead>
            <tr style="background:#eeeeee;">
            	<th width="50px" >Dividir</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Cliente</th>
            </tr>
        </thead>
        <tbody>
	        <c:forEach var="trans" items="${listaTrasferencia}">
	            <tr>
	                <td><input name="id_Transferencia" value="${trans.idtransferencia}" type="radio" onclick="javascript:document.getElementById('desglosar').style.display = 'block';"/>  </td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>   
	                <td>${trans.cliente}</td>
	                <input type="hidden" name="importe${trans.idtransferencia}" value="${trans.importeString}" />
	            </tr>
			   			
			   			
	       </c:forEach>
    
        </tbody>
    </table>
   
    	      <c:if test="${listaTrasferencia=='[]'}">
	      
	      No hay transferencias con el criterio seleccionado
	      	    
	      
	      </c:if>  

    <br/>
   
    <table id="desglosar" style="display:none;">

    <tr>
    	<td>Numero de desgloses</td>
    	<td><select id="cantidadDividir"  name="cantidadDividir" value="2"  >
    	<option>2</option>
    	<option>3</option>
    	<option>4</option>
    	<option>5</option>
    	<option>6</option>
    	<option>7</option>
    	<option>8</option>
    	<option>9</option>
    	<option>10</option>
    	</select></td>
    	<td> <div align="center" id="botonDividirTrans"></div></td>
    </tr>
    </table>
     </form>
    <br><br>
    	<div id="dividir">

	</div>
 