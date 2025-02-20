<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"pagosNoIdent.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>  
<title>Reporte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
		
<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="55px" >No</th>
            	<th>Región</th>
                <th>Lote</th>
                <th>Importe</th>
                <th>Fecha</th>
                <th>ID Trans</th>
                <th>Importe trans</th>
                <th>Fecha trans</th>
                <th>Banco</th>
            </tr>
        </thead>
	
        <tbody>
        	<c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a"  >
      	 		<c:choose>
	     		<c:when test="${trans.importe != 0.0}">
	     		   <tr>
	               <td><c:out value="${a.index+1}"   /></td>
	               <td>${trans.region}</td>
	               <td>${trans.lote}</td>
	               <td>$ ${trans.importeString}</td> 
	                <td>${trans.fecha}</td>
	                 
	                <td>${trans.idtransferencia}</td>
	                <td>$ ${trans.importeTransString}</td> 
	               <td>${trans.fechaHistorial}</td>
	                <td>${trans.banco}</td>
	                </tr>
	        	</c:when>
	        	<c:otherwise>
	        		<tr style="background:#ff0000;">
	        		<td><c:out value="${a.index+1}"   /></td>
	            	<td>${trans.region}</td>
	            	<td>${trans.lote}</td>
	            	<td>$ ${trans.importeTransString}</td> 
	            	<td>${trans.fecha}</td>
	                
	            	<td>------</td>
	            	<td>------</td>
	            	<td>------</td>
	            	<td>------</td>
	            	</tr>   
	        	</c:otherwise>		
				</c:choose>	   			
	       </c:forEach>
        </tbody>
    
   </table>
</body>
</html>
