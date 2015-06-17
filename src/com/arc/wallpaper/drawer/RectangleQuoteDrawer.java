package com.arc.wallpaper.drawer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;


public class RectangleQuoteDrawer implements QuoteQrawer{
	private final Rectangle area;
	private final Font font;
	private final Color color;
	
	public RectangleQuoteDrawer(Rectangle area, Font font, Color color){
		this.area = area;
		this.font = font;
		this.color = color;
	}
	@Override
	public void draw(Graphics2D g, String text) {
        AttributedString styledText = new AttributedString(text);
        styledText.addAttribute(TextAttribute.FONT, font);
        
        AttributedCharacterIterator iterator = styledText.getIterator();
        int start = iterator.getBeginIndex();
        int end = iterator.getEndIndex();
        
        FontRenderContext frc = g.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(iterator, frc);
        measurer.setPosition(start);

        float x = area.x, y = area.y;
        
        g.setColor(color);
        
        while (measurer.getPosition() < end){
        	
            TextLayout layout = measurer.nextLayout(area.width);

            y += layout.getAscent();
            
            float dx = layout.isLeftToRight() ? 0 : area.width - layout.getAdvance();

            layout.draw(g, x + dx, y);
            
            y += layout.getDescent() + layout.getLeading();
        }
	}
}
