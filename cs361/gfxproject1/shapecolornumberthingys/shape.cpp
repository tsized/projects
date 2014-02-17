#include "Angel.h"
#include "constant.h"
#include <iostream>
//g++ -I ../include/ ../Common/InitShader.cpp shape.cpp -lGLEW -lglut -lGLU

static vec2 points[TEN];
static vec4 colors[TEN];
static GLenum draw_method;
static int size     = 0;
const vec4 RED      = vec4 (POS_ONE, POS_ZER, POS_ZER, POS_ONE);
const vec4 GREEN    = vec4 (POS_ZER, POS_ONE, POS_ZER, POS_ONE);
const vec4 BLUE     = vec4 (POS_ZER, POS_ZER, POS_ONE, POS_ONE);
const vec4 BLACK    = vec4 (POS_ZER, POS_ZER, POS_ZER, POS_ONE);
const vec4 WHITE    = vec4 (POS_ONE, POS_ONE, POS_ONE, POS_ONE);
static vec4 draw_color;

/**
 * An OpenGL program that draws specificed shapes determined by keyboard input.
 *
 * @author Tim Sizemore
 * @author Bill Franklin
 * @version 2/15/13
 */
using namespace std;

/* initialize the shaders */
void init ( void ) {
    
    GLuint vao;
    glGenVertexArrays( 1, &vao );
    glBindVertexArray( vao );

    // Create and initialize a buffer object
    GLuint buffer;
    glGenBuffers( 1, &buffer );
    glBindBuffer( GL_ARRAY_BUFFER, buffer );
    
    // call glBufferData
    glBufferData( GL_ARRAY_BUFFER, sizeof(points) + sizeof(colors), NULL,
                  GL_STATIC_DRAW );

    // Load shaders and use the resulting shader program
    GLuint program = InitShader( "vin.glsl", "fin.glsl" );
    glUseProgram( program );

    // Initialize the vertex position attribute from the vertex shader
    GLuint vPos  = glGetAttribLocation( program, "vPosition" );
    GLuint color = glGetAttribLocation( program, "in_color" );

    glEnableVertexAttribArray( vPos );                                          
    glEnableVertexAttribArray( color );                                          

    //Direct Attribute Pointer to the shader values
    glVertexAttribPointer( vPos, TWO, GL_FLOAT, GL_FALSE, 0,                      
                           BUFFER_OFFSET(0) );                                  
    glVertexAttribPointer( color, FOUR, GL_FLOAT, GL_FALSE, 0,                      
                           BUFFER_OFFSET(sizeof(points)) );                                  
    //Atrributes
    glClearColor( POS_ONE, POS_ONE, POS_ONE, POS_ONE );                                           
    glClear( GL_COLOR_BUFFER_BIT );                                              
                                                                                
}                                                                               
                                                                                
/* 
 * Takes the colors for the cordinates, puts them and the points in the sub
 * buffer. If the draw methods is Polygon do a multiple draws. If it is any
 * other draw method do the default single draw. Flush the buffer.
 */
void display(void) {
    colors[0]     = draw_color;
    colors[1]     = draw_color;
    colors[TWO]   = draw_color;
    colors[THREE] = draw_color;
    colors[FOUR]  = draw_color;
    colors[FIVE]  = draw_color;

    // Look up glBufferSubData and put both buffers in
    glBufferSubData( GL_ARRAY_BUFFER, 0, sizeof(points), points );
    glBufferSubData( GL_ARRAY_BUFFER, sizeof(points), sizeof(colors), colors );

    // Changes the size of the points
    glPointSize( THREE );

    if (draw_method == GL_POLYGON && size == TEN) {
        glDrawArrays( draw_method, 0, SIX );
        glDrawArrays( GL_LINES, SIX, FOUR );
        glDrawArrays( GL_LINES, SEVEN, TWO );
    } else {
        glDrawArrays( draw_method, 0, size );                     
    }
    glFlush();                                                                  
}

/* 
 * The default cordinates that is used for all but lined_polygon.
 */
void default_settings(void) {
    size          =  SIX;
    points[0]     =  vec2( POS_THR, POS_FIV );
    points[1]     =  vec2( POS_FIV, POS_ZER );
    points[TWO]   =  vec2( POS_THR, -POS_FIV );
    points[THREE] =  vec2( -POS_THR, -POS_FIV ); 
    points[FOUR]  =  vec2( -POS_FIV, POS_ZER );
    points[FIVE]  =  vec2( -POS_THR, POS_FIV );
}

/* 
 * Draws all the points and just the points.
 */
void display_points(void) {                                                            
    glClear( GL_COLOR_BUFFER_BIT );                                              
    draw_method = GL_POINTS;
    default_settings();
    glutPostRedisplay();
}                                                                               

/* 
 * Draws lines to show edges of a non_filled hexagon.
 */
void display_lines(void) {
    glClear( GL_COLOR_BUFFER_BIT );                                              
    draw_method     = GL_LINE_LOOP;
    default_settings();
    glutPostRedisplay();
}

/* 
 * Draws two triangles.
 */
void display_triangle(void) {
    glClear( GL_COLOR_BUFFER_BIT );                                              
    draw_method     = GL_TRIANGLES;
    default_settings();
    glutPostRedisplay();
}

/* 
 * Draws three line segments.
 */
void display_star(void) {
    glClear( GL_COLOR_BUFFER_BIT );                                              
    draw_method = GL_LINES;
    points[0]       =  vec2( POS_THR, POS_FIV );
    points[1]       =  vec2( -POS_THR, -POS_FIV );
    points[TWO]     =  vec2( POS_FIV, POS_ZER );
    points[THREE]   =  vec2( -POS_FIV, POS_ZER );
    points[FOUR]    =  vec2( POS_THR, -POS_FIV );
    points[SIX]     =  vec2( -POS_THR, POS_FIV );
    glutPostRedisplay();
}

/* 
 * Draws the hexagon filled in.
 */
void display_polygon(void) {
    glClear( GL_COLOR_BUFFER_BIT );                                              
    draw_method     = GL_POLYGON;
    default_settings();
    glutPostRedisplay();
}

/* 
 * Draws the hexagon filled in, and line segments drawn over them.
 */
void display_lined_poly(void) {
    glClear( GL_COLOR_BUFFER_BIT );                                              
    draw_method     = GL_POLYGON;
    default_settings();
    size            = TEN;
    points[SIX]     = vec2( -POS_THR, POS_FIV );
    points[SEVEN]   = vec2( -POS_THR, -POS_FIV );
    points[EIGHT]   = vec2 ( POS_THR, POS_FIV );
    points[NINE]    = vec2 ( POS_THR, -POS_FIV );
    colors[SIX]     = WHITE;
    colors[SEVEN]   = WHITE;
    colors[EIGHT]   = WHITE;
    colors[NINE]    = WHITE;
    glutPostRedisplay();
}

/* Makes the color red */
void display_red(void) {
    glClear( GL_COLOR_BUFFER_BIT );
    draw_color = RED;
    glutPostRedisplay();
}

/* Makes the color blue */
void display_blue(void) {
    glClear( GL_COLOR_BUFFER_BIT );
    draw_color = BLUE;
    glutPostRedisplay();
}

/* makes the color green */
void display_green(void) {
    glClear( GL_COLOR_BUFFER_BIT );
    draw_color = GREEN;
    glutPostRedisplay();
}

/* makes the color black */
void display_black(void) {
    glClear( GL_COLOR_BUFFER_BIT );
    draw_color = BLACK;
    glutPostRedisplay();
}

/* takes input from keyboard and uses the value to determine function call */
void keyboard(unsigned char key, int x, int y) {
    switch ( key ) {
    case NUM_ONE:
        display_points();
        break;
    case NUM_TWO:
        display_lines();
        break;
    case NUM_THR:
        display_triangle();
        break;
    case NUM_FOU:
        display_star();
        break;
    case NUM_FIV:
        display_polygon();
        break;
    case NUM_SIX:
        display_lined_poly();
        break;
    case UPP_R:
    case LOW_R:
        display_red();
        break;
    case UPP_B:
    case LOW_B:
        display_blue();
        break;
    case UPP_G:
    case LOW_G:
        display_green();
        break;
    case UPP_K:
    case LOW_K:
        display_black();
        break;
    case UPP_Q:
    case LOW_Q:
        exit( 0 );
        break;
    }
}
                                                                                
/*
 * makes all the function calls, sets up window size, and text at top of window.
 */
int main(int argc, char **argv) {                                               
    glutInit(&argc, argv);                                                      
    glutInitDisplayMode( GLUT_RGBA );                                             
                                                                                
    glutInitWindowSize( WIN_SIZE, WIN_SIZE );                                               
    glutCreateWindow( WIN_NAME );                                         
                                                                                
    glewInit();                                                                 
    init();                                                                     
    glutDisplayFunc( display );                                                   
    glutKeyboardFunc( keyboard );                                                   
    glutMainLoop();                                                             
    return 0;                                                                   
}                                
