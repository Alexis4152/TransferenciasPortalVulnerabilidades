<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Reporte</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="ext/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="ext/excelGenerator.js"></script>
<script type="text/javascript" src="ext/alasql.min.js"></script>
<script type="text/javascript" src="ext/xlsx.core.min.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				var jsonGeneral = tableToJson('general');
				var jsonAcumulado = tableToJson('acumulado');
				var jsonTransferencias = tableToJson('transferencias');
				saveFile(jsonGeneral,jsonAcumulado,jsonTransferencias);
			});
</script>
</head>
<body>
	<div style="display: none;">
		<table id="general" border="1" width="220" cellspacing="5px">
			<tr>
				<td width="100px">Fecha Inicio:</td>
				<td width="100px">${fecha}</td>
			</tr>
			<tr>
				<td>Fecha Fin:</td>
				<td>${fecha2}</td>
			</tr>
			<tr>
				<td align="right">Generado por: ${sessionScope.empleado.nombre} el ${fechaCreacion}</td>
			</tr>
		</table>
		<table id="acumulado" border="1">
			<thead>
				<tr style="background: #eeeeee;">
					<th>Usuario</th>
					<th>Nombre</th>
					<th>Total</th>
					<th>Numero de transferencias</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="trans" items="${sessionScope.listaTrasferencias}"
					varStatus="a">
					<tr>
						<td>${trans.usuario}</td>
						<td>${trans.nombreUsuario}</td>
						<td>$ ${trans.importeString}</td>
						<td>${trans.idtransferencia}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table id="transferencias" width="100%" border="1">

			<thead>
				<tr style="background: #eeeeee;">
					<th>Usuario</th>
					<th>Nombre</th>
					<th>Fecha transferencia</th>
					<th>Importe</th>
					<th>Alias</th>
					<th>IdTransferencia</th>
					<th>TipoPagos</th>
					<th>Fecha Desglose</th>
					<th>Rechazado</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="trans" items="${sessionScope.listaAcumuladaUsuario}"
					varStatus="a">
					<tr>
						<td>${trans.usuario}</td>
						<td>${trans.nombreUsuario}</td>
						<td>${trans.fecha}</td>
						<td>$ ${trans.importeString}</td>
						<td>${trans.alias}</td>
						<td>${trans.idtransferencia}</td>
						<td>${trans.tipoPagos}</td>
						<td>${trans.fechaHistorial}</td>
						<td>${trans.rechazado}</td>
					</tr>

				</c:forEach>


			</tbody>
		</table>
	</div>
</body>
</html>