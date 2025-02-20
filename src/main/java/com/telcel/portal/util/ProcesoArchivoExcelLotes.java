package com.telcel.portal.util;



import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.pojos.PagoPojo;

public class ProcesoArchivoExcelLotes {
	
	private static Logger  logger = Logger.getLogger(GeneralDAO.class);
	private static final String RESULTADO = "resultado";
	
	public Map procesarExcel(ActionForm form){
		
		UploadForm theForm;
		FormFile archivo;
	    HSSFRow row; 
	    HSSFWorkbook wb;
	    HSSFSheet sheet;
	    List<PagoPojo> listaPagoPojo;
	    int count=0;
		
		  theForm = (UploadForm) form;
		  archivo = theForm.getTheFile();			 
		  POIFSFileSystem fs;
		  listaPagoPojo= new ArrayList<PagoPojo>();
		  HashMap map= new HashMap();
		  map.put("listaPagoPojo", null);
		try {
			
			 fs = new POIFSFileSystem(archivo.getInputStream());
			 wb = new HSSFWorkbook(fs); 
			 sheet = wb.getSheetAt(0); 
			 
	         int termino= sheet.getLastRowNum();
	         logger.info("***************termino:"+termino);
	         PagoPojo  pojo;   
	         for(count = 1;count<=termino;count++){
	          
	        	 logger.info("***************Contador:"+count);
	        	 
	        	 row= sheet.getRow(count);
	        	 logger.info("***************row:"+row);
	        	 pojo= new PagoPojo();
	        	 
	        	 //Esta validacion  es cuando no hay fila
	        	 try {
	        	   	 if(row.getCell(0).getStringCellValue()==null){
		        		 continue;
		        	 }
				} catch (Exception e) {
					
					 continue;
				}
	     
	        	 
	        	 String carga=row.getCell(0).getStringCellValue();
	        	     

	        	 
	        	 int fila=count+1;
	        	 // Esta validacione es por si el campo de renglon esta vacie se salte al siguiente
	        	 if (carga==null||carga.trim().length()==0) {
	        		 continue;
	        	 }
	        	 
	        	 carga=carga.toUpperCase(Locale.getDefault()).trim();
	        	 
	        	 if (carga.length() > ConstantesNumeros.TRES) {
	        		 carga = carga.substring(ConstantesNumeros.CERO, ConstantesNumeros.TRES);
	        	 }
	        	 
	        	 if(!carga.startsWith("R0")){
	        		 
	        		 map.put(this.RESULTADO, "Error en formato de region fila:"+fila+", debe comenzar con 'R0'");
	        		 return map;
	        	 }
	        	 
	        	 
	        	 pojo.setRegion(carga.trim());
	        	 try {
					
			
	        	 double numero = row.getCell(1).getNumericCellValue();	 
	        	 
	   
	        	 
	             DecimalFormat df = new DecimalFormat("#####");
	        	 pojo.setLote(""+df.format(numero));
	        	 logger.info("***************setLote:"+df.format(numero));
		     	} catch (Exception e) {
		     		
		     		 String stringLote = row.getCell(1).getStringCellValue();
		     		 
		     		 try {
		     			 
		     			 Double.parseDouble(stringLote);
		     			 pojo.setLote(stringLote);
		     			logger.info("***************setCuenta:"+stringLote);
					} catch (Exception e2) {
						
						 map.put(this.RESULTADO, "Error en formato del lote fila:"+fila);// TODO: handle exception
						 return map;
					}
		     		 
				}
	        	 
	        	 
	        	 
	        	 try {
	        		 row.getCell(2).getNumericCellValue();
	        		 pojo.setImporte(row.getCell(2).getNumericCellValue());
	        		 logger.info("***************setImporte:"+pojo.getImporte());
	             	 if(pojo.getImporte()<=0){
		        		 map.put(this.RESULTADO, "Error los importes deben ser mayores que cero fila:"+fila);// TODO: handle exception
						 return map;
		        	 }
	        		  
				} catch (Exception e) {
					
					try {
						
					String importeTemp = row.getCell(2).getStringCellValue();
					importeTemp = importeTemp.replace(",", "");
					importeTemp = importeTemp.replace("$", "");
					importeTemp = importeTemp.replace(" ", "");
					pojo.setImporte(Double.parseDouble(importeTemp));
			        if(pojo.getImporte()<=0){
		        		 map.put(this.RESULTADO, "Error, los importes deben ser mayores que cero fila:"+fila);// TODO: handle exception
						 return map;
		        	 }
			        logger.info("***************setImporte:"+pojo.getImporte());
					} catch (Exception e1) {
						 map.put(this.RESULTADO, "Error en formato de importe (no numerico) fila:"+fila);// TODO: handle exception
						 return map;
					}
					
				}
	        	 
	        	
	        	 
	        	try{
	        		 String fecha = row.getCell(ConstantesNumeros.TRES).getStringCellValue();
	        		 
	        		 if(fecha.length()== ConstantesNumeros.DIEZ){
	        			 int dia = Integer.parseInt(fecha.substring(0, 2));
	        			 int mes = Integer.parseInt(fecha.substring(ConstantesNumeros.TRES, ConstantesNumeros.CINCO));
	        			 int anio = Integer.parseInt(fecha.substring(ConstantesNumeros.SEIS, ConstantesNumeros.DIEZ));
	        			 String car1 = fecha.substring(ConstantesNumeros.DOS,ConstantesNumeros.TRES);
	        			 String car2 = fecha.substring(ConstantesNumeros.CINCO,ConstantesNumeros.SEIS);
	        			 
	        			 if(!(dia>ConstantesNumeros.CERO && dia<=ConstantesNumeros.TREINTAIUNO) || !(mes>ConstantesNumeros.CERO && mes<=ConstantesNumeros.DOCE) || !(String.valueOf(anio).length() == ConstantesNumeros.CUATRO)){
	        				 map.put(this.RESULTADO, "Error, el formato de la fecha no es valido, verificar dia, mes o anio:"+fila);// TODO: handle exception
							 return map;
	        			 }else if(!(car1.equals("/") && car2.equals("/"))){
	        				 map.put(this.RESULTADO, "Error, el formato de la fecha no es valido, verificar dia, mes o anio:"+fila);// TODO: handle exception
							 return map;
	        			 }
	        			
	        			 SimpleDateFormat formatoFinal = new SimpleDateFormat("dd/MM/yyyy");
	        			 pojo.setFechaNumLote(new Date(formatoFinal.parse(fecha).getTime()));
	        			 pojo.setFechaLoteString(fecha);
	        		 }else{
	        			 map.put(this.RESULTADO, "Error el formato de la fecha no es valido, debe ser DD/MM/YYYY:"+fila);// TODO: handle exception
						 return map;
	        		 }
	        	 }catch(Exception ex){
	        		 	map.put(this.RESULTADO, "Error en formato de fecha validar formato DD/MM/YYYY fila:"+fila);// TODO: handle exception
	        		 	return map;
	        	 }
	        	 
	        	 listaPagoPojo.add(pojo);
	         
	         }
	         if(listaPagoPojo.size() > ConstantesNumeros.VEINTE){
	        	map.put(this.RESULTADO, "Error, el numero maximo de registros por archivo debera ser  20." );// TODO: handle exception
     		 	return map;
	         }
	      map.put("listaPagoPojo", listaPagoPojo);
		} catch (Exception e){
			logger.debug(e.getMessage(), e);
			 int fila=count+1;
			 map.put(this.RESULTADO, "Error en la fila:"+fila + " [" + e.getMessage() + "]");
			 logger.info(e.getMessage());
			return map;
		} 
         
         
		return map;
	}

}
