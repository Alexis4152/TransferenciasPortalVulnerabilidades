/**
 * 
 */
package com.telcel.portal.util;

import org.apache.log4j.Logger;

/**
 * @author everis 10/09/2012
 * @version 1.0
 *
 */
public class FactoryUtils {
	private static Logger logger = Logger.getLogger(FactoryUtils.class);
	/**
	 * Transforma las regiones a enteros
	 * @param region
	 * @return int
	 */

	
	private static final double DMLL = 10000000;
	private static final double VMLL = 20000000;
	private static final double TMLL = 30000000;
	private static final double CMLL = 40000000;
	private static final double CIMLL = 50000000;
	private static final double SMLL = 60000000;
	private static final double SEMLL = 70000000D;
	private static final double OMLL = 80000000D;
	
	private static final double  DNMLL = 19999999;
	private static final double  VNMLL = 29999999;
	private static final double  TNMLL = 39999999;
	private static final double  CNMLL = 49999999;
	private static final double  CINMLL = 59999999;
	private static final double  SNMLL = 69999999;
	private static final double  SENMLL = 79999999;
	private static final double  ONMLL = 89999999;


	public int getRegionInt(String region){
		int reg = ConstantesNumeros.CERO;
		if(region.equals("R01") || region.equals("R1")){
			reg = ConstantesNumeros.UNO;
		}else if(region.equals("R02") || region.equals("R2")){
			reg = ConstantesNumeros.DOS;
		}else if(region.equals("R03") || region.equals("R3")){
			reg = ConstantesNumeros.TRES;
		}else if(region.equals("R04") || region.equals("R4")){
			reg = ConstantesNumeros.CUATRO;
		}else if(region.equals("R05") || region.equals("R5")){
			reg = ConstantesNumeros.CINCO;
		}else if(region.equals("R06") || region.equals("R6")){
			reg = ConstantesNumeros.SEIS;
		}else if(region.equals("R07") || region.equals("R7")){
			reg = ConstantesNumeros.SIETE;
		}else if(region.equals("R08") || region.equals("R8")){
			reg = ConstantesNumeros.OCHO;
		}else if(region.equals("R09") || region.equals("R9")){
			reg = ConstantesNumeros.NUEVE;
		}else if(region.equals("")){
			reg = ConstantesNumeros.NUEVE;
		}
		return reg;
	}

	
	/**
	 * Construye los parametros IN para la sentencia
	 * @param in
	 * @param tipoDato
	 * @return StringBuffer
	 */
	public StringBuffer getParamIn(String[] in, int tipoDato){
		StringBuffer inAux = new StringBuffer();
		for( int i=0; i< in.length; i++) {
			if(i>0) {
				inAux.append(",");
			}
			if(tipoDato == ConstantesNumeros.UNO || tipoDato == ConstantesNumeros.DOS){
				inAux.append("'");	
			}
			inAux.append(in[i]);	
			if(tipoDato == ConstantesNumeros.UNO || tipoDato == ConstantesNumeros.DOS){
				inAux.append("'");
			}
		}
		
		return inAux;
	}
	
	/**
	 * Obtiene el nombre de la sequencia a partir del folio 
	 * @param folio
	 * @return
	 */
	public String getSequenceName(String folio){
		String sequencia = "";
		String[] field = folio.split("-");
		sequencia = field[0]+(field.length > ConstantesNumeros.DOS ? field[1]:"");
		return sequencia;
	}

	 public String getRegionString(String cta){
		 String region = null;
		 
		 double cuenta=Double.parseDouble(cta);
		 
		 if(cuenta >= DMLL && cuenta <= DNMLL){
             region="R01";
         } else if(cuenta >= VMLL && cuenta <= VNMLL){
             region="R02";
         }else if(cuenta >= TMLL && cuenta <= TNMLL){
             region="R03";
         }else if(cuenta >= CMLL && cuenta <= CNMLL){
             region="R04";
         }else if(cuenta >= CIMLL && cuenta <= CINMLL){
             region="R05";
         }else if(cuenta >= SMLL && cuenta <= SNMLL){
             region="R06";
         }else if(cuenta >= SEMLL && cuenta <= SENMLL){
             region="R07";
         } else if(cuenta >= OMLL && cuenta <= ONMLL){
             region="R08";
         }else if(cuenta < DMLL){
			 region="R09";
         } else{
        	 logger.info("La cuenta no exite");
         }
		 return region;
	 }
	
	
}
