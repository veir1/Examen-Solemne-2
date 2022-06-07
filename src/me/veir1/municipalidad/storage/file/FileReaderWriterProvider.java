package me.veir1.municipalidad.storage.file;

import javax.swing.*;
import java.io.*;

public final class FileReaderWriterProvider {
    private BufferedReader reader;
    private BufferedWriter writer;

    private final String inputPath;
    private final String outputPath;
    private String outputAbsolutePath;
    private final static String PANIC_ERROR = "No se ha podido completar la acción solicitada: %s.\nCódigo de error: %s";

    public FileReaderWriterProvider(final String inputFile, final String outputFile) {
        inputPath = inputFile;
        outputPath = outputFile;

        makeReader(inputPath);
        makeWriter(outputPath);
    }

    private void makeReader(final String inputFile) {
        final String path = System.getProperty("user.dir") + System.getProperty("file.separator") + inputFile;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (final IOException exception) {
            JOptionPane.showMessageDialog(null, String.format(PANIC_ERROR, "FileReaderWriterProvider#makeReader", exception.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
            System.exit(1);
        }
    }

    private void makeWriter(final String outputFile) {
        final String path = System.getProperty("user.dir") + System.getProperty("file.separator") + outputFile;
        outputAbsolutePath = path; // Guardo la ruta absoluta de salida.
        try {
            writer = new BufferedWriter(new FileWriter(path));
        } catch (final IOException exception) {
            JOptionPane.showMessageDialog(null, String.format(PANIC_ERROR, "FileReaderWriterProvider#makeWriter", exception.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public BufferedReader getReader() {
        if (reader == null) {
            makeReader(inputPath);
        }
        return reader;
    }

    public BufferedWriter getWriter() {
        if (writer == null) {
            makeWriter(outputPath);
        }
        return writer;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getOutputAbsolutePath() {
        return outputAbsolutePath;
    }
}
