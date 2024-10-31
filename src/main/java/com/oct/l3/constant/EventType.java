package com.oct.l3.constant;

public class EventType {
    public static final String REGISTRATION = "REGISTRATION";
    public static final String SALARY_INCREASE = "SALARY INCREASE";
    public static final String PROPOSAL = "PROPOSAL";
    public static final String PROMOTION = "PROMOTION";
    public static final String TERMINATION_REQUEST  = "TERMINATION REQUEST";

    private EventType() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

}
