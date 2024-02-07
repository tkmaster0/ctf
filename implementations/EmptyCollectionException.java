package implementations;

/**
 *
 * @author Dinis
 */
public class EmptyCollectionException extends RuntimeException {

    public EmptyCollectionException() {
        super();
    }

    public EmptyCollectionException(String text) {
        super(text + " is empty.");
    }
}
