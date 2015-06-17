
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.arc.wallpaper.drawer.OneLineQuoteDrawer;
import com.arc.wallpaper.drawer.QuoteQrawer;
import com.arc.wallpaper.drawer.RectangleQuoteDrawer;

public class QuoteWallpaperGenerator {
	private int width = 1920;
    private int height = 1200;
    
    private List<Font> availableFonts = new ArrayList<>();
    private RandomUtil random = new RandomUtil();
    
    public QuoteWallpaperGenerator(){
    	//Use desktop screen size as picture size 
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();    	
    	this.width = screenSize.width;
    	this.height = screenSize.height;
    }
    
    public void generate(Quote quote, QuoteQrawer quoteDrawer, String pathToSave) throws IOException{
	    // Constructs a BufferedImage of one of the predefined image types.
	    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	    // Create a graphics which can be used to draw into the buffered image
	    Graphics2D g2d = bufferedImage.createGraphics();

	    // fill all the image with white
	    g2d.setColor(Color.black);
	    g2d.fillRect(0, 0, width, height);


	    quoteDrawer.draw(g2d, quote.content);
	    
	    // Disposes of this graphics context and releases any system resources that it is using. 
	    g2d.dispose();

	    // Save as JPEG
	    File file = new File(pathToSave);
	    ImageIO.write(bufferedImage, "jpg", file);
    }
    
    
    private Font randomPickFont(){
    	int index = random.randomInt(0, availableFonts.size()); 
    	return availableFonts.get(index);
    }
    
    private void initializeFontList(){
    	availableFonts.add(new Font("Segoe UI Light", Font.PLAIN, 80));
    	availableFonts.add(new Font("Calibri Light", Font.PLAIN, 80));
    	//availableFonts.add(new Font("Segoe UI Light", Font.PLAIN, 80));
    	//availableFonts.add(new Font("Calibri Light", Font.BOLD, 80));
    }
    
    private Point computerStartPoint(BufferedImage bi, Font font, String text){
    	FontMetrics fm = bi.getGraphics().getFontMetrics(font);
    	int textWidth = fm.stringWidth(text);
    	
    	int x = (width - textWidth) / 2;
    	int y = height / 2;
    	
    	return new Point(x, y);
    }
    
    private Font computerFont(BufferedImage bi, int start, int end, String text){
    	Font font = availableFonts.get(random.randomInt(0, availableFonts.size() - 1));
    	int width = end - start;

    	FontMetrics fm = bi.getGraphics().getFontMetrics(font);
    	int textWidth = fm.stringWidth(text);
    	
    	if(textWidth > width){
    		int lastFontSize = font.getSize();
    		while(textWidth > width){
    			lastFontSize = lastFontSize - 1;
    			font = new Font(font.getName(), font.getStyle(), lastFontSize);
    	    	fm = bi.getGraphics().getFontMetrics(font);
    	    	textWidth = fm.stringWidth(text);
    		}
    		
    		font = new Font(font.getName(), font.getStyle(), lastFontSize + 1);
    	}
    	else{
    		int lastFontSize = font.getSize();
    		while(textWidth < width){
    			lastFontSize = lastFontSize + 1;
    			font = new Font(font.getName(), font.getStyle(), lastFontSize);
    	    	fm = bi.getGraphics().getFontMetrics(font);
    	    	textWidth = fm.stringWidth(text);
    		}
    		
    		font = new Font(font.getName(), font.getStyle(), lastFontSize  - 1);
    		
    	}
    	
    	return font;
    }
    
    
	public static void main(String[] args) throws IOException {
		Quote quote = new Quote("The best of men cannot suspend their fate. The good die early, and the bad die late.", "Daniel Defoe", null);
		QuoteQrawer quoteDrawer1 = new RectangleQuoteDrawer(new Rectangle(100, 100, 800, 1000), new Font("Segoe UI Light", Font.PLAIN, 48), Color.WHITE);
		OneLineQuoteDrawer quoteDrawer2 = new OneLineQuoteDrawer(new Rectangle(100, 100, 800, 1000), new Font("Segoe UI Light", Font.PLAIN, 48), Color.WHITE);
		
		QuoteWallpaperGenerator generator = new QuoteWallpaperGenerator();
		generator.generate(quote, quoteDrawer1, "quote1.jpg");
		generator.generate(quote, quoteDrawer2, "quote2.jpg");
	}

}
