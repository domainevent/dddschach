package com.iks.dddschach.service.binding_rest.providers;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;


/**
 * Behandelt die Ausnahme <code>BadRequestException</code> und erzeugt eine Response mit
 * Status-Code 400 und eine Plain-Text-Nachricht, die die Validationsfehler enthält
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    public final static String CR = System.lineSeparator();

    public Response toResponse(ConstraintViolationException exception) {
        final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        String errorList = "Validation errors:" + CR;
        int counter = 0;
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            final Path propertyPath = constraintViolation.getPropertyPath();
            final String message = constraintViolation.getMessage();
            final Object invalidValue = constraintViolation.getInvalidValue();
            if (counter > 0) errorList += CR;
            errorList += ++counter + ". ";
            if (message.startsWith("|")) {
                errorList += "The value " + propertyPath + " = ";
                errorList += (invalidValue == null) ? "<null>" : "'" + invalidValue + "'";
                errorList += " " + message.substring(1) + ".";
            }
            else {
                errorList += message;
            }
        }
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(errorList)
                .type(MediaType.TEXT_PLAIN).build();
    }

}