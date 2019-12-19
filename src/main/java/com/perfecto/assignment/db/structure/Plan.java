package com.perfecto.assignment.db.structure;

public enum Plan {
    FreeTrial,
    Fifty,
    Hundred,
    Unlimited,
    PerUse;

    public int getValue() {
        switch (this) {
            case FreeTrial:
                return 10;
            case Fifty:
                return 50;
            case Hundred:
                return 100;
            default:
                return -1;
        }
    }

}
