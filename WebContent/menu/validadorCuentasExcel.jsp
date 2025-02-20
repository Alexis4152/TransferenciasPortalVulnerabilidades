<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"reporteValidadorCuentas.xls\""); %>       
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
	
	<table border="1" width="100%" id="validadorCuentas">
	
	    <thead>
            <tr style="background:#eeeeee;">
				<th width="120px" >REGION </th>
                <th width="120px" >CUENTA </th>
                <th>RESPONSABILIDAD</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="vali" items="${listaValidadorCuentas}">
	            <tr>
					<td>${vali.region}</td>
	                <td>${vali.cuenta}</td>
	                <td>${vali.responsabilidad}</td>              	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>

	    </table>
</body>
</html>
