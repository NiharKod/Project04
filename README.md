# Readme

## Compiling and Running
### Compile and run the project through running `javac Main.java && java Main`

## Submissions
### Info


## Class Descriptions

### `Main.java`
- This is the main class where all other classes are ran. The primary use of this class is to house the main method which includes all of the prompts. In this class, the user is able to create or login to an existing account. They can then view a list of customers of stores which they can message depending on if they are a customer or a seller. They can then start a new/existing message with a user as well as edit and delete a message. All of these options are available via terminal prompts. 
- Testing done
- Almost every class is referenced by the main class. 

### `Account.java`
- The account class is a super class to the customer and seller class. It houses the main login method which allows a user to create or login to an existing account. It populates the many instance variables an account may have such as email, username, password, etc. This class contains methods on blocking and invisibility as well. The customer list and seller list methods and file io associated with this is written within this class. Overall, this class is the super class to the Customer and the Seller class which are inheriting the fields and methods of this class. 
- Testing done
- Account is the super class to the customer and seller classes. 

### `Customer.java`
- The customer class is the child class of the Account class. It inherits the fields and the methods of the parent class. The customer class has specific methods such as view stores which lists all of the stores, search seller, and store to seller which finds the respective seller from a store.  
- Testing done
- Customer is a child of account and is referenced when creating and or logging into an account. 

### `Seller.java`
- The seller class is a child of the Account class. It inherits the fields and the methods of the Account class. The Seller class has many unique methods such as create store which creates as store and adds it to the list of stores, view customer which returns all of the customers available to message, search customer which returns true or false if there is a given customer, and get stores which returns a list of all of the stores. 
- Testing done
- Seller is a child of account and is referenced when creating and or logging into an account. Many of hte methods are referenced within the main class. 
### `Message.java`
- Message is the primary class which handles all of the messaging actions. It allows the user to start a new message with another user, send a message which a specific text, print the message history from the current conversation, print history with indeces on a given message, edit a particular message which will edit both sides of the conversation, and finally deleting a message which will only delete it on the side of the user who has sent the delete command. 
- Testing done
- Message is utilized in the main method. Accounts are referenced in the message class as well.
### `Statistics.java`
- Functionality
- Testing done
- Relationship to other classes
### `WrongInputException.java`
- This is an exception which is thrown when the user tries to message someone with the same role or someone that does not exist.
- Testing done
- Utilized in message class. 



