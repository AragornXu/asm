package com.asm.sampleGen;

import java.util.Arrays;

public class MatrixOperations {
    private static class Complex {
        private final double real;
        private final double imag;

        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public Complex add(Complex other) {
            return new Complex(this.real + other.real, this.imag + other.imag);
        }

        public Complex multiply(Complex other) {
            double newReal = this.real * other.real - this.imag * other.imag;
            double newImag = this.real * other.imag + this.imag * other.real;
            return new Complex(newReal, newImag);
        }

        @Override
        public String toString() {
            return String.format("%.1f + %.1fi", real, imag);
        }
    }

    public static Complex[][] multiplyMatrices(Complex[][] a, Complex[][] b) 
        throws InvalidMatrixOperationException {
        
        if (a[0].length != b.length) {
            throw new InvalidMatrixOperationException(
                "Matrix dimension mismatch: " + 
                a[0].length + " vs " + b.length
            );
        }

        Complex[][] result = new Complex[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < a[0].length; k++) {
                    sum = sum.add(a[i][k].multiply(b[k][j]));
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    private static Complex[][] createMatrix(double[][][] values) {
        return Arrays.stream(values)
            .map(row -> Arrays.stream(row)
                .map(v -> new Complex(v[0], v[1]))
                .toArray(Complex[]::new))
            .toArray(Complex[][]::new);
    }

    public static void main(String[] args) {
        try {
            Complex[][] matrixA = createMatrix(new double[][][] {
                {{1, 2}, {3, 4}},
                {{5, 6}, {7, 8}}
            });

            Complex[][] matrixB = createMatrix(new double[][][] {
                {{2, 0}, {1, 2}},
                {{0, 1}, {3, 0}}
            });

            Complex[][] result = multiplyMatrices(matrixA, matrixB);
            
            for (Complex[] row : result) {
                System.out.println(Arrays.toString(row));
            }

            multiplyMatrices(matrixA, new Complex[3][3]);
            
        } catch (InvalidMatrixOperationException e) {
            System.out.println("Matrix error: " + e.getMessage());
        }
    }
}

class InvalidMatrixOperationException extends Exception {
    public InvalidMatrixOperationException(String message) {
        super(message);
    }
}