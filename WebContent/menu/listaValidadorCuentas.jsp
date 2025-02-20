<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
<table  width="100%" id="validadorCuentasReporte">
        <thead>
            <tr style="background:#eeeeee;">
            	<th>REGION</th>
            	<th>CUENTA</th>
            	<th>RESPONSABILIDAD</th>	  
            </tr>
        </thead>

        <tbody>
        	 <c:forEach var="validador" items="${listaValidadorCuentas}">
	            <tr>
	                <td>${validador.region}</td>
	                <td>${validador.cuenta}</td>
	                <td>${validador.responsabilidad}</td>	                
	            </tr>		   			
			   			
	       </c:forEach>
        </tbody>
    </table>
</div>
