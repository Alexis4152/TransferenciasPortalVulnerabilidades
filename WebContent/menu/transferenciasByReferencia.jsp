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
                <th>Referencia de Pago</th>
                <th>Importe</th>
                <th>Fecha Pago</th>
                <th>Banco</th>
                <th>Fecha de Solicitud</th>
                <th>Estatus</th>
                <th>Asesor</th>   
				<th width="50px" >Seleccionar</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a" >
	            <tr bgcolor="${trans.color}">
	                <td>${trans.referenciaCliente}</td>
	                <td>$ ${trans.importeString}</td>   
	                <td>${trans.fecha}</td>   
	                <td>${trans.banco}</td>
	                <td>${trans.fechaTransferencia}</td>
	                <td>${trans.estatusDescripcion}</td>
	                <td>${trans.nombreUsuario}</td>     
	                <th><a href="javascript:addTabDetalle('TemplateAction.action','parameter=desgloseTransferencias&idTransferencia=${trans.idtransferencia}')">Seleccionar</a>  </th>       
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>
	 <form id="listaTraExcelEstatus">
	 <input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
	 <input name="fecha2"  type="hidden" value="<c:out value="${fecha2}"/>"  />
	 <input name="estatus" type="hidden" value="<c:out value="${estatus}"/>" />
	</form>
		<br/>
    
    
</div>
