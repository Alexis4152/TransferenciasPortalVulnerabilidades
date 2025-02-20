package com.telcel.portal.lector.impl;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.telcel.portal.factory.vo.ParametrosVo;
import com.telcel.portal.lector.ILectorSentenceSQL;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.FactoryUtils;
import com.telcel.portal.util.PropertiesFiles;



/**
 * Clase que implementa la interface ILectorSentenceSQL, por medio de la cual
 * permite leer las sentencias Sql desde un archivo xml
 * 
 * @author everis 04-sep-2012
 * @version 1.0
 */
public class LectorSentenceXML implements ILectorSentenceSQL {

	private static Logger  logger = Logger.getLogger(LectorSentenceXML.class);
    private PropertiesFiles prop = new PropertiesFiles();
    private Properties p = prop.getPropertiesConf();
	
	/* (non-Javadoc)
	 * @see com.telcel.lector.ILectorSentenceSQL#getSentenceSql(java.lang.String)
	 */
	public StringBuffer getSentenceSql(ParametrosVo params, Integer modulo) {
		StringBuffer instruccion = null;
		Integer mod = modulo!=null?modulo: ConstantesNumeros.NUEVE;
	    try {
	          String type = getTypeSql(params.getNameQuery());
	          if(type!=null){

	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            InputStream in = getClass().getResourceAsStream((new StringBuilder(String.valueOf(getRutaXml(mod)))).append(getDocXml(params.getNameQuery()).trim()).toString());
	            Document doc = docBuilder.parse(in);
	            
	            logger.debug(" - Documento: " + getDocXml(params.getNameQuery()).trim());
	            logger.debug(" - ID Query: " + params.getNameQuery());
	            
	            // normalize text representation
	            doc.getDocumentElement ().normalize ();
	            logger.debug(" - Elemento raiz del documento es: " + doc.getDocumentElement().getNodeName());
	            NodeList listOfSelect = doc.getElementsByTagName(type);
	
	            logger.debug(" - Total no of "+type+" : " + listOfSelect.getLength());
	            
	            String name	= null;
	            for(int s=0; s<listOfSelect.getLength() ; s++){
	            	
	                Node firstPersonNode = listOfSelect.item(s);
	                
	                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
	                	
	                    Element firstPersonElement	= (Element)firstPersonNode;
	                    //Name of query
	                    NodeList firstNameList		= firstPersonElement.getElementsByTagName("name");
	                    Element firstNameElement	= (Element)firstNameList.item(0);
	                    NodeList textFNList			= firstNameElement.getChildNodes();
	                    name						= ((Node)textFNList.item(0)).getNodeValue().trim();
	                    if(name==null?false:name.equals("")?false:true){
		                    if(name.equals(params.getNameQuery().trim())){
			                    //Instruction
		                    	logger.debug(" - "+type.toUpperCase(Locale.getDefault())+" Nombre : " + name);
		                    	
			                    NodeList lastNameList	= firstPersonElement.getElementsByTagName("instruction");
			                    Element lastNameElement = (Element)lastNameList.item(0);
			                    NodeList textLNList		= lastNameElement.getChildNodes();
			                    StringBuffer value		= new StringBuffer();
			                    instruccion				= new StringBuffer();
			                    value.append(((Node)textLNList.item(0)).getNodeValue().trim());
			                    logger.debug(" - Instruccion antes de formatear: " + value);
			                    
			                    instruccion	=	getFormatSentence(value, params);
			                    logger.debug(" - Instruccion despues de formatear: " + instruccion);
			                    break;
		                    }
	                    } else {
	                    name = null;
	                    }
	                    
	                }//end of if clause
	                
	            }//end of for loop with s var
		          if(name==null){
			      instruccion = null;
			      logger.info(" - ERROR: El identificador del query no se ha encontrado en el archivo " + getDocXml(params.getNameQuery()).trim());
			      }
	          } else {
	        	  instruccion = null;
			      logger.info(" - ERROR: El tipo de sentencia " + params.getNameQuery().trim() + "NO se encuentra definido. El identificador debe iniciar con S_ para SELECT, I_ para INSERT, U_  para UPDATE, D_ para DELETE, Q_ para SEQUENCE");
	          }

	            
        }catch (SAXParseException err) {
        	
        	logger.info(" - PARSING ERROR" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
        	logger.debug(" - PARSING ERROR " + err.getMessage ());
	        
        }catch (SAXException e) {
        	
        	logger.info(" - SAX ERROR:  Se ha producido un error al leer el archivo xml - CAUSA: " + e.getMessage());
        	logger.debug(" - SAX ERROR:  Se ha producido un error al leer el archivo xml - CAUSA: " + e.getMessage());
	        Exception x = e.getException ();
	        ((x == null) ? e : x).printStackTrace ();
	        
        }catch (Exception t) {
        	
        	logger.info(" - ERROR: Se ha producido un error al leer el archivo xml - CAUSA: " + t.getMessage());
        	logger.debug(" - ERROR: " + t.getMessage());
        	t.printStackTrace ();
        }

		return instruccion;
	}
	
	/**
	 * Metodo que regresa el tipo de instruccion que va a ser lanzada a la base de datos
	 * @param nameQuery
	 * @return String: tipo de instruccion
	 */
	private String getTypeSql(String nameQuery){
	String id = nameQuery.substring(0, 2);
	return id.equals("S_")?"select":id.equals("I_")?"insert":id.equals("U_")?"update":id.equals("D_")?"delete":id.equals("Q_")?"sequence":null;
	}

	/**
	 * Metodo que regresa el nombre del archivo del que se va a obtener la instruccion Sql
	 * dependiendo si es consulta, actualizacion, insercion, elimiacion o secuencia
	 * @param nameQuery
	 * @return String: nombre del archivo xml
	 */
	private String getDocXml(String nameQuery){
	String id = nameQuery.substring(0, 2);
    return id.equals("S_")?"SentenciasSQL-select.xml":id.equals("I_")?"SentenciasSQL-insert.xml":id.equals("U_")?"SentenciasSQL-update.xml":id.equals("D_")?"SentenciasSQL-delete.xml":id.equals("Q_")?"SentenciasSQL-sequence.xml":null;
	}

	/**
	 * Metodo que da formato a la sentencia Sql dejandola cambiando los prefijos 
	 * corporativos de mercado y los caracteres logicos
	 * @param instruccion
	 * 			Sentencia SQL que se lee del archivo xml
	 * @param region
	 * 			Region por medio de la cual se coloca la instancia de bd de la
	 * 			cual extraera los datos
	 * @return StringBuffer
	 * 			Regresa la sentencia Sql formateada y lista para realizar la operacion
	 */
	private StringBuffer getFormatSentence(StringBuffer instruccion, ParametrosVo params){
		StringBuffer sentenciaFormateada = new StringBuffer();
		String cadenaInstruccion = instruccion.toString();
		if(params.getNameQuery().contains("Q_")){
			cadenaInstruccion = cadenaInstruccion.replaceFirst("ID_SEQUENCE",params.getParams()[0].toString());
		}else{
			   if(params.getRegion()==null?false:params.getRegion().equals("")?false:true){
			   int region = new FactoryUtils().getRegionInt(params.getRegion().trim());
			   cadenaInstruccion = cadenaInstruccion.replaceAll(p.getProperty("CORP.PREFIX").trim(), getInstanciaXRegion(region, p.getProperty("sufix.corpo")));
			   cadenaInstruccion = cadenaInstruccion.replaceAll(p.getProperty("MKT.PREFIX").trim(), getInstanciaXRegion(region, p.getProperty("sufix.mkt")));
			   }
			   cadenaInstruccion = cadenaInstruccion.replaceAll(p.getProperty("CAR.DIFERENTE.QUE").trim(), p.getProperty("OPERADOR.DIFERENTE.QUE").trim());
			   cadenaInstruccion = cadenaInstruccion.replaceAll(p.getProperty("CAR.MAYOR.IGUAL").trim(), p.getProperty("OPERADOR.MAYOR.IGUAL").trim());
			   cadenaInstruccion = cadenaInstruccion.replaceAll(p.getProperty("CAR.MENOR.IGUAL").trim(), p.getProperty("OPERADOR.MENOR.IGUAL").trim());
			   cadenaInstruccion = cadenaInstruccion.replaceAll(p.getProperty("CAR.MAYOR.QUE").trim(), p.getProperty("OPERADOR.MAYOR.QUE").trim());
			   cadenaInstruccion = cadenaInstruccion.replaceAll(p.getProperty("CAR.MENOR.QUE").trim(), p.getProperty("OPERADOR.MENOR.QUE").trim());
			   if(params.getParams() != null && params.getParamsOut() == null){
					   cadenaInstruccion = getInstructionWithParam(new StringBuffer(cadenaInstruccion),params).toString();
			   }
		}
		sentenciaFormateada.append(cadenaInstruccion);
			   
		return sentenciaFormateada;
	}
	
	/**
	 * Coloca los parametros en la cadena de la instruccion
	 * @param instruccion
	 * @param vo
	 * @return StringBuffer
	 */
	private StringBuffer getInstructionWithParam(StringBuffer instruccion, ParametrosVo vo){
		StringBuffer newInstruccion = new StringBuffer();
		String strDatos=instruccion.toString();
		StringTokenizer tokens=new StringTokenizer(strDatos, "?");
	    int nDatos=tokens.countTokens();
	    String[] datos=new String[nDatos];
	    int i=0;
	    int cant=vo.getParams().length;
	    while(tokens.hasMoreTokens()){
	       String str=tokens.nextToken();
	       datos[i]=str;
	       if(i<cant){
	    	   String datosParam = datos[i]+vo.getParams()[i];
	    	   newInstruccion.append(datosParam);
	       }else{
	    	if(datos[i].length()>0){
	    		newInstruccion.append(datos[i]);
	    	}
	       	break;
	       }
	       i++;
	    }
		return newInstruccion;
	}
	
	
	/**
	 * Sustituye la instancia por medio de la cual se va realizar la consulta de
	 * acuerdo a la region.  
	 * @param region
	 * @param sufix
	 * @return
	 */
	private String getInstanciaXRegion(int region, String sufix){
		String instancia = null;
		switch(region){
		case ConstantesNumeros.UNO:
			instancia = p.getProperty("r01."+sufix.trim());
			break;
		case ConstantesNumeros.DOS:
			instancia = p.getProperty("r02."+sufix.trim());
			break;
		case ConstantesNumeros.TRES:
			instancia = p.getProperty("r03."+sufix.trim());
			break;
		case ConstantesNumeros.CUATRO:
			instancia = p.getProperty("r04."+sufix.trim());
			break;
		case ConstantesNumeros.CINCO:
			instancia = p.getProperty("r05."+sufix.trim());
			break;
		case ConstantesNumeros.SEIS:
			instancia = p.getProperty("r06."+sufix.trim());
			break;
		case ConstantesNumeros.SIETE:
			instancia = p.getProperty("r07."+sufix.trim());
			break;
		case ConstantesNumeros.OCHO:
			instancia = p.getProperty("r08."+sufix.trim());
			break;
		case ConstantesNumeros.NUEVE:
			instancia = p.getProperty("r09."+sufix.trim());
			break;
		default:
			instancia = p.getProperty("r09."+sufix.trim()+".des");
			break;
		}
		
		return instancia.trim();
	}
	
	private String getRutaXml(Integer modulo){
		String url = "";
		
		switch(modulo){
		case ConstantesNumeros.UNO:url=p.getProperty("tic.ruta.xml.Des");
				break;
		case ConstantesNumeros.DOS:url=p.getProperty("tic.ruta.xml.Rep");
				break;
		case ConstantesNumeros.TRES:url=p.getProperty("tic.ruta.xml.Gen");
				break;
		default:url=p.getProperty("tic.ruta.xml");
				break;
		}
		
		return url;
	}
	
	
}