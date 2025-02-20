<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <style type=text/css>
        #fi-button-msg {
            border: 2px solid #ccc;
            background: #eee;                     
        }
    </style>

<script type="text/javascript">
 Ext.onReady(function(){ 

  Ext.getCmp('<c:out value="${panelId}"/>').setTitle('<c:out value="${titulo}"/>');

 });
  

 function resultado(texto){
 mascara(false);
 var el = Ext.fly('fi-button-msg');
                el.update(texto);
                if(!el.isVisible()){
                    el.slideIn('t', {
                        duration: .2,
                        easing: 'easeIn',
                        callback: function(){
                            el.highlight();
                        }
                    });
                }else{
                    el.highlight();
                }
  
  Ext.DomHelper.overwrite('fileref','<input name="theFile" id="theFile" size="20" type="file" /><br />	');
 }
 function validaCarga(){
 
 	var file;
 	var fileSplit;
 
	 if(Ext.getDom('theFile').value==''){
	 
		 Ext.MessageBox.alert('Estatus','Selecciona un archivo' );
	 	 return;
	 }else{
	 
	/* file=Ext.getDom('theFile').value;
	 fileSplit=file.split('.') 
		
		if(fileSplit[1]!='xls'){
			Ext.MessageBox.alert('Estatus','Selecciona un archivo Excel' );
			return;
		}
	*/  
	 
	 }
 
    mascara(true);
 	document.getElementById('file_upload_form').submit();
  
 }
 
 document.getElementById('file_upload_form').target = 'upload_target';
</script>

<div>

<div class="instrucciones">	
	Instrucciones:<br/>
	-Para realizar la carga debes seleccionar el archivo con la información de los numeros de documento de cada lote y dar clic en Cargar.<br/>
</div>
<br/>
<form   id="file_upload_form" method="post"   enctype="multipart/form-data" action="CargaArchivoCompensacion.action">
<table align="center" width="500" border="0">
	<tr>
		<td width="150px;"></td>
		<td></td>
	</tr>
	<tr>
		<td >Archivo:</td>
		<td class="left">
		<div id="fileref" >
		<input name="theFile" id="theFile" size="20" type="file" /><br />	
		</div>
		</td>
	</tr>
	<tr>	
		<td></td>	
		<td class="left">
		<input type="button" onclick="validaCarga()"  name="action" value="Cargar" />
		</td>
	</tr>
</table>

	
	<br />
	<iframe id="upload_target" name="upload_target" src="" style="width:0;height:0;border:1px solid #fff;"></iframe>
</form>
<div   align="center" id="fi-button-msg" style="display:none;"></div>

</div>