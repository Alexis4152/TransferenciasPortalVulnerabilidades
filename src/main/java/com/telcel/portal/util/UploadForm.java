package com.telcel.portal.util;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;



public class UploadForm extends ActionForm {
   

   	private static final long serialVersionUID = 2847928045491174394L;
	private FormFile theFile;
    protected String filePath;
    public FormFile getTheFile() {
        return theFile;
    }
   

    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }
}