<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){ 


  });
</script>

<div>
	Historial:
	 <table>
	  <c:forEach var="h" items="${listaHistorial}">
		 <tr>
		    
			 <td  <c:if test="${h.estatus==4 || h.estatus==8}">style="color: red;"</c:if><c:if test="${h.estatus==7}">style="color: gray;"</c:if><c:if test="${h.estatus==2 && h.empleado=='SISTEMA'}">style="color: gray;"</c:if> >
			 	${h.fecha}-${h.empleado}-${h.statusString} <c:if test="${h.comentario!=null}">  ${h.comentario}</c:if> 
			 </td>
		 
		 </tr>
	 </c:forEach> 
	 </table>

</div>