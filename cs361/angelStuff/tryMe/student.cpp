#include "Angel.h"
#include <iostream>
//g++ -I ../include/ ../Common/InitShader.cpp in.cpp -lGLEW -lglut -lGLU

using namespace std;
void init (void ) {

    // Make some points and some colors TTS: changed array size to 5
    vec2 points[] = { vec2(-0.8, -0.8),vec2 (-0.8, 0.0), vec2(0.0, 0.0),
                      vec2(0.0, 0.8), vec2(0.8, 0.8)};
    vec4 colors[] = { vec4(1.0, 0.0, 0.0, 1.0), vec4(1.0, 0.0, 0.0, 1.0),
                       vec4(1.0, 0.0, 0.0, 1.0), vec4(1.0, 0.0, 0.0, 1.0),
                       vec4(1.0, 0.0, 0.0, 1.0) };

    vec4 xcolors[] = { vec4(1.0, 0.0, 0.0, 1.0), vec4(0.0, 1.0, 0.0, 1.0),
                       vec4(0.0, 0.0, 1.0, 1.0) };

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
    glBufferSubData(GL_ARRAY_BUFFER, 0, sizeof(points), points);
    glBufferSubData(GL_ARRAY_BUFFER, sizeof(points), sizeof(colors), colors);
    // Load shaders and use the resulting shader program
    GLuint program = InitShader( "vin.glsl", "fin.glsl" );          
    glUseProgram( program );                                                    

    // Initialize the vertex position attribute from the vertex shader
    GLuint vPos = glGetAttribLocation( program, "vPosition" );
    GLuint cPos = glGetAttribLocation( program, "InColor" );
    glEnableVertexAttribArray( vPos );
    glEnableVertexAttribArray( cPos );
    glVertexAttribPointer( vPos, 2, GL_FLOAT, GL_FALSE, 0,
                           BUFFER_OFFSET(0) );
    glVertexAttribPointer( cPos, 4, GL_FLOAT, GL_FALSE, 0,
                           BUFFER_OFFSET(sizeof(points)) );
    // What other attribute do you need?
    glClearColor(1.0, 1.0, 1.0, 1.0);

}

void display(void) {
    glClear( GL_COLOR_BUFFER_BIT );
    glDrawArrays( GL_LINE_LOOP, 0, 5 );
    glFlush();
}

void set_color() {

int main(int argc, char **argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA);

    glutInitWindowSize(512, 512);
    glutCreateWindow( "Tim and Bill Exceptionally Long Name That's An Adventure." );

    glewInit();
    init();
    glutDisplayFunc(display);
    glutMainLoop();
    return 0;
}
