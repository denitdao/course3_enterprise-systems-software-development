package ua.kpi.tef.essd.exception;

import ua.kpi.tef.essd.util.EntityNames;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Integer id) {
        super(resourceName + " with id=" + id + " was not found.");
    }

    public ResourceNotFoundException(EntityNames resourceName, Integer id) {
        super(resourceName + " with id=" + id + " was not found.");
    }

    public ResourceNotFoundException(Integer id) {
        super("Resource with id=" + id + " was not found.");
    }

    public ResourceNotFoundException() {
        super("Resource was not found.");
    }

}
