import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<String> WORD_BANK = new ArrayList<String>();
		initWordBank(WORD_BANK);
		
		Trie trie = new Trie();
		
		for(String word : WORD_BANK)
			trie.setWord(word);
		
		System.out.println("Hello world!\n\n");
		
		
		ArrayList<String> wordList = new ArrayList<String>();
		wordList = trie.getWordList("", trie.getRoot() , wordList);
		
		if(wordList.size() == 0)
			System.out.println("\n\n The Trie Contains No Words :\\ \n\n");
		else {
			for(String word : wordList)
				System.out.println(word);
		}
		
		
		System.out.println("\n\n");
		
		// Words aren't being marked correctly, currently inputting "Pi" only suggests "pickled" and "piper".
		ArrayList<String> suggestions = trie.getSuggestionsList("Pe");
		if(suggestions.size() == 0)
			System.out.println("\n\n No suggestions to show :/ \n\n");
		else {
			for(String suggestion : suggestions)
				System.out.println(suggestion + "\n");
		}
	}
	
	public static void initWordBank(ArrayList<String> wordBank) {
		wordBank.add("Peter");
		wordBank.add("Pecked");
		wordBank.add("Peppers");
		wordBank.add("Picked");
		wordBank.add("Pickled");
		wordBank.add("Piper");
	}

}
