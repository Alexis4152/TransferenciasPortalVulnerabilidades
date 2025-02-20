<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"reporteDesglosado.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>  
<title>Reporte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<table  >
<tr>
	                <td colspan="9" align="center">Generado el ${fechaCreacion} por ${sessionScope.empleado.nombre}</td>
</tr>
 </table>
	<table  >

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="100px">USUARIO</th>
            	<th width="100px">FECHA DESGLOSE</th>
            	<th width="50px">REGION</th>
            	<th width="150px">CUENTA/FACT</th>
            	<th width="150px">IMPORTE</th>
            	<th width="150px">TOTAL TRANS</th>
            	<th width="100px">EMPRESA</th>
            	
            </tr>
        </thead>

        <tbody>
        	 <c:forEach var="trans" items="${reporteList}">
	            <tr>
	                <td>${trans.usuario}</td>
	                <td>${trans.fecha} </td>
	                <td>${trans.region} </td>
	                <td>${trans.cuenta} </td>
	                <td>${trans.importe} </td>
	                <td>${trans.total} </td>
	                <td>${trans.empresa} </td>
	                
	            </tr>		   			
			   			
	       </c:forEach>
        </tbody>
            </table>
    


</body>
</html>
