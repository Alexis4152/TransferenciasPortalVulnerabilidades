<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="s" uri="/struts-tags"%>
>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/estilos/estilos_dex.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/recursos/ext/resources/css/ext-all.css" />    
    
        
    <script type="text/javascript"  src="<%=request.getContextPath()%>/recursos/ext/adapter/ext/ext-base.js">   </script>
    <script type="text/javascript"     src="<%=request.getContextPath()%>/recursos/ext/ext-all.js">                 </script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/recursos/ext/general.js">                 </script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/recursos/ext/source/locale/ext-lang-es.js"> </script>-->    



<script language="javascript" type="text/javascript">
    Ext.onReady(function(){
        Ext.util.CSS.swapStyleSheet("theme","recursos/ext/resources/css/xtheme-light-green.css");        
    
        Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/recursos/ext/resources/images/gray/s.gif';
        Ext.Updater.defaults.loadScripts=true;
    
        var fechaInicial = new Ext.form.DateField({
            id : 'fechaInicialExt',
            name : 'fechaInicialExt',
            emptyText : 'Selecciona',
            invalidText : [ "La fecha debe estar en el formato <ul>",
                            "<li>DD/MM/YYYY</li>",
                            "</ul>"
                           ].join(""),
            msgTarget:'under',
            value : new Date((new Date()).getFullYear(),(new Date()).getMonth(),(new Date()).getDate() - 0,0,0,0),
            format : 'd/m/Y',
            altFormats : 'd/m/Y|d/M/Y',
            fieldClass : 'Input',
            width : 100,
            showToday : true,
            validator : validarFecha,
            renderTo : 'fechaIni'
          });
          
        var fechaCambio = new Ext.form.DateField({
            id : 'fechaFinalExt',
            name : 'fechaFinalExt',
            emptyText : 'Selecciona',
            invalidText : [ "La fecha debe estar en el formato <ul>",
                            "<li>DD/MM/YYYY</li>",
                            "</ul>"
                           ].join(""),
            msgTarget:'under',
            value : new Date((new Date()).getFullYear(),(new Date()).getMonth(),(new Date()).getDate() - 0,0,0,0),
            format : 'd/m/Y',
            altFormats : 'd/m/Y|d/M/Y',
            fieldClass : 'Input',
            width : 100,
            showToday : true,
            validator : validarFecha,
            renderTo : 'fechaFin'
          });
          
        pais2Ext();
        moneda2Ext();
          
    });    

    var fechaIniExt = function(){
        var fechaInicial = new Ext.form.DateField({
            id : 'fechaInicialExt',
            name : 'fechaInicialExt',
            emptyText : 'Selecciona',
            invalidText : [ "La fecha debe estar en el formato <ul>",
                            "<li>DD/MM/YYYY</li>",
                            "</ul>"
                           ].join(""),
            msgTarget:'under',
            value : new Date((new Date()).getFullYear(),(new Date()).getMonth(),(new Date()).getDate() - 0,0,0,0),
            format : 'd/m/Y',
            altFormats : 'd/m/Y|d/M/Y',
            fieldClass : 'Input',
            width : 100,
            showToday : true,
            validator : validarFecha,
            renderTo : 'fechaIni'
          });
    };

    var fechaFinExt = function(){
          var fechaCambio = new Ext.form.DateField({
                id : 'fechaFinalExt',
                name : 'fechaFinalExt',
                emptyText : 'Selecciona',
                invalidText : [ "La fecha debe estar en el formato <ul>",
                                "<li>DD/MM/YYYY</li>",
                                "</ul>"
                               ].join(""),
                msgTarget:'under',
                value : new Date((new Date()).getFullYear(),(new Date()).getMonth(),(new Date()).getDate() - 0,0,0,0),
                format : 'd/m/Y',
                altFormats : 'd/m/Y|d/M/Y',
                fieldClass : 'Input',
                width : 100,
                showToday : true,
                validator : validarFecha,
                renderTo : 'fechaFin'
              });
    };

    var pais2Ext = function(){
          var idPaisExt = new Ext.form.ComboBox({
            typeAhead: true,
            triggerAction: 'all',
            transform:'idPais',
            emptyText:'Seleccione...',            
            width:135,
            forceSelection:true
        });
        idPaisExt.on({
            'change':{
                fn: buscaMonedas
            },            
            specialkey: function(field, e){
                // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
                // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
                if (e.getKey() == e.TAB||e.getKey() == e.ENTER) {
                     buscaMonedas();
                }
            }
        });
    };
    

    var moneda2Ext = function(){
        var idMonedaExt = new Ext.form.ComboBox({
            typeAhead: true,
            triggerAction: 'all',
            transform:'monedaId',
            emptyText:'Seleccione...',            
            width:135,
            forceSelection:true
        });
        idMonedaExt.on({
            'change':{
                fn: consultar
            },            
            specialkey: function(field, e){
                // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
                // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
                if (e.getKey() == e.TAB||e.getKey() == e.ENTER) {
                     consultar();
                }
            }
        
        });
          
    };        


    
    function validarFecha(valor){
       var retorno;
       var expresion = /^\d{1,2}\/\d{1,2}\/\d{1,4}$/;
       retorno = expresion.test(valor);
       if(!retorno)
       {
          retorno = "La fecha debe estar en el formato DD/MM/YYYY";
       }
       return retorno;
    }
    
    function buscaMonedas(){
        
        var params = 'paisId='+Ext.getDom('idPais').value;
//alert(params);
        if(Ext.getDom('idPais').value > 0){
            ajaxDivUpdater('<%=request.getContextPath()%>/consultaHistoricaProy!consultaMonedas.action',params,'slMoneda');
            moneda2Ext();
        }else{
            Ext.MessageBox.show(
                    {
                        title: [
                                "<p align=center>Saldos y Ganancias</p>"
                                ].join(""),
                        closable: false,
                        msg: 'Seleccione un pais',
                        buttons: Ext.MessageBox.OK,
                        animEl: 'blank',
                        icon: Ext.MessageBox.ERROR
                    }
                );
        }
    }
    
    function consultar(){
        var params = 'paisId='+Ext.getDom('idPais').value +
                     '&monedaId='+ Ext.getDom('monedaId').value +
                     '&fechaInicio='+ Ext.getCmp('fechaInicialExt').value +
                     '&fechaFin='+ Ext.getCmp('fechaFinalExt').value ;
//alert(params);
            if(Ext.getDom('idPais').value >0 && Ext.getDom('monedaId').value > 0){
                    ajaxDivUpdater('<%=request.getContextPath()%>/consultaHistoricaProy!consultaProyeccion.action',params,'resBusqueda');
            }else{
                Ext.MessageBox.show(
                        {
                            title: [
                                    "<p align=center>Saldos y Ganancias</p>"
                                    ].join(""),
                            closable: false,
                            msg: 'Seleccione una moneda asociada al pais',
                            buttons: Ext.MessageBox.OK,
                            animEl: 'blank',
                            icon: Ext.MessageBox.ERROR
                        }
                    );
            }
    }


function Desplaza(obj){
    document.all('divCabeceros').scrollLeft = obj.scrollLeft;
    document.all('divTotales').scrollLeft = obj.scrollLeft;
    document.all('divDetalle').scrollTop = obj.scrollTop;
}

</script>

</head>

<body>

<!--<table width="784" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="175" bgcolor="#D6D6D6"><table width="568" border="0" class="font10">
      <tr>
        <td width="199">Bienvenido Luis Ibarra</td>
        <td width="189">Usted trabaja con perfil de solicitante</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="<=request.getContextPath()%>/imagenes/head.jpg" alt="head"></td>
  </tr>
</table>-->
<!--<table width="980" height="30" border="0" cellpadding="0" cellspacing="0" class="detalleOperacion">
  <tr>
    <td class="font12b">Efectuando Operaci&oacute;n de <span class="font12b_verde">PROYECCIONES</span></td>
  </tr>
</table>-->
<table width="980" border="0" cellspacing="0" cellpadding="0">
<!--  <tr> 
    <td valign="top" class="tdTabs">
        <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="95"><img src="<=request.getContextPath()%>/imagenes/tab_parametros0.gif"></td>
          <td width="145"><img src="<=request.getContextPath()%>/imagenes/tab_saldosNeto0.gif"></td>
          <td width="95"><img src="<=request.getContextPath()%>/imagenes/tab_proyecciones1.gif"></td>
          <td width="115"><img src="<=request.getContextPath()%>/imagenes/tab_comision0.gif"></td>
        </tr>
      </table>
      </td>
  </tr>-->
  <tr>
    <!-- <td valign="top" class="tdContenido">-->
    <td valign="top" >
    <table width="760" border="0" cellspacing="0" cellpadding="0">
        <tr> 
<!--          <td width="149" valign="top" class="tdTabsIzq">
           <table width="100" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="35" align="right" valign="top"><img src="<=request.getContextPath()%>/imagenes/tab_izq_regArchivo0.gif"></td>
              </tr>
              <tr> 
                <td height="35" valign="top"><img name="tab_izq_saldoNeto0" src="<=request.getContextPath()%>/imagenes/tab_izq_generaSolicitudes0.gif" border="0" alt=""></td>
              </tr>
              <tr> 
                <td height="35" align="right" valign="top"><img src="<=request.getContextPath()%>/imagenes/tab_izq_consultaSolicitudes0.gif"></td>
              </tr>
              <tr> 
                <td height="35" align="right" valign="top"><img src="<=request.getContextPath()%>/imagenes/tab_izq_consultaHistorica1.gif"></td>
              </tr>
              <tr> 
                <td height="35" align="right" valign="top"><img src="<=request.getContextPath()%>/imagenes/tab_izq_reportes0.gif"></td>
              </tr>
              <tr> 
                <td height="35" align="right" valign="top"><img src="<=request.getContextPath()%>/imagenes/tab_izq_consSaldosReg0.gif"></td>
              </tr>
            </table>
            </td>-->
<!--          <td width="602" valign="top" class="tdContenido2">-->
<td>
          <table width="795" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="583" height="25" valign="top" class="font12b_verde">
                Consulta Hist&oacute;rica</td>
              </tr>
              <tr> 
                <td height="110">
<!-- inicio control -->                
                
                <table width="790" height="106" border="0" cellpadding="0"
                cellspacing="0">
                <tr>
                    <td width="19%" align="center" valign="top"><img
                        src="<%=request.getContextPath()%>/imagenes/ico_diafestivo.png"></td>
                    <td width="81%" valign="top">
                        <table width="491" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="188" height="25" class="font11b">Fecha Inicial :</td>
                                <td width="312" class="font11b">Fecha Final:</td>
                            </tr>
                            <tr>
                                <td width="188">
                                    <table width="160" border="0" cellspacing="1">
                                        <tr>
                                            <td width="35">
                                                <div id="fechaIni"></div>
                                                <div id="ErrfechaIni"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="312">
                                    <table width="160" border="0" cellspacing="1">
                                        <tr>
                                            <td width="35">
                                                <div id="fechaFin"></div>
                                                <div id="ErrfechaFin"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr valign="bottom">
                                <td height="35" colspan="2" align="left"><br>
                                    <table width="500" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="232" class="font11b">Indique el pa&iacute;s</td>
                                            <td width="264" class="font11b">Indique la Moneda</td>
                                            <td width="104" class="font11b">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td width="232" height="31" class="font11">
                                                Pa&iacute;s: 
                                                <div id="slPais">
                                                    <select id="idPais" name="idPais"  onChange="buscaMonedas();" class="tb_130">
                                                        <s:iterator value="listaPaises">
                                                            <option value="<s:property value="paisId"/>">
                                                                <s:property value="nombrePais" />
                                                            </option>
                                                        </s:iterator>
            <!--                                              <option selected>TODOS</option>
                                                        <option>M&eacute;xico</option>
                                                        <option>Guatemala</option>-->
                                                    </select>
                                                </div>
                                            </td>
                                            <td width="264" height="31" class="font11">
                                                Moneda:
                                                <div id="slMoneda">
                                                    <select id="monedaId" name="monedaId" onChange="consultar();" class="tb_130">
                                                        <option selected>TODOS</option>
                                                        <option>Saldo neto en USD</option>
                                                        <option>Tiempo</option>
                                                        <option>D&iacute;as festivos</option>
                                                    </select>
                                                </div>
                                                </td>
                                            <td width="104" class="font11">
                                                <a href="javascript:consultar();"><img src="<%=request.getContextPath()%>/imagenes/btn_buscar.jpg"
                                                                                    alt="Buscar" id="buscar" border="0"></a>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
<!-- fin control -->                  
<div id="resBusqueda">
                  <table width="790" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="790" height="25" colspan="2" class="font12b_verde">Consulta
        hist&oacute;rico de solicitudes</td>
    </tr>
    <tr>
        <td width="790">
        <div id='divCabeceros' style='OVERFLOW: hidden; HEIGHT: 26px; WIDTH:790' onscroll='Desplaza(this)'>
            <table width="1370" height="20" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr align="center" class="colhead">
                    <td width="120" valign="middle">
                        Folio de solicitud
                    </td>
                    <td width="120" valign="top">
                        Fecha y hora compra / venta
                    </td>
                    <td width="120">
                        Pa&iacute;s
                    </td>
                    <td width="120">
                        Moneda local
                    </td>
                    <td width="150">
                        Saldo neto
                    </td>
                    <td width="150" valign="top">
                        Tipo de cambio liquidaci&oacute;n vs USD
                    </td>
                    <td width="150" valign="top">
                        Tipo de cambio Cliente vs USD
                    </td>
                    <td width="150">
                        FX te&oacute;rico (USD)
                    </td>
                    <td width="150" valign="top">
                        Ganancia op&eacute;rdida<br> por cambios (USD)
                    </td>
                    <td>
                        FX liquidaci&oacute;n (USD)
                    </td>
                </tr>
            </table>
        </div>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div id='divDetalle' onScroll='Desplaza(this);' style='OVERFLOW: auto; HEIGHT: 240px; WIDTH:790'>
                <table width="1350" cellpadding="0" cellspacing="0">
                <tr align="center">
                        <td width="120" class="rowLineaBot_dotted">10</td>
                        <td width="120" class="rowLineaBot_dotted">29/Ago/2009 00:00</td>
                        <td width="120" class="rowLineaBot_dotted">M&eacute;xico</td>
                        <td width="120" class="rowLineaBot_dotted">MXN</td>
                        <td width="150" class="rowLineaBot_dotted">
                            <span class="font11_rojo">$4,733,305</span>
                        </td>
                        <td width="150" class="rowLineaBot_dotted">$14.3480</td>
                        <td width="150" class="rowLineaBot_dotted">$14.3480</td>
                        <td width="150" class="rowLineaBot_dotted">$3,886</td>
                        <td width="150" class="rowLineaBot_dotted">$2,862</td>
                        <td class="rowLineaBot_dotted">$1,024</td>
                    </tr>
                    
                    <s:iterator value="listaProyecciones" status="rowstatus" >
                    <tr align="center" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">
                    <!-- <tr align="center">
                        <td width="120" class="rowLineaBot_dotted">10</td>-->
                        
                        <td width="120" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">
                            <s:property value="folioSol"/>
                        </td>
                        <td width="120" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">
                            <s:property value="fechaCompraVenta"/>
                        </td>                        
                        <td width="120" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">
                            <s:property value="descPais"/>
                        </td>
                        <td width="120" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">
                            <s:property value="monLocal"/>
                        </td>
                        <td width="150" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">
                            <!--<s:if test="#saldoNeto == true ">
                                <span class="font11_rojo"><s:property value="saldoNeto"/></span>
                            </s:if>
                            <s:else>-->
                                <s:property value="saldoNeto"/>
                            <!--</s:else>-->
                        </td>
                        <td width="150" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">                            
                            <s:property value="tipoCambioLiqVsUSD"/>                            
                        </td>
                        <td width="150" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">                            
                            <s:property value="tipoCambioCteVsUSD"/>                            
                        </td>
                        <td width="150" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">                            
                            <s:property value="fxTeoricoUSD"/>                            
                        </td>
                        <td width="150" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">                            
                            <s:property value="gananciaPerdidaUSD"/>                            
                        </td>                    
                        <td width="150" class="<s:if test="#rowstatus.odd == true ">Tabla_Dinamica_B</s:if><s:else>Tabla_Dinamica_A</s:else>">                            
                            <s:property value="fxLiquidacion"/>                            
                        </td>    
                    </tr>
                    </s:iterator>
                                        
                </table>
            </div>
            <div id='divTotales' style='OVERFLOW: hidden; HEIGHT: 26px; WIDTH:790' onscroll='Desplaza(this)'>
                <table width="1370" height="20" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr align="center" class="colhead">
                        <td width="120" valign="middle">&nbsp;</td>
                        <td width="120" valign="top">&nbsp;</td>
                        <td width="120">&nbsp;</td>
                        <td width="120">&nbsp;</td>
                        <td width="150">&nbsp;</td>
                        <td width="150" valign="top">&nbsp;</td>
                        <td width="150" class="font12b">Totales:</td>
                        <td width="150" class="font12b">$5,250</td>
                        <td width="150" class="font12b">$1,583</td>
                        <td class="font12b">$1,170</td>
                    </tr>
                </table>
                        </div>
                        </td>
                    </tr>
                  </table>
                  </div>
                </td>
              </tr>
            </table>
            <table width="790" height="50" border="0" cellpadding="0" cellspacing="0" class="font11">
              <tr> 
                <td> <span class="font11_verde">* Saldo positivo:</span> Moneda 
                  local a vender<br> <span class="font11_verde">* Saldo negativo:</span> 
                  Moneda local a comprar</td>
              </tr>
            </table>
            <table width="790" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td><table border="0" align="right" cellspacing="3">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/imagenes/btn_exportarxls.jpg" alt="guardar"></td>
                      <td align="center"><img src="<%=request.getContextPath()%>/imagenes/btn_escSalir.jpg" alt="salir"></td>
                    </tr>
                  </table></td>
              </tr>
            </table> </td>
<!--           <td width="9" align="right" valign="top"><img src="<=request.getContextPath()%>/imagenes/right_contenido2.gif"></td>
-->
        </tr>
    </table></td>
  </tr>
<!--  <tr>
    <td valign="top"><img src="<=request.getContextPath()%>/imagenes/bottom_contenido.jpg"></td>
  </tr>-->
</table>
<div id="blank"></div>

</body>
</html>
