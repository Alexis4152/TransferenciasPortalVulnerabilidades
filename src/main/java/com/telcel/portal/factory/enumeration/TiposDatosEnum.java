/**
 * 
 */
package com.telcel.portal.factory.enumeration;

/**
 * @author everis 05102012
 * @version 1.0
 *
 */
public enum TiposDatosEnum {
	TYPE_STRING(1),
	TYPE_CHAR(2),
	TYPE_NUMERIC(3);
	
	private final int type;

	
	TiposDatosEnum(int type) {
		this.type = type;
	}


	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	
}
