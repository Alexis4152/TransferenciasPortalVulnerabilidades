<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"reporteConcentrado.xls\""); %>       
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
            	<th>Dias</th>
            	<th>Bancomer</th>
            	<th>HSBC</th>
            	<th>BANAMEX</th>
            	<th>BANORTE</th>
            	<th>SANTANDER</th>
            	<th>SCOTIABANK</th>
            	<th>INBURSA</th>
            	<th>TOTAL</th>
   
            </tr>
        </thead>

        <tbody>
        	 <c:forEach var="trans" items="${reporteList}">
	            <tr>
	                <td>${trans.fecha.fecha} </td>
	                <td>${trans.BANCO1.transaccion} /${trans.BANCO1.monto} </td>
	                <td>${trans.BANCO2.transaccion} /${trans.BANCO2.monto}</td>
	                <td>${trans.BANCO3.transaccion} / ${trans.BANCO3.monto}</td>
	                <td>${trans.BANCO4.transaccion}/ ${trans.BANCO4.monto} </td>
	                <td>${trans.BANCO5.transaccion}/ ${trans.BANCO5.monto} </td>
	                <td>${trans.BANCO6.transaccion} / ${trans.BANCO6.monto}</td>
	                <td>${trans.BANCO7.transaccion} / ${trans.BANCO7.monto}</td>
	                <td>${trans.total.transaccion} /${trans.total.monto}</td>
	                
	            </tr>		   			
			   			
	       </c:forEach>
        </tbody>
    </table>
    


</body>
</html>
