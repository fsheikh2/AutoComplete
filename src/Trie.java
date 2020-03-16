import java.util.ArrayList;
import java.util.Stack;

public class Trie<T> {
	
	private Node mRoot;
	private static final int ENGLISH_ALPHABET_SIZE = 26;
	private static final int ASCII_DIFF = 97; // only works with lower case letters
	
	private static class Node{
		private int mValue;
		private ArrayList<Node> mChildren;
		private boolean mIsWord;
		private char mC;
		
		public Node() {
			
			mValue = -1;
	
			mChildren = new ArrayList();
			for(int i = 0; i < ENGLISH_ALPHABET_SIZE; i++)
				mChildren.add(null);
			
	 		mIsWord = false;
	 		mC = 0;
		}
		
		public Node(char c) {
			mValue = -1;
			
			mChildren = new ArrayList();
			for(int i = 0; i < ENGLISH_ALPHABET_SIZE; i++)
				mChildren.add(null);
			
	 		mIsWord = false;
	 		mC = c;
			
		}
		
		public void setValue(int value) {
			mValue = value;
		}
		
		public int getValue() {
			return mValue;
		}
		
		public void setIsWord(boolean isWord) {
			mIsWord = isWord;
		}
		
		public boolean isWord() {
			return mIsWord;
		}
		
		public void setChildNode(int nodeIdx, Node node) {
			this.mChildren.set(nodeIdx, node);
		}
		
		public Node getNode(int index) {
			return mChildren.get(index);
		}
		
		public char getChar() {
			return mC;
		}
		
		public static int charToIdx(char c) {
			return  (int) (c-ASCII_DIFF);
		}
		
		public static char idxToChar(int idx) {
			return (char) (idx+ASCII_DIFF);
		}
				
	} // end Node class

	
	public Trie(){
		mRoot = new Node();
		mRoot.setValue(-1);
	}
	
	public Node getRoot() {
		return mRoot;
	}
	
	public void setWord(String word) {
		int value = 1;
		Node currentNode = mRoot;
		word = word.toLowerCase();
		Node childNode;
		for(int i = 0; i < word.length(); i++) {
			int index =  Node.charToIdx(word.charAt(i)); //word.charAt(i)-ASCII_DIFF;
			childNode = currentNode.getNode(index);
			if(childNode == null)
				childNode = new Node(word.charAt(i));
			if(childNode.getValue() != -1)
				childNode.setValue(childNode.getValue()+1);
			else
				childNode.setValue(value); // update the current nodes list to mark this index as being part of a word
			currentNode.setChildNode(index, childNode);
			currentNode = childNode; // set the current node to be the child node
		}
		currentNode.setIsWord(true); // as we reach the end of the path that represents the given word, we mark the node as a word
	}
	
	
	public ArrayList<String> getWordList(String prefix, Node root, ArrayList<String> wordList){
		Node currentNode;
		for(int i = 0; i < ENGLISH_ALPHABET_SIZE; i++) {
			currentNode = root.getNode(i);
			if(currentNode != null) {
				if(currentNode.isWord()) {
					wordList.add(prefix+currentNode.getChar());
					getWordList(prefix+currentNode.getChar(), currentNode, wordList); // check if this word itself is a prefix for other words
				}
				else
					getWordList(prefix+currentNode.getChar(), currentNode, wordList);
			}
		}
		return wordList;
	}
	
	public ArrayList<String> getSuggestionsList(String input){
		input = input.toLowerCase();
		ArrayList<String> suggestionList = new ArrayList<String>();
		
		Node currentNode = mRoot;
		Node childNode = null;
		
		// First reach the node which marks the end of the path representing the input string.
		for(int i = 0; i < input.length(); i++) {
			childNode = currentNode.getNode( Node.charToIdx(input.charAt(i)) );
			if(childNode != null)
				currentNode = childNode;
			else {
				// in case there's only a partial match, search the subsection of the trie that matches the partial
				input = input.substring(0, i);
				break;
			}
		}
		
		// From here currentNode holds the node which acts as the root of all paths which represent words which are appropriate suggestions for the input
		if(childNode != null)
			suggestionList = getWordList(input, childNode, suggestionList);
		
		return suggestionList;
	}
}