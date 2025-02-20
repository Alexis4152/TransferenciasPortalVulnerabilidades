<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
	
	
  //alert('cargaResult'); 
  window.parent.resultadoDesglosar('<c:out value="${texto}"/>');

</script>

