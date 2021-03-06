import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


class TextContainer extends JPanel{
	private static final long serialVersionUID = 1L;

	private int m_width;
    private int m_height;
    private AttributedCharacterIterator m_iterator;
    private int m_start;
    private int m_end;
    
    public TextContainer(String text, int width, int height, Font font){
        m_width = width;
        m_height = height;

        AttributedString styledText = new AttributedString(text);
        styledText.addAttribute(TextAttribute.FONT, font);
        m_iterator = styledText.getIterator();
        m_start = m_iterator.getBeginIndex();
        m_end = m_iterator.getEndIndex();
    }


    public void rectanglePain(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        FontRenderContext frc = g2.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(m_iterator, frc);
        measurer.setPosition(m_start);

        float x = 0, y = 0;
        while (measurer.getPosition() < m_end)
        {
            TextLayout layout = measurer.nextLayout(m_width);

            y += layout.getAscent();
            float dx = layout.isLeftToRight() ?
                    0 : m_width - layout.getAdvance();

            layout.draw(g2, x + dx, y);
            y += layout.getDescent() + layout.getLeading();
        }
    }
    
    public void circlePain(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        FontRenderContext frc = g2.getFontRenderContext();

        LineBreakMeasurer measurer = new LineBreakMeasurer(m_iterator, frc);
        measurer.setPosition(m_start);

        float y = 0;
        while (measurer.getPosition() < m_end)
        {
            double ix = Math.sqrt((m_width / 2 - y) * y);
            float x = m_width / 2.0F - (float) ix;
            int width = (int) ix * 2;

            TextLayout layout = measurer.nextLayout(width);

            y += layout.getAscent();
            float dx = layout.isLeftToRight() ?
                    0 : width - layout.getAdvance();

            layout.draw(g2, x + dx, y);
            y += layout.getDescent() + layout.getLeading();
        }
    }   
    
    
    public static void main(String[] args) throws IOException{
    	String text = "A NOISELESS, patient spider," +  
    				"I mark�d, where, on a little promontory, it stood, isolated; Mark�d how, to explore the vacant, vast surrounding, It launch�d forth filament, filament, filament, out of itself; Ever unreeling them�ever tirelessly speeding them.";
    	
    	int width = 800;
    	int height = 1000;
    	Font font = new Font("Segoe UI Light", Font.PLAIN, 48);

    	BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = bufferedImage.createGraphics();
	    g2d.setColor(Color.black);
	    g2d.fillRect(0, 0, width, height);
	    
	    
	    g2d.setColor(Color.WHITE);
	    
    	TextContainer container = new TextContainer(text, width, height, font);
	    container.circlePain(g2d);
	    g2d.dispose();
	    
	    File file = new File("quote.jpg");
	    ImageIO.write(bufferedImage, "jpg", file);
    }
	    
	    
}