package krr.sldresn.exceptions;

/**
 * Instance of this exception are created when SLD resolution was successful but
 * image generation failed.
 * @author nithin
 *
 */
public class ImageGenException extends Exception {

	public ImageGenException(String msg, Exception e) {
		super(msg,e);
	}

	private static final long serialVersionUID = 1L;

}
