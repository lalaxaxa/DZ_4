package com.borisov.DZ_4.messaging.publishers.events;

public class UserChangedEvent {
    private int id;
    private String email;
    private Operation operation;

    public UserChangedEvent() {
    }

    public UserChangedEvent(int id, String email, Operation operation) {
        this.id = id;
        this.email = email;
        this.operation = operation;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public static enum Operation {
        CREATE,
        DELETE;

        private Operation() {
        }
    }
}