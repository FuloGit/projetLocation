package com.accenture.shared.model;

import java.time.LocalDateTime;

public record ErreurReponse(LocalDateTime temporalite, String type, String message) {
}
