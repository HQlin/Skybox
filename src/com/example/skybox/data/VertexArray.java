package com.example.skybox.data;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexArray {
	private final FloatBuffer floatBuffer;
	
	/**
	 * ���ش洢�����������
	 * @param vertexData �����������
	 */
	public VertexArray(float[] vertexData) {
		// TODO Auto-generated constructor stub
		floatBuffer = ByteBuffer.allocateDirect(vertexData.length*Constants.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(vertexData);
	}
	
	/**
	 * ������������ɫ�����Թ���
	 * @param dataOffset          ƫ����
	 * @param attributeLocation   ���Ե�ַ
	 * @param componentCount	       �����������
	 * @param stride              ���
	 */
	public void setVertexAttribPointer(int dataOffset, int attributeLocation,
			int componentCount, int stride){
		floatBuffer.position(dataOffset);
		glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT,
				false, stride, floatBuffer);
		glEnableVertexAttribArray(attributeLocation);
		floatBuffer.position(0);
	}
	
	public void updateBuffer(float[] vertexData, int start, int count) {
		// TODO Auto-generated method stub
		floatBuffer.position(start);
		floatBuffer.put(vertexData, start, count);
		floatBuffer.position(0);
	}
}
