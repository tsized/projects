#version 150

in vec4 vPosition;

in vec4 InColor;

out vec4 PassColor;

void
main()
{
    PassColor = InColor;

    gl_Position = vPosition;
}
