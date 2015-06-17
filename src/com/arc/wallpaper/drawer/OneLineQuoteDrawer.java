package com.arc.wallpaper.drawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class OneLineQuoteDrawer implements QuoteQrawer{
	public static enum Style{FIT, CENTER};
	
	private final Font font;
	private final Color color;
	private final Rectangle area;
	
	public OneLineQuoteDrawer(Rectangle area, Font font, Color color){
		this.font = font;
		this.color = color;
		this.area = area;
	}

	@Override
	public void draw(Graphics2D g, String text) {
		Point startPoint = computerStartPoint(g, font, text);
		
		int startY = startPoint.y;
		int startX = startPoint.x;
		
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, startX, startY);
	}

	private Point computerStartPoint(Graphics2D g, Font font, String text){
    	FontMetrics fm = g.getFontMetrics(font);
    	int textWidth = fm.stringWidth(text);
    	
    	int x = (area.width - textWidth) / 2;
    	int y = area.height / 2;
    	
    	return new Point(x, y);
    }
	

}
