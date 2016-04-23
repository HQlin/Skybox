package com.example.skybox;

import android.os.Bundle;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	private GLSurfaceView glSurfaceView;
	private boolean rendererSet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//1.创建GLSurfaceView实例
		glSurfaceView = new GLSurfaceView(this);
		//2.检查系统是否支持opengl es2.0
		final ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo ci = am.getDeviceConfigurationInfo();
		final boolean supportsEs2 = ci.reqGlEsVersion >= 0x20000;
		final SkyboxRenderer renderer = new SkyboxRenderer(this);
		//3.为opengl es2.0配置渲染表面
		if(supportsEs2){
			glSurfaceView.setEGLContextClientVersion(2);
			glSurfaceView.setRenderer(renderer);
			rendererSet = true;
		} else {
			Toast.makeText(this, "设备不支持opengl es 2.0", Toast.LENGTH_SHORT).show();
			return;
		}		
		//4.glSurfaceView添加监听触控事件
		glSurfaceView.setOnTouchListener(new OnTouchListener() {
			float previousX,previousY;		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event != null) {
					// 转发触控事件给渲染器
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						previousX = event.getX();
						previousY = event.getY();
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
						final float deltaX = event.getX() - previousX;
						final float deltaY = event.getY() - previousY;
						previousX = event.getX();
						previousY = event.getY();
						
						glSurfaceView.queueEvent(new Runnable() {

							@Override
							public void run() {
								// 调用渲染器拖拽事件
								renderer.handleTouchDrag(deltaX, deltaY);
							}
						});
					}
					return true;
				} else {
					return false;
				}
			}
				});
				//5.显示GLSurfaceView
				setContentView(glSurfaceView);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(rendererSet){
			glSurfaceView.onResume();
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(rendererSet){
			glSurfaceView.onPause();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
