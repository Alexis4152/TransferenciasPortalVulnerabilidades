package com.telcel.portal.lector;

/**
 * 
 */
import com.telcel.portal.factory.vo.ParametrosVo;

/**
 * Interfaz lectora de sentencias Sql, contiene un metodo por medio del cual se pueden
 * obtener las sentencias Sql que pueden ser leidas desde un archivo xml, para este caso,
 * o puede implementarse para ser leido desde algun otro tipo de archivo.
 * 
 * @author everis
 *
 */
public interface ILectorSentenceSQL {

	/**
	 * Metodo que obtiene la sentencia Sql desde un archivo, enviando de como parametro
	 * el identificador de la instruccion, por medio de la cual sera encontrada
	 * en el archivo. Este metodo puede ser implementado para utilizar diferentes tipos
	 * de archivos.
	 * @return StringBuffer: sentencia Sql
	 */
	StringBuffer getSentenceSql(ParametrosVo params, Integer modulo);
	
}
