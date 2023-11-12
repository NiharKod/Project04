/**
 * CantMessageException class
 *
 * <p>Purdue University -- CS18000 -- Fall 2023 -- Project 04 -- </p>
 *
 * @author Nihar Kodkani, Nangba Konyak, Isabelle Lee, Sandesh Reddy
 * @version November 12th, 2023
 */
public class CantMessageException extends Exception {
    public CantMessageException(String role) {
        super("You can't message this person because you are both " + role + " or this user does not exist.");
    }
}
