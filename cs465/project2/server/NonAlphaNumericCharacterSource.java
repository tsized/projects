/**
 * Generates the chars which are output to the client.
 *
 * @author Chis Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public class NonAlphaNumericCharacterSource implements ChargenCharacterSource { 
    /** The amount of chars to be generated. */
    private int charCount;

    /**
     * Assigns <code>STARTCHAR</code> to <code>charCount</code> and sets length
     * to zero.
     */
    public NonAlphaNumericCharacterSource() {
        this.charCount = 0;
    }

    /**
     * The entry point of <code>DefactoChargenCharacterSource</code>.
     */
    public static void main(String[]args) {
        NonAlphaNumericCharacterSource NonAlphaCharSource 
            = new NonAlphaNumericCharacterSource();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 70; j++) {
                System.out.print(NonAlphaCharSource.getNextChar() + " ");
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
        char[] nonAlphaNum = {' ','!','"','#','$','(',')','*','+','-','.',
            '/',':',';','<','=','>','?','@','[','\\',']','^','_','`','{','|',
            '}','~'};

        if(charCount < 28) {
            charCount++; 
            return nonAlphaNum[charCount];
        } else {
            charCount = 1;
            return nonAlphaNum[charCount];
        } 
    }


}
