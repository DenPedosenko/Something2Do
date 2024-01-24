package com.sysaid.assignment.domain;

import java.io.Serial;
import java.io.Serializable;


/**
 * representing simple task
 */

public record Task(String activity, Float accessibility, String type, Integer participants, Float price, String link,
                   String key) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
