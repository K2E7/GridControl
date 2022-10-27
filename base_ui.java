import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class WorkBench extends JPanel {

    public int originX = 0;
    public int originY = 0;
 //  public int delta   = 50;
    public int width   = 0;
    public int height  = 0;
 //  LIGHT+GRAY = "#565656", DARK+GRAY = "#2a2b2b" 

    Insets ins;
    
    WorkBench()
    {
       
    }

    protected void paintComponent(Graphics G) 
    {
        super.paintComponent(G);
        int x, y, x2, y2;

        int height = getHeight();
        int width  = getWidth();

        ins = getInsets();

        originX = (width - ins.left - ins.right)/2;
        originY = (height - ins.top - ins.bottom)/2;

        Color background = new Color(86,86,86);
        Color foreground = new Color(42,43,43);
        
        setBackground(background);
        G.setColor(foreground);
        G.drawOval(originX, originY, 10, 10);
    }
}

class Base_UI {

    JLabel    jlab;
    WorkBench canvas;

    Base_UI()
    {
        JFrame jfrm = new JFrame("Paint Demo");

        jfrm.setSize(200,150);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new WorkBench();

        jfrm.add(canvas);
        jfrm.setVisible(true);
    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                new Base_UI();
            }
        });
    }
}