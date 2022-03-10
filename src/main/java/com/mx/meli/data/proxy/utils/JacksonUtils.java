package com.mx.meli.data.proxy.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilidades de Jackson, mantienen una instancia de Jackson para ser
 * reutilzable y evitar la instanciacion del mismo en varias artes del proyecto
 * donde es necesario serealizar objetos
 * 
 */

public final class JacksonUtils {

    /**
     * Instancia Jackson 
     */
    public static final ObjectMapper JACKSON = new ObjectMapper();

    /**
     * Constructor vacio y privado ya que no se permiten instancias del mismo solo
     * acceso a las propiedades estaticas y publicas
     */
    private JacksonUtils() {
        /**
         * Constructor vacio y privado ya que no se permiten instancias del mismo solo
         * acceso a las propiedades estaticas y publicas
         */
    }

    /**
     * Metodo de ayuda para convertir un objecto a String json, se controla la
     * exception y se imprime como error, es probable que si ocurre un problema con
     * serealizado, puedan ocurrir problemas como nullpointerexceptio, etc, despues
     * del tretamiento del estring que se espera de respuesta
     * 
     * @param obj
     *            Objecto a Serealizar
     * @return {@link String}
     */
    public static String writeValueAsString(Object obj) {
        try {
            // convierte el Obj a String
            return JACKSON.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("No se pudo hace binding del JSON", e);
        }
    }

    public static String writeValueAsString(Object obj, boolean includeNulls) {
        try {
            // convierte el Obj a String
        	if(!includeNulls) {
        		JACKSON.setSerializationInclusion(Include.NON_NULL);
        	}
            return JACKSON.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("No se pudo hace binding del JSON", e);
        }
    }
    /**
     * Metodo de ayuda para convertir un objecto a String json pero con formato, se
     * controla la exception y se imprime como error, es probable que si ocurre un
     * problema con serealizado, puedan ocurrir problemas como nullpointerexceptio,
     * etc, despues del tretamiento del estring que se espera de respuesta
     * 
     * @param obj
     *            objeto a serealizar
     * @return json pretty
     */
    public static String writeValueAsStringPretty(Object obj) {
        try {
            // convierte el Obj a String
            return JACKSON.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("No se pudo hace binding del JSON", e);
        }
    }

    /**
     * metodo de apoyo para convertir String a Json,se controla la exception y se
     * imprime como error, es probable que si ocurre un problema con serealizado,
     * puedan ocurrir problemas como nullpointerexceptio, etc, despues del
     * tretamiento del estring que se espera de respuesta
     * 
     * @param jsonString
     *            String json
     * @param type
     *            modelo del json
     * @return retorna el tipo de clase informado
     */
    public static <T> T readValue(String jsonString, Class<T> type) {
        try {
            return JACKSON.readValue(jsonString, type);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo hacer binding del JSON", e);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T readValue(String jsonString,  TypeReference valueTypeRef) {
        try {
        	
        	
            return (T) JACKSON.readValue(jsonString, valueTypeRef);
            
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo hacer binding del JSON", e);
        }
    }
    
    public static <T> T convertValue(Object fromValue,  Class<T> toValueType) {
        try {

            return (T) JACKSON.convertValue(fromValue, toValueType);
            
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("El tipo de objeto no coincide cin la clase.", e);
        }
    }
}
