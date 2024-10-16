package isr5;

import java.util.ArrayList;
import java.util.List;

public class EvaluationMetrics {

    public static List<Integer> commonMember(int[] a, int[] b) {
        List<Integer> result = new ArrayList<>();
        for (int valueA : a) {
            for (int valueB : b) {
                if (valueA == valueB) {
                    result.add(valueA);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] Q = {3, 7, 8, 11, 14, 19, 23, 25}; // total retrieved documents
        int[] A = {1, 2, 3, 7, 9, 10, 14, 20, 23, 24, 25}; // total relevant documents
        List<Integer> relevantRetriveA = commonMember(Q, A);
        System.out.println("\n\nRelevant Retrieved Documents: " + relevantRetriveA);
        System.out.println();
        for (int x = 0; x < relevantRetriveA.size(); x++) {
            int aValue = indexOf(Q, relevantRetriveA.get(x)) + 1;
            int aPlusC = indexOf(A, relevantRetriveA.get(x)) + 1;
            int b = 1; // Assuming the value of b as 1 for the E-measure
            double precision = (double) aValue / aPlusC * 100;
            double recall = (double) aValue / Q.length * 100;
            double f1Score = 2 * ((precision * recall) / (precision + recall));
            double harmonicMean = 2 / ((1 / recall) + (1 / precision));
            double eMeasure = 1 - ((1 + b * b) / (((b * b) / recall) + (1 / precision)));
            String precisionAnswer = "Precision: " + String.format("%.2f", precision);
            String recallAnswer = "Recall: " + String.format("%.2f", recall);
            String f1MeasureAnswer = "F1 Measure: " + String.format("%.2f", f1Score);
            String harmonicMeanAnswer = "Harmonic Mean: " + String.format("%.2f", harmonicMean);
            String eMeasureAnswer = "E-Measure: " + String.format("%.2f", eMeasure);
            System.out.println("Document: " + relevantRetriveA.get(x));
            System.out.println(precisionAnswer);
            System.out.println(recallAnswer);
            System.out.println(f1MeasureAnswer);
            System.out.println(harmonicMeanAnswer);
            System.out.println(eMeasureAnswer);
            System.out.println();
        }
        double totalRecall = (double) relevantRetriveA.size() / Q.length * 100;
        double totalPrecision = (double) relevantRetriveA.size() / A.length * 100;
        System.out.println("Total Recall: " + String.format("%.2f", totalRecall));
        System.out.println("Total Precision: " + String.format("%.2f", totalPrecision));
    }

    public static int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1; // Element not found
    }
}
