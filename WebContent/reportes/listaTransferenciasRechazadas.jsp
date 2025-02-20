<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		
       /* var grid = new Ext.ux.grid.TableGrid("the-table-Tra-ByBanco", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/
        
          

  });
</script>
<div>

<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
                <th>Usuario</th>
					<th>Nombre</th>
					<th>Fecha transferencia</th>
					<th>Importe</th>
					<th>Alias</th>
					<th>IdTransferencia</th>
					<th>TipoPagos</th>
					<th>Fecha Rechazo</th>
					<th>Motivo</th>
					<th>Persona que rechazo</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a" >
	            <tr>
	                <td>${trans.usuario}</td>
					<td>${trans.nombreUsuario}</td>
					<td>${trans.fecha}</td>
					<td>$ ${trans.importeString}</td>
					<td>${trans.alias}</td>
					<td>${trans.idtransferencia}</td>
					<td>${trans.tipoPagos}</td>
					<td>${trans.fechaHistorial}</td>
					<td>${trans.descripcion}</td>
					<td>${trans.nombreUsuario}</td>
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>
	 <form id="listaTraExcelBanco">
	 <input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
	 <input name="fecha2"  type="hidden" value="<c:out value="${fecha2}"/>"  />
	 <input name="banco" type="hidden" value="<c:out value="${banco}"/>" />
	 <input name="excel" type="hidden" value="<c:out value="${estatus}"/>" />
	</form>
		<br/>
    
    
</div>
