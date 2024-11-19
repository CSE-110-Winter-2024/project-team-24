package edu.ucsd.cse110.successorator.lib.domain;

import java.io.Serializable;

public class Contexts implements Serializable {
    public enum Context {
        HOME,
        WORK,
        SCHOOL,
        ERRANDS,
    }
}