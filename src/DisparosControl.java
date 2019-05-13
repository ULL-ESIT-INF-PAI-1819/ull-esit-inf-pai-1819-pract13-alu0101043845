import javax.swing.*;


import java.awt.event.*;
import java.awt.*;

public class DisparosControl extends JPanel {
	  private int delay = 20;

	  private Timer timer = new Timer(delay, new TimerListener());
  private Disparos ball = new Disparos();
  private final JButton btnI = new JButton("i");

  public DisparosControl() {
    // Group buttons in a panel
    JPanel panel = new JPanel();
    panel.add(btnI);

    // Add ball and buttons to the panel
    ball.setBorder(new javax.swing.border.LineBorder(Color.BLACK));
    ball.setBackground(Color.WHITE);
    setLayout(new BorderLayout());
    add(ball, BorderLayout.CENTER);
    add(panel, BorderLayout.SOUTH);


    btnI.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog(); 

                JLabel l = new JLabel("Programacion de Apricaciones Interactivas");
                JLabel l1 = new JLabel("Practica 13: Un juego de disparo de bolas");
                JLabel l2 = new JLabel("Juego de bolas:");
                JLabel l3 = new JLabel(" -se lanza una bola al clickar con el raton");
                JLabel l4 = new JLabel(" -la bola se lanza siguiendo la direccion del raton");
                JLabel l5 = new JLabel("Daniel Dominguez Gutierrez");
                d.setLayout(new BorderLayout());
                d.add(l, BorderLayout.NORTH); 
                JPanel p = new JPanel();
                JPanel p2 = new JPanel();
                p.setLayout(new BorderLayout());
                p2.setLayout(new GridLayout(3, 1));
                p.add(l1, BorderLayout.NORTH); 
                p2.add(l2);
                p2.add(l3);
                p2.add(l4);
                p.add(p2, BorderLayout.CENTER); 
                p.add(l5, BorderLayout.SOUTH); 
                d.add(p, BorderLayout.CENTER); 
                d.setTitle("informacion");
                d.setSize(300, 150); 
      
                d.setVisible(true); 
    	}
    });
    

    ball.addMouseListener(new MouseAdapter() {
    	@Override
		public void mousePressed(MouseEvent e) {
    		ball.Lanzar(e.getPoint());
    	}
    	
    });

    ball.addMouseMotionListener(new MouseAdapter() {
    	@Override
    	public void mouseMoved(MouseEvent e) {
    		ball.ActualizarCanon(e.getPoint());
    	}
    	
    });
    timer.setDelay(delay);
    timer.start();
  }
  
  private class TimerListener implements ActionListener {
    /** Handle the action event */
    public void actionPerformed(ActionEvent e) {
      repaint();
    }
  }
}


