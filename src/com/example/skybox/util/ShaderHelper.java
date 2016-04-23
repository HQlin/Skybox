package com.example.skybox.util;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

import com.example.skybox.LoggerConfig;

import android.util.Log;

public class ShaderHelper {
	private static final String TAG = "ShaderHelper";
	
	public static int compileVertexShader(String shaderCode){
		return compileShader(GL_VERTEX_SHADER, shaderCode);
	}

	public static int compileFragmentShader(String shaderCode){
		return compileShader(GL_FRAGMENT_SHADER, shaderCode);
	}
	
	/**
	 * 根据着色器代码生成着色器程序
	 * @param type        着色器类型
	 * @param shaderCode  着色器代码
	 * @return            着色器程序
	 */
	private static int compileShader(int type, String shaderCode) {
		// TODO Auto-generated method stub
		final int shaderObjectId = glCreateShader(type);		
		if(shaderObjectId==0){
			if(LoggerConfig.ON){
				Log.w(TAG, "不能创建新的着色器对象");
			}
			return 0;
		}
		glShaderSource(shaderObjectId, shaderCode);
		glCompileShader(shaderObjectId);
		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
		if(LoggerConfig.ON){
			Log.v(TAG, "result of compiling program: \n"
					+ shaderCode + "\n" 
					+ glGetProgramInfoLog(shaderObjectId));
		}
		if(compileStatus[0]==0){
			glDeleteProgram(shaderObjectId);
			if(LoggerConfig.ON){
				Log.w(TAG, "创建着色器对象失败 ");
			}
			return 0;
		}
		return shaderObjectId;	
	}
	
	/**
	 * 链接顶点着色器与片段着色器程序
	 * @param vertexShaderId   顶点着色器程序
	 * @param fragmentShaderId 片段着色器程序
	 * @return
	 */
	public static int linkProgram(int vertexShaderId,int fragmentShaderId){
		final int programObjectId = glCreateProgram();
		if(programObjectId==0){
			if(LoggerConfig.ON){
				Log.w(TAG, "不能创建新的链接着色器的程序");
			}
			return 0;
		}
		glAttachShader(programObjectId, vertexShaderId);
		glAttachShader(programObjectId, fragmentShaderId);
		glLinkProgram(programObjectId);
		final int[] linkStatus = new int[1];
		glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
		if(LoggerConfig.ON){
			Log.v(TAG, "result of linking program: "+ glGetProgramInfoLog(programObjectId));
		}
		if(linkStatus[0]==0){
			glDeleteProgram(programObjectId);
			if(LoggerConfig.ON){
				Log.w(TAG, "链接程序创建失败 ");
			}
			return 0;
		}
		return programObjectId;
	}
	
	/**
	 * 验证着色器程序
	 * @param programObjectId
	 * @return
	 */
	public static boolean validateProgram(int programObjectId){
		glValidateProgram(programObjectId);
		
		final int[] validateStatus = new int[1];
		glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
		
		Log.v(TAG, "result of validating program: "+glGetProgramInfoLog(programObjectId));
	
		return validateStatus[0]!=0;
	}
	
	/**
	 * 生成着色器程序
	 * @param vertexShaderSoure    顶点着色器代码
	 * @param fragmentShaderSoure  片段着色器代码
	 * @return
	 */
	public static int buildProgram(String vertexShaderSoure,String fragmentShaderSoure){
		int program;
		
		//获取对对应着色器
		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSoure);
		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSoure);
		
		program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
		
		if (LoggerConfig.ON) {
			ShaderHelper.validateProgram(program);
		}
		
		return program;		
	}
}
