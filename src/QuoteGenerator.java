import java.util.ArrayList;
import java.util.List;


public class QuoteGenerator {
	private RandomUtil random = new RandomUtil();
	private List<Quote> quotes = new ArrayList<>();
	
	public Quote random(){
		int index = random.randomInt(0, quotes.size());
		return quotes.get(index);
	}
	
	public List<Quote> getAllQuotes(){
		return quotes;
	}
	
	
	public QuoteGenerator(){
		quotes.add(new Quote("Things will change if you change first.", "Unknown", null));
		quotes.add(new Quote("The best of men cannot suspend their fate. The good die early, and the bad die late.", "Daniel Defoe", null));
		quotes.add(new Quote("We cannot command nature except by obeying her.", "Francis Bacon", null));
		quotes.add(new Quote("Every man can see things far off but is blind to what is near.", "Sophocles ", null));
		quotes.add(new Quote("Man does not control his own fate. The women in his life do that for him.", "Mehrdad77 ", null));
		
	}
}
