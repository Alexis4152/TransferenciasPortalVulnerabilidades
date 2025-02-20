<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type=text/css>
#fi-button-msg {
	border: 2px solid #ccc;
	background: #eee;
}
</style>

<script type="text/javascript">
	Ext
			.onReady(function() {

				Ext.getCmp('<c:out value="${panelId}"/>').setTitle(
						'<c:out value="${titulo}"/>');			
				
				var tb = new Ext.Toolbar(
						{
							items : [ {
								// xtype: 'button', // default for Toolbars, same as 'tbbutton'
								iconCls : 'excel',
								text : ' ',
								width : 40,
								listeners : {
									'click' : function() {
										var validaExcel = Ext.get('HdnValExcel').dom.value;
										if(validaExcel == "OK"){
											var redirect = 'TemplateAction.action?parameter=validadorCuentas&reporte=excel';
											window.open(redirect);
										}
										else
											Ext.MessageBox.alert('Estatus',
											'Verifique que se haya realizado el proceso y sus resultados.');
									}
								}
							} ]
						});
				tb.render('toolbar<c:out value="${panelId}"/>');

			});

	function resultado(erroresProceso, texto) {
		mascara(false);
		var el = Ext.fly('fi-button-msg');
		var hidn = Ext.get('HdnValExcel');
		el.update(texto)
		if (!el.isVisible()) {
			el.slideIn('t', {
				duration : .2,
				easing : 'easeIn',
				callback : function() {
					el.highlight();
				}
			});
		} else {
			el.highlight();
		}

		Ext.DomHelper
				.overwrite('fileref',
						'<input name="theFile" id="theFile" size="20" type="file" /><br />	');
		if(erroresProceso < 100 && erroresProceso > -1){			
			hidn.set({'value':'OK'});
			ajaxDivUpdater('TemplateAction.action',
					'parameter=validadorCuentas&reporte=vista',
					'validadorCuentasReporte');
			
		}
		else{
			hidn.set({'value':''});
			Ext.DomHelper
			.overwrite('validadorCuentasReporte','');
		}
	}

	function validaCarga() {

		var file;
		var fileSplit;

		file = Ext.getDom('theFile').value;
		fileSplit = file.split('.')
		var sLength =  fileSplit.length;
		
		if (fileSplit[sLength-1] != 'xlsx') {
			Ext.MessageBox.alert('Estatus',
					'Seleccione Hoja de cálculo de Microsoft Excel (.xlsx)');
			return;
		}

		mascara(true);
		document.getElementById('file_upload_form').submit();

	}

	document.getElementById('file_upload_form').target = 'upload_target';
</script>
<div>
	<div id="toolbar<c:out value="${panelId}"/>"></div>
	<div>
		<div class="instrucciones">
			<b>El reporte validador de cuentas muestra la responsabiliad de
				pago asociada a la cuenta indicada en cada fila del archivo de Excel
				a cargar.<br /> El sistema procesa la información y muestra en una
				tabla las cuentas que tuvieron responsabilidad.<br /> En caso de no
				tener la responsabilidad, se indicará la cuenta que la tiene.
			</b> <br /> Instrucciones:<br /> - Seleccione un archivo de Excel
			(xlsx).<br /> - Presione el botón cargar.<br /> - Para exportar el
			reporte a EXCEL de clic sobre el icono ubicado en la parte superior
			izquierda de esta area. <br />- El validador de responsabilidad procesa solo 100
			cuentas por archivo.
		</div>
		<br />
		<form id="file_upload_form" method="post"
			enctype="multipart/form-data"
			action="CargaArchivoValidadorCuentas.action">
			<table align="center" width="500" border="0">
				<tr>
					<td width="150px;"></td>
					<td></td>
				</tr>
				<tr>
					<td>Archivo:</td>
					<td class="left">
						<div id="fileref">
							<input name="theFile" id="theFile" size="20" type="file" /><br />
						</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td class="left"><input type="button" onclick="validaCarga()" name="action" value="Cargar" /></td>
				</tr>
			</table>
			<br />
		</form>
		<div align="center" id="fi-button-msg" style="display: none;"></div>
		<input type="hidden" id="HdnValExcel" value=".">
		<div id="fileref" style="color: #FF0000"></div>
		<div align="center" id="validadorCuentasReporte"></div>
	</div>
	<iframe id="upload_target" name="upload_target" src=""
		style="width: 0; height: 0; border: 1px solid #fff;"></iframe>
</div>