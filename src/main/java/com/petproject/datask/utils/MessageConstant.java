package com.petproject.datask.utils;

public enum MessageConstant {
    USERNAME_EXISTED("Username already existed."),
    INVALID_USERNAME("Username is invalid."),
    LOGIN_SUCCESSFULLY("Login Successfully."),
    CREATED_SUCCESSFULLY("Created Successfully."),
    DELETED_SUCCESSFULLY("Deleted Successfully."),
    UPDATED_SUCCESSFULLY("Updated Successfully."),
    UNAUTHORIZED("Unauthorized."),
    INVALID_API_KEY("Invalid API key."),
    RESOURCES_NOT_FOUND("Resources not found"),
    SUCCESS("Success");
    public final String label;

    MessageConstant(String label) {
        this.label = label;
    }
}
