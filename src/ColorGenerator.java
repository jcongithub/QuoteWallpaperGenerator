import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class ColorGenerator {
	private List<Color> colors = new ArrayList<Color>();
	private RandomUtil random = new RandomUtil();
	
	public ColorGenerator() {
		colors.add(Color.CYAN);
		colors.add(Color.GREEN);
		colors.add(Color.LIGHT_GRAY);
		colors.add(Color.MAGENTA);
		colors.add(Color.PINK);
		colors.add(Color.RED);
		colors.add(Color.WHITE);
	}
	
	
	public Color random(){
		int index = random.randomInt(0, colors.size());
		return colors.get(index);
	}
}
