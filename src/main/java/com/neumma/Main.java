package com.neumma;

public class Main {
    public static void main(String[] args) {

        Matrix A = new Matrix(2, 3, "A", true);
        Matrix B = new Matrix(3, 2, "B", true);
        Matrix C = new Matrix(2, 3, "C", true);

        // Addition example:
        try {
            System.out.println("\n\n--- Adding A and C ---");
            Matrix AplusC = A.add(C);
            AplusC.printMatrix();
        } catch (Exception e) {
            System.out.println("Operation failed.");
        }

        //  Addition fail example:
        try {
            System.out.println("\n\n--- Adding A and B ---");

            Matrix AplusB = A.add(B);
            AplusB.printMatrix();
        } catch (Exception e) {
            System.out.println("Operation failed.");
        }

        // Multiplication example:
        try {
            System.out.println("\n\n--- Multiplying A by B ---");
            Matrix AB = A.multiply(B);
            AB.printMatrix();
        } catch (IncompatibleMatrixSizeException e) {
            System.out.println("Operation failed.");
        }

        // Multiplication fail example:
        try {
            System.out.println("\n\n--- Multiplying A by C ---");
            Matrix AC = A.multiply(C);
            AC.printMatrix();
        } catch (Exception e) {
            System.out.println("Operation failed.");
        }
    }
}