package com.perfecto.assignment.entitlement;

import com.perfecto.assignment.db.connectivity.DBFaker;
import com.perfecto.assignment.db.structure.Entitlement;
import com.perfecto.assignment.db.structure.Plan;
import com.perfecto.assignment.exceptions.UserCreationException;

/**
 * Service to support restrictions on user according to plans
 */
public class EntitlementService {

    private final static String UNLIMITED = "unlimited";

    public static boolean isValidForSearchingMatchingSongs (int userId) {
        String numSearchesLeft = getUserNumOfSearchesLeft(userId);
        return UNLIMITED.equals(numSearchesLeft) || Integer.parseInt(numSearchesLeft) > 0;
    }

    public static void increaseUserSearchCount(int userId) {
        DBFaker.getInstance().increaseUsageForUser(userId);
    }

    public static String getUserNumOfSearchesLeft(int userId) {
        Entitlement userEntitlement = DBFaker.getInstance().getUserEntitlement(userId);
        switch (userEntitlement.getPlan()) {
            case Fifty:
            case Hundred:
            case FreeTrial:
                return Integer.toString(userEntitlement.getPlan().getValue() - userEntitlement.getNumberOfSearches());
            default:
                return UNLIMITED;
        }
    }

    public static int addUserWithPlan(String username, String plan) throws UserCreationException {
        return DBFaker.getInstance().addUserWithPlan(username, Plan.valueOf(plan));
    }
}
