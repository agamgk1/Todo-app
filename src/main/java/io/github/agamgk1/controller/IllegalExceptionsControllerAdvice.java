package io.github.agamgk1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//adnotacja pozwala obsłuzyc bledy z kontrolerow z adnotacja IllegalExceptionProcessing

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)
public class IllegalExceptionsControllerAdvice {

    //metoda umozliwiajaca obsługę wyjątkow - wszystkich kontrolerow. Po to żeby nie pojawiał się za każdym razem błąd serwera - 500.
    // Wywoła się automatycznie w razie ojawienia sie wyjatku
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }
    //to samo tylko inny wyjatek
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
