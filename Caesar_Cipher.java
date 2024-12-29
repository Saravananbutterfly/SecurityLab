import java.util.Scanner;

public class Caesar_Cipher {
    // Method to encrypt a string using Caesar Cipher
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        shift = shift % 26; // Ensure shift stays within alphabet range

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                // Shift the character and wrap around the alphabet
                char encryptedChar = (char) ((character - base + shift) % 26 + base);
                result.append(encryptedChar);
            } else {
                result.append(character); // Keep non-alphabetic characters unchanged
            }
        }
        return result.toString();
    }

    // Method to decrypt a string using Caesar Cipher
    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - shift); // Decrypt by reversing the shift
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the text to encrypt:");
        String text = scanner.nextLine();

        System.out.println("Enter the shift value:");
        int shift = scanner.nextInt();

        String encryptedText = encrypt(text, shift);
        System.out.println("Encrypted text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, shift);
        System.out.println("Decrypted text: " + decryptedText);
    }
}