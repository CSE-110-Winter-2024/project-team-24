package edu.ucsd.cse110.successorator.lib.domain;

import java.io.Serializable;

public class Views implements Serializable {
    public enum ViewEnum {
        TODAY,
        TOMORROW,
        RECURRING,
        PENDING,
    }
}