package com.telcel.portal.util;

public class ValidadorCuentasPojo {
	
	private String region;
	
	private String[] cuentasParaValidar;
	
	public ValidadorCuentasPojo(){
		cuentasParaValidar = null;
	}

	public String[] getCuentasParaValidar() {
		return cuentasParaValidar;
	}

	public Double[] getCuentasParaValidarNumeric() {
		Double[]  cuentasParaValidarNumeric = new Double[cuentasParaValidar.length];
			System.arraycopy(cuentasParaValidar, 0, cuentasParaValidarNumeric, 0, cuentasParaValidar.length);
	
		return cuentasParaValidarNumeric;
	}
	public String[] getCuentasParaValidarString() {
		String[]  cuentasParaValidarNumeric = new String[cuentasParaValidar.length];
			System.arraycopy(cuentasParaValidar, 0, cuentasParaValidarNumeric, 0, cuentasParaValidar.length);

		return cuentasParaValidarNumeric;
	}
	public void setCuentasParaValidar(String[] cuentasParaValidar) {
		this.cuentasParaValidar = cuentasParaValidar;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	

	
	
	

}
