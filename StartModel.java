import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.io.IOException;
import java.util.Vector;


public class StartModel implements ActionListener {

	private Main main;
	private int level;
	private Vector<ModelListener> views = new Vector<ModelListener>();
	
	StartModel(Main main){
		level = 1;
		this.main = main;
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
	public void actionPerformed(ActionEvent e) {
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
		
		if(e.getActionCommand() == "Start"){
			try {
				this.notifyObservers();
				main.GameStart(level);
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
