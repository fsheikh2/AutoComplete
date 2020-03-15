import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<String> WORD_BANK = new ArrayList<String>();
		initWordBank(WORD_BANK);
		
		Trie trie = new Trie();
		
		for(String word : WORD_BANK)
			trie.setWord(word);
		
		System.out.println("Hello world!\n\n");
		
		ArrayList<String> wordList = trie.getWordList();
		if(wordList.size() == 0)
			System.out.println("\n\n The Trie Contains No Words :\\ \n\n");
		
		for(String word : wordList)
			System.out.println(word);
		
		
		ArrayList<String> suggestions = trie.getSuggestions("Pe");
		if(suggestions.size() == 0)
			System.out.println("\n\n No suggestions to show :/ \n\n");
		for(String suggestion : suggestions)
			System.out.println(suggestion + "\n");
		
		

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
