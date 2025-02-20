<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>Reporte</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
td {
	text-align: right;
}
</style>
<script type="text/javascript">
 
 window.parent.resultado('<c:out value="${mensaje}"/>');
 
</script>
</head>
<body>
	<br />
	<br />
	<c:if test="${mensaje=='OK'}">
	<div style="width: 100%" align="center">
		<table style="width: 70%; border: 1px solid #0681d2;">
			<thead style="background: #0681d2; color: white;">
				<tr>
					<th style="text-align: center;">USUARIO</th>
					<th style="text-align: center;">NOMBRE</th>
					<th style="text-align: center;">FECHA TRANSFERENCIA</th>
					<th style="text-align: center;">IMPORTE</th>
					<th style="text-align: center;">ALIAS</th>
					<th style="text-align: center;">ID TRANSFERENCIA</th>
					<th style="text-align: center;">TIPO PAGOS</th>
					<th style="text-align: center;">FECHA DESGLOSE</th>
					<th style="text-align: center;">HORA DESGLOSE</th>
					<th style="text-align: center;">REFERENCIA CLIENTE</th>
					<th style="text-align: center;">BANCO</th>
					<th style="text-align: center;">CUENTA</th>
				<tr>
			</thead>
			<tbody>
				<c:set var="con" value="0" />
				<c:forEach var="lista" items="${listaTransIngresos}" varStatus="i">
					<c:set var="con" value="${con+1}" />
					<c:if test="${con%2==0}">
						<tr style="background: #c2e5fe">
							<td>${lista.usuario}</td>
							<td>${lista.nombreUsuario}</td>
							<td>${lista.fechaTransferencia}</td>
							<fmt:setLocale value="es_MX" />
							<td>$<fmt:formatNumber type="number" maxFractionDigits="2"
									minFractionDigits="2" value="${lista.importe}" /></td>
							<td>${lista.alias}</td>
							<td>${lista.idtransferencia}</td>
							<td>${lista.tipoPagos}</td>
							<td>${lista.fecha}</td>
							<td>${lista.indicador}</td>
							<td>${lista.referenciaCliente}</td>
							<td>${lista.banco}</td>
							<td>${lista.cuenta}</td>
						</tr>
					</c:if>
					<c:if test="${con%2!=0}">
						<tr style="background: #fff">
							<td>${lista.usuario}</td>
							<td>${lista.nombreUsuario}</td>
							<td>${lista.fechaTransferencia}</td>
							<fmt:setLocale value="es_MX" />
							<td>$<fmt:formatNumber type="number" maxFractionDigits="2"
									minFractionDigits="2" value="${lista.importe}" /></td>
							<td>${lista.alias}</td>
							<td>${lista.idtransferencia}</td>
							<td>${lista.tipoPagos}</td>
							<td>${lista.fecha}</td>
							<td>${lista.indicador}</td>
							<td>${lista.referenciaCliente}</td>
							<td>${lista.banco}</td>
							<td>${lista.cuenta}</td>
						</tr>
					</c:if>

				</c:forEach>
			</tbody>
		</table>
	</div>
	</c:if>
</body>
</html>
