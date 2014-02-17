/**
 * Generates the chars which are output to the client.
 *
 * @author Chis Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public class AlphaNumericCharacterSource implements ChargenCharacterSource { 
    /** The amount of chars to be generated. */
    private int charCount;

    /**
     * Assigns <code>STARTCHAR</code> to <code>charCount</code> and sets length
     * to zero.
     */
    public AlphaNumericCharacterSource() {
        this.charCount = 0;
    }

    /**
     * The entry point of <code>DefactoChargenCharacterSource</code>.
     */
    public static void main(String[]args) {
        AlphaNumericCharacterSource DefactCharSource 
            = new AlphaNumericCharacterSource();
        for (int i = 0; i < 100; i++) {
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
        char[] alphaNum = {' ','A','B','C','D','E','F','G','H','I','J','K','L',
               'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b',
               'c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
               's','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8',
               '9'};
        if(charCount < 61) {
            charCount++; 
            return alphaNum[charCount];
        } else {
            charCount = 1;
            return alphaNum[charCount];
        } 
    }

}
