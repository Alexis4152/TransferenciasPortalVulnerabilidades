package com.telcel.portal.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import com.telcel.portal.pojos.ValidadorCuentasPojo;

public class ProcesoArchivoReponsabilidad {

	private static final int MAXCOLS = 2;
	private static final int MAXROWS = 100;
	private static final int MAXHEADERS = 1;
	private static final int DEFAULTPAGEINDEX = 0;
	private static final String HEADERREGEX = "^REGION|REGI\\ÓN|CUENTA$";
	private static final String REGIONREGEX = "^[R|r]0[1-9]{1}$";
	private static final String CUENTAREGEX = "^[1-9](\\d*)$";

	private String mensajesProceso;
	private int erroresProceso;

	public List<ValidadorCuentasPojo> cargarXLSX(UploadForm theForm) throws FileNotFoundException, IOException {

		int rowIndex = MAXHEADERS;
		int rowsCount;
		boolean filaValida;

		String valorRegion;
		String valorCuenta;
		String salidaVal;

		String valorIncorrecto = "BadFormat";
		String verifiqueArchivo = "Verifique el contenido del archivo.";

		StringBuilder sbDetError = new StringBuilder();
		StringBuilder sbMensajeSalida = new StringBuilder();

		ValidadorCuentasPojo validadorPojoAux;
		List<ValidadorCuentasPojo> lista = new ArrayList<>();

		FormFile archivo;
		XSSFRow row;
		XSSFWorkbook wb;
		XSSFSheet sheet;
		archivo = theForm.getTheFile();
		InputStream archivoIS = archivo.getInputStream();
		wb = new XSSFWorkbook(archivoIS);
		sheet = wb.getSheetAt(DEFAULTPAGEINDEX);
		rowsCount = sheet.getLastRowNum();

		erroresProceso = 0;

		if (!validaCabeceras(sheet)) {
			erroresProceso = -1;
			rowsCount = 0;
		}

		if (rowsCount > (MAXROWS + MAXHEADERS)) {
			sbMensajeSalida.append(String.format("Se procesaron los primeros 100 registros. %s", verifiqueArchivo));
			rowsCount = MAXROWS;
		}

		for (rowIndex = MAXHEADERS; rowIndex <= rowsCount; rowIndex++) {

			sbDetError = new StringBuilder();
			valorRegion = new String();
			valorCuenta = new String();
			salidaVal = new String();

			filaValida = true;

			row = sheet.getRow(rowIndex);

			if (row == null) {
				erroresProceso++;
				continue;
			}

			if (row.getLastCellNum() > MAXCOLS)
				sbDetError.append(String.format("La fila %s contiene más columnas de las permitidas (2). %s", rowIndex,
						verifiqueArchivo));

			XSSFCell cellRegion = row.getCell(0);
			XSSFCell cellCuenta = row.getCell(1);

			validadorPojoAux = new ValidadorCuentasPojo();

			try {
				valorRegion = cellRegion.getStringCellValue();

				if (valorRegion.isEmpty())
					throw new Exception();

				salidaVal = validaValorRegion(valorRegion);

				if (!salidaVal.equals("OK"))
					throw new Exception(valorIncorrecto);

			} catch (Exception ex) {
				if (ex.getMessage() == null || ex.getMessage().isEmpty())
					sbDetError.append(String.format("El dato de región en la fila %s está vacio. %s", rowIndex,
							verifiqueArchivo));
				else if (ex.getMessage().equals(valorIncorrecto))
					sbDetError.append(String.format("El dato de región en la fila %s contiene un error[%s]. %s",
							rowIndex, salidaVal, verifiqueArchivo));
				else
					sbDetError.append(String.format("El dato de región en la fila %s contiene un error. %s", rowIndex,
							verifiqueArchivo));

				filaValida = false;
			}

			try {
				if (cellCuenta.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					try {
						cellCuenta.setCellType(XSSFCell.CELL_TYPE_STRING);
						valorCuenta = cellCuenta.getStringCellValue();
					} catch (Exception ex) {
						valorCuenta = String.valueOf(cellCuenta.getNumericCellValue());
					}
				} else
					valorCuenta = cellCuenta.getStringCellValue();

				if (valorCuenta.isEmpty())
					throw new Exception();

				salidaVal = validaValorCuenta(valorCuenta);

				if (!salidaVal.equals("OK"))
					throw new Exception(valorIncorrecto);

			} catch (Exception ex) {

				if (ex.getMessage() == null || ex.getMessage().isEmpty())
					sbDetError.append(String.format("El valor de la  cuenta en la fila %s está vació. %s", rowIndex,
							verifiqueArchivo));
				else if (ex.getMessage().equals(valorIncorrecto))
					sbDetError.append(String.format("El valor de la cuenta en la fila %s contiene un error[%s]. %s",
							rowIndex, salidaVal, verifiqueArchivo));
				else
					sbDetError.append(String.format("El valor de la cuenta en la fila %s contiene un error. %s",
							rowIndex, verifiqueArchivo));
				filaValida = false;

			}

			validadorPojoAux.setRegion(valorRegion);
			validadorPojoAux.setCuenta(valorCuenta);
			validadorPojoAux.setResponsabilidad("OK");

			lista.add(validadorPojoAux);

			if (!filaValida) {
				String finalError = String.format("%s->%s", "ERROR", sbDetError.toString());
				validadorPojoAux.setResponsabilidad(finalError);
				erroresProceso++;
			}

		}
		
		if (erroresProceso != -1)
			if (erroresProceso >= rowsCount) {
				mensajesProceso = String.format("El proceso presenta [%s] errores en [%s] filas. %s", erroresProceso,
						rowsCount, verifiqueArchivo);
				lista = new ArrayList<>();
			} else
				mensajesProceso = sbMensajeSalida.toString();
		
		return lista;
	}

	private boolean validaCabeceras(XSSFSheet sheet) {
		
		int lastCell;
				
		XSSFCell cellRegion;
		XSSFCell cellCuenta;
		XSSFRow headerRow;
		
		int firstRow = sheet.getFirstRowNum();
		 
		mensajesProceso = "Error en las cabeceras del archivo.";
		
		if(firstRow > 0) 
			return false;		
		
		headerRow = sheet.getRow(0);
		
		if(headerRow == null) 
			return false;
		
		lastCell = headerRow.getLastCellNum();
		
		if(lastCell > 2 || lastCell < 2) {
			mensajesProceso = "La cabecera debe contener solamente dos columnas.";
			return false;
		}
		
		cellRegion = headerRow.getCell(0);
		cellCuenta = headerRow.getCell(1);
		
		try {
			mensajesProceso = "Error en el formato de las cabeceras. Verifique el archivo.";
			if(!cellRegion.getStringCellValue().toUpperCase().matches(HEADERREGEX))
				return false;
			if(!cellCuenta.getStringCellValue().toUpperCase().matches(HEADERREGEX))
				return false;
		}catch(Exception ex) {
			return false;
		}
		
		mensajesProceso = new String();
		return true;
	}

	private String validaValorCuenta(String valorCuenta) {
		if (valorCuenta.matches(CUENTAREGEX))
			return "OK";
		return "el valor de la cuenta no debe contener ceros a la izquierda.";
	}

	private String validaValorRegion(String valorRegion) {
		if (valorRegion.matches(REGIONREGEX))
			return "OK";
		return "El valor de región es incorrecto";
	}

	public String getMensajesProceso() {
		return this.mensajesProceso;
	}

	public int getErroresProceso() {
		return this.erroresProceso;
	}
}
