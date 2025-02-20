<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <link rel="stylesheet" href="recursos/Layout.css" type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" />
	<script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="ext/ext-all.js"></script>
	<script type="text/javascript" src="ext/general.js"></script>
	<script type="text/javascript" src="ext/locale/ext-lang-es.js"></script>
	<style type="text/css">
        #the-table-detalleDesglose { border:1px solid #bbb;border-collapse:collapse; }
        #the-table-detalleDesglose td,#the-table th { border:1px solid #ccc;border-collapse:collapse;padding:5px; }
    </style>
	
	<style type="text/css">
        .lista { border:1px solid #bbb;border-collapse:collapse; }
        .lista td,.lista th { border:1px solid #ccc;border-collapse:collapse;padding:5px; }
    </style>
	
	<style>
	
	
	.excel {
    background-image: url(img/page_excel.png) !important;
	}
	.barras {
    background-image: url(img/chart_bar.png) !important;
	}
	.autorizar {
    background-image: url(img/autorizar.png) !important;
	}
	.usuarios {
    background-image: url(img/usuarios.png) !important;
	}
	.aplicar {
    background-image: url(img/aplicar.png) !important;
	}
	.carga {
    background-image: url(img/carga1.png) !important;   
	}
	.salir {
    background-image: url(img/door_in.png) !important;   
	}
	.correo {
    background-image: url(img/correo.png) !important;
	}
	.administrador {
    background-image: url(img/administrador.png) !important;
	}
	.alertas {
    background-image: url(img/alertas.png) !important;
	}
	
	
	</style>
	
<script type="text/javascript">
 
    alert('<c:out value="${sessionScope.empleado.idPefril}"/>');

 	 // Variables iniciales
 	 
 	 //Si esta imagen no se referencia, la toma de la red
 	 Ext.BLANK_IMAGE_URL = 'ext/resources/images/default/s.gif';
 	 //para que el autoload permita los scripts de ext
 	 Ext.Updater.defaults.loadScripts=true;
 	 //para que cada petición se le agrege un numero irrepetible(para IE), esto deshabilita el cache	 
 	 Ext.Updater.defaults.disableCaching=true;
 	 
 	
//Hilos o tareas para actualizacion en tiempo real	
	var pendientesTask = {
		    run: function(){
		        ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=pendientes&idEstatus=3','pendientes','...','false'); 
		    },
		    interval: 120000 //60   
		}
	var enProcesoTask = {
	    run: function(){
	         ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=proceso&idEstatus=5','proceso','...','false'); 
	    },
	    interval: 121000 //60 
	}
	var aplicadasTask = {
	    run: function(){
	        ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=aplicadas&idEstatus=7','aplicadas','...','false'); 
	    },
	    interval: 122000 //60 
	}	
	var errorTask = {
	    run: function(){
	       ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=error&idEstatus=4','error','...','false'); 
	    }, 
	    interval: 123000 //60 
	}	

	

// La funcion onReady espera a que todo el DOM(Html) se carge completamente, despues de eso podemos renderear paneles	
 Ext.onReady(function(){


	mascara(false);
	// para que los tooltips funcionen
	Ext.QuickTips.init();


	//Creamos un nuevo panel	
	new Ext.Panel({ 
		id:'viewport',
	    renderTo: 'main',	//Le decimos en que div se va a renderear
	    width: 900,
	    height: 450,       
	    layout: 'border',	//aqui le decimos que sea de tipo border(norte,sur,este,oeste)
	    
	    //este panel va atener varios hijos o items, cada uno se le espesifica el lugar en donde estara con "region"
	    //<c:out value="${sessionScope.empleado.idPefril}" />
	    items: [{
	    	id: 'menu_panel',
	        title: 'Menu  ',
	        region:'west',
	        margins: '5 0 0 5',
	        split: true,
	        width: 200,
	        collapsible: true,  //efecto muy chido :) 
	        cmargins: '5 5 0 5',
	        tools:[{
				    id:'maximize',
				    qtip: 'Maximiza el area de trabajo',
				    // hidden:true,
				    handler: function(event, toolEl, panel){
				    	toolEl.hidden=true;
				        Ext.getCmp('viewport').setHeight(650);
				    }
				},{
				    id:'restore',
				    qtip: 'Vuelve a su estado normal',
				    // hidden:true,
				    handler: function(event, toolEl, panel){
				        Ext.getCmp('viewport').setHeight(450);
				    }
				}],
	        layout:'anchor', 
	        
               // este panel va a tener mas hijos
                items:[{                                 
                    xtype: 'treepanel',
                    rootVisible:false,
                    loader: new Ext.tree.TreeLoader(),
			        root: new Ext.tree.AsyncTreeNode({
			        expanded: true,
			            children: [
			            
			            {
			                text: 'Carga Transferencia',
			                leaf: true,			               
			                iconCls:'carga',
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
					        disabled:true,
							hidden:true,
					  		</c:if>
			                listeners: {
						            click: function(n) {
						                // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('cargaTransferencia','TemplateAction.action');
						            } 
						        }	 		                
			            }, { 
			                text: 'Autoriza Transferencia', 
			                iconCls:'autorizar',
			                leaf: true,
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
					        disabled:true,
							hidden:true,
					  		</c:if>
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('autorizaTransferencia','TemplateAction.action');
						            }
						        }
			            }, {
			                text: 'Aplica Transferencia',
			                leaf: true,
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
					        disabled:true,
							hidden:true,
					  		</c:if>
			                iconCls:'aplicar',
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('aplicaTransferencia','TemplateAction.action');
						            }
						        }
			            },{
			                text: 'Envio SAP',
			                leaf: true,
			                disabled:true,
							hidden:true,
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('envioSap','TemplateAction.action')
						            }
						        }
			            },{
			                text: 'Revertir Transferencia',
			                leaf: true,
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
			                disabled:true,
							hidden:true,
							</c:if>
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('revertirTransferencia','TemplateAction.action')
						            }
						        }
			            },{
			                text: 'Bandeja de entrada',
			                leaf: true,
			                iconCls:'correo',
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
					        disabled:true,
							hidden:true,
					  		</c:if>
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('getBandeja','TemplateAction.action')
						            }
						        }
						        
			           	},S{
			                text: 'Desglose',
			                leaf: false,
			                <c:if test="${sessionScope.empleado.idPefril==2}">
							 disabled:true,
							 hidden:true,
							</c:if>			                
			            	children:[
				            			{text: 'Tomar Transferencias',
							             leaf: true,
							            // iconCls:'barras',
							                listeners: {
										            click: function(n) {
										              //  Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										               addPanel('tomarTransferencia','TemplateAction.action');
										            }
										        }
							            	
							            	
							           	},{
				            			 text: 'Desglosar Transferencias',
							             leaf: true,
							         //   	 iconCls:'barras',
							                listeners: {
										            click: function(n) {
										               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										               addPanel('transferenciasByUsuario','TemplateAction.action','','autoload');
										            }
										        }
							            	
							            	
							           	}
						           	]
			            },{
			                text: 'Reportes',
			                leaf: false,			                
			            	children:[
				            			{text: 'Transferencias Aplicadas',
							             leaf: true,
							             iconCls:'barras',
							             <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
									        disabled:true,
											hidden:true,
									  	 </c:if>
							                listeners: {
										            click: function(n) {
										              //  Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										               addPanel('transferenciasAplicadas','Reportes.action');
										            }
										        }
							            	
							            	
							           	},{
				            			 text: 'Transferencias Por Estatus',
							             leaf: true,
							            	 iconCls:'barras',
							                listeners: {
										            click: function(n) {
										               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										                 addPanel('transferenciasByEstatus','Reportes.action','concentrado=falses');
										            }
										        }
							            	
							            	
							           	},{
				            			 text: 'Transferencias Por Banco',
							             leaf: true, 
							             <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
									     disabled:true,
										  hidden:true,
									  	 </c:if>							
							             iconCls:'barras',
							                listeners: {
										            click: function(n) {
										               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										                 addPanel('transferenciasByBanco','Reportes.action');
										            }
										        }
							            	
							            	
							           	},{text: 'Transferencias Desglosadas',
							             leaf: true,
							             iconCls:'barras',
							                listeners: {
										            click: function(n) {
										              //  Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										               addPanel('transferenciasDesglosadas','Reportes.action');
										            }
										        }
							            	
							            	
							           	},{
				            			 text: 'Estados de Cuenta',
							             leaf: true, 
							             <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
									     disabled:true,
										  hidden:true,
									  	 </c:if>							
							             iconCls:'barras',
							                listeners: {
										            click: function(n) {
										               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										                 addPanel('transferenciasByEstadoDeCuenta','Reportes.action');
										            }
										        }
							            	
							            	
							           	}
							           	
						           	]
			            },{
			                text: 'Administración de peticiones',
			                leaf: true,			               
			                iconCls:'administrador',
			                <c:if test="${sessionScope.empleado.esJefe!=1}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			                listeners: {
					            click: function(n) {
					               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
					               addPanel('getPeticiones','TemplateAction.action')
					            }
					        }	 		                
						        
						},{
			                text: 'Alertas',
			                leaf: true,			               
			                iconCls:'alertas',
			                <c:if test="${sessionScope.empleado.esJefe!=1}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			                listeners: {
					            click: function(n) {
					               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
					               addPanel('getAlertas','TemplateAction.action')
					            }
					        }	 		                
						        
						},{
			                text: 'Formato Desglose',
			                leaf: true,			               
			                iconCls:'excel',
			                <c:if test="${sessionScope.empleado.idPefril==2}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			                listeners: {
						            click: function(n) {
										window.open('<%=request.getContextPath()%>/formatos/DesgloseTrans.xls','name','height=200,width=150');																             
						            } 
						        }	 		                
						        
						}, {
			                text: 'Usuarios',
			                leaf: true,
			                iconCls:'usuarios',
			                <c:if test="${sessionScope.empleado.esJefe!=1}">
					        disabled:true,
							hidden:true,
					  		</c:if>
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('usuarios','TemplateAction.action')
						            }
						        }
						        
			           	},{
			                text: 'Salir',
			                leaf: true,			               
			                iconCls:'salir',
			                listeners: {
						            click: function(n) {
						              mascara(true)
						              ajaxDivUpdater('Login.action','parameter=salir','dsplay_none'); 
						             
						            } 
						        }	 		                
			            }
			            ]
			        }),
                    border:false
                   
                },{
                    title:'Transferencias',
                    contentEl:'transferencias', //le decimos que el contenido del div con id "transferencias" lo contenga el panel
                    border:true
                },{
                    title:'Compensaciones',
                    contentEl:'compensaciones', //le decimos que el contenido del div con id "transferencias" lo contenga el panel
                    border:true
                },{
                    title:'Búsqueda',
                    contentEl:'busquedas',
                    border:false
                }] 
	       
	    },{
	  	    id: 'tab_panel',
			region: 'center',
			split: true,                 
	        xtype: 'tabpanel', // El panel del centro va a ser un Tab panel(aunque puede ser cualquier layout)
			activeTab: 0,	
					
	        items: {
	        	id:'tab_inicial',
	            title: 'INICIO',
	            autoScroll:true,
	            contentEl: 'inicio'
	        }
	    }]
	});


		


		// el combobos hace referencia al id del combo en html que se quiera transformar
		new Ext.form.ComboBox({
		    id:'comboBusquedasExt',
		    transform:'busquedaRapida',
		    triggerAction: 'all'

		});
     
   //el boton fuinciona igual al combo
	      new  Ext.Button({
	      	   id:'botonBuscarExt',
	      	   text:'Buscar',
	      	   width:'60',	      	                
	      	   applyTo:'botonBuscar',
	      	   listeners:{
		            'click': function(){
		            
		            	            	if(Ext.getCmp('fechaBusquedaExt').value==null){
		            	
						            	 Ext.MessageBox.alert('Estatus','Selecciona una fecha' );
					 					 return;
						            	 }
		            	
									     addTabBusqueda('TemplateAction.action','parameter=busqueda&banco='+Ext.getCmp('comboBusquedasExt').value+'&importe='+Ext.getDom('importeBusqueda').value+'&fecha='+Ext.getCmp('fechaBusquedaExt').value);
		            }
		    	}
	      });

	//Comenzamos los hilos
	
	<c:if test="${sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2}">
	
	
		Ext.TaskMgr.start(pendientesTask);
		Ext.TaskMgr.start(enProcesoTask);
		Ext.TaskMgr.start(aplicadasTask);
		Ext.TaskMgr.start(errorTask);
		
	 </c:if>
	 
	  <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
	  
	  	var regresadasTask = {
		    run: function(){
		        ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=pendientes&idEstatus=8','regresadas','...'); 
		    },
		    interval: 120000 //60   
		}
	  
	  Ext.TaskMgr.start(regresadasTask);
	  </c:if>
	 
 	new  Ext.form.DateField({
			id:'fechaBusquedaExt',
 			renderTo:'fechaBusqueda',
 			name:'fecha',
 			editable:true		
 		});
 
 
 	new Ext.Panel({
 			title:'Bienvenido',
 			renderTo:'panelBienvenida',
 			html:'<br/><br/><div align="center" >Portal de Transferencias</div>',
 			width:300,
 			layout:'fit',
 			height:100
 	
 	
 	})
 
 	new Ext.ToolTip({        
        title: 'Busqueda Rapida',
        id: 'TipAyudaBusRapida',
        target: 'AyudaBusRapida',
        anchor: 'left',
        html: null,
        width: 415,
        autoHide: false,
        closable: true,
        contentEl: 'tip-busquedaRapida' // load content from the page
    });
 
  });
</script>
    <title>Portal de Transferencias</title>
  </head>
  <body>
    <div id="container">
      <div id="encabezado">
        <div id="encabezado-izq">
          <div id="encabezado-der">
            <div id="encabezado-medtop">
              <img src="img/logo_telcel.gif"/>
            </div>
            <div id="encabezado-med"> (<c:out value="${sessionScope.empleado.usuario}"/>)<c:out value="${sessionScope.empleado.nombre}"/> - <c:out value="${sessionScope.empleado.descripcionPerfil}"/>  </div>
          </div>
        </div>
      </div>
      <div id="main">
             
        <div id="contenido">
          
        </div>

       <div align="left" id="transferencias">  
        
       <table>
   	  <c:if test="${sessionScope.empleado.idPefril!=3 || sessionScope.empleado.idPefril!=5}">
					
       	<tr height="22" >
       		<td><a href="javascript:addPanel('listaPendientes','TiempoReal.action','tipo=pendiente','autoload')"  class="x-form-item" style="font-size:11px" >Pendientes:</a></td>   
       		<td><span id="pendientes">0</span></td>    	
       	</tr>
       	<tr height="22" >
       		<td><a href="javascript:addPanel('listaEnProceso','TiempoReal.action','tipo=proceso','autoload')" class="x-form-item" style="font-size:11px" > En proceso:</a></td>   
       		<td><span id="proceso">0</span></td>    	
       	</tr>
       	<tr height="22">
       		<td><a href="javascript:addPanel('listaAplicadas','TiempoReal.action','tipo=aplicadas','autoload')" class="x-form-item" style="font-size:11px" >Aplicadas:</a></td>   
       		<td><span id="aplicadas">0</span></td>    	
       	</tr>
       	<tr  height="22" >    
       		<td><a href="javascript:addPanel('listaError','TiempoReal.action','tipo=error','autoload')" class="x-form-item" style="font-size:11px" >Error:</a></td>   
       		<td><span id="error">0</span></td>    	
       	</tr>
     </c:if>
      <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==5}">
        <tr  height="22" >    
       		<td><a href="javascript:addPanel('transferenciasByUsuario','TemplateAction.action?regresada=true','tipo=regresa','autoload');" class="x-form-item" style="font-size:11px" >Regresadas:</a></td>   
       		<td><span id="regresadas">0</span></td>    	
       	</tr>
      
       </c:if>
       </table>
                                              
         
        </div>
        <div align="left" id="compensaciones">  
        
       <table>
   	 <c:if test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=4 && sessionScope.empleado.idPefril!=5}">
					
       	<tr height="22" >
       		<td><a href="javascript:addPanel('listaCompensacionesPendientes','TiempoReal.action','tipo=pendiente','autoload')"  class="x-form-item" style="font-size:11px" >Pendientes:</a></td>   
       		<td><span id="pendientes">0</span></td>    	
       	</tr>
       	<tr height="22" >
       		<td><a href="javascript:addPanel('listaCompensacionesEnProceso','TiempoReal.action','tipo=proceso','autoload')" class="x-form-item" style="font-size:11px" > En proceso:</a></td>   
       		<td><span id="proceso">0</span></td>    	
       	</tr>
       	<tr height="22">
       		<td><a href="javascript:addPanel('listaCompensacionesAplicadas','TiempoReal.action','tipo=aplicadas','autoload')" class="x-form-item" style="font-size:11px" >Aplicadas:</a></td>   
       		<td><span id="aplicadas">0</span></td>    	
       	</tr>
       	<tr  height="22" >    
       		<td><a href="javascript:addPanel('listaCompensacionesError','TiempoReal.action','tipo=error','autoload')" class="x-form-item" style="font-size:11px" >Error:</a></td>   
       		<td><span id="error">0</span></td>    	
       	</tr>
     </c:if>
       </table>
                                              
         
        </div>
        
       <div align="left" id="busquedas">  
        
	   <table>
		   	<tr>
		   		<td>
		   		  
		   			<select id="busquedaRapida">
		   			<c:forEach var="banco" items="${listaBancos}">
		   			<option value="${banco.idbanco}" >${banco.nombre}</option>
		   		    </c:forEach>
   					</select>
		   		
		   		</td>
		   		<td>
		   			&nbsp;&nbsp;<img src="img/help.png" id="AyudaBusRapida">
		   		</td>
		   	</tr>
	   	   	<tr>
		   		<td colspan="2"> <div id="fechaBusqueda"></div>  </td>
		   	</tr>
		   	<tr>
		   		<td colspan="2"><input type="text" class="x-form-text x-form-field"  id="importeBusqueda"  value=""  /></td>
		   	</tr>
		   	<tr>
		   		<td colspan="2" align="center" ><div id="botonBuscar" /></td>
		   	</tr>	   
	   </table>
   		
   		
   		
  		<div id="dsplay_none" style="display:none"></div>
        
        </div>
      </div>
      <div id="pie_pagina"><div class="blue"><a href="http://www.telcel.com.mx">® TELCEL</a></div>
      </div>
    </div>
    <div id="inicio"  >
    <div style="padding:50px;margin:70px" id="panelBienvenida" ></div>
    </div>
  
  	<!-- Esta es la ayuda de Busqueda Rapida -->
	<div style="display:none;">
        <div id="tip-busquedaRapida" >
            Para conocer el estatus de una transferencia de manera rapida capture los datos de la misma y de clic en Buscar.
            <br/><br/>
            Los datos a capturar en orden son:
            <ul>
            	<li><b>Banco:</b> Seleccione el Banco de la transferencia. </li>
            	<li><b>Fecha:</b> Capture o seleccione la Fecha de la transferencia (dd/mm/yyyy). </li>
            	<li><b>Importe(Opcional):</b> Capture el importe de la transferencia. </li>
            </ul>
            <br/>
            <i>Tip: Puede capturar solamente la primer parte del importe y el Portal le mostrara todas las similitudes encontradas.</i>
        </div>
    </div>
	
  </body>
</html>
