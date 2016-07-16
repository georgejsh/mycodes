package exceptions;

public class ImageGenException extends Exception {

	public ImageGenException(String msg, Exception e) {
		super(msg,e);
	}

	private static final long serialVersionUID = 1L;

}