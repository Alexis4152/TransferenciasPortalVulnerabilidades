


	
	function ajaxDivUpdater(url1,parametros,div){
		  try{
				Ext.get(div).load({
				        url: url1,
				        scripts: true,
				        params: parametros+'&numero='+Math.random(),
				        text: 'Cargando...',
					    callback:function(el,success,response){
				        	
				        	if(success!=true){
					        	 mascara(false);
					        	 Ext.MessageBox.alert(response.status,response.statusText);
				        	 }
				        	}
				});
				
		}catch(e){
		
			alert(e);
		
		}
	
	    
	}
	function ajaxDivUpdater(url1,parametros,div,text,error){
	   
			Ext.get(div).load({
			        url: url1,
			        scripts: true,
			        params: parametros+'&numero='+Math.random(),
			        text: text,
			        callback:function(el,success,response){
				        	
				        	if(success!=true){
				        	 mascara(false);
				        	 if(error==null)
				        	 Ext.MessageBox.alert(response.status,response.statusText);
				        	 }
				        	}
				      
			});
	
	
	    
	}	
	
	
	 /*
 	 *Funcion que añade un tabpanel 
 	 */
	function addPanel(id,action,parametros,cargar){
	
		if(parametros==null)parametros='';
		//bajamos el componente por su id. si a un objeto no se le especifica el id ext genera uno por default
		var tabPanel=Ext.getCmp('tab_panel');
	
		//checamos que el componente(panel) exista
		if(tabPanel!=null){
		
			//buscamos en todos sus hijos un panel que tenga el id dado
			var pes=tabPanel.findById(id);
			
			//si no existe añadimos un tabpanel
			if(pes==null){
			
			tabPanel.add({	//usamos notacion json
						   id:id,
						   title:'---',		                 
		                   border:false,
		                   closable:true,
		                   autoScroll:true,     
		                   autoLoad:{
		                   	    url:action,
		                   	    //el párametro "parameter" hace referencia a un metodo de Dispatch action
							    params:parametros+'&parameter='+id,
							    callback:function(el,success,response){
				        	
				        	if(success!=true){
					        	 mascara(false);
					        	 Ext.MessageBox.alert(response.status,response.statusText);
				        	 }
				        	}		                   
		                   }
						
						}).show();
			
			}else{
			
			// solo volvemos a mostrar la pestaña
			if(cargar==null){
			pes.show();
			}
			else{
			pes.destroy();
			tabPanel.add({	//usamos notacion json
						   id:id,
						   title:'---',		                 
		                   border:false,
		                   closable:true,
		                   autoScroll:true,     
		                   autoLoad:{
		                   	    url:action,
		                   	    //el párametro "parameter" hace referencia a un metodo de Dispatch action
							    params:parametros+'&parameter='+id,
								callback:function(el,success,response){
						        	
						        	if(success!=true){
							        	 mascara(false);
							        	 Ext.MessageBox.alert(response.status,response.statusText);
						        	 }
					        	}		                   
		                   }
						
						}).show();			
			
			}
			
			
			}
			
		
		}
	
	}
	/*
	*	Funcion que añade el detalle de la transferencia,solo puede existir un detalle a la vez
	*/
	function addTabDetalle(action,parametros){
	
			//bajamos el componente por su id. si a un objeto no se le especifica el id ext genera uno por default
		var tabPanel=Ext.getCmp('tab_panel');
	
		//checamos que el componente(panel) exista
		if(tabPanel!=null){
		
			//buscamos en todos sus hijos un panel que tenga el id dado
			var pes=tabPanel.findById('detalle');
			
			//si no existe añadimos un tabpanel
			if(pes==null){
			
			tabPanel.add({	//usamos notacion json
						   id:'detalle',
						   title:'Detalle Transferencia',		                 
		                   border:false,
		                   closable:true,
		                   autoScroll:true,     
		                   autoLoad:{
		                   	    url:action,
		                   	    //el párametro "parameter" hace referencia a un metodo de Dispatch action
							    params:parametros,
							    callback:function(el,success,response){
						        	
						        	if(success!=true){
							        	 mascara(false);
							        	 Ext.MessageBox.alert(response.status,response.statusText);
						        	 }
					        	}			                   
		                   }
						
						}).show();
			
			}else{
			
			// Removemos el tab y añadimos el nuevo
			pes.destroy();
			tabPanel.add({	//usamos notacion json
						   id:'detalle',
						   title:'Detalle Transferencia',		                 
		                   border:false,
		                   closable:true,
		                   autoScroll:true,     
		                   autoLoad:{
		                   	    url:action,
		                   	    //el párametro "parameter" hace referencia a un metodo de Dispatch action
							    params:parametros,
							    callback:function(el,success,response){
						        	
						        	if(success!=true){
							        	 mascara(false);
							        	 Ext.MessageBox.alert(response.status,response.statusText);
						        	 }
					        	}		                   
		                   }
						
						}).show();
			
			}
			
		
		}
	
	
	}
	/*
	*	Funcion que añade el tab busqueda,solo puede existir un detalle a la vez
	*/
	function addTabBusqueda(action,parametros){
	
			//bajamos el componente por su id. si a un objeto no se le especifica el id ext genera uno por default
		var tabPanel=Ext.getCmp('tab_panel');
	
		//checamos que el componente(panel) exista
		if(tabPanel!=null){
		
			//buscamos en todos sus hijos un panel que tenga el id dado
			var pes=tabPanel.findById('busqueda');
			
			//si no existe añadimos un tabpanel
			if(pes==null){
			
			tabPanel.add({	//usamos notacion json
						   id:'busqueda',
						   title:'Busqueda',		                 
		                   border:false,
		                   closable:true,
		                   autoScroll:true,     
		                   autoLoad:{
		                   	    url:action,
		                   	    //el párametro "parameter" hace referencia a un metodo de Dispatch action
							    params:parametros,
							    callback:function(el,success,response){
						        	
						        	if(success!=true){
							        	 mascara(false);
							        	 Ext.MessageBox.alert(response.status,response.statusText);
						        	 }
					        	}			                   
		                   }
						
						}).show();
			
			}else{
			
			// Removemos el tab y añadimos el nuevo
			pes.destroy();
			tabPanel.add({	//usamos notacion json
						   id:'busqueda',
						   title:'Busqueda',		                 
		                   border:false,
		                   closable:true,
		                   autoScroll:true,     
		                   autoLoad:{
		                   	    url:action,
		                   	    //el párametro "parameter" hace referencia a un metodo de Dispatch action
							    params:parametros,
							    callback:function(el,success,response){
						        	
						        	if(success!=true){
							        	 mascara(false);
							        	 Ext.MessageBox.alert(response.status,response.statusText);
						        	 }
					        	}		                   
		                   }
						
						}).show();
			
			}
			
		
		}
	
	
	}
	

	new Ext.Window({
			id:'mascara',
			width:100,
			modal:true,
			closable:false,
			height:35,
			resizable:false,
			draggable:false,
			closeAction:'hide',
			onEsc:function(b){ 
	            
	            },
			html:'<img src="ext/resources/images/default/grid/loading.gif" /> Cargando...'
		});
		
	function mascara(activa){
		
			var mask=Ext.getCmp('mascara');
			
			if(mask!=null){
			
				if(activa==true){
				
					if(mask.isVisible()==false ){
					mask.show();
					}
				}else{
					
					if(mask.isVisible()==true ){
					mask.hide();
					}
				
				}
			
			
			}
			
		}
	
/*!
 * Ext JS Library 3.1.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('Ext.ux.form');

/**
 * @class Ext.ux.form.FileUploadField
 * @extends Ext.form.TextField
 * Creates a file upload field.
 * @xtype fileuploadfield
 */
Ext.ux.form.FileUploadField = Ext.extend(Ext.form.TextField,  {
    /**
     * @cfg {String} buttonText The bu	tton text to display on the upload button (defaults to
     * 'Browse...').  Note that if you supply a value for {@link #buttonCfg}, the buttonCfg.text
     * value will be used instead if available.
     */
    buttonText: 'Browse...',
    /**
     * @cfg {Boolean} buttonOnly True to display the file upload field as a button with no visible
     * text field (defaults to false).  If true, all inherited TextField members will still be available.
     */
    buttonOnly: false,
    /**
     * @cfg {Number} buttonOffset The number of pixels of space reserved between the button and the text field
     * (defaults to 3).  Note that this only applies if {@link #buttonOnly} = false.
     */
    buttonOffset: 3,
    /**
     * @cfg {Object} buttonCfg A standard {@link Ext.Button} config object.
     */

    // private
    readOnly: true,

    /**
     * @hide
     * @method autoSize
     */
    autoSize: Ext.emptyFn,

    // private
    initComponent: function(){
        Ext.ux.form.FileUploadField.superclass.initComponent.call(this);

        this.addEvents(
            /**
             * @event fileselected
             * Fires when the underlying file input field's value has changed from the user
             * selecting a new file from the system file selection dialog.
             * @param {Ext.ux.form.FileUploadField} this
             * @param {String} value The file value returned by the underlying file input field
             */
            'fileselected'
        );
    },

    // private
    onRender : function(ct, position){
        Ext.ux.form.FileUploadField.superclass.onRender.call(this, ct, position);

        this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-file-wrap'});
        this.el.addClass('x-form-file-text');
        this.el.dom.removeAttribute('name');
        this.createFileInput();

        var btnCfg = Ext.applyIf(this.buttonCfg || {}, {
            text: this.buttonText
        });
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo: this.wrap,
            cls: 'x-form-file-btn' + (btnCfg.iconCls ? ' x-btn-icon' : '')
        }));

        if(this.buttonOnly){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }

        this.bindListeners();
        this.resizeEl = this.positionEl = this.wrap;
    },
    
    bindListeners: function(){
        this.fileInput.on({
            scope: this,
            mouseenter: function() {
                this.button.addClass(['x-btn-over','x-btn-focus'])
            },
            mouseleave: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            mousedown: function(){
                this.button.addClass('x-btn-click')
            },
            mouseup: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            change: function(){
                var v = this.fileInput.dom.value;
                this.setValue(v);
                this.fireEvent('fileselected', this, v);    
            }
        }); 
    },
    
    createFileInput : function() {
        this.fileInput = this.wrap.createChild({
            id: this.getFileInputId(),
            name: this.name||this.getId(),
            cls: 'x-form-file',
            tag: 'input',
            type: 'file',
            size: 1
        });
    },
    
    reset : function(){
        this.fileInput.remove();
        this.createFileInput();
        this.bindListeners();
        Ext.ux.form.FileUploadField.superclass.reset.call(this);
    },

    // private
    getFileInputId: function(){
        return this.id + '-file';
    },

    // private
    onResize : function(w, h){
        Ext.ux.form.FileUploadField.superclass.onResize.call(this, w, h);

        this.wrap.setWidth(w);

        if(!this.buttonOnly){
            var w = this.wrap.getWidth() - this.button.getEl().getWidth() - this.buttonOffset;
            this.el.setWidth(w);
        }
    },

    // private
    onDestroy: function(){
        Ext.ux.form.FileUploadField.superclass.onDestroy.call(this);
        Ext.destroy(this.fileInput, this.button, this.wrap);
    },
    
    onDisable: function(){
        Ext.ux.form.FileUploadField.superclass.onDisable.call(this);
        this.doDisable(true);
    },
    
    onEnable: function(){
        Ext.ux.form.FileUploadField.superclass.onEnable.call(this);
        this.doDisable(false);

    },
    
    // private
    doDisable: function(disabled){
        this.fileInput.dom.disabled = disabled;
        this.button.setDisabled(disabled);
    },


    // private
    preFocus : Ext.emptyFn,

    // private
    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }

});

Ext.reg('fileuploadfield', Ext.ux.form.FileUploadField);

// backwards compat
Ext.form.FileUploadField = Ext.ux.form.FileUploadField;
	
	
	
/*!
 * Ext JS Library 3.1.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('Ext.ux.grid');

/**
 * @class Ext.ux.grid.TableGrid
 * @extends Ext.grid.GridPanel
 * A Grid which creates itself from an existing HTML table element.
 * @history
 * 2007-03-01 Original version by Nige "Animal" White
 * 2007-03-10 jvs Slightly refactored to reuse existing classes * @constructor
 * @param {String/HTMLElement/Ext.Element} table The table element from which this grid will be created -
 * The table MUST have some type of size defined for the grid to fill. The container will be
 * automatically set to position relative if it isn't already.
 * @param {Object} config A config object that sets properties on this grid and has two additional (optional)
 * properties: fields and columns which allow for customizing data fields and columns for this grid.
 */
Ext.ux.grid.TableGrid = function(table, config){
    config = config ||
    {};
    Ext.apply(this, config);
    var cf = config.fields || [], ch = config.columns || [];
    table = Ext.get(table);
    
    var ct = table.insertSibling();
    
    var fields = [], cols = [];
    var headers = table.query("thead th");
    for (var i = 0, h; h = headers[i]; i++) {
        var text = h.innerHTML;
        var name = 'tcol-' + i;
        
        fields.push(Ext.applyIf(cf[i] ||
        {}, {
            name: name,
            mapping: 'td:nth(' + (i + 1) + ')/@innerHTML'
        }));
        
        cols.push(Ext.applyIf(ch[i] ||
        {}, {
            'header': text,
            'dataIndex': name,
            'width': h.offsetWidth,
            'tooltip': h.title,
            'sortable': true
        }));
    }
    
    var ds = new Ext.data.Store({
        reader: new Ext.data.XmlReader({
            record: 'tbody tr'
        }, fields)
    });
    
    ds.loadData(table.dom);
    
    var cm = new Ext.grid.ColumnModel(cols);
    
    if (config.width || config.height) {
        ct.setSize(config.width || 'auto', config.height || 'auto');
    }
    else {
        ct.setWidth(table.getWidth());
    }
    
    if (config.remove !== false) {
        table.remove();
    }
    
    Ext.applyIf(this, {
        'ds': ds,
        'cm': cm,
        'sm': new Ext.grid.RowSelectionModel(),
        autoHeight: true,
        autoWidth: false
    });
    Ext.ux.grid.TableGrid.superclass.constructor.call(this, ct, {});
};

Ext.extend(Ext.ux.grid.TableGrid, Ext.grid.GridPanel);

//backwards compat
Ext.grid.TableGrid = Ext.ux.grid.TableGrid;
	
	