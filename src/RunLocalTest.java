import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class RunLocalTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testMainCopyWithValidInput() {
        // Prepare the input that you want to provide to your application (MainCopy)
        String input = "1\nnihar@gmail.com\nnkodkani04\n2\nJohnDoe\n1\nhi\n1\nhi\n4\n9\nBye\n5\n2\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call the main method of your application (MainCopy)
        Main.main(new String[]{});

        // Define the expected output based on your application's behavior
        String expectedOutput = "Log-In [1] or Create New Account [2]?\n" +
                "LOG-IN:\n" +
                "Please Enter your Email:\nPlease Enter your Password:\nWelcome back, Customer niharkod!\n\n" +
                "View Stores List [1], Search for a Seller [2], or Cancel [0]?\nPlease enter a username. Remember that all names are case-sensitive.\n" +
                "Message Seller JohnDoe [1], Block Seller [2], Edit Conversation [3], Delete a Message [4], or Cancel [0],Get statistics [5]\n" +
                "(Seller)JohnDoe: yo nihar wsg\n" +
                "(Seller)JohnDoe: u tryna cop some stuff\n(Seller)JohnDoe: i miss u gangy\n(Seller)JohnDoe: hi\n" +
                "(Seller)JohnDoe: pls answer me :(\n(Customer)niharkod: hi\n(Customer)niharkod: 1\n(Customer)niharkod: hi\n" +
                "Enter Message:\nhi\n(Seller)JohnDoe: yo nihar wsg\n(Seller)JohnDoe: u tryna cop some stuff\n(Seller)JohnDoe: i miss u gangy\n" +
                "(Seller)JohnDoe: hi\n(Seller)JohnDoe: pls answer me :(\n(Customer)niharkod: hi\n(Customer)niharkod: 1\n(Customer)niharkod: hi\n" +
                "(Customer)niharkod: hi\n(Seller)JohnDoe: yo nihar wsg\n(Seller)JohnDoe: u tryna cop some stuff\n(Seller)JohnDoe: i miss u gangy\n" +
                "(Seller)JohnDoe: hi\n(Seller)JohnDoe: pls answer me :(\n(Customer)niharkod: hi\n(Customer)niharkod: 1\n(Customer)niharkod: hi\n" +
                "(Customer)niharkod: hi\n(Seller)JohnDoe: yo nihar wsg\n(Seller)JohnDoe: u tryna cop some stuff\n(Seller)JohnDoe: i miss u gangy\n" +
                "(Seller)JohnDoe: hi\n(Seller)JohnDoe: pls answer me :(\n(Customer)niharkod: hi\n(Customer)niharkod: 1\n(Customer)niharkod: hi\n" +
                "Get Chat History [1], Get most common word occurrences [2]\nMost common words in overall messages:\nhi: 3 occurrences\n" +
                "Message Seller JohnDoe [1], Block Seller [2], Edit Conversation [3], Delete a Message [4], or Cancel [0],Get statistics [5]";

        // Compare the expected output with the actual output captured
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}
