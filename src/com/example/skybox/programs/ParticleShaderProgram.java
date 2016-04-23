package com.example.skybox.programs;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;

import com.example.skybox.R;

import android.content.Context;

public class ParticleShaderProgram extends ShaderProgram{
	//Uniform locations
	private final int uMatrixLocation;
	private final int uTimeLocation;
	private final int uTextureUnitLocation;
	
	//Attribute location
	private final int aPositionLocation;
	private final int aColorLocation;
	private final int aDirectionVectorLocation;
	private final int aParticleStartTimeLocation;
	
	public ParticleShaderProgram(Context context) {
		// TODO Auto-generated constructor stub
		super(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader);
		
		uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
		uTimeLocation = glGetUniformLocation(program, U_TIME);
		uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
		
		aPositionLocation = glGetAttribLocation(program, A_POSITION);
		aColorLocation = glGetAttribLocation(program, A_COLOR);
		aDirectionVectorLocation = glGetAttribLocation(program, A_DIRECTION_VECTOR);
		aParticleStartTimeLocation = glGetAttribLocation(program, A_PARTICLE_START_TIME);
	}

	public void setUniforms(float[] matrix, float elapsedTime, int textureId){
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		glUniform1f(uTimeLocation, elapsedTime);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureId);
		glUniform1i(uTextureUnitLocation, 0);
	}
	public int getaPositionLocation() {
		return aPositionLocation;
	}

	public int getaColorLocation() {
		return aColorLocation;
	}

	public int getaDirectionVectorLocation() {
		return aDirectionVectorLocation;
	}

	public int getaParticleStartTimeLocation() {
		return aParticleStartTimeLocation;
	}

	public void useProgram() {
		// TODO Auto-generated method stub
		glUseProgram(program);
	}
	
	
}
