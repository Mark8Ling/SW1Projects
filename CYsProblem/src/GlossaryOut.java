import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to test static methods {@code generateElements} and
 * {@code nextWordOrSeparator}.
 *
 * @author Mark Ling
 *
 */
public final class GlossaryOut {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private GlossaryOut() {
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int currentIndex = position;
        boolean isSeparator = separators.contains(text.charAt(position));

        while (currentIndex < text.length() && separators
                .contains(text.charAt(currentIndex)) == isSeparator) {

            currentIndex++;
        }
        String result = text.substring(position, currentIndex);
        if (isSeparator) {
            if (currentIndex < text.length()
                    && separators.contains(text.charAt(currentIndex))) {
                currentIndex++;
            }
        }
        return result;
    }

    /**
     * Creates the header for the base index file.
     *
     * @param file
     *            The html to output the header to
     */
    public static void createHomepage(SimpleWriter file) {
        file.println("<!DOCTYPE html>");

        file.println("<html>\n" + "<head>\n" + "<title>Glossary</title>\n"
                + "</head>\n" + "<body>\n" + "<h2>Glossary</h2>\n" + "<hr />\n"
                + "<h3>Index</h3>\n");
    }

    /**
     * Creates the header for the base index file.
     *
     * @param file
     *            The html to output the closing to.
     */

    private static void closeHomepage(SimpleWriter file) {
        file.println("</ul>\n" + "</body>\n" + "</html>");
    }

    /**
     * Creates an html page for each word.
     *
     * @param def
     *            The definition of the word
     * @param word
     *            The word for which the page is being made for.
     * @param file
     *            The input file stream.
     */
    public static void createWordPage(String word, String def,
            SimpleWriter file) {
        file.println("<html>\n" + "<head>\n" + "<title>" + word + "</title>\n");
        file.println("</head>\n" + "<body>\n" + "<h2><b><i><font color=\"red\">"
                + word + "</font></i></b></h2>");
        file.println("<blockquote>" + def + "</blockquote>");
        file.println("<hr />");
        file.println("<p>Return to <a href=\"index.html\">index</a>.</p>\n"
                + "</body>\n" + "</html>");
    }

    /**
     * Gets the words from the input and puts them in a Queue.
     *
     * @param words
     *            the queue for the definitions to be stored
     * @param ifile
     *            The input file stream.
     */
    public static void getWords(Set<String> words, SimpleReader ifile) {

        while (!ifile.atEOS()) {
            String test = ifile.nextLine();
            if (!(test.contains(" ") && test.isEmpty())) {
                words.add(test);
            }
        }
    }

    /**
     * Gets the definitions for words from the input and puts them in a Queue.
     *
     * @param def
     *            the queue for the definitions to be stored
     * @param ifile
     *            The input file stream.
     */
    public static void getDefinitions(Set<String> def, SimpleReader ifile) {

        while (!ifile.atEOS()) {

            String definition = "";
            String test = ifile.nextLine();

            while (!test.equals("")) {

                if (test.contains(" ")) {

                    test = (test + " ");

                }
                if (ifile.atEOS()) {
                    test = "";
                } else {
                    test = ifile.nextLine();
                }
            }
            definition = test;
            def.add(definition);
        }
    }

    /**
     * Makes the list in the index page.
     *
     * @param file
     *            The output stream writing to the index page.
     * @param words
     *            The queue of words.
     * @return returns a file of the list to add to the folder package
     */
    public static SimpleWriter createList(SimpleWriter file,
            Set<String> words) {

        file.println("<ul>");

        String word = "";
        while (words.size() != 0) {
            word = words.removeAny();
            file.println(
                    "<li><a href =" + word + ".html>" + word + "</a></li>");
        }
        SimpleWriter world = new SimpleWriter1L(word + ".html");
        return world;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("Enter your input file");
        String ifile = in.nextLine();
        SimpleReader inputFile = new SimpleReader1L(ifile);

        out.println("Enter the name of the folder you want your output file");
        String ofile = in.nextLine();
        SimpleWriter outputFile = new SimpleWriter1L(ofile);

        createHomepage(outputFile);

        Set<Character> seperator = new Set1L<>();
        seperator.add(' ');
        seperator.add('\n');

        String word = "";
        String def = "";

        Set<String> defSet = new Set1L<>();
        Set<String> wordSet = new Set1L<>();

        for (int i = 0; i > seperator.size(); i++) {
            Character sep = seperator.removeAny();
            if (sep.equals('\n')) {
                getDefinitions(defSet, inputFile);
            } else {
                getWords(wordSet, inputFile);
            }

        }

        for (int i = 0; i > wordSet.size(); i++) {
            word = nextWordOrSeparator(word, i, seperator);
        }

        for (int i = 0; i > defSet.size(); i++) {
            def = nextWordOrSeparator(def, i, seperator);
        }

        for (int i = 0; i > wordSet.size(); i++) {
            SimpleWriter wordIndex = new SimpleWriter1L();
            wordIndex = createList(outputFile, wordSet);
            createWordPage(word, def, wordIndex);
            wordIndex.close();
        }

        closeHomepage(outputFile);

        outputFile.close();
        inputFile.close();
        in.close();
        out.close();
    }
}
