package com.telcel.portal.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.telcel.portal.dao.implementacion.TransferenciasDaoImp3;
import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.pojos.BancomerConceptosPojo;
import com.telcel.portal.pojos.TransferenciaPojo;

public class CargaArchivosTexto {
	
	private static Logger  logger = Logger.getLogger(GeneralDAO.class);

	public List<TransferenciaPojo> cargaBancomer(FormFile archivo,Integer idBanco,Integer idEmpleado, Integer idRegion){
	
	
		BufferedReader entrada=null;
		String linea;
		ArrayList <TransferenciaPojo> listaTransferencias;
		TransferenciaPojo pojoTransferencia;
		
		
		boolean esAgregado=true;
		TransferenciasDaoImp3 dao=new TransferenciasDaoImp3();
		List <BancomerConceptosPojo> listaConceptos=null;
		
		 SimpleDateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 entrada = new BufferedReader(new InputStreamReader (archivo.getInputStream()));
			 listaConceptos=dao.consultaByRegion(idRegion);
			 int lineaNum=0;
			 listaTransferencias= new ArrayList <TransferenciaPojo>();
			 while((linea = entrada.readLine())!=null){
				 lineaNum++;
				 logger.info("---Region a procesar: "+idRegion);
				 	 if (!idRegion.equals(9)) {	
				 		 logger.info("CARGA BANCOMER TEXTO R1-R8");
				 		for (int i = 0; i < listaConceptos.size(); i++) {	 			
				 			BancomerConceptosPojo bancomerConceptosPojo=listaConceptos.get(i);
				 			String concepto=linea.substring(Constantes.REFERENCIA_FINAL, Constantes.CONCEPTO_FINAL);
				 			if (concepto.contains(bancomerConceptosPojo.getConcepto())) {
				 				esAgregado=true;	
				 				if (concepto.contains("SPEI RECIBIDO") && linea.substring(Constantes.TIPOM_FINAL).contains("AMEXCO")) {
				 					esAgregado=false;
								}
							    break;
							} else {
								esAgregado=false;
							}
				 		} //END FOR
				    }//END IF, SI NO ES REGION 9
				 	 
				pojoTransferencia = new TransferenciaPojo();
				pojoTransferencia.setCuenta(linea.substring(Constantes.CUENTA_INICIO, Constantes.CUENTA_FINAL));
				pojoTransferencia.setDateFecha(new Date(formatoFinal
						.parse(linea.substring(Constantes.CUENTA_FINAL, Constantes.FECHA_FINAL)).getTime()));
				pojoTransferencia
						.setReferenciaBanco(linea.substring(Constantes.FECHA_FINAL, Constantes.REFERENCIA_FINAL));
				pojoTransferencia.setConcepto(linea.substring(Constantes.REFERENCIA_FINAL, Constantes.CONCEPTO_FINAL));
				pojoTransferencia.setIndicador(linea.substring(Constantes.CONCEPTO_FINAL, Constantes.INDICADOR));
				pojoTransferencia.setImporte(
						Double.parseDouble(linea.substring(Constantes.INDICADOR, Constantes.IMPORTE_FINAL)));
				pojoTransferencia.setTipoMoneda(linea.substring(Constantes.IMPORTE_FINAL, Constantes.TIPOM_FINAL));
				pojoTransferencia.setReferenciaCliente(linea.substring(Constantes.TIPOM_FINAL));
				pojoTransferencia.setIdBanco(idBanco);
				pojoTransferencia.setIdEmpleado(idEmpleado);
				pojoTransferencia.setEsAgregada(esAgregado);
				listaTransferencias.add(pojoTransferencia);
				 	 
				 if (!esAgregado) { 					 
					 logger.info("Linea descartadas numero: "+lineaNum +" CONTENIDO: "+ linea); 
				 }
			 }
			
			
		} catch (Exception e) {
			// TODO Bloque catch generado automaticamente
			logger.debug(e.getMessage(), e);
			return null;
		}finally{
			try {
				entrada.close();
			} catch (IOException e) {
				// TODO Bloque catch generado automaticamente
				logger.debug(e.getMessage(), e);
			}
		}
		
		return listaTransferencias;
	}
	
	public List<TransferenciaPojo> cargaBancomerNuevoLayout(FormFile archivo, Integer idBanco, Integer idEmpleado,
			Integer idRegion) {
		BufferedReader entrada = null;
		String primeraLinea = "";
		String cuentaBanco = "";
		String fecha = "";
		String fechaFinal = "";
		String tipoMoneda = "";
		String concepto = "";
		String finArchivo = "32MEX";
		ArrayList<TransferenciaPojo> listaTransferencias;
		TransferenciaPojo pojoTransferencia;
		TransferenciasDaoImp3 dao = new TransferenciasDaoImp3();
		List<BancomerConceptosPojo> listaConceptos = null;
		try {
			entrada = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
			listaConceptos = dao.consultaByRegion(idRegion);
			int lineaNum = 0;
			listaTransferencias = new ArrayList<TransferenciaPojo>();
			logger.info("---Region a procesar: " + idRegion);
			while ((primeraLinea = entrada.readLine()) != null) {
				lineaNum++;
				if (lineaNum == 1) {
					cuentaBanco = primeraLinea.substring(Constantes.CTA_INICIO43, Constantes.CTA_FINAL43);
					fecha = primeraLinea.substring(Constantes.FECHA_INICIO43, Constantes.FECHA_FINAL43);
					fechaFinal = "20" + fecha.substring(0, 2) + "-" + fecha.substring(2, 4) + "-"
							+ fecha.substring(4, 6);
					tipoMoneda = primeraLinea.substring(Constantes.TIPOM_INICIO43, Constantes.TIPOM_FINAL43);
				}
				if (lineaNum > 1 && (lineaNum % 2 == 0) && !primeraLinea.substring(0, 6).contains(finArchivo)) {
					concepto = primeraLinea.substring(Constantes.CONCEPTO_INICIO43, Constantes.CONCEPTO_FINAL43);
				}
				if (!idRegion.equals(9) && lineaNum > 1) {
					logger.info("CARGA BANCOMER TEXTO R1-R8");
					if (primeraLinea.substring(0, 6).contains(finArchivo)) {
						logger.info("FIN DEL ARCHIVO...........");
						break;
					}
					for (int i = 0; i < listaConceptos.size(); i++) {
						BancomerConceptosPojo bancomerConceptosPojo = listaConceptos.get(i);
						if (concepto.contains(bancomerConceptosPojo.getConcepto())) {
							if (concepto.contains("SPEI RECIBIDO") && concepto.contains("AMEXCO")) {
								logger.info(lineaNum % 2 == 0
										? "Linea se descarta (SPEI RECIBIDO AMEXCO) -----> " + lineaNum
										: "");
								break;
							}
							pojoTransferencia = new TransferenciaPojo();
							pojoTransferencia = pojoBancomerNuevoLayout(primeraLinea, entrada, cuentaBanco, fechaFinal,
									concepto, tipoMoneda, idEmpleado);
							listaTransferencias.add(pojoTransferencia);
							logger.info("Linea agregada -----> " + lineaNum + "  " + primeraLinea);
							lineaNum++;
							break;
						} else if ((i == (listaConceptos.size() - 1) && (lineaNum % 2 == 0))) {
							logger.info("Linea descartada -----> " + lineaNum);
						}
					}
				}
				if (idRegion.equals(9) && lineaNum > 1) {
					logger.info("CARGA BANCOMER TEXTO R9");
					if (primeraLinea.substring(0, 6).contains(finArchivo)) {
						logger.info("FIN DEL ARCHIVO...........");
						break;
					}
					pojoTransferencia = new TransferenciaPojo();
					pojoTransferencia = pojoBancomerNuevoLayout(primeraLinea, entrada, cuentaBanco, fechaFinal,
							concepto, tipoMoneda, idEmpleado);
					listaTransferencias.add(pojoTransferencia);
					logger.info("Linea agregada -----> " + lineaNum + "  " + primeraLinea);
					lineaNum++;
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			return null;
		} finally {
			try {
				entrada.close();
			} catch (IOException e) {
				logger.debug(e.getMessage(), e);
			}
		}
		return listaTransferencias;
	}
	
	public List<TransferenciaPojo> cargaBanamex(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
		BufferedReader entrada = null;
		String linea;
		ArrayList <TransferenciaPojo> listaTransferencias;
		TransferenciaPojo pojoTransferencia;
		String[] lineaSeparada;
		 SimpleDateFormat formatoFinal = new SimpleDateFormat("yyyy.MM.dd");
		try {
			
			 entrada = new BufferedReader(new InputStreamReader (archivo.getInputStream()));
			
			 
			 listaTransferencias= new ArrayList <TransferenciaPojo>();
			 while((linea = entrada.readLine())!=null){
				 
				 
				 pojoTransferencia= new TransferenciaPojo();
				 lineaSeparada = linea.split(",");
				 if(lineaSeparada.length>= ConstantesNumeros.SIETE){
				 
				 pojoTransferencia.setCuenta(lineaSeparada[0]);
				 
				 pojoTransferencia.setDateFecha(new Date(formatoFinal.parse(lineaSeparada[1]).getTime()));
				 pojoTransferencia.setReferenciaBanco(lineaSeparada[2]);
				 pojoTransferencia.setConcepto(lineaSeparada[ConstantesNumeros.TRES]);
				 pojoTransferencia.setImporte(Double.parseDouble(lineaSeparada[ConstantesNumeros.CUATRO]));
				 pojoTransferencia.setReferenciaCliente(lineaSeparada[ConstantesNumeros.CINCO]);
				 pojoTransferencia.setIdBanco(idBanco);
				 pojoTransferencia.setIdEmpleado(idEmpleado);
				 listaTransferencias.add(pojoTransferencia);
				 }
			 }
			
			
		} catch (Exception e) {
			
			logger.info("*************Banamex---:"+e.getMessage());
			logger.debug(e.getMessage(), e);
			
			
			return null;
		}finally{
			try {
				entrada.close();
			} catch (IOException e) {
				// TODO Bloque catch generado automaticamente
				logger.debug(e.getMessage(), e);
			}
		}
		
		return listaTransferencias;
	}
	//	Metodo inhabilitado, permanece solo como referencia...
	
	public List<TransferenciaPojo> cargaHSBC(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
		BufferedReader entrada = null;
		String linea;
		ArrayList <TransferenciaPojo> listaTransferencias;
		TransferenciaPojo pojoTransferencia;
		SimpleDateFormat formatoFinal = new SimpleDateFormat("yyyyMMdd");
		try {
			
			 entrada = new BufferedReader(new InputStreamReader (archivo.getInputStream()));
			
			 
			 listaTransferencias= new ArrayList <TransferenciaPojo>();
			 while((linea = entrada.readLine())!=null){
				 
				 
				 pojoTransferencia= new TransferenciaPojo();
				 pojoTransferencia.setCuenta(linea.substring(Constantes.HSBC_CUENTA_INICIO, Constantes.HSBC_CUENTA_FINAL));
				 pojoTransferencia.setDateFecha( new Date(formatoFinal.parse(linea.substring(Constantes.HSBC_CUENTA_FINAL, Constantes.HSBC_FECHA_FINAL)).getTime()));
				 pojoTransferencia.setReferenciaBanco(linea.substring(Constantes.HSBC_FECHA_FINAL, Constantes.HSBC_REFERENCIA_FINAL));
				
				 pojoTransferencia.setConcepto(linea.substring(Constantes.HSBC_REFERENCIA_FINAL, Constantes.HSBC_CONCEPTO_FINAL));
				
				 String importe = linea.substring(Constantes.HSBC_CONCEPTO_FINAL, Constantes.HSBC_IMPORTE_FINAL);
				 
				 String centavos = importe.substring(importe.length()-2);
				 importe = linea.substring(Constantes.HSBC_CONCEPTO_FINAL, Constantes.HSBC_IMPORTE_FINAL-2);
				 
				 importe=importe+"."+centavos;
				 
				 pojoTransferencia.setImporte(Double.parseDouble(importe));
				 pojoTransferencia.setIndicador(linea.substring(Constantes.HSBC_IMPORTE_FINAL, Constantes.HSBC_INDICADOR));
				 pojoTransferencia.setReferenciaCliente(linea.substring(Constantes.HSBC_INDICADOR));
				 pojoTransferencia.setIdBanco(idBanco);
				 pojoTransferencia.setIdEmpleado(idEmpleado);
				 listaTransferencias.add(pojoTransferencia);
			 }
			
			
		} catch (Exception e) {
			logger.info("*************Carga---:"+e.getMessage());
			logger.debug(e.getMessage(), e);
			
			return null;
		}finally{
			try {
				entrada.close();
			} catch (IOException e) {
				// TODO Bloque catch generado automaticamente
				logger.debug(e.getMessage(), e);
			}
		}
		
		return listaTransferencias;
	}
	
	public List<TransferenciaPojo> cargaOtrosBancos(FormFile archivo,Integer idBanco,Integer idEmpleado){
		
		BufferedReader entrada = null;
		String linea;
		ArrayList <TransferenciaPojo> listaTransferencias;
		TransferenciaPojo pojoTransferencia;
		String[] lineaSeparada;
		 SimpleDateFormat formatoFinal = new SimpleDateFormat("yyyy.MM.dd");
		try {
			
			 entrada = new BufferedReader(new InputStreamReader (archivo.getInputStream()));
			
			 
			 listaTransferencias= new ArrayList <TransferenciaPojo>();
			 while((linea = entrada.readLine())!=null){
				 
				 
				 pojoTransferencia= new TransferenciaPojo();
				 lineaSeparada = linea.split(",");
				 if(lineaSeparada.length>= ConstantesNumeros.SIETE){
				 
				 pojoTransferencia.setCuenta(lineaSeparada[ConstantesNumeros.CERO]);
				 
				 pojoTransferencia.setDateFecha(new Date(formatoFinal.parse(lineaSeparada[1]).getTime()));
				 pojoTransferencia.setReferenciaBanco(lineaSeparada[ConstantesNumeros.DOS]);
				 pojoTransferencia.setConcepto(lineaSeparada[ConstantesNumeros.TRES]);
				 pojoTransferencia.setImporte(Double.parseDouble(lineaSeparada[ConstantesNumeros.CUATRO]));
				 pojoTransferencia.setReferenciaCliente(lineaSeparada[ConstantesNumeros.SEIS]);
				 pojoTransferencia.setIdBanco(idBanco);
				 pojoTransferencia.setIdEmpleado(idEmpleado);
				 listaTransferencias.add(pojoTransferencia);
				 }
			 }
			
			
		} catch (Exception e) {
			
			logger.info("*************cargaOtrosBancos---:"+e.getMessage());
			logger.debug(e.getMessage(), e);
			
			
			return null;
		}finally{
			try {
				entrada.close();
			} catch (IOException e) {
				// TODO Bloque catch generado automaticamente
				logger.debug(e.getMessage(), e);
			}
		}
		
		return listaTransferencias;
	}
	
	public TransferenciaPojo pojoBancomerNuevoLayout(String primeraLinea, BufferedReader entrada, String cuentaBanco,
			String fechaFinal, String concepto, String tipoMoneda, int idEmpleado) throws ParseException, IOException {
		TransferenciaPojo pojoTransferencia;
		pojoTransferencia = new TransferenciaPojo();
		SimpleDateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
		pojoTransferencia.setCuenta(cuentaBanco);
		pojoTransferencia.setDateFecha(new Date(formatoFinal.parse(fechaFinal).getTime()));
		pojoTransferencia.setConcepto(concepto);
		pojoTransferencia.setImporte(
				Double.parseDouble(primeraLinea.substring(Constantes.IMPORTE_INICIO43, Constantes.IMPORTE_FINAL43)));
		String segundaLinea = entrada.readLine();
		pojoTransferencia.setTipoMoneda(tipoMoneda);
		pojoTransferencia
				.setReferenciaCliente(segundaLinea.substring(Constantes.REF_CTE_INICIO43, Constantes.REF_CTE_FINAL43));
		pojoTransferencia.setReferenciaBanco(
				segundaLinea.substring(Constantes.REF_BANCO_INICIO43, Constantes.REF_BANCO_FINAL43));
		pojoTransferencia.setIdBanco(Constantes.BANCOMER);
		pojoTransferencia.setIdEmpleado(idEmpleado);
		return pojoTransferencia;
	}
	
}
