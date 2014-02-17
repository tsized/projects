// Two-Dimensional Sierpinski Gasket       
// Generated using randomly selected vertices and bisection

#include "Angel.h"

const int NumPoints = 5000;

/*
 * Used to initialize vairables for attributes and setting up viewing
 */
void init( void ) {
    vec2 points[NumPoints];

    // Specifiy the vertices for a triangle
    vec2 vertices[3] = {
        vec2( -1.0, -1.0 ), vec2( 0.0, 1.0 ), vec2( 1.0, -1.0 )
    };

    // Select an arbitrary initial point inside of the triangle
    points[0] = vec2( 0.25, 0.50 );

    // compute and store N-1 new points
    for ( int i = 1; i < NumPoints; ++i ) {
        int j = rand() % 3;   // pick a vertex at random

        // Compute the point halfway between the selected vertex
        //   and the previous point
        points[i] = ( points[i - 1] + vertices[j] ) / 2.0;
    }

    // Create a vertex array object
    GLuint vao; // vertex array object used to encapsulate vertex array state
                // on the client side. We can switch between vao's easily
    /*
     * Create n number of vertex array names in the address pointed to by the
     * second argument. These names are not guaranteed to be a consecutive
     * set of integers.
     */
    glGenVertexArrays( 1, &vao );
    /*
     * Bind an array object. First call (on a given name) actually creates 
     * the object, subsequent calls make the named object active.
     */
    glBindVertexArray( vao );

    // Create and initialize a buffer object -- which is unformatted linear
    // memory allocated by the current OpenGL context. Can be used to store
    // vertex or pixel data. Used in a variety of different ways.
    GLuint buffer; 
    /*
     * Create n number of buffer array names in the address pointed to by the
     * second argument. These names are not guaranteed to be a consecutive
     * set of integers.
     */
    glGenBuffers( 1, &buffer );
    
    /*
     * Create the object and bind it to the current context. The first
     * argument (the target) indicates how you intend to use this buffer.
     * Second agrument is the buffer name.
     *
     * Open_GL may make judgement calls on your intended use of the object by
     * the first target you bind the object to. Could be important for
     * performance.
     */
    glBindBuffer( GL_ARRAY_BUFFER, buffer );

    /*
     * Create and initializes a buffer objects data store.
     * first argument (target) is the same as in bindbuffer
     * second arg is the size in bytes of the new data store
     * third arg is set of data to be copied into the data store (NULL) for no
     * copying.
     * fourth agument is usage
     * GL_X_Y
     * X = STREAM or STATIC or DYNAMIC
     * Y = DRAW or READ or COPY
     * 
     * STREAM  => contents modified once, used a few times
     * STATIC  => contents modified once, used many times
     * DYNAMIC => contents will be modified repeatedly and used many times
     * DRAW    => contents modified by the app and used as the source for GL
     *            drawing and image command specifications
     * READ    => contents are modified by reaading from the GL and ued to
     *            return data when queried by the app
     * COPY    => data store modified by reading from the GL and used at the
     *            source for drawing and image specification.
     */
    glBufferData( GL_ARRAY_BUFFER, sizeof(points), points, GL_STATIC_DRAW );

    // Load shaders and use the resulting shader program
    // Code written by the author of our book to get a module containing the
    // shaders that we can execute. This involves reading from the files,
    // compiling them, and linking them together.
    GLuint program = InitShader( "vshader21.glsl", "fshader21.glsl" );
    /*
     * Specifies the program object to use as part of the current rendering
     * state. The program object should have shader objects attached to it.
     */
    glUseProgram( program );

    // Initialize the vertex position attribute from the vertex shader
    // When the shaders are compiled and linked the names of the shader
    // variables are bound to indices within a table, the below function gets
    // us an index of one such shader variable.

    GLuint loc = glGetAttribLocation( program, "vPosition" );

    // enable, or disable a generic vertex array
    //glDisableVertexAttribArray( loc );
    glEnableVertexAttribArray( loc );

    /*
     * Define an array of generic vertex attribute data
     * (index, size, type, normalized, stride, pointer)
     * (index, size, type, stride, pointer)
     * index = the index of the vertex atttibute to be modified
     * size  = number of components per generic vertex attribute (1,2,3, or 4)
     * type  = Data type of each component
     * normalized = true or false
     * stride = byte offset between consecutive vertex attributes. 0, means
     * tightly packed in the array.
     * pointer = the offset to the first generic vertex attribute
     */
    glVertexAttribPointer( loc, 2, GL_FLOAT, GL_FALSE, 0,
                           BUFFER_OFFSET(0) );

    // specify a value for the color buffers when the are cleared.
    glClearColor( 1.0, 1.0, 1.0, 1.0 ); // white background
}

void display( void ) {
    // clear buffer to preset values.
    // GL_COLOR_BUFFER_BIT => buffers that are currently enabled for color
    // writing (there are other buffers)
    glClear( GL_COLOR_BUFFER_BIT );     // clear the window
    /* 
     * render primitives from array data
     * (mode, first, count)
     * mode => type of primitives to render.
     * first => starting index in the enables arrays
     * count, number of indices to be rendered.
     */
    glDrawArrays( GL_POINTS, 0, NumPoints );    // draw the points
    // force the execution o GL commands in finite time

    glFlush();
}

void keyboard( unsigned char key, int x, int y ) {
    switch ( key ) {
    case 033:
        exit( EXIT_SUCCESS );
        break;
    }
}


int main( int argc, char **argv ) {
    /* Initialize comminication between the windowing system and opengl
     *      argc - pointer to the unmodified argc variable from main. Pointer
     *      because glutInit may modify it (extract command line args intended
     *      for GLUT
     *      argv - pointer to unmodified variable from main (again it may be
     *      modified)
     */
    glutInit( &argc, argv );
    /*
     * Sets the initial display mode.
     * GLUT_RGBA    -- default if nothing is specified
     * GLUT_RGB     -- is now an alias for GLUT_RGBA
     * GLUT_INDEX   -- overrides GLUT_RGBA if also specified
     * GLUT_SINGLE  -- single buffered window 
     * GLUT_DOUBLE  -- doubly buffered window 
     * GLUT_DEPTH   -- select a window with a depth buffer
     * 
     * There are others, not mentioned here.
     */
    glutInitDisplayMode( GLUT_RGBA );
    /*
     * Set initial window size in pixels
     */
    glutInitWindowSize( 512, 512 );

    // If you are using freeglut, the next two lines will check if 
    // the code is truly 3.2. Otherwise, comment them out
    
    /*
     * Introduced with OpenGL 3.0, only available when using freeglut
     * First integer is major version, second integer is minor version.
     *
     * An OpenGl context is the data structure where OpenGL stores state
     * information to be used when you are rendering images. Texttures,
     * server-side buffer objects, function entry points, blending states...
     * in previous versions of opengl there was only one type of context (full
     * context)
     * Noe there is a new type of context (forward-compatible-context -- hides
     * features marked for future removal. // without this a 2.1 compatible
     * context is requested.
     */
    glutInitContextVersion( 3, 2 );
    /*
     * OpenGL 3.0 also introduced the concept of profiles. A profile is a
     * subset of OpenGL functionaliity for a specific application domain,
     * i.e., gaming, CAD, embedded platforms.
     */
    glutInitContextProfile( GLUT_CORE_PROFILE );

    /*
     * Creates a top-level window, pass in the window's title. The display
     * state of the window is set to "Shown", but this display state is not
     * acted upon until glutMainLoop is called.
     */
    glutCreateWindow( "Sierpinski Gasket" );

    /*
     * Should really check to make sure this return GLEW_OK (which is a
     * GLenum). if it works you can use the GLEW extensions 
     */
    glewInit();


    /*
     * Just a function to set variables.
     */
    init();

    /*
     * set up the display callback
     */
    glutDisplayFunc( display );
    /*
     * set up the keyboard callback
     */
    glutKeyboardFunc( keyboard );
    /*
     * Begins an event processing loop. No events, means just sit and wait,
     * wheee. Any events and the program responds to them through the use of
     * function callbacks.
     */
    glutMainLoop();
    // exit the program
    return 0;
}
