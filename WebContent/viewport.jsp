<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
     <link rel="stylesheet" href="recursos/completa.css" type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" />
	<script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="ext/ext-all.js"></script>
	<script type="text/javascript" src="ext/general.js"></script>
	<script type="text/javascript" src="ext/locale/ext-lang-es.js"></script>
	<script type="text/javascript" src="ext/anychart-base.min.js"></script>
	<script type="text/javascript" src="ext/anychart-exports.min.js"></script>
	<script type="text/javascript" src="ext/anychart-ui.min.js"></script>
	<style type="text/css">
        #the-table-detalleDesglose { border:1px solid #bbb;border-collapse:collapse; }
        #the-table-detalleDesglose td,#the-table-detalleDesglose th { border:1px solid #ccc;border-collapse:collapse;padding:5px; }
        
        #the-table-h { border:1px solid #bbb;border-collapse:collapse; }
        #the-table-h td,#the-table-h th { border:1px solid #ccc;border-collapse:collapse;padding:5px; }
        
        
    </style>
	
	
	
	
	<style type="text/css">
        .lista { border:1px solid #bbb;border-collapse:collapse; }
        .lista td,.lista th { border:1px solid #ccc;border-collapse:collapse;padding:5px; }
    </style>
	
	<style>
	
		.excel {
    background-image: url(img/page_excel.png) !important;
	}
	.word {
    background-image: url(img/page_word.png) !important;
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
 
 //alert('<c:out value="${sessionScope.empleado.idPefril}"/>');

 	 // Variables iniciales
 	 
 	 //Si esta imagen no se referencia, la toma de la red
 	 Ext.BLANK_IMAGE_URL = 'ext/resources/images/default/s.gif';
 	 //para que el autoload permita los scripts de ext
 	 Ext.Updater.defaults.loadScripts=true;
 	 //para que cada peticiï¿½n se le agrege un numero irrepetible(para IE), esto deshabilita el cache	 
 	 Ext.Updater.defaults.disableCaching=true;
 	 //Para modificar el timeout de las peticiones en segundos 	 
 	 Ext.Updater.defaults.timeout=180;
//Hilos o tareas para actualizacion en tiempo real	
	var pendientesTask = {
		    run: function(){
		        ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=pendientes&idEstatus=3','pendientes','...','false'); 
		    },
		    interval: 240000 //60   
		}
	var enProcesoTask = {
	    run: function(){
	         ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=proceso&idEstatus=5','proceso','...','false'); 
	    },
	    interval: 240000 //60 
	}
	var aplicadasTask = {
	    run: function(){
	        ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=aplicadas&idEstatus=7','aplicadas','...','false'); 
	    },
	    interval: 24000 //60 
	}	
	var errorTask = {
	    run: function(){
	       ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=error&idEstatus=4','error','...','false'); 
	    }, 
	    interval: 243000 //60 
	}	
	var compensacionesPendientesTask = {
		    run: function(){
		        ajaxDivUpdater('TiempoReal.action','parameter=random&tipo=compensacion&consulta=pendientes&idEstatus=7','compensacionesPendientes','...','false'); 
		    },
		    interval: 24000 //60   
		}
	var compensacionesEnProcesoTask = {
	    run: function(){
	         ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=proceso&idEstatus=18','compensacionesProceso','...','false'); 
	    },
	    interval: 24000 //60 
	}
	var compensacionesAplicadasTask = {
	    run: function(){
	        ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=aplicadas&idEstatus=17','compensacionesAplicadas','...','false'); 
	    },
	    interval: 24000 //60 
	}	
	var compensacionesErrorTask = {
	    run: function(){
	       ajaxDivUpdater('TiempoReal.action','parameter=random&consulta=error&idEstatus=19','compensacionesError','...','false'); 
	    }, 
	    interval: 24000 //60 
	}	

	

// La funcion onReady espera a que todo el DOM(Html) se carge completamente, despues de eso podemos renderear paneles	
 Ext.onReady(function(){


	mascara(false);
	// para que los tooltips funcionen
	Ext.QuickTips.init();


	//Creamos un nuevo panel	
	new Ext.Viewport({ 
		id:'viewport',
	  //  renderTo: document.body,	//Le decimos en que div se va a renderear
	   // width: 900,
	   // height: 450,       
	    layout: 'border',	//aqui le decimos que sea de tipo border(norte,sur,este,oeste)
	    
	    //este panel va atener varios hijos o items, cada uno se le espesifica el lugar en donde estara con "region"
	    //<c:out value="${sessionScope.empleado.idPefril}" />
	    items: [{
		        region: 'north',
		        contentEl: 'encabezado-med',
		        autoHeight: true,
		        border: false,
		        margins: '0 0 5 0'
		    },{	
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
			        colapsible:true,
			            children: [
			            
			            {
			                text: 'Carga Transferencia',
			                leaf: true,			               
			                iconCls:'carga',
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
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
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
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
			                text: 'Dividir Transferencia', 
			                iconCls:'autorizar',
			                leaf: true,
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
					        disabled:true,
							hidden:true,
					  		</c:if>
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('dividirTransferencia','TemplateAction.action');
						            }
						        }
			            },{
			                text: 'Aplica Transferencia',
			                leaf: true,
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
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
			                text: 'Compensacion SAP',
			                leaf: false,
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			            	children:[
				            			{text: 'Cargar numero de documento',
							             leaf: true,
							             iconCls:'carga',
						                 listeners: {
										            click: function(n) {
										              //  Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										               addPanel('cargarNumeroDocumento','TemplateAction.action');
										            }
										        }
							            	
							            	
							           	},{
				            			 text: 'Compensar transferencias',
							             leaf: true,
							             iconCls:'aplicar',
							             listeners: {
										            click: function(n) {
										               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										                 addPanel('compensarTransferencias','TemplateAction.action');
										            }
										      }
							           	}
						           	]
			            },{
			                text: 'Bandeja de entrada',
			                leaf: true,
			                iconCls:'correo',
			                <c:if test="${sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 }">
					        disabled:true,
							hidden:true,
					  		</c:if>
			                listeners: {
						            click: function(n) {
						               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
						               addPanel('getBandeja','TemplateAction.action')
						            }
						        }
						        
			           	},{
			                text: 'Desglose',
			                leaf: false,
			                <c:if test="${sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==4}">
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
							             <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
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
							             <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
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
							             <c:if test="${sessionScope.empleado.idPefril==4}">
									     disabled:true,
										 hidden:true,
									  	 </c:if>
							             iconCls:'barras',
							                listeners: {
										            click: function(n) {
										              //  Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										               addPanel('transferenciasDesglosadas','Reportes.action');
										            }
										        }
							            	
							            	
							           	},{
								           	 text: 'Transferencias por Usuario',
								             leaf: true, 
								             <c:if test="${sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==2}">
										     disabled:true,
											 hidden:true,
										  	 </c:if>							
								             iconCls:'barras',
								                listeners: {
											            click: function(n) {
											               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
											                 addPanel('transferenciasByUser','Reportes.action');
											            }
											        }	
								         },{
									           	 text: 'Transferencias Rechazadas',
									             leaf: true, 
									             <c:if test="${sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==4}">
											     disabled:true,
												  hidden:true,
											  	 </c:if>		
									             iconCls:'barras',
									                listeners: {
												            click: function(n) {
												               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
												                 addPanel('transferenciasRechazadas','Reportes.action');
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
							            	
							            	
							           	},{
				            			 text: 'Reporte de Pagos No Identificados',
							             leaf: true, 
							             <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
									     disabled:true,
										  hidden:true,
									  	 </c:if>							
							             iconCls:'barras',
							                listeners: {
										            click: function(n) {
										               // Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
										                 addPanel('transferenciasByPagosNoIdent','Reportes.action');
										            }
										        }
							            	
							            	
							           	},{
							           		text: 'Reporte por ciclo',
								             leaf: true, 
								             <c:if test="${sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 }">
										     disabled:true,
											  hidden:true,
										  	 </c:if>		
								             iconCls:'barras',
								                listeners: {
											            click: function(n) {
											                 addPanel('reporteCiclo','Reportes.action');
											            }
											        }
								            	
								            	
										},{
											text: 'Reporte de peticiones',
								             leaf: true, 
								             <c:if test="${sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 }">
										     disabled:true,
											  hidden:true,
										  	 </c:if>		
								             iconCls:'barras',
								                listeners: {
											            click: function(n) {
											                 addPanel('reportePeticiones','Reportes.action');
											            }
											        }
								            	
								            	
										},{
							           		text: 'Validador de Cuentas',
								             leaf: true,
								             <c:if test="${sessionScope.empleado.idPefril!=5}">
										     disabled:true,
											  hidden:true,
										  	 </c:if>
								             iconCls:'barras',
								                listeners: {
											            click: function(n) {
											              //  Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
											               addPanel('validadorCuentas','TemplateAction.action');
											            }
											        }
								            	
								            	
								           	},{
												text: 'Reporte Trans Ingresos',
									             leaf: true, 
									             <c:if test="${sessionScope.empleado.idPefril!=5}">
											     disabled:true,
												  hidden:true,
											  	 </c:if>		
									             iconCls:'barras',
									                listeners: {
												            click: function(n) {
												                 addPanel('reporteTransIngresos','Reportes.action');
												            }
												        }
									            	
									            	
											}
						           	]
			            },{
			                text: 'Administración de peticiones',
			                leaf: true,			               
			                iconCls:'administrador',
			                <c:if test="${(sessionScope.empleado.esJefe!=1 ) || (sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4)}">
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
			                <c:if test="${(sessionScope.empleado.esJefe!=1 ) || (sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4)}">
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
			                <c:if test="${sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			                listeners: {
						            click: function(n) {
										window.open('<%=request.getContextPath()%>/formatos/DesgloseTrans.xls','name','height=200,width=150');																             
						            } 
						        }	 		                
						        
						},{
			                text: 'Formato Pagos',
			                leaf: true,			               
			                iconCls:'excel',
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			                listeners: {
						            click: function(n) {
										window.open('<%=request.getContextPath()%>/formatos/FormatoPagos.xls','name','height=200,width=150');																             
						            } 
						        }	 		                
						        
						},{
			                text: 'Ayuda Corporativo',
			                leaf: true,			               
			                iconCls:'word',
			                <c:if test="${sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==4}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			                listeners: {
						            click: function(n) {
										window.open('<%=request.getContextPath()%>/formatos/ManualCorpo.doc','name','height=200,width=150');																             
						            } 
						        }	 		                
						        
						},{
			                text: 'Ayuda Finanzas',
			                leaf: true,			               
			                iconCls:'word',
			                <c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
							 disabled:true,
							 hidden:true,
							</c:if>	
			                listeners: {
						            click: function(n) {
										window.open('<%=request.getContextPath()%>/formatos/ManualFin.doc','name','height=200,width=150');																             
						            } 
						        }	 		                
						        
						},{
			                text: 'Usuarios',
			                leaf: true,
			                iconCls:'usuarios',
			                <c:if test="${sessionScope.empleado.esJefe!=1 || sessionScope.empleado.idPefril==4}">
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
                    contentEl:'compensaciones', //le decimos que el contenido del div con id "compensaciones" lo contenga el panel
                    border:true
                },{
                    title:'Bï¿½squeda',
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
		Ext.TaskMgr.start(compensacionesPendientesTask);
		Ext.TaskMgr.start(compensacionesEnProcesoTask);
		Ext.TaskMgr.start(compensacionesAplicadasTask);
		Ext.TaskMgr.start(compensacionesErrorTask);
		
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
 			html:null,
 			layout:'fit',
 			contentEl:'AyudaInicio',
 			collapsible:true
 	})
 	
 	<c:if test="${sessionScope.empleado.idPefril==2}">
 	new Ext.Panel({
 			title:'Ayuda',
 			renderTo:'FAQ',
 			html:null,
 			layout:'fit',
 			contentEl:'AyudaFin',
 			collapsible:true
 	}) 	
 	</c:if>
 
    <c:if test="${sessionScope.empleado.idPefril==3 ||sessionScope.empleado.idPefril==4 || sessionScope.empleado.idPefril==5}">
 	new Ext.Panel({
 			title:'Ayuda',
 			renderTo:'FAQ',
 			html:null,
 			layout:'fit',
 			contentEl:'AyudaCor',
 			collapsible:true
 	}) 	
 	</c:if>
 	
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
    <title>TIC - Transferencias Interbancarias Corporativas</title>
  </head>
  <body>
    <div id="container">
      <div id="encabezado">
        <div id="encabezado-izq">
          <div id="encabezado-der">
            <div id="encabezado-medtop">
            
            </div>
            <div id="encabezado-med">
            	<h1 class="x-panel-header"> 
     				Transferencias Interbancarias Corporativas: (<c:out value="${sessionScope.empleado.numeroEmpleado}"/>)<c:out value="${sessionScope.empleado.nombre}"/> - <c:out value="${sessionScope.empleado.descripcionPerfil}"/> 
            	</h1>
            </div>
          </div>
        </div>
      </div>
      <div id="main">
             
        <div id="contenido">
          
        </div>

       <div align="left" id="transferencias">  
        
	       <table>
	   	  <c:if test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=4 && sessionScope.empleado.idPefril!=5}">
						
	       	<tr height="22" >
	       		<td><a href="javascript:addPanel('listaPendientes','ListaTransferencias.action','tipo=pendiente','autoload')"  class="x-form-item" style="font-size:11px" >Pendientes:</a></td>   
	       		<td><span id="pendientes">0</span></td>    	
	       	</tr>
	       	<tr height="22" >
	       		<td><a href="javascript:addPanel('listaEnProceso','ListaTransferencias.action','tipo=proceso','autoload')" class="x-form-item" style="font-size:11px" > En proceso:</a></td>   
	       		<td><span id="proceso">0</span></td>    	
	       	</tr>
	       	<tr height="22">
	       		<td><a href="javascript:addPanel('listaAplicadas','ListaTransferencias.action','tipo=aplicadas','autoload')" class="x-form-item" style="font-size:11px" >Aplicadas:</a></td>   
	       		<td><span id="aplicadas">0</span></td>    	
	       	</tr>
	       	<tr  height="22" >    
	       		<td><a href="javascript:addPanel('listaError','ListaTransferencias.action','tipo=error','autoload')" class="x-form-item" style="font-size:11px" >Error:</a></td>   
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
       		<td><a href="javascript:addPanel('listaCompensacionesPendientes','ListaTransferencias.action','tipo=pendiente','autoload')"  class="x-form-item" style="font-size:11px" >Pendientes:</a></td>   
       		<td><span id="compensacionesPendientes">0</span></td>    	
       	</tr>
       	<tr height="22" >
       		<td><a href="javascript:addPanel('listaCompensacionesEnProceso','ListaTransferencias.action','tipo=proceso','autoload')" class="x-form-item" style="font-size:11px" > En proceso:</a></td>   
       		<td><span id="compensacionesProceso">0</span></td>    	
       	</tr>
       	<tr height="22">
       		<td><a href="javascript:addPanel('listaCompensacionesAplicadas','ListaTransferencias.action','tipo=aplicadas','autoload')" class="x-form-item" style="font-size:11px" >Compensadas:</a></td>   
       		<td><span id="compensacionesAplicadas">0</span></td>    	
       	</tr>
       	<tr  height="22" >    
       		<td><a href="javascript:addPanel('listaCompensacionesError','ListaTransferencias.action','tipo=error','autoload')" class="x-form-item" style="font-size:11px" >Error:</a></td>   
       		<td><span id="compensacionesError">0</span></td>    	
       	</tr>
     </c:if>
      <c:if test="${sessionScope.empleado.idPefril!=3 && sessionScope.empleado.idPefril!=4 && sessionScope.empleado.idPefril!=5}">
        <tr  height="22" >    
       		<td><a href="javascript:addPanel('transferenciasByUsuario','TemplateAction.action?regresada=true','tipo=regresa','autoload');" class="x-form-item" style="font-size:11px" >Regresadas:</a></td>   
       		<td><span id="regresadas">0</span></td>    	
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
      <div id="pie_pagina"><div class="blue"><a href="http://www.telcel.com.mx">ï¿½ TELCEL</a></div>
      </div>
    </div>
    <div id="inicio"  >
    <div style="padding:10px;" id="panelBienvenida" ></div>
    <div style="padding:10px;" id="FAQ"> </div>
    </div>
    <!-- Esta es la ayuda de inicio del portal -->
	<div style="display:none;"> 
		<!-- Guia inicial -->
	  	<div id="AyudaInicio" style="background-image: url('img/logbg.jpg');background-position: right;background-repeat: repeat-y;"> 
	  		<div align="left" style="padding-left: 5px; font-size: 11px;" >
	  			<div style="width: 100%;height: 75px; background-image: url('img/telcellog.jpg');background-position: right;background-repeat: no-repeat;">
		  			<b style="color: navy;">Transferencias Interbancarias Corporativas</b>
		  			<br/><br/>
	  				Bienvenido <c:out value="${sessionScope.empleado.nombre}"/> - <c:out value="${sessionScope.empleado.puesto}"/>.
	  				<br/><br/>
	  				A continuacion te mostramos la guia rapida del Portal:	
	  			</div>
	  			<div style="padding: 0px 15px 5px 5px;">		  				  					  			
		  			<c:if test="${sessionScope.empleado.idPefril==2 || sessionScope.empleado.idPefril==1 }">
		  			<b style="color: navy;"> 1. Cargar Transferencias: </b> Para cargar las transferencias una entre al menu Carga Transferencia, seleccione el archivo y banco y de clic en Cargar, si quiere autorizarlas automaticamente selecciona la opcion de Autorizar Automatico.
		  			<br/> <br/>
		  			<b style="color: navy;"> 2. Autoriza Transferencias: </b> Para autorizar una transferencia hay dos opciones:
		  				<br>&nbsp;&nbsp;&nbsp; a) Entre al menu Autoriza Transferencia, proporcione el banco y fecha(Fecha Ini) de la(s) Transferencia(s) y de clic en Buscar, seleccione la(s) Transferencia(s)  a autorizar y de clic en Autorizar.
		  				<br>&nbsp;&nbsp;&nbsp; b) Busque la transferencias en la opcion de busqueda en la parte inferior del menu, entre al detalle de la transferencia(VER) y de clic en Autorizar.
		  			<br/> <br/>
		  			<b style="color: navy;"> 3. Aplica Transferencias: </b> Para aplicar las transferencias hay dos opciones:
			  			<br>&nbsp;&nbsp;&nbsp; a) Entre al menu Aplica Transferencia, proporcione el banco y fecha(Fecha Ini) de la(s) Transferencia(s) y de clic en Buscar, seleccione la(s) Transferencia(s)  a aplicar y de clic en Programar.
		  				<br>&nbsp;&nbsp;&nbsp; b) De clic sobre Pendientes del menu de Transferencias, seleccione la(s) Transferencia(s)  a aplicar y de clic en Programar.
		  			<br/> <br/>
		  			<b style="color: navy;"> 4. Error </b> Aqui se muestran las transferencias que tuvieron algun problema en su aplicaciï¿½n en MOBILE, por lo que tienen que ser revisadas.
		  			<br/> <br/>
		  			<b style="color: navy;"> 5. Conocer el Avance de una Transferencia: </b> Para conocer el avance de una Transferencia use la opcion de busqueda en la parte inferior del menu introduciendo el banco, fecha e importe de la transferencia, el importe es opcional. 
		  			<br/> <br/>
		  			<b style="color: navy;"> 6. Informacion Adicional: </b> Si tiene mas dudas acerca del funcionamiento del portal descargue el manual Ayuda Finanzas desde el menu.
		  			</c:if>
		  			
		  			
		  			<c:if test="${sessionScope.empleado.idPefril==3 || sessionScope.empleado.idPefril==1 || sessionScope.empleado.idPefril==5}">
		  			 <b style="color: navy;"> 1. Tomar Transferencia: </b> Para tomar una entre al menu Desglosar->Tomar Transferencia, proporciona el Banco y Fecha(Fecha Ini) de la transferencia y da clic en Buscar, marque las transferencias que quiera tomar y proporcione una referencia y para finalizar de clic en Tomar.
		  			 <br/> <br/>
		  			 <b style="color: navy;"> 2. Desglosar Transferencia: </b> Para tomar una transferencia entre al menu Desglosar->Desglosar Transferencia, despues de tomar las transferencias estan le apareceran en esta seccion, de clic en VER para entrar al detalle donde le permitira hacer el desglose, este puede ser ingresando cuentas individualmente o por archivo usando el Formato Desglose que puede descargar del menu.
		  			 <br/> <br/>
		  			 <b style="color: navy;"> 3. Conocer el Avance de una Transferencia: </b> Para conocer el avance de una Transferencia use la opcion de busqueda en la parte inferior del menu introduciendo el banco, fecha e importe de la transferencia, el importe es opcional. 
		  			 <br/> <br/>
		  			 <b style="color: navy;"> 4. Regresadas: </b> En esta seccion se muestran todas las transferencias que le fueron regresadas por parte de Finanzas para su revision, para conocer el motivo consulte el historial de la transferencia.
		  			 <br/> <br/>
		  			 <b style="color: navy;"> 4. Informacion Adicional: </b> Si tiene mas dudas acerca del funcionamiento del portal descargue el manual Ayuda Corporativo desde el menu.
		  			</c:if>	  			
		  			<br/>&nbsp;
		  		</div>
	  		</div>	  			  		
	  	</div>
	  	<!-- Guia Finanzas -->
	  	<div id="AyudaFin" style="background-image: url('img/logbg.jpg');background-position: right;background-repeat: repeat-y;">
	  		<div style="padding: 0px 15px 5px 5px;font-size: 11px;">
	  			<b style="color: navy;">Transferencias en ERROR</b>
	  			<br/><br/>
	  			Cuando existan transferencias que hayan quedado en ERROR hacer lo siguiente:
	  			<br/><br/>
	  			1. Entrar a la lista de transferencia en error.
	  			<br/><br/>
	  			2. Entrar al detalle de cada transferencia y revisar su historial, el cual tiene el mensaje de mobile por region.
	  			<br/><br/>
	  			3. Si el error es por alguna cuenta que no acepta MOBILE o algo causado por el desglose de la transferencia tiene que ser regresada al Ejecutivo para su revision, el ejecutivo la vera en sus desglosadas en aprox 15 min. 
	  			<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp; <i>Detalle Sistema: El sistema validara que si la transferencia quedo con lotes abiertos por el error estos seran eliminados, los lotes cerrados no tendran modificacion,  al regresarla el ejecutivo solo podra modificar la informacion de las cuentas que NO esten ya aplicadas. </i>
	  			<br/><br/>
	  			4. Si es un error por otra causa se tiene que dar clic en el boton de Reintentar para volver a enviar el movimiento.	  			
	  			<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp; <i>Detalle Sistema: El sistema cerrara lotes que se hayan quedado abiertos, eliminara lotes incompletos y reintentara ingresar las cuentas a un lote nuevo.</i>
	  			<br/><br/>
	  			5. Si despues del reintento no queda aplicada y no es por motivos de desglose reportar la transferencia a sistemas para su revisiï¿½n a detalle.
	  			<br/><br/>
	  		</div>
	  	</div>
	  	<!-- Guia Corporativo -->
	  	<div id="AyudaCor" style="background-image: url('img/logbg.jpg');background-position: right;background-repeat: repeat-y;">
	  		<div style="padding: 0px 15px 5px 5px;font-size: 11px;">
	  			<br/>
	  			<b style="color: navy;">No puedo tomar una Transferencia.</b>
	  			<br/><br/>
	  			En caso de que no aparezca una transferencia que se quiere tomar, consulta la transferencia y verifica lo siguiente:
	  		    <br/><br/>
	  			1. La transferencia debe estar en estatus Autorizada para que pueda ser tomada, si esta en estatus de Cargada comunicarse con Finanzas para que la autorice. 
	  			<br/><br/>
	  			2. Si la transferencia esta en estatus Autorizada y no se puede tomar, entrar al detalle  y revisar en el historial si otro ejecutivo ya la tomo, en ese caso comunicarse con el ejecutivo y pedir que la libere.
	  			<br/><br/>
	  			<br/>
	  			<b style="color: navy;">Significado de los estatus.</b>
	  			<br/>
	  			<br/>CARGADA. La transferencia fue ingresada al sistema, esta en espera de ser autorizada por Finanzas.
	  			<br/>AUTORIZADA. La transferencia fue autorizada por Finanzas y esta en espera de ser tomada por algun ejecutivo.
	  			<br/>DESGLOSADA. La transferencia ya fue desglosada, esta en espera de ser autorizada por Finanzas para su aplicacion en MOBILE.
	  			<br/>EN PROCESO. La transferencia esta en espera para ser aplicada en MOBILE por parte del sistema, esta acciï¿½n tardara algunos minutos (aprox 20 min).
	  			<br/>ERROR. La transferencia no se aplico correctamente, esta en espera de acciï¿½n por parte de Finanzas.
	  			<br/>APLICADA. La transferencia ya esta aplicada correctamente.
	  			<br/>CERRADA. La transferencia fue cerrada por Finanzas, ya no se puede trabajar por el sistema.
	  			<br/><br/><br/>
	  			<b style="color: navy;">La transferencia ya esta como Aplicada pero no se ve reflejada en MOBILE.</b>
	  			<br/><br/>
	  			Consultar la transferencia  y revisar el tipo de los pagos, para que los pagos se reflejen en MOBILE deben estar como CU, en caso de estar como FA comunicarse con Finanzas para que  autorice modificar la transferencia para su correcciï¿½n.
	  			<br/><br/>
	  		</div>
	  	</div>
	  	
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
