<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"reporteEstatus.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>  
		<title>Reporte</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<style>
			td {
				text-align: right;
			}
			.anychart-credits-text {
				display: none;
			}
			.anychart-credits-logo {
				display: none;
			}
			#container {
			    width: 100%;
			    height: 250px;
			    margin: 0;
			    padding: 0;
			}
		</style>
	</head>
	<body>
	
		<div style="width: 65%" align="center">
			<!-- start grafica -->
			<div id="anychart-embed-samples-bct-column-chart-03" class="anychart-embed anychart-embed-samples-bct-column-chart-03">
			<div id="ac_style_samples-bct-column-chart-03" style="display:none;">
			</div>
			<div id="container" style="width: 90%"></div>
			<script>
			anychart.onDocumentReady(function () {
			
			
			
			
			
			    // create data
			    var data = ${data};
			
			    // create a chart
			    var chart = anychart.column();
			
			    // create a column series and set the data
			    var series = chart.column(data);
			    
				series.name("Total");
			    
			
			    // set the chart title
			    chart.title("DESGLOSE POR CICLO");
			
			    // set the titles of the axes
			    chart.xAxis().title("Ciclo");
			    chart.yAxis().title("Total");
			
			    // set the container id
			    chart.container("container");
			
			    // initiate drawing the chart
			    chart.draw();
			});
			</script>
			</div>
			<!-- end grafica -->
		</div>
		
		<div style="width: 35%">
		
			<table style="width: 90%; border: 1px solid #0681d2;">
				<thead style="background: #0681d2; color: white;">
					<tr>
						<th style="text-align: center;">CICLO</th>
						<th style="text-align: center;">TOTAL</th>
						<th style="text-align: center;">PORCENTAJE</th>
					<tr>
				</thead>
				<tbody>
					<br/>
					<br/>
					<c:set var="con" value="0"/>
					<c:set var="total" value="0"/>
					<c:forEach var="ciclo" items="${listaCiclos}" varStatus="i" >
						<c:set var="con" value="${con+1}"/>
						<c:set var="total" value="${total+ciclo.cantidad}"/>
						<c:if test="${con%2==0}">
							<tr style="background: #c2e5fe">
				                <td>${ciclo.ciclo}</td>
				                <td>${ciclo.cantidad}</td>
				                <td>${ciclo.porcentaje}%</td>
				            </tr>
						</c:if>
			            <c:if test="${con%2!=0}">
							<tr style="background: #fff">
				                <td>${ciclo.ciclo}</td>
				                <td>${ciclo.cantidad}</td>
				                <td>${ciclo.porcentaje}%</td>
				            </tr>
						</c:if>
					</c:forEach>
				</tbody>
				<thead style="background: #0681d2; color: white;">
					<tr>
						<th>TOTAL:</th>
						<th style="text-align: right;">${total}</th>
						<th style="text-align: right;">100.00%</th>
					<tr>
				</thead>
			</table>
		</div>
	</body>
</html>