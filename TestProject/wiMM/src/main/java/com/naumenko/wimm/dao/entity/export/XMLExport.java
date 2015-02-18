package com.naumenko.wimm.dao.entity.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.os.Environment;
import android.os.ParcelFileDescriptor;

import com.naumenko.wimm.dao.entity.WimmEntity;

public class XMLExport implements Export{

	@Override
	public boolean export(List<WimmEntity> entities) {
		
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuilder.append("<payment_list>");
		xmlBuilder.append("\n");
		for (WimmEntity wimmEntity : entities) {
			xmlBuilder.append(wimmEntity.getConvertedXml());
			xmlBuilder.append("\n");
		}
		xmlBuilder.append("</payment_list>");
		
		storeOnSdCard(xmlBuilder.toString());
		
		return false;
	}

	private void storeOnSdCard(Object data){
		
		final String MODE_WRITABLE = "w";
		
		ParcelFileDescriptor pfd = null;
		FileOutputStream fileOutputStream = null;
		
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy"); 
	    	String dateString = formatter.format(System.currentTimeMillis());
			
			String fileName = "Wimm_Export_"+dateString+".xml";
			
			File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+fileName);
			
			f.createNewFile();
			
			fileOutputStream = new FileOutputStream(f);
			fileOutputStream.write(((String) data).getBytes());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(fileOutputStream!=null){
					fileOutputStream.close();
				}
				if(pfd!=null){
					pfd.close();	
				}
			} catch (IOException e) {
			}
		}	
	}
	
}
