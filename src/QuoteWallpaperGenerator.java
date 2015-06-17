
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class QuoteWallpaperGenerator {
	private int width = 1920;
    private int height = 1200;
    private int margin = 500;
    
    private List<Font> availableFonts = new ArrayList<>();
    private RandomUtil random = new RandomUtil();
    private final QuoteGenerator quoteGen;
    private ColorGenerator colorGen = new ColorGenerator();
    
    public QuoteWallpaperGenerator(QuoteGenerator quoteGen){
    	this.quoteGen = quoteGen;
    	//Use desktop screen size as picture size 
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();    	
    	this.width = screenSize.width;
    	this.height = screenSize.height;
    	
    	initializeFontList();
    }
    
    public void random(String pathToSave) throws IOException{
    	Quote quote = quoteGen.random();
    	System.out.println("Use Quote:" + quote);
    	generate(quote, pathToSave);
    }
    public void generate(Quote quote, String pathToSave) throws IOException{
	    // Constructs a BufferedImage of one of the predefined image types.
	    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	    // Create a graphics which can be used to draw into the buffered image
	    Graphics2D g2d = bufferedImage.createGraphics();

	    // fill all the image with white
	    g2d.setColor(Color.black);
	    g2d.fillRect(0, 0, width, height);


	    g2d.setColor(colorGen.random());
	    
	    Font font = computerFont(bufferedImage, margin, width - margin, quote.content);
	    
	    drawQuote(quote, g2d, font, bufferedImage);
	    
	    //g2d.setFont(font);	    
	    //Point startPoint = computerStartPoint(bufferedImage, font, quote.content);
	    //g2d.drawString(quote.content, startPoint.x, startPoint.y);

	    // Disposes of this graphics context and releases any system resources that it is using. 
	    g2d.dispose();

	    // Save as PNG
	    //File file = new File("myimage.png");
	    //ImageIO.write(bufferedImage, "png", file);

	    // Save as JPEG
	    File file = new File(pathToSave);
	    ImageIO.write(bufferedImage, "jpg", file);
    }
    
    private void drawQuote(Quote quote, Graphics2D g2d, Font font, BufferedImage bi){
	    
    	g2d.setFont(font);
	    
	    Point startPoint = computerStartPoint(bi, font, quote.content);
	    g2d.drawString(quote.content, startPoint.x, startPoint.y);
	    
	    
	    if(quote.author != null){
		    Rectangle2D rec = font.getStringBounds(quote.content, g2d.getFontRenderContext());
		    double height = rec.getHeight();
		    
		    double lineGap = height / 2;
		    
		    font = new Font(font.getName(), Font.PLAIN, font.getSize() / 2);
		    startPoint = computerStartPoint(bi, font, quote.author);
		    g2d.setFont(font);
		    g2d.drawString(quote.author, startPoint.x, (int)(startPoint.y + height + lineGap));
	    	
	    }
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
		QuoteGenerator quoteGen = new QuoteGenerator();		
		QuoteWallpaperGenerator generator = new QuoteWallpaperGenerator(quoteGen);
		
		List<Quote> quotes = quoteGen.getAllQuotes();
		
		for(int i = 0; i < quotes.size(); i++){
			Quote quote = quotes.get(i);
			
			String fileName = "c:\\jianfei\\quote" + i + ".jpg"; 
			
			generator.generate(quote, fileName);
		}
		
	}

}
