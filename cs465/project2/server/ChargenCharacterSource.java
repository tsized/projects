/**
 * The interface for a ChargenCharacterSource.
 *
 * @author Chris Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public interface ChargenCharacterSource {
   /**
    * Produces a character sequence for <code>Chargen</code>
    */
    public char getNextChar();
}
