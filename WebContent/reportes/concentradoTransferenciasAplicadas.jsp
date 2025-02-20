<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript">
 Ext.onReady(function(){
 
	
        var grid = new Ext.ux.grid.TableGrid("transferenciasPlicadasReporte", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();



  });
 function verPagos(fecha,banco,nombreBanco){


addPanel('pagosByEstatus','Reportes.action','fecha='+fecha+'&banco='+banco+'&nombreBanco='+nombreBanco,'autoload')

/* new Ext.Window({
			id:'detalleConcentrado',			
			title:'Detalle Concentrado',
			modal:true,
			closable:true,
			width:500,
		//	height:			
			resizable:false,
			draggable:false,			
			onEsc:function(b){ 
	            
	            },
			autoLoad:{
		              url:'Reportes.action',		             
					  params:'parameter=pagosByEstatus&
		                   }
		}).show();*/

}
  
</script>


<div>


<table  width="100%" id="transferenciasPlicadasReporte">

        <thead>
            <tr style="background:#eeeeee;">
            	<th>Dias</th>
            	<th>Bancomer</th>
            	<th>HSBC</th>
            	<th>BANAMEX</th>
            	<th>BANORTE</th>
            	<th>SANTANDER</th>
            	<th>SCOTIABANK</th>
            	<th>INBURSA</th>
            	<th>TOTAL</th>
   
            </tr>
        </thead>

        <tbody>
        	 <c:forEach var="trans" items="${reporteList}">
	            <tr>
	                <td>${trans.fecha.fecha} </td>
	                <td> <div onclick="verPagos('${trans.fecha.fecha}','1','Bancomer')" > ${trans.BANCO1.transaccion} <hr/>${trans.BANCO1.monto}</div> </td>
	                <td><div onclick="verPagos('${trans.fecha.fecha}','2','HSBC')" > ${trans.BANCO2.transaccion} <hr/> ${trans.BANCO2.monto}</div></td>
	                <td><div onclick="verPagos('${trans.fecha.fecha}','3','BANAMEX')" > ${trans.BANCO3.transaccion} <hr/> ${trans.BANCO3.monto}</div></td>
	                <td><div onclick="verPagos('${trans.fecha.fecha}','4','BANORTE')" > ${trans.BANCO4.transaccion} <hr/> ${trans.BANCO4.monto}</div> </td>
	                <td><div onclick="verPagos('${trans.fecha.fecha}','5','SANTANDER')" > ${trans.BANCO5.transaccion} <hr/> ${trans.BANCO5.monto}</div> </td>
	                <td><div onclick="verPagos('${trans.fecha.fecha}','6','SCOTIABANK')" > ${trans.BANCO6.transaccion} <hr/> ${trans.BANCO6.monto}</div></td>
	                <td><div onclick="verPagos('${trans.fecha.fecha}','7','INBURSA')" > ${trans.BANCO7.transaccion} <hr/> ${trans.BANCO7.monto}</div></td>
	                <td><div onclick="verPagos('${trans.fecha.fecha}','0','TOTAL')" > ${trans.total.transaccion} <hr/>${trans.total.monto}</div></td>	                
	            </tr>		   			
			   			
	       </c:forEach>
        </tbody>
    </table>
     <form id="listaTraConcentrado">
	 <input name="mes" type="hidden" value="<c:out value="${mes}"/>" />
	 <input name="year"  type="hidden" value="<c:out value="${year}"/>"  />
	 <input name="busca" type="hidden" value="ok" />
	 <input name="excel" type="hidden" value="ok" />
	
	</form>
</div>
