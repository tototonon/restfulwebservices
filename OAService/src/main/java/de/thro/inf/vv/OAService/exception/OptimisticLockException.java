package de.thro.inf.vv.OAService.exception;

public class OptimisticLockException extends Exception {
    public OptimisticLockException(String message) {
        super(message);
    }
}