import exception.WrongMatricesSizesException;
import exception.WrongMatrixFormatException;
import socket.SocketWrapper;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Matrix {
    public static final int MAX_SIZE = 10000 * 10000;
    private double[][] elements;

    public Matrix(int rows, int columns) {
        elements = new double[rows][columns];
    }

    public void setElement(int row, int column, double value) {
        elements[row][column] = value;
    }

    public double getElement(int row, int column) {
        return elements[row][column];
    }

    public int getRowsCount() {
        return elements.length;
    }

    public int getColumnsCount() {
        return elements[0].length;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getColumnsCount() != b.getRowsCount()) {
            throw new IllegalArgumentException();
        }

        Matrix c = new Matrix(a.getRowsCount(), b.getColumnsCount());
        for (int i = 0; i < c.getRowsCount(); i++) {
            for (int j = 0; j < c.getColumnsCount(); j++) {
                double value = 0;
                for (int k = 0; k < a.getColumnsCount(); k++) {
                    value += a.getElement(i, k) * b.getElement(k, j);
                }
                c.setElement(i, j, value);
            }
        }
        return c;
    }

    public static Matrix readFrom(BufferedReader reader) throws IOException, WrongMatrixFormatException {
        StringTokenizer tok = new StringTokenizer(reader.readLine());
        int rowsCount;
        int columnsCount;
        try {
            rowsCount = Integer.parseInt(tok.nextToken());
            columnsCount = Integer.parseInt(tok.nextToken());
        } catch (NumberFormatException e) {
            throw new WrongMatrixFormatException("Size values must be integers");
        }
        checkMatrixSize(rowsCount, columnsCount);
        Matrix matrix = new Matrix(rowsCount, columnsCount);
        for (int i = 0; i < rowsCount; i++) {
            tok = new StringTokenizer(reader.readLine());
            for (int j = 0; j < columnsCount; j++) {
                try {
                    matrix.setElement(i, j, Double.parseDouble(tok.nextToken()));
                } catch (NoSuchElementException e) {
                    throw new WrongMatrixFormatException("Wrong number of elements");
                }
            }
        }
        return matrix;
    }

    public static Matrix readFromFile(String filepath) throws IOException, WrongMatrixFormatException {
        return readFrom(new BufferedReader(new FileReader(filepath)));
    }

    public static Matrix readFromSocket(SocketWrapper socket) throws IOException, WrongMatrixFormatException {
        return readFrom(socket.getReader());
    }

    public static void writeTo(Matrix matrix, PrintWriter writer) {
        writer.println(matrix.getRowsCount() + " " + matrix.getColumnsCount());
        for (int i = 0; i < matrix.getRowsCount(); i++) {
            for (int j = 0; j < matrix.getColumnsCount(); j++) {
                writer.print(matrix.getElement(i, j) + " ");
            }
            writer.println();
        }
        writer.flush();
    }

    public static void writeToFile(Matrix matrix, String filepath) throws FileNotFoundException {
        writeTo(matrix, new PrintWriter(filepath));
    }

    public static void writeToSocket(Matrix matrix, SocketWrapper socket) throws IOException {
        writeTo(matrix, socket.getWriter());
    }

    public static void checkMatrixSize(int rowsCount, int columnsCount) throws WrongMatrixFormatException {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new WrongMatrixFormatException("Size values must be positive integers");
        }
        if ((long) rowsCount * columnsCount >= MAX_SIZE) {
            throw new WrongMatrixFormatException("Matrix is too big");
        }
    }

    public static void checkForMultiplicable(Matrix a, Matrix b) throws WrongMatricesSizesException {
        if (a.getColumnsCount() != b.getRowsCount()) {
            throw new WrongMatricesSizesException();
        }
    }
}
