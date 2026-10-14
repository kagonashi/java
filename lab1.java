import java.util.Scanner;
class Matrix {
    static double[][] newMatrix(int lines, int columns, int number) {
         Scanner scanner = new Scanner(System.in);
        double[][] matrix = new double[lines][columns];
        System.out.printf("Enter the values of %d matrix: ", number);

        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    static double[][] newEmptyMatrix(int lines, int columns) {
     Scanner scanner = new Scanner(System.in);
        double[][] matrix = new double[lines][columns];

        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    static void add() {
    System.out.println("Enter the amount of lines (all matrixes are square):");
    Scanner scanner = new Scanner(System.in);
    int lines = scanner.nextInt();
    double[][] matrix_1 = Matrix.newMatrix(lines, lines, 1);
    double[][] matrix_2 = Matrix.newMatrix(lines, lines, 2);
    double[][] matrix_3 = Matrix.newEmptyMatrix(lines, lines);

    for (int i = 0; i < lines; i++) {

            for (int j = 0; j < lines; j++) {
                matrix_3[i][j] = matrix_1[i][j]+matrix_2[i][j];
            }
        }

     System.out.println("The result:");

    for (int i = 0; i < matrix_3.length; i++) {

            for (int j = 0; j < matrix_3[i].length; j++) {
                System.out.print(matrix_3[i][j] + " ");
            }
            System.out.print('\n');
        }
    }

    static void subtract() {
    System.out.println("Enter the amount of lines (all matrixes are square):");
    Scanner scanner = new Scanner(System.in);
    int lines = scanner.nextInt();
    double[][] matrix_1 = Matrix.newMatrix(lines, lines, 1);
    double[][] matrix_2 = Matrix.newMatrix(lines, lines, 2);
    double[][] matrix_3 = Matrix.newEmptyMatrix(lines, lines);

    for (int i = 0; i < lines; i++) {

            for (int j = 0; j < lines; j++) {
                matrix_3[i][j] = matrix_1[i][j]-matrix_2[i][j];
            }
        }
     System.out.println("The result:");

    for (int i = 0; i < matrix_3.length; i++) {

            for (int j = 0; j < matrix_3[i].length; j++) {
                System.out.print(matrix_3[i][j] + " ");
            }
            System.out.print('\n');
        }
    }

    static void multiply() {
    System.out.println("Enter the amount of lines (all matrixes are square):");
    Scanner scanner = new Scanner(System.in);
    int lines = scanner.nextInt();

    double[][] matrix_1 = Matrix.newMatrix(lines, lines, 1);
    double[][] matrix_2 = Matrix.newMatrix(lines, lines, 2);
    double[][] matrix_3 = Matrix.newEmptyMatrix(lines, lines);

    for (int i = 0; i < lines; i++) {

        for (int j = 0; j < lines; j++) {

            for (int k = 0; k < lines; k++) {
                matrix_3[i][j] += matrix_1[i][k] * matrix_2[k][j];
            }
        }
    }
    System.out.println("The result:");

    for (int i = 0; i < lines; i++) {

        for (int j = 0; j < lines; j++) {
            System.out.print(matrix_3[i][j] + " ");
        }
        System.out.println();
    }
}

static void split() {
    System.out.println("Enter the amount of lines (all matrixes are square):");
    Scanner scanner = new Scanner(System.in);
    int lines = scanner.nextInt();
    double[][] matrix_1 = Matrix.newMatrix(lines, lines, 1);
    double[][] matrix_2 = Matrix.newMatrix(lines, lines, 2);
    double det = determinantValue(matrix_2, lines);

    if (det == 0) {
        System.out.println("Error: division is impossible!");
        return;
    }
    double[][] inverse = new double[lines][lines];

    for (int i = 0; i < lines; i++) {

        for (int j = 0; j < lines; j++) {
            double[][] minor = new double[lines - 1][lines - 1];
            int row = 0;

            for (int r = 0; r < lines; r++) {

                if (r == i) {
                     continue;
                 }
                 int col = 0;

                for (int c = 0; c < lines; c++) {

                    if (c == j) {
                      continue;
                     }
                    minor[row][col] = matrix_2[r][c];
                      col++;
                 }
                 row++;
            }
            double cofactor;

            if (lines == 1) {
                cofactor = 1;
            } else {
                cofactor = determinantValue(minor, lines - 1);
            }

            if ((i + j) % 2 != 0) {
                cofactor = -cofactor;
            }
            inverse[j][i] = (double) cofactor / det;
        }
    }

    double[][] result = new double[lines][lines];

    for (int i = 0; i < lines; i++) {

        for (int j = 0; j < lines; j++) {

            for (int k = 0; k < lines; k++) {
                result[i][j] += matrix_1[i][k] * inverse[k][j];
            }
        }
    }
    System.out.println("The result:");

    for (int i = 0; i < lines; i++) {

        for (int j = 0; j < lines; j++) {
            System.out.printf("%.2f ", result[i][j]);
        }
        System.out.println();
    }
}

static void transpose() {
    System.out.println("Enter the amount of lines (all matrixes are square):");
    Scanner scanner = new Scanner(System.in);
    int lines = scanner.nextInt();
    double[][] matrix_1 = Matrix.newMatrix(lines, lines, 1);
    double[][] matrix_2 = Matrix.newEmptyMatrix(lines, lines);

    for (int i = 0; i < lines; i++) {

        for (int j = 0; j < lines; j++) {
            matrix_2[j][i] = matrix_1[i][j];
        }
    }
    System.out.println("The result:");

    for (int i = 0; i < lines; i++) {

        for (int j = 0; j < lines; j++) {
            System.out.print(matrix_2[i][j] + " ");
        }
        System.out.println();
    }
}

static double determinantValue(double[][] matrix, int n) {

    if (n == 1) {
        return matrix[0][0];
    }

    if (n == 2) {
        return matrix[0][0] * matrix[1][1]
             - matrix[0][1] * matrix[1][0];
    }
    int det = 0;

    for (int j = 0; j < n; j++) {
        double[][] minor = new double[n - 1][n - 1];

        for (int i = 1; i < n; i++) {
            int column = 0;

            for (int k = 0; k < n; k++) {
                
                if (k != j) {
                    minor[i - 1][column] = matrix[i][k];
                    column++;
                }
            }
        }
        int sign = (j % 2 == 0) ? 1 : -1;
        det += sign * matrix[0][j] * determinantValue(minor, n - 1);
    }
    return det;
}

static void determinant() {
    System.out.println("Enter the amount of lines (all matrixes are square):");
    Scanner scanner = new Scanner(System.in);
    int lines = scanner.nextInt();
    double[][] matrix_1 = Matrix.newMatrix(lines, lines, 1);
    double det = determinantValue(matrix_1, lines);
    System.out.println("The determinant is: " + det);
}

    public static void main(String[] args) {
        int var;
        System.out.println("Enter 1 - to add matrixes, 2 - to substract, 3 - to multiply, 4 - to split, 5 - to transpose, 6 - to find determinant:");
        Scanner scanner = new Scanner(System.in);
        var = scanner.nextInt();
        switch (var) {
            case 1:
                Matrix.add();
                break;
            case 2:
                Matrix.subtract();
                break;
            case 3:
                Matrix.multiply();
                break;
            case 4:
                Matrix.split();
                break;
            case 5:
                Matrix.transpose();
                break;
            case 6:
                Matrix.determinant();
                break;
        }
    }
}
