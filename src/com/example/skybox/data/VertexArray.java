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
	 * 本地存储顶点矩阵数据
	 * @param vertexData 顶点矩阵数据
	 */
	public VertexArray(float[] vertexData) {
		// TODO Auto-generated constructor stub
		floatBuffer = ByteBuffer.allocateDirect(vertexData.length*Constants.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(vertexData);
	}
	
	/**
	 * 本地数据与着色器属性关联
	 * @param dataOffset          偏移量
	 * @param attributeLocation   属性地址
	 * @param componentCount	       顶点组件数量
	 * @param stride              跨距
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
