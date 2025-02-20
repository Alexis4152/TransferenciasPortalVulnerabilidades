package com.telcel.portal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.telcel.portal.pojos.PagoPojo;

public class DivisorCuentas {
	
	private static final int CINCUENTA = 50;
	
	@SuppressWarnings("unchecked")
	public   List<ValidadorCuentasPojo> divideCuentas(List <PagoPojo> pagoPojoList){
		
	
	    int cuentas=0;
	    String region="";
		ArrayList<PagoPojo> listaCuentas= new ArrayList<PagoPojo>();
		ArrayList<List> listaListas = new ArrayList<List>();
		ArrayList<ValidadorCuentasPojo> listFinal = new ArrayList<ValidadorCuentasPojo>();
	
		
		Collections.sort(pagoPojoList);
		if(pagoPojoList==null ||pagoPojoList.size()==0 ){
			return listFinal;
		}
		
		boolean primero=true;
		try {
			
	
		for(PagoPojo pagoPojo:pagoPojoList ){	    		
    		if(pagoPojo.getTipo()!=null && pagoPojo.getTipo().equals("CU")){
    			
    			cuentas++;
    			
    			if(primero){
    				region=pagoPojo.getRegion();
    			}
    			
    			if(pagoPojo.getRegion().equals(region) && cuentas < CINCUENTA){
    				
    				listaCuentas.add(pagoPojo);
    				
    			}else{
    				
    				listaListas.add(listaCuentas);
    				listaCuentas= new ArrayList();
    				cuentas=0;
    				listaCuentas.add(pagoPojo);
    				
    				
    			}
    			
    			region=pagoPojo.getRegion();
    			
    				
    			
    		}
		
    		primero=false;
		}
		
		if(listaCuentas!=null){
			listaListas.add(listaCuentas);
		}
		
		String[] cuentasParaValidar;
		
		for(List<PagoPojo> array:listaListas){
			
			cuentasParaValidar = new String[array.size()];
			int index=0;
			for(PagoPojo pojo:array){
				
				cuentasParaValidar[index] = pojo.getCuenta();
			
				index++;
			}
			if(cuentasParaValidar!=null && array.size()>=1){
			ValidadorCuentasPojo pojo= new ValidadorCuentasPojo();
			pojo.setRegion(array.get(0).getRegion());
			pojo.setCuentasParaValidar(cuentasParaValidar);
			
			listFinal.add(pojo);
			}
		}
		} catch (RuntimeException  e) {
			return null;
		}
		
		
		return listFinal;
	}

}
