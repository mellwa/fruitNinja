/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model implements ActionListener{
  // Observer list
  private Vector<ModelListener> views = new Vector<ModelListener>();

  // Fruit that we want to display
  private ArrayList<Fruit> shapes = new ArrayList<Fruit>();
  
  Random random;
  private Vector<BufferedImage> image = new Vector<BufferedImage>();
  private int fruit_num;
  private int split_num;
  private int height;
  private int width;
  private int level;

  // Constructor
  Model(int level) {
    shapes.clear();
    this.level = level;
    random = new Random();
    this.fruit_num = 5;
    this.split_num = 0;
    this.width = 800;
    this.height = 600;
  }

  // MVC methods
  // These likely don't need to change, they're just an implementation of the
  // basic MVC methods to bind view and model together.
  public void addObserver(ModelListener view) {
    views.add(view);
  }

  public void addImage(BufferedImage i){
	  image.add(i);
  }
  
  public void notifyObservers() {
    for (ModelListener v : views) {
      v.update();
    }
  }

  // Model methods
  // You may need to add more methods here, depending on required functionality.
  // For instance, this sample makes to effort to discard fruit from the list.
  public void add(Fruit s) {
    shapes.add(s);
    notifyObservers();
  }
  
  public void missFruit(){
	  this.fruit_num--;
  }
  
  public int fruitNum(){
	  return this.fruit_num;
  }
  
  public void clearFruits(){
	  this.shapes.clear();
  }
  
  public void remove(Fruit s){
	  shapes.remove(s);
  }
  
  public void SplitOneFruit(){
	  this.split_num++;
	  notifyObservers();
  }
  
  public int GetSplitNum(){
	  return this.split_num;
  }
  
  public int getFruitNum(){
	  return this.fruit_num;
  }

  @SuppressWarnings("unchecked")
public ArrayList<Fruit> getShapes() {
      return (ArrayList<Fruit>)shapes.clone();
  }
  
  public void addHeight(int height){
	  this.height = height;
  }

  public void addWidth(int width){
	  this.width = width;
  }
  public int getHeight(){
	  return this.height;
  }
  @Override
	public void actionPerformed(ActionEvent e) {
	  // TODO Auto-generated method stub
	  for(int i = 0; i < this.level; i++){
	  Fruit f = null;
	  int x = this.random.nextInt(this.width - 20);
	  int velocity = 0;
	  int speed = this.random.nextInt(this.width/5);
	  if((x < 30 || x > this.width-30) && speed < 30){
		  speed = 50;
	  }
	  if(x > (this.width/2)){
		  speed = 0 - speed;
	  }
	  int type = this.random.nextInt(5);
	  switch(type){
	  	case 1:
	  		//peach
	  		f = new Fruit(new Area(new Ellipse2D.Double(0, 0, 67, 67)),false,image.elementAt(2),image.elementAt(3),-11,-17,speed);
	  		break;
	  	case 2:
	  		//watermelon
	  		f = new Fruit(new Area(new Ellipse2D.Double(0, 0, 70, 65)),false,image.elementAt(4),image.elementAt(5),-13,-8,speed);
	  		break;
	  	case 3:
	  		//green apple
	  		f = new Fruit(new Area(new Ellipse2D.Double(0, 0, 84, 75)),false,image.elementAt(6),image.elementAt(7),-7,-5,speed);
	  		break;
	  	case 4:
	  		//orange
	  		f = new Fruit(new Area(new Ellipse2D.Double(0, 0, 80, 77)),false,image.elementAt(8),image.elementAt(9),-10,-10,speed);
	  		break;
	  	default:
	  		//apple
	  		f = new Fruit(new Area(new Ellipse2D.Double(0, 0, 74, 74)),false,image.elementAt(0),image.elementAt(1),-4,-11,speed);
	  		break;
	  }
	  f.translate(x, height);
	  velocity = (int) Math.sqrt(2*300*(height-100));
	  f.addVelocity(velocity);
	  f.addBottom(this.height);
	  //f.addBottom(height);
	  this.add(f);
	  }
	}
}
