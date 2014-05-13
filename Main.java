/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Main {
  static private Model model;
  static private View view;
  static private TitleView title;
  static private StartView start_view;
  static private JFrame start_frame,frame;
  static private Timer timer,timer2;
  static private StartModel start_model;
  static private GameOverModel game_over_model;
  /*
   * Main entry point for the application
   */
  public static void main(String[] args) throws NoninvertibleTransformException, IOException {
    // instantiate your model and views
    // add any new views here
    new Main().GamePre();
  }
  public void GamePre(){
	    start_model = new StartModel(this);
	    start_view = new StartView(start_model);
	    
	    
	    start_frame = new JFrame("Start");
	    start_frame.setLocation(350, 150);
	    start_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    start_frame.setContentPane(start_view);
	    start_frame.pack();
	    start_frame.setVisible(true);
  }
/*
 * Game start method to start the entire game
 */
public void GameStart(int level) throws NoninvertibleTransformException, IOException{
	if(title != null){ //check if the game is a restarting game
		title.setEnabled(false);
		title.setVisible(false);
		frame.remove(title);
	}
	if(view != null){//check if the game is a restarting game
		view.setEnabled(false);
		view.setVisible(false);
		frame.remove(view);
	}
	if(start_view != null || start_frame != null){
		start_view.setVisible(false);
		start_frame.dispose();
		start_frame = null;
		start_view = null;
	}
    	model = new Model(level);
    	game_over_model = new GameOverModel(this);
    	title = new TitleView(model);
    	view = new View(model,game_over_model,this);
    	if(timer == null){
    		timer = new Timer(20,view);
    	}
    	else{
    		timer.addActionListener(view);
    	}
	    timer.restart();
	    
	    if(timer2 == null){
	    	timer2 = new Timer(1000, model);
	    	timer2.addActionListener(title);
	    }
	    else{
	    	timer2.addActionListener(model);
	    	timer2.addActionListener(title);
	    }
	    timer2.restart();
	    // customize the title and any other top-level settings
	    if(frame == null){
	    	frame = new JFrame("CS349 A3 Fruit Ninja");
	    	frame.setLocation(200,0);
	    }
	    
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setLayout(new BorderLayout());
	    frame.add(title, BorderLayout.NORTH);
	    frame.add(view, BorderLayout.CENTER);
	    frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				model.addHeight(frame.getHeight());
				model.addWidth(frame.getWidth());
				view.repaint();
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				model.addHeight(frame.getHeight());
				model.addWidth(frame.getWidth());
				view.repaint();
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    frame.pack();
	    frame.setVisible(true);
	    //view.run();
  }

	public void GameOver(){
		timer.stop();
		timer2.stop();
		timer.removeActionListener(view);
		timer2.removeActionListener(model);
		timer2.removeActionListener(title);
	}

}




