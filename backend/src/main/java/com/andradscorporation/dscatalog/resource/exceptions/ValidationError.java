package com.andradscorporation.dscatalog.resource.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;

    private List<FieldMessages> errors = new ArrayList<>();

    public List<FieldMessages> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessages(fieldName, message));
    }

}
