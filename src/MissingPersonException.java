
public class MissingPersonException extends NullPointerException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message;

    public MissingPersonException(String message){
        this.message = message + " is not good";
    }

    // Overrides Exception's getMessage()
    @Override
    public String getMessage() {
        return message;
    }

}
