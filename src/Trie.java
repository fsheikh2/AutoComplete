import java.util.ArrayList;
import java.util.Stack;

public class Trie<T> {//extends Node {
	
	Node root;
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
		root = new Node();
		root.setValue(-1);
	}
	
	public void setWord(String word) {
		int value = 1;
		Node currentNode = root;
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
	
	// save prefixes separately from the rest of the word. Clear out prefixes when you bounce back from a leaf node
	public ArrayList<String> getWordList(){
		
		Stack<Node> s = new Stack<Node>();
		ArrayList<String> wordList = new ArrayList<String>();
		Node currentNode = root;
		String word = "";
		s.push(currentNode);
		
		while(!s.isEmpty()) {
			
			currentNode = s.peek();
			s.pop();
			word += Character.toString(currentNode.getChar());
			
			for(int i = 0; i < ENGLISH_ALPHABET_SIZE; i++) {
				if(currentNode.getNode(i) != null) {
					s.push(currentNode.getNode(i));
				}
			}
			if(currentNode.isWord() ) { 
				wordList.add(word);
				word = word.substring(0, s.size()+1); // the size of the stack tells us the size of the "prefix"
			}
		}
		return wordList;
	}
	
	public ArrayList<String> getSuggestions(String input){
		int CUR_IDX = 0;
		input = input.toLowerCase();
		ArrayList<String> suggestionList = new ArrayList<String>();
		char curLetter;
		int curLetterIdx;
		
		// take the first character in the input string and grab the corresponding child node from the root
		// then check which nodes of that child node have a non-null value and follow each one till we hit 
		// a null node. Along the way check if the new node we've arrived at is a word, if so, add it to the 
		// array list we'll return at the end.
		
		
		// OR, go down a path on the trie that represents the word. Then go down all possible paths to come up 
		// with suggestions.
		
		Node curNode = root;
		int endIdx = 0;
		// this loop will traverse down the path in the tree that matches the input
		// Q: What happens if there is no match for the whole word?
		for(int i = 0; i < input.length(); i++) {
			
			curLetter = input.charAt(i);
			curLetterIdx = curLetter - ASCII_DIFF;
			endIdx = i;
			if(curNode.getNode(curLetterIdx) == null)
				break;
			else
				curNode = curNode.getNode(curLetterIdx);
		}
		
		String suggestion = input.substring(0, endIdx);
		if(curNode.isWord()) {
			suggestion += curNode.getChar();
			suggestionList.add(suggestion);
		}
		
		// At this point curNode holds the node that marks the end of the input word
		// Should I use Depth First Search here? Maybe with a limit related to how many suggestions we want to display?
		Stack<Node> s = new Stack<Node>();
		String subSuggestion = suggestion;
		s.push(curNode);
		while(!s.isEmpty()) {
			Node n = s.peek();
			subSuggestion = suggestion;
			s.pop();
			for(int i = 0; i < ENGLISH_ALPHABET_SIZE; i++) {
				if(n.getNode(i) != null) {
					
					s.push(n.getNode(i));
					
					if(n.isWord())
						suggestionList.add(subSuggestion); // have to keep track of what string has been formed
				}
			}
		}
		
		return suggestionList;
	}

}