import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;

class WorkBench extends JPanel
implements MouseWheelListener {

    JButton zoomIN;
    JButton zoomOUT;

    ImageIcon in = new ImageIcon("images/zoomin.png","Zoom In");
    ImageIcon out = new ImageIcon("images/zoomout.png","Zoom Out");

    public int originX = 0;
    public int originY = 0;
    public int delta   = 8;
    public int height  = 0;
    public int width   = 0;
    
    Color background = new Color(32,33,36);
    Color foreground = new Color(61,64,67);
    Color dark_lines = new Color(14,15,16);
 //  LIGHT+GRAY = "#565656", DARK+GRAY = "#2a2b2b" 

    Insets ins;
    
    WorkBench()
    {
       addMouseWheelListener(this);
       zoomIN = new JButton(in);
       zoomOUT = new JButton(out);
       zoomIN.setBounds(50,100,95,75); 
       zoomOUT.setBounds(150,150,95,30);   

       add(zoomIN);
       add(zoomOUT);
    }

    public void plotpoint(int x, int y, Graphics G)
    {
        G.fillRect(originX + x*delta - delta/8, originY - y*delta - delta/8, delta/4, delta/4);
        //G.fillOval(originX+x*delta-delta/8,originY-y*delta-delta/8,delta/4,delta/4);
    }

    public void var_plotpoint(int x, int y, int px, Graphics G)
    {
        G.fillRect(originX + x*delta - delta/8, originY - y*delta - delta/8, px*delta/4, px*delta/4);
        //G.fillOval(originX+x*delta-delta/8,originY-y*delta-delta/8,delta/4,delta/4);
    }

    public void plotOrigin(Graphics G)
    {
        G.setColor(background);
        G.fillOval(originX-delta/2, originY-delta/2, delta, delta);
    }

    public void makeGrid(Graphics G)
    {
        int yCoord=0;

        G.setFont(new Font("monospace",Font.BOLD, delta/6));

        //Lines towards the right half
        for(int i=originX;i<originX*2+width - ins.right;i+=delta)
        {
            G.setColor(background);
            G.drawLine(i,originY*2-height,i,originY*2+height);
            if(delta>30 && i!=originX)
            {
                G.drawString(String.valueOf(yCoord), i-delta/6, originY+delta/6);
                
            }
            yCoord++;
        }
        yCoord=0;

        // Lines towards the left half
        for(int i=originX;i>originX*2-width - ins.left;i-=delta)
        {
            G.setColor(background);
            G.drawLine(i,originY*2-height,i,originY*2+height);
            if(delta>30 && i!=originX)
            {
                G.drawString(String.valueOf(yCoord), i+delta/12, originY+delta/6);
                
            }
            yCoord--;
        }
        
        // Lines towards the bottom half
        int xCoord=0;
        for(int i=originY;i<originY*2+height-ins.top;i+=delta)
        {
            G.setColor(background);
            G.drawLine(originX*2 - width,i,originX*2+width,i);
            if(delta>30 && i!=originY)
            {
                G.drawString(String.valueOf(-1 * xCoord), originX+delta/12, i + delta/6);
            }
            xCoord++;
            
        }

        // Lines towards the top half
        xCoord=0;
        for(int i=originY;i>originY*2-height-ins.bottom;i-=delta)
        {
            G.setColor(background);
            G.drawLine(originX*2 - width,i,originX*2+width,i);
            if(delta>30 && i!=originY)
            {
                G.drawString(String.valueOf(-1 * xCoord), originX+delta/6, i + delta/6);
            }
            xCoord--;
        }
    
        G.setColor(dark_lines);
        
        //G.setColor(Color.BLUE);
        //G.fillOval(originX-delta/4,originY-delta/4,delta/2,delta/2);
        G.setColor(background);
        if(delta!=10)
            G.drawString("(0,0)",originX+delta/4,originY+delta/4);
    }
    

    protected void paintComponent(Graphics G) 
    {
        super.paintComponent(G);
        //int x, y, x2, y2;

        height = getHeight();
        width  = getWidth();

        ins = getInsets();

        originX = (width - ins.left - ins.right)/2;
        originY = (height - ins.top - ins.bottom)/2;
        
        setBackground(foreground);
        makeGrid(G);

        G.setColor(Color.CYAN);
        plotpoint(0,0, G);

        plotOrigin(G);

        G.setColor(Color.green);
        plotpoint(originX+1, originY+1, G);
    }

    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        int notches = e.getWheelRotation();
        if (notches < 0) {
        delta = delta + 10;
        delta = delta > 150 ? 150 : delta;
        } else {
        delta = delta - 10;
        delta = delta < 5 ? 5 : delta;
        }
        repaint();
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