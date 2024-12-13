package com.neumma;


/*
    Requirements:
    - alokace matice zadaných rozměrů
    - vygenerování náhodného obsahu zadané matice
    - sčítání dvou matic (výsledek = nová matice s výsledkem) - ověřit kompatibilitu rozměrů)
    - násobení dvou matic + kontrola rozměrů
    - výpis obsahu matice
 */


public class Matrix {
    private String name;
    private int width;
    private int height;
    private int[][] values;

    // Default min max values for the random initial generation of the matrices.
    private final int DEFAULT_MIN = -20;
    private final int DEFAULT_MAX = 20;

    int cellSize; // Represents the size of longest value in the matrix, used for formatting the output print nicely.

    /**
     * Creates new Matrix and prefills it with random data
     * @param width Number of columns.
     * @param height number of rows.
     */
    public Matrix(int height, int width, String name, boolean verbose) {
        this.width = width;
        this.height = height;
        this.name = name;
        this.values = new int[height][width];
        generateRandomValues();

        int maxMinSize = String.valueOf(DEFAULT_MIN).length();
        int maxMaxSize = String.valueOf(DEFAULT_MAX).length();
        this.cellSize = Math.max(maxMaxSize, maxMinSize); // Gets the longest possible cell width, which will be used for all cells.

        if(verbose) {
            System.out.println("Matrix created: ");
            printMatrix();
        }
    }

    /**
     * Alternative to the full constructor, which implicitly sets verbose = false... bcs java is stupid and can not assign default values
     */
    public Matrix(int height, int width, String name) {
        this(height, width, name, false);
    }


    /**
     * Fills the matrix with random data
     */
    private void generateRandomValues() {
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                values[y][x] = RandomGenerator.getRandomInteger(DEFAULT_MIN, DEFAULT_MAX);
            }
        }
    }

    /**
     * Function to be called after calculation is made, so that cellSize is kept updated for later printout
     */
    private void updateCellSize() {
        int maxCellSize = Integer.MIN_VALUE;

        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int size = String.valueOf(this.getCell(y, x)).length();
                maxCellSize = Math.max(size, maxCellSize);
            }
        }

        this.cellSize = maxCellSize;
    }

    /**
     * Prints out the matrix in an appropriate format.
     */
    public void printMatrix() {

        int nameOffsetSize = name.length() + 3; // accommodates for the name in front, e.g. "A = "
        String cellFormat = "%" + cellSize + "d "; // set the size for cell

        // top border
        System.out.println(" ".repeat(nameOffsetSize) + "┌" + " ".repeat(width*(cellSize+1) + 2) + "┐");

        // print each line of the matrix
        for (int y = 0; y < height; y++) {

            // if this is the middle line, print the name, otherwise offset
            if(y == height/2) System.out.printf("%s = ", name);
            else System.out.print(" ".repeat(nameOffsetSize));

            // print out the content on the line
            System.out.print("│ ");
            for (int x = 0; x < width; x++) {
                System.out.printf(cellFormat, getCell(y,x));
            }
            System.out.println(" │");
        }

        // finish with bottom boredr
        System.out.println(" ".repeat(nameOffsetSize) + "└" + " ".repeat(width*(cellSize+1) + 2) + "┘");
    }

    /**
     * Verifies size compatibility of the current matrix with any other matrix given.
     * @param operation Type of operation being performed on the matrices.
     * @param secondMatrix Matrix the operation is being done with.
     * @return True if matrices are compatible for the given operation, false if incompatible.
     */
    public boolean checkMatrixSizeCompatibility(MatrixOperation operation, Matrix secondMatrix) {
        switch(operation) {
            case MATRIX_MULTIPLICATION -> {
                return (this.width == secondMatrix.getHeight());
            }
            case MATRIX_ADDITION -> {
                return (this.width == secondMatrix.getWidth() && this.height == secondMatrix.getHeight());
            }
        }
        return false;
    }

    /**
     * Function for matrix-matrix multiplication.
     * @param multiplier Matrix to multiply this matrix with.
     * @return Returns product of the operation.
     * @throws IncompatibleMatrixSizeException Exception is thrown if matrices are not compatible in size.
     */
    public Matrix multiply(Matrix multiplier) throws IncompatibleMatrixSizeException {

        // first check size compatibility
        if(!checkMatrixSizeCompatibility(MatrixOperation.MATRIX_MULTIPLICATION, multiplier)) {
                throw new IncompatibleMatrixSizeException(
                        "Number of columns of first matrix is not equal to number of rows of second matrix.",
                        MatrixOperation.MATRIX_MULTIPLICATION,
                        this,
                        multiplier);
        }

        //prepare the new matrix
        Matrix result = new Matrix(this.height, multiplier.getWidth(), String.format("%s*%s", this.name, multiplier.getName()));

        // go on with mutltiplicatoin
        for(int y = 0; y < this.height; y++) { // rows M1
            for(int x = 0; x < multiplier.getWidth(); x++) { // columns M2
                int sum = 0;
                for(int i = 0; i < this.width; i++) { // put together 1 result cell
                    sum += this.getCell(y, i) * multiplier.getCell(i, x);
                }
                result.setCell(y, x, sum);
            }
        }

        result.updateCellSize();

        return result;
    }

    /**
     * Function for adding two matrices together.
     * @param additionMatrix Matrix to be added to the original aatrix.
     * @return Result of the operation, new matrix which is a sum of the two matrices.
     * @throws IncompatibleMatrixSizeException Exception is thrown if matrices are not the same size.
     */
    public Matrix add(Matrix additionMatrix) throws IncompatibleMatrixSizeException {

        //check if size is equal
        if(!checkMatrixSizeCompatibility(MatrixOperation.MATRIX_ADDITION, additionMatrix)) {
            throw new IncompatibleMatrixSizeException(
                    "Matrices are not the same size.",
                    MatrixOperation.MATRIX_ADDITION,
                    this,
                    additionMatrix);
        }

        // prepare result matrix
        Matrix result = new Matrix(this.height, this.width,  String.format("%s+%s", this.name, additionMatrix.getName()));

        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                result.setCell(y, x, (this.getCell(y, x) + additionMatrix.getCell(y, x)));
            }
        }

        result.updateCellSize();

        return result;
    }


    // ----- Simple setters -----
    public void setName(String newName) {
        this.name = newName;
    }

    public void setCell(int y, int x, int value) {
        this.values[y][x] = value;
    }

    // ----- Simple getters -----
    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCell(int y, int x) {
        return values[y][x];
    }
}
