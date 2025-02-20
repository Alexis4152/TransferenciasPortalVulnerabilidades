<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"detalle.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <c:forEach var="detalle" items="${listaDetalle}" varStatus="a"  >
<div id="detalleCompleto"> 
	<table>
		 <tr >
			 <td colspan="4" align="center" style="font-weight:bold;">Portal de transferencias</td>
		 </tr>
		 <tr >
			 <td colspan="4" align="center" style="font-weight:bold;" >Fecha de generaci�n:<c:out value="${fecha}" /></td>
		 </tr>
		 <tr >
		  	 <td colspan="4" align="center" style="font-weight:bold;" >Generado por: ${sessionScope.empleado.nombre}</td>
		 </tr>
	</table>
<div>
	<table border="1" background="#0000FF"  >
		<tr>
			<td width="60px" style="font-weight:bold;" >Banco:</td>
			<td width="100px" ><c:out value="${detalle.transferenciaPojo.banco}" /></td>

			<td width="70px" style="font-weight:bold;"  >Cuenta:</td>
			<td><c:out value="${detalle.transferenciaPojo.cuenta}" /></td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >Fecha:</td>
			<td><c:out value="${detalle.transferenciaPojo.fecha}" /></td>
			<td width="60px" style="font-weight:bold;"  >Importe:</td>
			<td>$ <c:out value="${detalle.transferenciaPojo.importeString}" /></td>
		</tr>  
		<tr>
			<td width="60px" style="font-weight:bold;" >ID:</td>
			<td><c:out value="${detalle.transferenciaPojo.idtransferencia}" /></td>

		    <td width="70px" style="font-weight:bold;" >Alias</td>
			<td>${detalle.transferenciaPojo.alias}</td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >Estatus:</td>
			<td><c:out value="${detalle.transferenciaPojo.estatusDescripcion}" /></td>
	
			<td width="60px" style="font-weight:bold;" >&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	
	

</div>
<div>
	Desglose:
	<br/>
	<table  width="100%" border="1" id="the-table-detalle">
	
	        <thead>
	            <tr   bgcolor="#66CCFF">
	            	<th width="55px" >No</th>
	                <th >Region</th>
	                <th>Cuenta</th>
	                <th>Importe</th>
	                <th>Tipo</th>
	                <th>Lote</th>
	                <th>Fecha lote</th>
	
	            </tr>
	        </thead>
	
	        <tbody>
		       <c:forEach var="pagos" items="${detalle.listaPagos}" varStatus="a"  >
		            <tr>
		                <td><c:out value="${a.index+1}"></c:out></td>
		                <td>${pagos.region}</td>
		                <td>${pagos.cuenta}</td>   
		                <td>$ ${pagos.importeString}</td>   
		                <td>${pagos.tipo}</td>
		                <td>${pagos.lote}</td>
		                <td>${pagos.fechaLoteString}</td>
		                
		                
		            </tr>				   			
				   			
		       </c:forEach>
		       
	     
	           
	            
	            
	        </tbody>
	    </table>
	
	    
	 </div>  
<br/>

 <c:if test="${estatus == 7}">
 	Subtotales por Regi�n:
	  <div style="padding:15px;margin:15px">
	  	<table  border="1" >
	
	        <thead>
	            <tr  bgcolor="#66CCFF">
	            	
	                <th>Region</th>
	                <th>Lote</th>
	                <th colspan="2" >Importe</th>
	               
	
	            </tr>
	        </thead>
	
	        <tbody>
		       <c:forEach var="pagos" items="${detalle.listaPagos}" varStatus="a"  >
		            <tr>
		               
		                <td>${pagos.region}</td>
		                <td> <c:if test="${pagos.lote == 0}">FA </c:if> <c:if test="${pagos.lote != 0}">${pagos.lote}</c:if>  </td>   
		                <td colspan="2">$ ${pagos.importeString}</td>   
		               	                
		                
		            </tr>				   			
				   			
		       </c:forEach>
		       
	     
	           
	            
	            
	        </tbody>
	    </table>
	  
	  
	  </div>
	 </c:if>	 
	 
	 
	 
	 
	 
</div>
</c:forEach>
