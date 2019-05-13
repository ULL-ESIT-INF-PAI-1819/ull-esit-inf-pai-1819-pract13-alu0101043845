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

  private ArrayList<Integer> balls;
  private ArrayList<Color> colours;
  private Color launchColor;
  private Point mouseXY;
  private boolean launch;
  private ArrayList<Point> trayectoria;
  private int radio = 50;
  private double t;
  private double launchAngle;
  private double realTimeAngle;
  private int pos;
  private AudioClip currentAudio;
  private AudioClip[] audioClips = new AudioClip[2];

  public Disparos() {
	launch = false;
  	trayectoria = new ArrayList<Point>();
  	mouseXY = new Point();
  	pos = -1000;
  	t = 0;
  	realTimeAngle = Math.toRadians(90);
    balls = new ArrayList<Integer>();
    colours = new  ArrayList<Color>();
    audioClips[0] = Applet.newAudioClip(getClass().getResource("/sounds/La.wav"));
    audioClips[1] = Applet.newAudioClip(getClass().getResource("/sounds/Fa.wav"));
    ArrayList<Color> colors = new  ArrayList<Color>();
	colors.add(Color.MAGENTA);
	colors.add(Color.BLUE);
	colors.add(Color.CYAN);
	colors.add(Color.PINK);
	colors.add(Color.GREEN);
	colors.add(Color.YELLOW);
	colors.add(Color.RED);
	colors.add(Color.ORANGE);
	Random aleatorio = new Random(System.currentTimeMillis());
    for(int i = 0; i < 10; i++) {
    	balls.add(i);
    	colours.add(colors.get(aleatorio.nextInt(colors.size())));
    }
    launchColor = colours.get(aleatorio.nextInt(colours.size()));
  	trayectoria.add(new Point(0, 0));
  }


  public void Lanzar(Point p) {
	if(!launch) {
		mouseXY = p;
		t = 0;
	  	trayectoria = new ArrayList<Point>();
	  	trayectoria.add(new Point(getWidth()/2, getHeight()));
	  	launchAngle = calcularFuncion();
		launch = true;
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
	  int index = -1;
	  for(int i = 0; i < balls.size(); i++) {
		  Point aux = new Point((balls.get(i)*pos)+5+radio/2, 5+radio/2);
		  if(Choca(aux)) {
			  c++;
			  index = i;
		  }
	  }
	  if(c > 0) {
		  t = 0;
		  Random aleatorio = new Random(System.currentTimeMillis());
		  launchColor = colours.get(aleatorio.nextInt(colours.size()));
		  trayectoria = new ArrayList<Point>();
		  trayectoria.add(new Point(getWidth()/2, getHeight()));
		  launch = false;
		  if(c == 1) {
			  balls.remove(index);
			  colours.remove(index);
			  currentAudio = audioClips[1];
			  currentAudio.play();
		  }else {
			  currentAudio = audioClips[0];
			  currentAudio.play();
		  }
	  }
		  
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
	  double auxX = 100 * Math.cos(launchAngle) * t;
	  auxX = auxX + trayectoria.get(0).x;
	  auxY = -auxY + trayectoria.get(0).y;
	  if (auxX < radio/2||auxX > getWidth() - radio/2||auxY < radio/2) {
		  launch = false;
		  Random aleatorio = new Random(System.currentTimeMillis());
		  launchColor = colours.get(aleatorio.nextInt(colours.size()));
		  trayectoria = new ArrayList<Point>();
		  trayectoria.add(new Point(getWidth()/2, getHeight()));
		  t=0;
	  }else {
		  trayectoria.add(new Point((int)auxX, (int)auxY));
		  t += 0.1;
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
    for(int i = 0; i < balls.size(); i++) {
        g.setColor(colours.get(i));
        g.fillOval((balls.get(i)*pos)+5, 5, radio, radio);
    }

    g.setColor(launchColor);
    g.fillOval(trayectoria.get(trayectoria.size()-1).x-radio/2, trayectoria.get(trayectoria.size()-1).y-radio/2, radio, radio);
  }

}

