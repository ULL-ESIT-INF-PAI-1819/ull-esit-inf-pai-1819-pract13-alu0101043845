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
    frame.setSize(550, 650);
    frame.setVisible(true);
  }
}

