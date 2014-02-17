/**
 * Generates the chars which are output to the client.
 *
 * @author Chis Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public class NumericCharacterSource implements ChargenCharacterSource { 
    /** The amount of chars to be generated. */
    private int charCount;
    
    /**
     * Assigns <code>STARTCHAR</code> to <code>charCount</code> and sets length
     * to zero.
     */
    public NumericCharacterSource() {
        this.charCount = 0;
    }
    
    /**
     * The entry point of <code>DefactoChargenCharacterSource</code>.
     */
    public static void main(String[]args) {
        NumericCharacterSource DefactCharSource 
                                         = new NumericCharacterSource();
        for (int i = 0; i < 100 ; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.print(DefactCharSource.getNextChar() + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Returns the next char in the sequence.
     *
     * @return The next char.
     */
    public char getNextChar() {
        char[] numArray = {' ','1','2','3','4','5','6','7','8','9','0'};
        if(charCount < 10) {
            charCount++; 
            return numArray[charCount];
        } else {
            charCount = 1;
            return numArray[charCount];
        }
    }

}
