public class CantMessageException extends Exception{
    public CantMessageException(String role){
        super("You can't message this person because you are both " + role);
    }
}
