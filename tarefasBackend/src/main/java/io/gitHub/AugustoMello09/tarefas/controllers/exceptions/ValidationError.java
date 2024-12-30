package io.gitHub.AugustoMello09.tarefas.controllers.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;
    
    private List<FieldMessage> errors = new ArrayList<>();
    
    public ValidationError() {
        super();
    }

    public ValidationError(LocalDateTime timestemp, Integer status, String error, String path) {
        super(timestemp, status, error, path);
    }

    public void addErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}

