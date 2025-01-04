import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    @Test
    //1
    public void testOverlap() {
        assertEquals(2, StringReassembly.overlap("asd", "sdf"));
        assertEquals(0, StringReassembly.overlap("asd", "fgh"));
    }

    @Test
    //2
    public void testCombination() {
        assertEquals("asdfg", StringReassembly.combination("asd", "dfg",
                StringReassembly.overlap("asd", "dfg")));
    }

    @Test
    //3
    public void testAddToSetAvoidingSubstrings() {
        Set<String> test = new Set1L<>();
        test.add("asd");
        test.add("dfg");
        StringReassembly.addToSetAvoidingSubstrings(test, "asd");
        assertFalse(test.contains("asd"));
        assertTrue(test.contains("dfg"));
    }

    @Test
    //4
    public void testLinesFromInput() {
        //HOW TO TEST THIS
        //Need to hard code
        SimpleReader test = new SimpleReader1L(
                "/Users/markling/Downloads/workspace-2/StringReassemblyFromFragments/data/text.txt");
        Set<String> result = StringReassembly.linesFromInput(test);
        assertTrue(result.contains("abc"));
        assertTrue(result.contains("def"));
        assertFalse(result.contains("bcd"));
        assertFalse(result.contains("xyz"));
    }

    @Test
    //5
    public void testAssemble() {
        Set<String> test = new Set1L<>();
        test.add("asd");
        test.add("sdf");
        test.add("dfg");
        StringReassembly.assemble(test);
        assertEquals(1, test.size());
        assertTrue(test.contains("asdfg"));
    }

    @Test
    //6
    public void testPrintWithLineSeparators() {
        //WHAT
        SimpleWriter out = new SimpleWriter1L();
        StringReassembly.printWithLineSeparators("abc~def", out);
        String test = out.toString();
        assertEquals("(true,\"\",[contents])", test);
    }
}
