import dataStructures.AutoCompleteDictionaryTrie;

public final class Main {


    public static void main(String[] args) {




        AutoCompleteDictionaryTrie trie = new AutoCompleteDictionaryTrie();



        // Generates a list of completions.
        System.out.println(trie.produceCompletions("hel", 10).toString());
        // Checks whether it's a proper word.
        System.out.println(trie.isWord("hel"));


        /*
         * Output:
         * [help, held, hell, helm, helps, helen, helga, helix, hells, hello]
         * false
         */



    }


}
