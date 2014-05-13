/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit extends Component implements FruitInterface{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Area            fruitShape   = null;
    private Color           fillColor    = Color.RED;
    private Color           outlineColor = Color.BLACK;
    private AffineTransform transform    = new AffineTransform();
    private double          outlineWidth = 5;
    
    //add something
    private double lastTime;
    private double velocity;
    private double gravity;
    private double lastX;
    private double lastY;
    private boolean fragment;
    private boolean dropping;
    private BufferedImage image;
    private BufferedImage fragimage;
    private int image_x, image_y, bottom;
    private double x_speed;
    /**
     * A fruit is represented using any arbitrary geometric shape.
     */
    Fruit (Area fruitShape, boolean fragment) {
        this.fruitShape = (Area)fruitShape.clone();
        this.lastTime = System.currentTimeMillis();
        velocity = 300;
        this.fragment = fragment;
        if(fragment){
        	velocity = 0;
        }
        gravity = 100;
        lastY = 0;
        
    }
    //overwrite Fruit's constructor
    Fruit (Area fruitShape, boolean fragment, BufferedImage image, BufferedImage fragimage, int image_x, int image_y, double x_speed) {
        this.fruitShape = (Area)fruitShape.clone();
        this.lastTime = System.currentTimeMillis();
        this.image = image;
        this.fragimage = fragimage;
        this.x_speed = x_speed;
        
       	this.image_x = image_x;//(int) fruitShape.getBounds().getX();
       	this.image_y = image_y;//(int) fruitShape.getBounds().getY(); 
        
        velocity = 450;
        this.fragment = fragment;
        if(fragment){
        	velocity = 0;
        }
        gravity = 300;
        lastY = 0;
        lastX = 0;
    }

    public void addBottom(int b){
    	this.bottom = b;
    }
    /**
     * The color used to paint the interior of the Fruit.
     */
    public Color getFillColor() {
        return fillColor;
    }
    /**
     * The color used to paint the interior of the Fruit.
     */
    public void setFillColor(Color color) {
        fillColor = color;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public Color getOutlineColor() {
        return outlineColor;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public void setOutlineColor(Color color) {
        outlineColor = color;
    }
    
    /**
     * Gets the width of the outline stroke used when painting.
     */
    public double getOutlineWidth() {
        return outlineWidth;
    }

    /**
     * Sets the width of the outline stroke used when painting.
     */
    public void setOutlineWidth(double newWidth) {
        outlineWidth = newWidth;
    }

    /**
     * Concatenates a rotation transform to the Fruit's affine transform
     */
    public void rotate(double theta) {
        transform.rotate(theta);
    }

    /**
     * Concatenates a scale transform to the Fruit's affine transform
     */
    public void scale(double x, double y) {
        transform.scale(x, y);
    }

    /**
     * Concatenates a translation transform to the Fruit's affine transform
     */
    public void translate(double tx, double ty) {
        transform.translate(tx, ty);
    }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public AffineTransform getTransform() {
        return (AffineTransform)transform.clone();
    }

    /**
     * Creates a transformed version of the fruit. Used for painting
     * and intersection testing.
     */
    public Area getTransformedShape() {
        return fruitShape.createTransformedArea(transform);
    }
    
    public boolean isFragment(){
    	return this.fragment;
    }

    public void addVelocity(int velocity){
    	this.velocity = velocity;
    }
    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Graphics2D g2) {
        // TODO BEGIN CS349
    	double t = (System.currentTimeMillis() - lastTime)/1000;
    	double x,y;
    	double deltaY = 0;
    	double deltaX = 0;
    	double currentX = 0;
    	double currentY;
    	currentX = this.x_speed*t;
    	currentY = 0-((velocity*t) - (gravity*t*t/2));
    	deltaX = currentX - lastX;
    	deltaY = currentY - lastY;
    	lastY = currentY;
    	lastX = currentX;
    	if(deltaY > 0){
    		this.dropping = true;
    	}
    	//System.out.println("X: "+transform.getTranslateX()+" Y is: "+transform.getTranslateY());
    	x = transform.getTranslateX();
    	y = transform.getTranslateY();
        transform.translate(deltaX, deltaY);
       	AffineTransform original = g2.getTransform();
    	g2.setTransform(transform);
    	g2.setClip(fruitShape);
    	if(image != null && this.fragment){
    		g2.drawImage(fragimage, image_x, image_y, 
    				(int)fragimage.getWidth(), (int)fragimage.getHeight(), null);
    	}
    	else if(image != null){
    		g2.drawImage(image, image_x, image_y, 
    				(int)image.getWidth(), (int)image.getHeight(), null);
    		//g2.draw(fruitShape);
    	}
    	else{
    		g2.fill(this.fruitShape);
    	}
    	g2.setColor(Color.BLUE);
    	g2.setTransform(original);
        // TODO END CS349
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(Point2D p1, Point2D p2) {
        // TODO BEGIN CS349
    	Line2D line = new Line2D.Double(p1,p2);
    	Area a = fruitShape.createTransformedArea(transform);
    	if(!a.contains(p1) && !a.contains(p2) && line.intersects(a.getBounds2D())){
    		return true;
    	}
    	else{
    		return false;
    	}
        // TODO END CS349
    }

    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(Point2D p1) {
        return this.getTransformedShape().contains(p1);
    }
    
    
    //calculate the interct point of line 1 and line 2
    public Point2D InterctPoint(Line2D line1, Line2D line2){
        double x1 = line1.getX1();
        double y1 = line1.getY1();
        double x2 = line1.getX2();
        double y2 = line1.getY2();
        double x3 = line2.getX1();
        double y3 = line2.getY1();
        double x4 = line2.getX2();
        double y4 = line2.getY2();

        Point2D p = null;

        double distance = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (distance != 0) {
            double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / distance;
            double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / distance;
            p = new Point2D.Double(xi, yi);
        }
        return p;
    }
    
    public Point2D[] fruitShapeInterctionPoints(Line2D line){

        Point2D[] p = new Point2D[4];
        Point2D[] point = new Point2D[2];
        int j = 0;

        // Top line
        p[0] = InterctPoint(line,
                        new Line2D.Double(
                        fruitShape.getBounds2D().getX(),
                        fruitShape.getBounds2D().getY(),
                        fruitShape.getBounds2D().getX() + fruitShape.getBounds2D().getWidth(),
                        fruitShape.getBounds2D().getY()));
        if(p[0] != null){
        	if(p[0].getX() < fruitShape.getBounds().getMinX() || p[0].getX() > fruitShape.getBounds().getMaxX() 
        			|| p[0].getY() < fruitShape.getBounds().getMinY() || p[0].getY() > fruitShape.getBounds().getMaxY()){
        		p[0] = null;
        	}
        }
        // Bottom line
        p[1] = InterctPoint(line,
                        new Line2D.Double(
                        		fruitShape.getBounds2D().getX(),
                        		fruitShape.getBounds2D().getY() + fruitShape.getBounds2D().getHeight(),
                        		fruitShape.getBounds2D().getX() + fruitShape.getBounds2D().getWidth(),
                        		fruitShape.getBounds2D().getY() + fruitShape.getBounds2D().getHeight()));
        if(p[1] != null){
        	if(p[1].getX() < fruitShape.getBounds().getMinX() || p[1].getX() > fruitShape.getBounds().getMaxX() 
        			|| p[1].getY() < fruitShape.getBounds().getMinY() || p[1].getY() > fruitShape.getBounds().getMaxY()){
        		p[1] = null;
        	}
        }
        // Left side...
        p[2] = InterctPoint(line,
                        new Line2D.Double(
                        		fruitShape.getBounds2D().getX(),
                        		fruitShape.getBounds2D().getY(),
                        		fruitShape.getBounds2D().getX(),
                        		fruitShape.getBounds2D().getY() + fruitShape.getBounds2D().getHeight()));
        if(p[2] != null){
        	if(p[2].getX() < fruitShape.getBounds().getMinX() || p[2].getX() > fruitShape.getBounds().getMaxX() 
        			|| p[2].getY() < fruitShape.getBounds().getMinY() || p[2].getY() > fruitShape.getBounds().getMaxY()){
        		p[2] = null;
        	}
        }
        // Right side
        p[3] = InterctPoint(line,
                        new Line2D.Double(
                        		fruitShape.getBounds2D().getX() + fruitShape.getBounds2D().getWidth(),
                        		fruitShape.getBounds2D().getY(),
                        		fruitShape.getBounds2D().getX() + fruitShape.getBounds2D().getWidth(),
                        		fruitShape.getBounds2D().getY() + fruitShape.getBounds2D().getHeight()));
        if(p[3] != null){
        	if(p[3].getX() < fruitShape.getBounds().getMinX() || p[3].getX() > fruitShape.getBounds().getMaxX() 
        			|| p[3].getY() < fruitShape.getBounds().getMinY() || p[3].getY() > fruitShape.getBounds().getMaxY()){
        		p[3] = null;
        	}
        }
        
        for(int i = 0; i < 4; i++){
        	if(p[i] != null){
        		if(j > 1){
        			j = 1;
        			System.out.println("j cannot be over 1");
        		}
        		point[j] = p[i];
        		j++;
        	}
        }

        return point;
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(Point2D point1, Point2D point2) throws NoninvertibleTransformException {
    	if(this.fragment){
    		return null;
    	}
        Area topArea = null;
        Area bottomArea = null;
        Area totalArea = null;
        double delta = 0;
       
        double x1,x2,y1,y2;
        x1 =  (point1.getX() - transform.getTranslateX());
        x2 =  (point2.getX() - transform.getTranslateX());
        y1 =  (point1.getY() - transform.getTranslateY());
        y2 =  (point2.getY() - transform.getTranslateY());
        
        double actual_delta_y = point2.getY() - point1.getY();
        double actual_delta_x = point2.getX() - point1.getX();
        Point2D[] points; 
        Line2D line1 = new Line2D.Double(x1, y1, x2, y2);
        points = this.fruitShapeInterctionPoints(line1);
        if(points[0] == null || points[1]==null){
        	System.out.println("cut a point?");
        	return null;
        }
        Point2D p1 = points[0];
        Point2D p2 = points[1];
               
        
        // TODO BEGIN CS349
        // Rotate shape to align slice with x-axis
        // Bisect shape above/below x-axis (look at intersection methods!)
        double delta_x = p2.getX()-p1.getX();
        double delta_y = p2.getY() - p1.getY();
        if(actual_delta_x == 0){
        	actual_delta_x += 1;
        }
        delta = actual_delta_y/actual_delta_x;
        double theta = Math.atan2(delta_y,delta_x);
        if(Math.abs(theta) > Math.PI/2){
        	theta = 0 - theta;
        	if(theta < 0){
        		theta = 0 - theta - Math.PI;
        	}
        	else{
        		theta = Math.PI - theta;
        	}
        }
        AffineTransform trans = new AffineTransform();
        trans.rotate(0-theta);
        trans.translate(0-p1.getX(),0-p1.getY());
        Area shape_at_x_axis = this.fruitShape.createTransformedArea(trans);
        int topAreaY2 = (int) Math.min(shape_at_x_axis.getBounds2D().getMaxY(), 0);
        int topAreaY1 = (int) Math.min(shape_at_x_axis.getBounds2D().getMinY(), 0);
        int bottomAreaY2 = (int) Math.max(shape_at_x_axis.getBounds2D().getMaxY(), 0);
        int bottomAreaY1 = (int) Math.max(shape_at_x_axis.getBounds2D().getMinY(), 0);
        
        
        Rectangle topRec = new Rectangle((int)shape_at_x_axis.getBounds().getX(), topAreaY1, 
        									(int)shape_at_x_axis.getBounds().getWidth(), topAreaY2-topAreaY1);
        Rectangle bottomRec = new Rectangle((int)shape_at_x_axis.getBounds().getX(), bottomAreaY1, 
				(int)shape_at_x_axis.getBounds().getWidth(), bottomAreaY2-bottomAreaY1);

        totalArea = new Area(shape_at_x_axis);
        topArea = new Area(shape_at_x_axis);
        topArea.intersect(new Area(topRec));
        bottomArea = new Area(shape_at_x_axis);
        bottomArea.intersect(new Area(bottomRec));
        
        try {
			trans.invert();
		} catch (NoninvertibleTransformException e) {
			System.err.println("invalid invert affine transform");
			System.exit(1);
		}
        topArea = topArea.createTransformedArea(trans);
        bottomArea = bottomArea.createTransformedArea(trans);
        totalArea = totalArea.createTransformedArea(trans);
        topArea.transform(transform);
        bottomArea.transform(transform);
        totalArea.transform(transform);
        // TODO END CS349
        if (topArea != null && bottomArea != null){
        	if(this.x_speed < 0){
        		this.x_speed = 0 - this.x_speed;
        	}
        	if(delta >= 0){
        		return new Fruit[] { new Fruit(topArea,true,image,this.fragimage,(int)totalArea.getBounds().getX()+this.image_x,
        				(int)totalArea.getBounds().getY()+this.image_y,this.x_speed), 
        				new Fruit(bottomArea,true,image,this.fragimage,(int)totalArea.getBounds().getX()+this.image_x,
        						(int)totalArea.getBounds().getY()+this.image_y,0-this.x_speed) };
        	}
        	else{
        		return new Fruit[] { new Fruit(topArea,true,image,this.fragimage,(int)totalArea.getBounds().getX()+this.image_x,
        				(int)totalArea.getBounds().getY()+this.image_y,0-this.x_speed), 
        				new Fruit(bottomArea,true,image,this.fragimage,(int)totalArea.getBounds().getX()+this.image_x,
        						(int)totalArea.getBounds().getY()+this.image_y,this.x_speed) };
        	}
        }
        return new Fruit[0];
     }
    
    public boolean CouldBeRemove(){
    	if(this.dropping && transform.getTranslateY()>this.bottom){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

}
