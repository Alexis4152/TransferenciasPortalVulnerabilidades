<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 Ext.onReady(function(){   
 
 //comentario
 
 		 Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');
 	
      /*  var grid = new Ext.ux.grid.TableGrid("the-table-Tra", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/
        
        
      	        
        
        
        <c:if test="${titulo=='Pendientes' || titulo=='Aplicadas'}">
        var tb = new Ext.Toolbar({
		    items: [ 
		        { 
		            // xtype: 'button', // default for Toolbars, same as 'tbbutton'
		            iconCls: 'excel', 
		            text: ' ',
		            width: 40, 
		            listeners:{
			            'click': function(){
			            
                        	var redirect = 'TiempoReal.action?parameter=listaPendientes&excel=true';
			            	<c:if test="${titulo=='Aplicadas' }"> 
			            	redirect = 'TiempoReal.action?parameter=listaAplicadas&excel=true';
			            	</c:if>
			            	
                 
		                //    alert(redirect);
		                    document.location.href=redirect; 
		                    
			            
			            
			               
				            }
			    	}      
		        }
		    ]
		});  
		
		tb.render('listaTransferenciaToolbar${titulo}');
		  </c:if>       
        
        
      <c:if test="${titulo=='Pendientes'}">
         new  Ext.Button({
	      	   text:'Programar',
	      	   width:'60',	      	                
	      	   applyTo:'botonTranMultiplepen',
	      	   listeners:{
		            'click': function(){
		            
		              try{
						if(Ext.Ajax.serializeForm('formadeslgosa')==''){
						  Ext.MessageBox.alert('Estatus','No hay transferencias seleccionadas' );
						  return;
						 }
		           		}catch(e){
		           		 Ext.MessageBox.alert('Estatus','No hay transferencias disponibles' );
						  return;
		           		}
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAplicaForm'))
		                ajaxDivUpdater('TemplateAction.action','parameter=detalleTransferenciaExecute&pendiente=true&'+Ext.Ajax.serializeForm('formadeslgosa'),'listaTransferenciaPendiente'); 
		            }
		    	}
	      });  
	      </c:if>       

  });
  <c:if test="${titulo=='Pendientes'}">
 var generalPen=1; 
 var banderaFAPen=1;
 var banderaMIXPen=1;
 var banderaCUPen=1;
 
  
 function seleccionarTodoAplicaPen(){
   for (i=0;i<document.formadeslgosa.elements.length;i++)
      if(document.formadeslgosa.elements[i].type == "checkbox")
         document.formadeslgosa.elements[i].checked=generalPen;
  if(generalPen==1){
  
	   banderaFAPen=0;
	   banderaMIXPen=0;
	   banderaCUPen=0;	
  	   generalPen=0;   
 }		
 else if(generalPen==0){
 	   banderaFAPen=1;
	   banderaMIXPen=1;
	   banderaCUPen=1;	
 		generalPen=1;	
 	}	

}
 function seleccionarFAPen(){
  <c:forEach var="trans" items="${listaTrasferencia}">
	  <c:if test="${trans.tipoPagos=='FA'}">
	    
	 	 Ext.getDom('${trans.idtransferencia}_${trans.tipoPagos}p').checked=banderaFAPen;
	  </c:if>
 </c:forEach>
 
 if(banderaFAPen==1)
 		banderaFAPen=0;
 else if(banderaFAPen==0)
 		banderaFAPen=1;		
}
 function seleccionarMIXPen(){
	  <c:forEach var="trans" items="${listaTrasferencia}">
		  <c:if test="${trans.tipoPagos=='MIX'}">
		 	 Ext.getDom('${trans.idtransferencia}_${trans.tipoPagos}p').checked=banderaMIXPen;
		  </c:if>
	 </c:forEach>
	 
	 if(banderaMIXPen==1)
	 		banderaMIXPen=0;
	 else if(banderaMIXPen==0)
	 		banderaMIXPen=1;		
}
 function seleccionarCUPen(){
	  <c:forEach var="trans" items="${listaTrasferencia}">
		  <c:if test="${trans.tipoPagos=='CU'}">
		 	 Ext.getDom('${trans.idtransferencia}_${trans.tipoPagos}p').checked=banderaCUPen;
		  </c:if>
	 </c:forEach>
	 
	 if(banderaCUPen==1)
	 		banderaCUPen=0;
	 else if(banderaCUPen==0)
	 		banderaCUPen=1;		
}    
</c:if>
</script>

<c:if test="${titulo=='Pendientes' || titulo=='Aplicadas' }"> 
<div id="listaTransferenciaToolbar${titulo}"></div>
</c:if>
<div class="instrucciones">   
	
	<c:if test="${titulo=='Pendientes'}"> 
	<b>Aqui se muestran las transferencias que ya fueron desglosadas y estan pendientes de programar para su aplicación a MOBILE (ver Aplica Transferencia para las instrucciones).</b><br/>
	</c:if>
	<c:if test="${titulo=='En Proceso'}"> 
	<b>Aqui se muestran las transferencias que fueron programadas y estan en espera de ser aplicadas en MOBILE.</b>
	</c:if>
	<c:if test="${titulo=='Aplicadas'}"> 
	<b>Aqui se muestran las transferencias que ya fueron aplicadas el dia de hoy.</b>
	</c:if>
	<c:if test="${titulo=='Errores'}"> 
	<b>Aqui se muestran las transferencias que tuvieron un error al aplicarse y tienen que se revisadas para su correccion.</b>
	</c:if>
</div>   

<div id="listaTransferenciaPendiente" >
<c:if test="${titulo=='Pendientes'}"> 
<a href="javascript:seleccionarTodoAplicaPen()"><span class="x-tree-node">Seleccionar</span></a>|
<a href="javascript:seleccionarMIXPen()"><span class="x-tree-node">MIX</span></a>|
<a href="javascript:seleccionarFAPen()"><span class="x-tree-node">FA</span></a>|
<a href="javascript:seleccionarCUPen()"><span class="x-tree-node">CU</span></a>
</c:if>
<div>
<form  <c:if test="${titulo=='Pendientes'}"> id="listaTraForm" name="formadeslgosa" </c:if> >
<table  width="100%" id="the-table-h"  >

        <thead>
            <tr style="background:#eeeeee;">
            <c:if test="${titulo=='Pendientes'}">
            	<th width="55px" >Aplicar</th>
            </c:if>	
            	<th>Id Trans</th>
                <th width="120px" >Cuenta </th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Pagos</th>
                <th>Banco</th>
                <c:if test="${titulo=='Pendientes' || titulo=='Aplicadas'}">
                <th width="120px">${tituloFecha} </th>
                <th>Usuario</th>                 
                </c:if>
                <th width="200px">Cliente</th> 
                <th width="250px">Alias</th> 
                <th width="70px">Ver</th>
            </tr>
        </thead>

        <tbody>
      	 <c:forEach var="trans" items="${listaTrasferencia}">
	            <tr>
	                <c:if test="${titulo=='Pendientes'}">
	                <td><input name="aplica" id="${trans.idtransferencia}_${trans.tipoPagos}p" value="${trans.idtransferencia}_${trans.tipoPagos}" type="checkbox"/>  </td>
	                </c:if>
	                <td>${trans.idtransferencia}</td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td> 
	                <td>${trans.tipoPagos}</td> 
	                <td>${trans.banco}</td>  
	                <c:if test="${titulo=='Pendientes' || titulo=='Aplicadas'}">
	                <td>${trans.fechaHistorial}</td>    
	                <td>${trans.usuario}</td>
	                </c:if>  
	                <td>${trans.cliente}</td>
					<td>${trans.alias}</td>
	                <td><a href="javascript:addTabDetalle('TemplateAction.action','parameter=detalleTransferencia&idTransferencia=${trans.idtransferencia}&tipoTrans=${trans.tipoPagos}&pendiente=${pendiente}')">Ver</a>  </td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
      
            
        </tbody>
    </table>
</form>
		<br/>
	<c:if test="${titulo=='Pendientes'}"> 
	<div align="center" id="botonTranMultiplepen"></div>
	</c:if>	
     
     <div id="listaTransferenciaTra" ></div>
</div>
