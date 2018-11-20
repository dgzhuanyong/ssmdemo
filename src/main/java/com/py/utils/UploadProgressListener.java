package com.py.utils;

import java.text.NumberFormat;

import org.apache.commons.fileupload.ProgressListener;
import org.apache.shiro.session.Session;

public class UploadProgressListener implements ProgressListener {
	
	
    public UploadProgressListener(Session session) {  
        this.session = session;  
    }
    
    
	private Session session;  
    private long kiloBytes=-1;
    

	@Override
	public void update(long PBytesRead, long PContentLength, int PItems) {
		Long KBytes=PBytesRead/1024;  
        if(kiloBytes==KBytes){return;}  
        kiloBytes=KBytes;  
        //获取上传进度的百分比  
        double read=((double)KBytes)/(PContentLength/1024);
        //getNumberInstance 数字  getCurrencyInstance货币  getPercentInstance 百分数
        NumberFormat nf=NumberFormat.getPercentInstance();
        String percentage = nf.format(read);
        session.setAttribute("percentage", percentage);
	}

}
