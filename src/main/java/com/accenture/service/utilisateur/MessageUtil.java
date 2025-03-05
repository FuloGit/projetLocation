package com.accenture.service.utilisateur;

public class MessageUtil {
   public static final String EMAIL_OU_PASSWORD_ERRONE = "Email ou password erroné";
   public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@\\-_§]).{8,}$";
   public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
}
