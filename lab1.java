import java.util.Scanner;

class Lab1 {
    static final Scanner scanner = new Scanner(System.in);

    static class Complex {
        double re;
        double im;

        Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }

        static Complex input() {
            String s = scanner.next().replace(" ", "");

            if (!s.contains("i")) {
                return new Complex(Double.parseDouble(s), 0);
            }

            s = s.substring(0, s.length() - 1);

            if (s.isEmpty() || s.equals("+")) {
                return new Complex(0, 1);
            }
            if (s.equals("-")) {
                return new Complex(0, -1);
            }

            int pos = -1;
            for (int j = 1; j < s.length(); j++) {
                if (s.charAt(j) == '+' || s.charAt(j) == '-') {
                    pos = j;
                    break;
                }
            }

            if (pos >= 0) {
                double a = Double.parseDouble(s.substring(0, pos));
                String imaginary = s.substring(pos);
                double b;
                if (imaginary.equals("+")) {
                    b = 1;
                } else if (imaginary.equals("-")) {
                    b = -1;
                } else {
                    b = Double.parseDouble(imaginary);
                }
                return new Complex(a, b);
            }

            return new Complex(0, Double.parseDouble(s));
        }

        static Complex addComplex(Complex a, Complex b) {
            return new Complex(a.re + b.re, a.im + b.im);
        }

        static Complex subtractComplex(Complex a, Complex b) {
            return new Complex(a.re - b.re, a.im - b.im);
        }

        static Complex multiplyComplex(Complex a, Complex b) {
            return new Complex(
                    a.re * b.re - a.im * b.im,
                    a.re * b.im + a.im * b.re
            );
        }

        static Complex multiplyByNumber(Complex a, double number) {
            return new Complex(a.re * number, a.im * number);
        }

        static Complex divideComplex(Complex a, Complex b) {
            double denominator = b.re * b.re + b.im * b.im;
            if (denominator == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return new Complex(
                    (a.re * b.re + a.im * b.im) / denominator,
                    (a.im * b.re - a.re * b.im) / denominator
            );
        }

        @Override
        public String toString() {
            if (im == 0) {
                return String.valueOf(re);
            }
            if (re == 0) {
                if (im == 1) return "i";
                if (im == -1) return "-i";
                return im + "i";
            }
            if (im == 1) {
                return re + "+i";
            }
            if (im == -1) {
                return re + "-i";
            }
            return re + (im > 0 ? "+" : "") + im + "i";
        }
    }

    static class ComplexMatrix {
        static Complex[][] newMatrix(int lines, int columns, int number) {
            Complex[][] matrix = new Complex[lines][columns];
            System.out.println("Enter matrix " + number + " (examples: 3+2i, 3-2i, 2i, 3):");

            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < columns; j++) {
                    System.out.print("Element [" + i + "][" + j + "]: ");
                    matrix[i][j] = Complex.input();
                }
            }
            return matrix;
        }

        static Complex[][] newEmptyMatrix(int lines, int columns) {
            Complex[][] matrix = new Complex[lines][columns];
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = new Complex(0, 0);
                }
            }
            return matrix;
        }

        static int readSize() {
            System.out.print("Enter the size of square matrices: ");
            int lines = scanner.nextInt();
            if (lines < 1) {
                throw new IllegalArgumentException("Matrix size must be positive.");
            }
            return lines;
        }

        static void printMatrix(Complex[][] matrix) {
            for (Complex[] row : matrix) {
                for (Complex value : row) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        }

        static Complex[][] chooseFirstMatrix(Complex[][] previousResult, int number) {
            if (previousResult != null) {
                System.out.print("Use the previous result as matrix " + number + "? (1 - yes, 2 - no): ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    return previousResult;
                }
            }
            int lines = previousResult == null ? readSize() : previousResult.length;
            return newMatrix(lines, lines, number);
        }

        static Complex[][] add(Complex[][] previousResult) {
            Complex[][] matrix1 = chooseFirstMatrix(previousResult, 1);
            int lines = matrix1.length;
            Complex[][] matrix2 = newMatrix(lines, lines, 2);
            Complex[][] result = newEmptyMatrix(lines, lines);

            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    result[i][j] = Complex.addComplex(matrix1[i][j], matrix2[i][j]);
                }
            }
            System.out.println("The result:");
            printMatrix(result);
            return result;
        }

        static Complex[][] subtract(Complex[][] previousResult) {
            Complex[][] matrix1 = chooseFirstMatrix(previousResult, 1);
            int lines = matrix1.length;
            Complex[][] matrix2 = newMatrix(lines, lines, 2);
            Complex[][] result = newEmptyMatrix(lines, lines);

            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    result[i][j] = Complex.subtractComplex(matrix1[i][j], matrix2[i][j]);
                }
            }
            System.out.println("The result:");
            printMatrix(result);
            return result;
        }

        static Complex[][] multiply(Complex[][] previousResult) {
            Complex[][] matrix1 = chooseFirstMatrix(previousResult, 1);
            int lines = matrix1.length;
            Complex[][] matrix2 = newMatrix(lines, lines, 2);
            Complex[][] result = newEmptyMatrix(lines, lines);

            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    for (int k = 0; k < lines; k++) {
                        Complex product = Complex.multiplyComplex(matrix1[i][k], matrix2[k][j]);
                        result[i][j] = Complex.addComplex(result[i][j], product);
                    }
                }
            }
            System.out.println("The result:");
            printMatrix(result);
            return result;
        }

        static Complex determinantValue(Complex[][] matrix, int n) {
            if (n == 1) {
                return matrix[0][0];
            }

            Complex det = new Complex(0, 0);
            for (int j = 0; j < n; j++) {
                Complex[][] minor = new Complex[n - 1][n - 1];

                for (int i = 1; i < n; i++) {
                    int column = 0;
                    for (int k = 0; k < n; k++) {
                        if (k != j) {
                            minor[i - 1][column] = matrix[i][k];
                            column++;
                        }
                    }
                }

                Complex term = Complex.multiplyComplex(
                        matrix[0][j],
                        determinantValue(minor, n - 1)
                );

                if (j % 2 != 0) {
                    term = Complex.multiplyByNumber(term, -1);
                }
                det = Complex.addComplex(det, term);
            }
            return det;
        }

        static boolean isZero(Complex z) {
            return Math.abs(z.re) < 1e-10 && Math.abs(z.im) < 1e-10;
        }

        static void determinant(Complex[][] previousResult) {
            Complex[][] matrix;
            if (previousResult != null) {
                System.out.print("Find determinant of the previous result? (1 - yes, 2 - no): ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    matrix = previousResult;
                } else {
                    int lines = readSize();
                    matrix = newMatrix(lines, lines, 1);
                }
            } else {
                int lines = readSize();
                matrix = newMatrix(lines, lines, 1);
            }
            Complex det = determinantValue(matrix, matrix.length);
            System.out.println("The determinant is: " + det);
        }

        static Complex[][] transpose(Complex[][] previousResult) {
            Complex[][] matrix;
            if (previousResult != null) {
                System.out.print("Transpose the previous result? (1 - yes, 2 - no): ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    matrix = previousResult;
                } else {
                    int lines = readSize();
                    matrix = newMatrix(lines, lines, 1);
                }
            } else {
                int lines = readSize();
                matrix = newMatrix(lines, lines, 1);
            }

            int lines = matrix.length;
            Complex[][] result = newEmptyMatrix(lines, lines);
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    result[j][i] = matrix[i][j];
                }
            }
            System.out.println("The result:");
            printMatrix(result);
            return result;
        }

        static Complex[][] inverse(Complex[][] matrix, int lines) {
            Complex det = determinantValue(matrix, lines);
            if (isZero(det)) {
                return null;
            }

            Complex[][] result = newEmptyMatrix(lines, lines);

            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    Complex cofactor;

                    if (lines == 1) {
                        cofactor = new Complex(1, 0);
                    } else {
                        Complex[][] minor = new Complex[lines - 1][lines - 1];
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
                                minor[row][col] = matrix[r][c];
                                col++;
                            }
                            row++;
                        }
                        cofactor = determinantValue(minor, lines - 1);
                    }

                    if ((i + j) % 2 != 0) {
                        cofactor = Complex.multiplyByNumber(cofactor, -1);
                    }
                    result[j][i] = Complex.divideComplex(cofactor, det);
                }
            }
            return result;
        }

        static Complex[][] split(Complex[][] previousResult) {
            Complex[][] matrix1 = chooseFirstMatrix(previousResult, 1);
            int lines = matrix1.length;
            Complex[][] matrix2 = newMatrix(lines, lines, 2);

            Complex det = determinantValue(matrix2, lines);
            if (isZero(det)) {
                System.out.println("Error: division is impossible! The second matrix has determinant zero.");
                return previousResult;
            }

            Complex[][] inverseMatrix = inverse(matrix2, lines);
            Complex[][] result = newEmptyMatrix(lines, lines);

            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    for (int k = 0; k < lines; k++) {
                        Complex product = Complex.multiplyComplex(matrix1[i][k], inverseMatrix[k][j]);
                        result[i][j] = Complex.addComplex(result[i][j], product);
                    }
                }
            }
            System.out.println("The result:");
            printMatrix(result);
            return result;
        }

    }

    public static void main(String[] args) {
        Complex[][] previousResult = null;
        int var;

        do {
            System.out.println("Enter 1 - add, 2 - subtract, 3 - multiply, 4 - divide, 5 - transpose, 6 - determinant, 0 - exit:");
            var = scanner.nextInt();

            switch (var) {
                case 1:
                    previousResult = ComplexMatrix.add(previousResult);
                    break;
                case 2:
                    previousResult = ComplexMatrix.subtract(previousResult);
                    break;
                case 3:
                    previousResult = ComplexMatrix.multiply(previousResult);
                    break;
                case 4:
                    previousResult = ComplexMatrix.split(previousResult);
                    break;
                case 5:
                    previousResult = ComplexMatrix.transpose(previousResult);
                    break;
                case 6:
                    ComplexMatrix.determinant(previousResult);
                    break;
                case 0:
                    System.out.println("Finished");
                    break;
                default:
                    System.out.println("Wrong input");
            }
        } while (var != 0);
    }
}



