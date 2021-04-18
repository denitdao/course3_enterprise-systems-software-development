package ua.kpi.tef.essd.exception;

public class WrongValueException extends RuntimeException {

    public WrongValueException(String valueName, Integer value) {
        super(valueName + " of " + value + " is not valid.");
    }

    public WrongValueException(Integer value) {
        super("Value of " + value + " is not valid.");
    }

    public WrongValueException() {
        super("Wrong value.");
    }

}
