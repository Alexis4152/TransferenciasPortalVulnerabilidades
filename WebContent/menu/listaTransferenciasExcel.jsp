<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"listaTransferencias.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>  
<title>Lista Tranferencias</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div id="listaTransferenciaPendiente" >
<table>
	  
		 <tr >
		    
			 <td colspan="9" align="center" style="font-weight:bold;">Portal de transferencias</td>
		 
		 
		 </tr>
		 <tr >
		    
			 <td colspan="9" align="center" style="font-weight:bold;" >Fecha de generación:<c:out value="${fechaCreacion}" /></td>
		 
		 
		 </tr>
		 		 <tr >
		    
			 <td colspan="9" align="center" style="font-weight:bold;" >Generado por: ${sessionScope.empleado.nombre}</td>
		 
		 
		 </tr>  
	
	 </table>

<table  border="1" background="#0000FF" >

        <thead>
            <tr style="background:#0000FF;">
            	<th>Id Trans</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Pagos</th>
                <th>Banco</th>
                <c:if test="${titulo=='Pendientes' || titulo=='Aplicadas' }"> 
                <th width="120px">${tituloFecha}</th>
                <th>Usuario</th>                 
                </c:if>
                <th>Alias</th>
                <th>Cliente</th>

            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencia}">
	            <tr>
	            	<td>${trans.idtransferencia}</td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td> 
	                <td>${trans.tipoPagos}</td> 
	                <td>${trans.banco}</td>  
	                <c:if test="${titulo=='Pendientes' || titulo=='Aplicadas'}">
	                <td>${trans.fechaHistorial}</td>    
	                <td>${trans.usuario}</td>
	                </c:if>  
	                <td>${trans.alias}</td>
	                <td>${trans.cliente}</td>

	             
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>


</div>




</body>
</html>
