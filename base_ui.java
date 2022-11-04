import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Class that contains all the basic drawing elements.
class WorkBench extends JPanel
        implements MouseWheelListener, ActionListener {

    JButton zoomIN;
    JButton zoomOUT;
    
    // feature vector format : {beak(size),beak(tooth),ear(shape),body(spots),body(hair),limbs(spot/hair/size),tail(shapes)}
    static int parent1[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // feature vector for parent 1
    static int parent2[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1 }; // feature vector for parent 2

    static int child[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    static int var[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    public static int originX = 0;
    public static int originY = 0;
    public static int delta = 8;
    public static int height = 0;
    public static int width = 0;

    Color background = new Color(32, 33, 36);
    Color foreground = new Color(61, 64, 67);
    Color dark_lines = new Color(14, 15, 16);

    //JToggleButton p1_1 = new JToggleButton("P1: Round Ears");


    JButton b1 = new JButton("Parent1 Triangular Tail");
    JButton b2 = new JButton("Parent1 Circular Tail");
    JButton b3 = new JButton("Parent1 Triangular Ears");
    JButton b4 = new JButton("Parent1 Round Ears");

    Insets ins;

    WorkBench() {
        addMouseWheelListener(this);
        zoomIN = new JButton("Zoom In");
        zoomOUT = new JButton("Zoom Out");
        zoomIN.setBounds(0, 0, 20, 20);
        zoomOUT.setBounds(0, 0, 20, 20);
        add(zoomIN);
        add(zoomOUT);
        zoomIN.addActionListener(this);
        zoomIN.setActionCommand("Zoomed In");
        zoomOUT.addActionListener(this);
        zoomOUT.setActionCommand("Zoomed Out");

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent1[8] = 1;
                childset(parent1, parent2);
                repaint();
            }
        });
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent1[8] = 0;
                childset(parent1, parent2);
                repaint();
            }
        });
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent1[2] = 1;
                childset(parent1, parent2);
                repaint();
            }
        });
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent1[2] = 0;
                childset(parent1, parent2);
                repaint();
            }
        });

        /*p1_1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if(p1_1.isSelected())
                {
                    parent1[2] = 0;
                    //jlab.setText("Parent 1[0] has state 0");
                    repaint();
                }
                else
                {
                    parent1[2] = 1;
                    repaint();
                }
            }
        });*/

        b1.setBounds(30, 60, 100, 20);
        b2.setBounds(140, 60, 100, 20);
        b3.setBounds(30, 90, 100, 20);
        b4.setBounds(140, 90, 100, 20);

        add(b2);
        add(b1);
        add(b3);
        add(b4);
        //add(p1_1);
    }

    public void childset(int arr[], int arr2[]) {
        for (int i = 0; i < 9; i++) {
            var[i] = ((int) (Math.random() * 100 + i)) % 2;
        }
        for (int i = 0; i < 9; i++) {
            child[i] = var[i] * arr[i] + (1 - var[i]) * arr2[i];
        }
    }

    // Just plots the grid and labelling for the grid.
    public void makeGrid(Graphics G) {
        int yCoord = 0;

        G.setFont(new Font("monospace", Font.BOLD, delta / 6));

        // Lines towards the right half
        for (int i = originX; i < originX * 2 + width - ins.right; i += delta) {
            G.setColor(background);
            G.drawLine(i, originY * 2 - height, i, originY * 2 + height);
            if (delta > 30 && i != originX) {
                G.drawString(String.valueOf(yCoord), i - delta / 6, originY + delta / 6);

            }
            yCoord++;
        }
        yCoord = 0;

        // Lines towards the left half
        for (int i = originX; i > originX * 2 - width - ins.left; i -= delta) {
            G.setColor(background);
            G.drawLine(i, originY * 2 - height, i, originY * 2 + height);
            if (delta > 30 && i != originX) {
                G.drawString(String.valueOf(yCoord), i + delta / 12, originY + delta / 6);

            }
            yCoord--;
        }

        // Lines towards the bottom half
        int xCoord = 0;
        for (int i = originY; i < originY * 2 + height - ins.top; i += delta) {
            G.setColor(background);
            G.drawLine(originX * 2 - width, i, originX * 2 + width, i);
            if (delta > 30 && i != originY) {
                G.drawString(String.valueOf(-1 * xCoord), originX + delta / 12, i + delta / 6);
            }
            xCoord++;

        }

        // Lines towards the top half
        xCoord = 0;
        for (int i = originY; i > originY * 2 - height - ins.bottom; i -= delta) {
            G.setColor(background);
            G.drawLine(originX * 2 - width, i, originX * 2 + width, i);
            if (delta > 30 && i != originY) {
                G.drawString(String.valueOf(-1 * xCoord), originX + delta / 6, i + delta / 6);
            }
            xCoord--;
        }

        G.setColor(background);
        if (delta > 30)
            G.drawString("(0,0)", originX + delta / 2, originY + delta / 2);
    }

    protected void paintComponent(Graphics G) {
        super.paintComponent(G);
        StdDrawing std = new StdDrawing();
        Animal anim    = new Animal();
        height = getHeight();
        width = getWidth();

        ins = getInsets();

        originX = (width - ins.left - ins.right) / 2;
        originY = (height - ins.top - ins.bottom) / 2;

        setBackground(foreground);

        if (delta > 5)
            makeGrid(G);

        std.plotOrigin(G);

        G.setColor(Color.white);
        anim.plotanimal(-100,parent1, G);
        anim.plotanimal(100, parent2, G);
        anim.plotanimal(0, child, G);

    }

    // driving the zooming using Mouse Wheel movement
    public void mouseWheelMoved(MouseWheelEvent e) {
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

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand() == "Zoomed In") {
            delta = delta + 5;
            delta = delta > 150 ? 150 : delta;
        }

        if (ae.getActionCommand() == "Zoomed Out") {
            delta = delta - 5;
            delta = delta < 1 ? 1 : delta;
        }
        repaint();
    }
}

// Class where the UI is organized and managed.
class Base_UI {

    JLabel jlab;
    WorkBench canvas;

    Base_UI() {
        JFrame jfrm = new JFrame("Paint Demo");

        jfrm.setSize(200, 150);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new WorkBench();

        jfrm.add(canvas);
        jfrm.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Base_UI();
            }
        });
    }
}

class Animal extends WorkBench{

    StdDrawing std = new StdDrawing();
    public void plotanimal(int xr, int feat[], Graphics g) {
        // Imaginary animal 1

        // ears
        if (feat[2] == 0) {
            std.plotCircle(5, 10 + xr, 60, g);
            std.plotCircle(5, 12 + xr, 58, g);
        }
        if (feat[2] == 1) {
            std.plotTriangle1( 10, -10, 4 + xr, 60, 50, g);
            std.plotTriangle1( 10, -10, 10 + xr, 54, 0, g);
        }

        // head
        std.plotCircle(10, 0 + xr, 50, g);
        std.plotCircle(2, -3 + xr, 50, g);

        // body
        std.plotEllipse(12, 25, 20 + xr, 25, -30, g);
        if (feat[4] == 1) {
            plothair(12, 25, 20 + xr, 25, -30, g);
        }
        // spots(20,25,12,25,-30,g);
        // Hardcoded spots
        if (feat[3] == 1) {
            std.plotCircle(2, 20 + xr, 25, g);
            std.plotCircle(2, 12 + xr, 38, g);
            std.plotCircle(1, 24 + xr, 30, g);
            std.plotCircle(1, 10 + xr, 30, g);
            std.plotCircle(1, 28 + xr, 21, g);
            std.plotCircle(1, 12 + xr, 21, g);
            std.plotCircle(2, 28 + xr, 15, g);
            std.plotCircle(2, 15 + xr, 15, g);
        }
        
        // beaks
        if (feat[0] == 0) {
            std.plotTriangle2( -30, -4, -8 + xr, 46, 0, g);
            std.plotTriangle2( -30, 4, -8 + xr, 54, 0, g);
            // teeth
            if (feat[1] == 1) {
                for (int i = 4; i < 13; i++) {
                    if (i % 2 == 0) {
                        std.plotLine(-8 - i + xr, 52, -8 - i + xr, 51, g);
                    }
                    if (i % 2 == 1) {
                        std.plotLine(-8 - i + xr, 48, -8 - i + xr, 49, g);
                    }
                }
            }
        }
        if (feat[0] == 1) {
            std.plotTriangle2( -16, -4, -8 + xr, 46, 0, g);
            std.plotTriangle2( -16, 4, -8 + xr, 54, 0, g);
            // teeth
            if (feat[1] == 1) {
                for (int i = 4; i < 9; i++) {
                    if (i % 2 == 0) {
                        std.plotLine(-8 - i + xr, 52, -8 - i + xr, 51, g);
                    }
                    if (i % 2 == 1) {
                        std.plotLine(-8 - i + xr, 48, -8 - i + xr, 49, g);
                    }
                }
            }

        }

        // hand 1
        if (feat[7] == 0) {
            std.plotEllipse( 5, 8, 0 + xr, 20, -120,g);
            std.plotEllipse( 3, 5, -12 + xr, 16, -90, g);
            std.plotCircle(4, -22 + xr, 16, g);
            std.plotEllipse( 1, 3, -21 + xr, 22, 0, g);
            std.plotEllipse( 1, 3, -26 + xr, 24, -30, g);
            std.plotEllipse( 1, 3, -30 + xr, 21, -45, g);
            std.plotEllipse( 1, 3, -28 + xr, 15, 90, g);
            std.plotEllipse( 1, 2, -25 + xr, 12, -135, g);
            // hand 2
            int r = 10, z = 0;
            std.plotEllipse( 4, 6, -3 + xr, 32, -120, g);
            std.plotEllipse( 3, 5, -14 + xr, 30, -60, g);
            std.plotCircle(5, -31 + r + xr, 36 - z, g);
            std.plotEllipse( 1, 3, -27 + r + xr, 42 + z, 0, g);
            std.plotEllipse( 1, 3, -32 + r + xr, 44 + z, -30, g);
            std.plotEllipse( 1, 3, -37 + r + xr, 42 + z, -45, g);
            std.plotEllipse( 1, 3, -38 + r + xr, 36 + z, 90, g);
            std.plotEllipse( 1, 2, -34 + r + xr, 31 + z, -135, g);
        }
        if (feat[7] == 1) {
            // hand1
            std.plotEllipse( 5, 12, 0 + xr, 20, -120, g);
            std.plotEllipse( 3, 8, -18 + xr, 16, -90, g);
            std.plotCircle(5, -31 + xr, 18, g);
            std.plotEllipse( 1, 3, -27 + xr, 24, 0, g);
            std.plotEllipse( 1, 3, -32 + xr, 26, -30, g);
            std.plotEllipse( 1, 3, -37 + xr, 24, -45, g);
            std.plotEllipse( 1, 3, -38 + xr, 18, 90, g);
            std.plotEllipse( 1, 2, -34 + xr, 13, -135, g);
            // hand 2
            std.plotEllipse( 5, 10, -5 + xr, 32, -120, g);
            std.plotEllipse( 3, 8, -20 + xr, 30, -60, g);
            std.plotCircle(5, -31 + xr, 36, g);
            std.plotEllipse( 1, 3, -27 + xr, 42, 0, g);
            std.plotEllipse( 1, 3, -32 + xr, 44, -30, g);
            std.plotEllipse( 1, 3, -37 + xr, 42, -45, g);
            std.plotEllipse( 1, 3, -38 + xr, 36, 90, g);
            std.plotEllipse( 1, 2, -34 + xr, 31, -135, g);
        }

        // leg 1
        std.plotEllipse( 6, 20, 33 + xr, -14, -10, g);
        std.plotEllipse( 4, 15, 33 + xr, -45, 15, g);
        std.plotEllipse( 3, 6, 7 + xr, -53, 50, g);
        std.plotEllipse( 1, 2, 0 + xr, -55, 50, g);
        std.plotEllipse( 1, 2, 2 + xr, -57, 50, g);
        std.plotEllipse( 1, 2, 3 + xr, -58, 50, g);
        // leg 2
        std.plotEllipse( 6, 18, 15 + xr, -9, 30, g);
        std.plotEllipse( 4, 17, 9 + xr, -36, -10, g);
        std.plotEllipse( 3, 6, 26 + xr, -61, 70, g);
        std.plotEllipse( 1, 2, 25 + xr, -65, 70, g);
        std.plotEllipse( 1, 2, 20 + xr, -63, 70, g);
        std.plotEllipse( 1, 2, 22 + xr, -64, 70, g);
        // leg spots
        if (feat[5] == 1) {
            std.plotEllipse( 2, 2, 18 + xr, -2, 0, g);
            std.plotEllipse( 2, 2, 12 + xr, -12, 0, g);
            std.plotEllipse( 2, 2, 8 + xr, -30, 0, g);
            std.plotEllipse( 2, 2, 9 + xr, -40, 0, g);
            std.plotEllipse( 2, 2, 30 + xr, -5, 0, g);
            std.plotEllipse( 2, 2, 34 + xr, -20, 0, g);
            std.plotEllipse( 2, 2, 34 + xr, -40, 0, g);
            std.plotEllipse( 2, 2, 31 + xr, -52, 0, g);
        }

        // body hair
        if (feat[6] == 1) {
            plothair(5, 12, 0 + xr, 20, -120, g);
            plothair(3, 8, -18 + xr, 16, -90, g);
            plothair(5, 10, -5 + xr, 32, -120, g);
            plothair(3, 8, -20 + xr, 30, -60, g);
            plothair(6, 20, 33 + xr, -14, -10, g);
            plothair(4, 15, 33 + xr, -45, 15, g);
            plothair(6, 18, 15 + xr, -9, 30, g);
            plothair(4, 17, 9 + xr, -36, -10, g);
        }

        // tail
        if (feat[8] == 0) {
            std.plotEllipse(5, 10, 45 + xr, 13, 50, g);
            if (feat[6] == 1) {
                plothair(5, 10, 45 + xr, 13, 50, g);
            }
        }
        if (feat[8] == 1) {
            std.plotTriangle2(40, 5, 35 + xr, 13, 45, g);
        }
    }

    public void plothair(int rx, int ry,int xc ,int yc,int angle,Graphics g){
		double dx, dy, d1, d2, x, y;
		int p1,p2,p3,p4,p5,p6,p7,p8;
		double rot = 3.14*(angle%360)/180;
		x = 0;
		y = ry;
		// Initial decision parameter of region 1
		d1 = (ry * ry) - (rx * rx * ry) + (0.25 * rx * rx);

		dx = 2 * ry * ry * x;
		dy = 2 * rx * rx * y;
		// For region 1
		while (dx < dy)
		{
     
			// Print points based on 4-way symmetry
			p1=(int)((x*Math.cos(-rot)-y*Math.sin(-rot))+xc);
			p2=(int)((x*Math.sin(-rot)+y*Math.cos(-rot))+yc);
			p3=(int)(-(x*Math.cos(rot)-y*Math.sin(rot))+xc);
			p4=(int)((x*Math.sin(rot)+y*Math.cos(rot))+yc);
			p5=(int)((x*Math.cos(rot)-y*Math.sin(rot))+xc);
			p6=(int)(-(x*Math.sin(rot)+y*Math.cos(rot))+yc);
			p7=(int)(-(x*Math.cos(-rot)-y*Math.sin(-rot))+xc);
			p8=(int)(-(x*Math.sin(-rot)+y*Math.cos(-rot))+yc);
 
			std.plotLine(p1,p2,(int)(Math.random()*5)+p1-2,(int)(Math.random()*5)+p2-2,g);
			std.plotLine(p3,p4,(int)(Math.random()*5)+p3-2,(int)(Math.random()*5)+p4-2,g);
			std.plotLine(p5,p6,(int)(Math.random()*5)+p5-2,(int)(Math.random()*5)+p6-2,g);
			std.plotLine(p7,p8,(int)(Math.random()*5)+p7-2,(int)(Math.random()*5)+p8-2,g);
			
			// Checking and updating value of
			// decision parameter based on algorithm
			if (d1 < 0)
			{
				x++;
				dx = dx + (2 * ry * ry);
				d1 = d1 + dx + (ry * ry);
			}
			else
			{
				x++;
				y--;
				dx = dx + (2 * ry * ry);
				dy = dy - (2 * rx * rx);
				d1 = d1 + dx - dy + (ry * ry);
			}
		}
 
		// Decision parameter of region 2
		d2 = ((ry * ry) * ((x + 0.5) * (x + 0.5))) + ((rx * rx) * ((y - 1) * (y - 1))) - (rx * rx * ry * ry);
		// Plotting points of region 2
		while (y >= 0) {
 
			// printing points based on 4-way symmetry
			p1=(int)((x*Math.cos(-rot)-y*Math.sin(-rot))+xc);
			p2=(int)((x*Math.sin(-rot)+y*Math.cos(-rot))+yc);
			p3=(int)(-(x*Math.cos(rot)-y*Math.sin(rot))+xc);
			p4=(int)((x*Math.sin(rot)+y*Math.cos(rot))+yc);
			p5=(int)((x*Math.cos(rot)-y*Math.sin(rot))+xc);
			p6=(int)(-(x*Math.sin(rot)+y*Math.cos(rot))+yc);
			p7=(int)(-(x*Math.cos(-rot)-y*Math.sin(-rot))+xc);
			p8=(int)(-(x*Math.sin(-rot)+y*Math.cos(-rot))+yc);
 
			std.plotLine(p1,p2,(int)(Math.random()*5)+p1-2,(int)(Math.random()*5)+p2-2,g);
			std.plotLine(p3,p4,(int)(Math.random()*5)+p3-2,(int)(Math.random()*5)+p4-2,g);
			std.plotLine(p5,p6,(int)(Math.random()*5)+p5-2,(int)(Math.random()*5)+p6-2,g);
			std.plotLine(p7,p8,(int)(Math.random()*5)+p7-2,(int)(Math.random()*5)+p8-2,g);
			// Checking and updating parameter
			// value based on algorithm
			if (d2 > 0) {
				y--;
				dy = dy - (2 * rx * rx);
				d2 = d2 + (rx * rx) - dy;
			}
			else {
				y--;
				x++;
				dx = dx + (2 * ry * ry);
				dy = dy - (2 * rx * rx);
				d2 = d2 + dx - dy + (rx * rx);
			}
		}
	}
}