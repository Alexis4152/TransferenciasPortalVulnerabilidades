<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
		    var grid = new Ext.ux.grid.TableGrid("concentradoTransferenciasRechazadas", {
            stripeRows: true
        });
        grid.getStore().fields.get('tcol-1').sortType = function(nombre){
        	return nombre.substring(nombre.indexOf('>')+1,nombre.indexOf('</div>'));
	    }
        grid.getStore().fields.get('tcol-2').sortType = function(numeroTransferencias){
        	return parseInt(numeroTransferencias.substring(numeroTransferencias.indexOf('>')+1,numeroTransferencias.indexOf('</div>')));
	    }
        grid.render();

  });
 function pagosByUsuarioRechazos(usuario){


	 addPanel('pagosByUsuarioRechazos','Reportes.action','usuario='+usuario,'autoload')

	 }
</script>
<div>

<table  width="100%" id="concentradoTransferenciasRechazadas">

        <thead>
            <tr style="background:#eeeeee;">
                <th>Usuario</th>
                <th>Nombre</th>
                <th>Numero de transferencias</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a" >
	            <tr>
	                <td><div onclick="pagosByUsuarioRechazos('${trans.usuario}')" >${trans.usuario}</div></td>
	                <td><div onclick="pagosByUsuarioRechazos('${trans.usuario}')" >${trans.nombreUsuario}</div></td> 
	                <td><div onclick="pagosByUsuarioRechazos('${trans.usuario}')" >${trans.idtransferencia}</div></td>
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
