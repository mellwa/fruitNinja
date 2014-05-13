import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;



public class StartView extends JPanel implements ModelListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer_start;
	int time;
	private JButton button;
	private JButton button2;
	private JRadioButton radio_button1, radio_button2, radio_button3;
	private ButtonGroup bg;
	private int loc;
	Rectangle rec;
	private BufferedImage image = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private StartModel start_model;
	
	//constructor
	public StartView(StartModel model) {
		this.start_model = model;
		timer_start = new Timer(50,this);
	    timer_start.start();
		button = new JButton("Start");
		button.setMaximumSize(new Dimension(100, 50));
		button.setPreferredSize(new Dimension(100, 50));
		// a GridBagLayout with default constraints centres
		// the widget in the window
		this.setLayout(null);
		button.setBounds(100, 300, 100, 50);
		this.add(button);
		button.setVisible(false);
		button.addActionListener(start_model);
		
		button2 = new JButton("Exit");
		button2.setMaximumSize(new Dimension(100, 50));
		button2.setPreferredSize(new Dimension(100, 50));
		button2.setBounds(300, 300, 100, 50);
		this.add(button2);
		button2.setVisible(false);
		button2.addActionListener(start_model);
		loc = 500;
		
		bg = new ButtonGroup();
		
		radio_button1 = new JRadioButton("Easy");
		radio_button1.setBounds(200,170,150,50);
		radio_button1.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		radio_button1.setForeground(Color.GREEN);
		radio_button1.setSelected(true);
		radio_button1.setBackground(new Color(0,0,0,0));
		bg.add(radio_button1);
		this.add(radio_button1);
		radio_button1.addActionListener(start_model);
		radio_button1.setVisible(false);
		
		radio_button2 = new JRadioButton("Normal");
		radio_button2.setBounds(200,200,150,50);
		radio_button2.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		radio_button2.setForeground(Color.GREEN);
		radio_button2.setBackground(new Color(0,0,0,0));
		bg.add(radio_button2);
		this.add(radio_button2);
		radio_button2.addActionListener(start_model);
		radio_button2.setVisible(false);
		
		radio_button3 = new JRadioButton("Hard");
		radio_button3.setBounds(200,230,150,50);
		radio_button3.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		radio_button3.setForeground(Color.GREEN);
		radio_button3.setBackground(new Color(0,0,0,0));
		bg.add(radio_button3);
		this.add(radio_button3);
		radio_button3.addActionListener(start_model);
		radio_button3.setVisible(false);
	}
	
	// Panel size
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,400);
    }
    
	@Override
	public void update() {//game start
		// TODO Auto-generated method stub
		this.timer_start.stop();
		button.setVisible(false);
		button.setVisible(false);
	}
	
	@Override
	public void paintComponent(Graphics g){//showing the animation
		super.paintComponent(g);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		g.setColor(Color.GREEN);
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = (int) (i + time);
			
		}
		g.drawImage(image, 0, 0, getWidth(),getHeight(),null);
		g.drawString("Fruit Ninja", 120, loc);
		if(loc < 150){//after the string arrive the checkpoint:)
			button.setVisible(true);
			button2.setVisible(true);
			radio_button1.setVisible(true);
			radio_button2.setVisible(true);
			radio_button3.setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(loc > 140){
			loc -= 3;
		}
		time += 1;
		this.repaint();
		if(!this.isVisible()){
			timer_start.stop();
		}	
	}
}
