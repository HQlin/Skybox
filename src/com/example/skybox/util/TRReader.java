package com.example.skybox.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class TRReader {
	/**
	 * 获取资源文件数据
	 * @param context    
	 * @param resId      资源id
	 * @return           文件数据
	 */
	public static String readTFfromRes(Context context, int resId){
		StringBuilder body = new StringBuilder();
		InputStream inputStream = context.getResources().openRawResource(resId);
		InputStreamReader isReader = new InputStreamReader(inputStream);
		BufferedReader bReader = new BufferedReader(isReader);			
		String nextLine;			
		try {
			while ((nextLine=bReader.readLine())!=null) {
				body.append(nextLine);
				body.append('\n');
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return body.toString();	
	}
}
