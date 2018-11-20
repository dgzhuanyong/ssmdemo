package com.py.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;

import sun.misc.BASE64Decoder;


public class FileUtils {
	
	/**
	 * 上传单个文件-- 需要form表单的name 
	 * 0success -1请选择文件 -2上传目录不存在 -3上传目录没有写权限-4上传文件大小超过限制-5上传文件扩展名是不允许的扩展名 -6上传失败
	 * @throws FileUploadException 
	 */
	public static Map<String,Object> uploadFile(HttpServletRequest request,String name) throws FileUploadException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		// 文件保存目录路径
		String savePath = Utils.getProperties("file_upload_path");
		// 定义允许上传的文件扩展名
		String fileExtStr = "gif,jpg,jpeg,png,bmp,swf,flv,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,7z,pdf";
		// 最大文件大小
		long maxSize = Long.parseLong(Utils.getProperties("file_upload_size"));
		// 创建文件夹
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 检查文件
		if (!ServletFileUpload.isMultipartContent(request)) {
			resultMap.put("state", "-1");
			return resultMap;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			resultMap.put("state", "-2");
			return resultMap;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			resultMap.put("state", "-3");
			return resultMap;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				String formName = item.getFieldName();
				//文件的名字与from表单不一致 不上传
				if(!formName.equals(name)){
					item.delete();//删除临时文件 继续循环
					continue;
				}
				// 检查文件大小
				if (item.getSize() > maxSize) {
					resultMap.put("state", "-4");
					return resultMap;
				}
				resultMap.put("size", item.getSize());
				// 检查扩展名
				String fileName = item.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (fileExtStr.indexOf(fileExt) < 0) {
					resultMap.put("state", "-5");
					return resultMap;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
				} catch (Exception e) {
					resultMap.put("state", "6");
					return resultMap;
				}
				resultMap.put("link", newFileName);
			}
		}
		resultMap.put("state", "0");
		return resultMap;
	}
	
	
	/**
	 * 上传单个文件带监听-- 需要form表单的name 
	 * 0success -1请选择文件 -2上传目录不存在 -3上传目录没有写权限-4上传文件大小超过限制-5上传文件扩展名是不允许的扩展名 -6上传失败
	 * @throws FileUploadException 
	 */
	public static Map<String,Object> uploadFileMonitor(HttpServletRequest request,String name) throws FileUploadException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		// 文件保存目录路径
		String savePath = Utils.getProperties("file_upload_path");
		// 定义允许上传的文件扩展名
		String fileExtStr = "gif,jpg,jpeg,png,bmp,swf,flv,swf,flv,mp4,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,7z,pdf";
		// 最大文件大小
		long maxSize = Long.parseLong(Utils.getProperties("file_upload_size"));
		// 创建文件夹
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 检查文件
		if (!ServletFileUpload.isMultipartContent(request)) {
			resultMap.put("state", "-1");
			return resultMap;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			resultMap.put("state", "-2");
			return resultMap;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			resultMap.put("state", "-3");
			return resultMap;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(savePath)); 
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		//文件上传进度的监听
		UploadProgressListener listener=new UploadProgressListener(SecurityUtils.getSubject().getSession());
		upload.setProgressListener(listener);
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				String formName = item.getFieldName();
				//文件的名字与from表单不一致 不上传
				if(!formName.equals(name)){
					item.delete();//删除临时文件 继续循环
					continue;
				}
				// 检查文件大小
				if (item.getSize() > maxSize) {
					resultMap.put("state", "-4");
					return resultMap;
				}
				resultMap.put("size", item.getSize());
				// 检查扩展名
				String fileName = item.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (fileExtStr.indexOf(fileExt) < 0) {
					resultMap.put("state", "-5");
					return resultMap;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				String uploadFile = savePath+newFileName;
				try {
					File uploadedFile = new File(uploadFile);
					item.write(uploadedFile);
				} catch (Exception e) {
					e.printStackTrace();
					resultMap.put("state", "-6");
					return resultMap;
				}
				resultMap.put("link", newFileName);
			}
		}
		resultMap.put("state", "0");
		return resultMap;
	}
	
	
	/**
	 * base64转化为图片
	 * @param pic
	 * @return
	 * @throws Exception
	 */
	public static String base64ToPiC(String base64data) throws Exception{
		if(base64data.indexOf(",") > -1) {
			base64data = base64data.split(",")[1];
		}
		String newFileName = "";
		BASE64Decoder base = new BASE64Decoder();
        byte[] decode = base.decodeBuffer(base64data);
        // 图片输出路径
        String savePath = Utils.getProperties("file_upload_path");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + ".jpg";
		File path = new File(savePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File file = new File(savePath,newFileName);
		file.createNewFile();
        // 定义图片输入流
        InputStream fin = new ByteArrayInputStream(decode);
        // 定义图片输出流
        FileOutputStream fout = new FileOutputStream(file);
        // 写文件
        byte[] b = new byte[1024];
        int length=0;
		while((length = fin.read(b))>0){
		    fout.write(b, 0, length);
		}
        fin.close();
        fout.close();
		return newFileName;
	}
	
	
	/**
	 * url下载图片
	 * @param urlList
	 * @return
	 * @throws Exception
	 */
	public static String urlDownloadPic(String urlAddress) throws Exception {
		String newFileName = "";
		// 图片输出路径
        String savePath = Utils.getProperties("file_upload_path");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + ".jpg";
		File path = new File(savePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File file = new File(savePath,newFileName);
		file.createNewFile();
        URL url = null;
        try {
            url = new URL(urlAddress);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFileName;
    }
	
	
	
}
