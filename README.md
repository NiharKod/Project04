# README.md
# Project 04
### Members
- Nihar Kodkani, Isabelle Lee, Nangba Konyak, Sandesh Patlolla

## Compiling and Running
### Compile and run the project through running `javac Main.java && java Main`

## Submissions
### Vocareum : Nihar
### Report : Isabelle



## Class Descriptions

### `Main.java`
- This is the main class where all other classes are ran. 
  - The primary use of this class is to house the main method which includes all of the prompts. 
  - In this class, the user is able to create or login to an existing account. 
  - They can then view a list of customers of stores which they can message depending on if they are a customer or a seller. 
  - They can then start a new/existing message with a user as well as edit and delete a message.
  - All of these options are available via terminal prompts. 
  - Lastly, the seller or the customer is able to view
- This class has been tested via the RunLocal test class and has been tested with example inputs. 
- Almost every class is referenced by the main class. 

### `Account.java`
- The account class is a super class to the customer and seller class. 
  - It houses the main login method which allows a user to create or login to an existing account. 
  - It populates the many instance variables an account may have such as email, username, password, etc. 
  - This class contains methods on blocking and invisibility as well. 
  - The customer list and seller list methods and file io associated with this is written within this class. 
  - Overall, this class is the super class to the Customer and the Seller class which are inheriting the fields and methods of this class. 
- Important Methods:
  - `public Account accountAccess()` Allows the user to login or create an account. Interacts with file stream
  - `public boolean checkFiles(int accountChoices)`  Checks if provided account information is found in accounts.txt
  - `public ArrayList<String> getCustomerList(String filename)` Creates a list of all customers
  - `public ArrayList<String> getCustomerListInvis() throws IOException` calls getCustomerList and checks for invisibility
  - `public ArrayList<String> getSellerList(String filename)` Creates an arrayList of all sellers
  -  `public ArrayList<String> getSellerListInvis()` Gets seller list considering invisibility
  -  `public void writeBlockedByList(String username)` if user1 is on user2's blocked-by list, user2 cannot send a message to user1
  -  `public boolean checkIfBlocked(String username)` Method checks if user has a blocked-by list, and if the person they want to message is on the list
     if person they want to message is on their blocked-by list, return true, thus they are blocked and
     won't be allowed to message the person
  - `public void writeCantSeeList(String username)` Creates the can not see list file for a given user
  - `public boolean checkIfCantSee(String username)` if returns true, means the user is not allowed to see the other person's usernmae
- This class has been tested via the AccountTest JUnit tests. Each method has been tested with a given input and has been compared to the expected output. 
- Account is the super class to the customer and seller classes. 

### `Customer.java`
- The customer class is the child class of the Account class. 
  - It inherits the fields and the methods of the parent class. 
  - The customer class has specific methods such as view stores which lists all of the stores, search seller, and store to seller which finds the respective seller from a store.  
- Important Methods
  - `public boolean searchSeller(String sellerName)` Method to search for a seller and assign if found
  - `public String storeToSeller(String storeName)` Method to find seller name from storename
- This class has been tested via the CustomerTests JUnit testing class. Every method has been tested with a given input and has been compared to an expected output. 
- Customer is a child of account and is referenced when creating and or logging into an account. 

### `Seller.java`
- The seller class is a child of the Account class. It inherits the fields and the methods of the Account class. The Seller class has many unique methods such as create store which creates as store and adds it to the list of stores, view customer which returns all of the customers available to message, search customer which returns true or false if there is a given customer, and get stores which returns a list of all of the stores. 
- Important Methods
  - `public boolean createStore(String storeName)` Creates the store by adding it to the storemanager.txt file as well as an instance variable in the 
  -  `public boolean searchCustomer(String customerName)` Returns a customer from the list that is being searched fro
  - `public ArrayList<String> getStores()` Populates an arraylist of all stores in the platform
  - ` public void populateStoreManagerList()` Populates the store manager list with new stores
- This class has been tested via the SellerTests JUnit testing class. Every methods has been tested with a 
- Seller is a child of account and is referenced when creating and or logging into an account. Many of the methods are referenced within the main class. 
### `Message.java`
- Message is the primary class which handles all of the messaging actions. 
- It allows the user to start a new message with another user, send a message which a specific text, print the message history from the current conversation, print history with indeces on a given message, edit a particular message which will edit both sides of the conversation, and finally deleting a message which will only delete it on the side of the user who has sent the delete command. 
- Important Methods
  - `public void sendMessage(String message)` Sends a message to a given user with a string message
  - `public void printMessageHistory()` Prints message history from a given conversation
  - `public void printMessageHistoryWithIndeces()` Prints message history from a given conversation with line numbers indexed at 1
  - `public void editMessage(int lineIndex, String updatedMessage)` Edits a message from a given conversation given a line number and message to edit
  - `public void deleteMessage(int i)` Deletes a message given a line number
  - `public void displayMostCommonWords()` Displays most common words in a particular conversation
  -  `public void displayLeastCommonWords()` Displays least common words in a given conversation
  - `private static String extractCustomerFromMessage(String message)` Finds the customer given a particular message
- This class has been tested via the MessageTest JUnit testing class. Every method has been tested and compared with an expected output. 
- Message is utilized in the main method. Accounts are referenced in the message class as well.
### `CantMessageException.java`
- This is an exception which is thrown when the user tries to message someone with the same role or someone that does not exist.
- Utilized in message class. 



