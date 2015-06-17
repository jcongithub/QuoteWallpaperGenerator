import java.util.Random;


public class RandomUtil {
	private Random random = new Random();
	
	public int randomInt(int min, int max){
		return random.nextInt(max - min) + min;
	}
}
