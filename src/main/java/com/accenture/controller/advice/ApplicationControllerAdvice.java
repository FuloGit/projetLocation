package com.accenture.controller.advice;

import com.accenture.exception.UtilisateurException;
import com.accenture.exception.VehiculeException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.accenture.shared.model.ErreurReponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * <p>Gère les exceptions</p>
 */
@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErreurReponse> gestionEntityException (EntityNotFoundException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Mauvaise Requete", ex.getMessage());
        log.error(er.message());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(UtilisateurException.class)
    public ResponseEntity<ErreurReponse> gestionAdministrateurException (UtilisateurException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur Fonctionnelle", ex.getMessage());
        log.error(er.message());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(VehiculeException.class)
    public ResponseEntity<ErreurReponse> gestionVoitureException(VehiculeException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Erreur Fonctionnelle", ex.getMessage());
        log.error(er.message());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurReponse> gestionArgumentNotValid (MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Validation erreur", message);
        log.error(er.message());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErreurReponse> gestionHttpNotReadableExceptiob(HttpMessageNotReadableException ex){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Mauvais typage", ex.getMessage());
        log.error(er.message());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }
}



