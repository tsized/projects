#version 150                                                                    
                                                                                
in vec4 vPosition;                                                              

in vec4 in_color;
out vec4 out_color;
                                                                                
void                                                                            
main()                                                                          
{                                                                               
    gl_Position = vPosition;                                                    
    out_color = in_color;
}         
