package Utils;

public class StringUtils {

    /**
     * Found on https://www.geeksforgeeks.org/reverse-a-string-in-java/
     * @param s
     * @return
     */
    public static String reverse(String s) {
        char ch;
        String reversedString = "";
        for (int i=0; i<s.length(); i++)
        {
            ch= s.charAt(i); //extracts each character
            reversedString= ch+reversedString; //adds each character in front of the existing string
        }
        return reversedString;
    }
}
