/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * View to display the Title, and Score
 * Score currently just increments every time we get an update
 * from the model (i.e. a new fruit is added).
 */
public class TitleView extends JPanel implements ModelListener,ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Model model;
  private JLabel title, time,score;
  private int count = 0;
  private int min = 0;
  private int split_num = 0;
  // Constructor requires model reference
  TitleView (Model model) {
    // register with model so that we get updates
    this.model = model;
    this.model.addObserver(this);

    // draw something
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.GRAY);
    // You may want a better name for this game!
    title = new JLabel(" CS349 Fruit Ninja");
    time = new JLabel();
    score = new JLabel();

    // use border layout so that we can position labels on the left and right
    this.setLayout(new BorderLayout());
    this.add(title, BorderLayout.WEST);
    this.add(time, BorderLayout.EAST);
    this.add(score,BorderLayout.CENTER);
  }

  // Panel size
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500,35);
  }

  // Update from model
  // This is ONLY really useful for testing that the view notifications work
  // You likely want something more meaningful here.
  @Override
  public void update() {
    this.split_num = this.model.GetSplitNum();
    this.repaint();
    //paint(getGraphics());
  }

  // Paint method
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    time.setText("Time: " + min+" : "+count + "  ");
    score.setText("		High Score: "+this.split_num);
  }

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
    count++;
    if(count>59){
    	min++;
    	count = 0;
    }
    this.repaint();
    //paint(getGraphics());
}
}
