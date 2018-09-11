import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Filter extends IndexQueue<Character> {

    private void fillParenthesisQueue() {
        String s = "({[]})";
        char[] chars = s.toCharArray();
        boolean found = false;

        /* Collect the input from ./input.txt */
        try (FileReader inputStream = new FileReader("src/recursive-characters.c")) {
            int stream;
            System.out.print("Input is: ");
            while ((stream = inputStream.read()) != -1) {
                for(char c : chars){
                    if(c == (char)stream) {
                        found = true;
                        break;
                    }
                }
                if(found){
                    this.addLast((char)stream); // push chars to stack
                    found = false;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void checkParenthesis() {
        fillParenthesisQueue();

        
    }

    /**
     * Unit testing the filter
     */
    public static void main(String[] args) {

        Filter filter = new Filter();

        filter.checkParenthesis();

        filter.addLast('X');

        /* String representation */
        System.out.print("\n\nfilter.toString() returns: ");
        System.out.println(filter.toString());

    }
}
