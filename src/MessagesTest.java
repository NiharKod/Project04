import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class MessagesTest {
    private Account sender;
    private Account receiver;
    private Message message;
    private PrintStream originalOut; // Declare the original standard output stream
    private static boolean allTestsPassed = false; // Track whether all tests have passed

    @Before
    public void setUp() {
        // Store the original standard output stream
        originalOut = System.out;

        // Create sender and receiver accounts for testing
        sender = new Account("niharkod", "nkodkani@purdue.edu", "fancybook3!2", "seller");
        receiver = new Account("jdoe", "jdoe@gmail.com", "Password123!", "customer");

        // Create a Message instance
        try {
            message = new Message(sender, receiver.getUsername());
        } catch (CantMessageException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMessage() {
        try {
            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Send a message
            message.sendMessage("Test message");

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the sent message is in the printed output
            assertTrue(printedMessage.contains("Test message"));


            // Clean up
            System.setOut(originalOut); // Restore the original standard output
        } catch (IOException e) {
            fail("Exception occurred while testing sendMessage: " + e.getMessage());
        }
    }

    @Test
    public void testEditMessage() {
        try {
            // Send a message
            message.sendMessage("Original message");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Edit the message
            message.editMessage(1, "Edited message");

            // You can choose to capture the edited message for further assertion
            String editedMessage = outContent.toString().trim();

            // Clean up
            System.setOut(originalOut); // Restore the original standard output

            // Now you can assert the edited message if needed
            assertEquals("(seller)niharkod: Edited message\n-", editedMessage);
        } catch (IOException e) {
            fail("Exception occurred while testing editMessage: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteMessage() {
        try {
            // Send a message
            message.sendMessage("Test message");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Delete the message
            message.deleteMessage(1);

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the message is deleted
            assertFalse(printedMessage.contains("Test message"));
            allTestsPassed = true;

            // Clean up
            System.setOut(originalOut); // Restore the original standard output
        } catch (IOException e) {
            allTestsPassed = false; // Set the variable to false if this test fails
            fail("Exception occurred while testing deleteMessage: " + e.getMessage());
        }
    }

    @Test
    public void testPrintMessageHistory() {
        try {
            // Send messages
            message.sendMessage("Message 1");
            message.sendMessage("Message 2");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Print message history
            message.printMessageHistory();

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the history is correctly printed
            assertTrue(printedMessage.contains("Message 1"));
            assertTrue(printedMessage.contains("Message 2"));

            // Clean up
            System.setOut(originalOut); // Restore the original standard output
        } catch (IOException e) {
            fail("Exception occurred while testing printMessageHistory: " + e.getMessage());
        }
    }

    @Test
    public void testDisplayMostCommonWords() {
        try {
            // Send messages with common words
            message.sendMessage("apple banana apple");
            message.sendMessage("banana cherry apple");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Display most common words
            message.displayMostCommonWords();

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the most common words are correctly displayed
            assertTrue(printedMessage.contains("apple: 3 occurrences"));
            assertFalse(printedMessage.contains("banana: 2 occurrences"));
            assertFalse(printedMessage.contains("cherry:"));


            // Clean up
            System.setOut(originalOut); // Restore the original standard output
        } catch (IOException e) {
            fail("Exception occurred while testing displayMostCommonWords: " + e.getMessage());
        }
    }

    @Test
    public void testDisplayLeastCommonWords() {
        try {
            // Send messages with uncommon words
            message.sendMessage("elephant giraffe zebra");
            message.sendMessage("giraffe kangaroo elephant");

            // Redirect System.out to capture output
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Display least common words
            message.displayLeastCommonWords();

            // Capture the printed message
            String printedMessage = outContent.toString().trim();

            // Assert that the least common words are correctly displayed
            assertTrue(printedMessage.contains("zebra: 1 occurrences"));
            assertTrue(printedMessage.contains("kangaroo: 1 occurrences"));
            assertFalse(printedMessage.contains("giraffe:"));

            // Clean up
            System.setOut(originalOut); // Restore the original standard output
        } catch (IOException e) {
            fail("Exception occurred while testing displayLeastCommonWords: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        // Clean up by deleting the created message history files
        String filenameFrom = sender.getUsername() + "-" + receiver.getUsername() + ".txt";
        String filenameTo = receiver.getUsername() + "-" + sender.getUsername() + ".txt";

        File fileFrom = new File(filenameFrom);
        File fileTo = new File(filenameTo);

        if (fileFrom.exists()) {
            fileFrom.delete();
        }

        if  (fileTo.exists()) {
            fileTo.delete();
        }
    }

}
