import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class WorkBench extends JPanel {

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
        G.setColor(foreground);
        G.fillOval(originX, originY, 10, 10);
    }

    public void makeGrid(Graphics G)
    {
        int yCoord=0;
        for(int i=originX;i<originX*2+width;i+=delta)
        {
            G.setColor(foreground);
            G.drawLine(i,originY*2-height,i,originY*2+height);
            if(delta>30 && i!=originX)
            {
                G.drawString(String.valueOf(-1 * yCoord), i - 15, originY+15);
                
            }
            yCoord++;
        }
        yCoord=0;
        for(int i=originX;i>originX*2-width;i-=delta)
        {
            G.setColor(foreground);
            G.drawLine(i,originY*2-height,i,originY*2+height);
            if(delta>30 && i!=originX)
            {
                G.drawString(String.valueOf(-1 * yCoord), i - 15, originY+15);
                
            }
            yCoord--;
        }
    
        int xCoord=0;
        for(int i=originY;i<originY*2+height;i+=delta)
        {
            G.setColor(foreground);
            G.drawLine(originX*2 - width,i,originX*2+width,i);
            if(delta>30 && i!=originY)
            {
                G.drawString(String.valueOf(-1 * xCoord), originX+10, i + 15);
            }
            xCoord++;
            
        }
        xCoord=0;
        for(int i=originY;i>originY*2-height;i-=delta)
        {
            G.setColor(foreground);
            G.drawLine(originX*2 - width,i,originX*2+width,i);
            if(delta>30 && i!=originY)
            {
                G.drawString(String.valueOf(-1 * xCoord), originX+10, i + 15);
            }
            xCoord--;
        }
    
        G.setColor(dark_lines);
        G.fillRect(originX,originY, 3, height/2);
        G.drawLine(originX+2,originY*2-getHeight(),originX+2,originY*2+getHeight());
        G.drawLine(originX-1,originY*2-getHeight(),originX-1,originY*2+getHeight());
        G.drawLine(originX,originY*2-getHeight(),originX,originY*2+getHeight());
        G.drawLine(originX*2-getWidth(),originY,originX*2+getWidth(),originY);
        //G.setColor(Color.BLUE);
        //G.fillOval(originX-delta/4,originY-delta/4,delta/2,delta/2);
        G.setColor(foreground);
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
        
        setBackground(background);
        makeGrid(G);
        plotOrigin(G);
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