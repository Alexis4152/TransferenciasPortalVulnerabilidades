package com.telcel.portal.factory.vo;

/**
 * @author everis 10/09/2012
 * @version 1.0
 */
public class ParametrosVo {

	private String nameQuery;
	private String region;
	private Object[] params = null;
	private Object[] paramsOut = null;
	
	 /**
	 * @return the nameQuery
	 */
	public String getNameQuery() {
		return nameQuery;
	}


	/**
	 * @param nameQuery the nameQuery to set
	 */
	public void setNameQuery(String nameQuery) {
		this.nameQuery = nameQuery;
	}


	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}


	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}


	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}


	/**
	 * @param params the params to set
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}


	/**
	 * @return the paramsOut
	 */
	public Object[] getParamsOut() {
		return paramsOut;
	}


	/**
	 * @param paramsOut the paramsOut to set
	 */
	public void setParamsOut(Object[] paramsOut) {
		this.paramsOut = paramsOut;
	}


	/**
	 * Agrega los parametros de entrada
	 * @param o
	 */
	public void addParam(Object o){
	 if (params == null) {
		 params = new Object[1];
		 params[0] = o;
	 } else {
		 Object[] temp = new Object[params.length + 1];

		 System.arraycopy(params, 0, temp, 0, params.length);

	 temp[params.length] = o;
	 params = temp;
	 }
	}
	
	/**
	 * Agrega los parametros de salida
	 * @param o
	 */
	public void addParamOut(Object o){
	 if (paramsOut == null) {
		 paramsOut = new Object[1];
		 paramsOut[0] = o;
	 } else {
		 Object[] temp = new Object[paramsOut.length + 1];

		 System.arraycopy(paramsOut, 0, temp, 0, paramsOut.length);

	 temp[paramsOut.length] = o;
	 paramsOut = temp;
	 }
	}
	
	
}
