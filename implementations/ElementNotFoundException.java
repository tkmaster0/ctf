package implementations;

/**
 *
 * @author Dinis
 */
public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException() {
        super();
    }

    public ElementNotFoundException(String text) {
        super(text);
    }
}
