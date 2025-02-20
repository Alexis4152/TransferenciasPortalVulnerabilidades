<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="application/vnd.ms-excel" %>                                                                                                                   
<% response.setHeader("Content-Disposition", "attachment; filename=\"desglose.xls\""); %>       
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
<table>
	  
		 <tr >
		    
			 <td colspan="4" align="center" style="font-weight:bold;">Portal de transferencias</td>
		 
		 
		 </tr>
		 <tr >
		    
			 <td colspan="4" align="center" style="font-weight:bold;" >Fecha de generación:<c:out value="${fecha}" /></td>
		 
		 
		 </tr>
		 		 <tr >
		    
			 	 <td colspan="4" align="center" style="font-weight:bold;" >Generado por: ${sessionScope.empleado.nombre}</td>
		 
		 
		 </tr>
	
	 </table>
<div>
	<table  border="1"  >
		<tr>
			<td width="60px" style="font-weight:bold;" >Banco:</td>
			<td width="100px" ><c:out value="${transferencia.banco}" /></td>

			<td width="70px" style="font-weight:bold;"  >Cuenta:</td>
			<td><c:out value="${transferencia.cuenta}" /></td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >Fecha:</td>
			<td><c:out value="${transferencia.fecha}" /></td>
			<td width="60px" style="font-weight:bold;"  >Importe:</td>
			<td>$ <c:out value="${transferencia.importeString}" /></td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >ID:</td>
			<td><c:out value="${transferencia.idtransferencia}" /></td>

			<td width="75px" style="font-weight:bold;" >Ref:</td>
			<td><c:out value="${transferencia.alias}" /></td>
		</tr>
		<tr>
			<td width="60px" style="font-weight:bold;" >Estatus:</td>
			<td><div id="estatusDesglose" > <c:out value="${transferencia.estatusDescripcion}" /></div></td>
	
			<td width="60px" style="font-weight:bold;" >&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	

 
Detalle de cuentas:
<div>
	
	<table  border="1" >
	
	        <thead>
	            <tr  bgcolor="#66CCFF" >
	            	<th width="55px" >No</th>
	                <th>Region</th>
	                <th>Cuenta</th>
	                <th>Importe</th>	
	                <th>Tipo</th>	               
	                <th>Esporadico SAP</th>     
	                <th>Estatus PP</th>	               
	                <th>Descripcion PP</th>                 
	                	
	            </tr>
	        </thead>
	
	        <tbody>
		       <c:forEach var="pagos" items="${listaPagos}" varStatus="a"  >
		            <tr>
		                <td><c:out value="${a.index+1}"   /></td>
		                <td>${pagos.region}</td>
		                <td>${pagos.cuenta}</td>   
		                <td>$ ${pagos.importeString}</td>  	
		                <td>${pagos.tipo}</td>  		               
		                <td>${pagos.sap}</td>     
		                <td>${pagos.estatusPP}</td>       
		                <td>${pagos.descripcionPP}</td>                  	                
		                
		            </tr>				   			
				   			
		       </c:forEach>
		       
	     
	            
	            
	            
	        </tbody>
	    </table>
	    
	 
	


	 </div>  

	

</div>

<div >
	Historial:
	 <table>
		  <c:forEach var="h" items="${listaHistorial}">
			 <tr >
			 
				  <td colspan="4">${h.fecha}-${h.empleado}-${h.statusString} <c:if test="${h.comentario!=null}">  ${h.comentario}</c:if> </td>
		 
			 
			 </tr>
		 </c:forEach> 
	 </table>
</div>
</div>

