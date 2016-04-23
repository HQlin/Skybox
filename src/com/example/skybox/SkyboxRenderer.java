package com.example.skybox;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.perspectiveM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.skybox.objs.ParticleShooter;
import com.example.skybox.objs.ParticleSystem;
import com.example.skybox.objs.Skybox;
import com.example.skybox.programs.ParticleShaderProgram;
import com.example.skybox.programs.SkyboxShaderProgram;
import com.example.skybox.util.Geometry.Point;
import com.example.skybox.util.Geometry.Vector;
import com.example.skybox.util.TextureHelper;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;

public class SkyboxRenderer implements Renderer{
	private Context context;
	
	private SkyboxShaderProgram skyboxProgram;
	private Skybox skybox;
	private int skyboxTexture;
	
	private final float[] projectionMatrix = new float[16];
	private final float[] viewMatrix = new float[16];
	private final float[] viewProjectionMatrix = new float[16];
	
	private ParticleShaderProgram particleProgram;
	private ParticleSystem particleSystem;
	private ParticleShooter redParticleShooter;
	private ParticleShooter greenParticleShooter;
	private ParticleShooter blueParticleShooter;
	private long globalStartTime;
	
	private int texture;
	
	private float xRotation, yRotation;
	
	public SkyboxRenderer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		glClear(GL_COLOR_BUFFER_BIT);
		drawSkybox();
		drawParticles();
	}

	private void drawParticles() {
		// TODO Auto-generated method stub
		float currentTime = (System.nanoTime() - globalStartTime)/1000000000f;//1,000,000,000f
		
		redParticleShooter.addParticles(particleSystem, currentTime, 1);
		greenParticleShooter.addParticles(particleSystem, currentTime, 1);
		blueParticleShooter.addParticles(particleSystem, currentTime, 1);
		
		setIdentityM(viewMatrix, 0);
		rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
		rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
		translateM(viewMatrix, 0, 0f, -1.5f, -5f);
		multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		
		//用累加混合技术混合粒子：粒子越多越亮
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
				
		particleProgram.useProgram();
		particleProgram.setUniforms(viewProjectionMatrix, currentTime, texture);
		particleSystem.bindData(particleProgram);
		particleSystem.draw();
		
		glDisable(GL_BLEND);
	}

	private void drawSkybox() {
		// TODO Auto-generated method stub
		setIdentityM(viewMatrix, 0);
		rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
		rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
		multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		skyboxProgram.useProgram();
		skyboxProgram.setUniforms(viewProjectionMatrix, skyboxTexture);
		skybox.bindData(skyboxProgram);
		skybox.draw();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		glViewport(0, 0, width, height);
		
		perspectiveM(projectionMatrix, 0, 45, (float)width/(float)height, 1f, 10f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		skyboxProgram = new SkyboxShaderProgram(context);
		skybox = new Skybox();
		skyboxTexture = TextureHelper.loadCubeMap(context, 
				new int[]{R.drawable.bg, R.drawable.bg1,//左右
						R.drawable.bg, R.drawable.bg1,//下上
						R.drawable.bg, R.drawable.bg1});//前后	
		
		particleProgram = new ParticleShaderProgram(context);
		particleSystem = new ParticleSystem(10000);
		globalStartTime = System.nanoTime();
		
		final Vector particleDirection = new Vector(0.0f, 0.5f, 0.0f);
		final float angleVarianceInDegrees = 5f;
		final float speedVariance = 1f;
		
		redParticleShooter = new ParticleShooter(
				new Point(-1f, 0f, 0f),
				particleDirection, 
				Color.rgb(255, 50, 5),
				angleVarianceInDegrees,
				speedVariance);
		
		greenParticleShooter = new ParticleShooter(
				new Point(0f, 0f, 0f),
				particleDirection, 
				Color.rgb(25, 255, 25),
				angleVarianceInDegrees,
				speedVariance);
		
		blueParticleShooter = new ParticleShooter(
				new Point(1f, 0f, 0f),
				particleDirection, 
				Color.rgb(5, 50, 255),
				angleVarianceInDegrees,
				speedVariance);
		
		texture = TextureHelper.loadTexture(context, R.drawable.ic_launcher);
	}

	public void handleTouchDrag(float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		xRotation += deltaX / 16f;
		yRotation += deltaY / 16f;
		
		if(yRotation < -90){
			yRotation = -90;
		} else if(yRotation > 90){
			yRotation = 90;
		}
	}

}
