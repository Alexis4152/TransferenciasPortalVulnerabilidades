<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"reporteTransAplicadas.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>  
<title>Reporte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
			<table border="1"  width="220" cellspacing="5px" >

				<tr>
					<td colspan="5" >  
					Generado por:${sessionScope.empleado.nombre} el ${fechaCreacion}
					</td>
				</tr>
			
							
			</table>	
	
	<table border="1" width="100%" id="listaPagosReporte">
	
	    <thead>
            <tr style="background:#eeeeee;">
				<th width="120px" >Id Trans </th>
                <th width="120px" >Cuenta </th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Banco</th>
                <th>Cliente</th>
               	<th>Alias</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTransferencias}">
	            <tr>
					<td>${trans.idtransferencia}</td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td> 
	                <td>${trans.banco}</td>
	                <td>${trans.cliente}</td>
	               	<td>${trans.alias}</td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>

	    </table>
</body>
</html>
