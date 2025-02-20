<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"reporteBanco.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>  
<title>Reporte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
			<table border="1"  width="220" cellspacing="5px" >
				<tr>
					<td width="100px">Fecha Inicio:</td>
					<td width="100px">${fecha}</td>
					<td width="100px">			
			          Banco: 
			              
			              
			        <c:forEach var="banco" items="${listaBancos}">

		   			<c:if test="${banco.idbanco==bancoInt}">${banco.nombre}</c:if>
		   		    </c:forEach>
			          
					</td>
				</tr>
				<tr>
					<td>Fecha Fin:</td>
					<td>${fecha2}</td>
					<td align="right">  
					Generado por:${sessionScope.empleado.nombre} el ${fechaCreacion}
					</td>
				</tr>
			
							
			</table>	
		
<table border="1" >

        <thead>
            <tr style="background:#eeeeee;">
            	
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Cliente</th>
                <th>Estatus</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}">
	            <tr>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>   
	                <td>${trans.cliente}</td>
	                <td>${trans.estatusDescripcion}</td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>
</body>
</html>
