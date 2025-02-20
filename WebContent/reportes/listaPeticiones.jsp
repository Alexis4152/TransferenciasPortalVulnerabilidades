<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"reporteEstatus.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>  
		<title>Reporte</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<style>
			td {
				text-align: right;
			}
		</style>
	</head>
	<body>
		<br/>
		<br/>
		<div style="width: 100%" align="center">
			<table style="width: 70%; border: 1px solid #0681d2;">
				<thead style="background: #0681d2; color: white;">
					<tr>
						<th style="text-align: center;">ASESOR</th>
						<th style="text-align: center;">TRANSFERENCIAS</th>
						<th style="text-align: center;">MONTO</th>
					<tr>
				</thead>
				<tbody>
					<c:set var="con" value="0"/>
					<c:set var="total" value="0"/>
					<c:forEach var="peticion" items="${listaPeticiones}" varStatus="i" >
						<c:set var="con" value="${con+1}"/>
						<c:set var="total" value="${total+peticion.cantidad}"/>
						<c:set var="montoTotal" value="${montoTotal+peticion.monto}"/>
						<c:if test="${con%2==0}">
							<tr style="background: #c2e5fe">
				                <td style="text-align: left !important;">${peticion.acesor}</td>
				                <td>${peticion.cantidad}</td>
				                <fmt:setLocale value = "es_MX"/>
				                <td>$<fmt:formatNumber type = "number"  maxFractionDigits = "2"  minFractionDigits = "2" value = "${peticion.monto}" /></td>
				            </tr>
						</c:if>
			            <c:if test="${con%2!=0}">
							<tr style="background: #fff">
				                <td style="text-align: left !important;">${peticion.acesor}</td>
				                <td>${peticion.cantidad}</td>
				                <fmt:setLocale value = "es_MX"/>
				                <td>$<fmt:formatNumber type = "number"  maxFractionDigits = "2"  minFractionDigits = "2" value = "${peticion.monto}" /></td>
						</c:if>
					</c:forEach>
				</tbody>
				<thead style="background: #0681d2; color: white;">
					<tr>
						<th>TOTAL:</th>
						<th style="text-align: right;">${total}</th>
						<fmt:setLocale value = "es_MX"/>
						<th style="text-align: right;">$<fmt:formatNumber type = "number"  maxFractionDigits = "2"  minFractionDigits = "2" value = "${montoTotal}" /></th>
					<tr>
				</thead>
			</table>
		</div>
	</body>
</html>