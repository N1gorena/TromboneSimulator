package gui.tromboneScreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import trombone.Trombone;

import java.lang.Math.*;
import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;

import static javax.media.opengl.GL.*;  // GL constants
import static javax.media.opengl.GL2.*; // GL2 constants

import com.jogamp.opengl.util.FPSAnimator;

public class TromboneGUI extends GLCanvas implements GLEventListener {
	private Trombone myInstrument;
	private static final int FPS = 60;
	private FPSAnimator animator; 
	private GLU glu;
	private double offSet;
	public static Map<Character,Double> slideNoteOffset = null;
	static{ 
		slideNoteOffset = new HashMap<Character , Double>();
		slideNoteOffset.put('q', 0.0);
		slideNoteOffset.put('w', -3.25);
		slideNoteOffset.put('e', -6.75);
		slideNoteOffset.put('r', -10.5);
		slideNoteOffset.put('t', -14.5);
		slideNoteOffset.put('y', -18.75);
		slideNoteOffset.put('u', -22.25);
		slideNoteOffset.put('a', 0.0);
		slideNoteOffset.put('s', -3.25);
		slideNoteOffset.put('d', -6.75);
		slideNoteOffset.put('f', -10.5);
		slideNoteOffset.put('g', -14.5);
		slideNoteOffset.put('h', -18.75);
		slideNoteOffset.put('j', -22.25);
		slideNoteOffset.put('z', 0.0);
		slideNoteOffset.put('x', -3.25);
		slideNoteOffset.put('c', -6.75);
		slideNoteOffset.put('v', -10.5);
		slideNoteOffset.put('b', -14.5);
		slideNoteOffset.put('n', -18.75);
		slideNoteOffset.put('m', -22.25);
	}
	private static enum Axis{incX,decX,incY,decY,incZ,decZ};
	private static enum Plane{XY,XZ,YX,YZ,ZX,ZY};
	
	public TromboneGUI(Trombone Tromby, GLCapabilities glcapabilities){
		super(glcapabilities);
		//this.setMinimumSize(new Dimension(100,100));
		//this.setSize(100, 100);
		if(Tromby == null){
			System.out.println("SOMETHING WENT WRONG");
			Thread.dumpStack();
		}
		this.myInstrument = Tromby;
		//this.setBackground(Color.BLACK);
		//this.setSize(700,700);
		this.animator = new FPSAnimator(this,FPS,true);
		animator.start();
		this.addGLEventListener(this);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
	      glu = new GLU();                         // get GL Utilities
	      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
	      gl.glClearDepth(1.0f);      // set clear depth value to farthest
	      gl.glEnable(GL_DEPTH_TEST); // enables depth testing
	      gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
	      gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
	      gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
	    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	    gl.glLoadIdentity();  // reset the model-view matrix
	    
	    float seperationDistance = 3.0f;
	    float slideLength = 24.0f;
	    
	      // ----- Your OpenGL rendering code here (Render a white triangle for testing) -----
	    //Camera
		    //X neg = Spin axis towards you from sides
		    //Y neg = Look Left
		    //Z neg = Spin axis Clockwise from front LeftHanded
	    	//Done Bottom to Top
	    		
	    		
	    		//Missing old working viewpoint
	    		gl.glRotatef(90, 0.0f, -1.0f, 0.0f);
	    	
	    	//Negative means Positive?
	    		
	    		gl.glTranslatef( -slideLength - 5.5f , -2.4f, seperationDistance);
		    	//gl.glTranslatef( 0.0f , -30.5f,-30.5f);old translation
		   
	    //Brush
	    	//Orienting Axes
			   /*gl.glBegin(GL.GL_LINES); // draw using triangles
			    //Positive Axes 
			    gl.glColor3d(0.0f, 0.0f, 1.0f);
			    gl.glVertex3d(0, 0, 0);
			    gl.glVertex3d(1.8f, 0, 0);
			    gl.glColor3d(1.0f, 0.0f, 0.0f);
			    gl.glVertex3d(0, 0, 0);
			    gl.glVertex3d(0, 1.2f, 0);
			    gl.glColor3d(0.0f, 1.0f, 0.0f);
			    gl.glVertex3d(0, 0, 0);
			    gl.glVertex3d(0, 0, 1);
			    //Neg Axes
			    gl.glColor3d(0.0f, 0.0f, 1.0f);
			    gl.glVertex3d(0, 0, 0);
			    gl.glVertex3d(-1.8f, 0, 0);
			    gl.glColor3d(1.0f, 0.0f, 0.0f);
			    gl.glVertex3d(0, 0, 0);
			    gl.glVertex3d(0, -1.2f, 0);
			    gl.glColor3d(0.0f, 1.0f, 0.0f);
			    gl.glVertex3d(0, 0, 0);
			    gl.glVertex3d(0, 0, -1);
			    gl.glEnd();
			    */
			//Background Done    
		    //Object Trombone
			
				//OuterSlide section
					double trombHeight = 0.5f;
					double trombInnerOffset = -0.1f;
					
					gl.glColor3d(0.71f,0.65f,0.26f);
					
					createSquareTube2( gl, trombHeight , -trombHeight + offSet , trombHeight , 0.0f , Axis.decZ , seperationDistance );
					createNegativeTravelElbow( gl , trombHeight , offSet , 0.0f , 0.0f , Plane.ZY , 1 );//Near side of end of slide
					createNegativeTravelElbow( gl , trombHeight , offSet , 0.0f , -3.0f , Plane.ZY , 2 );//Far side of end of slide
					createSquareTube2( gl , trombHeight , slideLength + offSet , trombHeight , -seperationDistance , Axis.decX, slideLength );//Far side of slide
					createSquareTube2( gl , trombHeight , slideLength + offSet, trombHeight , trombHeight , Axis.decX, slideLength );//Near side of slide
					createSquareTube2( gl , trombHeight , (slideLength-3.5f) + offSet ,trombHeight, 0.0f, Axis.decZ, seperationDistance );
					createSquareTube2( gl , trombHeight , slideLength - trombHeight + offSet ,trombHeight, 0.0f, Axis.decZ, seperationDistance );
				//MouthPiece
					double mouthPieceLength = 3.0f;
					gl.glColor3d( 0.765625f , 0.77734375f , 0.805f );
					
					createSquareTube2( gl , trombHeight , slideLength + mouthPieceLength , trombHeight , -seperationDistance ,  Axis.decX, mouthPieceLength );
				//InnerSlide TODO
					
					createSquareTube2( gl , trombHeight + (2*trombInnerOffset) , slideLength, trombHeight + trombInnerOffset , -seperationDistance + trombInnerOffset , Axis.decX, (slideLength-1) );//Far side of slide
					createSquareTube2( gl , trombHeight + (2*trombInnerOffset) , slideLength, trombHeight + trombInnerOffset , trombHeight + trombInnerOffset , Axis.decX, (slideLength-1) );//Near side of slide
				//TuningSection
					double sectionLength = 15.5f;
					double verticalSeperation = 3.0f;
					gl.glColor3d(0.71f,0.65f,0.26f);
					
					createSquareTube2( gl , trombHeight , slideLength + sectionLength , trombHeight , trombHeight , Axis.decX , sectionLength );//Bottom
					createNegativeTravelElbow( gl , trombHeight , slideLength + sectionLength , trombHeight , 0.0f, Plane.XZ , 1);//Turn up
					createSquareTube2( gl , trombHeight , slideLength + sectionLength + trombHeight , trombHeight + verticalSeperation , trombHeight , Axis.decY, verticalSeperation );//Straight up
					createPositiveTravelElbow( gl , trombHeight, slideLength + sectionLength  , trombHeight + verticalSeperation , 0.0f , Plane.XZ , 1);//Turn Back
					createSquareTube2( gl , trombHeight , slideLength + (sectionLength)/2 , trombHeight + verticalSeperation, trombHeight , Axis.decY , verticalSeperation);//Spanning tube
					createSquareTube2( gl , trombHeight , slideLength + sectionLength , trombHeight + verticalSeperation + trombHeight, trombHeight , Axis.decX , sectionLength );//Head Back/Top
				//Bell
					double Cx = slideLength;
					double Cy = verticalSeperation + (trombHeight/2.0f) + trombHeight;
					double Cz = trombHeight/2.0f;
					createBell(gl , Cx , Cy, Cz , trombHeight/2 + 0.1f);    
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
		 
	      if (height == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)width / height;
	 
	      // Set the view port (display area) to cover the entire window
	      gl.glViewport(0, 0, width, height);
	 
	      // Setup perspective projection, with aspect ratio matches viewport
	      gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
	      gl.glLoadIdentity();             // reset projection matrix
	      glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar
	 
	      // Enable the model-view transform
	      gl.glMatrixMode(GL_MODELVIEW);
	      gl.glLoadIdentity(); // reset
		
	}
	
	private void drawCircle(GL2 brush, double circleRadius , Plane pl , double Cx , double Cy , double Cz){
		double radius = circleRadius;
		double pi = Math.PI;
		
		brush.glBegin(GL.GL_LINE_LOOP);
		
		if( pl == Plane.XY || pl == Plane.YX ){
			for(double ini = 0.0f ; ini < 2*pi ; ini+=0.1){
				brush.glVertex3d( Cx + ( radius*Math.cos(ini) ) , Cy + ( radius * Math.sin(ini) ), Cz + 0.0f);
			}
		}
		else if( pl == Plane.XZ || pl == Plane.ZX ){
			for(double ini = 0.0f ; ini < 2*pi ; ini+=0.01f){
				brush.glVertex3d( Cx + ( radius*Math.cos(ini) ), Cy + 0.0f, Cz + ( radius * Math.sin(ini) ) );
			}
		}
		else if( pl == Plane.YZ || pl == Plane.ZY ){
			for(double ini = 0.0f ; ini < 2*pi ; ini+=0.1){
				brush.glVertex3d( Cx + 0.0f , Cy + ( radius * Math.sin(ini) ), Cz + ( radius*Math.cos(ini) ) );
			}
		}
		brush.glEnd();
	}
	
	//Assuming Quads
	//Takes in a sideLength, a "brush", the top left coordinate in x,y,z and enums for orientation.
	//Square is drawn expanding in leading enum direction by amount=sideLength,
	//and then expanding in second enum direction by amount=sideLength;
	private void createSquare(GL2 gl, double sideLength, double TLx, double TLy, double TLz, Axis expansion1, Axis expansion2){
	    gl.glBegin(GL2.GL_QUADS);
	    gl.glVertex3d(TLx,TLy,TLz);
	    
	    double x = TLx;
	    double y = TLy;
	    double z = TLz;
	    switch(expansion1){
	    	case incX:
    			x = TLx + sideLength;
    			gl.glVertex3d(x, TLy, TLz); 
    			break;
	    	case decX:
	    		x = TLx - sideLength;
    			gl.glVertex3d(x, TLy, TLz); 
    			break;
	    	case incY:
	    		y = TLy + sideLength;
    			gl.glVertex3d(TLx, y, TLz); 
    			break;
	    	case decY:
	    		y = TLy - sideLength;
    			gl.glVertex3d(TLx, y, TLz); 
    			break;
	    	case incZ:
	    		z = TLz + sideLength;
    			gl.glVertex3d(TLx, TLy, z); 
    			break;
	    	case decZ:
	    		z = TLz - sideLength;
    			gl.glVertex3d(TLx, TLy, z); 
    			break;
	    	default:System.out.println("fucked up CreateSquare");break;
	    }
	    switch(expansion2){
	    	case incX:gl.glVertex3d(TLx + sideLength, y, z); break;
	    	case decX:gl.glVertex3d(TLx - sideLength, y, z); break;
	    	case incY:gl.glVertex3d(x, TLy + sideLength, z); break;
	    	case decY:gl.glVertex3d(x, TLy - sideLength, z); break;
	    	case incZ:gl.glVertex3d(x, y, TLz + sideLength); break;
	    	case decZ:gl.glVertex3d(x, y, TLz - sideLength); break;
	    	default:System.out.println("fucked up CreateSquare");break;
	    }
	    switch(expansion2){
	    	case incX:gl.glVertex3d(TLx + sideLength, TLy, TLz); break;
	    	case decX:gl.glVertex3d(TLx - sideLength, TLy, TLz); break;
	    	case incY:gl.glVertex3d(TLx, TLy + sideLength, TLz); break;
	    	case decY:gl.glVertex3d(TLx, TLy - sideLength, TLz); break;
	    	case incZ:gl.glVertex3d(TLx, TLy, TLz + sideLength); break;
	    	case decZ:gl.glVertex3d(TLx, TLy, TLz - sideLength); break;
	    	default:System.out.println("fucked up CreateSquare");break;
	    }
	    gl.glEnd();
	}
	
	//Assuming Quads
	//Takes in ah, a "brush", the top left coordinate in x,y,z and enums for orientation.
	//Square is drawn expanding in leading enum direction by amount=height,
	//and then expanding in second enum direction by amount=width;
	private void createRectangle(GL2 gl, double height , double width , double TLx , double TLy , double TLz , Axis expansion1 , Axis expansion2){
		    gl.glBegin(GL2.GL_QUADS);
		    gl.glVertex3d(TLx,TLy,TLz);
		    
		    double x = TLx;
		    double y = TLy;
		    double z = TLz;
		    switch(expansion1){
		    	case incX:
	    			x = TLx + height;
	    			gl.glVertex3d(x, TLy, TLz); 
	    			break;
		    	case decX:
		    		x = TLx - height;
	    			gl.glVertex3d(x, TLy, TLz); 
	    			break;
		    	case incY:
		    		y = TLy + height;
	    			gl.glVertex3d(TLx, y, TLz); 
	    			break;
		    	case decY:
		    		y = TLy - height;
	    			gl.glVertex3d(TLx, y, TLz); 
	    			break;
		    	case incZ:
		    		z = TLz + height;
	    			gl.glVertex3d(TLx, TLy, z); 
	    			break;
		    	case decZ:
		    		z = TLz - height;
	    			gl.glVertex3d(TLx, TLy, z); 
	    			break;
		    	default:System.out.println("fucked up CreateSquare");break;
		    }
		    switch(expansion2){
		    	case incX:gl.glVertex3d(TLx + width, y, z); break;
		    	case decX:gl.glVertex3d(TLx - width, y, z); break;
		    	case incY:gl.glVertex3d(x, TLy + width, z); break;
		    	case decY:gl.glVertex3d(x, TLy - width, z); break;
		    	case incZ:gl.glVertex3d(x, y, TLz + width); break;
		    	case decZ:gl.glVertex3d(x, y, TLz - width); break;
		    	default:System.out.println("fucked up CreateSquare");break;
		    }
		    switch(expansion2){
		    	case incX:gl.glVertex3d(TLx + width, TLy, TLz); break;
		    	case decX:gl.glVertex3d(TLx - width, TLy, TLz); break;
		    	case incY:gl.glVertex3d(TLx, TLy + width, TLz); break;
		    	case decY:gl.glVertex3d(TLx, TLy - width, TLz); break;
		    	case incZ:gl.glVertex3d(TLx, TLy, TLz + width); break;
		    	case decZ:gl.glVertex3d(TLx, TLy, TLz - width); break;
		    	default:System.out.println("fucked up CreateSquare");break;
		    }
		    gl.glEnd();
		}
	
	//TODO Most Recent
	//Creating elbow by giving center coordinates and a plane and a quadrant in said plane.
	//From center point, create elbow will create a square in the plane in the quadrant using the given center as a (0,0,0) point
	//Elbow will be created as if looking at the elbow from the non-used dimensions perspective staring at the origin from some distance back
	//First axis of plane is the "X" axis, second axis of the plane is "Y" axis.
	//It will be as though the elbow is curling toward the "Y" dimension of any given plane
	//Elbows: Traveling in negative "X" direction, get sent in negative non-used direction
	//
	private void createNegativeTravelElbow(GL2 brush,double sideLength, double Cx, double Cy, double Cz, Plane plane, int quadrant){
		brush.glBegin(GL2.GL_QUAD_STRIP);
		
		//Quadrant Validation?
		
		double angleFrom = 0.0f;
		double angleTo = 0.0f;
		
		switch(quadrant){
			case 1:
				angleFrom = 0.0f;
				angleTo = 90.0f;
				break;
			case 2:
				angleFrom = 180.0f;
				angleTo = 90.0f;
				break;
			case 3:
				angleFrom = 180.0f;
				angleTo = 270.0f;
				break;
			case 4:
				angleFrom = 360.0f;
				angleTo = 270.0f;
				break;
			default:
				System.out.println("Funked Up");;
				break;
		}
		
		if( quadrant%2 != 0 ){//ODD quadrants
			//Different perspective I take I think...
			for(double angle = angleFrom ; angle < angleTo ; angle += 0.001f ){
				//TODO Obey expand axis rules, change it to plane to plane
				brush.glVertex3d(Cx, Cy, Cz);//1
				//Up the second Axis
				//Point 2
				switch(plane){
					case XY:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
								break;
							case 3:
								brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
								break;
							default:
								System.out.println("fucked up1");
								break;
						}
						break;
					case XZ:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx , Cy , Cz + sideLength );//2
								break;
							case 3:
								brush.glVertex3d( Cx , Cy , Cz - sideLength);//2
								break;
							default:
								System.out.println("fucked up2");
								break;
						}
						break;
					case YX:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
								break;
							case 3:
								brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
								break;
							default:
								System.out.println("fucked up3");
								break;
						}
						break;
					case YZ:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx , Cy , Cz + sideLength );//2
								break;
							case 3:
								brush.glVertex3d( Cx , Cy , Cz - sideLength );//2
								break;
							default:
								System.out.println("fucked up4");
								break;
						}
						break;
					case ZX:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
								break;
							case 3:
								brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
								break;
							default:
								System.out.println("fucked up3");
								break;
						}
						break;
					case ZY:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
								break;
							case 3:
								brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
								break;
							default:
								System.out.println("fucked up3");
								break;
						}
						break;
					default:
						System.out.println("fucked up5");
						break;
				}
				//Point 4
				switch(plane){
					case XY:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							case 3:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked up6");
								break;
						}
						break;
					case XZ:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
								break;
							case 3:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
								break;
							default:
								System.out.println("fucked up7");
								break; 
						}
						break;
					case YX://TODO
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							case 3:
								brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked up8");
								break;
						}
						break;
					case YZ:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx  - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
								break;
							case 3:
								brush.glVertex3d( Cx  + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
								break;
							default:
								System.out.println("fucked up9");
								break;
						}
						break;
					case ZX:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							case 3:
								brush.glVertex3d( Cx , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked up7");
								break; 
						}
						break;
					case ZY:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							case 3:
								brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked up7");
								break; 
						}
						break;
					default:
						System.out.println("fucked up10");
						break;
				}
				//Point three
				switch(plane){
					case XY:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + sideLength , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							case 3:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked up11");
								break;
						}
						break;
					case XZ:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + sideLength );//3;
								break;
							case 3:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz - sideLength );//3;
								break;
							default:
								System.out.println("fucked up12");
								break;
						}
						break;
					case YX:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							case 3:
								brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked up13");
								break;
						}
						break;
					case YZ:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + sideLength );//3;
								break;
							case 3:
								brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - sideLength );//3;
								break;
							default:
								System.out.println("fucked up14");
								break;
						}
						break;
					case ZX:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx + sideLength , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							case 3:
								brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked up12");
								break;
						}
						break;
					case ZY:
						switch(quadrant){
							case 1:
								brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							case 3:
								brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked up12");
								break;
						}
						break;
					default:
						System.out.println("fucked up last");
						break;
					
				}
			}
			brush.glEnd();
		}
		else{//EVENS
			for(double angle = angleFrom ; angle > angleTo ; angle -= 0.001f ){
				//TODO Obey expand axis rules, change it to plane to plane
				brush.glVertex3d(Cx, Cy, Cz);//1
				//Up the second Axis
				switch(plane){
					case XY:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
								break;
							case 4:
								brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
								break;
							default:
								System.out.println("fucked upA");
								break;
						}
						break;
					case XZ:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx , Cy , Cz  + sideLength );//2
								break;
							case 4:
								brush.glVertex3d( Cx , Cy , Cz  - sideLength );//2
								break;
							default:
								System.out.println("fucked upB");
								break;
						}
						break;
					case YX:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
								break;
							case 4:
								brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
								break;
							default:
								System.out.println("fucked upA");
								break;
						}
						break;
					case YZ:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx , Cy , Cz + sideLength );//2
								break;
							case 4:
								brush.glVertex3d( Cx , Cy , Cz - sideLength );//2
								break;
							default:
								System.out.println("fucked upA");
								break;
						}
						break;
					case ZX:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
								break;
							case 4:
								brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
								break;
							default:
								System.out.println("fucked upB");
								break;
						}
						break;
					case ZY:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
								break;
							case 4:
								brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
								break;
							default:
								System.out.println("fucked upB");
								break;
						}
						break;
					default:
						System.out.println("fucked upC");
						break;
				}
				
				switch(plane){
					case XY:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							case 4:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked upD");
								break;
						}
						break;
					case XZ:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
								break;
							case 4:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
								break;
							default:
								System.out.println("fucked upE");
								break;
						}
						break;
					case YX:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							case 4:
								brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked upD");
								break;
						}
						break;
					case YZ:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
								break;
							case 4:
								brush.glVertex3d( Cx  + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
								break;
							default:
								System.out.println("fucked upD");
								break;
						}
						break;
					case ZX:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							case 4:
								brush.glVertex3d( Cx , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked upD");
								break;
						}
						break;
					case ZY:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							case 4:
								brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
								break;
							default:
								System.out.println("fucked upD");
								break;
						}
						break;
					default:
						System.out.println("fucked upF");
						break;
				}
				
				switch(plane){
					case XY:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + sideLength , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							case 4:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked upG");
								break;
						}
						break;
					case XZ:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + sideLength );//3;
								break;
							case 4:
								brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz - sideLength );//3;
								break;
							default:
								System.out.println("fucked up H");
								break;
						}
						break;
					case YX:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							case 4:
								brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked upG");
								break;
						}
						break;
					case YZ:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + sideLength );//3;
								break;
							case 4:
								brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - sideLength );//3;
								break;
							default:
								System.out.println("fucked upG");
								break;
						}
						break;
					case ZX:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx + sideLength , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							case 4:
								brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked upG");
								break;
						}
						break;
					case ZY:
						switch(quadrant){
							case 2:
								brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							case 4:
								brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
								break;
							default:
								System.out.println("fucked upG");
								break;
						}
						break;
					default:
						System.out.println("fucked upI");
						break;
					
				}

			}
			brush.glEnd();
		}
	}
	
	//Defaults to traveling in negative direction because its the same thing as traveling in positive direction
	//+Y axis is Up when traveling x or z axis
	//+Z axis is Up when traveling y axis.
	private void createSquareTube(GL2 brush, double sideLength, double TLx, double TLy, double TLz, Axis direction, double stretchLength){
		//TODO Current
		brush.glBegin(GL2.GL_QUAD_STRIP);
		//Square 1
			//Point 1
			brush.glVertex3d( TLx , TLy , TLz );
			//Point 2
			if(direction == Axis.decX || direction == Axis.incX){
				brush.glVertex3d( TLx , TLy , TLz - sideLength );
			}
			else if( direction == Axis.decY || direction == Axis.incY){
				brush.glVertex3d( TLx - sideLength , TLy , TLz );
			}
			else if(direction == Axis.decZ || direction == Axis.incZ){
				brush.glVertex3d( TLx + sideLength  , TLy, TLz );
			}
			//Point 4
			if(direction == Axis.decX || direction == Axis.incX){
				brush.glVertex3d( TLx , TLy - sideLength , TLz );
			}
			else if( direction == Axis.decY || direction == Axis.incY){
				brush.glVertex3d( TLx , TLy , TLz - sideLength );
			}
			else if(direction == Axis.decZ || direction == Axis.incZ){
				brush.glVertex3d( TLx , TLy  - sideLength , TLz );
			}
			//point 3
			if(direction == Axis.decX || direction == Axis.incX){
				brush.glVertex3d( TLx , TLy - sideLength , TLz - sideLength );
			}
			else if( direction == Axis.decY || direction == Axis.incY){
				brush.glVertex3d( TLx  - sideLength , TLy , TLz - sideLength );
			}
			else if(direction == Axis.decZ || direction == Axis.incZ){
				brush.glVertex3d( TLx + sideLength , TLy - sideLength , TLz );
			}
		//Square 2
			for(double length = 0.0f ; length < stretchLength ; length += 0.0001f){
				//Point 1
				if(direction == Axis.decX || direction == Axis.incX){
					brush.glVertex3d( TLx - length , TLy , TLz );
				}
				else if( direction == Axis.decY || direction == Axis.incY){
					brush.glVertex3d( TLx , TLy - length , TLz );
				}
				else if(direction == Axis.decZ || direction == Axis.incZ){
					brush.glVertex3d( TLx  , TLy, TLz - length);
				}
				//Point 2
				if(direction == Axis.decX || direction == Axis.incX){
					brush.glVertex3d( TLx - length , TLy , TLz - sideLength );
				}
				else if( direction == Axis.decY || direction == Axis.incY){
					brush.glVertex3d( TLx - sideLength , TLy - length , TLz );
				}
				else if(direction == Axis.decZ || direction == Axis.incZ){
					brush.glVertex3d( TLx + sideLength  , TLy, TLz - length );
				}
				//Point 4
				if(direction == Axis.decX || direction == Axis.incX){
					brush.glVertex3d( TLx - length , TLy - sideLength , TLz );
				}
				else if( direction == Axis.decY || direction == Axis.incY){
					brush.glVertex3d( TLx , TLy - length , TLz - sideLength );
				}
				else if(direction == Axis.decZ || direction == Axis.incZ){
					brush.glVertex3d( TLx , TLy  - sideLength , TLz - length );
				}
				//point 3
				if(direction == Axis.decX || direction == Axis.incX){
					brush.glVertex3d( TLx - length , TLy - sideLength , TLz - sideLength );
				}
				else if( direction == Axis.decY || direction == Axis.incY){
					brush.glVertex3d( TLx  - sideLength , TLy - length , TLz - sideLength );
				}
				else if(direction == Axis.decZ || direction == Axis.incZ){
					brush.glVertex3d( TLx + sideLength , TLy - sideLength , TLz - length );
				}
			}
		
		
		brush.glEnd();
	}
	
	
	//Defaults to traveling in negative direction because its the same thing as traveling in positive direction
	//+Y axis is Up when traveling x or z axis
	//+Z axis is Up when traveling y axis.
	private void createSquareTube2(GL2 brush, double sideLength, double TLx, double TLy, double TLz, Axis direction, double stretchLength){
		//-x
		if(direction == Axis.incX || direction == Axis.decX){
		createSquare(brush, sideLength , TLx,TLy,TLz,Axis.decZ,Axis.decY);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy,TLz,Axis.decY,Axis.decX);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy,TLz,Axis.decZ,Axis.decX);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy - sideLength,TLz-sideLength,Axis.incZ,Axis.decX);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy - sideLength,TLz-sideLength,Axis.incY,Axis.decX);
		createSquare(brush,sideLength, TLx - stretchLength,TLy,TLz,Axis.decZ,Axis.decY);
		}
		//-y
		else if(direction == Axis.incY || direction == Axis.decY){
		createSquare(brush, sideLength , TLx,TLy,TLz,Axis.decX,Axis.decZ);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy,TLz,Axis.decX,Axis.decY);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy,TLz,Axis.decZ,Axis.decY);
		createRectangle(brush,sideLength,stretchLength,TLx - sideLength,TLy,TLz-sideLength,Axis.incZ,Axis.decY);
		createRectangle(brush,sideLength,stretchLength,TLx - sideLength,TLy,TLz-sideLength,Axis.incX,Axis.decY);
		createSquare(brush,sideLength, TLx,TLy - stretchLength,TLz,Axis.decX,Axis.decZ);
		}
		//-z
		else if(direction == Axis.incZ || direction == Axis.decZ){
		createSquare(brush, sideLength , TLx,TLy,TLz,Axis.incX,Axis.decY);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy,TLz,Axis.incX,Axis.decZ);
		createRectangle(brush,sideLength,stretchLength,TLx,TLy,TLz,Axis.decY,Axis.decZ);
		createRectangle(brush,sideLength,stretchLength,TLx+sideLength,TLy - sideLength,TLz,Axis.decX,Axis.decZ);
		createRectangle(brush,sideLength,stretchLength,TLx+sideLength,TLy - sideLength,TLz,Axis.incY,Axis.decZ);
		createSquare(brush,sideLength, TLx,TLy,TLz - stretchLength,Axis.incX,Axis.decY);
		}
	}
	//TODO Current if need be, not Done, just did the one I needed so far.
	//Creating elbow by giving center coordinates and a plane and a quadrant in said plane.
	//From center point, create elbow will create a square in the plane in the quadrant using the given center as a (0,0,0) point
	//Elbow will be created as if looking at the elbow from the non-used dimensions perspective staring at the origin from some distance back
	//First axis of plane is the "X" axis, second axis of the plane is "Y" axis.
	//It will be as though the elbow is curling toward the "Y" dimension of any given plane
	//Elbows: Traveling in Positive "X" direction, get sent in negative non-used direction
	//
	private void createPositiveTravelElbow(GL2 brush,double sideLength, double Cx, double Cy, double Cz, Plane plane, int quadrant){
			brush.glBegin(GL2.GL_QUAD_STRIP);
			
			//Quadrant Validation?
			
			double angleFrom = 0.0f;
			double angleTo = 0.0f;
			
			switch(quadrant){
				case 1:
					angleFrom = 0.0f;
					angleTo = 90.0f;
					break;
				case 2:
					angleFrom = 180.0f;
					angleTo = 90.0f;
					break;
				case 3:
					angleFrom = 180.0f;
					angleTo = 270.0f;
					break;
				case 4:
					angleFrom = 360.0f;
					angleTo = 270.0f;
					break;
				default:
					System.out.println("Funked Up");;
					break;
			}
			
			if( quadrant%2 != 0 ){//ODD quadrants
				//Different perspective I take I think...
				for(double angle = angleFrom ; angle < angleTo ; angle += 0.001f ){
					//TODO Obey expand axis rules, change it to plane to plane
					brush.glVertex3d(Cx, Cy, Cz);//1
					//Up the second Axis
					//Point 2
					switch(plane){
						case XY:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
									break;
								case 3:
									brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
									break;
								default:
									System.out.println("fucked up1");
									break;
							}
							break;
						case XZ:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx , Cy , Cz + sideLength );//2
									break;
								case 3:
									brush.glVertex3d( Cx , Cy , Cz - sideLength);//2
									break;
								default:
									System.out.println("fucked up2");
									break;
							}
							break;
						case YX:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
									break;
								case 3:
									brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
									break;
								default:
									System.out.println("fucked up3");
									break;
							}
							break;
						case YZ:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx , Cy , Cz + sideLength );//2
									break;
								case 3:
									brush.glVertex3d( Cx , Cy , Cz - sideLength );//2
									break;
								default:
									System.out.println("fucked up4");
									break;
							}
							break;
						case ZX:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
									break;
								case 3:
									brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
									break;
								default:
									System.out.println("fucked up3");
									break;
							}
							break;
						case ZY:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
									break;
								case 3:
									brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
									break;
								default:
									System.out.println("fucked up3");
									break;
							}
							break;
						default:
							System.out.println("fucked up5");
							break;
					}
					//Point 4
					switch(plane){
						case XY:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								case 3:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked up6");
									break;
							}
							break;
						case XZ:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
									break;
								case 3:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
									break;
								default:
									System.out.println("fucked up7");
									break; 
							}
							break;
						case YX://TODO
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								case 3:
									brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked up8");
									break;
							}
							break;
						case YZ:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx  - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
									break;
								case 3:
									brush.glVertex3d( Cx  + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
									break;
								default:
									System.out.println("fucked up9");
									break;
							}
							break;
						case ZX:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								case 3:
									brush.glVertex3d( Cx , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked up7");
									break; 
							}
							break;
						case ZY:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								case 3:
									brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked up7");
									break; 
							}
							break;
						default:
							System.out.println("fucked up10");
							break;
					}
					//Point three
					switch(plane){
						case XY:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + sideLength , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								case 3:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked up11");
									break;
							}
							break;
						case XZ:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + sideLength );//3;
									break;
								case 3:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz - sideLength );//3;
									break;
								default:
									System.out.println("fucked up12");
									break;
							}
							break;
						case YX:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								case 3:
									brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked up13");
									break;
							}
							break;
						case YZ:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + sideLength );//3;
									break;
								case 3:
									brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - sideLength );//3;
									break;
								default:
									System.out.println("fucked up14");
									break;
							}
							break;
						case ZX:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx + sideLength , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								case 3:
									brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked up12");
									break;
							}
							break;
						case ZY:
							switch(quadrant){
								case 1:
									brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								case 3:
									brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked up12");
									break;
							}
							break;
						default:
							System.out.println("fucked up last");
							break;
						
					}
				}
				brush.glEnd();
			}
			else{//EVENS
				for(double angle = angleFrom ; angle > angleTo ; angle -= 0.001f ){
					//TODO Obey expand axis rules, change it to plane to plane
					brush.glVertex3d(Cx, Cy, Cz);//1
					//Up the second Axis
					switch(plane){
						case XY:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
									break;
								case 4:
									brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
									break;
								default:
									System.out.println("fucked upA");
									break;
							}
							break;
						case XZ:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx , Cy , Cz  + sideLength );//2
									break;
								case 4:
									brush.glVertex3d( Cx , Cy , Cz  - sideLength );//2
									break;
								default:
									System.out.println("fucked upB");
									break;
							}
							break;
						case YX:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
									break;
								case 4:
									brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
									break;
								default:
									System.out.println("fucked upA");
									break;
							}
							break;
						case YZ:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx , Cy , Cz + sideLength );//2
									break;
								case 4:
									brush.glVertex3d( Cx , Cy , Cz - sideLength );//2
									break;
								default:
									System.out.println("fucked upA");
									break;
							}
							break;
						case ZX:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + sideLength , Cy , Cz );//2
									break;
								case 4:
									brush.glVertex3d( Cx - sideLength , Cy , Cz );//2
									break;
								default:
									System.out.println("fucked upB");
									break;
							}
							break;
						case ZY:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx , Cy + sideLength , Cz );//2
									break;
								case 4:
									brush.glVertex3d( Cx , Cy - sideLength , Cz );//2
									break;
								default:
									System.out.println("fucked upB");
									break;
							}
							break;
						default:
							System.out.println("fucked upC");
							break;
					}
					
					switch(plane){
						case XY:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								case 4:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked upD");
									break;
							}
							break;
						case XZ:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
									break;
								case 4:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz );//4
									break;
								default:
									System.out.println("fucked upE");
									break;
							}
							break;
						case YX:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								case 4:
									brush.glVertex3d( Cx , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked upD");
									break;
							}
							break;
						case YZ:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
									break;
								case 4:
									brush.glVertex3d( Cx  + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz );//4
									break;
								default:
									System.out.println("fucked upD");
									break;
							}
							break;
						case ZX:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								case 4:
									brush.glVertex3d( Cx , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked upD");
									break;
							}
							break;
						case ZY:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								case 4:
									brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//4
									break;
								default:
									System.out.println("fucked upD");
									break;
							}
							break;
						default:
							System.out.println("fucked upF");
							break;
					}
					
					switch(plane){
						case XY:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + sideLength , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								case 4:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked upG");
									break;
							}
							break;
						case XZ:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + sideLength );//3;
									break;
								case 4:
									brush.glVertex3d( Cx + (sideLength*Math.cos(Math.toRadians(angle))) , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz - sideLength );//3;
									break;
								default:
									System.out.println("fucked up H");
									break;
							}
							break;
						case YX:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								case 4:
									brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + (sideLength*Math.sin(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked upG");
									break;
							}
							break;
						case YZ:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz + sideLength );//3;
									break;
								case 4:
									brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy + (sideLength*Math.cos(Math.toRadians(angle))) , Cz - sideLength );//3;
									break;
								default:
									System.out.println("fucked upG");
									break;
							}
							break;
						case ZX:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx + sideLength , Cy - (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								case 4:
									brush.glVertex3d( Cx - sideLength , Cy + (sideLength*Math.sin(Math.toRadians(angle))) , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked upG");
									break;
							}
							break;
						case ZY:
							switch(quadrant){
								case 2:
									brush.glVertex3d( Cx - (sideLength*Math.sin(Math.toRadians(angle))) , Cy + sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								case 4:
									brush.glVertex3d( Cx + (sideLength*Math.sin(Math.toRadians(angle))) , Cy - sideLength , Cz + (sideLength*Math.cos(Math.toRadians(angle))) );//3;
									break;
								default:
									System.out.println("fucked upG");
									break;
							}
							break;
						default:
							System.out.println("fucked upI");
							break;
						
					}

				}
				brush.glEnd();
			}
		}
		
	private void createBell(GL2 brush, double Cx , double Cy, double Cz , double startRadius){

		
		//Orientation is opening in neg x direction, because Its what I need for my Trombone
		for( double startX = Cx ; startX > Cx - 5.0f; startX -= 0.01f ){
			drawCircle( brush , startRadius , Plane.YZ , startX , Cy , Cz);
			if(startRadius < 1.0f)
				startRadius += 0.001f;
		}
		
		for( double startX = Cx - 5.0f ; startX > Cx - 5.5f; startX -= 0.001f ){
			drawCircle( brush , startRadius , Plane.YZ , startX , Cy , Cz);
			if(startRadius < 3.0f)
				startRadius += 0.0015f;
		}
		
		
		
		
	}

	public void setOffset(double offSet){
		this.offSet = offSet;
		this.repaint();
	}

	public static double getSlideNoteOffset(char c) {
		return -5.0f;
	}
}
