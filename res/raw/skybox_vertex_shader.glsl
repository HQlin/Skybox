//res/raw/skybox_vertex_shader.glsl
uniform mat4 u_Matrix;
attribute vec3 a_Position;
varying vec3 v_Position;

void main()
{
	v_Position = a_Position;
	//把世界的右手坐标空间转换为天空盒的左手坐标空间
	v_Position.z = -v_Position.z;
	
	gl_Position = u_Matrix*vec4(a_Position, 1.0);
	//设置贴图在远平面上
	gl_Position = gl_Position.xyww;
}