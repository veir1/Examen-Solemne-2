package me.veir1.municipalidad.storage;

import me.veir1.municipalidad.entity.Entity;
import me.veir1.municipalidad.storage.file.FileReaderWriterProvider;
import me.veir1.municipalidad.storage.work.SuggestedWorkProcessor;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class PeopleStorage {
    private final FileReaderWriterProvider fileReaderWriterProvider;
    private final List<Entity> entities;

    private int womenQuantity;
    private int menQuantity;

    private final static String WRITE_SUCCESSFUL = "Se han guardado un total de %s entidades. \nEl archivo de salida está ubicado en: %s.";
    private final static String ENTITY_PARSE_ERROR = "La entidad [%s] no posee el formato correcto para ser procesada.\nEl programa se cerrará.\n \nFormato correcto: [Apellido:Nombre:Genero:Edad]";
    private final static String PANIC_ERROR = "No se ha podido completar la acción solicitada: %s.\nCódigo de error: %s";
    public PeopleStorage(final String inputFile, final String outputFile) {
        fileReaderWriterProvider = new FileReaderWriterProvider(inputFile, outputFile);
        entities = new ArrayList<>();

        readEntities();
        writeEntities();
    }

    public void readEntities() {
        try {
            final BufferedReader reader = fileReaderWriterProvider.getReader();
            while (reader.ready()) {
                final StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), "\n");
                final String rawEntity = tokenizer.nextToken();
                final String[] entityAttr = rawEntity.split(":");

                if (entityAttr.length != 4) {
                    JOptionPane.showMessageDialog(null, String.format(ENTITY_PARSE_ERROR, rawEntity), "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                    return;
                }
                final String apellido = entityAttr[0];
                final String nombre = entityAttr[1];
                final char genero = entityAttr[2].charAt(0);
                final int edad = Integer.parseInt(entityAttr[3]);

                final Entity entity = new Entity(nombre, apellido, genero, edad);

                System.out.println("Añadiendo entidad (Entity): " + entity);
                if (entity.getGenero() == 'F') womenQuantity++;
                if (entity.getGenero() == 'M') menQuantity++;

                entities.add(entity);
            }
        } catch (final IOException exception) {
            JOptionPane.showMessageDialog(null, String.format(PANIC_ERROR, "PeopleStorage#writeEntities", exception.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
            System.exit(1);
        }
    }


    public void writeEntities() {
        try {
            final BufferedWriter writer = fileReaderWriterProvider.getWriter();
            writer.write("Nombre  Apellido  Programa sugerido");
            writer.newLine();
            writer.write("------------------------------------");
            writer.newLine();
            entities.forEach(entity -> {
                try {
                    final String suggestedWork = SuggestedWorkProcessor.getSuggestedWork(entity);

                    writer.write(entity.getNombre() + " " + entity.getApellido() + " " + suggestedWork);
                    writer.newLine();
                } catch (final IOException exception) {
                    JOptionPane.showMessageDialog(null, String.format(PANIC_ERROR, "PeopleStorage#writeEntities#forEach(Entity)", exception.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                    System.exit(1);
                }
            });

            final int total = (womenQuantity + menQuantity);
            final double womenPercentage = (100.0 * womenQuantity / total);
            final double menPercentage = (100.0 * menQuantity / total);
            final DecimalFormat percentageRounded = new DecimalFormat("##.00");

            writer.write("------------------------------------");
            writer.newLine();
            writer.write("Total mujeres: " + womenQuantity + " " + "Total hombres: " + menQuantity + " " + "Total general: " + (womenQuantity + menQuantity));
            writer.newLine();
            writer.write("Promedio mujeres: " + percentageRounded.format(womenPercentage) + "% " + "Promedio hombres: " + percentageRounded.format(menPercentage) + "%");

            writer.flush();

            JOptionPane.showMessageDialog(null, String.format(WRITE_SUCCESSFUL, entities.size(), fileReaderWriterProvider.getOutputAbsolutePath()), "Correcto", JOptionPane.INFORMATION_MESSAGE);
        } catch (final IOException exception) {
            JOptionPane.showMessageDialog(null, String.format(PANIC_ERROR, "PeopleStorage#writeEntities", exception.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
            System.exit(1);
        }
    }
}
