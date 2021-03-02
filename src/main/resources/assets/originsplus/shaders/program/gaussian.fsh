#version 110

uniform sampler2D diffuseSampler;

varying vec2 texCoord;

void main() {

    gl_FragColor = texture2D(DiffuseSampler, texCoord);

}