package com.telcel.portal.util;

import java.util.ArrayList;
import java.util.List;

import com.telcel.portal.pojos.TransferenciaPojo;

public class TransferenciasUtil {

	public static List<TransferenciaPojo> clonarTransferenciaPojo(TransferenciaPojo original, int cantidad){
		List<TransferenciaPojo> lista=new ArrayList<TransferenciaPojo>();
		for (int i = 0; i < cantidad; i++) {
			TransferenciaPojo pojo=new TransferenciaPojo();

			pojo.setIdBanco(original.getIdBanco());
			pojo.setDateFecha(original.getDateFecha());
			pojo.setCuenta(original.getCuenta());
			pojo.setImporte(original.getImporte());
			pojo.setReferenciaBanco(original.getReferenciaBanco());
			pojo.setReferenciaCliente(original.getReferenciaCliente());
			pojo.setEstatus(original.getEstatus());
			pojo.setRegion(original.getRegion());
			lista.add(pojo);
		}
		
		
		return lista;
		
	}
}
