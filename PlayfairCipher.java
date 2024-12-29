import java.util.Scanner;

public class PlayfairCipher {
    private static char[][] keyMatrix = new char[5][5];

    // Generate the key matrix
    public static void generateKeyMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.toUpperCase().replaceAll("J", "I").replaceAll("[^A-Z]", "");

        int index = 0;
        for (char ch : key.toCharArray()) {
            if (!used[ch - 'A']) {
                keyMatrix[index / 5][index % 5] = ch;
                used[ch - 'A'] = true;
                index++;
            }
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch == 'J') continue; // Skip J (treated as I)
            if (!used[ch - 'A']) {
                keyMatrix[index / 5][index % 5] = ch;
                used[ch - 'A'] = true;
                index++;
            }
        }
    }

    // Process input text (prepare pairs)
    public static String prepareText(String text) {
        text = text.toUpperCase().replaceAll("J", "I").replaceAll("[^A-Z]", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (i + 1 < text.length() && current == text.charAt(i + 1)) {
                result.append(current).append('X');
            } else {
                result.append(current);
            }
        }
        if (result.length() % 2 != 0) result.append('X'); // Padding if odd length
        return result.toString();
    }

    // Encrypt using the Playfair cipher
    public static String encrypt(String text) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) {
                // Same row
                encryptedText.append(keyMatrix[posA[0]][(posA[1] + 1) % 5]);
                encryptedText.append(keyMatrix[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) {
                // Same column
                encryptedText.append(keyMatrix[(posA[0] + 1) % 5][posA[1]]);
                encryptedText.append(keyMatrix[(posB[0] + 1) % 5][posB[1]]);
            } else {
                // Rectangle swap
                encryptedText.append(keyMatrix[posA[0]][posB[1]]);
                encryptedText.append(keyMatrix[posB[0]][posA[1]]);
            }
        }
        return encryptedText.toString();
    }

    // Find position of a character in the key matrix
    private static int[] findPosition(char ch) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == ch) {
                    return new int[]{i, j};
                }
            }
        }
        return null; // Should never happen
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the key:");
        String key = scanner.nextLine();

        generateKeyMatrix(key);

        System.out.println("Enter the text to encrypt:");
        String text = scanner.nextLine();

        String preparedText = prepareText(text);
        String encryptedText = encrypt(preparedText);

        System.out.println("Encrypted text: " + encryptedText);
    }
}
