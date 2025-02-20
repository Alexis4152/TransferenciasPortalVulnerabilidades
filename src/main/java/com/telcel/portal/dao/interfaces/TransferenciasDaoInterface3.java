package com.telcel.portal.dao.interfaces;
import java.util.List;

import com.telcel.portal.pojos.BancomerConceptosPojo;
import com.telcel.portal.pojos.ValidadorCuentasPojo;
public interface TransferenciasDaoInterface3 {
	List<BancomerConceptosPojo> consultaByRegion(int region);
	
	List<ValidadorCuentasPojo> consultaValidadorCuentas(List<ValidadorCuentasPojo> listaValidadorCuentas);
}
