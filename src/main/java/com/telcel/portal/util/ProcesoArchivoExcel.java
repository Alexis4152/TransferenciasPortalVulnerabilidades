package com.telcel.portal.util;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.telcel.portal.dao.implementacion.TransferenciasDaoImp3;
import com.telcel.portal.pojos.BancomerConceptosPojo;
import com.telcel.portal.pojos.CargaDocumentoPojo;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.TransferenciaPojo;

public class ProcesoArchivoExcel {
	
	private static Logger  logger = Logger.getLogger(ProcesoArchivoExcel.class);
	private static final String RESULTADO = "resultado";
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public Map procesarExcel(ActionForm form,Integer idTransferencia,Integer idEmpleado){
		
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
	         PagoPojo pojo;   
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
	        	 
	   
	        	 
	             DecimalFormat df = new DecimalFormat("################################");
	        	 pojo.setCuenta(""+df.format(numero));
	        	 logger.info("***************setCuenta:"+df.format(numero));
		     	} catch (ArithmeticException  e) {
		     		
		     		 String stringCuenta = row.getCell(1).getStringCellValue();
		     		 
		     		 try {
		     			 
		     			 Double.parseDouble(stringCuenta);
		     			 pojo.setCuenta(stringCuenta);
		     			logger.info("***************setCuenta:"+stringCuenta);
					} catch (Exception e2) {
						
						 map.put(this.RESULTADO, "Error en formato de la cuenta fila:"+fila);// TODO: handle exception
						 return map;
						
					}
		     		 
				
				}
	        	 
	        	 
	        	 
	        	 try {
	        		 row.getCell(2).getNumericCellValue();
	        		 pojo.setImporte(row.getCell(2).getNumericCellValue());
	        		 logger.info("***************setImporte:"+pojo.getImporte());
	             	 if(pojo.getImporte()<=0){
		        		 map.put(this.RESULTADO, "Error los importes deben mayores que cero fila:"+fila);// TODO: handle exception
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
		        		 map.put(this.RESULTADO, "Error los importes deben mayores que cero fila:"+fila);// TODO: handle exception
						 return map;
		        	 }
			        logger.info("***************setImporte:"+pojo.getImporte());
					} catch (Exception e1) {
						 map.put(this.RESULTADO, "Error en formato de importe (no numerico) fila:"+fila);// TODO: handle exception
						 return map;
					}
					
					
					
				}
	        	 
	        	 
	        	 if(row.getCell(ConstantesNumeros.TRES).getStringCellValue()==null){
	        		 pojo.setTipo("CU");
	        	 }
	        	 else{
	        		 //Aqui le hago trim y a mayusculas todo si comienza con FA.. pues FA si no CU
	        		 String tipo = row.getCell(ConstantesNumeros.TRES).getStringCellValue().toUpperCase(Locale.getDefault()).trim();
	        		 pojo.setTipo(tipo);
	        		 if(!tipo.startsWith("FA") && !tipo.equals("CU")){
	        				 pojo.setTipo("CU");
	        		 }
	        			 	

	        	 }
	        	 
	        	 if(pojo.getTipo().equals("FA")){
	        		 try {
	        			 
	    	        	 double esporadico = row.getCell(4).getNumericCellValue() ;	 
	    	             DecimalFormat df1 = new DecimalFormat("################################");
	    	        	 pojo.setSap(df1.format(esporadico));
	    	        	 logger.info("***************setEsporadico:"+df1.format(esporadico));
	    		     	}catch (NullPointerException  ne){
	    		     		map.put(this.RESULTADO, "Falta indicar el esporadico, fila:"+fila);
	    		     		return map;
	    		     	}catch (Exception  e) {
	    		     		logger.info("EXCEPTION ARITHMETIC");
	    		     		e.printStackTrace();
	    		     		 String stringEsporadico = row.getCell(4).getStringCellValue();
	    		     		 
	    		     		 try {
	    		     			 
	    		     			pojo.setSap(stringEsporadico);
	    		     			logger.info("***************setEsporadicoString:"+stringEsporadico);
	    					} catch (Exception e2) {
	    						 map.put(this.RESULTADO, "Error en formato del esporadico fila:"+fila);// TODO: handle exception
	    						 return map;
	    					}
	    				}
	        	 }else{
	        		 pojo.setSap("");
	        	 }
	        	 
	        	 pojo.setIdEmpleado(idEmpleado);
	        	 pojo.setIdTrans(idTransferencia);
	        	 listaPagoPojo.add(pojo);
	       
	         
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
	
	public List<TransferenciaPojo> cargaExcelHSBC(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
	    HSSFRow row; 
	    HSSFWorkbook wb;
	    HSSFSheet sheet;
	    List<TransferenciaPojo> listaTrans = new ArrayList<TransferenciaPojo>();
		TransferenciaPojo TransPojo;
	    int count=0; 
		POIFSFileSystem fs;
		 
		try {
			 fs = new POIFSFileSystem(archivo.getInputStream());
			 wb = new HSSFWorkbook(fs); 
			 sheet = wb.getSheetAt(0); 
			 
	         int termino= sheet.getLastRowNum()-2;
	         logger.info("***************size:"+termino);
	       
	         for(count = 5; count < termino; count++){
	        	 logger.info("***************Contador:"+count);
	        	 row= sheet.getRow(count);
	        	 TransPojo= new TransferenciaPojo();
	        	 int fila=count+1;
	        	 
	        	 if(row.getCell(4) != null){
	        		 double importe =  row.getCell(4).getNumericCellValue();
	        		 if(importe > 0){
	        			 logger.info("FILA:"+fila+" FIN DE ARCHIVO:"+importe);
						break;
					 } 
	        	 }
	        	 
	        	 TransPojo.setCuenta("4014177083");
	        	 TransPojo.setIdBanco(idBanco);
	        	 TransPojo.setReferenciaBanco("00000000000");
	        	 TransPojo.setIdEmpleado(idEmpleado);
	        	 
	        	 if(row.getCell(1).getDateCellValue() != null){
	        		 java.util.Date fechaDate = row.getCell(1).getDateCellValue();
		        	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        	 String fecha = dateFormat.format(fechaDate);
		        	 TransPojo.setFecha(fecha);
		        	 TransPojo.setDateFecha( new Date(dateFormat.parse(fecha).getTime()));
		        	
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA");
	        		 return null;
	        	 }
	        	 
	        	 String descripcion = "";
	        	 try{
	        		 descripcion = row.getCell(3).getStringCellValue() != null ? row.getCell(3).getStringCellValue().trim() : "";
	         	 }catch(Exception ex){
	         		 try{
	         			 DecimalFormat df = new DecimalFormat("################################");
	         			 descripcion = df.format(row.getCell(3).getNumericCellValue());
	         		 }catch(Exception e){
	         			 logger.info("ERROR EN FILA:"+fila+" CAMPO DESCRIP ES NUMERICO");
	         			 return null;
	         		 }
	         	 }
	        	 TransPojo.setReferenciaCliente(descripcion.length() > 150 ? descripcion.substring(0, 150) : descripcion);
	        	
	        	 if(row.getCell(5) != null){
	        		 double importe =  row.getCell(5).getNumericCellValue();
	        		 
	        		 if(importe <= 0){
						 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE MENOR A CERO");
		        		 return null;
					 }
	        		 TransPojo.setImporte(importe);
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE NULO");
	        		 return null;
	        	 }
				 
	        	 listaTrans.add(TransPojo);
	         }
		} catch (Exception e){
			logger.debug(e.getMessage(), e);
			logger.info(e.getMessage());
			return null;
		} 
         
		return listaTrans;
	}
	
	public List<TransferenciaPojo> cargaExcelBanamex(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
	    XSSFRow row; 
	    XSSFWorkbook wb;
	    XSSFSheet sheet;
	    List<TransferenciaPojo> listaTrans = new ArrayList<TransferenciaPojo>();
		TransferenciaPojo TransPojo;
	    int count=0; 
		 
		try {
			 wb = new XSSFWorkbook(archivo.getInputStream()); 
			 sheet = wb.getSheetAt(0); 
			 
	         int termino= sheet.getLastRowNum(); 
	         logger.info("***************size:"+termino);
	       
	         for(count = 16; count <= termino; count++){
	        	 logger.info("***************Contador:"+count);
	        	 row= sheet.getRow(count);
	        	 int fila=count+1;
	        	 String tipo = row.getCell(2).getStringCellValue() != null ? row.getCell(2).getStringCellValue().trim() : "C";
	        	 
	        	 if(tipo.equals("A")){
	        		 TransPojo= new TransferenciaPojo();
	        		 TransPojo.setCuenta("7611918");
		        	 TransPojo.setIdBanco(idBanco);
		        	 TransPojo.setReferenciaBanco("00000000000");
		        	 TransPojo.setIdEmpleado(idEmpleado);
		        	 
		        	 if(row.getCell(1).getDateCellValue() != null){
		        		 java.util.Date fechaDate = row.getCell(1).getDateCellValue();
			        	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			        	 String fecha = dateFormat.format(fechaDate);
			        	 TransPojo.setFecha(fecha);
			        	 TransPojo.setDateFecha( new Date(dateFormat.parse(fecha).getTime()));
		        	 }else{
		        		 logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA");
		        		 return null;
		        	 }
		        	 
		        	 String descripcion = "";
		        	 try{
		        		 descripcion = row.getCell(8).getStringCellValue() != null ? row.getCell(8).getStringCellValue().trim() : "";
		        	 }catch(Exception ex){
		        		 try{
		        			 DecimalFormat df = new DecimalFormat("################################");
		        			 descripcion = df.format(row.getCell(8).getNumericCellValue());
		        		 }catch(Exception e){
		        			 logger.info("ERROR EN FILA:"+fila+" CAMPO DESCRIP ES NUMERICO MAYOR A 16 DIGITOS");
		        			 return null;
		        		 }
		        	 }
		        	 TransPojo.setReferenciaCliente(descripcion.length() > 150 ? descripcion.substring(0, 150) : descripcion);
		        	
		        	 if(row.getCell(9) != null){
		        		 double importe =  row.getCell(9).getNumericCellValue();
		        		 
		        		 if(importe <= 0){
							 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE MENOR A CERO");
			        		 continue;
						 }
		        		 TransPojo.setImporte(importe);
		        	 }else{
		        		 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE NULO");
		        		 continue;
		        	 }
		        	 listaTrans.add(TransPojo); 
	        	 }
	         }
		} catch (Exception e){
			logger.debug(e.getMessage(), e);
			logger.info(e.getMessage());
			return null;
		} 
         
		return listaTrans;
	}
	
	public List<TransferenciaPojo> cargaExcelBanorte(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
		 XSSFRow row; 
		 XSSFWorkbook wb;
		 XSSFSheet sheet;
		 List<TransferenciaPojo> listaTrans = new ArrayList<TransferenciaPojo>();
	     TransferenciaPojo TransPojo;
		 int count=0; 
			 
		try {
			 wb = new XSSFWorkbook(archivo.getInputStream()); 
			 sheet = wb.getSheetAt(0); 
			 
	         int termino= sheet.getLastRowNum()-2;
	         logger.info("***************size:"+termino);
	       
	         for(count = 18; count < termino; count++){
	        	 logger.info("***************Contador:"+count);
	        	 row= sheet.getRow(count);
	        	 TransPojo= new TransferenciaPojo();
	        	 int fila=count+1;
	        	 
	        	 if(row.getCell(6) != null){
	        		 try{
	        			 row.getCell(6).getNumericCellValue();
	        		 }catch(Exception ex){
	        			 String finArchivo = row.getCell(6).getStringCellValue().trim();
	        			 if(finArchivo.startsWith("Operaciones") ){
		        			 logger.info("FILA:"+fila+" FIN DE ARCHIVO:"+finArchivo);
							break;
						 } 
	        		 }
	        	 }
	        	 
	        	 TransPojo.setCuenta("671025954");
	        	 TransPojo.setIdBanco(idBanco);
	        	 TransPojo.setReferenciaBanco("00000000000");
	        	 TransPojo.setIdEmpleado(idEmpleado);
	        	 
	        	 if(row.getCell(2).getDateCellValue() != null){
	        		 java.util.Date fechaDate = row.getCell(2).getDateCellValue();
		        	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        	 String fecha = dateFormat.format(fechaDate);
		        	 TransPojo.setFecha(fecha);
		        	 TransPojo.setDateFecha( new Date(dateFormat.parse(fecha).getTime()));
		        	
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA");
	        		 return null;
	        	 }
	        	 
	        	 String descripcion = "";
	        	 try{
	        		 descripcion = row.getCell(11).getStringCellValue() != null ? row.getCell(11).getStringCellValue().trim() : "";
	         	 }catch(Exception ex){
	         		 try{
	         			 DecimalFormat df = new DecimalFormat("################################");
	         			 descripcion = df.format(row.getCell(11).getNumericCellValue());
	         		 }catch(Exception e){
	         			 logger.info("ERROR EN FILA:"+fila+" CAMPO DESCRIP ES NUMERICO");
	         			 return null;
	         		 }
	         	 }
	        	 TransPojo.setReferenciaCliente(descripcion.length() > 150 ? descripcion.substring(0, 150) : descripcion);
	        	
	        	 if(row.getCell(7) != null){
	        		 double importe =  row.getCell(7).getNumericCellValue();
	        		 
	        		 if(importe <= 0){
						 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE MENOR A CERO");
		        		 continue;
					 }
	        		 TransPojo.setImporte(importe);
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE NULO");
	        		 continue;
	        	 }
				 
	        	 listaTrans.add(TransPojo);
	         }
		} catch (Exception e){
			logger.debug(e.getMessage(), e);
			logger.info(e.getMessage());
			return null;
		} 
         
		return listaTrans;
	}
	
	public List<TransferenciaPojo> cargaExcelSantander(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
		 XSSFRow row; 
		 XSSFWorkbook wb;
		 XSSFSheet sheet;
		 List<TransferenciaPojo> listaTrans = new ArrayList<TransferenciaPojo>();
	     TransferenciaPojo TransPojo;
		 int count=0; 
			 
		try {
			 wb = new XSSFWorkbook(archivo.getInputStream()); 
			 sheet = wb.getSheetAt(0); 
			 
	         int termino= sheet.getLastRowNum();
	         logger.info("***************size:"+termino);
	       
	         for(count = 5; count < termino; count++){
	        	 logger.info("***************Contador:"+count);
	        	 row= sheet.getRow(count);
	        	 
	        	 String cargo =""; 
	        	 try{ 
	        		cargo = row.getCell(0).getStringCellValue() != null ? row.getCell(0).getStringCellValue().trim() : "";
	        	 }catch(Exception ex){
	        		cargo ="";
	        	 }
	        	 if(cargo.startsWith("Movimientos")){
	        		 break;
	        	 }
	        	 
	        	 TransPojo= new TransferenciaPojo();
	        	 int fila=count+1;
	        	 TransPojo.setCuenta("52451002658");
	        	 TransPojo.setIdBanco(idBanco);
	        	 TransPojo.setReferenciaBanco("00000000000");
	        	 TransPojo.setIdEmpleado(idEmpleado);
	        	 
	        	 if(row.getCell(0).getDateCellValue() != null){
	        		 java.util.Date fechaDate = row.getCell(0).getDateCellValue();
		        	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        	 String fecha = dateFormat.format(fechaDate);
		        	 TransPojo.setFecha(fecha);
		        	 TransPojo.setDateFecha( new Date(dateFormat.parse(fecha).getTime()));
		        	
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA");
	        		 return null;
	        	 }
	        	 
	        	 String descripcion = "";
	        	 try{
	        		 descripcion = row.getCell(8).getStringCellValue() != null ? row.getCell(8).getStringCellValue().trim() : "";
	         	 }catch(Exception ex){
	         		 try{
	         			 DecimalFormat df = new DecimalFormat("################################");
	         			 descripcion = df.format(row.getCell(8).getNumericCellValue());
	         		 }catch(Exception e){
	         			 logger.info("ERROR EN FILA:"+fila+" CAMPO DESCRIP ES NUMERICO");
	         			 return null;
	         		 }
	         	 }
	        	 TransPojo.setReferenciaCliente(descripcion.length() > 150 ? descripcion.substring(0, 150) : descripcion);
	        	
	        	 if(row.getCell(5) != null){
	        		 double importe = 0;
	        		 try{
	        			 importe =  row.getCell(5).getNumericCellValue();
	        		 }catch(Exception ex){
	        			 continue;
	        		 }
	        		 
	        		 if(importe <= 0){
						 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE MENOR A CERO");
						 continue;
					 }
	        		 TransPojo.setImporte(importe);
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE NULO");
	        		 continue;
	        	 }
				 
	        	 listaTrans.add(TransPojo);
	         }
		} catch (Exception e){
			logger.debug(e.getMessage(), e);
			logger.info(e.getMessage());
			return null;
		} 
        
		return listaTrans;
	}
	
	public List<TransferenciaPojo> cargaExcelInbursa(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
		 XSSFRow row; 
		 XSSFWorkbook wb;
		 XSSFSheet sheet;
		 List<TransferenciaPojo> listaTrans = new ArrayList<TransferenciaPojo>();
	     TransferenciaPojo TransPojo;
		 int count=0; 
			 
		try {
			 wb = new XSSFWorkbook(archivo.getInputStream()); 
			 sheet = wb.getSheetAt(0); 
			 
	         int termino= sheet.getLastRowNum();
	         logger.info("***************size:"+termino);
	       
	         for(count = 7; count <= termino; count++){
	        	 logger.info("***************Contador:"+count);
	        	 row= sheet.getRow(count);
	        	 TransPojo= new TransferenciaPojo();
	        	 int fila=count+1;
	        	 
	        	 if(row.getCell(7) != null){
	        		 double importe =  row.getCell(7).getNumericCellValue();
	        		 
	        		 if(importe <= 0){
						 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE MENOR A CERO");
		        		 continue;
					 }
	        		 TransPojo.setImporte(importe);
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO IMPORTE NULO");
	        		 continue;
	        	 }
	        	 
	        	 TransPojo.setCuenta("34403130");
	        	 TransPojo.setIdBanco(idBanco);
	        	 TransPojo.setReferenciaBanco("00000000000");
	        	 TransPojo.setIdEmpleado(idEmpleado);
	        	 
	        	 if(row.getCell(0).getDateCellValue() != null){
	        		 java.util.Date fechaDate = row.getCell(0).getDateCellValue();
		        	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        	 String fecha = dateFormat.format(fechaDate);
		        	 TransPojo.setFecha(fecha);
		        	 TransPojo.setDateFecha( new Date(dateFormat.parse(fecha).getTime()));
		        	
	        	 }else{
	        		 logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA");
	        		 return null;
	        	 }
	        	 
	        	 String descripcion = "";
	        	 try{
	        		 descripcion = row.getCell(1).getStringCellValue() != null ? row.getCell(1).getStringCellValue().trim() : "";
	         	 }catch(Exception ex){
	         		 try{
	         			 DecimalFormat df = new DecimalFormat("################################");
	         			 descripcion = df.format(row.getCell(1).getNumericCellValue());
	         		 }catch(Exception e){
	         			 logger.info("ERROR EN FILA:"+fila+" CAMPO DESCRIP ES NUMERICO");
	         			 return null;
	         		 }
	         	 }
	        	 TransPojo.setReferenciaCliente(descripcion.length() > 150 ? descripcion.substring(0, 150) : descripcion);
	        	
	        	 listaTrans.add(TransPojo);
	         }
		} catch (Exception e){
			logger.debug(e.getMessage(), e);
			logger.info(e.getMessage());
			return null;
		} 
       
		return listaTrans;
	}
	
	public List<TransferenciaPojo>cargaHSBC(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
		java.util.Date dFecha = new java.util.Date();
		
		List<TransferenciaPojo> listaTrans = new ArrayList<TransferenciaPojo>();
		TransferenciaPojo pojoTransferencia;
	    HSSFWorkbook wbLibro;
	    HSSFSheet hoja1;
		
	    try {
			
			wbLibro = new HSSFWorkbook(archivo.getInputStream());
			hoja1 = wbLibro.getSheetAt(0);
				 		
	 		Iterator<Row> rowIterator = hoja1.iterator();
	 		Row rReg = null;
	 		String sCuenta = "";
			String sDesc = "";
			String sAbono = "";
			String sCargo = "";
			String sRef = "";

	 		while(rowIterator.hasNext()){
	 			rReg = rowIterator.next();
	 			if(rReg.getRowNum()>=5){
	 				Iterator<Cell> CellIterator = rReg.cellIterator();
		 			Cell Celda;
		 			
		 			while(CellIterator.hasNext()){
		 				Celda = CellIterator.next();
		 				switch(Celda.getCellType()){
		 				case Cell.CELL_TYPE_NUMERIC:
		 					if(HSSFDateUtil.isCellDateFormatted(Celda)){
		 						if(Celda.getColumnIndex() == 1){
		 							dFecha = Celda.getDateCellValue();
		 						}
		 						
		 					}else{
		 						if(Celda.getColumnIndex() == 0){
		 							sCuenta = String.valueOf(Celda.getNumericCellValue()).split("E")[0].replace(".", "");
		 						}else if(Celda.getColumnIndex() == 4){
									sCargo = String.valueOf(Celda.getNumericCellValue());
								}else if(Celda.getColumnIndex() == 5){
									sAbono = String.valueOf(Celda.getNumericCellValue());
								}
		 					}
		 				break;
		 				
		 				case Cell.CELL_TYPE_BLANK:
							break;
						
						case Cell.CELL_TYPE_STRING:
							if(Celda.getColumnIndex() == 3){
								sDesc = Celda.getStringCellValue();
							}else if(Celda.getColumnIndex() == 7){
								sRef = Celda.getStringCellValue();
							}else if(Celda.getColumnIndex() == 0){
	 							sCuenta = Celda.getStringCellValue();
	 						}
							
							break;
		 				}
		 				if(Celda.getColumnIndex() == 7 && !sCuenta.equals("") && !sDesc.equals("") ){
		 					//Se cargan los datos al beean y se limpiean variables
		 					pojoTransferencia= new TransferenciaPojo();
		 					Date sqlDate = new Date(dFecha.getTime());
		 					pojoTransferencia.setCuenta(sCuenta);
		 					//pojoTransferencia.setFecha(formatoFinal.format(dFecha));
		 					pojoTransferencia.setDateFecha(sqlDate);
		 					pojoTransferencia.setConcepto(sDesc);
		 					if(!sCargo.equals("")){
		 						pojoTransferencia.setImporte(Double.parseDouble(sCargo));
		 						pojoTransferencia.setImporteTransString(sCargo);
		 					}else{
		 						pojoTransferencia.setImporte(Double.parseDouble(sAbono));
		 						pojoTransferencia.setImporteTransString(sAbono);
		 					}
		 					
		 					pojoTransferencia.setIdBanco(idBanco);
		 					pojoTransferencia.setIdEmpleado(idEmpleado);
		 					pojoTransferencia.setReferenciaCliente(sRef);
		 					pojoTransferencia.setReferenciaBanco(sRef);
		 					listaTrans.add(pojoTransferencia);
		 					
		 					sCuenta = "";
		 					sDesc = "";
		 					sAbono = "";
		 					sCargo = "";
		 					sRef = "";
		 				}
		 			}
	 			}
			 }
	         
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}	    
		return listaTrans;
		
	}
public List<TransferenciaPojo>cargaExcelBancomer(FormFile archivo,Integer idBanco,Integer idEmpleado, Integer idRegion){

	 XSSFRow row; 
	 XSSFWorkbook wb;
	 XSSFSheet sheet;
	 boolean esAgregado=true;
	 List<TransferenciaPojo> listaTrans = new ArrayList<TransferenciaPojo>();
     TransferenciaPojo transPojo;
	 String cuenta="";
	 String concepto="";
	 Date date=null;
	 java.util.Date fecha =null;
	 String referenciaBanco="";
	 String referenciaCliente="";
	 double  importe=0;
	 String tipoMoneda="MXP";
	 List <BancomerConceptosPojo> listaConceptos=null;
	 TransferenciasDaoImp3 dao=new TransferenciasDaoImp3();
	try {	
		 listaConceptos=dao.consultaByRegion(idRegion);
		 
		 
		wb = new XSSFWorkbook(archivo.getInputStream());
		sheet = wb.getSheetAt(0);
		int ultimaFila= sheet.getLastRowNum();
		ultimaFila++;
		logger.info("ultima fila :"+ultimaFila);
		
		if (ultimaFila <= 2) {
			throw new Exception("ARCHIVO "+archivo.getFileName()+" VACIO ");
		}

		row= sheet.getRow(0);
		
		//CUENTA
        if(row.getCell(1) != null){
     		
     		 try{
     			//ESPERANDO QUE LA CELDA SEA UN STRING
     			 cuenta=row.getCell(1).getStringCellValue();
    		 }catch(Exception e){
//    			 int c=(int) row.getCell(1).getNumericCellValue();
    			 throw new Exception("ERROR EN NUMERO DE CUENTA: "+cuenta+" VALOR NO VALIDO");
    		 }
     	}else{
     		 logger.info("ERROR EN FILA:"+cuenta+" CAMPO CUENTA NULO");	
     		 throw new Exception("ERROR EN FILA:"+cuenta+" CAMPO CUENTA NULO");
     	}
        
       
        for(int count = 2; count < ultimaFila; count++){
        	 
        	row= sheet.getRow(count);
        	transPojo= new TransferenciaPojo();
	        int fila=count+1;
	        logger.info("INICIA PROCESO DE FILA : "+ fila);
	        if(row == null){  
	        	logger.info("ESTA FILA ES NULA:"+fila); 
	        	 break;
	        }
	        
	        //FECHA
	        if(row.getCell(0) != null){  
	        	try{
	        		
	        		fecha =  row.getCell(0).getDateCellValue();	
	        		date=new Date(fecha.getTime());
	        	}catch(Exception e){
	        		String s=row.getCell(0).getStringCellValue();
	        		logger.info("FECHA valor despues :"+s+" :");
	        		if(s.equals("")){
	        			logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA ESTA VACIA");
	        			throw new Exception("ERROR EN FILA:"+fila+" CAMPO FECHA ESTA VACIA");
	        		}
	        		logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA NO CUMPLE CON EL FORMATO");
	         		e.printStackTrace();
	         		throw new Exception("ERROR EN FILA:"+fila+" CAMPO FECHA NO CUMPLE CON EL FORMATO");
	         	}
	        }else{
	        	logger.info("ERROR EN FILA:"+fila+" CAMPO FECHA NULO");
	        	throw new Exception("ERROR EN FILA:"+fila+" CAMPO FECHA NULO");
	        } 
	        	
	        //CONCEPTO
	        if(row.getCell(1) != null){
	        	concepto =  row.getCell(1).getStringCellValue();
	        }else{
	        	logger.info("ERROR EN FILA:"+fila+" CAMPO CONCEPTO NULO");
	        	throw new Exception("ERROR EN FILA:"+fila+" CAMPO CONCEPTO NULO");
	        }
	        	
	        //REFERENCIA BANCO
	        if(row.getCell(2) != null){
	        	try{
	        		referenciaBanco =  row.getCell(2).getStringCellValue();
	        	}catch(Exception e){
	        		int c=(int) row.getCell(2).getNumericCellValue();
	        		referenciaBanco =  String.valueOf(c); 
	        		 }
	        		 if(referenciaBanco.length()> 200)referenciaBanco=referenciaBanco.substring(0, 200);
	        }else{
	        	logger.info("ERROR EN FILA:"+fila+" CAMPO REFERENCIA NULO");	
	        	throw new Exception("ERROR EN FILA:"+fila+" CAMPO REFERENCIA NULO");
	        }
	        	
	        //REFERENCIA CLIENTE
	        if(row.getCell(3) != null){
	        	referenciaCliente=  row.getCell(3).getStringCellValue();	
	        	if(referenciaCliente.length()> 200)referenciaCliente=referenciaCliente.substring(0, 200);
	        }else{
	        	throw new Exception("ERROR EN FILA:"+fila+" CAMPO REFERENCIA AMPLIADA NULO");
	        }
	        
	        //IMPORTE
	        if(row.getCell(5) != null){
	        	try{
	        		importe =  row.getCell(5).getNumericCellValue();
	        	}catch(Exception e){
	        		logger.info("ADVERTENCIA EN FILA:"+fila+" CAMPO IMPORTE NO ES NUMERICO O VACIO");
	        		importe=0.0d;
	        		continue;
	         	}
	        	if(importe <= 0){
	        		logger.info("ADVERTENCIA EN FILA :"+fila+" CAMPO IMPORTE MENOR O IGUAL A CERO");
		        	continue;
				}
	  
	       }else{
	        	throw new Exception("ERROR EN FILA:"+fila+" CAMPO IMPORTE NULO");
	        }
	        
	 		for (int i = 0; i < listaConceptos.size(); i++) {	 			
	 			BancomerConceptosPojo bancomerConceptosPojo=listaConceptos.get(i);
	 			if (concepto.contains(bancomerConceptosPojo.getConcepto())) {
	 				esAgregado=true;
	 				if (concepto.contains("SPEI RECIBIDO") && referenciaCliente.contains("AMEXCO")) {
	 					esAgregado=false;
					}
				    break;
				} else {
					esAgregado=false;
				}
	 		} //END FOR
	       
	               	 
	       transPojo.setCuenta(cuenta);
	       transPojo.setDateFecha(date);
	       transPojo.setImporte(importe);
	       transPojo.setConcepto(concepto);
      	   transPojo.setTipoMoneda(tipoMoneda);
      	   transPojo.setIdBanco(idBanco);
      	   transPojo.setReferenciaBanco(referenciaBanco);
      	   transPojo.setIdEmpleado(idEmpleado);
      	   transPojo.setReferenciaCliente(referenciaCliente);
      	   transPojo.setEsAgregada(esAgregado);
      	   listaTrans.add(transPojo);
      	   
      	 if (esAgregado) {
      	   logger.info("FINALIZA PROCESO DE FILA : "+ fila + "Se agrego correctamente");
	    	}else{
      	   logger.info("FINALIZA PROCESO DE FILA : "+ fila+ "No se agrego el concepto no es correcto");
	    	}
		}  //END FOR
	       
		} catch (FileNotFoundException e) {
			logger.error("ERROR cargaExcelBancomer() FileNotFoundException "+e.getMessage());
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			logger.error("ERROR cargaExcelBancomer() IOException "+e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception ex){
			logger.error("ERROR Exception causada por : "+ex.getMessage());
			ex.printStackTrace();
			return null;
		}	    
		return listaTrans;
		
	}

	public List<CargaDocumentoPojo> procesaArchivoCompensacion(FormFile archivo) {
		List<CargaDocumentoPojo> documentos = new ArrayList<>();
		XSSFWorkbook wb;
		XSSFSheet sheet;
		try {
			wb = new XSSFWorkbook(archivo.getInputStream());
			sheet = wb.getSheetAt(0);
			int rowsCount = sheet.getLastRowNum();
            for (int i = 1; i <= rowsCount; i++) {
            	CargaDocumentoPojo cargaDocumentoPojo = new CargaDocumentoPojo();
            	cargaDocumentoPojo.setIndex(i);
                Row row = sheet.getRow(i);
                int colCounts = row.getLastCellNum();
                for (int j = 0; j < colCounts; j++) {
                    Cell cell = row.getCell(j);
                    /*if (j==0) {
						cargaDocumentoPojo.setFechaDocumento(dateFormat.format(cell.getDateCellValue()));
					}else*/ if(j==0) {
						cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						cargaDocumentoPojo.setNumeroDocumento(new BigDecimal(cell.getNumericCellValue()));
					}else if(j==1) {
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cargaDocumentoPojo.setLote(Long.valueOf(cell.getStringCellValue().split("-")[0]));
					}else if(j==2) {
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cargaDocumentoPojo.setDivision(cell.getStringCellValue().replace("MR", "R"));
					}else if(j==3) {
						cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						cargaDocumentoPojo.setFechaLote(dateFormat.format(cell.getDateCellValue()));
					}
                }
                documentos.add(cargaDocumentoPojo);
            }
		} catch (FileNotFoundException e) {
			logger.error("ERROR procesaArchivoCompensacion() FileNotFoundException " + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			logger.error("ERROR procesaArchivoCompensacion() IOException " + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			logger.error("ERROR Exception causada por : " + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
		return documentos;
	}
	
	
	
}
