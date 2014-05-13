import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.io.IOException;
import java.util.Vector;


public class GameOverModel implements ActionListener {

	private Vector<ModelListener> views = new Vector<ModelListener>();
	private Main main;
	private int level;
	
	//constructor
	GameOverModel(Main main){
		this.main = main;
		level = 1;
	}
	
	public void addObserver(ModelListener view) {
	    views.add(view);
	  }
	
	public void notifyObservers() {
	    for (ModelListener v : views) {
	     v.update();
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//handle button actions
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "Easy"){
			this.level = 1;
		}
		else if(e.getActionCommand() == "Normal"){
			this.level = 2;
			
		}
		else if(e.getActionCommand() == "Hard"){
			this.level = 3;
			
		}
		
		if(e.getActionCommand() == "Restart"){
			try {
				this.notifyObservers();
				main.GameStart(this.level);
			} catch (NoninvertibleTransformException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand() == "Exit"){
			this.notifyObservers();
			System.exit(0);
		}
	}

}
