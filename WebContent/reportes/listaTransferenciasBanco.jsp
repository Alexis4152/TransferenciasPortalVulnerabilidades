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
            	<th  width="20px" >No</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Cliente</th>
                <th>Estatus</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a" >
	            <tr>
	            	<td><c:out value="${a.index+1}"></c:out></td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>   
	                <td>${trans.cliente}</td>
	                <td>${trans.estatusDescripcion}</td>
              
	                
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
