<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
		    var grid = new Ext.ux.grid.TableGrid("concentradoTransferenciasUsuario", {
            stripeRows: true
        });
        grid.getStore().fields.get('tcol-1').sortType = function(nombre){
        	return nombre.substring(nombre.indexOf('>')+1,nombre.indexOf('</div>'));
	    }
        grid.getStore().fields.get('tcol-2').sortType = function(total){
        	console.log(total.substring(total.indexOf('>')+1,total.indexOf('</div>')).replace('$ ','').replace(/,/g,''));
        	return parseFloat(total.substring(total.indexOf('>')+1,total.indexOf('</div>')).replace('$ ','').replace(/,/g,''));
	    }
        grid.getStore().fields.get('tcol-3').sortType = function(numeroTransferencias){
        	return parseInt(numeroTransferencias.substring(numeroTransferencias.indexOf('>')+1,numeroTransferencias.indexOf('</div>')));
	    }
        grid.render();

  });
 function verPagos(usuario){


	 addPanel('pagosByUsuario','Reportes.action','usuario='+usuario,'autoload')

	 }
</script>
<div>

<table  width="100%" id="concentradoTransferenciasUsuario">

        <thead>
            <tr style="background:#eeeeee;">
                <th>Usuario</th>
                <th>Nombre</th>
                <th>Total</th>
                <th>Numero de transferencias</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a" >
	            <tr>
	                <td><div onclick="verPagos('${trans.usuario}')" >${trans.usuario}</div></td>
	                <td><div onclick="verPagos('${trans.usuario}')" >${trans.nombreUsuario}</div></td>
	                <td><div onclick="verPagos('${trans.usuario}')" >$ ${trans.importeString}</div></td>   
	                <td><div onclick="verPagos('${trans.usuario}')" >${trans.idtransferencia}</div></td>
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>
	 <form id="listaTraExcelUsuario">
	 <input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
	 <input name="fecha2"  type="hidden" value="<c:out value="${fecha2}"/>"  />
	 <input name="excel" type="hidden" value="<c:out value="${estatus}"/>" />
	</form>
		<br/>
    
    
</div>
