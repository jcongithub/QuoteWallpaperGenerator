public class Quote{
	String content;
	String author;
	String from;
	
	public Quote(String content, String author, String from){
		this.content = content;
		this.author = author;
		this.from = from;
	}
	
	public String toString(){
		return String.format("[%s][%s][%s]", content, author, from);
	}
}
    
