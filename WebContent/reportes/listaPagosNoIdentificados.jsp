<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		
       /* var grid = new Ext.ux.grid.TableGrid("the-table-Tra-ByBanco", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/
        
           mascara(false);    

  });
  
    
</script>
<div id="listaTrans">

<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="55px" >No</th>
            	<th>Región</th>
            	<th>Lote</th>
                <th>Importe</th>
                <th>Fecha</th>
                <th>ID Trans</th>
                <th>Importe Trans</th>
                <th>Fecha Trans</th>
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
	 <form id="listaTraExcelBanco" name="forma">
	 <input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
	 <input name="lote" type="hidden" value="<c:out value="${lote}"/>" />
	 <input name="excel" type="hidden" value="<c:out value="${estatus}"/>" />
	 <input name="importe" type="hidden" value="<c:out value="${importe}"/>" />
	 <input name="region" type="hidden" value="<c:out value="${region}"/>" />
	</form>
		<br/>
    
    
</div>
