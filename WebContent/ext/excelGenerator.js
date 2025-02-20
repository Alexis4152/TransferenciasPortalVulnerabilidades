function tableToJson(tableId) {
	var table = document.getElementById(tableId);
	var headers;
	if(tableId == 'general'){headers = ['FechaInicio','FechaFin','GeneradoPor'];}
	if(tableId == 'acumulado'){headers = ['Usuario','Nombre','Total','NumeroTransferencias'];}
	if(tableId == 'transferencias'){headers = ['Nombre','FechaTransferencia','Importe','Alias','IdTransferencia','TipoPagos','FechaDesglose','Rechazado'];}
	var data = []; // first row needs to be headers var headers = [];
	for (var i = 0; i < table.rows[0].cells.length; i++) {
		headers[i] = table.rows[0].cells[i].innerHTML.replace(
				/ /gi, '');
	}
	// go through cells
	for (var i = 1; i < table.rows.length; i++) {
		var tableRow = table.rows[i];
		var rowData = {};
		for (var j = 0; j < tableRow.cells.length; j++) {
			rowData[headers[j]] = tableRow.cells[j].innerHTML;
		}
		data.push(rowData);
	}
	return data;
}

function saveFile(dataGeneral,dataAcumulado,dataTransferencias) {
	var data1 = dataGeneral;
	var data2 = dataAcumulado;
	var data3 = dataTransferencias;
	var opts = [{sheetid:'General',header:true},{sheetid:'Acumulado',header:true},{sheetid:'Detalle',header:true}];
	var res = alasql('SELECT INTO XLSX("reporteUsuario.xlsx",?) FROM ?',
	                 [opts,[data1,data2,data3]]);
}