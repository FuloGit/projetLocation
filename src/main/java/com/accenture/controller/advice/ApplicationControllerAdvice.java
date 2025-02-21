package com.accenture.controller.advice;

import com.accenture.exception.ClientException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.accenture.shared.model.ErreurReponse;

import java.time.LocalDateTime;

/**
 * <p>Gère les exceptions</p>
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErreurReponse> gestionTacheException (ClientException ex){
       ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur Fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErreurReponse> gestionEntityException (EntityNotFoundException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Mauvaise Requete", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }
}
