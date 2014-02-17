#include "Angel.h"
#include <iostream>
//g++ -I ../include/ ../Common/InitShader.cpp in.cpp -lGLEW -lglut -lGLU

using namespace std;
void init (void ) {

    // Make some points and some colors TTS: changed array size to 5
    vec2 points[5] = vec2 (0.0, 0.0) vec2(0.5, 0.5) vec2(-0.5, -0.5) 
                     vec2(-0.5, 0.0) vec2(0.0, 0.5);
    vec4 colors[6];


    GLuint vao;
    glGenVertexArrays(1, &vao);
    glBindVertexArray(vao);
    
    // Create and initialize a buffer object
    GLuint buffer;
    glGenBuffers( 1, &buffer );
    glBindBuffer( GL_ARRAY_BUFFER, buffer );
    // call glBufferData
    glBufferData( GL_ARRAY_BUFFER, sizeof(points) + sizeof(colors), NULL,
                  GL_STATIC_DRAW );
    // Look up glBufferSubData and put both buffers in
    glBufferSubData(GL_ARRAY_BUFFER, 0, sizeof(points), points)
    // Load shaders and use the resulting shader program
    GLuint program = InitShader( "vin.glsl", "fin.glsl" );          
    glUseProgram( program );                                                    

    // Initialize the vertex position attribute from the vertex shader
    GLuint vPos = glGetAttribLocation( program, "vPosition" );
    glEnableVertexAttribArray( vPos );
    glVertexAttribPointer( vPos, 2, GL_FLOAT, GL_FALSE, 0,
                           BUFFER_OFFSET(0) );
    // What other attribute do you need?
    glClearColor(1.0, 1.0, 1.0, 1.0);

}

void display(void) {
    // writeme
    glClear( GL_COLOR_BUFFER_BIT);
    glDrawArrays( GL_POINTS, 0, 5);
    glFlush();
}

int main(int argc, char **argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA);

    glutInitWindowSize(512, 512);
    glutCreateWindow( "King Phallus" );
    //glutInitWindowPosition();
    //glutInitWindowSize();
    //glutCreateWindow();

    glewInit();
    init();
    glutDisplayFunc(display);
    glutMainLoop();
    return 0;
}