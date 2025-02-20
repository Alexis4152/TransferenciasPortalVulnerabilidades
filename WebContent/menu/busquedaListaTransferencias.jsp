<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>

<div class="instrucciones">
	Instrucciones:<br/>		
	- Ingrese correo electronico de clic en Buscar.<br/>
	- Espere unos minutos y le llegara un correo con un archivo adjunto, donde estara la informacion de las transferencias solicitadas.
</div>
<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="55px" >No</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Pagos</th>
                <th>Estatus</th>
                <th>Cliente</th>
                <th width="50px" >Ver</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencia}"  varStatus="a"  >
	            <tr>
	               <td><c:out value="${a.index+1}"></c:out></td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td> 
	                <td>${trans.tipoPagos}</td> 
	                <td>${trans.estatusDescripcion}</td>  
	                <td>${trans.cliente}</td>
	                
	                <td><a href="javascript:addTabDetalle('TemplateAction.action','parameter=detalleTransferenciaCompensacion&idTransferencia=${trans.idtransferencia}&tipoTrans=${trans.tipoPagos}')">Ver</a>  </td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>
	<br/>
</div>
