import java.awt.*;
import javax.swing.*;

public class DisparosApp extends JApplet {
  public DisparosApp() {
    add(new DisparosControl());
  }

  public static void main(String[] args) {
    DisparosApp applet = new DisparosApp();
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("BounceBallApp");
    frame.add(applet, BorderLayout.CENTER);
    frame.setSize(550, 350);
    frame.setVisible(true);
  }
}


/**
    * The BounceBallApp class simply places an instance of BallControl in the applet 
		* The main method is provided in the applet so that you can also run it standalone
*/

/* vim: set nu:ts=2:sw=2: */
