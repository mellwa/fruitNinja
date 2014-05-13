/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * View of the main play area.
 * Displays pieces of fruit, and allows players to slice them.
 */
public class View extends JPanel implements ModelListener, ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Model model;
    private final MouseDrag drag;
    private int x,y,height;
    private long lastTime;
    private BufferedImage apple;
    private BufferedImage frag_apple;
    private BufferedImage peach;
    private BufferedImage frag_peach;
    private BufferedImage melon;
    private BufferedImage frag_melon;
    private BufferedImage green_apple;
    private BufferedImage frag_green_apple;
    private BufferedImage orange;
    private BufferedImage frag_orange;
    private BufferedImage back_ground;
    
    private GameOverModel game_over_model;
	private JButton button, button2;
	private ButtonGroup bg;
	private JRadioButton radio_button1, radio_button2, radio_button3;
	private Main main;
	private boolean gameover;
    // Constructor
    View (Model m,GameOverModel game_over_model, Main main) throws NoninvertibleTransformException, IOException {
        model = m;
        this.main = main;
        this.game_over_model = game_over_model;
        model.addObserver(this);
        setBackground(Color.black);
        gameover = false;
        
        Class<? extends View> c = this.getClass();
        apple = null;
        frag_apple = null;
        URL url;
        //reading all needed images into bufferedimages
        try{
        	url = c.getResource("redapple.jpg");
        	apple = ImageIO.read(url);
        	url = c.getResource("fragApple.jpg");
        	frag_apple = ImageIO.read(url);
        	
        	url = c.getResource("peach.jpg");
        	peach = ImageIO.read(url);
        	url = c.getResource("fragPeach.jpg");
        	frag_peach = ImageIO.read(url);
        	
        	url = c.getResource("watermelon.jpg");
        	melon = ImageIO.read(url);
        	url = c.getResource("fragWatermelon.jpg");
        	frag_melon = ImageIO.read(url);
        	
        	url = c.getResource("greenapple.jpg");
        	green_apple = ImageIO.read(url);
        	url = c.getResource("fragGreenapple.jpg");
        	frag_green_apple = ImageIO.read(url);
        	
        	url = c.getResource("orange.jpg");
        	orange = ImageIO.read(url);
        	url = c.getResource("fragOrange.jpg");
        	frag_orange = ImageIO.read(url);
        	
        	url = c.getResource("background.jpg");
        	back_ground = ImageIO.read(url);
        }catch(IOException e){
        	System.err.println("image loading failed");
        	System.exit(1);
        }
        model.addImage(apple);
        model.addImage(frag_apple);
        model.addImage(peach);
        model.addImage(frag_peach);
        model.addImage(melon);
        model.addImage(frag_melon);
        model.addImage(green_apple);
        model.addImage(frag_green_apple);
        model.addImage(orange);
        model.addImage(frag_orange);
 
        

        // drag represents the last drag performed, which we will need to calculate the angle of the slice
        drag = new MouseDrag();
        // add mouse listener
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        lastTime = System.currentTimeMillis();
        this.height = 457;
        
        
		
		button = new JButton("Restart");
		button.setMaximumSize(new Dimension(100, 50));
		button.setPreferredSize(new Dimension(100, 50));
		// a GridBagLayout with default constraints centres
		// the widget in the window
		this.setLayout(null);
		button.setBounds(260, 420, 100, 50);
		
		button2 = new JButton("Exit");
		button2.setMaximumSize(new Dimension(100, 50));
		button2.setPreferredSize(new Dimension(100, 50));
		button2.setBounds(440, 420, 100, 50);
		
		bg = new ButtonGroup();
		radio_button1 = new JRadioButton("Easy");
		radio_button1.setBounds(360,220,150,50);
		radio_button1.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		radio_button1.setForeground(Color.BLUE);
		radio_button1.setBackground(new Color(0,0,0,0));
		radio_button1.setSelected(true);
		bg.add(radio_button1);
		
		radio_button2 = new JRadioButton("Normal");
		radio_button2.setBounds(360,270,150,50);
		radio_button2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		radio_button2.setForeground(Color.BLUE);
		radio_button2.setBackground(new Color(0,0,0,0));
		bg.add(radio_button2);
		
		radio_button3 = new JRadioButton("Hard");
		radio_button3.setBounds(360,320,150,50);
		radio_button3.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		radio_button3.setForeground(Color.BLUE);
		radio_button3.setBackground(new Color(0,0,0,0));
		bg.add(radio_button3);
		
    }

    // Update fired from model
    @Override
    public void update() {
        this.repaint();
    }

    // Panel size
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }

    // Paint this panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int fruit_num = this.model.getFruitNum();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(back_ground, 0, 0, this.getWidth(), this.getHeight(), null);
        
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2.setColor(Color.GREEN);
		switch(fruit_num){
		case 5:
			g2.drawString("O", this.getWidth()-240, 50);
			g2.drawString("O", this.getWidth()-200, 50);
			g2.drawString("O", this.getWidth()-160, 50);
			g2.drawString("O", this.getWidth()-120, 50);
			g2.drawString("O", this.getWidth()-80, 50);
			g2.drawString("O", this.getWidth()-40, 50);
			break;
		case 4:
			g2.drawString("O", this.getWidth()-240, 50);
			g2.drawString("O", this.getWidth()-200, 50);
			g2.drawString("O", this.getWidth()-160, 50);
			g2.drawString("O", this.getWidth()-120, 50);
			g2.drawString("O", this.getWidth()-80, 50);
			g2.setColor(Color.RED);
			g2.drawString("X", this.getWidth()-40, 50);
			break;
		case 3:
			g2.drawString("O", this.getWidth()-240, 50);
			g2.drawString("O", this.getWidth()-200, 50);
			g2.drawString("O", this.getWidth()-160, 50);
			g2.drawString("O", this.getWidth()-120, 50);
			g2.setColor(Color.RED);
			g2.drawString("X", this.getWidth()-80, 50);
			g2.drawString("X", this.getWidth()-40, 50);
			break;
		case 2:
			g2.drawString("O", this.getWidth()-240, 50);
			g2.drawString("O", this.getWidth()-200, 50);
			g2.drawString("O", this.getWidth()-160, 50);
			g2.setColor(Color.RED);
			g2.drawString("X", this.getWidth()-120, 50);
			g2.drawString("X", this.getWidth()-80, 50);
			g2.drawString("X", this.getWidth()-40, 50);
			break;
		case 1:
			g2.drawString("O", this.getWidth()-240, 50);
			g2.drawString("O", this.getWidth()-200, 50);
			g2.setColor(Color.RED);
			g2.drawString("X", this.getWidth()-160, 50);
			g2.drawString("X", this.getWidth()-120, 50);
			g2.drawString("X", this.getWidth()-80, 50);
			g2.drawString("X", this.getWidth()-40, 50);
			break;
		case 0:
			g2.drawString("O", this.getWidth()-240, 50);
			g2.setColor(Color.RED);
			g2.drawString("X", this.getWidth()-200, 50);
			g2.drawString("X", this.getWidth()-160, 50);
			g2.drawString("X", this.getWidth()-120, 50);
			g2.drawString("X", this.getWidth()-80, 50);
			g2.drawString("X", this.getWidth()-40, 50);
			break;
		default:
			g2.setColor(Color.RED);
			g2.drawString("X", this.getWidth()-240, 50);
			g2.drawString("X", this.getWidth()-200, 50);
			g2.drawString("X", this.getWidth()-160, 50);
			g2.drawString("X", this.getWidth()-120, 50);
			g2.drawString("X", this.getWidth()-80, 50);
			g2.drawString("X", this.getWidth()-40, 50);
			break;	
		}
        
        // draw all pieces of fruit
        // note that fruit is responsible for figuring out where and how to draw itself
        for (Fruit s : model.getShapes()) {
            s.draw(g2);
        }
        if(gameover){
        	g.setFont(new Font("TimesRoman", Font.PLAIN, 80));
    		g.setColor(Color.BLUE);
    		g.drawString("GAME OVER", 150,180 );
        }
    }

    // Mouse handler
    // This does most of the work: capturing mouse movement, and determining if we intersect a shape
    // Fruit is responsible for determining if it's been sliced and drawing itself, but we still
    // need to figure out what fruit we've intersected.
    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            drag.start(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        	// TODO Auto-generated method stub
        	super.mouseMoved(e);

        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
        	// TODO Auto-generated method stub
        	super.mouseDragged(e);
        	x = e.getPoint().x;
        	y = e.getPoint().y;
        	//repaint();
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            drag.stop(e.getPoint());
        	//repaint();

            // you could do something like this to draw a line for testing
            // not a perfect implementation, but works for 99% of the angles drawn
            
            // int[] x = { (int) drag.getStart().getX(), (int) drag.getEnd().getX(), (int) drag.getEnd().getX(), (int) drag.getStart().getX()};
            // int[] y = { (int) drag.getStart().getY()-1, (int) drag.getEnd().getY()-1, (int) drag.getEnd().getY()+1, (int) drag.getStart().getY()+1};
            // model.add(new Fruit(new Area(new Polygon(x, y, x.length))));

            // find intersected shapes
            int offset = 0; // Used to offset new fruits
            for (Fruit s : model.getShapes()) {
                if (s.intersects(drag.getStart(), drag.getEnd())) {
                    //s.setFillColor(Color.RED);
                    try {
                        Fruit[] newFruits = s.split(drag.getStart(), drag.getEnd());
                        if(newFruits == null) continue;
                        // add offset so we can see them split - this is used for demo purposes only!
                        // you should change so that new pieces appear close to the same position as the original piece
                        for (Fruit f : newFruits) {
                            f.translate(0, offset);
                            f.addBottom(model.getHeight());
                            model.add(f);
                            offset += 20;
                        }
                        model.remove(s);
                        model.SplitOneFruit();
                    } catch (Exception ex) {
                        System.err.println("Caught error: " + ex.getMessage());
                    }
                } else {
                    s.setFillColor(Color.BLUE);
                }
            }
        }
    };

    /*
     * Track starting and ending positions for the drag operation
     * Needed to calculate angle of the slice
     */
    private class MouseDrag {
        private Point2D start;
        private Point2D end;

        MouseDrag() { }

        protected void start(Point2D start) { this.start = start; }
        protected void stop(Point2D end) { this.end = end; }

        protected Point2D getStart() { return start; }
        protected Point2D getEnd() { return end; }

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		lastTime = System.currentTimeMillis();
		for (Fruit s : model.getShapes()) {
			  if(s.CouldBeRemove()){
				  model.remove(s);
				  if(!s.isFragment()){
					  model.missFruit();
					  this.update();
					  if(model.fruitNum() < 0){//the fruit miss number exceed 5
						  model.clearFruits();
						  this.gameover = true;
						  this.GameOver();
					  }
				  }
			  }
		  }
		repaint();
	}
	
	//handle game over, show a restart view
     private void GameOver(){
 		this.add(button);
 		button.setVisible(true);
 		button.addActionListener(this.game_over_model);
 		
		this.add(button2);
		button2.setVisible(true);
		button2.addActionListener(this.game_over_model);

		this.add(radio_button1);
		radio_button1.addActionListener(game_over_model);
		radio_button1.setVisible(true);

		this.add(radio_button2);
		radio_button2.addActionListener(game_over_model);
		radio_button2.setVisible(true);

		this.add(radio_button3);
		radio_button3.addActionListener(game_over_model);
		radio_button3.setVisible(true);
		
		this.game_over_model.addObserver(this);
		this.repaint();
		this.main.GameOver();
     }

}
