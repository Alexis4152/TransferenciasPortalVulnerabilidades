<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
 Ext.onReady(function(){
 
 		
      /*  var grid = new Ext.ux.grid.TableGrid("the-table-Aplica", {
            stripeRows: true // stripe alternate rows
        });
        grid.render();*/
        
        
    new  Ext.Button({
	      	   text:'Programar',
	      	   width:'60',	      	                
	      	   applyTo:'botonActivaMultiple',
	      	   listeners:{
		            'click': function(){
		            
		              try{
						if(Ext.Ajax.serializeForm('listaAplicaForm')==''){
						  Ext.MessageBox.alert('Estatus','No hay transferencias seleccionadas' );
						  return;
						 }
		           		}catch(e){
		           		 Ext.MessageBox.alert('Estatus','No hay transferencias disponibles' );
						  return;
		           		}
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('listaAplicaForm'))
		                ajaxDivUpdater('TemplateAction.action','parameter=detalleTransferenciaExecute&lista=lista&'+Ext.Ajax.serializeForm('listaAplicaForm'),'listaTransferenciaAplica'); 
		            }
		    	}
	      });     

  });
 var general=1; 
 var banderaFA=1;
 var banderaMIX=1;
 var banderaCU=1;
 
  
 function seleccionarTodoAplica(){
   for (i=0;i<document.listaAplicaFormName.elements.length;i++)
      if(document.listaAplicaFormName.elements[i].type == "checkbox")
         document.listaAplicaFormName.elements[i].checked=general;
  if(general==1){
  
	   banderaFA=0;
	   banderaMIX=0;
	   banderaCU=0;	
  	   general=0;
 }		
 else if(general==0){
 	   banderaFA=1;
	   banderaMIX=1;
	   banderaCU=1;	
 		general=1;	
 	}	

}
 function seleccionarFA(){
  <c:forEach var="trans" items="${listaTrasferencia}">
	  <c:if test="${trans.tipoPagos=='FA'}">
	    
	 	 Ext.getDom('${trans.idtransferencia}_${trans.tipoPagos}').checked=banderaFA;
	  </c:if>
 </c:forEach>
 
 if(banderaFA==1)
 		banderaFA=0;
 else if(banderaFA==0)
 		banderaFA=1;		
}
 function seleccionarMIX(){
	  <c:forEach var="trans" items="${listaTrasferencia}">
		  <c:if test="${trans.tipoPagos=='MIX'}">
		 	 Ext.getDom('${trans.idtransferencia}_${trans.tipoPagos}').checked=banderaMIX;
		  </c:if>
	 </c:forEach>
	 
	 if(banderaMIX==1)
	 		banderaMIX=0;
	 else if(banderaMIX==0)
	 		banderaMIX=1;		
}
 function seleccionarCU(){
	  <c:forEach var="trans" items="${listaTrasferencia}">
		  <c:if test="${trans.tipoPagos=='CU'}">
		 	 Ext.getDom('${trans.idtransferencia}_${trans.tipoPagos}').checked=banderaCU;
		  </c:if>
	 </c:forEach>
	 
	 if(banderaCU==1)
	 		banderaCU=0;
	 else if(banderaCU==0)
	 		banderaCU=1;		
}    
</script>
<div>
<a href="javascript:seleccionarTodoAplica()"><span class="x-tree-node">Seleccionar</span></a>|
<a href="javascript:seleccionarMIX()"><span class="x-tree-node">MIX</span></a>|
<a href="javascript:seleccionarFA()"><span class="x-tree-node">FA</span></a>|
<a href="javascript:seleccionarCU()"><span class="x-tree-node">CU</span></a>
<br/>
<form id="listaAplicaForm" name="listaAplicaFormName" >
<table  width="100%" id="the-table-h">

        <thead>
            <tr style="background:#eeeeee;">
            	<th width="55px" >Aplicar</th>
                <th>Cuenta</th>
                <th>Fecha</th>
                <th>Importe</th>
                <th>Pagos</th>
                <th>Cliente</th>
                <th width="50px">Ver</th>
            </tr>
        </thead>

        <tbody>
        	 <c:forEach var="trans" items="${listaTrasferencia}">
	            <tr>
	                <td><input name="aplica" id="${trans.idtransferencia}_${trans.tipoPagos}" value="${trans.idtransferencia}_${trans.tipoPagos}" type="checkbox"/>  </td>
	                <td>${trans.cuenta}</td>
	                <td>${trans.fecha}</td>
	                <td>$ ${trans.importeString}</td>   
	                <td>${trans.tipoPagos}</td>
	                <td>${trans.cliente}</td>
	                 <td><a href="javascript:addTabDetalle('TemplateAction.action','parameter=detalleTransferencia&idTransferencia=${trans.idtransferencia}&tipoTrans=${trans.tipoPagos}')">Ver</a>  </td>
              
	                
	            </tr>		   			
			   			
	       </c:forEach>
    
      
            
        </tbody>
    </table>
</form>
		<br/>
     <div align="center" id="botonActivaMultiple"></div>
     <div id="listaTransferenciaAplica" ></div>
</div>
<form id="filtroBusqueda"/>
<input name="fecha" type="hidden" value="<c:out value="${fecha}"/>" />
<input name="banco" type="hidden" value="<c:out value="${banco}"/>" />
<input name="importe" type="hidden" value="<c:out value="${importe}"/>" /> 
</form>

