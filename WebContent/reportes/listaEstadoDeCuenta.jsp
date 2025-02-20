<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){
 
 		
       /* var grid = new Ext.ux.grid.TableGrid("the-table-Tra-ByBanco", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/
        
          

  });
  
  function addPago(coment){
 
  if(coment.checked){
 	 new Ext.Window({
  				id:'addPagoWindowExt',
  				title:'Agregar Comentario',                
                layout:'fit',
                width:300,
              //  height:100, 
                modal:true,
                
                plain: true,
                 autoLoad:{
		                   url:'Reportes.action',
		                   	//el párametro "parameter" hace referencia a un metodo de Dispatch action
							//params:'parameter=addComentario&idTransferencia=${idTransferencia}';
							params:'parameter=addComentario&idTransferencia='+coment.value+'&importe=${importe}&marca=${marca}&banco=${banco}&fecha=${fecha}'
							}
                }).show();
                coment.checked=false;
      }else {
      	 coment.checked=true;
	  	if(confirm('¿Desea desmarcar la transferencia?')){
	  		
	  		ajaxDivUpdater('Reportes.action','parameter=deleteComentario&idTransferencia='+coment.value+'&fecha=${fecha}&banco=${banco}&importe=${importe}&marca=${marca}','listaTrans');
	  	}	
	  
	  }
  
  }
   
</script>
<div id="listaTrans">

<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="55px" >No</th>
            	<th>Marcar</th>
            	<th>Tipo de Marca</th>
                <th>Comentario</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Pagos</th>
                <th>Estatus</th>
                <th>Cliente</th>
                <th width="50px" >Ver</th>
            </tr>
        </thead>
	
        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencias}"  varStatus="a"  >
	            <tr <c:if test="${trans.alias=='M2K'}"> style="background:#ffff00;" </c:if> <c:if test="${trans.alias=='SAP'}"> style="background:#0080ff;" </c:if> >
	               <td><c:out value="${a.index+1}"   /></td>
	                <td><input type="checkbox" id="marcaTrans"  value="${trans.idtransferencia}"  onclick="addPago(this);" <c:if test="${trans.alias=='SAP' || trans.alias=='M2K'}"> checked </c:if> ></td>
	               <td>${trans.alias}</td>
	                <td>${trans.concepto}</td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td> 
	                <td>${trans.tipoPagos}</td> 
	                <td>${trans.estatusDescripcion}</td>  
	                <td>${trans.cliente}</td>
	                
	                <td><a href="javascript:addTabDetalle('TemplateAction.action','parameter=detalleTransferencia&idTransferencia=${trans.idtransferencia}&tipoTrans=${trans.tipoPagos}')">Ver</a>  </td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    
   </table>
	 <form id="listaTraExcelBanco" name="forma">
	 <input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
	 <input name="banco" type="hidden" value="<c:out value="${banco}"/>" />
	 <input name="excel" type="hidden" value="<c:out value="${estatus}"/>" />
	 <input name="importe" type="hidden" value="<c:out value="${importe}"/>" />
	 <input name="marca" type="hidden" value="<c:out value="${marca}"/>" />
	</form>
		<br/>
    
    
</div>
