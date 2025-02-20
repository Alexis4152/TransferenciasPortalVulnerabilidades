<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		
       /* var grid = new Ext.ux.grid.TableGrid("the-table-Tra-Esatus", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/
        
          

  });
</script>
<div>

<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="20px">No</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Banco</th>
                <th>Cliente</th>
                <th>Alias</th>
                <th width="50px" >Ver</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a" >
	            <tr>
	           	    <td ><c:out value="${a.index+1}"></c:out></td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>  
	                <td>${trans.banco}</td>   
	                <td>${trans.cliente}</td>
	                <td>${trans.alias}</td>
	                <td><a href="javascript:addTabDetalle('TemplateAction.action','parameter=detalleTransferencia&idTransferencia=${trans.idtransferencia}&tipoTrans=${trans.tipoPagos}')">Ver</a>  </td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>
	 <form id="listaTraExcelEstatus">
	 <input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
	 <input name="fecha2"  type="hidden" value="<c:out value="${fecha2}"/>"  />
	 <input name="estatus" type="hidden" value="<c:out value="${estatus}"/>" />
	 <input name="excel" type="hidden" value="<c:out value="${estatus}"/>" />
	  <input name="bandera" type="hidden" value="<c:out value="${bandera}"/>" />
	</form>
		<br/>
    
    
</div>
