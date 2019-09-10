package dataStructures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * This is an implementation of a trie.
 * This particular trie is used to perform spell-check and auto-completion of english words.
 *
 * @author Anton Hrytsyk
 */
public final class AutoCompleteDictionaryTrie {

    /**
     * Represents a TrieNode.
     * @author Anton Hrytsyk
     */
    private class Node {


        /**
         * Stores references to child nodes.
         */
         Map<Character, Node> children;

        /**
         * Stores text on the trie.
         */
        String text;

        /**
         * Keeps track of whether this trie marks the end of a word
         */
        boolean isEndOfWord;

        /**
         * Default Constructor
         *
         * Creates a new node with an empty text.
         */
        Node(){
            this("");
        }

        /**
         * Creates a node with the specified text.
         * @param text data to be stored inside the node.
         */
        Node(String text) {

            this.text = text;
            this.isEndOfWord = false;
            this.children = new HashMap<>();
        }

        /**
         * Accesses the <code> children HashMap </code> of the current node and returns a specific Node.
         * @param data letter that needs to be accessed
         * @return a child that is associated with the specific character
         */
        Node getChild(Character data) {
            return children.get(data);
        }

        /**
         * Return the set of characters that have links from this node
         * @return set of characters that have links from this node
         */
        Set<Character> getNextValidCharacters() {
            return this.children.keySet();
        }


        /**
         * Adds a child to an existing node.
         * @param data letter to which the current node will now link.
         * @return inserted Node.
         */
        Node addChild(Character data) {


            Node node = new Node(text + data.toString());
            this.children.put(data, node);

            return node;

        }


    }

    /**
     * Pointer to the root node.
     */
    private Node root;
    /**
     * Keeps track of the number of words
     */
    private int size;

    /**
     * Default Constructor
     * Creates a Trie and initializes it with a default dictionary.
     */
    public AutoCompleteDictionaryTrie() {

        this("src/data/dictionary.txt");

    }

    /**
     * Creates a trie and initializes it with a specified dictionary.
     * @param dictionary
     */
    public AutoCompleteDictionaryTrie(String dictionary) {

        root = new Node();
        size = 0;
        loadDictionary(dictionary);

    }


    /**
     * Adds a new word to the dictionary.
     * @param text word that will now be stored in the trie.
     * @return <code> true  </code> if new word was added.
     *         <code> false </code> if the word is already in the dictionary.
     */
    public boolean addWord(String text) {
        text = text.toLowerCase();
        boolean isNewWord = false;
        Node current = root;

        for (char c:text.toCharArray()) {

            if (current.getChild(c) == null) {
                current = current.addChild(c);
                isNewWord = true;
            }
            else current = current.getChild(c);

        }


        if (!current.isEndOfWord) {
            current.isEndOfWord = true;
            size++;
        }


        return isNewWord;
    }


    /**
     * Checks whether parameter is a valid word in the trie.
     * @param text text to check.
     * @return <code> true  </code> if trie already stores the parameter.
     *         <code> false </code> if trie doesn't store  the parameter.
     */
    public boolean isWord(String text) {

        Node current = root;
        text = text.toLowerCase();

        for (Character c : text.toCharArray()) {

            current = current.getChild(c);

            if (current.getChild(c) == null) return false;
        }

        return true;
    }


    /**
     * Auto-Complete algorithm.
     * Generates a specified number of completions.
     *
     * First checks whether the parameter can potentially be a valid word.
     * If yes - performs a level-order traversal of the trie and returns a list of a specific size that contains possible completions to the word.
     * If no - returns an empty list.
     *
     * @param text - word that needs to be auto-completed.
     * @param numOfCompletions - number of completions that needs to be generated
     * @return list with a specified number of completions.
     */
    public List<String> produceCompletions(String text, int numOfCompletions) {

        List<String> completions = new LinkedList<>();
        Node current = root;
        text = text.toLowerCase();

        for (Character c : text.toCharArray()) {

            if (current.getChild(c) == null) {
                return completions;
            }
            current = current.getChild(c);

        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(current);

        while (!queue.isEmpty() && completions.size() < numOfCompletions) {

            current = queue.remove();
            if (current.isEndOfWord) completions.add(current.text);

            for (Character c : current.getNextValidCharacters()) {
                queue.add(current.getChild(c));
            }


        }

        return completions;


    }


    /**
     * Returns the number of words in the trie
     * @return number of words in the trie
     */
    public int size() {
        return size;
    }


    /**
     * Loads the dictionary from a specified locations.
     * @param path - a String containing the path to the dictionary.
     */
    private void loadDictionary(String path) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String nextWord;
            while ( (nextWord = reader.readLine() ) != null )
                this.addWord(nextWord);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
