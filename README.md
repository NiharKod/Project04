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
- Functionality
- Testing done
- Relationship to other classes

### `Seller.java`
- Functionality
- Testing done
- Relationship to other classes
### `Message.java`
- Functionality
- Testing done
- Relationship to other classes

