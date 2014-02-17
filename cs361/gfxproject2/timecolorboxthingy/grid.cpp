#include "Angel.h"
#include "constant.h"
#include <iostream>
//g++ -I ../include/ ../Common/InitShader.cpp shape.cpp -lGLEW -lglut -lGLU

/**
 * An OpenGL program that draws a grid and after .5 seconds the individual cells
 * change colors based on a pseudorandom value.
 *
 * @author Tim Sizemore
 * @author Bill Franklin
 * @version 3/1/13
 */
using namespace std;

/* A pointer to an array of colors. */
static vec4 *colors;

/* A pointer to an array of colors. */
static vec2 *points;

/* An enumerated value for comparision. */
static GLenum draw_method;

/* Used to move the color changing frame along the grid. */
static int count = 0;

/* The number of points held in the points array. */
static int size;

/* The number of squares for the specifyed grid size. */ 
static int num_squares;

/* The memory size of the points array. */
static int size_var;

/* The memory size of the points array. */
static int size_col;

/* The amount by which the frame coloring the squares moves. */
static int offsetter = 0;

/* The number of rows in the grid. */
static int x_size;

/* The number of columns in the grid. */
static int y_size;

/* Default row size of the grid. */
static double x_value = -.5;

/* Default column size of the grid. */
static double y_value = .5;

/* The color used to populate the color's sub buffer. */
static vec4 draw_color;

/* Used to maintain the black color of the grid lines. */
static vec4 temp_color;

/* Forward declaration for drawing the grid. */
void drawGrid();

/* 
 * Initialize the shaders and the default points and colors for the sub buffers.
 *
 * @param argv the address of the array containing the command line arguments
 */
void init (char **argv) {
    GLuint vao;
    glGenVertexArrays( 1, &vao );
    glBindVertexArray( vao );

    x_size = atoi(argv[ONE]);
    y_size = atoi(argv[TWO]);
    size = (x_size + 1) * (y_size + 1);
    num_squares = (x_size) * (y_size);

    points = new vec2[size];
    size_var = sizeof(vec2) * size;

    colors = new vec4[size];
    size_col = sizeof(vec4) * size;
    
    // Create and initialize a buffer object
    GLuint buffer;
    glGenBuffers( 1, &buffer );
    glBindBuffer( GL_ARRAY_BUFFER, buffer );
    
    // call glBufferData
    glBufferData( GL_ARRAY_BUFFER, size_var + size_col, NULL,
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
                           BUFFER_OFFSET(size_var) );                                  

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
    if(count > 0 ){
        glDrawArrays( draw_method, 0, size );                     
        draw_method = GL_LINE_LOOP;
        temp_color = draw_color;
        draw_color = vec4(0.0, 0.0 ,0.0 ,1.0);
        colors[ZERO] = draw_color;
        colors[ONE] = draw_color;
        colors[TWO] = draw_color;
        colors[THREE] = draw_color;
        glBufferSubData( GL_ARRAY_BUFFER, size_var, size_col, colors );
        glDrawArrays( draw_method, 0, size );                     
    }

    draw_color = temp_color;
    glBufferSubData( GL_ARRAY_BUFFER, size_var, size_col, colors );
    glFlush();                                                                  
}

/* 
 * Draws all the points and just the points.
 */
void display_points(void) {                                                            
    int i;
    size = (x_size + 1) * (y_size + 1);
    
    for ( i = 0; i < size; i++ ) {
        points[i] = vec2(x_value, y_value);
        if ( (i != 0) && (((i + 1) % (x_size + 1)) == 0) ) {
            y_value -= (1.0 / y_size);
            x_value = -.5;
        } else {
            x_value   += (1.0 / x_size);
        }
    }
    glBufferSubData( GL_ARRAY_BUFFER, 0, size_var, points );
    glBufferSubData( GL_ARRAY_BUFFER, size_var, size_col, colors );
    drawGrid();
}

/* 
 * Establishes the lines for the rows and columns of the grid.
 */
void drawGrid(void) {
    draw_method     = GL_LINE_LOOP;
    draw_color = vec4(0.0, 0.0 ,0.0 ,1.0);
    int x = 0;
    while(x <= (y_size)) {
        glDrawArrays( draw_method, (x_size * x) + x, size / (y_size + 1) );                     
        x++;
    }

    int y = 0;
    int z = (size - (x_size + 1));

    draw_method     = GL_LINES;

    vec2 temp1 = points[ZERO];
    vec2 temp2 = points[ONE];

    while(y <= (x_size)) {
        points[ZERO] = points[y];
        points[ONE] = points[z];
        glBufferSubData( GL_ARRAY_BUFFER, 0, size_var, points );
        glDrawArrays( draw_method, 0, TWO );                     
        y++;
        z++;
        points[ZERO] = temp1;
        points[ONE] = temp2;
    }
    glutPostRedisplay();
}                                                                               


/* 
 * Draws lines to show edges of a non_filled hexagon.
 */
void display_squares(void) {
    if(count >= num_squares || count == 0) {
        double random1 = .1*(rand() % NINE);
        double random2 = .1*(rand() % NINE);
        double random3 = .1*(rand() % NINE);
        draw_color = vec4(random1 + .01, random2, random3, 0.5);
        offsetter = 0;
        count = 0;
        
    }

    count++;
    colors[ZERO] = draw_color;
    colors[ONE] = draw_color;
    colors[TWO] = draw_color;
    colors[THREE] = draw_color;

    vec2 temp1 = points[ZERO];
    vec2 temp2 = points[ONE];
    vec2 temp3 = points[TWO];
    vec2 temp4 = points[THREE];
    size = FOUR;

    if (((offsetter + 1) % (x_size + 1)) == 0){
        offsetter++;
    }

    points[ZERO] = points[offsetter];     
    points[ONE] = points[offsetter + 1];     
    points[TWO] = points[TWO + x_size + offsetter];     
    points[THREE] = points[1 + x_size + offsetter];     
    
    if (x_size == 1 && y_size == 1 && count == 1) {
        points[THREE] = vec2(x_value, x_value);
    }

    if (x_size == 1 && y_size != 1 && count == 1) {
        points[THREE] = vec2(x_value, .5 - (1.0 / y_size));
    }

    int i = 0;
    while( i < size ){
        i++;
    }
        
    glBufferSubData( GL_ARRAY_BUFFER, 0, size_var, points );
    glBufferSubData( GL_ARRAY_BUFFER, size_var, size_col, colors );

    points[ZERO] = temp1;
    points[ONE] = temp2;
    points[TWO] = temp3;
    points[THREE] = temp4;

    offsetter = offsetter + 1;

    draw_method     = GL_POLYGON; 
    glutPostRedisplay();
}

/*
 * The idle function that handles the color changing animation.
 */
void idle() {
    usleep(500000);
    display_squares();
}

/* 
 * Takes input from keyboard and uses the value to determine function call 
 * 
 * @param key the key input from the keyboard
 * @param x unused
 * @param y unused
 */
void keyboard(unsigned char key, int x, int y) {
    switch ( key ) {
    case UPP_Q:
    case LOW_Q:
        exit( 0 );
        break;
    }
}
                                                                                
/*
 * Makes all the function calls, sets up window size, and text at top of window.
 *
 * @param argc the count of the arguments entered
 * @param argv an address of the array that contains the arguments
 * @return int 0 if the program exits successfully, 1 if not
 */
int main(int argc, char **argv) {                                               
    draw_color = vec4( 0.0, 0.0, 0.0, 0.0);
    
    if (argc != THREE) {
        cout << "There must be 2 arguments entered indicating the rows";
        cout <<  " and columns. \n";
        exit(0);
    }
    glutInit(&argc, argv);                                                      
    glutInitDisplayMode( GLUT_RGBA );                                             

    glutInitWindowSize( WIN_SIZE, WIN_SIZE );                                               
    glutCreateWindow( WIN_NAME );                                         

    glewInit();                                                                 
    init( argv);
    glutDisplayFunc( display );                                                   
    glutKeyboardFunc( keyboard );                                                   
    display_points();
    glutIdleFunc(idle);
    glutMainLoop();                                                             

    delete [] points;
    delete [] colors;
    return 0;                                                                   
}                                
