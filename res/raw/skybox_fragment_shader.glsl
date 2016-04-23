//res/raw/skybox_fragment_shader.glsl
precision mediump float;

uniform samplerCube u_TextureUnit;
varying vec3 v_Position;

void main()
{
	gl_FragColor = textureCube(u_TextureUnit, v_Position);
}