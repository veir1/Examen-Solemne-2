package me.veir1.municipalidad.storage.work;

import me.veir1.municipalidad.entity.Entity;

public final class SuggestedWorkProcessor {
    private final static String MUJERES_TEJEDORAS = "Mujeres Tejedoras.";
    private final static String IDIOMA_ALTERNATIVO = "Idioma alternativo.";
    private final static String NATACION = "Natación.";

    private final static String CURSO_INGLES = "Curso de inglés.";
    private final static String JARDINERIA = "Jardinería.";
    private final static String CURSO_MANEJO = "Curso de manejo clase B.";

    private final static String UNKNOWN_RANGE = "Trabajo desconocido o fuera del rango de edades.";

    public static String getSuggestedWork(final Entity entity) {
        final int edad = entity.getEdad();

        if (entity.getGenero() == 'F') {
            if (edad >= 70) {
                return MUJERES_TEJEDORAS;
            }
            if (edad >= 50) {
                return IDIOMA_ALTERNATIVO;
            }
            if (edad >= 30) {
                return NATACION;
            }
        }

        if (entity.getGenero() == 'M') {
            if (edad >= 70) {
                return CURSO_INGLES;
            }
            if (edad >= 50) {
                return JARDINERIA;
            }
            if (edad >= 30) {
                return CURSO_MANEJO;
            }
        }

        return UNKNOWN_RANGE;
    }
}
