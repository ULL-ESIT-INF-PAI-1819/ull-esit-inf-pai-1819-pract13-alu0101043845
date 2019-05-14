import javax.swing.Timer;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public class Disparos extends JPanel {

  private ArrayList<ArrayList<Integer>> balls;
  private ArrayList<ArrayList<Color>> colours;
  private ArrayList<Color> colors;
  private Color launchColor;
  private Point mouseXY;
  private boolean launch;
  private boolean chocado;
  private ArrayList<Point> trayectoria;
  private int radio = 50;
  private double t;
  private double tx;
  private double launchAngle;
  private double realTimeAngle;
  private int pos;
  private AudioClip currentAudio;
  private AudioClip[] audioClips = new AudioClip[3];
  private int eliminadas;

  public Disparos() {
	  eliminadas = 0;
	launch = false;
	chocado = false;
  	trayectoria = new ArrayList<Point>();
  	mouseXY = new Point();
  	pos = -1000;
  	t = 0;
  	tx=0;
  	realTimeAngle = Math.toRadians(90);
    balls = new ArrayList<ArrayList<Integer>>();
    colours = new  ArrayList<ArrayList<Color>>();
    audioClips[0] = Applet.newAudioClip(getClass().getResource("/sounds/La.wav"));
    audioClips[1] = Applet.newAudioClip(getClass().getResource("/sounds/Fa.wav"));
    audioClips[2] = Applet.newAudioClip(getClass().getResource("/sounds/Re.wav"));
    colors = new  ArrayList<Color>();
	colors.add(Color.BLUE);
	colors.add(Color.PINK);
	colors.add(Color.GREEN);
	colors.add(Color.YELLOW);
	colors.add(Color.RED);
	Random aleatorio = new Random(System.currentTimeMillis());
    for(int j = 0; j < 4; j++) {
    	balls.add(new ArrayList<Integer>());
    	colours.add(new ArrayList<Color>());
    	if(j%2==0) {
	        for(int i = 0; i < 10; i++) {
	        	balls.get(j).add(i);
	    		colours.get(j).add(colors.get(aleatorio.nextInt(colors.size())));
	        }
    	}else {
            for(int i = 0; i < 9; i++) {
            	balls.get(j).add(i);
        		colours.get(j).add(colors.get(aleatorio.nextInt(colors.size())));
            }
    	}
    }
    
    launchColor = colors.get(aleatorio.nextInt(colors.size()));
  	trayectoria.add(new Point(0, 0));
  }

  private void checkColor() {
	  boolean in = false;
	    for(int k = 0; k < colors.size(); k++) {
	    	in = false;
	        for(int i = 0; i < colours.size() && !in; i++) {
	            for(int j = 0; j < colours.get(i).size() && !in; j++) {
	            	if(colours.get(i).get(j).equals(colors.get(k)))
	            		in = true;
	            }
	        }
	        if(!in)
	        	colors.remove(k);
	    }
  }
  
  public void Lanzar(Point p) {
	if(!launch) {
		mouseXY = p;
		t = 0;
		tx=0;
	  	trayectoria = new ArrayList<Point>();
	  	trayectoria.add(new Point(getWidth()/2, getHeight()));
	  	launchAngle = calcularFuncion();
		launch = true;
		chocado = false;
	}
  }

  public void ActualizarCanon(Point p) {
	mouseXY = p;
  	realTimeAngle = calcularFuncion();
  }
  
  public boolean Choca(Point p) {
	  Point t = trayectoria.get(trayectoria.size()-1);
	  double distancia;
	  distancia = Math.abs(Math.sqrt(((p.x-t.x)*(p.x-t.x))+((p.y-t.y)*(p.y-t.y))));
	  if(distancia < radio)
		  return true;
	  else 
		  return false;
  }
  
  public void ComprobarChoques() {
	  int c = 0;
	  Point index = new Point();
	  for(int j = 0; j < balls.size(); j++) {
		  for(int i = 0; i < balls.get(j).size(); i++) {
			  Point aux = new Point((balls.get(j).get(i)*pos)+5+radio/2, 5+(j * radio)+radio/2);
			  if(Choca(aux)) {
				  c++;
				  index.x = j;
				  index.y = i;
			  }
		  }
	  }
	  if(c > 0) {
		  if(c == 1 && colours.get(index.x).get(index.y).equals(launchColor)) {
			  boolean vecina = true;
			  int del = 1;
			  int esp = 0;
			  while(index.y+1 < balls.get(index.x).size() && vecina) {
				  if(balls.get(index.x).get(index.y+1) == balls.get(index.x).get(index.y) + esp) {
					  if(colours.get(index.x).get(index.y+1).equals(launchColor)) {
						  balls.get(index.x).remove(index.y+1);
						  colours.get(index.x).remove(index.y+1);
						  del++;
						  esp++;
					  }else
						  vecina = false;
				  }else 
					  vecina = false;
			  }

			  esp = 0;
			  vecina = true;
			  while(index.y-1 >= 0 && vecina) {
				  if(balls.get(index.x).get(index.y-1) == balls.get(index.x).get(index.y) - esp) {
					  if(colours.get(index.x).get(index.y-1).equals(launchColor)) {
						  balls.get(index.x).remove(index.y-1);
						  colours.get(index.x).remove(index.y-1);
						  index.y--;
						  del++;
						  esp++;
					  }else
						  vecina = false;
				  }else 
					  vecina = false;
			  }
			  balls.get(index.x).remove(index.y);
			  colours.get(index.x).remove(index.y);
			  if(del>1)
				  currentAudio = audioClips[1];
			  else
				  currentAudio = audioClips[2];
			  currentAudio.play();
			  eliminadas += del;
			  checkColor();
		  }else {
			  if(index.x <= balls.size()) {
				  balls.add(new ArrayList<Integer>());
				  colours.add(new ArrayList<Color>());
			  }
			  balls.get(index.x+1).add(balls.get(index.x).get(index.y));
			  colours.get(index.x+1).add(launchColor);
			  currentAudio = audioClips[0];
			  currentAudio.play();

		  }
		  t = 0;
		  tx = 0;
		  Random aleatorio = new Random(System.currentTimeMillis());
		  launchColor = colors.get(aleatorio.nextInt(colors.size()));
		  trayectoria = new ArrayList<Point>();
		  trayectoria.add(new Point(getWidth()/2, getHeight()));
		  launch = false;
	  }
		  
  }
  
  public int getEliminadas() {
	  return eliminadas;
  }
  
  private double calcularFuncion() {
	  double x1 = getWidth()/2;
	  double x2 = mouseXY.x;
	  double y1 = getHeight();
	  double y2 = mouseXY.y;
	  double x12 = x2 - x1;
	  double y12 = y2 - y1;
	  double m = y12/x12;
	  int angle = (int) Math.toDegrees(Math.atan(Math.abs(m)));
	  if(mouseXY.x < getWidth()/2)
		  angle = 180 - angle;
	  double a = Math.toRadians(angle);
	  return a;
  }
  
  public int getAngle() {
	  return (int) Math.toDegrees(realTimeAngle);
  }

  private void addPuntoAngulo() {
	  double auxY = 100 * Math.sin(launchAngle) * t;
	  double auxX = 100 * Math.cos(launchAngle) * tx;
	  auxX = auxX + trayectoria.get(0).x;
	  auxY = -auxY + trayectoria.get(0).y;
	  if (auxY < radio/2) {
		  launch = false;
		  Random aleatorio = new Random(System.currentTimeMillis());
		  launchColor = colors.get(aleatorio.nextInt(colors.size()));
		  trayectoria = new ArrayList<Point>();
		  trayectoria.add(new Point(getWidth()/2, getHeight()));
		  t=0;
	  }else if (auxX > getWidth() - radio/2 || auxX < radio/2) {
		  chocado = !chocado;
		  if(chocado)
			  tx -= 0.1;
		  else 
			  tx +=0.1;
		  auxY = 100 * Math.sin(launchAngle) * t;
		  auxX = 100 * Math.cos(launchAngle) * tx;
		  auxX = auxX + trayectoria.get(0).x;
		  auxY = -auxY + trayectoria.get(0).y;
		  trayectoria.add(new Point((int)auxX, (int)auxY));
		  t += 0.1;
	  }else {
		  trayectoria.add(new Point((int)auxX, (int)auxY));
		  t += 0.1;
		  if(chocado)
			  tx -= 0.1;
		  else 
			  tx +=0.1;
	  }
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D)g;
    BasicStroke s = new BasicStroke(20.0F,BasicStroke.CAP_SQUARE, 
            BasicStroke.JOIN_BEVEL);
    g2d.setStroke(s);	    
    g.setColor(Color.BLACK);
    g2d.draw(new Line2D.Double(getWidth()/2, getHeight()+10, (getWidth()/2) + (Math.cos(realTimeAngle) * 35), getHeight() - (Math.sin(realTimeAngle) * 35)));

  	trayectoria.set(0, new Point(getWidth()/2, getHeight()));
    pos = (getWidth()-10)/10;
  	if(launch)
  		addPuntoAngulo();
  	
    ComprobarChoques();
    for(int j = 0; j < balls.size(); j++) {
    	if(j%2==0) {
		    for(int i = 0; i < balls.get(j).size(); i++) {
		        g.setColor(colours.get(j).get(i));
		        g.fillOval((balls.get(j).get(i)*pos)+5, 5+(j * radio), radio, radio);
		    }
    	}else {
    	    for(int i = 0; i < balls.get(j).size(); i++) {
    	        g.setColor(colours.get(j).get(i));
    	        g.fillOval((balls.get(j).get(i)*pos)+5+radio/2, 5+(j * radio), radio, radio);
    	    }
    		
    	}
    }

    g.setColor(launchColor);
    g.fillOval(trayectoria.get(trayectoria.size()-1).x-radio/2, trayectoria.get(trayectoria.size()-1).y-radio/2, radio, radio);
  }

}

