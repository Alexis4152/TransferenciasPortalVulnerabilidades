<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		
        var grid = new Ext.ux.grid.TableGrid("the-table-Usuarios", {
            stripeRows: true // stripe alternate rows
        });
        grid.render(); 
        
   
	
  });
</script>
<div  >



<table  width="100%" id="the-table-Usuarios">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="35px" >Selec</th>
                <th width="100px"  >Usuario</th>
                <th width="200px" >Nombre</th>
                <th>Puesto</th>
                <th>Perfil</th>
                <th>Estatus </th>
               
            </tr>
        </thead>

        <tbody>
        	 <c:forEach var="usr" items="${list}">  
        	
	            <tr>
	                <td><input name="usuarios" value="${usr.idEmpleado}" type="checkbox"/>  </td>
	                <td>${usr.usuario}</td>
	                <td>${usr.nombre}</td>
	                <td> ${usr.puesto}</td>   
	                <td>${usr.descripcionPerfil}</td>
	                <td><c:if test="${usr.estatusUsuario==1}">Habilitado</c:if><c:if test="${usr.estatusUsuario==-1}">Deshabilitado</c:if>  </td>
	               
              
	                
	            </tr>		   			
			   			
	       </c:forEach>

      
            
        </tbody>
    </table>


</div>
