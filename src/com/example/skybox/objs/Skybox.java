package com.example.skybox.objs;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glDrawElements;

import java.nio.ByteBuffer;

import com.example.skybox.data.VertexArray;
import com.example.skybox.programs.SkyboxShaderProgram;

public class Skybox {
	private static final int POSITON_COMPONENT_COUNT = 3;
	private final VertexArray vertexArray;
	private final ByteBuffer indexArray;
	
	public Skybox(){
		vertexArray = new VertexArray(new float[]{
			-1,  1,  1,//0
			 1,  1,  1,//1
			-1, -1,  1,//2
			 1, -1,  1,//3
			-1,  1, -1,//4
			 1,  1, -1,//5
			-1, -1, -1,//6
			 1, -1, -1 //7
		});
		
		//遵循三角形的卷曲顺序来区分物体前后，即逆时针为前面
		indexArray = ByteBuffer.allocateDirect(6*6)
				.put(new byte[]{
					//front
					1,3,0,
					0,3,2,
					
					//back
					4,6,5,
					5,6,7,
					
					//left
					0,2,4,
					4,2,6,
					
					//right
					5,7,1,
					1,7,3,
					
					//top
					5,1,4,
					4,1,0,
					
					//bottom
					6,2,7,
					7,2,3
				});
		indexArray.position(0);
	}
	
	public void bindData(SkyboxShaderProgram skyboxProgram){
		vertexArray.setVertexAttribPointer(0,
				skyboxProgram.getPositionAttributeLocation(), 
				POSITON_COMPONENT_COUNT, 0);
	}
	
	public void draw(){
		//indexArray数据为无符号字节数
		glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, indexArray);
	}
}
