import java.util.ArrayList;

public class Node<T> {

	private ArrayList<Node> children;
	private T value;
	private Boolean isAWord;
	private int ALPHABET_SIZE =26;
	
	public Node() {
		children = new ArrayList<Node>(ALPHABET_SIZE);
	}
		
	public Node(int alphabetSize){
		ALPHABET_SIZE = alphabetSize;
		children = new ArrayList<Node>(ALPHABET_SIZE);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public T getValue() {
		return value;
	}
}
