package com.telcel.portal.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.telcel.portal.pojos.TransferenciaPojo;

public class DivisorTransferenciasUsuario {
	public static Map<String, List<TransferenciaPojo>> separaTransferencias(List<TransferenciaPojo> listaDetalleTransferencia){
		Map<String, List<TransferenciaPojo>> mapaTransferencias = new HashMap<String, List<TransferenciaPojo>>();
		for(TransferenciaPojo transferenciaPojo : listaDetalleTransferencia) {
			if(!mapaTransferencias.containsKey(transferenciaPojo.getUsuario())) {
				List<TransferenciaPojo> lista = new ArrayList<TransferenciaPojo>();
				lista.add(transferenciaPojo);
				mapaTransferencias.put(transferenciaPojo.getUsuario(), lista);
			}else {
				mapaTransferencias.get(transferenciaPojo.getUsuario()).add(transferenciaPojo);
			}
		}
		return mapaTransferencias;
	}
	
	public static void validaRechazadas(List<TransferenciaPojo> listaDetalleTransferencia, Map<String, List<TransferenciaPojo>> mapaTransferencias) {
		DecimalFormat formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
		for(TransferenciaPojo transferenciaPojo : listaDetalleTransferencia) {
			Set<TransferenciaPojo> set = new HashSet<TransferenciaPojo>(mapaTransferencias.get(transferenciaPojo.getUsuario()));
			if(set.size() < mapaTransferencias.get(transferenciaPojo.getUsuario()).size()) {
				for(TransferenciaPojo tt : findDuplicates(mapaTransferencias.get(transferenciaPojo.getUsuario()))) {
					mapaTransferencias.get(transferenciaPojo.getUsuario()).remove(mapaTransferencias.get(transferenciaPojo.getUsuario()).indexOf(tt));
					transferenciaPojo.setIdtransferencia(transferenciaPojo.getIdtransferencia()-1);
					transferenciaPojo.setImporte(transferenciaPojo.getImporte()-tt.getImporte());
					if (transferenciaPojo.getImporte() != null) {
						formateador = new DecimalFormat(ConstantesTDUI.FORMATODECIMAL);
						transferenciaPojo.setImporteString(formateador.format(transferenciaPojo.getImporte()));
					}
					mapaTransferencias.get(transferenciaPojo.getUsuario()).get(mapaTransferencias.get(transferenciaPojo.getUsuario()).lastIndexOf(tt)).setRechazado("SI");
				}
			}
		}
	}
	
	private static List<TransferenciaPojo> findDuplicates(List<TransferenciaPojo> listContainingDuplicates) {
		 
		final List<TransferenciaPojo> setToReturn = new ArrayList<TransferenciaPojo>();
		final Set<TransferenciaPojo> set1 = new HashSet<TransferenciaPojo>();
 
		for (TransferenciaPojo t : listContainingDuplicates) {
			if (!set1.add(t)) {
				setToReturn.add(t);
			}
		}
		return setToReturn;
	}
}
