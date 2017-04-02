package com.pk.model;

import lombok.Data;
import lombok.NonNull;

/**
 * a person
 * Created by pallav.kothari on 4/2/17.
 */
public @Data class Person {
    @NonNull private final String firstName, lastName;
}
