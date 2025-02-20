<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		 Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 
        var grid = new Ext.ux.grid.TableGrid("the-table-DesUsuario", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();
        
          

  });
</script>
<div id="desglosarDivLista" >
<div class="instrucciones">	
	<b>Aqui se muestran las tranferencias que ha tomado y estan pendientes de desglosar.</b><br/>
	Instrucciones:<br/>
	- Ubique la transferencia que quiere operar y de clic en <b>ver</b> para entrar el detalle donde podra: Desglosar, Editar o Soltar la transferencia.<br/>
</div>
<table  width="100%" id="the-table-DesUsuario">

        <thead>
            <tr style="background:#eeeeee;">
            	
                <th>Cuenta</th>
                <th width="70" >Referencia</th>
                <th width="70" >Fecha</th>
                <th width="100" >Importe</th>
                <th>Pertenece</th>
                <th>Cliente</th>
                <th width="50" >Ver</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}">
	            <tr>
	                <td>${trans.cuenta}</td>
	                <td>${trans.alias}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>   
	                <td>${trans.usuario}</td>
	                <td>${trans.cliente}</td>
	                <td><a href="javascript:addTabDetalle('TemplateAction.action','parameter=desgloseTransferencias&idTransferencia=${trans.idtransferencia}')">Ver</a>  </td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>

		<br/>
     <div align="center" id="botonTranMultiple"></div>
     <div id="listaTransferenciaTra" ></div>
</div>
