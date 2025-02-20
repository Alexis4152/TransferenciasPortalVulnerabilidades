package com.telcel.portal.servicios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.pojos.PagoPojo;
import com.telcel.portal.pojos.TransferenciaPojo;

public class PromesaPago {

	private static Logger  logger = Logger.getLogger(GeneralDAO.class);
	
	public boolean insertaPromesaPago(TransferenciaPojo transferencia, List<PagoPojo> listaPagos, int diasPromesa){
		
		try{
			
			CHIST chist = new CHIST();
			CSMAN csman = new CSMAN();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
			
			
			Calendar calendar = Calendar.getInstance();
			Date fechaActual = calendar.getTime();
			calendar.setTime(fechaActual); 
			calendar.add(Calendar.DAY_OF_YEAR, diasPromesa);  
			String fechaPromesa = dateFormat.format(calendar.getTime());
				
			
			for(PagoPojo pago : listaPagos){
				
				if(pago.getTipo().equals("CU")){
				
					String resultadoCHIST = chist.consultaCHIST(pago.getRegion(), pago.getCuenta());
					//REPROCESO
					for(int i=0; i<3 && resultadoCHIST.contains("Error en login"); i++){
						System.out.println("REPROCESO CONSULTA CHIST1:"+i);
						resultadoCHIST = chist.consultaCHIST(pago.getRegion(), pago.getCuenta());
					}
					
					if(resultadoCHIST.contains("CL045")){
						logger.info("ALTA CSMAN");
						String resultadoCSMAN = csman.altaCSMAN(pago.getRegion(), pago.getCuenta());
						//REPROCESO
						for(int i=0; i<3 && resultadoCSMAN.contains("Error en login"); i++){
							System.out.println("REPROCESO ALTA CSMAN:"+i);
							resultadoCSMAN = csman.altaCSMAN(pago.getRegion(), pago.getCuenta());
						}
						if(resultadoCSMAN.equals("OK")){
							resultadoCHIST = chist.consultaCHIST(pago.getRegion(), pago.getCuenta());
							//REPROCESO
							for(int i=0; i<3 && resultadoCHIST.contains("Error en login"); i++){
								System.out.println("REPROCESO CONSULTA CHIST2:"+i);
								resultadoCHIST = chist.consultaCHIST(pago.getRegion(), pago.getCuenta());
							}
							if(resultadoCHIST.equals("OK") || resultadoCHIST.contains("SD026") || resultadoCHIST.contains("ASAHK")){
								
								resultadoCHIST = chist.altaCHIST(pago.getRegion(), pago.getCuenta(), fechaPromesa);
								//REPROCESO
								for(int i=0; i<3 && resultadoCHIST.contains("Error en login"); i++){
									System.out.println("REPROCESO ALTA CHIST1:"+i);
									resultadoCHIST = chist.altaCHIST(pago.getRegion(), pago.getCuenta(), fechaPromesa);
								}
								if(resultadoCHIST.equals("OK")){
									pago.setEstatusPP("OK");
									pago.setDescripcionPP("PROMESA APLICADA");
								}else{
									
									if(resultadoCHIST.equals("ER")){
										resultadoCHIST = "ERROR AL INGRESAR LA PROMESA DE PAGO";
									}else if(resultadoCHIST.contains("CL382") || resultadoCHIST.contains("CL272")){
										resultadoCHIST = "YA EXISTE UNA PROMESA DE PAGO";
									}
									
									pago.setEstatusPP("ER");
									pago.setDescripcionPP(resultadoCHIST);
								}
								
							}else{
								pago.setEstatusPP("ER");
								pago.setDescripcionPP(resultadoCHIST);
							}
						}else{
							logger.info("ERROR EN ALTA CSMAN");
							pago.setEstatusPP("ER");
							pago.setDescripcionPP(resultadoCSMAN.equals("ER") ? "ERROR AL INGRESAR LA PROMESA DE PAGO":resultadoCSMAN);
						}
					}else if(resultadoCHIST.equals("OK") || resultadoCHIST.contains("SD026") || resultadoCHIST.contains("ASAHK")){
						logger.info("CONSULTA CHIST OK");
						resultadoCHIST = chist.altaCHIST(pago.getRegion(), pago.getCuenta(), fechaPromesa);
						//REPROCESO
						for(int i=0; i<3 && resultadoCHIST.contains("Error en login"); i++){
							System.out.println("REPROCESO ALTA CHIST2:"+i);
							resultadoCHIST = chist.altaCHIST(pago.getRegion(), pago.getCuenta(), fechaPromesa);
						}
						if(resultadoCHIST.equals("OK")){
							pago.setEstatusPP("OK");
							pago.setDescripcionPP("PROMESA APLICADA");
						}else{
							//ERROR
							if(resultadoCHIST.equals("ER")){
								resultadoCHIST = "ERROR AL INGRESAR LA PROMESA DE PAGO";
							}else if(resultadoCHIST.contains("CL382") || resultadoCHIST.contains("CL272")){
								resultadoCHIST = "YA EXISTE UNA PROMESA DE PAGO";
							}
							
							logger.info("ERROR EN ALTA CHIST");
							pago.setEstatusPP("ER");
							pago.setDescripcionPP(resultadoCHIST);
						}
					}else{
						pago.setEstatusPP("ER");
						pago.setDescripcionPP(resultadoCHIST);
					}
				}else{
					pago.setEstatusPP("ER");
					pago.setDescripcionPP("NO APLICA");
				}
			}
		}catch(Exception ex){
			return false;
		}
		
		return true;
	}
}
