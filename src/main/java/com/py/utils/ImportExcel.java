package com.py.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;

public class ImportExcel {
	//输入流
	public static InputStream inputStream;
	//扩展名
	public static String prefix;
	

	/**
	 * 处理类只导入不存文件
	 * 
	 * @param filePath
	 * @param clazz
	 * @return
	 * @throws FileNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IOException
	 * @throws IntrospectionException
	 * @throws InvalidFormatException
	 */
	public static LinkedList<Object> handle(HttpServletRequest request,String name,Class clazz)
			throws FileNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InstantiationException, IOException, IntrospectionException, InvalidFormatException {
		String result = "";
		try {
			result = uploadFile(request, name);
		} catch (FileUploadException e) {
			return null;
		}
		if(result.equals("0")){
			if (prefix.equals("xls")) {
				return parseXLS(inputStream, clazz,request);
			} else {
				return parseXLSX(inputStream, clazz,request);
			}
		}else{
			return null;
		}
	}

	
	
	/**
	 * 上传单个文件只导入不存文件-- 需要form表单的name 
	 * 0success -1请选择文件 -2上传目录不存在 -3上传目录没有写权限-4上传文件大小超过限制-5上传文件扩展名是不允许的扩展名 -6上传失败
	 * @throws FileUploadException 
	 */
	public static String uploadFile(HttpServletRequest request,String name) throws FileUploadException{
		// 文件保存目录路径
		String savePath = Utils.getProperties("file_upload_path");
		// 定义允许上传的文件扩展名
		String fileExtStr = "xls,xlsx";
		// 创建文件夹
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 检查文件
		if (!ServletFileUpload.isMultipartContent(request)) {
			return "-1";
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			return "-2";
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			return "-3";
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
				// 检查扩展名
				String fileName = item.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (fileExtStr.indexOf(fileExt) < 0) {
					return "-5";
				}
				//赋值扩展名和输入流
				prefix=fileExt;
				try {
					inputStream = item.getInputStream();
				} catch (IOException e) {
					return "-6";
				}
			}
		}
		return "0";
	}
	

	/**
	 * 反射类
	 * 
	 * @param obj
	 * @param paramNumber
	 * @param value
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object reflect(Object obj, int paramNumber, Object value) throws InstantiationException,
			IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException {
		Field[] fields = obj.getClass().getDeclaredFields();// 获取属性名
		// 返回的是一个参数类型
		String type = fields[paramNumber].getGenericType().toString();
		// 返回的是一个类对象
		// Class classType = field.getType();
		PropertyDescriptor pd = new PropertyDescriptor(fields[paramNumber].getName(), obj.getClass());
		Method setmd = pd.getWriteMethod();// 获取某个属性的set方法
		if (type.equals("class java.lang.String")) {
			setmd.invoke(obj, value);
		}else if ("int".equals(type) || "class java.lang.Integer".equals(type)) {
			setmd.invoke(obj, value);
		}else if ("double".equals(type) || "class java.lang.Double".equals(type)) {
			setmd.invoke(obj, value);
		}else {
			setmd.invoke(obj, value);
		}
		return obj;
	}
	
	/**
	 * 读取xls格式
	 * 
	 * @param filePath
	 * @param classObject
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public static LinkedList<Object> parseXLS(InputStream is, Class classObject,HttpServletRequest request)
			throws FileNotFoundException, IOException, IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {
		LinkedList<Object> list = new LinkedList<Object>();
		HSSFWorkbook hwb = new HSSFWorkbook(is);
		HSSFSheet hs = hwb.getSheetAt(0);// 获取第0个sheet页的数据
		int rowStart = hs.getFirstRowNum();// 获取第一行的行数 0
		int rowEnd = hs.getLastRowNum();// 获取最后一行的行数-1
		
		for (int i = rowStart + 1; i <= rowEnd; i++) {// 行的遍历
			Object obj = classObject.newInstance();

			HSSFRow hr = hs.getRow(i);// 获取行
			if (hr == null) {
				continue;
			}
			int cellStart = hr.getFirstCellNum();// 获取第一列
			int cellEnd = hr.getLastCellNum();// 获取列的总数
	
			for (int k = cellStart; k < cellEnd; k++) {// 列的遍历
				HSSFCell hc = hr.getCell(k);// 获取列
				if (hc == null || hc.equals("")) {
					continue;
				}
				switch (hc.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:// 字符串
					obj = reflect(obj, k, hc.getStringCellValue());
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:// boolean类型
					obj = reflect(obj, k, hc.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:// 数字
					obj = reflect(obj, k, hc.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA:// 公式
					obj = reflect(obj, k, hc.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK:// 空值
					obj = reflect(obj, k, "");
					break;
				case HSSFCell.CELL_TYPE_ERROR:// 错误
					obj = reflect(obj, k, "");
					break;
				default:
					break;
				}
			}
			list.add(obj);
		}
		return list;
	}

	/**
	 * 读取xlsx格式
	 * 
	 * @param filePath
	 * @param classObject
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static LinkedList<Object> parseXLSX(InputStream is, Class classObject,HttpServletRequest request)
			throws FileNotFoundException, IOException, InvalidFormatException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		LinkedList<Object> list = new LinkedList<Object>();
		XSSFWorkbook xw = new XSSFWorkbook(is);
		XSSFSheet xs = xw.getSheetAt(0);// 获取xsl
		int rowStart = xs.getFirstRowNum();// 获取第一行的行数 0
		int rowEnd = xs.getLastRowNum();// 获取最后一行的行数-1
		for (int i = rowStart + 1; i <= rowEnd; i++) {// 行的遍历
			Object obj = classObject.newInstance();
			XSSFRow hr = xs.getRow(i);// 获取行
			if (hr == null) {
				continue;
			}
			int cellStart = hr.getFirstCellNum();// 获取第一列
			int cellEnd = hr.getLastCellNum();// 获取列的总数
			for (int k = cellStart; k < cellEnd; k++) {// 列的遍历
				XSSFCell hc = hr.getCell(k);// 获取列
				if (hc == null || hc.equals("")) {
					continue;
				}
				switch (hc.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:// 字符串
					obj = reflect(obj, k, hc.getStringCellValue());
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:// boolean类型
					obj = reflect(obj, k, hc.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:// 数字
					obj = reflect(obj, k, hc.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA:// 公式
					obj = reflect(obj, k, hc.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK:// 空值
					obj = reflect(obj, k, "");
					break;
				case HSSFCell.CELL_TYPE_ERROR:// 错误
					obj = reflect(obj, k, "");
					break;
				default:
					break;
				}
			}
			list.add(obj);
		}
		return list;
	}

}
