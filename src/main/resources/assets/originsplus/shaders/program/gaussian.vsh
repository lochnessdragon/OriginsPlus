#version 110

attribute vec4 position;

uniform mat4 projectionMat;
uniform vec2 outSize;

varying vec2 texCoord;

void main() {
	vec4 outPos = projectionMat * vec4(position.xy, 0.0, 1.0);
	gl_Position = vec4(outPos.xy, 0.2, 1.0);
	texCoord = position.xy / outSize;
}