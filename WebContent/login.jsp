<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" />
<script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="ext/ext-all.js"></script>
<script type="text/javascript" src="ext/general.js"></script>
<script type="text/javascript">
 Ext.onReady(function() {
 
	 new Ext.Window({
			id:'loginWindow',			
			title:'Transferencias Interbancarias Corporativas',
			modal:true,
			closable:false,
			width:280,			
			resizable:false,
			draggable:false,			
			onEsc:function(b){ 
	            
	            },
			contentEl:'ventana'
		}).show();
 
 	   new  Ext.Button({
	      	   text:'Entrar',
	      	   width:'70',	      	                
	      	   applyTo:'login',
	      	   listeners:{
		            'click': function(){
		           		mascara(true);
		            	//alert(Ext.Ajax.serializeForm('loginForm'))
		                 ajaxDivUpdater('Login.action','parameter=template&'+Ext.Ajax.serializeForm('loginForm'),'dsplay_none_login'); 
		            }
		    	}
	      }); 

  });
</script>
<title>TIC - Transferencias Interbancarias Corporativas</title>
</head>
<body>
<div id="ventana">
<form  id="loginForm">
	<table width="50%" cellspacing="10" >
		<tr>
			<td colspan="2" align="center" style="font-weight:bold;color: red;"><c:out value="${mensajeLogin}"/></td>
		</tr>
		<tr>
			<td align="right" style="font-weight:bold;">Numero Empleado:</td>
			<td><input name="usuario" type="text" width="100" /> </td>
		</tr>	
		<tr>
			<td align="right" style="font-weight:bold;" >Contraseña: </td>
			<td><input name="password" type="password" width="100" /></td>
		</tr>		
		<tr>
			
			<td colspan="2" align="center" > <div id="login"></div> </td>
		</tr>
	</table>
	</form>
</div>
<div id="dsplay_none_login" style="display:none" ></div>
</body>
</html>
