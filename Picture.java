import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.ArrayList;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Copyright Georgia Institute of Technology 2004-2008
 * @author Barbara Ericson ericson@cc.gatech.edu
 * 
 * @author Delos Chang - modified for PS1 for CS10 -- 1/20/13
 * modified to reduce the image into X colors using k-means algorithm, random colors 
 * 
 */

public class Picture extends SimplePicture { 

  ///////////////////// constructors ////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture () {
    
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height) {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
   /**
   * Class method to let the user pick a file name and then create the picture 
   * and show it
   * @return the picture object
   */
  public static Picture pickAndShow() {
    String fileName = FileChooser.pickAFile();
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * Class method to create a picture object from the passed file name and 
   * then show it
   * @param fileName the name of the file that has a picture in it
   * @return the picture object
   */
  public static Picture showNamed(String fileName) {
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * A method create a copy of the current picture and return it
   * @return the copied picture
   */
  public Picture copy()
  {
    return new Picture(this);
  }
  
  /**
   * Method to increase the red in a picture.
   */
  public void increaseRed() {
    Pixel [] pixelArray = this.getPixels();
    for (Pixel pixelObj : pixelArray) {
      pixelObj.setRed(pixelObj.getRed()*2);
    }
  }
  
  /**
   * Method to negate a picture
   */
  public void negate() {
    Pixel [] pixelArray = this.getPixels();
    int red,green,blue;
    
    for (Pixel pixelObj : pixelArray) {
      red = pixelObj.getRed();
      green = pixelObj.getGreen();
      blue = pixelObj.getBlue();
      pixelObj.setColor(new Color(255-red, 255-green, 255-blue));
    }
  }
  
  /**
   * Method to flip a picture 
   */
  public Picture flip() {
    Pixel currPixel = null;
    Pixel targetPixel = null;
    Picture target = 
      new Picture(this.getWidth(),this.getHeight()); // target is a pic with same width and height
    //e.g. (new Picture(10,20)).explore(); all the pixels will be white
    
    // runs through X's
    for (int srcX = 0, trgX = getWidth()-1; 
         srcX < getWidth(); // assumes there is a this.getWidth();
         srcX++, trgX--) { // add for source X, subtract for target X 
    	
    	// runs through Y's
      for (int srcY = 0, trgY = 0; 
           srcY < getHeight();
           srcY++, trgY++) { 
        
        // get the current pixel
        currPixel = this.getPixel(srcX,srcY); // pixel from source
        targetPixel = target.getPixel(trgX,trgY); // pixel from target
        
        // copy the color of currPixel into target
        targetPixel.setColor(currPixel.getColor()); // get color from source and put in target
        // recolor pixels and work way in
      }
    }
    return target;
  }
  
  /**
   * Method to decrease the red by half in the current picture
   */
  public void decreaseRed() {
  
    Pixel pixel = null; // the current pixel
    int redValue = 0;       // the amount of red

    // get the array of pixels for this picture object
    Pixel[] pixels = this.getPixels();

    // start the index at 0
    int index = 0;

    // loop while the index is less than the length of the pixels array
    while (index < pixels.length) {

      // get the current pixel at this index
      pixel = pixels[index];
      // get the red value at the pixel
      redValue = pixel.getRed();
      // set the red value to half what it was
      redValue = (int) (redValue * 0.5);
      // set the red for this pixel to the new value
      pixel.setRed(redValue);
      // increment the index
      index++;
    }
  }
  
  /**
   * Method to decrease the red by an amount
   * @param amount the amount to change the red by
   */
  public void decreaseRed(double amount) {
 
    Pixel[] pixels = this.getPixels();
    Pixel p = null;
    int value = 0;

    // loop through all the pixels
    for (int i = 0; i < pixels.length; i++) {
 
      // get the current pixel
      p = pixels[i];
      // get the value
      value = p.getRed();
      // set the red value the passed amount time what it was
      p.setRed((int) (value * amount));
    }
  }
  
  /**
   * Method to compose (copy) this picture onto a target picture
   * at a given point.
   * @param target the picture onto which we copy this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void compose(Picture target, int targetX, int targetY) {
 
    Pixel currPixel = null;
    Pixel newRedPixel = null;

    // loop through the columns
    for (int srcX=0, trgX = targetX; srcX < this.getWidth();
         srcX++, trgX++) {
  
      // loop through the rows
      for (int srcY=0, trgY=targetY; srcY < this.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* copy the color of currPixel into target,
         * but only if it'll fit.
         */
        if (trgX < target.getWidth() && trgY < target.getHeight()) { // make sure targetX is less than width etc. 
          newRedPixel = target.getPixel(trgX,trgY);
          newRedPixel.setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to scale the picture by a factor, and return the result
   * @param factor the factor to scale by (1.0 stays the same,
   *    0.5 decreases each side by 0.5, 2.0 doubles each side)
   * @return the scaled picture
   */
  public Picture scale(double factor) {
    
    Pixel sourcePixel, targetPixel;
    Picture canvas = new Picture(
                                 (int) (factor*this.getWidth())+1,
                                 (int) (factor*this.getHeight())+1);
    // loop through the columns
    for (double sourceX = 0, targetX=0;
         sourceX < this.getWidth();
         sourceX+=(1/factor), targetX++) {
      
      // loop through the rows
      for (double sourceY=0, targetY=0;
           sourceY < this.getHeight();
           sourceY+=(1/factor), targetY++) {
        
        sourcePixel = this.getPixel((int) sourceX,(int) sourceY);
        targetPixel = canvas.getPixel((int) targetX, (int) targetY);
        targetPixel.setColor(sourcePixel.getColor());
      }
    }
    return canvas;
  }
  
  /**
   * Method to do chromakey using an input color for the background
   * and a point for the upper left corner of where to copy
   * @param target the picture onto which we chromakey this picture
   * @param bgColor the color to make transparent
   * @param threshold within this distance from bgColor, make transparent
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void chromakey(Picture target, Color bgColor, int threshold,
                        int targetX, int targetY) {
 
    Pixel currPixel = null;
    Pixel newRedPixel = null;

    // loop through the columns
    for (int srcX=0, trgX=targetX;
        srcX<getWidth() && trgX<target.getWidth();
        srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
        srcY<getHeight() && trgY<target.getHeight();
        srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel is within threshold of
         * the input color, then don't copy the pixel
         */
        if (currPixel.colorDistance(bgColor)>threshold) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
    /**
   * Method to do chromakey assuming a blue background 
   * @param target the picture onto which we chromakey this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void blueScreen(Picture target,
                        int targetX, int targetY) {

    Pixel currPixel = null;
    Pixel newRedPixel = null;

    // loop through the columns
    for (int srcX=0, trgX=targetX;
         srcX<getWidth() && trgX<target.getWidth();
         srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
           srcY<getHeight() && trgY<target.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel mostly blue (blue value is
         * greater than red and green combined), then don't copy pixel
         */
        if (currPixel.getRed() + currPixel.getGreen() > currPixel.getBlue()) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to change the picture to gray scale with luminance
   */
  public void grayscaleWithLuminance()
  {
    Pixel[] pixelArray = this.getPixels();
    Pixel pixel = null;
    int luminance = 0;
    double redValue = 0;
    double greenValue = 0;
    double blueValue = 0;

    // loop through all the pixels
    for (int i = 0; i < pixelArray.length; i++)
    {
      // get the current pixel
      pixel = pixelArray[i];

      // get the corrected red, green, and blue values
      redValue = pixel.getRed() * 0.299;
      greenValue = pixel.getGreen() * 0.587;
      blueValue = pixel.getBlue() * 0.114;

      // compute the intensity of the pixel (average value)
      luminance = (int) (redValue + greenValue + blueValue);

      // set the pixel color to the new color
      pixel.setColor(new Color(luminance,luminance,luminance));

    }
  }
  
  /** 
   * Method to do an oil paint effect on a picture
   * @param dist the distance from the current pixel 
   * to use in the range
   * @return the new picture
   */
  public Picture oilPaint(int dist) {
    
    // create the picture to return
    Picture retPict = new Picture(this.getWidth(),this.getHeight());
    
    // declare pixels
    Pixel currPixel = null;
    Pixel retPixel = null;
    
    // loop through the pixels
    for (int x = 0; x < this.getWidth(); x++) {
      for (int y = 0; y < this.getHeight(); y++) {
        currPixel = this.getPixel(x,y);
        retPixel = retPict.getPixel(x,y);
        retPixel.setColor(currPixel.getMostCommonColorInRange(dist));
      }
    }
    return retPict;
  }
  
  
  /** 
   * Method to modify image by weighting sums of input matrices
   * created by Delos for SA2
   * @param matrix weight to convolve image with
   * @return the new picture
   */
  public Picture convolve(float [][] matrix){
    // first make a copy of the picture
    Pixel currPixel = null;
    Pixel targetPixel = null;
    Picture target = 
      new Picture(this.getWidth(),this.getHeight()); // target is a pic with same width and height
    //e.g. (new Picture(10,20)).explore(); all the pixels will be white
    
    // loop through X's 
    for (int srcX = 0, trgX = 0;
    		srcX < getWidth();
    		srcX++, trgX++){
    	// loop through Y's
    	for (int srcY = 0, trgY = 0;
    		srcY < getHeight();
    		srcY++, trgY++){
    		
    		// grab the currPixel at the X,Y location
    		currPixel = this.getPixel(srcX, srcY);
    		
    		// variables for surrounding pixels and their colors
    		Pixel surroundingPixel;
    		float newRedPixel, newGreenPixel, newBluePixel;
    		
    		// do not weight for the pixels around the edges
    		if (srcX != 0 && srcY != 0 && srcX != this.getWidth() - 1 && srcY != this.getHeight() - 1){
	    		// top left
	    		surroundingPixel = this.getPixel(srcX - 1, srcY - 1);
	    		newRedPixel = surroundingPixel.getRed() * matrix[0][0];
	    		newGreenPixel = surroundingPixel.getGreen() * matrix[0][0];
	    		newBluePixel = surroundingPixel.getBlue() * matrix[0][0];
	    		
	    		// mid top
	    		surroundingPixel = this.getPixel(srcX, srcY - 1);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[0][1];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[0][1];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[0][1];
	    			
	    		// right top
	    		surroundingPixel = this.getPixel(srcX + 1, srcY - 1);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[0][2];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[0][2];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[0][2];
	    		
	    		// mid left
	    		surroundingPixel = this.getPixel(srcX - 1, srcY);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[1][0];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[1][0];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[1][0];
	    		
	    		// center
	    		surroundingPixel = this.getPixel(srcX, srcY);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[1][1];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[1][1];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[1][1];
	    		
	    		// mid right
	    		surroundingPixel = this.getPixel(srcX + 1, srcY);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[1][2];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[1][2];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[1][2];
	    		
	    		// bottom left
	    		surroundingPixel = this.getPixel(srcX - 1, srcY + 1);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[2][0];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[2][0];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[2][0];
	    		
	    		// bottom mid
	    		surroundingPixel = this.getPixel(srcX, srcY + 1);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[2][1];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[2][1];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[2][1];
	    		
	    		// bottom right
	    		surroundingPixel = this.getPixel(srcX + 1, srcY + 1);
	    		newRedPixel = newRedPixel + surroundingPixel.getRed() * matrix[2][2];
	    		newGreenPixel = newGreenPixel + surroundingPixel.getGreen() * matrix[2][2];
	    		newBluePixel = newBluePixel + surroundingPixel.getBlue() * matrix[2][2];
	    		
	    		// set the target pixel to the new weighted sum
	    		targetPixel = target.getPixel(srcX, srcY);
	    		targetPixel.setRed((int)newRedPixel);
	    		targetPixel.setGreen((int)newGreenPixel);
	    		targetPixel.setBlue((int)newBluePixel);
    		}
    	}
    }
    
    return target;
  }
  
  
  /** 
   * Method that generates random RGB values
   * @return random RGB color
   */
  public static Color randomColorValue(){
	  int randRedValue = (int)(Math.random()*255);
	  int randGreenValue = (int)(Math.random()*255);
	  int randBlueValue = (int)(Math.random()*255);
	  
	  Color randColor = new Color(randRedValue, randGreenValue, randBlueValue);
	  return randColor;
  }
  
  
  
  /** 
   * Method to compute relative distance without sq rt (faster runtime)
   * written by Delos
   * @param pixel pixel to grab color from and calculate with, color2 2nd RGB color to calculate dist to
   * @return the distance between them without square root
   */
  public static int computeDistance(Pixel pixel, Color color2){
	  Color color1 = pixel.getColor();
	  return (int) (Math.pow(color1.getRed() - color2.getRed(), 2.0) + Math.pow(color1.getGreen() - color2.getGreen(), 2.0) + Math.pow(color1.getBlue() - color2.getBlue(), 2.0));
  }
  
  /** 
   * Method takes in ArrayList of colors and a pixel
   * written by Delos
   * @param colors list of colors
   * @param pixel pixel to calculate distance from 
   * @return closest color to that pixel color
   */
  public static Color closestColor(Pixel pixel, ArrayList<Color> colors){
	  
	  int lowestdist = 0;
	  Color closestcolor = null;
	  
	  for (Color color : colors){
		  int calcdist = computeDistance(pixel, color);
//		  int calcdist = pixel.colorDistance(color); // alternative distance calc with sq rt
		  
		  if (lowestdist == 0){
			  lowestdist = calcdist;
			  closestcolor = color;
		  }
		  
		  if (calcdist < lowestdist){
			  lowestdist = calcdist;
			  closestcolor = color;
//			  System.out.println("lowest "+calcdist);
		  }
		  
	  }
	  return closestcolor;
  }
  
  /** 
   * Method to reduce image colors according to color list
   * created by Delos for PS1 on 1/20/13
   * @param colors List of colors to modify image with
   * @return the new modified picture
   */
  public Picture mapToColorList(ArrayList<Color> colors){
	  
    // first make a copy of the picture
    Pixel currPixel = null;
    Pixel targetPixel = null;
    Picture target = 
      new Picture(this.getWidth(),this.getHeight()); // target is a pic with same width and height
    //e.g. (new Picture(10,20)).explore(); all the pixels will be white
    
    // loop through X's 
    for (int srcX = 0, trgX = 0;
    		srcX < getWidth();
    		srcX++, trgX++){
    	// loop through Y's
    	for (int srcY = 0, trgY = 0;
    			srcY < getHeight();
    			srcY++, trgY++){

    		// grab the currPixel at the X,Y location
    		currPixel = this.getPixel(srcX, srcY);
    		targetPixel = target.getPixel(srcX, srcY);
    		
    		// find the closest color to pixel
    		Color closestColor = closestColor(currPixel, colors);
    		targetPixel.setColor(closestColor); // set pixel color to related in list
    		
    	}
    }
    	return target;
  }
  
  /** 
   * Method to compute list of colors using k-means algo
   * created by Delos for PS1 on 1/20/13
   * @param number desired number of clusters
   * @return returns a converged color list after reclustering iterations
   */
  public ArrayList<Color> computeColors(int number){
	  ArrayList<Color> init = new ArrayList<Color>(); // initial centroids
	  ArrayList<Color> colorListFinal = new ArrayList<Color>(); // final centroids to be returned after clustering
	  
	  // a) Random Color List
//	    for (int i = 0; i < number; i++){
//		    init.add(randomColorValue());
//	    }
	  
//	   b) First k unique Colors
	  Pixel [] pixelArray = this.getPixels();
	  for (Pixel pixel : pixelArray) {
		  Color pixelColor = pixel.getColor();
		  
		  // check for duplicate
		  if (!init.contains(pixelColor)){
			  init.add(pixelColor);
		  } else {
			  // if duplicate, then skip to next pixel
			  continue;
		  }
		  
		  // did it hit our alloted color length? then stop.
		  if (init.size() == number){
			  break;
		  }
	  }
	  
	  // Use init list as beginning centroids
	  ArrayList<Color> current = init;
	  
	  colorListFinal = recluster(number, current);  // k-means current and return a final..
	  
	  while(!current.equals(colorListFinal)){
		  System.out.println("colorlistfinal "+colorListFinal);
		  
		  current = colorListFinal; // save the list for comparison and recluster 
		  colorListFinal = recluster(number, current);
	  }
	  
	  System.out.println("CONVERGENCE FINAL "+colorListFinal);
	  return colorListFinal;

  }
  
  /** 
   * Method clusters colors into a list and calculates a new color list 
   * by averaging the RGB values
   * written by Delos
   * @param number desired number of clusters
   * @param init initial color list to start clustering with
   * @return reclustered color list based on k-means algo
   */
  public ArrayList<Color> recluster(int number, ArrayList<Color> init){
	  ArrayList<ArrayList<Color>> clusters = new ArrayList<ArrayList<Color>>(); // inner represents Color list in that cluster
	  ArrayList<Color> colorListFinal = new ArrayList<Color>(); // returns the reclustered list of colors
	  
	  // add appropriate # of empty ArrayList<Color> lists to clusters... 
	  for (int i = 0; i < number; i++){ 
		  clusters.add(new ArrayList<Color>());
	  }
	  
	  // iterate and cluster through each pixel
	  Pixel [] pixelArray = this.getPixels();
	  for (Pixel pixel : pixelArray) {
		  Color pixelColor = pixel.getColor();
		  Color closestColor = closestColor(pixel, init); // pixel's closest color in the list
		  
		  // find the index of the color in the list
		  int closestColorIndex = init.indexOf(closestColor);
		  
		  // add the pixel color into the index of the cluster.
		  clusters.get(closestColorIndex).add(pixelColor);
	  } 
	  
	  // now that we've sorted into clusters,
	  // we need to average out the R G B and return a new list of centroids
	  
	  int redSum = 0;
	  int greenSum = 0;
	  int blueSum = 0;
	  
	  for (ArrayList<Color> cluster : clusters){
		  // remove empty clusters, cannot modify while looping
		  if (cluster.size() == 0 ){
			  continue;
		  }
		  
		  for (Color color : cluster){
			  redSum = redSum + color.getRed();
			  greenSum = greenSum + color.getGreen();
			  blueSum = blueSum + color.getBlue();
		  }
		  
		  redSum = redSum / cluster.size();
		  greenSum = greenSum / cluster.size();
		  blueSum = blueSum / cluster.size();
		  
		  Color newCentroid = new Color(redSum, greenSum, blueSum);
		  
		  colorListFinal.add(newCentroid);
		  
		  // reset values after averaging
		  redSum = 0;
		  greenSum = 0;
		  blueSum = 0;
	  }
	  
	  return colorListFinal;
  }
  
  
  /** 
   * Method calls compute Colors and feeds it into mapToColorList
   * written by Delos
   * @param number desired number of clusters
   */
  public void reduceColors(int number){
	  mapToColorList(computeColors(number)).explore();
  }
  
  public static void main(String[] args) {
	// show original image
    Picture p = 
      new Picture(FileChooser.pickAFile());
    
    
    // pre-selected color list 
    ArrayList<Color> colorList = new ArrayList<Color>();
    colorList.add(Color.red);
    colorList.add(Color.green);
    colorList.add(Color.blue);
    colorList.add(Color.cyan);
    colorList.add(Color.orange);
    colorList.add(Color.yellow);
    colorList.add(Color.black);
    colorList.add(Color.white);
    
    p.mapToColorList(colorList).explore(); // color will be mapped to 8 pre selected colors
    
    
    // hand-picked color list
    ArrayList<Color> handColorList = new ArrayList<Color>();
    Color c1 = new Color(160, 220, 220);
    Color c2 = new Color(230, 180, 170);
    Color c3 = new Color(32, 40, 16);
    Color c5 = new Color(0, 0, 0);
    Color c6 = new Color(100, 144, 144);
    Color c7 = new Color(26, 35, 4);
    Color c8 = new Color(231, 218, 186);
    
    handColorList.add(c1);
    handColorList.add(c2);
    handColorList.add(c3);
    handColorList.add(Color.white);
    handColorList.add(c5);
    handColorList.add(c6);
    handColorList.add(c7);
    handColorList.add(c8);
    
    p.mapToColorList(handColorList).explore(); // color will be mapped to the 8 hand-picked colors
    
    // random color lists for mapToColorList
    ArrayList<Color> random8ColorList = new ArrayList<Color>();
    ArrayList<Color> random256ColorList = new ArrayList<Color>();
    
    // 8 colors random
    for (int i=0; i < 8; i++){
    	random8ColorList.add(randomColorValue());
    }
    p.mapToColorList(random8ColorList).explore();
    
    // 256 colors random
    for (int i=0; i < 256; i++){
    	random256ColorList.add(randomColorValue());
    }
    p.mapToColorList(random256ColorList).explore();
    
    // Test the k-means
    // Note: to test random and first k unique colors, uncomment code in computeColors method.
    p.reduceColors(256);
    
  }
} 
 