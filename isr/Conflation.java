package isr;

import java.io.*;
import java.util.Scanner;

public class Conflation {

    public static void main(String[] args) throws IOException {
        try {
            File fi = new File("Input.txt");
            Scanner scl = new Scanner(fi);
            int ch;

            do {
                System.out.println("1. Display the file");
                System.out.println("2. Remove Stop Words");
                System.out.println("3. Suffix Stripping");
                System.out.println("4. Count Frequency");
                System.out.println("Enter your choice:");
                Scanner sc = new Scanner(System.in);
                ch = sc.nextInt();

                switch (ch) {
                    case 1:
                        while (scl.hasNext()) {
                            System.out.print(scl.next() + " ");
                        }
                        System.out.println();
                        break;
                    case 2:
                        remove_punctuation(fi);
                        remove_stop_words(fi);
                        break;
                    case 3:
                        suffix_stripping();
                        break;
                    case 4:
                        frequency_count();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (ch != 4);

            scl.close(); // Close the scanner for file input
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static void remove_punctuation(File fi) throws IOException {
        Scanner sc_punctuation = new Scanner(fi);
        BufferedWriter out = new BufferedWriter(new FileWriter("without_punctuation_and_stopwords.txt"));

        while (sc_punctuation.hasNext()) {
            String str_p = sc_punctuation.next();
            String str_r = str_p.replaceAll("[^a-zA-Z]", ""); // Keep only letters

            if (!str_r.isEmpty() && !isStopWord(str_r)) {
                out.write(str_r + " ");
            }
        }
        out.close();
        sc_punctuation.close(); // Close the scanner for punctuation

        System.out.println("File after removing punctuation and stopwords:");
        printFile("without_punctuation_and_stopwords.txt");
    }

    private static boolean isStopWord(String word) {
        String[] stopWords = {"the", "is", "and", "of", "are", "for", "in"};
        for (String stopWord : stopWords) {
            if (word.equalsIgnoreCase(stopWord)) {
                return true;
            }
        }
        return false;
    }

    private static void printFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String z;
        while ((z = br.readLine()) != null) {
            System.out.println(z);
        }
        br.close();
    }

    private static void suffix_stripping() throws IOException {
        Scanner scl = new Scanner(new File("without_punctuation_and_stopwords.txt"));
        BufferedWriter out = new BufferedWriter(new FileWriter("suffix_stripping2.txt"));

        while (scl.hasNext()) {
            String str = scl.next();
            if (str.endsWith("ier")) {
                str = str.replaceAll("ier$", "y");
            } else if (str.endsWith("ied")) {
                str = str.replaceAll("ied$", "y");
            } else if (str.endsWith("iest")) {
                str = str.replaceAll("iest$", "y");
            }
            out.write(str + " ");
        }
        out.close();
        scl.close();

        System.out.println("File after suffix stripping:");
        printFile("suffix_stripping2.txt");
    }

    private static void frequency_count() throws IOException {
        Scanner sc = new Scanner(new File("suffix_stripping2.txt"));
        String[] words = new String[1000];
        int[] counts = new int[1000];
        int i = 0;

        while (sc.hasNext()) {
            String word = sc.next();
            boolean found = false;
            for (int j = 0; j < i; j++) {
                if (word.equalsIgnoreCase(words[j])) {
                    counts[j]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                words[i] = word;
                counts[i] = 1;
                i++;
            }
        }
        for (int j = 0; j < i; j++) {
            System.out.println(words[j] + ": " + counts[j]);
        }
        sc.close(); // Close the scanner for frequency count
    }

    private static void remove_stop_words(File fi) {
        // Implement this function if you have specific stop words to remove
    }
}
