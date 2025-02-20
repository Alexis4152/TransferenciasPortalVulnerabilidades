package com.telcel.portal.dao.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.sql.rowset.WebRowSet;
import com.telcel.portal.factory.dao.GeneralDAO;
import com.telcel.portal.factory.vo.ParametrosVo;
import com.telcel.portal.pojos.BancomerConceptosPojo;
import com.telcel.portal.pojos.ValidadorCuentasPojo;
import com.telcel.portal.util.ConstantesNumeros;
import com.telcel.portal.util.ConstantesTDDI;
import com.telcel.portal.util.ConstantesTDUI;
import com.telcel.portal.util.PropertiesFiles;
import org.apache.log4j.Logger;
import com.telcel.portal.dao.interfaces.TransferenciasDaoInterface3;

public class TransferenciasDaoImp3 implements TransferenciasDaoInterface3 {
	private static Logger logger = Logger.getLogger(TransferenciasDaoImp3.class);

	@Override
	public List<BancomerConceptosPojo> consultaByRegion(int region) {

		List<BancomerConceptosPojo> conceptos = new ArrayList<BancomerConceptosPojo>();
		GeneralDAO dao = new GeneralDAO(ConstantesNumeros.TRES);

		try {

			PropertiesFiles prop = new PropertiesFiles();
			Properties p = prop.getPropertiesConf();
			ParametrosVo params = new ParametrosVo();
			params.setNameQuery("S_PTRA_CONCEPTOS_BANCOMER");
			params.addParam(region);
			WebRowSet rowSet = dao.selectSql(params, p.getProperty(ConstantesTDUI.APLTIC));

			while (rowSet.next()) {
				BancomerConceptosPojo pojo = new BancomerConceptosPojo();
				pojo.setId(rowSet.getInt("ID_CONCEPTO"));
				pojo.setConcepto(rowSet.getString("CONCEPTO"));
				pojo.setRegion(rowSet.getInt("REGION"));
				conceptos.add(pojo);
			}

		} catch (Exception e) {
			logger.info(e.getMessage());

		}

		return conceptos;
	}

	@Override
	public List<ValidadorCuentasPojo> consultaValidadorCuentas(List<ValidadorCuentasPojo> listaValidadorCuentas) {

		String valRegion;
		String valCuenta;
		String valResponsabilidad;

		ValidadorCuentasPojo valAux;

		List<ValidadorCuentasPojo> listaCuentasResultado = new ArrayList<>();

		try {

			for (ValidadorCuentasPojo pojo : listaValidadorCuentas) {
												
				valAux = new ValidadorCuentasPojo();

				valRegion = pojo.getRegion();
				valCuenta = pojo.getCuenta();
				valResponsabilidad = pojo.getResponsabilidad();

				valResponsabilidad = valResponsabilidad(valRegion,valCuenta,valResponsabilidad);
				
				valAux.setCuenta(valCuenta);
				valAux.setRegion(valRegion);
				valAux.setResponsabilidad(valResponsabilidad);

				listaCuentasResultado.add(valAux);

			}

		} catch (Exception e) {

			logger.info(e.getMessage());
		}
		return listaCuentasResultado;
	}
	
	public String valResponsabilidad(String region, String cuenta, String responsabilidad) {
		String valRegion;
		String valCuenta;
		String valResponsabilidad = responsabilidad;
		String valCuentaNac;
		String valResponsabilidadNac;
		
		ParametrosVo auxParams;
		WebRowSet wrsRes;

		PropertiesFiles prop = new PropertiesFiles();
		Properties p = prop.getPropertiesConf();
		GeneralDAO dao = new GeneralDAO(1); // xml/desglose
		
		auxParams = new ParametrosVo();
		
		auxParams.setNameQuery("S_TRAN_RESPAGO");
		auxParams.addParam(region);
		auxParams.addParam(cuenta);
		auxParams.setRegion(region);
		
		try {
			if (responsabilidad.contains("OK")) {
				wrsRes = dao.selectSql(auxParams, p.getProperty(ConstantesTDDI.APLMOBILE1));

				if (wrsRes.next()) {

					valRegion = wrsRes.getString(1);
					valCuenta = wrsRes.getString(2);
					valResponsabilidad = wrsRes.getString(4);
					valCuentaNac = wrsRes.getString(6);
					wrsRes.close();

					if (valCuentaNac == null || valCuentaNac.isEmpty()) {
						if (valResponsabilidad.toUpperCase().equals("S")) {
							valResponsabilidad = "S";
						} else {
							wrsRes.clearParameters();
							auxParams = new ParametrosVo();
							auxParams.setNameQuery("S_PTRA_CTASPADRE");
							auxParams.addParam(valRegion);
							auxParams.addParam(valCuenta);
							auxParams.setRegion(valRegion);
							wrsRes = dao.selectSql(auxParams, p.getProperty(ConstantesTDDI.APLMOBILE1));
							if (wrsRes.next()) {
								valResponsabilidad = wrsRes.getString(1);
								//Validamos si la cuenta con responsabilidad pertenece a una cuenta nacional.
								valResponsabilidadNac = valResponsabilidad(valRegion,valResponsabilidad, "OKNac");
								//Si identifica cuenta nacional y no obtuvo error, se asigna el valor de valResponsabilidadNac
								if(!(valResponsabilidadNac.equals("S") || 
										valResponsabilidadNac.contains("Los datos de esta cuenta no arrojaron resultados.") ||
										valResponsabilidadNac.equals("0")))
									valResponsabilidad = valResponsabilidadNac;
							}														 
						}
					} else {
						if(valCuenta.equals(valCuentaNac))
							valResponsabilidad = "S";
						else
							valResponsabilidad = valCuentaNac;
					}

				} else
					valResponsabilidad = "Los datos de esta cuenta no arrojaron resultados.";

				wrsRes.close();
			}
		} catch (Exception e) {
			valResponsabilidad = "Los datos de esta cuenta no arrojaron resultados.";
		}
		
		
		return valResponsabilidad;
	}
}
