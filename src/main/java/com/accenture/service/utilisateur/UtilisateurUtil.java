package com.accenture.service.utilisateur;

import com.accenture.exception.UtilisateurException;
import lombok.extern.slf4j.Slf4j;

/**
 * Pour les vérifications et messages communs des utilisateurs.
 */
@Slf4j
public class UtilisateurUtil {
    public static final String EMAIL_OU_PASSWORD_ERRONE = "Email ou password erroné";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@\\-_§]).{8,}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String VERIFIER_UTILISATEUR = "vérifier Utilisateur : {} ";

    static void verifierStringNotNullNotEmpty(String string, String message) {
        if (string == null || string.isBlank()) {
            log.error(VERIFIER_UTILISATEUR, message);
            throw new UtilisateurException(message);
        }
    }
    static void verifierObjectNotNull(Object object, String message) {
        if (object == null) {
            log.error(VERIFIER_UTILISATEUR, message);
            throw new UtilisateurException(message);
        }
    }
     static void verifierPassWordMatchingRegex(String string, String message){
         if (string == null || string.isBlank() || (!string.matches(PASSWORD_REGEX))) {
             log.error(VERIFIER_UTILISATEUR, message);
             throw  new UtilisateurException(message);

     }}

     static void verifierEmailMatchingRegex(String string, String message){
         if (string == null || string.isBlank() || (!string.matches(EMAIL_REGEX))) {
             log.error(VERIFIER_UTILISATEUR, message);
             throw  new UtilisateurException(message);
     }

}

    private UtilisateurUtil() {}
}
