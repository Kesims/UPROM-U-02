package com.neumma;

public class IncompatibleMatrixSizeException extends Exception {

    /**
     * IncompatibleMatrixSizeException to be thrown when sizes of matrix are not compatible for given operation
     * @param message Message for the exception .getMessage().
     */
    public IncompatibleMatrixSizeException(String message) {
        super(message);
    }

    /**
     * IncompatibleMatrixSizeException to be thrown when sizes of matrix are not compatible for given operation,
     * with automatic System print containing info about exception that occurred.
     * @param message Default message for the Exception
     * @param operation Type of operation attempted for the matrix
     * @param matrix1 First matrix involved in the operation
     * @param matrix2 Second matrix involved in the operation
     */
    public IncompatibleMatrixSizeException(String message, MatrixOperation operation, Matrix matrix1, Matrix matrix2) {
        super(message);
        System.out.printf("MATRIX OPERATION EXCEPTION: %s requires matrices to meet size requirements.%n", operation.toString());
        System.out.printf("Matrix %s has size of %d x %d; matrix %s has size %d x %d.%n",
                matrix1.getName(), matrix1.getHeight(), matrix1.getWidth(), matrix2.getName(), matrix2.getHeight(), matrix2.getWidth());
    }
}
