package isr4;

import java.io.*;

public class RecallPrecisionEvaluation {

    public static String left(String s, int w) {
        StringBuilder sb = new StringBuilder(s);
        int padding = w - s.length();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        return sb.toString() + "|";
    }

    public static String center(String s, int w) {
        StringBuilder sb = new StringBuilder();
        int padding = w - s.length();
        int leftPadding = padding / 2;
        for (int i = 0; i < leftPadding; i++) {
            sb.append(" ");
        }
        sb.append(s);
        for (int i = 0; i < padding - leftPadding; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String prd(float x, int decDigits, int width) {
        String format = "% " + width + "." + decDigits + "f";
        return String.format(format, x);
    }

    public static String printDocs(String[] state, int size) {
        StringBuilder sb = new StringBuilder("| ");
        for (int i = 0; i < size; i++) {
            if (state[i] != null) {
                sb.append(state[i]);
                if (i + 1 < size && state[i + 1] != null && !state[i + 1].isEmpty()) {
                    sb.append(", ");
                }
            }
        }
        return left(sb.toString(), 98);
    }

    public static float E_value(float b, float rj, float pj) {
        return 1 - ((1 + b * b) * rj * pj) / (b * b * pj + rj);
    }

    public static void main(String[] args) {
        String[] Rq = {"d3", "d5", "d9", "d25", "d39", "d44", "d56", "d71", "d89", "d123"};
        String[] A = {"d123", "d84", "d56", "d6", "d8", "d9", "d51", "d129", "d187", "d25", "d38", "d48",
            "d250", "d113", "d3"};
        try (BufferedWriter write = new BufferedWriter(new FileWriter("Recall_Precision_Evaluation_output.txt"))) {
            float modRq = Rq.length;
            String[] Ra = new String[A.length];
            float[] P = new float[A.length];
            float[] R = new float[A.length];
            float modRa = 0;
            float modA = 0;
            float precision;
            float recall;
            System.out.printf("%s%n", "-".repeat(146));
            write.write("-".repeat(146) + "\n");
            System.out.printf("|%s|%s|%s|%s|%s|%n", center("Documents", 96), center("|Ra|", 8),
                    center("|A|", 8), center("Precision(%)", 13), center("Recall(%)", 13));
            write.write(String.format("|%s|%s|%s|%s|%s|%n", center("Documents", 96), center("|Ra|", 8),
                    center("|A|", 8), center("Precision(%)", 13), center("Recall(%)", 13)));
            System.out.printf("%s%n", "-".repeat(146));
            write.write("-".repeat(146) + "\n");
            for (int i = 0; i < A.length; i++) {
                Ra[i] = A[i];
                modA++;
                for (String r : Rq) {
                    if (A[i].equals(r)) {
                        modRa++;
                        break;
                    }
                }
                precision = (modRa / modA) * 100;
                P[i] = precision / 100;
                recall = (modRa / modRq) * 100;
                R[i] = recall / 100;
                System.out.print(printDocs(Ra, Ra.length));
                write.write(printDocs(Ra, Ra.length));
                System.out.printf("%s|%s|%s|%s|%n", prd(modRa, 2, 10), prd(modA, 2, 10), prd(precision, 2,
                        13), prd(recall, 2, 10));
                write.write(String.format("%s|%s|%s|%s|%n", prd(modRa, 2, 10), prd(modA, 2, 10),
                        prd(precision, 2, 13), prd(recall, 2, 10)));
            }
            System.out.printf("%s%n", "-".repeat(146));
            write.write("-".repeat(146) + "\n");
            int j;
            do {
                System.out.printf(
                        "Harmonic mean and E-value\nEnter value of j(0 - %d) to find F(j) and E(j):%n",
                        A.length - 1);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                j = Integer.parseInt(reader.readLine());
            } while (j < 0 || j >= Ra.length);
            float Fj = 2 * (P[j] * R[j]) / (P[j] + R[j]);
            System.out.printf("%s%n| Harmonic mean (F%d) is: %.2f|%n%s%n", "-".repeat(38), j, Fj, "-".repeat(38));
            write.write(String.format("%s%n| Harmonic mean (F%d) is: %.2f|%n%s%n", "-".repeat(38), j, Fj, "-".repeat(38)));
            System.out.printf("%s%n|%s%n", "-".repeat(38), center("E-Value", 32));
            System.out.printf("%s%n", "-".repeat(38));
            write.write(String.format("%s%n|%s%n", "-".repeat(38), center("E-Value", 32)));
            write.write(String.format("%s%n", "-".repeat(38)));
            System.out.printf("|%s|%s|%s|%n", center("b>1", 10), center("b=0", 10), center("b<1", 10));
            System.out.printf("%s%n", "-".repeat(38));
            write.write(String.format("|%s|%s|%s|%n", center("b>1", 10), center("b=0", 10), center("b<1", 10)));
            write.write("-".repeat(38) + "\n");
            System.out.printf("|%s|%s|%s|%n", prd(E_value(1.1f, R[j], P[j]), 2, 10), prd(E_value(0, R[j], P[j]), 2,
                    10), prd(E_value(0.9f, R[j], P[j]), 2, 10));
            write.write(String.format("|%s|%s|%s|%n", prd(E_value(1.1f, R[j], P[j]), 2, 10), prd(E_value(0, R[j],
                    P[j]), 2, 10), prd(E_value(0.9f, R[j], P[j]), 2, 10)));
            System.out.printf("%s%n", "-".repeat(38));
            write.write("-".repeat(38) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid number.");
        }
    }
}
