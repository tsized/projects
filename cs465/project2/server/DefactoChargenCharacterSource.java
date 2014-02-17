/**
 * Generates the chars which are output to the client.
 *
 * @author Chis Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public class DefactoChargenCharacterSource implements ChargenCharacterSource { 
    /** The amount of chars to be generated. */
    private int charCount;

    /**
     * Assigns <code>STARTCHAR</code> to <code>charCount</code> and sets length
     * to zero.
     */
    public DefactoChargenCharacterSource() {
        this.charCount = 0;
    }

    /**
     * The entry point of <code>DefactoChargenCharacterSource</code>.
     */
    public static void main(String[]args) {
        DefactoChargenCharacterSource DefactCharSource 
            = new DefactoChargenCharacterSource();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 70; j++) {
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
        char[] alphaNum = {' ','!','"','#','$','%','&','(',')','*','+','+','`',
            '-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<',
            '=','>','?','@','A','B','C','D','E','F','G','H','I','J','K','L',
            'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[','\\',
            ']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l',
            'm','n','o','p','q','r','s','t','u','v','w','x','y','z','{','|',
            '}','~'};
        if(charCount < 94) {
            charCount++; 
            return alphaNum[charCount];
        } else {
            charCount = 1;
            return alphaNum[charCount];
        } 
    }   
}
