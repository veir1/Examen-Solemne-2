package me.veir1.municipalidad;

import me.veir1.municipalidad.storage.PeopleStorage;

import javax.swing.*;

/**
 * Aplicación del Examen Solemne 2 de la asignatura
 * Programación Orientada a Objetos.
 *
 * Docente: Myrle Suárez.
 *
 * @author veir1
 */
public final class Programa {
    private PeopleStorage peopleStorage; // Lo he instanciado por si lo llego a necesitar más adelante

    private final static String READ_FILE_NAME = "Por favor ingrese el nombre del archivo a leer. El programa determinará automáticamente la ruta de éste.\nEjemplo: vecinos.txt";
    private final static String WRITE_FILE_NAME = "Por favor ingrese el nombre del archivo en el cual se escribirán los resultados. El programa determinará automáticamente la ruta de éste.\nEjemplo: vecinosOutput.txt";
    private final static String ERROR_FILE_NULL = "El archivo ingresado es inválido, está vacío, o se ha cancelado la operación cerrando el recuadro.";

    public static void main(String[] args) {
        final String inputFile = JOptionPane.showInputDialog(null, READ_FILE_NAME, "Archivo de lectura", JOptionPane.QUESTION_MESSAGE);
        if (inputFile == null || inputFile.equals("")) {
            JOptionPane.showMessageDialog(null, ERROR_FILE_NULL, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        final String outputFile = JOptionPane.showInputDialog(null, WRITE_FILE_NAME, "Archivo de salida", JOptionPane.QUESTION_MESSAGE);
        if (outputFile == null || outputFile.equals("")) {
            JOptionPane.showMessageDialog(null, ERROR_FILE_NULL, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        new Programa(inputFile, outputFile);
    }

    public Programa(final String inputFile, final String outputFile) {
        peopleStorage = new PeopleStorage(inputFile, outputFile);
    }
}
