package com.perfecto.assignment.db.connectivity;

import com.perfecto.assignment.db.structure.Entitlement;
import com.perfecto.assignment.db.structure.Plan;
import com.perfecto.assignment.db.structure.User;
import com.perfecto.assignment.exceptions.UserCreationException;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simulates the DB collections
 */
public class DBFaker {

    final static int INVALID_USER = -1;
    final static int USER_EXISTS = -2;

    HashMap<String, User> users;
    HashMap<Integer, Entitlement> entitlements;

    int runningUserId = 0;
    ReentrantLock userAdditionLock = new ReentrantLock();

    private static DBFaker instance;

    private DBFaker() {
        entitlements = new HashMap<>();
        users = new HashMap<>();
    }

    public static DBFaker getInstance() {
        // should add lock but seems irrelevant for the assignment
        if (instance == null) {
            instance = new DBFaker();
        }
        return instance;
    }

    public int addUserWithPlan(String username, Plan plan) throws UserCreationException {
        int userId = addUser(username);
        if (userId == INVALID_USER) {
            throw new UserCreationException(username, "write failure - try again");
        } else if (userId == USER_EXISTS) {
            throw new UserCreationException(username, "user already exists");
        }
        entitlements.put(userId, new Entitlement(userId, 0, plan));
        return userId;
    }

    private int addUser(String username) {
        if (users.containsKey(username)) {
            return USER_EXISTS;
        } else {
            int myUserId;
            try {
                userAdditionLock.lock();
                myUserId = runningUserId;
                runningUserId++; // even though it can be done in a single line - this way is more readable
                userAdditionLock.unlock();
                users.put(username, new User(myUserId, username));
            } catch (Exception e) {
                System.err.println("Failed to add user to database due to " +  e.getMessage());
                return INVALID_USER;
            }
            return myUserId;
        }
    }

    public void increaseUsageForUser(int userId) {
        Entitlement userEntitlement = entitlements.get(userId);
        userEntitlement.increaseNumberOfSearches();
    }

    public Entitlement getUserEntitlement(int userId) {
        return entitlements.get(userId);
    }
}
