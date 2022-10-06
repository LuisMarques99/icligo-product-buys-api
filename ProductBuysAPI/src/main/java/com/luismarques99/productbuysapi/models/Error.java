package com.luismarques99.productbuysapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Class that represents the structure of an {@link Error Error} used to handle the exceptions thrown in the application
 * runtime.
 *
 * @author Luis Marques
 */
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Error {

    /**
     * Timestamp in which the exception occurred.
     */
    private String timestamp;

    /**
     * HTTP response status.
     */
    @Setter
    private Integer status;

    /**
     * Error title.
     */
    @Setter
    private String error;

    /**
     * Error description.
     */
    @Setter
    private String message;

    /**
     * List of {@link Field fields} that are causing errors.
     */
    private final List<Field> fields;

    /**
     * Creates an instance of an {@link Error Error}.
     *
     * @param status  HTTP response status
     * @param error   error title
     * @param message error message
     */
    public Error(Integer status, String error, String message) {
        setTimestamp();
        this.status = status;
        this.error = error;
        this.message = message;
        fields = new ArrayList<>();
    }

    /**
     * Adds a {@link Field Field} to the list of {@link Field fields}.
     *
     * @param field {@link Field Field}
     */
    public void addField(Field field) {
        this.fields.add(field);
    }

    /**
     * Defines the timestamp with the current time in UTC time zone.
     */
    private void setTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.timestamp = simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    /**
     * Inner class that represents the structure of a {@link Field Field}.
     *
     * @author Luis Marques
     */
    @Data
    public static class Field {

        /**
         * Name of the field which is causing the error.
         */
        private final String name;

        /**
         * Message about the field error.
         */
        private final String message;

    }

}
